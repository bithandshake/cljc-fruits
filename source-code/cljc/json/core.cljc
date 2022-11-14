
(ns json.core
    (:require [candy.api          :refer [return]]
              [json.config        :as config]
              [mid-fruits.keyword :as keyword]
              [mid-fruits.map     :as map]
              [mixed.api          :as mixed]
              [mid-fruits.string  :as string]
              [syntax.api         :as syntax]
              [mid-fruits.vector  :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn json->clj
  ; @param (json) n
  ;
  ; @example (json->clj ...)
  ;  {...}
  ;
  ; @return (map)
  [n]
  #?(:cljs (js->clj n :keywordize-keys true)))

;; -- Keywordize / unkeywordize key -------------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordize-key
  ; @param (*) n
  ;
  ; @example
  ;  (unkeywordize-key :my-namespace/key)
  ;  =>
  ;  "my-namespace/key"
  ;
  ; @return (*)
  [n]
  (keyword/to-string n))

(defn keywordize-key
  ; @param (*) n
  ;
  ; @example
  ;  (keywordize-key "my-namespace/key")
  ;  =>
  ;  :my-namespace/key
  ;
  ; @return (*)
  [n]
  (keyword n))

;; -- Underscore / hyphenize key ----------------------------------------------
;; ----------------------------------------------------------------------------

(defn underscore-key
  ; @param (*) n
  ;
  ; @example
  ;  (underscore-key :my-namespace/key)
  ;  =>
  ;  :my_namespace/key
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (string/replace-part n "-" "_")
        (keyword? n) (-> n name underscore-key keyword)
        :return   n))

(defn hyphenize-key
  ; @param (*) n
  ;
  ; @example
  ;  (hyphenize-key :my_namespace/key)
  ;  =>
  ;  :my-namespace/key
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (string/replace-part n "_" "-")
        (keyword? n) (-> n name hyphenize-key keyword)
        :return   n))

;; -- Snake-case / CamelCase key ----------------------------------------------
;; ----------------------------------------------------------------------------

(defn CamelCase-key
  ; @param (*) n
  ;
  ; @example
  ;  (CamelCase-key :my-key)
  ;  =>
  ;  :myKey
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (syntax/CamelCase n)
        (keyword? n) (-> n name CamelCase-key keyword)
        :return   n))

(defn snake-case-key
  ; @param (*) n
  ;
  ; @example
  ;  (snake-case-key :myKey)
  ;  =>
  ;  :my-key
  ;
  ; @return (*)
  [n]
  (cond (string?  n) (syntax/snake-case n)
        (keyword? n) (-> n name snake-case-key keyword)
        :return   n))

;; -- Keywordize / unkeywordize value -----------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordized-value?
  ; @param (*) n
  ;
  ; @example
  ;  (unkeywordized-value? "*:apple")
  ;  =>
  ;  true
  ;
  ; @return (boolean)
  [n]
  (and    (string? n)
       (> (count   n) 2)
       (= config/KEYWORD-PREFIX (str (nth n 0)))
       (= ":"                   (str (nth n 1)))))

(defn keywordize-value
  ; @param (*) n
  ;
  ; @example
  ;  (keywordize-value "*:my-value")
  ;  =>
  ;  :my-value
  ;
  ; @return (*)
  [n]
  (if (unkeywordized-value? n)
      (->     n (subs 2) keyword)
      (return n)))

(defn unkeywordize-value
  ; @param (*) n
  ;
  ; @example
  ;  (unkeywordize-value :my-value)
  ;  =>
  ;  "*:my-value"
  ;
  ; @return (*)
  [n]
  (if (keyword?                  n)
      (str config/KEYWORD-PREFIX n)
      (return                    n)))

;; -- Trim value --------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-value
  ; @param (*) n
  ;
  ; @example
  ;  (trim-value " My value ")
  ;  =>
  ;  "My value"
  ;
  ; @return (*)
  [n]
  (if (string?     n)
      (string/trim n)
      (return      n)))

;; -- Parse number value ------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-number-value
  ; @param (*) n
  ;
  ; @example
  ;  (parse-number-value "89.420")
  ;  =>
  ;  89.420
  ;
  ; @return (*)
  [n]
  (mixed/parse-number n))

;; -- Keywordize / unkeywordize keys ------------------------------------------
;; ----------------------------------------------------------------------------

