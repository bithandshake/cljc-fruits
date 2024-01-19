
(ns fruits.keyword.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @description
  ; Returns the given 'n' keyword converted to string.
  ;
  ; @param (keyword) n
  ;
  ; @usage
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
