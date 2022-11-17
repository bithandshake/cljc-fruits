
(ns keyword.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join
  ; @param (keywords and/or strings) abc
  ;
  ; @usage
  ; (join :a :b "c")
  ;
  ; @example
  ; (join :a :b "c" :d)
  ; =>
  ; :abcd
  ;
  ; @example
  ; (join :x/a :x/b "c" :d)
  ; =>
  ; :abcd
  ;
  ; @return (keyword)
  [& abc]
  (letfn [(f [result x] (if (keyword? x) (str result (name x))
                                         (str result x)))]
         (keyword (reduce f abc))))

(defn append
  ; @param (keyword) n
  ; @param (keyword) x
  ; @param (string)(opt) separator
  ;
  ; @example
  ; (append :a :b)
  ; =>
  ; :ab
  ;
  ; @example
  ; (append :a/b :c)
  ; =>
  ; :a/bc
  ;
  ; @example
  ; (append :a/b :c "--")
  ; =>
  ; :a/b--c
  ;
  ; @return (keyword)
  ([n x]
   (if-let [namespace (namespace n)]
           ; If n is namespaced ...
           (keyword namespace (str (name n) (name x)))
           ; If n is NOT namespaced ...
           (keyword (str (name n) (name x)))))

  ([n x separator]
   (if-let [namespace (namespace n)]
           ; If n is namespaced ...
           (keyword namespace (str (name n) separator (name x)))
           ; If n is NOT namespaced ...
           (keyword (str (name n) separator (name x))))))
