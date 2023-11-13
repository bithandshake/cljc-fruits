
(ns map.key
    (:refer-clojure :exclude [keys])
    (:require [loop.api :refer [reduce-pairs]]))

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
  (letfn [(f [%1 %2 %3] (if (get-f %3) (conj %1 %2) %1))]
         (reduce-kv f [] n)))

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
  ; Returns TRUE if the given 'n' map contains any item of the given 'keys' vector.
  ;
  ; @param (map) n
  ; @param (* in vector) keys
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
  [n keys]
  (letfn [(f [%] (contains? n %))]
         (boolean (some f keys))))

(defn contains-all-keys?
  [n keys]
  (letfn [(f [%] (contains? n %))]
         (boolean (every? keys f))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn rekey-item
  ; @description
  ; Renames the given 'original-key' key in the given 'n' map to the 'renamed-key' value.
  ;
  ; @param (map) n
  ; @param (*) original-key
  ; @param (*) renamed-key
  ;
  ; @usage
  ; (rekey-item {:a "A"} :a :b)
  ;
  ; @example
  ; (rekey-item {:a "A"} :a :b)
  ; =>
  ; {:b "A"}
  ;
  ; @example
  ; (rekey-item {:a "A"} :c :d)
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  [n original-key renamed-key]
  (if (contains? n original-key)
      (dissoc (assoc n renamed-key (get n original-key)) original-key)
      (-> n)))

(defn rekey-items
  ; @description
  ; Renames the given keys (odd items in the 'key-pairs' vector) in the given 'n' map
  ; to their new names (even items in the 'key-pairs' vector).
  ;
  ; @param (map) n
  ; @param (list of * pairs) key-pairs
  ;
  ; @usage
  ; (rekey-items {:a "A" :b "B"} :a :x :b :y)
  ;
  ; @example
  ; (rekey-items {:a "A" :b "B"} :a :x :b :y)
  ; =>
  ; {:x "A" :y "B"}
  ;
  ; @example
  ; (rekey-items {:a "A" :b "B"} :c :z)
  ; =>
  ; {:a "A" :b "B"}
  ;
  ; @return (map)
  [n & key-pairs]
  (letfn [(f [n original-key renamed-key] (if (contains? n original-key)
                                              (dissoc (assoc n renamed-key (get n original-key)) original-key)
                                              (-> n)))]
         (reduce-pairs f n key-pairs)))
