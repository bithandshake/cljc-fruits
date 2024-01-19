
(ns fruits.loop.do)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn do-indexed
  ; @param (function) do-f
  ; @param (collection) coll
  ;
  ; @usage
  ; (do-indexed (fn [dex x]) [...])
  ;
  ; @usage
  ; (do-indexed (fn [dex x] (println x "is the" dex "th item of the collection"))
  ;             [:a :b :c :d :e])
  [do-f coll]
  (letfn [(f0 [dex x]
              (do-f dex x)
              (inc  dex))]
         (reduce f0 0 coll)
         (-> nil)))

(defn do-while
  ; @param (function) f
  ; @param (*) n
  ; The initial parameter of the 'f' function.
  ; @param (function) test-f
  ; If the 'test-f' functions returns true the iteration stops.
  ;
  ; @usage
  ; (do-while inc 0 #(> % 3))
  ; =>
  ; 4
  ;
  ; @return (*)
  [f n test-f]
  (let [result (f n)]
       (if (-> result test-f)
           (-> result)
           (do-while f result test-f))))
