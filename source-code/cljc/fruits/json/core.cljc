
(ns fruits.json.core
    (:require [fruits.json.config :as config]
              [fruits.keyword.api :as keyword]
              [fruits.map.api     :as map]
              [fruits.mixed.api   :as mixed]
              [fruits.string.api  :as string]
              [fruits.syntax.api  :as syntax]
              [fruits.vector.api  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn json->clj
  ; @param (json) n
  ;
  ; @example (json->clj ...)
  ; {...}
  ;
  ; @return (map)
  [n]
  #?(:cljs (js->clj n :keywordize-keys true)))

;; -- Keywordize / unkeywordize key -------------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordize-key
  ; @param (*) n
  ;
  ; @usage
  ; (unkeywordize-key :my-namespace/key)
  ;
  ; @example
  ; (unkeywordize-key :my-namespace/key)
  ; =>
  ; "my-namespace/key"
  ;
  ; @return (*)
  [n]
  (keyword/to-string n))

(defn keywordize-key
  ; @param (*) n
  ;
  ; @usage
  ; (keywordize-key "my-namespace/key")
  ;
  ; @example
  ; (keywordize-key "my-namespace/key")
  ; =>
  ; :my-namespace/key
  ;
  ; @return (*)
  [n]
  (keyword n))

;; -- Underscore / hyphenize key ----------------------------------------------
;; ----------------------------------------------------------------------------

(defn underscore-key
  ; @param (*) n
  ;
  ; @usage
  ; (underscore-key :my-namespace/key)
  ;
  ; @usage
  ; (underscore-key "my-namespace/key")
  ;
  ; @example
  ; (underscore-key :my-namespace/key)
  ; =>
  ; :my_namespace/key
  ;
  ; @example
  ; (underscore-key "my-namespace/key")
  ; =>
  ; "my_namespace/key"
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (-> n (string/replace-part "-" "_"))
        (keyword? n) (-> n name underscore-key keyword)
        :return   n))

(defn hyphenize-key
  ; @param (*) n
  ;
  ; @usage
  ; (hyphenize-key :my_namespace/key)
  ;
  ; @usage
  ; (hyphenize-key "my_namespace/key")
  ;
  ; @example
  ; (hyphenize-key :my_namespace/key)
  ; =>
  ; :my-namespace/key
  ;
  ; @example
  ; (hyphenize-key "my_namespace/key")
  ; =>
  ; "my-namespace/key"
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (-> n (string/replace-part "_" "-"))
        (keyword? n) (-> n name hyphenize-key keyword)
        :return   n))

;; -- Snake-case / CamelCase key ----------------------------------------------
;; ----------------------------------------------------------------------------

(defn CamelCase-key
  ; @param (*) n
  ;
  ; @usage
  ; (CamelCase-key :my-key)
  ;
  ; @usage
  ; (CamelCase-key "my-key")
  ;
  ; @example
  ; (CamelCase-key :my-key)
  ; =>
  ; :myKey
  ;
  ; @example
  ; (CamelCase-key "my-key")
  ; =>
  ; "myKey"
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (-> n syntax/ToCamelCase)
        (keyword? n) (-> n name CamelCase-key keyword)
        :return   n))

(defn snake-case-key
  ; @param (*) n
  ;
  ; @usage
  ; (snake-case-key :myKey)
  ;
  ; @usage
  ; (snake-case-key "myKey")
  ;
  ; @example
  ; (snake-case-key :myKey)
  ; =>
  ; :my-key
  ;
  ; @example
  ; (snake-case-key "myKey")
  ; =>
  ; "my-key"
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (-> n syntax/to-snake-case)
        (keyword? n) (-> n name snake-case-key keyword)
        :return   n))

;; -- Keywordize / unkeywordize value -----------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordized-value?
  ; @param (*) n
  ;
  ; @usage
  ; (unkeywordized-value? "*:apple")
  ;
  ; @example
  ; (unkeywordized-value? "*:apple")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  ; @NOTE (source-code/cljc/fruits/json/config.cljc#5914)
  (and (-> n string?)
       (-> n count (> 2))
       (= config/KEYWORD-PREFIX (str (nth n 0)))
       (= ":"                   (str (nth n 1)))))

