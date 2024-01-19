
(ns fruits.eql.append)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn append-to-query
  ; @param (nil or vector) query
  ; @param (keyword, map, string or vector) query-parts
  ;
  ; @usage
  ; (append-to-query nil :all-users)
  ; =>
  ; [:all-users]
  ;
  ; @usage
  ; (append-to-query [] :all-users)
  ; =>
  ; [:all-users]
  ;
  ; @usage
  ; (append-to-query [:all-users]
  ;                  [:directory/id :my-directory])
  ; =>
  ; [:all-users [:directory/id :my-directory]]
  ;
  ; @return (vector)
  [query & query-parts]
  (cond (vector?  query) (vec (concat query   query-parts))
        (nil?     query) (vec (concat []      query-parts))
        :return          (vec (concat [query] query-parts))))
