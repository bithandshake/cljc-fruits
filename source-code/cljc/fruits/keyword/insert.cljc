
(ns fruits.keyword.insert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prepend
  ; @description
  ; Prepends the given 'x' keyword to the given 'n' keyword.
  ;
  ; @param (keyword) n
  ; @param (keyword) x
  ; @param (string)(opt) delimiter
  ;
  ; @usage
  ; (prepend :a :b)
  ; =>
  ; :ba
  ;
  ; @usage
  ; (prepend :a/b :c)
  ; =>
  ; :a/cb
  ;
  ; @usage
  ; (prepend :a/b :c "--")
  ; =>
  ; :a/c--b
  ;
  ; @return (keyword)
  ([n x]
   (if-let [namespace (namespace n)]
           ; If 'n' is namespaced ...
           (keyword namespace (str (name x) (name n)))
           ; If 'n' is NOT namespaced ...
           (keyword (str (name x) (name n)))))

  ([n x delimiter]
   (if-let [namespace (namespace n)]
           ; If 'n' is namespaced ...
           (keyword namespace (str (name x) delimiter (name n)))
           ; If 'n' is NOT namespaced ...
           (keyword (str (name x) delimiter (name n))))))

(defn append
  ; @description
  ; Appends the given 'x' keyword to the given 'n' keyword.
  ;
  ; @param (keyword) n
  ; @param (keyword) x
  ; @param (string)(opt) delimiter
  ;
  ; @usage
  ; (append :a :b)
  ; =>
  ; :ab
  ;
  ; @usage
  ; (append :a/b :c)
  ; =>
  ; :a/bc
  ;
  ; @usage
  ; (append :a/b :c "--")
  ; =>
  ; :a/b--c
  ;
  ; @return (keyword)
  ([n x]
   (if-let [namespace (namespace n)]
           ; If 'n' is namespaced ...
           (keyword namespace (str (name n) (name x)))
           ; If 'n' is NOT namespaced ...
           (keyword (str (name n) (name x)))))

  ([n x delimiter]
   (if-let [namespace (namespace n)]
           ; If 'n' is namespaced ...
           (keyword namespace (str (name n) delimiter (name x)))
           ; If 'n' is NOT namespaced ...
           (keyword (str (name n) delimiter (name x))))))
