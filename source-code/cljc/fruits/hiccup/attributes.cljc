
(ns fruits.hiccup.attributes
    (:require [fruits.keyword.api   :as keyword]
              [fruits.normalize.api :as normalize]
              [fruits.string.api    :as string]
              [fruits.vector.api    :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-attributes
  ; @param (hiccup) n
  ;
  ; @usage
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
  ; =>
  ; [:div {:style {:color "red"}} "Hello World!"]
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
  ; (join-class :my-class [:another-class])
  ; =>
  ; [:my-class :another-class]
  ;
  ; @usage
  ; [:div {:class (join-class :my-class [:another-class])}]
  ; =>
  ; [:div {:class [:my-class :another-class]}]
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
  ; Converts the given 'n' keyword / string into HICCUP attribute value.
  ;
  ; @param (keyword or string) n
  ; @param (string)(opt) flag
  ;
  ; @usage
  ; (value "my-namespace/my-value?")
  ; =>
  ; "my-namespace--my-value"
  ;
  ; @usage
  ; (value :my-namespace/my-value!)
  ; =>
  ; "my-namespace--my-value"
  ;
  ; @usage
  ; (value :my-namespace/my-value "420")
  ; =>
  ; "my-namespace--my-value--420"
  ;
  ; @usage
  ; [:div {:id (value :my-namespace/my-value)}]
  ; =>
  ; [:div {:id "my-namespace--my-value"}]
  ;
  ; @return (string)
  [n & [flag]]
  (letfn [(f0 [%] (string/replace-part % "." "-"))]
         (if (-> n keyword?)
             (str (if-let [namespace (namespace n)] (str (-> namespace f0 normalize/clean-text) "--"))
                  (let    [name      (name      n)]      (-> name      f0 normalize/clean-text))
                  (if flag (str "--" flag)))
             (str (-> n f0 normalize/clean-text)
                  (if flag (str "--" flag))))))
