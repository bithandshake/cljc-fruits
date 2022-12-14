
(ns hiccup.attributes
    (:require [candy.api   :refer [return]]
              [keyword.api :as keyword]
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
              (if (map?   attributes)
                  (return attributes)))))

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
              (vector/inject-item n 1 {:style style}))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join-class
  ; @param (list of keyword or keywords in vector) xyz
  ;
  ; @example
  ; (join-class :my-class [:your-class] :our-class)
  ; =>
  ; [:my-class :your-class :our-class]
  ;
  ; @return (keywords in vector)
  [& xyz]
  (letfn [(join-class-f [o x] (cond (vector?  x) (concat o x)
                                    (keyword? x) (conj   o x)
                                    :return   o))]
         (reduce join-class-f [] xyz)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn value
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
  ; A value f??ggv??ny az n param??terk??nt ??tadott kulcssz?? vagy string t??pus??
  ; ??rt??ket ??gy alak??tja ??t, hogy az megfeleljen egy HTML attrib??tum ??rt??kek??nt.
  (let [x (cond (keyword? n) (keyword/to-string n)
                (string?  n) (return            n))]
       (letfn [(f [result tag] (case tag "." (str result "--")
                                         "/" (str result "--")
                                         "?" result
                                         "!" result
                                         ">" result
                                             (str result tag)))]
              (str (reduce f nil x)
                   (if flag (str "--" flag))))))
