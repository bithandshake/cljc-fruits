
(ns fruits.hiccup.type)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn hiccup?
  ; @param (*) n
  ;
  ; @usage
  ; (hiccup? [:div {:class :my-class}])
  ;
  ; @example
  ; (hiccup? [:div {:class :my-class}])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n first keyword?)))

(defn tag-name?
  ; @param (hiccup) n
  ; @param (keyword) tag-name
  ;
  ; @usage
  ; (tag-name? [:div "Hello World!"] :div)
  ;
  ; @example
  ; (tag-name? [:div "Hello World!"] :div)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n tag-name]
  (and (-> n vector?)
       (-> n first (= tag-name))))
