
(ns fruits.hiccup.attributes
    (:require [fruits.keyword.api   :as keyword]
              [fruits.normalize.api :as normalize]
              [fruits.string.api    :as string]
              [fruits.vector.api    :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-attributes
  ; @description
  ; Returns the attributes map (if any) of the given HICCUP element.
  ;
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
  ; @description
  ; Returns the inline style map (if any) of the given HICCUP element.
  ;
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
  ; @description
  ; Associates the given inline style map to the given HICCUP element.
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (set-style [:div "Hello World!"] {:color "red"})
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
  ; @description
  ; Returns the given CSS classes merged into a single vector.
  ;
  ; @param (list of keywords or keywords in vectors) abc
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
  [& abc]
  (letfn [(f0 [result x] (cond (vector?  x) (concat result x)
                               (keyword? x) (conj   result x)
                               :return result))]
         (reduce f0 [] abc)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn merge-event-fn
  ; @description
  ; - Associates the given 'event-f' function to a specific key within the given attribute map.
  ; - In case the attribute map already contains a function associated to the key, it merges the two functions.
  ;
  ; @param (map) attributes
  ; @param (keyword) event-key
  ; @param (map) event-f
  ;
  ; @usage
  ; (merge-event-fn {:on-click (fn [_] (println "Function #1")) ...}
  ;                 :on-click
  ;                 (fn [_] (println "Function #2")))
  ; =>
  ; {:on-click (fn [& params] (apply (fn [_] (println "Function #1"))  params)
  ;                           (apply (fn [_] (println "Function #2"))  params))
  ;  ...}
  ;
  ; @return (map)
  [attributes event-key event-f]
  (cond (-> event-f              ifn? not) (-> attributes)
        (-> attributes event-key ifn? not) (-> attributes (assoc event-key (fn [& params] (-> event-f              (apply params)))))
        :merge-event-fn                    (-> attributes (assoc event-key (fn [& params] (-> event-f              (apply params))
                                                                                          (-> attributes event-key (apply params)))))))

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
  ; - Replaces special characters with dash characters instead of just simply removing them, to decrease the possibilty of identical outputs:
  ;   (value :abc*) => :abc_
  ;   (value :abc)  => :abc
  ; - Otherwise, these outputs would be identical:
  ;   (value :abc*) => :abc
  ;   (value :abc)  => :abc
  (letfn [(f0 [%] (string/replace-part  % "." "-"))
          (f1 [%] (normalize/clean-text % "-" "_"))]
         (if (-> n keyword?)
             (str (if-let [namespace (namespace n)] (str (-> namespace f0 f1) "--"))
                  (let    [name      (name      n)]      (-> name      f0 f1))
                  (if flag (str "--" flag)))
             (str (-> n f0 f1)
                  (if flag (str "--" flag))))))
