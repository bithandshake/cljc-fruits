
(ns fruits.map.key
    (:refer-clojure :exclude [keys])
    (:require [fruits.mixed.api :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keys
  ; @description
  ; Returns the keys of the given 'n' map in a vector.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (keys {:a {:c "C"} :b "B"})
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-map n)]
       (-> n clojure.core/keys vec)))

(defn keys-by
  ; @description
  ; Returns the keys of the given 'n' map for which the given 'f' function returns TRUE.
  ;
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (keys-by {:a "A" :b :b :c :c} string?)
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n get-f]
  (let [n     (mixed/to-map n)
        get-f (mixed/to-ifn get-f)]
       (letfn [(f0 [%1 %2 %3] (if (get-f %3) (conj %1 %2) %1))]
              (reduce-kv f0 [] n))))

(defn first-key
  ; @note
  ; Clojure maps are unordered data structures.
  ;
  ; @description
  ; Returns the first key of the given 'n' map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (first-key {:a {:c "C"} :b "B"})
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-map n)]
       (-> n clojure.core/keys first)))

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
  ; (contains-key? {:a {:b "B"}} :a)
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-key? {:a {:b "B"}} :b)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n k]
  (let [n (mixed/to-map n)]
       (contains? n k)))

(defn contains-any-key?
  ; @description
  ; Returns TRUE if the given 'n' map contains any value associated to a key from the given 'ks' vector.
  ;
  ; @param (map) n
  ; @param (vector) ks
  ;
  ; @usage
  ; (contains-any-key? {:a {:b "B"} :c "C"} [:a])
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-any-key? {:a {:b "B"} :c "C"} [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @usage
  ; (contains-any-key? {:a {:b "B"}} [:b :c :d])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n ks]
  (let [n  (mixed/to-map n)
        ks (mixed/to-vector ks)]
       (letfn [(f0 [%] (contains? n %))]
              (boolean (some f0 ks)))))

(defn contains-all-key?
  ; @description
  ; Returns TRUE if the given 'n' map contains all values associated to the keys from the given 'ks' vector.
  ;
  ; @param (map) n
  ; @param (vector) ks
  ;
  ; @usage
  ; (contains-all-key? {:a "A" :b "B"} [:a :b])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n ks]
  (let [n  (mixed/to-map n)
        ks (mixed/to-vector ks)]
       (letfn [(f0 [%] (contains? n %))]
              (every? ks f0))))

(defn has-same-keys?
  ; @description
  ; Returns TRUE if any of the given maps has a key that is present in any other provided map.
  ;
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (has-same-keys? {:a "A"} {:b "B"} {:a "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (has-same-keys? {:a "A"} {:b "B"} {:c "C"})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [& xyz]
  (loop [ks [] dex 0]
        (if (seqable/dex-in-bounds? xyz dex)
            (let [n (nth xyz dex)]
                 (if (contains-any-key? n ks)
                     (-> true)
                     (recur (-> n keys (concat ks))
                            (-> dex inc)))))))
