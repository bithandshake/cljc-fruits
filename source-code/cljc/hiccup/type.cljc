
(ns hiccup.type)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn hiccup?
  ; @param (*) n
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n first keyword?)))

(defn tag-name?
  ; @param (hiccup) n
  ; @param (keyword) tag-name
  ;
  ; @example
  ; (tag-name? [:div "Hello World!"] :div)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n tag-name]
  (= (first n) tag-name))
