
(ns bson.core
    (:require [keyword.api :as keyword]
              [map.api     :as map]
              [string.api  :as string]
              [vector.api  :as vector]))

;; -- Undot key ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn undot-key
  ; @description
  ; Replaces dot characters with hyphens in the given key.
  ;
  ; Dot characters are not allowed to presence in BSON keys:
  ; https://www.mongodb.com/docs/manual/core/document/#dot-notation
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (undot-key :my.keyword)
  ;
  ; @usage
  ; (undot-key "my.string")
  ;
  ; @example
  ; (undot-key :my.keyword)
  ; =>
  ; :my-keyword
  ;
  ; @example
  ; (undot-key "my.string")
  ; =>
  ; "my-string"
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (string/replace-part n "." "-")
        (keyword? n) (-> n keyword/to-string undot-key keyword)
        :return   n))

;; -- Undot keys --------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn undot-keys
  ; @description
  ; Replaces dot characters with hyphens in keys found in the given data.
  ;
  ; Dot characters are not allowed to presence in BSON keys:
  ; https://www.mongodb.com/docs/manual/core/document/#dot-notation
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (undot-keys {:my.keyword :my-value})
  ;
  ; @usage
  ; (undot-keys {"my.string" "My value"})
  ;
  ; @example
  ; (undot-keys {:my.keyword :my-value})
  ; =>
  ; {:my-keyword :my-value}
  ;
  ; @example
  ; (undot-keys {"my.string" "My value"})
  ; =>
  ; {"my-string" "My value"}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n undot-keys)
        (vector? n) (vector/->items n undot-keys)
        :return     (undot-key      n)))
