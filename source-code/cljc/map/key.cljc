
(ns map.key
    (:require [noop.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-keys
  ; @param (map) n
  ;
  ; @usage
  ; (get-keys {:a "A" :b "B"})
  ;
  ; @example
  ; (get-keys {:a {:c "C"} :b "B"})
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n]
  (-> n keys vec))

(defn get-first-key
  ; @param (map) n
  ;
  ; @usage
  ; (get-first-key {:a "A" :b "B"})
  ;
  ; @example
  ; (get-first-key {:a {:c "C"} :b "B"})
  ; =>
  ; :a
  ;
  ; @return (*)
  [n]
  (-> n keys first))

(defn contains-key?
  ; @param (map) n
  ; @param (*) x
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
  [n x]
  (contains? n x))

(defn contains-of-keys?
  ; @param (map) n
  ; @param (* in vector) keys
  ;
  ; @usage
  ; (contains-of-keys? {:a "A" :b "B"} [:a])
  ;
  ; @example
  ; (contains-of-keys? {:a {:b "B"} :c "C"} [:a])
  ; =>
  ; true
  ;
  ; @example
  ; (contains-of-keys? {:a {:b "B"} :c "C"} [:a :b :c :d])
  ; =>
  ; true
  ;
  ; @example
  ; (contains-of-keys? {:a {:b "B"}} [:b :c :d])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n keys]
  (letfn [(f [%] (contains? n %))]
         (boolean (some f keys))))

(defn rekey-item
  ; @param (map) n
  ; @param (*) from
  ; @param (*) to
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
  [n from to]
  (if (contains? n from)
      (dissoc (assoc n to (get n from)) from)
      (return n)))

(defn rekey-items
  ; @param (map) n
  ; @param (list of *) keys
  ;
  ; @usage
  ; (rekey-items {:a "A" :c "C"} :a :b :c :d)
  ;
  ; @example
  ; (rekey-items {:a "A" :c "C"} :a :b :c :d)
  ; =>
  ; {:b "A" :d "C"}
  ;
  ; @return (map)
  [n & keys]
  (letfn [(f [o dex x] (if (even? dex)
                           (dissoc (assoc o (nth keys (inc dex)) (get o x)) x)
                           (return o)))]
         (reduce-kv f n (vec keys))))

(defn get-keys-by
  ; @param (map) n
  ; @param (function) f
  ;
  ; @usage
  ; (get-keys-by {:a "A" :b :b} string?)
  ;
  ; @example
  ; (get-keys-by {:a "A" :b :b :c :c} string?)
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n get-f]
  (letfn [(f [%1 %2 %3] (if (get-f %3) (conj %1 %2) %1))]
         (reduce-kv f [] n)))
