
(ns fruits.bson.update
    (:require [fruits.keyword.api :as keyword]
              [fruits.map.api     :as map]
              [fruits.string.api  :as string]
              [fruits.vector.api  :as vector]))

;; -- Undot key ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn undot-key
  ; @note
  ; Dot characters are not allowed to presence in BSON keys:
  ; https://www.mongodb.com/docs/manual/core/document/#dot-notation
  ;
  ; @description
  ; Replaces dot characters with hyphens in the given key.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (undot-key :my.keyword)
  ; =>
  ; :my-keyword
  ;
  ; @usage
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
  ; @note
  ; Dot characters are not allowed to presence in BSON keys:
  ; https://www.mongodb.com/docs/manual/core/document/#dot-notation
  ;
  ; @description
  ; Recursively replaces dot characters with hyphens in keys found in the given data.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (undot-keys {:my.keyword :my-value})
  ; =>
  ; {:my-keyword :my-value}
  ;
  ; @usage
  ; (undot-keys {"my.string" "My value"})
  ; =>
  ; {"my-string" "My value"}
  ;
  ; @usage
  ; (undot-keys [{"my.string" "My value"}
  ;              {:my.keyword :my-value}])
  ; =>
  ; [{"my-string" "My value"}
  ;  {:my-keyword :my-value}]
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n undot-keys)
        (vector? n) (vector/->items n undot-keys)
        :return     (undot-key      n)))
