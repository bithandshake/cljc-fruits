
(ns fruits.map.collapse
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn collapse
  ; @description
  ; Flattens the given 'n' nested map, converting its paths into dot-separated keys,
  ; optionally keywordizing keys, and providing options to control which paths
  ; are collapsed.
  ;
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:separator (string)(opt)
  ;   Default: "."
  ;  :inner-except-f (function)(opt)
  ;   An optional control function that determines whether specific key-value
  ;   pairs within nested maps should be excluded from the collapsing process.
  ;   It is invoked for each key-value pair within nested maps during the inner iteration.
  ;   If returns FALSE for a particular key-value pair, that pair will not be collapsed.
  ;  :keywordize? (boolean)(opt)
  ;   Default: false
  ;  :outer-except-f (function)(opt)
  ;   An optional control function that operates at the outer level of the map.
  ;   Similar to 'inner-except-f', it determines whether specific key-value pairs
  ;   at the outermost level of the map should be excluded from collapsing.}
  ;
  ; @usage
  ; (collapse {:a {:b "c"}})
  ; =>
  ; {"a.b" "c"}
  ;
  ; @usage
  ; (collapse {:a {:b "c"}} {:keywordize? true})
  ; =>
  ; {:a.b "c"}
  ;
  ; @usage
  ; (collapse {:a {:b {:c "d"} :q [{:r {:s "t"}}]}} {:keywordize? true})
  ; =>
  ; {:a.b.c "d" :q [{:r.s "t"}]}
  ;
  ; @usage
  ; The ':c' key is an exception, and its value will not be collapsed to the outer map.
  ; (defn my-inner-except-f [k v] (not= k :c))
  ; (collapse {:a {:b {:c "d"}}} {:keywordize? true :except-f my-inner-except-f})
  ; =>
  ; {:a.b {:c "d"}}
  ;
  ; @usage
  ; The ':b' key is an exception, and its value's keys will not be collapsed to their outer map.
  ; (defn my-outer-except-f [k v] (not= k :b))
  ; (collapse {:a {:b {:c "d"}}} {:keywordize? true :except-f my-outer-except-f})
  ; =>
  ; {:a.b {:c "d"}}
  ;
  ; @return (*)
  ([n]
   (collapse n {}))

  ([n {:keys [inner-except-f outer-except-f keywordize? separator] :or {separator "."}}]
   (let [n (mixed/to-map n)]
        (letfn [; @description
                ; Converts the given key into string.
                ;
                ; @param (*) k
                ;
                ; @return (string)
                (stringize-key-f [k] (if (keyword? k)
                                         (if-let [namespace (namespace k)]
                                                 (str namespace "/" (name k))
                                                 (name k))
                                         (str k)))

                ; @description
                ; Joins an outer and an inner key into one with the separator string between each key.
                ; The type of the return value depends on the 'keywordize?' option.
                ;
                ; @param (*) a
                ; @param (*) b
                ;
                ; @usage
                ; (join-keys-f :a :b)
                ; =>
                ; :a.b
                ;
                ; @usage
                ; (join-keys-f :a :b)
                ; =>
                ; "a.b"
                ;
                ; @return (keyword or string)
                (join-keys-f [a b] (if keywordize? (keyword (str (stringize-key-f a) separator (stringize-key-f b)))
                                                   (str          (stringize-key-f a) separator (stringize-key-f b))))

                ; @description
                ; ...
                ;
                ; @param (*) ko
                ; Key from outer map.
                ; @param (*) vo
                ; Value from outer map.
                ;
                ; @return (boolean)
                (collapse-o? [ko vo] (and (map? vo)
                                          (or (not  outer-except-f)
                                              (not (outer-except-f ko vo)))))

                ; @description
                ; ...
                ;
                ; @param (*) ki
                ; Key from inner map.
                ; @param (*) vi
                ; Value from inner map.
                ;
                ; @return (boolean)
                (collapse-i? [ki vi] (or (not  inner-except-f)
                                         (not (inner-except-f ki vi))))

                ; @description
                ; ...
                ;
                ; @param (map) ro
                ; Result of the outer iteration.
                ; @param (*) ko
                ; Actual key of the map in the outer iteration.
                ; @param (*) vo
                ; Actual value of the map in the outer iteration.
                ;
                ; @return (map)
                (map-f [ro ko vo] (if (collapse-o? ko vo)
                                      (letfn [; @description
                                              ; ...
                                              ;
                                              ; @param (map) ri
                                              ; Result of the inner iteration.
                                              ; @param (*) ki
                                              ; Actual key of the map in the inner iteration.
                                              ; @param (*) vi
                                              ; Actual value of the map in the inner iteration.
                                              ;
                                              ; @return (map)
                                              (collapse-f [ri ki vi] (if (collapse-i? ki vi)
                                                                         (assoc    ri (join-keys-f ko ki) vi)
                                                                         (assoc-in ri [ko ki] vi)))]

                                             ; Inner iteration of the collapsing method.
                                             (reduce-kv collapse-f ro (walk-f vo)))

                                      ; ...
                                      (assoc ro ko (walk-f vo))))

                ; @description
                ; ...
                ;
                ; @param (vector) r
                ; @param (*) x
                ;
                ; @return (vector)
                (vector-f [r x] (conj r (walk-f x)))

                ; @description
                ; ...
                ;
                ; @param (*) n
                ;
                ; @return (*)
                (walk-f [n] (cond (vector? n) (reduce    vector-f [] n)
                                  (map?    n) (reduce-kv map-f    {} n)
                                  :return  n))]

               ; ...
               (walk-f n)))))
