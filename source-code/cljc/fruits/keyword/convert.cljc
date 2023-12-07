
(ns fruits.keyword.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (keyword) n
  ;
  ; @usage
  ; (to-string :a/b)
  ;
  ; @example
  ; (to-string :a/b)
  ; =>
  ; "a/b"
  ;
  ; @return (string)
  [n]
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (str namespace "/" (name n))
              (name n))
      (str n)))
