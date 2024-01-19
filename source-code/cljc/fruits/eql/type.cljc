
(ns fruits.eql.type)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn document-link?
  ; @param (*) n
  ;
  ; @usage
  ; (document-link? {:directory/id "my-directory"})
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n keys first keyword?)
       (-> n vals first string?)))

(defn document-entity?
  ; @param (*) n
  ;
  ; @usage
  ; (document-entity? [:directory/id "my-directory"])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n count (= 2))
       (-> n first keyword?)
       (-> n second string?)))