(defn keywordize-value
  ; @param (*) n
  ;
  ; @usage
  ; (keywordize-value "*:my-value")
  ;
  ; @example
  ; (keywordize-value "*:my-value")
  ; =>
  ; :my-value
  ;
  ; @return (*)
  [n]
  ; @NOTE (source-code/cljc/fruits/json/config.cljc#5914)
  (if (-> n unkeywordized-value?)
      (-> n (subs 2) keyword)
      (-> n)))

(defn unkeywordize-value
  ; @param (*) n
  ;
  ; @usage
  ; (unkeywordize-value :my-value)
  ;
  ; @example
  ; (unkeywordize-value :my-value)
  ; =>
  ; "*:my-value"
  ;
  ; @return (*)
  [n]
  ; @NOTE (source-code/cljc/fruits/json/config.cljc#5914)
  (if (-> n keyword?)
      (-> config/KEYWORD-PREFIX (str n))
      (-> n)))

;; -- Trim value --------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-value
  ; @param (*) n
  ;
  ; @usage
  ; (trim-value " My value ")
  ;
  ; @example
  ; (trim-value " My value ")
  ; =>
  ; "My value"
  ;
  ; @return (*)
  [n]
  (if (-> n string?)
      (-> n string/trim)
      (-> n)))

;; -- Parse number value ------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-number-value
  ; @param (*) n
  ;
  ; @usage
  ; (parse-number-value "89.420")
  ;
  ; @example
  ; (parse-number-value "89.420")
  ; =>
  ; 89.420
  ;
  ; @return (*)
  [n]
  (mixed/parse-number n {:return? true}))

;; -- Keywordize / unkeywordize keys ------------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordize-keys
  ; @param (*) n
  ;
  ; @usage
  ; (unkeywordize-keys {:my-namespace/key :my-value})
  ;
  ; @example
  ; (unkeywordize-keys {:my-namespace/key :my-value})
  ; =>
  ; {"my-namespace/key" :my-value}
  ;
  ; @return (*)
  [n]
  ; @NOTE
  ; This function uses the 'unkeywordize-key' function that changes only keyword
  ; type values. Therefore, no need to apply any other type-checking stage in this function.
  (cond (map?    n) (map/->>keys      n unkeywordize-keys)
        (vector? n) (vector/->items   n unkeywordize-keys)
        :return     (unkeywordize-key n)))

(defn keywordize-keys
  ; @param (*) n
  ;
  ; @usage
  ; (keywordize-keys {"my-namespace/key" :my-value})
  ;
  ; @example
  ; (keywordize-keys {"my-namespace/key" :my-value})
  ; =>
  ; {:my-namespace/key :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n keywordize-keys)
        (vector? n) (vector/->items n keywordize-keys)
        :return     (keywordize-key n)))

;; -- Underscore / hyphenize keys ---------------------------------------------
;; ----------------------------------------------------------------------------

(defn underscore-keys
  ; @param (*) n
  ;
  ; @usage
  ; (underscore-keys {:my-namespace/key :my-value})
  ;
  ; @usage
  ; (underscore-keys {"my-namespace/key" :my-value})
  ;
  ; @example
  ; (underscore-keys {:my-namespace/key :my-value})
  ; =>
  ; {:my_namespace/key :my-value}
  ;
  ; @example
  ; (underscore-keys {"my-namespace/key" :my-value})
  ; =>
  ; {"my_namespace/key" :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n underscore-keys)
        (vector? n) (vector/->items n underscore-keys)
        :return     (underscore-key n)))

(defn hyphenize-keys
  ; @param (*) n
  ;
  ; @usage
  ; (hyphenize-keys {:my_namespace/key :my-value})
  ;
  ; @usage
  ; (hyphenize-keys {"my_namespace/key" :my-value})
  ;
  ; @example
  ; (hyphenize-keys {:my_namespace/key :my-value})
  ; =>
  ; {:my-namespace/key :my-value}
  ;
  ; @example
  ; (hyphenize-keys {"my_namespace/key" :my-value})
  ; =>
  ; {"my-namespace/key" :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n hyphenize-keys)
        (vector? n) (vector/->items n hyphenize-keys)
        :return     (hyphenize-key  n)))

;; -- Snake-case / CamelCase keys ---------------------------------------------
;; ----------------------------------------------------------------------------

(defn CamelCase-keys
  ; @param (*) n
  ;
  ; @usage
  ; (Case-keys {:my-key :my-value})
  ;
  ; @usage
  ; (Case-keys {"my-key" :my-value})
  ;
  ; @example
  ; (Case-keys {:my-key :my-value})
  ; =>
  ; {:myKey :my-value}
  ;
  ; @example
  ; (CamelCase-keys {"my-key" :my-value})
  ; =>
  ; {"myKey" :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n CamelCase-keys)
        (vector? n) (vector/->items n CamelCase-keys)
        :return     (CamelCase-key  n)))

