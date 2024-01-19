
(ns fruits.css.parse
    (:require [fruits.keyword.api :as keyword]
              [fruits.map.api     :as map]
              [fruits.string.api  :as string]
              [fruits.vector.api  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse
  ; @description
  ; Converts the given inline style map into CSS string.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (unparse {:opacity 1 :width "100%"})
  ; =>
  ; "opacity: 1; width: 100%;"
  ;
  ; @return (string)
  [n]
  (-> n (map/to-vector   (fn [k v] [k v]))
        (vector/->>items (fn [x] (keyword/to-string x)))
        (vector/->items  (fn [x] (string/join x ": ")))
        (vector/suffix-items "; ")
        (string/join)
        (string/trim)))

(defn parse
  ; @description
  ; Converts the given CSS string into inline style map.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (parse "opacity: 1; width: 100%;")
  ; =>
  ; {:opacity 1 :width "100%"}
  ;
  ; @return (map)
  [n]
  (-> n (string/split    #";")
        (vector/->items  (fn [x] (string/split x #":")))
        (vector/->>items (fn [x] (string/trim  x)))
        (vector/to-map   (fn [_ [k v]] [(keyword k) v]))))