(defn unkeywordize-keys
  ; @param (*) n
  ;
  ; @example
  ;  (unkeywordize-keys {:my-namespace/key :my-value})
  ;  =>
  ;  {"my-namespace/key" :my-value}
  ;
  ; @return (*)
  [n]
  ; Az unkeywordize-key függvény csak keyword típusokat módosít, ezért nincs szükség további
  ; típus-vizsgálatra!
  (cond (map?    n) (map/->>keys      n unkeywordize-keys)
        (vector? n) (vector/->items   n unkeywordize-keys)
        :return     (unkeywordize-key n)))

(defn keywordize-keys
  ; @param (*) n
  ;
  ; @example
  ;  (keywordize-keys {"my-namespace/key" :my-value})
  ;  =>
  ;  {:my-namespace/key :my-value}
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
  ; @example
  ;  (underscore-keys {:my-namespace/key :my-value})
  ;  =>
  ;  {:my_namespace/key :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n underscore-keys)
        (vector? n) (vector/->items n underscore-keys)
        :return     (underscore-key n)))

(defn hyphenize-keys
  ; @param (*) n
  ;
  ; @example
  ;  (hyphenize-keys {:my_namespace/key :my-value})
  ;  =>
  ;  {:my-namespace/key :my-value}
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
  ; @example
  ;  (CamelCase-keys {:my-key :my-value})
  ;  =>
  ;  {:myKey :my-value}
  ;
  ; @return (*)
  [n]
  (cond (map?    n) (map/->>keys    n CamelCase-keys)
        (vector? n) (vector/->items n CamelCase-keys)
        :return     (CamelCase-key  n)))

(defn snake-case-keys
  ; @param (*) n
  ;
  ; @example
  ;  (snake-case-keys {:myKey :my-value})
  ;  =>
  ;  {:my-key :my-value}
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
  ; @example
  ;  (unkeywordize-values {:a :b :c [:d "e"] :f {:g "h" :i :j}})
  ;  =>
  ;  {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}}
  ;
  ; @return (*)
  [n]
  ; XXX#5914 (source-code/cljc/json/config.cljc)
  ;
  ; Az unkeywordize- függvény csak keyword típusokat módosít, ezért nincs szükség további
  ; típus-vizsgálatra!
  (cond (map?    n) (map/->>values      n unkeywordize-values)
        (vector? n) (vector/->items     n unkeywordize-values)
        :return     (unkeywordize-value n)))

(defn keywordize-values
  ; @param (*) n
  ;
  ; @example
  ;  (keywordize-values {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}})
  ;  =>
  ;  {:a :b :c [:d "e"] :f {:g "h" :i :j}}
  ;
  ; @return (*)
  [n]
  ; XXX#5914 (source-code/cljc/json/config.cljc)
  ;
  ; A keywordize-value függvény csak string típusokat módosít, ezért nincs szükség további
  ; típus-vizsgálatra!
  (cond (map?    n) (map/->>values    n keywordize-values)
        (vector? n) (vector/->items   n keywordize-values)
        :return     (keywordize-value n)))

;; -- Trim values -------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-values
  ; @param (*) n
  ;
  ; @example
  ;  (trim-values {:a "b " :c [" d " "e"] :f {:g " h"}})
  ;  =>
  ;  {:a "b" :c ["d" "e"] :f {:g "h"}}
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
  ; @example
  ;  (parse-number-values {:a "0" :c ["1"] :f {:g "2"}})
  ;  =>
  ;  {:a 0 :c [1] :f {:g 2}}
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
  ; @example
  ;  (remove-blank-values {:a "" :c [] :f {:g nil}})
  ;  =>
  ;  {}
  ;
  ; @return (*)
  [n]
  ; A remove-blank-values függvény az üres értékek eltávolítása után rekurzívan
  ; meghívja önmagát addig, amíg már nem okoz újabb változást az n értékében,
  ; így biztosítva, hogy ne hagyjon maga után üres értékeket.
  ; Pl.: Ha az n térkép egyik értéke egy vektor, amiben egy üres térkép van,
  ;      akkor a rekurzió első iterációjakor a vektor még nem üres,
  ;      de az üres térkép eltávolítása után az azt tartalmazó vektor is üressé
  ;      válik és ezért a következő iterációban már eltávolítható.
  (letfn [(r-f [x] (vector/contains-item? [{} [] () nil ""] x))]
         (let [result (map/->>remove-values-by n r-f)]
              (if (=                 n result)
                  (return              result)
                  (remove-blank-values result)))))