(defn snake-case-keys
  ; @param (*) n
  ;
  ; @usage
  ; (snake-case-keys {:myKey :my-value})
  ;
  ; @usage
  ; (snake-case-keys {"myKey" :my-value})
  ;
  ; @example
  ; (snake-case-keys {:myKey :my-value})
  ; =>
  ; {:my-key :my-value}
  ;
  ; @example
  ; (snake-case-keys {"myKey" :my-value})
  ; =>
  ; {"my-key" :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys     n snake-case-keys)
        (vector? n) (vector/->items  n snake-case-keys)
        :return     (snake-case-key  n)))

;; -- Keywordize / unkeywordize values ----------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordize-values
  ; @param (*) n
  ;
  ; @usage
  ; (unkeywordize-values {:a :b :c [:d "e"] :f {:g "h" :i :j}})
  ;
  ; @example
  ; (unkeywordize-values {:a :b :c [:d "e"] :f {:g "h" :i :j}})
  ; =>
  ; {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}}
  ;
  ; @return (*)
  [n]
  ; @NOTE (source-code/cljc/fruits/json/config.cljc#5914)
  ;
  ; @NOTE
  ; This function uses the 'unkeywordize-value' function that changes only keyword
  ; type values. Therefore, no need to apply any other type-checking stage in this function.
  (cond (map?    n) (map/->>values      n unkeywordize-values)
        (vector? n) (vector/->items     n unkeywordize-values)
        :return     (unkeywordize-value n)))

(defn keywordize-values
  ; @param (*) n
  ;
  ; @usage
  ; (keywordize-values {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}})
  ;
  ; @example
  ; (keywordize-values {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}})
  ; =>
  ; {:a :b :c [:d "e"] :f {:g "h" :i :j}}
  ;
  ; @return (*)
  [n]
  ; @NOTE (source-code/cljc/fruits/json/config.cljc#5914)
  ;
  ; @NOTE
  ; This function uses the 'keywordize-value' function that changes only string
  ; type values. Therefore, no need to apply any other type-checking stage in this function.
  (cond (map?    n) (map/->>values    n keywordize-values)
        (vector? n) (vector/->items   n keywordize-values)
        :return     (keywordize-value n)))

;; -- Trim values -------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-values
  ; @param (*) n
  ;
  ; @usage
  ; (trim-values {:a "b " :c [" d " "e"] :f {:g " h"}})
  ;
  ; @example
  ; (trim-values {:a "b " :c [" d " "e"] :f {:g " h"}})
  ; =>
  ; {:a "b" :c ["d" "e"] :f {:g "h"}}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>values  n trim-values)
        (vector? n) (vector/->items n trim-values)
        :return     (trim-value     n)))

;; -- Parse number values -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-number-values
  ; @param (*) n
  ;
  ; @usage
  ; (parse-number-values {:a "0" :c ["1"] :f {:g "2"}})
  ;
  ; @example
  ; (parse-number-values {:a "0" :c ["1"] :f {:g "2"}})
  ; =>
  ; {:a 0 :c [1] :f {:g 2}}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>values      n parse-number-values)
        (vector? n) (vector/->items     n parse-number-values)
        :return     (parse-number-value n)))

;; -- Remove blank values -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-blank-values
  ; @param (*) n
  ;
  ; @usage
  ; (remove-blank-values {:a "" :c [] :f {:g nil}})
  ;
  ; @example
  ; (remove-blank-values {:a "" :c [] :f {:g nil}})
  ; =>
  ; {}
  ;
  ; @return (*)
  [n]
  ; @NOTE
  ; This function calls itself recursively after it removes the blank values
  ; from the given data, until it cannot find more blank values within it.
  ; E.g., If the given 'n' value has any item that is a vector that contains
  ;       an empty map, after the first call of this function, only the empty map
  ;       would be removed, because the containing vector was not empty.
  ;       But after removing the empty map from the vector, the vector gets empty
  ;       and the recursive call of this function can remove the emptied vector as well.
  (letfn [(f [x] (vector/contains-item? [{} [] () nil ""] x))]
         (let [result (map/remove-values-by n f {:recur? true})]
              (if (-> result (= n))
                  (-> result)
                  (-> result remove-blank-values)))))