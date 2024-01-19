
(ns fruits.pretty.print
    (:require [fruits.loop.api     :refer [reduce-indexed reduce-kv-indexed]]
              [fruits.pretty.utils :as utils]
              [fruits.vector.api   :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn mixed->string
  ; @note
  ; The PRE HTML tag makes the output more readable.
  ;
  ; @description
  ; Converts the given data structure into pretty printed string.
  ;
  ; @param (*) n
  ; @param (map) options
  ; {:abc? (boolean)
  ;   Alphabetical ordered map items
  ;   Default: false}
  ;
  ; @usage
  ; (mixed->string {:a {:b "a/b"}})
  ;
  ; @usage
  ; [:pre (mixed->string {:a {:b "a/b"}})]
  ;
  ; @return (string)
  ([n]
   (mixed->string n {}))

  ([n {:keys [abc?]}]
   (letfn [; @param (map) n
           ; @param (map) options
           ; {:depth (integer)
           ;  :wrap-items? (boolean)(opt)}
           ;
           ; @return (string)
           (map->string [n {:keys [depth wrap-items?]}]
                        (letfn [(f0 [result dex k v]
                                    (utils/append-map-kv result (mixed->string k {:depth 0})
                                                                (mixed->string v {:depth       (-> depth inc)
                                                                                  :wrap-items? (-> v utils/mixed->wrap-items?)})
                                                                {:depth       (-> depth)
                                                                 :first-item? (-> dex zero?)
                                                                 :wrap-items? (-> wrap-items?)}))]
                               (utils/map-wrap (reduce-kv-indexed f0 nil n))))

           ; @param (map) n
           ; @param (map) options
           ; {:depth (integer)
           ;  :wrap-items? (boolean)(opt)}
           ;
           ; @return (string)
           (map->ordered-string [n {:keys [depth wrap-items?]}]
                                (let [ordered-keys (vector/abc-items (keys n))]
                                     (letfn [(f0 [result dex k]
                                                 (let [v (get n k)]
                                                      (utils/append-map-kv result (mixed->string k {:depth 0})
                                                                                  (mixed->string v {:depth       (-> depth inc)
                                                                                                    :wrap-items? (-> v utils/mixed->wrap-items?)})
                                                                                  {:depth       (-> depth)
                                                                                   :first-item? (-> dex zero?)
                                                                                   :wrap-items? (-> wrap-items?)})))]
                                            (utils/map-wrap (reduce-indexed f0 nil ordered-keys)))))

           ; @param (vector) n
           ; @param (map) options
           ; {:depth (integer)
           ;  :wrap-items? (boolean)(opt)}
           ;
           ; @return (string)
           (vector->string [n {:keys [depth wrap-items?]}]
                           (letfn [(f0 [result dex x]
                                       (utils/append-vector-v result (mixed->string x {:depth       (-> depth inc)
                                                                                       :wrap-items? (-> x utils/mixed->wrap-items?)})
                                                                     {:depth       (-> depth)
                                                                      :first-item? (-> dex zero?)
                                                                      :wrap-items? (-> wrap-items?)}))]
                                  (utils/vector-wrap (reduce-indexed f0 nil n))))

           ; @param (*) n
           ; @param (map) options
           ; {:depth (integer)
           ;  :wrap-items? (boolean)(opt)}
           ;
           ; @return (string)
           (mixed->string [n {:keys [depth wrap-items?] :as options}]
                          (cond (and (map? n) abc?) (map->ordered-string   n options)
                                (map?     n)        (map->string           n options)
                                (vector?  n)        (vector->string        n options)
                                (fn?      n)        (utils/fn->string      n)
                                (float?   n)        (utils/float->string   n)
                                (integer? n)        (utils/integer->string n)
                                (nil?     n)        (utils/nil->string     n)
                                (string?  n)        (utils/string->string  n)
                                (var?     n)        (utils/var->string     n)
                                :return             (str                   n)))]

         ; mixed->string
         (utils/remove-unnecessary-breaks (mixed->string n {:depth 0 :wrap-items? (utils/mixed->wrap-items? n)})))))
