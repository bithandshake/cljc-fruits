
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
  ; Dissociates the value from the given path in the given 'n' map.
  ;
  ; @param (map) n
  ; @param (vector) path
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
  [n [k & ks :as path]]
  (if ks (if-let [next-n (get n k)]
                 (let [new-n (dissoc-in next-n ks)]
                      (if (seq new-n)
                          (assoc n k new-n)
                          (dissoc n k)))
                 (-> n))
         (dissoc n k)))

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
  ; @param (vector) path
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
  [n path value]
  (if (get-in    n path)
      (dissoc-in n path)
      (assoc-in  n path value)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move
  ; @param (map) n
  ; @param (list of *) ks
  ;
  ; @usage
  ; (move {:a "A" :b "B"} :a :x)
  ;
  ; @example
  ; (move {:a "A" :b "B"} :a :x)
  ; =>
  ; {:x "A" :b "B"}
  ;
  ; @example
  ; (move {:a "A" :b "B"} :a :x :b :y)
  ; =>
  ; {:x "A" :y "B"}
  ;
  ; @example
  ; (move {:a "A" :b "B"} :a :x :x :y)
  ; =>
  ; {:y "A" :b "B"}
  ;
  ; @return (map)
  [n & ks]
  (loop [n n ks (vec ks)]
        (if (-> ks count (< 2))
            (-> n)
            (recur (-> n  (assoc (second ks) (get n (first ks))) (dissoc (first ks)))
                   (-> ks (subvec 2))))))

(defn move-in
  ; @param (map) n
  ; @param (vector) from
  ; @param (vector) to
  ;
  ; @usage
  ; (move-in {:a {:b "B"}} [:a :b] [:x :y])
  ;
  ; @example
  ; (move-in {:a {:b "B"}} [:a :b] [:x :y])
  ; =>
  ; {:x {:y "B"}}
  ;
  ; @return (map)
  [n from to]
  (-> n (dissoc-in from)
        (assoc-in to (get-in n from))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-by
  ; @description
  ; Returns the value from the given 'path' dynamic path in the given 'n' map.
  ;
  ; @param (map) n
  ; @param (vector) path
  ;
  ; @usage
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ;
  ; @example
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ; =>
  ; {:c "C"}
  ;
  ; @example
  ; (get-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)])
  ; =>
  ; {:c "C"}
  ;
  ; @return (*)
  [n path]
  (get-in n (seqable/dynamic-path n path)))

(defn assoc-by
  ; @description
  ; Associates the given 'xyz' values to the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
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
  ; @example
  ; (assoc-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)] :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n path & xyz]
  (apply assoc-in n (seqable/dynamic-path n path) xyz))

(defn dissoc-by
  ; @description
  ; Dissociates the value from the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ;
  ; @usage
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ;
  ; @example
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex])
  ; =>
  ; {:a [{:b "B"}]}
  ;
  ; @example
  ; (dissoc-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec))])
  ; =>
  ; {:a [{:b "B"}]}
  ;
  ; @return (map)
  [n path]
  (dissoc-in n (seqable/dynamic-path n path)))

(defn update-by
  ; @description
  ; Updates the value in the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ;
  ; @usage
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] assoc :x "X")
  ;
  ; @example
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a seqable/last-dex] assoc :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @example
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)] assoc :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n path f & params]
  (apply update-in n (seqable/dynamic-path n path) f params))
