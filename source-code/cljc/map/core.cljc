
(ns map.core
    (:require [loop.api    :refer [reduce-pairs]]
              [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn swap
  ; @description
  ; Swaps the keys and values in a map, generating a new map with the values as keys and the keys as values.
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dissoc-in
  ; @origin
  ; re-frame.utils/dissoc-in
  ;
  ; @description
  ; Dissociates a value at the given 'value-path' from the given 'n' map.
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

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-by
  ; @description
  ; Gets the value from the given 'n' map at the given 'value-path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ;
  ; @usage
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ;
  ; @example
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ; =>
  ; {:c "C"}
  ;
  ; @return (*)
  [n value-path]
  (get-in n (seqable/dynamic-path n value-path)))

(defn assoc-by
  ; @description
  ; Associates the given 'xyz' values to the given 'n' map at the given 'value-path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] :x "X")
  ;
  ; @example
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n value-path & xyz]
  (apply assoc-in n (seqable/dynamic-path n value-path) xyz))

(defn update-by
  ; @description
  ; Updates the value in the given 'n' map at the given 'value-path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ;
  ; @usage
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] assoc :x "X")
  ;
  ; @example
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] assoc :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n value-path f & params]
  (apply update-in n (seqable/dynamic-path n value-path) f params))

(defn dissoc-by
  ; @description
  ; Dissociates the value from the given 'n' map at the given 'value-path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ;
  ; @usage
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ;
  ; @example
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ; =>
  ; {:a [{:b "B"}]}
  ;
  ; @return (map)
  [n value-path]
  (apply dissoc-in n (seqable/dynamic-path n value-path)))
