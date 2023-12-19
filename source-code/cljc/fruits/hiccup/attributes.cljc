
(ns fruits.hiccup.attributes
    (:require [fruits.keyword.api :as keyword]
              [fruits.vector.api  :as vector]
              [fruits.normalize.api :as normalize]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-attributes
  ; @param (hiccup) n
  ;
  ; @usage
  ; (get-attributes [:div {:style {:color "red"}} "Hello World!"])
  ;
  ; @example
  ; (get-attributes [:div {:style {:color "red"}} "Hello World!"])
  ; =>
  ; {:style {:color "red"}}
  ;
  ; @return (map)
  [n]
  (if (vector? n)
      (let [[_ x & _] n]
           (if (-> x map?)
               (-> x)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-style
  ; @param (hiccup) n
  ;
  ; @usage
  ; (get-style [:div {:style {:color "red"}} "Hello World!"])
  ;
  ; @example
  ; (get-style [:div {:style {:color "red"}} "Hello World!"])
  ; =>
  ; {:color "red"}
  ;
  ; @return (map)
  [n]
  (if (vector? n)
      (if-let [attributes (get-attributes n)]
              (:style attributes))))

(defn set-style
  ; @param (hiccup) n
  ;
  ; @usage
  ; (set-style [:div {:style {:color "red"}} "Hello World!"])
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
  ; @usage
  ; (join-class :my-class [:your-class] :our-class)
  ;
  ; @example
  ; (join-class :my-class [:your-class] :our-class)
  ; =>
  ; [:my-class :your-class :our-class]
  ;
  ; @return (keywords in vector)
  [& xyz]
  (letfn [(f0 [result x] (cond (vector?  x) (concat result x)
                               (keyword? x) (conj   result x)
                               :return result))]
         (reduce f0 [] xyz)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn value
  ; @description
  ; Converts the given 'n' keyword / string into a valid HICCUP attribute value.
  ;
  ; @param (keyword or string) n
  ; @param (string)(opt) flag
  ;
  ; @usage
  ; (value "my-namespace/my-value?")
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
  (letfn [(f0 [%] (if flag (str % "--" flag) %))]
         (if (-> n keyword?)
             (-> n keyword/to-string normalize/clean-text f0)
             (-> n str               normalize/clean-text f0))))
