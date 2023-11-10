
(ns map.core
    (:require [clojure.data :as data]
              [loop.api     :refer [reduce-pairs]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn difference
  ; @description
  ; Computes the difference between two maps, returning key-value pairs that are
  ; present in the first map but not in the second map.
  ;
  ; @param (map) a
  ; @param (map) b
  ;
  ; @usage
  ; (difference {:a "a" :b "b"} {:a "a"})
  ;
  ; @example
  ; (difference {:a "a" :b "b"} {:a "a"})
  ; =>
  ; {:b "b"}
  ;
  ; @return (map)
  ; Key-value pairs presented only in 'a' map.
  [a b]
  (-> a (data/diff b) first))

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

(defn dissoc-items
  ; @description
  ; Removes specified keys and their associated values from the given 'n' map.
  ;
  ; @param (map) n
  ; @param (* in vector) keys
  ;
  ; @usage
  ; (dissoc-items {:a "A" :b "B"} [:a])
  ;
  ; @example
  ; (dissoc-items {:a "A" :b "B" :c "C"} [:a :b])
  ; =>
  ; {:c "C"}
  ;
  ; @return (map)
  [n keys]
  (apply dissoc n keys))

(defn inject-in
  ; @description
  ; Adds a key-value pair at a specified path within a nested map or creates
  ; a nested map structure if it doesn't exist.
  ;
  ; @param (map) n
  ; @param (vector) inject-path
  ; @param (*) key
  ; @param (*) value
  ;
  ; @usage
  ; (inject-in {} [:a] :b "B")
  ;
  ; @example
  ; (inject-in {} [:a :b] :c "C")
  ; =>
  ; {:a {:b {:c "C"}}}
  ;
  ; @return (*)
  [n inject-path key value]
  (assoc-in n (conj inject-path key) value))

(defn toggle
  ; @description
  ; Adds a key-value pair to a map if the key is not present, or removes it
  ; if the key is already present.
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
  ; Modifies a map by adding a value at a specified path if it's not present,
  ; or removing it if it already exists.
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

(defn update-some
  ; @description
  ; Updates a map by applying a function to a specified key if the provided
  ; value is not nil or falsy.
  ;
  ; @param (map) n
  ; @param (*) key
  ; @param (function) f
  ; @param (*) value
  ;
  ; @usage
  ; (update-some {:a []} :a conj :b)
  ;
  ; @example
  ; (update-some {:a [:b :c]} :a conj :d)
  ; =>
  ; {:a [:b :c :d]}
  ;
  ; @example
  ; (update-some {:a [:b :c]} :a conj nil)
  ; =>
  ; {:a [:b :c]}
  ;
  ; @return (map)
  [n key f value]
  (if (some?          value)
      (update n key f value)
      (-> n)))

(defn update-in-some
  ; @description
  ; Updates a map at a specified path if the provided value is not nil or falsy.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ; @param (function) update-f
  ; @param (*) value
  ;
  ; @usage
  ; (update-in-some {:a []} [:a] conj :b)
  ;
  ; @example
  ; (update-in-some {:a {:b [:c :d]}} [:a :b] conj :e)
  ; =>
  ; {:a {:b [:c :d :e]}}
  ;
  ; @example
  ; (update-in-some {:a {:b [:c :d]}} [:a :b] conj nil)
  ; =>
  ; {:a {:b [:c :d]}}
  ;
  ; @return (map)
  [n value-path update-f value]
  (if (some?                           value)
      (update-in n value-path update-f value)
      (-> n)))

(defn assoc-some
  ; @description
  ; Associates key-value pairs into a map, excluding any pairs where the value
  ; is nil or falsy.
  ;
  ; @param (map) n
  ; @param (list of * pairs) kv-pairs
  ;
  ; @usage
  ; (assoc-some {} :a "A" :b "B")
  ;
  ; @example
  ; (assoc-some {:a [:b :c]} :d "D")
  ; =>
  ; {:a [:b :c] :d "D"}
  ;
  ; @example
  ; (assoc-some {:a [:b :c]} :d nil :e "E")
  ; =>
  ; {:a [:b :c] :e "E"}
  ;
  ; @return (map)
  [n & kv-pairs]
  ; TEMP#5500
  ; This function uses the new 'reduce-pairs' recursion that is designed for applying
  ; functions on parameter pairs e.g., to make map functions take not just a single
  ; key-value pair but more than one pairs.
  ; Other map functions will use that recursion as well (in later versions).
  (letfn [(f [n k v] (if (-> v some?)
                         (-> n (assoc k v))
                         (-> n)))]
         (reduce-pairs f n kv-pairs)))

(defn assoc-in-some
  ; @description
  ; Associates a value at a specified path within a nested map, but only if
  ; the provided value is not NIL.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ; @param (*) value
  ;
  ; @usage
  ; (assoc-in-some {} [:a :b] "B")
  ;
  ; @example
  ; (assoc-in-some {:a [:b :c]} [:d :e] "E")
  ; =>
  ; {:a [:b :c] :d {:e "E"}}
  ;
  ; @example
  ; (assoc-in-some {:a [:b :c]} [:d :e] nil)
  ; =>
  ; {:a [:b :c]}
  ;
  ; @return (map)
  [n value-path value]
  (if (some?                 value)
      (assoc-in n value-path value)
      (-> n)))

(defn assoc-or
  ; @description
  ; Associates a value with a key in a map, but only if the key does not already
  ; exist or has a nil value.
  ;
  ; @param (map) n
  ; @param (*) key
  ; @param (*) value
  ;
  ; @usage
  ; (assoc-or {:a "A"} :a "X")
  ;
  ; @example
  ; (assoc-or {:a "A"} :a "X")
  ; =>
  ; {:a "A"}
  ;
  ; @example
  ; (assoc-or {:a nil} :a "X")
  ; =>
  ; {:a "X"}
  ;
  ; @return (map)
  [n key value]
  (assoc n key (or (get n key) value)))

(defn assoc-in-or
  ; @description
  ; Associates a value at a specified path within a nested map, but only if the
  ; current value at that path is NIL.
  ;
  ; @param (map) n
  ; @param (vector) value-path
  ; @param (*) value
  ;
  ; @usage
  ; (assoc-in-or {:a {:b nil}} [:a :b] "B")
  ;
  ; @example
  ; (assoc-in-or {:a {:b "B"}} [:a :b] "X")
  ; =>
  ; {:a {:b "B"}}
  ;
  ; @example
  ; (assoc-in-or {:a {:b nil}} [:a :b] "X")
  ; =>
  ; {:a {:b "X"}}
  ;
  ; @return (map)
  [n value-path value]
  (assoc-in n value-path (or (get n value-path) value)))
