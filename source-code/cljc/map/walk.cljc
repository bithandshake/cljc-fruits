
(ns map.walk
    (:require [map.remove :as remove]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->keys
  ; @description
  ; - Applies the given 'update-f' function on each key of the given 'n' map.
  ; - The 'update-f' function takes a key as parameter.
  ;
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @usage
  ; (->keys {"a" "A" "b" "B"} keyword)
  ;
  ; @example
  ; (->keys {:a "A" :b "B"} name)
  ; =>
  ; {"a" "A" "b" "B"}
  ;
  ; @return (map)
  [n update-f]
  (letfn [(f [%1 %2 %3] (assoc %1 (update-f %2) %3))]
         (reduce-kv f {} n)))

(defn ->>keys
  ; @description
  ; - Applies the given 'update-f' function on each key of the given 'n' map (recursivelly).
  ; - The 'update-f' function takes a key as parameter.
  ;
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @usage
  ; (->>keys {:a "A" :b [{:c "C"}]} name)
  ;
  ; @example
  ; (->>keys {:a "A" :b "B" :c [{:d "D"}]} name)
  ; =>
  ; {"a" "A" "b" "B" "c" [{"d" "D"}]}
  ;
  ; @return (map)
  [n update-f]
  ; The recursion DOES NOT apply the 'update-f' function on vector items,
  ; because vector items are equivalents with map values not with map keys!
  (letfn [(f [n] (cond (vector? n) (reduce    #(conj  %1               (f %2)) [] n)
                       (map?    n) (reduce-kv #(assoc %1 (update-f %2) (f %3)) {} n)
                       :return  n))]
         (f n)))

(defn ->values
  ; @description
  ; - Applies the given 'update-f' function on each value of the given 'n' map.
  ; - The 'update-f' function takes a value as parameter.
  ;
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @example
  ; (->values {:a "A" :b "B"} keyword)
  ; =>
  ; {:a :A :b :B}
  ;
  ; @return (map)
  [n update-f]
  (reduce-kv #(assoc %1 %2 (update-f %3)) {} n))

(defn ->>values
  ; @description
  ; - Applies the given 'update-f' function on each value of the given 'n' map (recursivelly).
  ; - The 'update-f' function takes a value as parameter.
  ;
  ; @param (map) n
  ; @param (function) update-f
  ;
  ; @usage
  ; (->>values {:a "A" :b ["C"]} keyword)
  ;
  ; @example
  ; (->>values {:a "A" :b "B" :c [:d "E" {:f "F"}]} keyword)
  ; =>
  ; {:a :A :b :B :c [:d :e {:f :F}]}
  ;
  ; @return (map)
  [n update-f]
  ; The recursion applies the 'update-f' function on vector items as well,
  ; because vector items are equivalents with map values!
  (letfn [(f [n] (cond (map?    n) (reduce-kv #(assoc %1 %2 (f %3)) {} n)
                       (vector? n) (reduce    #(conj  %1    (f %2)) [] n)
                       :return     (update-f n)))]
         (f n)))

(defn ->kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map.
  ; - The 'k-f' function takes a key as parameter.
  ;   The 'v-f' function takes a value as parameter.
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ;
  ; @usage
  ; (->kv {:a 1} name inc)
  ;
  ; @example
  ; (->kv {:a 1 :b 2} name inc)
  ; =>
  ; {"a" 2 "b" 3}
  ;
  ; @return (map)
  [n k-f v-f]
  (letfn [(f [%1 %2 %3] (assoc %1 (k-f %2) (v-f %3)))]
         (reduce-kv f {} n)))

(defn ->>kv
  ; @description
  ; - Applies the given 'k-f' function on each key and the given 'v-f' function on each value of the given 'n' map (recursivelly).
  ; - The 'k-f' function takes a key as parameter.
  ;   The 'v-f' function takes a value as parameter.
  ;
  ; @param (map) n
  ; @param (function) k-f
  ; @param (function) v-f
  ;
  ; @example
  ; (->>kv {"a" "A" "b" ["C"]} keyword keyword)
  ;
  ; @example
  ; (->>kv {"a" "A" "b" "B" "c" ["D" "E" {"f" "F"}]} keyword keyword)
  ; =>
  ; {:a :A :b :B :c [:D :E {:f :F}]}
  ;
  ; @return (map)
  [n k-f v-f]
  ; The recursion applies the 'v-f' function on vector items as well,
  ; because vector items are equivalents with map values!
  (letfn [(f [n] (cond (map?    n) (reduce-kv #(assoc %1 (k-f %2) (f %3)) {} n)
                       (vector? n) (reduce    #(conj  %1          (f %2)) [] n)
                       :return     (v-f n)))]
         (f n)))

(defn ->remove-keys-by
  ; @description
  ; - Removes specific keys from the given 'n' map.
  ;   Decides which keys to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a key as parameter.
  ;   If the 'r-f' function returns TRUE, the key will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @usage
  ; (->remove-keys-by {:a "A"} #(= % :a))
  ;
  ; @example
  ; (->remove-keys-by {:a "A" :b "B"}
  ;                   #(= % :a))
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  [n r-f]
  (remove/remove-keys-by n r-f))

(defn ->>remove-keys-by
  ; @description
  ; - Removes specific keys from the given 'n' map (recursivelly).
  ; - Decides which keys to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a key as parameter.
  ;   If the 'r-f' function returns TRUE, the key will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @example
  ; (->>remove-keys-by {:a "A" :b "B" :c {:a "A" :b "B"}}
  ;                    #(= % :a))
  ; =>
  ; {:b "B" :c {:b "B"}}
  ;
  ; @return (map)
  [n r-f])
  ; TODO

(defn ->remove-values-by
  ; @description
  ; - Removes specific values from the given 'n' map.
  ;   Decides which values to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a value as parameter.
  ;   If the 'r-f' function returns TRUE, the value will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @example
  ; (->remove-values-by {:a "A" :b "B"}
  ;                     #(= % "A"))
  ; =>
  ; {:b "B"}
  ;
  ; @return (map)
  [n r-f]
  (remove/remove-values-by n r-f))

(defn ->>remove-values-by
  ; @description
  ; - Removes specific values from the given 'n' map (recursivelly).
  ;   Decides which values to be removed by applying the given 'r-f' function on them.
  ; - The 'r-f' function takes a value as parameter.
  ;   If the 'r-f' function returns TRUE, the value will be removed.
  ;
  ; @param (map) n
  ; @param (function) r-f
  ;
  ; @usage
  ; (->>remove-values-by {:a "A" :b {:c [{:d "D"}]}}
  ;                      #(= % "D"))
  ;
  ; @example
  ; (->>remove-values-by {:a "A" :b "B" :c {:a "A" :b "B" :c [{:a "A"}]}}
  ;                      #(= % "A"))
  ; =>
  ; {:b "B" :c {:b "B" :c [{}]}}
  ;
  ; @return (map)
  [n r-f]
  ; The recursion applies the 'f' function on vector items as well,
  ; because vector items are equivalents with map values!
  (letfn [(m-f [n k x] (if   (r-f     x) n (assoc n k (f x))))
          (v-f [n   x] (if   (r-f     x) n (conj  n   (f x))))
          (f   [n]     (cond (map?    n)   (reduce-kv m-f {} n)
                             (vector? n)   (reduce    v-f [] n)
                             :return  n))]
         (f n)))
