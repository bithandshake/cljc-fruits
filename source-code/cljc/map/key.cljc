
(ns map.key
    (:refer-clojure :exclude [keys]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keys
  ; @description
  ; Returns the keys of the given 'n' map in a vector.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (keys {:a "A" :b "B"})
  ;
  ; @example
  ; (keys {:a {:c "C"} :b "B"})
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n]
  (-> n clojure.core/keys vec))

(defn keys-by
  ; @description
  ; Returns the keys of the given 'n' map for which the given 'f' function returns TRUE.
  ;
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (keys-by {:a "A" :b :b} string?)
  ;
  ; @example
  ; (keys-by {:a "A" :b :b :c :c} string?)
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n get-f]
  (letfn [(f0 [%1 %2 %3] (if (get-f %3) (conj %1 %2) %1))]
         (reduce-kv f0 [] n)))

(defn first-key
  ; @warning
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first key of the given 'n' map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (first-key {:a "A" :b "B"})
  ;
  ; @example
  ; (first-key {:a {:c "C"} :b "B"})
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (-> n clojure.core/keys first))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contains-key?
  ; @description
  ; Returns TRUE if the given 'n' map contains the given 'k' key.
  ;
  ; @param (map) n
  ; @param (*) k
  ;
  ; @usage
  ; (contains-key? {:a "B" :b "B"} :a)
  ;
  ; @example
  ; (contains-key? {:a {:b "B"}} :a)
  ; =>
  ; true
  ;
  ; @example
  ; (contains-key? {:a {:b "B"}} :b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n k]
  (contains? n k))

(defn contains-any-key?
  ; @description
  ; Returns TRUE if the given 'n' map contains any value associated to a key from the given 'ks' vector.
  ;
  ; @param (map) n
  ; @param (* in vector) ks
  ;
  ; @usage
  ; (contains-any-key? {:a "A" :b "B"} [:a])
  ;
  ; @example
  ; (contains-any-key? {:a {:b "B"} :c "C"} [:a])
  ; =>
  ; true
  ;
  ; @example
  ; (contains-any-key? {:a {:b "B"} :c "C"} [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @example
  ; (contains-any-key? {:a {:b "B"}} [:b :c :d])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n ks]
  (letfn [(f0 [%] (contains? n %))]
         (boolean (some f0 ks))))

(defn contains-all-key?
  ; @description
  ; Returns TRUE if the given 'n' map contains all values associated to a key from the given 'ks' vector.
  ;
  ; @param (map) n
  ; @param (* in vector) ks
  ;
  ; @usage
  ; (contains-all-key? {:a "A" :b "B"} [:a :b])
  ;
  ; @example
  ; (contains-all-key? {:a "A" :b "B"} [:a :b])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n ks]
  (letfn [(f0 [%] (contains? n %))]
         (every? ks f0)))
