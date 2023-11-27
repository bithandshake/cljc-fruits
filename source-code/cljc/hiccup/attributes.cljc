
(ns hiccup.attributes
    (:require [keyword.api :as keyword]
              [vector.api  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-attributes
  ; @param (hiccup) n
  ;
  ; @example
  ; (get-attributes [:div {:style {:color "red"}} "Hello World!"])
  ; =>
  ; {:style {:color "red"}}
  ;
  ; @return (map)
  [n]
  (if (vector? n)
      (if-let [attributes (vector/nth-item n 1)]
              (if (-> attributes map?)
                  (-> attributes)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-style
  ; @param (hiccup) n
  ;
  ; @example
  ; (get-style [:div {:style {:color "red"}} "Hello World!"])
  ; =>
  ; {:color "red"}
  ;
  ; @return (map)
  [n]
  (if-let [attributes (get-attributes n)]
          (:style attributes)))

(defn set-style
  ; @param (hiccup) n
  ;
  ; @example
  ; (set-style [:div {:style {:color "red"}} "Hello World!"])
  ; =>
  ; {:style {:color "red"}}
  ;
  ; @return (map)
  [n style]
  (if (vector? n)
      (if-let [attributes (get-attributes n)]
              (assoc-in n [1 :style] style)
              (vector/insert-item n 1 {:style style}))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join-class
  ; @param (list of keywords or keywords in vectors) xyz
  ;
  ; @example
  ; (join-class :my-class [:your-class] :our-class)
  ; =>
  ; [:my-class :your-class :our-class]
  ;
  ; @return (keywords in vector)
  [& xyz]
  (letfn [(join-class-f [result x] (cond (vector?  x) (concat result x)
                                         (keyword? x) (conj   result x)
                                         :return result))]
         (reduce join-class-f [] xyz)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn value
  ; @description
  ; Converts the given 'n' keyword / string into a valid HICCUP attribute value.
  ;
  ; @param (keyword or string) n
  ; @param (string)(opt) flag
  ;
  ; @example
  ; (value "my-namespace/my-value?")
  ; =>
  ; "my-namespace--my-value"
  ;
  ; @example
  ; (value :your-namespace/your-value!)
  ; =>
  ; "your-namespace--your-value"
  ;
  ; @example
  ; (value :our-namespace/our-value "420")
  ; =>
  ; "our-namespace--our-value--420"
  ;
  ; @return (string)
  [n & [flag]]
  (let [n (cond (keyword? n) (-> n keyword/to-string)
                (string?  n) (-> n))]
       (letfn [(f0 [result char]
                   (case char "." (str result "--")
                              "/" (str result "--")
                              "?" result
                              "!" result
                              ">" result
                                  (str result char)))]
              (str (reduce f0 nil n)
                   (if flag (str "--" flag))))))
