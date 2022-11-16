
(ns eql.type)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn document-link?
  ; @param (*) n
  ;
  ; @example
  ; (document-link? {:directory/id "my-directory"})
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and      (map?  n)
       (= 1 (count n))
       (-> n keys first keyword?)
       (-> n vals first string?)))

(defn document-entity?
  ; @param (*) n
  ;
  ; @example
  ; (document-entity? [:directory/id "my-directory"])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (boolean (and      (vector? n)
                (= 2 (count   n))
                (keyword? (first  n))
                (string?  (second n)))))
