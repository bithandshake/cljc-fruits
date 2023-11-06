
### json.api

Functional documentation of the json.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > json.api

### Index

- [CamelCase-key](#camelcase-key)

- [CamelCase-keys](#camelcase-keys)

- [hyphenize-key](#hyphenize-key)

- [hyphenize-keys](#hyphenize-keys)

- [json->clj](#json-clj)

- [keywordize-key](#keywordize-key)

- [keywordize-keys](#keywordize-keys)

- [keywordize-value](#keywordize-value)

- [keywordize-values](#keywordize-values)

- [parse-number-value](#parse-number-value)

- [parse-number-values](#parse-number-values)

- [remove-blank-values](#remove-blank-values)

- [snake-case-key](#snake-case-key)

- [snake-case-keys](#snake-case-keys)

- [trim-value](#trim-value)

- [trim-values](#trim-values)

- [underscore-key](#underscore-key)

- [underscore-keys](#underscore-keys)

- [unkeywordize-key](#unkeywordize-key)

- [unkeywordize-keys](#unkeywordize-keys)

- [unkeywordize-value](#unkeywordize-value)

- [unkeywordize-values](#unkeywordize-values)

- [unkeywordized-value?](#unkeywordized-value)

---

### CamelCase-key

```
@param (*) n
```

```
@usage
(CamelCase-key :my-key)
```

```
@usage
(CamelCase-key "my-key")
```

```
@example
(CamelCase-key :my-key)
=>
:myKey
```

```
@example
(CamelCase-key "my-key")
=>
"myKey"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn CamelCase-key
  [n]
  (cond (string?  n) (-> n syntax/ToCamelCase)
        (keyword? n) (-> n name CamelCase-key keyword)
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [CamelCase-key]]))

(json.api/CamelCase-key ...)
(CamelCase-key          ...)
```

</details>

---

### CamelCase-keys

```
@param (*) n
```

```
@usage
(Case-keys {:my-key :my-value})
```

```
@usage
(Case-keys {"my-key" :my-value})
```

```
@example
(Case-keys {:my-key :my-value})
=>
{:myKey :my-value}
```

```
@example
(CamelCase-keys {"my-key" :my-value})
=>
{"myKey" :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn CamelCase-keys
  [n]
  (cond (map?    n) (map/->>keys    n CamelCase-keys)
        (vector? n) (vector/->items n CamelCase-keys)
        :return     (CamelCase-key  n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [CamelCase-keys]]))

(json.api/CamelCase-keys ...)
(CamelCase-keys          ...)
```

</details>

---

### hyphenize-key

```
@param (*) n
```

```
@usage
(hyphenize-key :my_namespace/key)
```

```
@usage
(hyphenize-key "my_namespace/key")
```

```
@example
(hyphenize-key :my_namespace/key)
=>
:my-namespace/key
```

```
@example
(hyphenize-key "my_namespace/key")
=>
"my-namespace/key"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn hyphenize-key
  [n]
  (cond (string?  n) (-> n (string/replace-part "_" "-"))
        (keyword? n) (-> n name hyphenize-key keyword)
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [hyphenize-key]]))

(json.api/hyphenize-key ...)
(hyphenize-key          ...)
```

</details>

---

### hyphenize-keys

```
@param (*) n
```

```
@usage
(hyphenize-keys {:my_namespace/key :my-value})
```

```
@usage
(hyphenize-keys {"my_namespace/key" :my-value})
```

```
@example
(hyphenize-keys {:my_namespace/key :my-value})
=>
{:my-namespace/key :my-value}
```

```
@example
(hyphenize-keys {"my_namespace/key" :my-value})
=>
{"my-namespace/key" :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn hyphenize-keys
  [n]
  (cond (map?    n) (map/->>keys    n hyphenize-keys)
        (vector? n) (vector/->items n hyphenize-keys)
        :return     (hyphenize-key  n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [hyphenize-keys]]))

(json.api/hyphenize-keys ...)
(hyphenize-keys          ...)
```

</details>

---

### json->clj

```
@param (json) n
```

```
@example=>
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn json->clj
  [n]
  #?(:cljs (js->clj n :keywordize-keys true)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [json->clj]]))

(json.api/json->clj ...)
(json->clj          ...)
```

</details>

---

### keywordize-key

```
@param (*) n
```

```
@usage
(keywordize-key "my-namespace/key")
```

```
@example
(keywordize-key "my-namespace/key")
=>
:my-namespace/key
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn keywordize-key
  [n]
  (keyword n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [keywordize-key]]))

(json.api/keywordize-key ...)
(keywordize-key          ...)
```

</details>

---

### keywordize-keys

```
@param (*) n
```

```
@usage
(keywordize-keys {"my-namespace/key" :my-value})
```

```
@example
(keywordize-keys {"my-namespace/key" :my-value})
=>
{:my-namespace/key :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn keywordize-keys
  [n]
  (cond (map?    n) (map/->>keys    n keywordize-keys)
        (vector? n) (vector/->items n keywordize-keys)
        :return     (keywordize-key n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [keywordize-keys]]))

(json.api/keywordize-keys ...)
(keywordize-keys          ...)
```

</details>

---

### keywordize-value

```
@param (*) n
```

```
@usage
(keywordize-value "*:my-value")
```

```
@example
(keywordize-value "*:my-value")
=>
:my-value
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn keywordize-value
  [n]
  (if (-> n unkeywordized-value?)
      (-> n (subs 2) keyword)
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [keywordize-value]]))

(json.api/keywordize-value ...)
(keywordize-value          ...)
```

</details>

---

### keywordize-values

```
@param (*) n
```

```
@usage
(keywordize-values {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}})
```

```
@example
(keywordize-values {:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}})
=>
{:a :b :c [:d "e"] :f {:g "h" :i :j}}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn keywordize-values
  [n]
  (cond (map?    n) (map/->>values    n keywordize-values)
        (vector? n) (vector/->items   n keywordize-values)
        :return     (keywordize-value n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [keywordize-values]]))

(json.api/keywordize-values ...)
(keywordize-values          ...)
```

</details>

---

### parse-number-value

```
@param (*) n
```

```
@usage
(parse-number-value "89.420")
```

```
@example
(parse-number-value "89.420")
=>
89.420
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn parse-number-value
  [n]
  (mixed/parse-number n {:return? true}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [parse-number-value]]))

(json.api/parse-number-value ...)
(parse-number-value          ...)
```

</details>

---

### parse-number-values

```
@param (*) n
```

```
@usage
(parse-number-values {:a "0" :c ["1"] :f {:g "2"}})
```

```
@example
(parse-number-values {:a "0" :c ["1"] :f {:g "2"}})
=>
{:a 0 :c [1] :f {:g 2}}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn parse-number-values
  [n]
  (cond (map?    n) (map/->>values      n parse-number-values)
        (vector? n) (vector/->items     n parse-number-values)
        :return     (parse-number-value n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [parse-number-values]]))

(json.api/parse-number-values ...)
(parse-number-values          ...)
```

</details>

---

### remove-blank-values

```
@param (*) n
```

```
@usage
(remove-blank-values {:a "" :c [] :f {:g nil}})
```

```
@example
(remove-blank-values {:a "" :c [] :f {:g nil}})
=>
{}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn remove-blank-values
  [n]
  (letfn [(r-f [x] (vector/contains-item? [{} [] () nil ""] x))]
         (let [result (map/->>remove-values-by n r-f)]
              (if (-> result (= n))
                  (-> result)
                  (-> result remove-blank-values)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [remove-blank-values]]))

(json.api/remove-blank-values ...)
(remove-blank-values          ...)
```

</details>

---

### snake-case-key

```
@param (*) n
```

```
@usage
(snake-case-key :myKey)
```

```
@usage
(snake-case-key "myKey")
```

```
@example
(snake-case-key :myKey)
=>
:my-key
```

```
@example
(snake-case-key "myKey")
=>
"my-key"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn snake-case-key
  [n]
  (cond (string?  n) (-> n syntax/to-snake-case )
        (keyword? n) (-> n name snake-case-key keyword)
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [snake-case-key]]))

(json.api/snake-case-key ...)
(snake-case-key          ...)
```

</details>

---

### snake-case-keys

```
@param (*) n
```

```
@usage
(snake-case-keys {:myKey :my-value})
```

```
@usage
(snake-case-keys {"myKey" :my-value})
```

```
@example
(snake-case-keys {:myKey :my-value})
=>
{:my-key :my-value}
```

```
@example
(snake-case-keys {"myKey" :my-value})
=>
{"my-key" :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn snake-case-keys
  [n]
  (cond (map?    n) (map/->>keys     n snake-case-keys)
        (vector? n) (vector/->items  n snake-case-keys)
        :return     (snake-case-key  n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [snake-case-keys]]))

(json.api/snake-case-keys ...)
(snake-case-keys          ...)
```

</details>

---

### trim-value

```
@param (*) n
```

```
@usage
(trim-value " My value ")
```

```
@example
(trim-value " My value ")
=>
"My value"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn trim-value
  [n]
  (if (-> n string?)
      (-> n string/trim)
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [trim-value]]))

(json.api/trim-value ...)
(trim-value          ...)
```

</details>

---

### trim-values

```
@param (*) n
```

```
@usage
(trim-values {:a "b " :c [" d " "e"] :f {:g " h"}})
```

```
@example
(trim-values {:a "b " :c [" d " "e"] :f {:g " h"}})
=>
{:a "b" :c ["d" "e"] :f {:g "h"}}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn trim-values
  [n]
  (cond (map?    n) (map/->>values  n trim-values)
        (vector? n) (vector/->items n trim-values)
        :return     (trim-value     n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [trim-values]]))

(json.api/trim-values ...)
(trim-values          ...)
```

</details>

---

### underscore-key

```
@param (*) n
```

```
@usage
(underscore-key :my-namespace/key)
```

```
@usage
(underscore-key "my-namespace/key")
```

```
@example
(underscore-key :my-namespace/key)
=>
:my_namespace/key
```

```
@example
(underscore-key "my-namespace/key")
=>
"my_namespace/key"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn underscore-key
  [n]
  (cond (string?  n) (-> n (string/replace-part "-" "_"))
        (keyword? n) (-> n name underscore-key keyword)
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [underscore-key]]))

(json.api/underscore-key ...)
(underscore-key          ...)
```

</details>

---

### underscore-keys

```
@param (*) n
```

```
@usage
(underscore-keys {:my-namespace/key :my-value})
```

```
@usage
(underscore-keys {"my-namespace/key" :my-value})
```

```
@example
(underscore-keys {:my-namespace/key :my-value})
=>
{:my_namespace/key :my-value}
```

```
@example
(underscore-keys {"my-namespace/key" :my-value})
=>
{"my_namespace/key" :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn underscore-keys
  [n]
  (cond (map?    n) (map/->>keys    n underscore-keys)
        (vector? n) (vector/->items n underscore-keys)
        :return     (underscore-key n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [underscore-keys]]))

(json.api/underscore-keys ...)
(underscore-keys          ...)
```

</details>

---

### unkeywordize-key

```
@param (*) n
```

```
@usage
(unkeywordize-key :my-namespace/key)
```

```
@example
(unkeywordize-key :my-namespace/key)
=>
"my-namespace/key"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn unkeywordize-key
  [n]
  (keyword/to-string n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [unkeywordize-key]]))

(json.api/unkeywordize-key ...)
(unkeywordize-key          ...)
```

</details>

---

### unkeywordize-keys

```
@param (*) n
```

```
@usage
(unkeywordize-keys {:my-namespace/key :my-value})
```

```
@example
(unkeywordize-keys {:my-namespace/key :my-value})
=>
{"my-namespace/key" :my-value}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn unkeywordize-keys
  [n]
  (cond (map?    n) (map/->>keys      n unkeywordize-keys)
        (vector? n) (vector/->items   n unkeywordize-keys)
        :return     (unkeywordize-key n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [unkeywordize-keys]]))

(json.api/unkeywordize-keys ...)
(unkeywordize-keys          ...)
```

</details>

---

### unkeywordize-value

```
@param (*) n
```

```
@usage
(unkeywordize-value :my-value)
```

```
@example
(unkeywordize-value :my-value)
=>
"*:my-value"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn unkeywordize-value
  [n]
  (if (-> n keyword?)
      (-> config/KEYWORD-PREFIX (str n))
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [unkeywordize-value]]))

(json.api/unkeywordize-value ...)
(unkeywordize-value          ...)
```

</details>

---

### unkeywordize-values

```
@param (*) n
```

```
@usage
(unkeywordize-values {:a :b :c [:d "e"] :f {:g "h" :i :j}})
```

```
@example
(unkeywordize-values {:a :b :c [:d "e"] :f {:g "h" :i :j}})
=>
{:a "*:b" :c ["*:d" "e"] :f {:g "*h" :i "*:j"}}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn unkeywordize-values
  [n]
  (cond (map?    n) (map/->>values      n unkeywordize-values)
        (vector? n) (vector/->items     n unkeywordize-values)
        :return     (unkeywordize-value n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [unkeywordize-values]]))

(json.api/unkeywordize-values ...)
(unkeywordize-values          ...)
```

</details>

---

### unkeywordized-value?

```
@param (*) n
```

```
@usage
(unkeywordized-value? "*:apple")
```

```
@example
(unkeywordized-value? "*:apple")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn unkeywordized-value?
  [n]
  (and    (string? n)
       (> (count   n) 2)
       (= config/KEYWORD-PREFIX (str (nth n 0)))
       (= ":"                   (str (nth n 1)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [json.api :refer [unkeywordized-value?]]))

(json.api/unkeywordized-value? ...)
(unkeywordized-value?          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

