
(ns css.parse
    (:require [keyword.api :as keyword]
              [map.api     :as map]
              [string.api  :as string]
              [vector.api  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse
  ; @description
  ; Converts the given inline style map into a CSS string.
  ;
  ; @param (map) n
  ;
  ; @example
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
  ; Converts the given CSS string into an inline style map.
  ;
  ; @param (string) n
  ;
  ; @example
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
