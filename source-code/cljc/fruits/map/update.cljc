
(ns fruits.map.update
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-by
  ; @description
  ; Updates a specific nested value in the given 'n' map at the given 'path' dynamic path.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (defn last-dex [%] (-> % count dec))
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a last-dex] assoc :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @usage
  ; (update-by {:a [{:b "B"} {:c "C"}]} [:a #(-> % count dec)] assoc :x "X")
  ; =>
  ; {:a [{:b "B"} {:c "C" :x "X"}]}
  ;
  ; @return (map)
  [n path f & params]
  (let [n    (mixed/to-map n)
        f    (mixed/to-ifn f)
        path (seqable/dynamic-path n path)]
       (apply update-in n path f params)))

(defn update-some
  ; @description
  ; Updates a specific value in the given 'n' map, in case it is not NIL.
  ;
  ; @param (map) n
  ; @param (*) k
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-some {:a "A"} :a str "0")
  ; =>
  ; {:a "A0"}
  ;
  ; @usage
  ; (update-some {} :a str "0")
  ; =>
  ; {}
  ;
  ; @return (map)
  [n k f & params]
  (if (-> n (get k) nil?)
      (-> n)
      (apply update n k f params)))

(defn update-in-some
  ; @description
  ; Updates a specific nested value in the given 'n' map, in case it is not NIL.
  ;
  ; @param (map) n
  ; @param (vector) path
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-in-some {:a "A"} [:a] str "0")
  ; =>
  ; {:a "A0"}
  ;
  ; @usage
  ; (update-in-some {} [:a] str "0")
  ; =>
  ; {}
  ;
  ; @return (map)
  [n path f & params]
  (if (-> n (get-in path) nil?)
      (-> n)
      (apply update-in n path f params)))

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
  ; =>
  ; {"a" "A" "b" "B" "c" "C"}
  ;
  ; @usage
  ; (update-all-key {0 "A" 1 "B" 2 "C"} + 10)
  ; =>
  ; {10 "A" 11 "B" 12 "C"}
  ;
  ; @return (map)
  [n f & params]
  (let [n (mixed/to-map n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [result k v]
                   (assoc result (apply f k params) v))]
              (reduce-kv f0 {} n))))

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
  ; =>
  ; {:a :A :b :B :c :C}
  ;
  ; @usage
  ; (update-all-value {:a 0 :b 1 :c 2} + 10)
  ; =>
  ; {:a 10 :b 11 :c 12}
  ;
  ; @return (map)
  [n f & params]
  (let [n (mixed/to-map n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [result k v]
                   (assoc result k (apply f v params)))]
              (reduce-kv f0 {} n))))

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
  ; =>
  ; {10 "A" 1 "B" 12 "C"}
  ;
  ; @return (map)
  [n test-f f & params]
  (let [n      (mixed/to-map n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (letfn [(f0 [result k v]
                   (if (test-f k)
                       (assoc result (apply f k params) v)
                       (assoc result k v)))]
              (reduce-kv f0 {} n))))

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
  ; =>
  ; {:a 10 :b 1 :c 12}
  ;
  ; @return (map)
  [n test-f f & params]
  (let [n      (mixed/to-map n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (letfn [(f0 [result k v]
                   (if (test-f v)
                       (assoc result k (apply f v params))
                       (assoc result k v)))]
              (reduce-kv f0 {} n))))
