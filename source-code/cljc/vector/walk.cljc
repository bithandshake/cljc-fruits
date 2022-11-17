
(ns vector.walk)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->items
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @usage
  ; (->items [0 1 2] inc)
  ;
  ; @example
  ; (->items [:a :b :c] name)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  [n update-f]
  (letfn [(f [%1 %2] (conj %1 (update-f %2)))]
         (reduce f [] n)))

(defn ->>items
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @usage
  ; (->>items [0 1 2 [3 4 {:x 6}]] inc)
  ;
  ; @example
  ; (->>items [:a :b :c [:d :e {:e :f}]] name)
  ; =>
  ; ["a" "b" "c" ["d" "e" {:e "f"}]]
  ;
  ; @return (vector)
  [n update-f]
  ; A rekurzió a térképek értékein is végrehajtja az update-f függvényt, mivel azok a vektorok elemeinek megfelelői!
  (letfn [(f [n] (cond (vector? n) (reduce    #(conj  %1    (f %2)) [] n)
                       (map?    n) (reduce-kv #(assoc %1 %2 (f %3)) {} n)
                       :return     (update-f n)))]
         (f n)))
