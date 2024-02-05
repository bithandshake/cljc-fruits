
(ns fruits.map.default
    (:require [fruits.map.merge :as merge]
              [fruits.map.key :as key]
              [fruits.map.assoc :as assoc]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn use-default-value
  ; @description
  ; Replaces NIL or missing corresponding value of the given 'k' key in the given 'n' map
  ; with the corresponding value derived from the rest of the given maps.
  ;
  ; @param (map) n
  ; @param (*) k
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (use-default-value {:a "A1"}
  ;                    :a
  ;                    {:a "A2"}
  ;                    {:a "A3"})
  ; =>
  ; {:a "A1"}
  ;
  ; @usage
  ; (use-default-value {}
  ;                    :a
  ;                    {:a "A2"}
  ;                    {:a "A3"})
  ; =>
  ; {:a "A3"}
  ;
  ; @usage
  ; (use-default-value {...} ; <- The first given map is the PRIMARY source of the value.
  ;                    {...} ; <- The rest of the given maps is the SECONDARY source of the value.
  ;                    {...} ; <- The later argument is a map; the higher its priority among the rest of the given maps.)
  ;
  ; @return (map)
  [n k & xyz]

  ; DEPRECATED (previous version)
  ; (let [xyz (apply merge/deep-merge xyz)]
  ;      (if (-> n (get k) nil?)
  ;          (-> n (assoc/assoc-some k (get xyz k)))
  ;          (-> n))))
  ; DEPRECATED (previous version)

  (letfn [(f0 [%] (get % k))]
         (if (-> n (get k) nil?)
             (-> n (assoc/assoc-some k (some f0 xyz)))
             (-> n))))

(defn use-default-values
  ; @description
  ; - Merges the given 'n' map onto the rest of the given maps.
  ; - Nested values are deep merged.
  ; - I.e. (deep-merge {2nd} {3rd} {4th} ... {1st})
  ;
  ; @param (map) n
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (use-default-values {:a "A1"}
  ;                     {:a "A2"
  ;                      :b "B2"}
  ;                     {:b "B3"})
  ; =>
  ; {:a "A1"
  ;  :b "B3"}
  ;
  ; @usage
  ; (use-default-values {:a {:b "B1"}}
  ;                     {:a {:c "C2"}})
  ; =>
  ; {:a {:b "B1"
  ;      :c "C2"}}
  ;
  ; @usage
  ; (use-default-values {...} ; <- The first given map is the PRIMARY source of values.
  ;                     {...} ; <- The rest of the given maps is the SECONDARY source of values.
  ;                     {...} ; <- The later argument is a map; the higher its priority among the rest of the given maps.)
  ;
  ; @return (map)
  [n & xyz]
  (let [xyz (apply merge/deep-merge xyz)]
       (merge/deep-merge-some xyz n)))

(defn use-default-value-group
  ; @description
  ; - Merges the given 'n' map onto the rest of the given maps, if at least one key of the rest maps
  ;   is also present in the given 'n' map.
  ; - Nested values are deep merged.
  ; - I.e. (deep-merge {2nd} {3rd} {4th} ... {1st})
  ;
  ; @param (map) n
  ; @param (list of maps) xyz
  ;
  ; @usage
  ; (use-default-value-group {:a "A1"}
  ;                          {:a "A2"
  ;                           :b "B2"})
  ; =>
  ; {:a "A1"
  ;  :b "B2"}
  ;
  ; @usage
  ; (use-default-value-group {:a "A1"}
  ;                          {:b "B2"})
  ; =>
  ; {:a "A1"}
  ;
  ; @usage
  ; (use-default-value-group {...} ; <- The first given map is the PRIMARY source of values.
  ;                          {...} ; <- The rest of the given maps is the SECONDARY source of values.
  ;                          {...} ; <- The later argument is a map; the higher its priority among the rest of the given maps.)
  ;
  ; @return (map)
  [n & xyz]
  (let [xyz (apply merge/deep-merge xyz)]
       (if (key/has-same-keys?    xyz n)
           (merge/deep-merge-some xyz n)
           (-> n))))
