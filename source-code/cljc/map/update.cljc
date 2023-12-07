
(ns map.update
    (:require [seqable.api  :as seqable]
              [vector.check :as check]
              [vector.dex   :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-all-key
  ; @description
  ; Updates all key in the given 'n' map with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-all-key {:a "A" :b "B" :c "C"} name)
  ;
  ; @example
  ; (update-all-key {:a "A" :b "B" :c "C"} name)
  ; =>
  ; {"a" "A" "b" "B" "c" "C"}
  ;
  ; @example
  ; (update-all-key {0 "A" 1 "B" 2 "C"} + 10)
  ; =>
  ; {10 "A" 11 "B" 12 "C"}
  ;
  ; @return (map)
  [n f & params]
  (letfn [(f0 [result k v]
              (assoc result (apply f k params) v))]
         (reduce-kv f0 {} n)))

(defn update-all-value
  ; @description
  ; Updates all value in the given 'n' map with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (map) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-all-value {:a "A" :b "B" :c "C"} keyword)
  ;
  ; @example
  ; (update-all-value {:a "A" :b "B" :c "C"} keyword)
  ; =>
  ; {:a :A :b :B :c :C}
  ;
  ; @example
  ; (update-all-value {:a 0 :b 1 :c 2} + 10)
  ; =>
  ; {:a 10 :b 11 :c 12}
  ;
  ; @return (map)
  [n f & params]
  (letfn [(f0 [result k v]
              (assoc result k (apply f v params)))]
         (reduce-kv f0 {} n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-keys-by
  ; @description
  ; Updates all key (that the given 'test-f' function returns TRUE for) in the given 'n' map,
  ; with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-keys-by {0 "A" 1 "B" 2 "C"} even? + 10)
  ;
  ; @example
  ; (update-keys-by {0 "A" 1 "B" 2 "C"} even? + 10)
  ; =>
  ; {10 "A" 1 "B" 12 "C"}
  ;
  ; @return (map)
  [n test-f f & params]
  (letfn [(f0 [result k v]
              (if (test-f k)
                  (assoc result (apply f k params) v)
                  (assoc result k v)))]
         (reduce-kv f0 {} n)))

(defn update-values-by
  ; @description
  ; Updates all value (that the given 'test-f' function returns TRUE for) in the given 'n' map,
  ; with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (map) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-values-by {:a 0 :b 1 :c 2} even? + 10)
  ;
  ; @example
  ; (update-values-by {:a 0 :b 1 :c 2} even? + 10)
  ; =>
  ; {:a 10 :b 1 :c 12}
  ;
  ; @return (map)
  [n test-f f & params]
  (letfn [(f0 [result k v]
              (if (test-f v)
                  (assoc result k (apply f v params))
                  (assoc result k v)))]
         (reduce-kv f0 {} n)))
