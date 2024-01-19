
(ns fruits.loop.some)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn some-indexed
  ; @param (function) test-f
  ; @param (collection) coll
  ;
  ; @usage
  ; (some-indexed #(if (= %1 3) %2)
  ;               [:a :b :c :d :e])
  ; =>
  ; :d
  ;
  ; @usage
  ; (some-indexed #(if (= %2 :d) %1)
  ;               [:a :b :c :d :e])
  ; =>
  ; 3
  ;
  ; @return (*)
  [test-f coll]
  (letfn [(f0 [test-f coll dex]
              (if-let [result (test-f dex (get coll dex))]
                      (-> result)
                      (when-not (= dex (-> coll count dec))
                                (f0 test-f coll (inc dex)))))]
         (f0 test-f coll 0)))
