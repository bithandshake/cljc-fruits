
(ns map.core
    (:require [clojure.data :as data]
              [loop.api     :refer [reduce-pairs]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn swap
  ; @description
  ; Swaps the keys and values in a map, generating a new map with the values
  ; as keys and the keys as values.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (swap {:a "A" :b "B"})
  ;
  ; @example
  ; (swap {:a "A" :b "B"})
  ; =>
  ; {"A" :a "B" :b}
  ;
  ; @return (map)
  [n]
  (zipmap (vals n)
          (keys n)))

(defn dissoc-in
  ; @description
  ; - Original: re-frame.utils/dissoc-in
  ; - Dissociates an entry from a nested associative structure returning a new
  ;   nested structure. keys is a sequence of keys. Any empty maps that result
  ;   will not be present in the new structure.
  ;   The key thing is that 'm' remains identical? to istelf if the path was
  ;   never present.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ;
  ; @usage
  ; (dissoc-in {:a {:b "B"}} [:a :b])
  ;
  ; @example
  ; (dissoc-in {:a {:b "B"}} [:a :b])
  ; =>
  ; {}
  ;
  ; @return (map)
  [n [key & keys :as value-path]]
  (if keys (if-let [next-n (get n key)]
                   (let [new-n (dissoc-in next-n keys)]
                        (if (seq          new-n)
                            (assoc  n key new-n)
                            (dissoc n key)))
                   (-> n))
           (dissoc n key)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn toggle
  ; @description
  ; Associates a key-value pair to the given 'n' map if the key is not present,
  ; or removes it if the key is already present.
  ;
  ; @param (map) n
  ; @param (*) key
  ; @param (*) value
  ;
  ; @usage
  ; (toggle {:a "A"} :a "A")
  ;
  ; @example
  ; (toggle {} :a "A")
  ; =>
  ; {:a "A"}
  ;
  ; @example
  ; (toggle {:a "A"} :a "A")
  ; =>
  ; {}
  ;
  ; @example
  ; (toggle {:a "B"} :a "A")
  ; =>
  ; {}
  ;
  ; @return (*)
  [n key value]
  (if-let [_ (get n key)]
          (dissoc n key)
          (assoc  n key value)))

(defn toggle-in
  ; @description
  ; Associates a nested key-value pair to the given 'n' map if the key is not present,
  ; or removes it if the key is already present.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ; @param (*) value
  ;
  ; @usage
  ; (toggle-in {:a {:b "B"}} [:a :b] "B")
  ;
  ; @example
  ; (toggle-in {} [:a :b] "B")
  ; =>
  ; {:a {:b "B"}}
  ;
  ; @example
  ; (toggle-in {:a "A"} [:a :b] "B")
  ; =>
  ; {:a {:b "B"}}
  ;
  ; @example
  ; (toggle-in {:a {:b "B"}} [:a :B] "B")
  ; =>
  ; {}
  ;
  ; @return (*)
  [n value-path value]
  (if-let [_ (get-in n value-path)]
          (dissoc-in n value-path)
          (assoc-in  n value-path value)))
