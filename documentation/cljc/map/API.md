
# map.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > map.api

### Index

- [->>keys](#-keys)

- [->>kv](#-kv)

- [->>remove-keys-by](#-remove-keys-by)

- [->>remove-values-by](#-remove-values-by)

- [->>values](#-values)

- [->keys](#-keys)

- [->kv](#-kv)

- [->remove-keys-by](#-remove-keys-by)

- [->remove-values-by](#-remove-values-by)

- [->values](#-values)

- [add-namespace](#add-namespace)

- [all-values-match?](#all-values-match)

- [any-key-match?](#any-key-match)

- [any-value-match?](#any-value-match)

- [assoc-in-or](#assoc-in-or)

- [assoc-in-some](#assoc-in-some)

- [assoc-ns](#assoc-ns)

- [assoc-or](#assoc-or)

- [assoc-some](#assoc-some)

- [contains-key?](#contains-key)

- [contains-of-keys?](#contains-of-keys)

- [contains-value?](#contains-value)

- [deep-merge](#deep-merge)

- [difference](#difference)

- [dissoc-in](#dissoc-in)

- [dissoc-items](#dissoc-items)

- [filter-values](#filter-values)

- [filter-values-by](#filter-values-by)

- [get-first-key](#get-first-key)

- [get-first-match-key](#get-first-match-key)

- [get-first-match-value](#get-first-match-value)

- [get-first-value](#get-first-value)

- [get-keys](#get-keys)

- [get-keys-by](#get-keys-by)

- [get-namespace](#get-namespace)

- [get-ns](#get-ns)

- [get-values](#get-values)

- [inject-in](#inject-in)

- [match-pattern?](#match-pattern)

- [merge-some](#merge-some)

- [namespaced?](#namespaced)

- [nonempty?](#nonempty)

- [rekey-item](#rekey-item)

- [rekey-items](#rekey-items)

- [remove-keys](#remove-keys)

- [remove-keys-by](#remove-keys-by)

- [remove-namespace](#remove-namespace)

- [remove-values](#remove-values)

- [remove-values-by](#remove-values-by)

- [reversed-merge](#reversed-merge)

- [swap](#swap)

- [to-vector](#to-vector)

- [toggle](#toggle)

- [toggle-in](#toggle-in)

- [update-in-some](#update-in-some)

- [update-some](#update-some)

- [values-equal?](#values-equal)

### ->>keys

```
@param (map) n
@param (function) update-f
```

```
@usage
(->>keys {:a "A" :b [{:c "C"}]} name)
```

```
@example
(->>keys {:a "A" :b "B" :c [{:d "D"}]} name)
=>
{"a" "A" "b" "B" "c" [{"d" "D"}]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->>keys
  [n update-f]
  (letfn [(f [n] (cond (vector? n) (reduce    #(conj  %1               (f %2)) [] n)
                       (map?    n) (reduce-kv #(assoc %1 (update-f %2) (f %3)) {} n)
                       :return  n))]
         (f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->>keys]]))

(map.api/->>keys ...)
(->>keys         ...)
```

</details>

---

### ->>kv

```
@param (map) n
@param (function) k-f
@param (function) v-f
```

```
@example
(->>kv {"a" "A" "b" ["C"]} keyword keyword)
  ;
@example
(->>kv {"a" "A" "b" "B" "c" ["D" "E" {"f" "F"}]} keyword keyword)
=>
{:a :A :b :B :c [:D :E {:f :F}]}
```

```
@example
(->>kv {"a" "A" "b" "B" "c" ["D" "E" {"f" "F"}]} keyword keyword)
=>
{:a :A :b :B :c [:D :E {:f :F}]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->>kv
  [n k-f v-f]
  (letfn [(f [n] (cond (map?    n) (reduce-kv #(assoc %1 (k-f %2) (f %3)) {} n)
                       (vector? n) (reduce    #(conj  %1          (f %2)) [] n)
                       :return     (v-f n)))]
         (f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->>kv]]))

(map.api/->>kv ...)
(->>kv         ...)
```

</details>

---

### ->>remove-keys-by

```
@param (map) n
@param (function) r-f
```

```
@example
(->>remove-keys-by {:a "A" :b "B" :c {:a "A" :b "B"}}
                   #(= % :a))
=>
{:b "B" :c {:b "B"}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->>remove-keys-by
  [n r-f])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->>remove-keys-by]]))

(map.api/->>remove-keys-by ...)
(->>remove-keys-by         ...)
```

</details>

---

### ->>remove-values-by

```
@param (map) n
@param (function) r-f
```

```
@usage
(->>remove-values-by {:a "A" :b {:c [{:d "D"}]}}
                     #(= % "D"))
```

```
@example
(->>remove-values-by {:a "A" :b "B" :c {:a "A" :b "B" :c [{:a "A"}]}}
                     #(= % "A"))
=>
{:b "B" :c {:b "B" :c [{}]}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->>remove-values-by
  [n r-f]
  (letfn [(m-f [n k x] (if   (r-f     x) n (assoc n k (f x))))
          (v-f [n   x] (if   (r-f     x) n (conj  n   (f x))))
          (f   [n]     (cond (map?    n)   (reduce-kv m-f {} n)
                             (vector? n)   (reduce    v-f [] n)
                             :return  n))]
         (f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->>remove-values-by]]))

(map.api/->>remove-values-by ...)
(->>remove-values-by         ...)
```

</details>

---

### ->>values

```
@param (map) n
@param (function) update-f
```

```
@usage
(->>values {:a "A" :b ["C"]} keyword)
```

```
@example
(->>values {:a "A" :b "B" :c [:d "E" {:f "F"}]} keyword)
=>
{:a :A :b :B :c [:d :e {:f :F}]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->>values
  [n update-f]
  (letfn [(f [n] (cond (map?    n) (reduce-kv #(assoc %1 %2 (f %3)) {} n)
                       (vector? n) (reduce    #(conj  %1    (f %2)) [] n)
                       :return     (update-f n)))]
         (f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->>values]]))

(map.api/->>values ...)
(->>values         ...)
```

</details>

---

### ->keys

```
@param (map) n
@param (function) update-f
```

```
@usage
(->keys {"a" "A" "b" "B"} keyword)
```

```
@example
(->keys {:a "A" :b "B"} name)
=>
{"a" "A" "b" "B"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->keys
  [n update-f]
  (letfn [(f [%1 %2 %3] (assoc %1 (update-f %2) %3))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->keys]]))

(map.api/->keys ...)
(->keys         ...)
```

</details>

---

### ->kv

```
@param (map) n
@param (function) k-f
@param (function) v-f
```

```
@usage
(->kv {:a 1} name inc)
```

```
@example
(->kv {:a 1 :b 2} name inc)
=>
{"a" 2 "b" 3}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->kv
  [n k-f v-f]
  (letfn [(f [%1 %2 %3] (assoc %1 (k-f %2) (v-f %3)))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->kv]]))

(map.api/->kv ...)
(->kv         ...)
```

</details>

---

### ->remove-keys-by

```
@param (map) n
@param (function) r-f
```

```
@usage
(->remove-keys-by {:a "A"} #(= % :a))
```

```
@example
(->remove-keys-by {:a "A" :b "B"}
                  #(= % :a))
=>
{:b "B"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->remove-keys-by
  [n r-f]
  (remove/remove-keys-by n r-f))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->remove-keys-by]]))

(map.api/->remove-keys-by ...)
(->remove-keys-by         ...)
```

</details>

---

### ->remove-values-by

```
@param (map) n
@param (function) r-f
```

```
@example
(->remove-values-by {:a "A" :b "B"}
                    #(= % "A"))
=>
{:b "B"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->remove-values-by
  [n r-f]
  (remove/remove-values-by n r-f))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->remove-values-by]]))

(map.api/->remove-values-by ...)
(->remove-values-by         ...)
```

</details>

---

### ->values

```
@param (map) n
@param (function) update-f
```

```
@example
(->values {:a "A" :b "B"} keyword)
=>
{:a :A :b :B}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn ->values
  [n update-f]
  (reduce-kv #(assoc %1 %2 (update-f %3)) {} n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [->values]]))

(map.api/->values ...)
(->values         ...)
```

</details>

---

### add-namespace

```
@param (map) n
@param (keyword) namespace
```

```
@usage
(add-namespace {:a "A"} :b)
```

```
@example
(add-namespace {:a "A"} :b)
=>
{:a/b "A"}
```

```
@example
(add-namespace {"a" "A"} :b)
=>
{"a/b" "A"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn add-namespace
  [n namespace]
  (letfn [(f [n item-key item-value]
             (cond (string?  item-key) (assoc n (str     (name namespace) "/"   item-key)  item-value)
                   (keyword? item-key) (assoc n (keyword (name namespace) (name item-key)) item-value)
                   :return n))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [add-namespace]]))

(map.api/add-namespace ...)
(add-namespace         ...)
```

</details>

---

### all-values-match?

```
@param (map) n
@param (function) test-f
```

```
@example
(all-values-match? {:a "A"} string?)
  ;
@example
(all-values-match? {:a :A :b "B"} string?)
=>
false
```

```
@example
(all-values-match? {:a :A :b "B"} string?)
=>
false
```

```
@example
(all-values-match? {:a "A" :b "B"} string?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn all-values-match?
  [n test-f]
  (letfn [(f [%] (test-f (second %)))]
         (every? f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [all-values-match?]]))

(map.api/all-values-match? ...)
(all-values-match?         ...)
```

</details>

---

### any-key-match?

```
@param (map) n
@param (function) test-f
```

```
@usage
(any-key-match? {:a "A"} keyword?)
```

```
@example
(any-key-match? {:a "A" :b "B"} string?)
=>
false
```

```
@example
(any-key-match? {:a "A" "b" "B"} string?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn any-key-match?
  [n test-f]
  (letfn [(f [%] (test-f (first %)))]
         (boolean (some f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [any-key-match?]]))

(map.api/any-key-match? ...)
(any-key-match?         ...)
```

</details>

---

### any-value-match?

```
@param (map) n
@param (function) test-f
```

```
@usage
(any-value-match? {:a "A"} string?)
```

```
@example
(any-value-match? {:a "A" :b "B"} keyword?)
=>
false
```

```
@example
(any-value-match? {:a :A :b "B"} string?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn any-value-match?
  [n test-f]
  (letfn [(f [%] (test-f (second %)))]
         (boolean (some f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [any-value-match?]]))

(map.api/any-value-match? ...)
(any-value-match?         ...)
```

</details>

---

### assoc-in-or

```
@description
Assoc-in the value to the n map if the value-path's value is nil.
```

```
@param (map) n
@param (vector) value-path
@param (*) value
```

```
@usage
(assoc-in-or {:a {:b nil}} [:a :b] "B")
```

```
@example
(assoc-in-or {:a {:b "B"}} [:a :b] "X")
=>
{:a {:b "B"}}
```

```
@example
(assoc-in-or {:a {:b nil}} [:a :b] "X")
=>
{:a {:b "X"}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn assoc-in-or
  [n value-path value]
  (assoc-in n value-path (or (get n value-path) value)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [assoc-in-or]]))

(map.api/assoc-in-or ...)
(assoc-in-or         ...)
```

</details>

---

### assoc-in-some

```
@description
Assoc-in the value to the n map if the value is something.
```

```
@param (map) n
@param (vector) value-path
@param (*) value
```

```
@usage
(assoc-in-some {} [:a :b] "B")
```

```
@example
(assoc-in-some {:a [:b :c]} [:d :e] "E")
=>
{:a [:b :c] :d {:e "E"}}
```

```
@example
(assoc-in-some {:a [:b :c]} [:d :e] nil)
=>
{:a [:b :c]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn assoc-in-some
  [n value-path value]
  (if (some?                 value)
      (assoc-in n value-path value)
      (return   n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [assoc-in-some]]))

(map.api/assoc-in-some ...)
(assoc-in-some         ...)
```

</details>

---

### assoc-ns

```
@param (map) n
@param (keyword) key
@param (*) value
```

```
@example
(assoc-ns {:fruit/apple "red"} :banana "yellow")
=>
{:fruit/apple "red" :fruit/banana "yellow"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn assoc-ns
  [n key value])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [assoc-ns]]))

(map.api/assoc-ns ...)
(assoc-ns         ...)
```

</details>

---

### assoc-or

```
@description
Assoc the value to the n map if the value is nil.
```

```
@param (map) n
@param (*) key
@param (*) value
```

```
@usage
(assoc-or {:a "A"} :a "X")
```

```
@example
(assoc-or {:a "A"} :a "X")
=>
{:a "A"}
```

```
@example
(assoc-or {:a nil} :a "X")
=>
{:a "X"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn assoc-or
  [n key value]
  (assoc n key (or (get n key) value)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [assoc-or]]))

(map.api/assoc-or ...)
(assoc-or         ...)
```

</details>

---

### assoc-some

```
@description
Assoc values to the n map if the value is something.
```

```
@param (map) n
@param (list of * pairs) kv-pairs
```

```
@usage
(assoc-some {} :a "A" :b "B")
```

```
@example
(assoc-some {:a [:b :c]} :d "D")
=>
{:a [:b :c] :d "D"}
```

```
@example
(assoc-some {:a [:b :c]} :d nil :e "E")
=>
{:a [:b :c] :e "E"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn assoc-some
  [n & kv-pairs]
  (letfn [(f [n k v] (if (some? v)
                         (assoc n k v)
                         (return n)))]
         (reduce-pairs f n kv-pairs)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [assoc-some]]))

(map.api/assoc-some ...)
(assoc-some         ...)
```

</details>

---

### contains-key?

```
@param (map) n
@param (*) x
```

```
@usage
(contains-key? {:a "B" :b "B"} :a)
```

```
@example
(contains-key? {:a {:b "B"}} :a)
=>
true
```

```
@example
(contains-key? {:a {:b "B"}} :b)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-key?
  [n x]
  (contains? n x))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [contains-key?]]))

(map.api/contains-key? ...)
(contains-key?         ...)
```

</details>

---

### contains-of-keys?

```
@param (map) n
@param (* in vector) keys
```

```
@usage
(contains-of-keys? {:a "A" :b "B"} [:a])
```

```
@example
(contains-of-keys? {:a {:b "B"} :c "C"} [:a])
=>
true
```

```
@example
(contains-of-keys? {:a {:b "B"} :c "C"} [:a :b :c :d])
=>
true
```

```
@example
(contains-of-keys? {:a {:b "B"}} [:b :c :d])
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-of-keys?
  [n keys]
  (letfn [(f [%] (contains? n %))]
         (boolean (some f keys))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [contains-of-keys?]]))

(map.api/contains-of-keys? ...)
(contains-of-keys?         ...)
```

</details>

---

### contains-value?

```
@param (map) n
@param (*) x
```

```
@example
(contains-value? {:a "A"} "A")
  ;
@example
(contains-value? {} "A")
=>
false
```

```
@example
(contains-value? {} "A")
=>
false
```

```
@example
(contains-value? {:a "A"} "A")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-value?
  [n x]
  (letfn [(f [%] (= x (val %)))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [contains-value?]]))

(map.api/contains-value? ...)
(contains-value?         ...)
```

</details>

---

### deep-merge

```
@param (map) n
@param (list of maps) xyz
```

```
@usage
(deep-merge {:a {:b "a/b"}} {:c {:d "c/d"}})
```

```
@usage
(deep-merge {:a {:b "a/b"}} {:c {:d "c/d"}})
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn deep-merge
  [n & xyz]
  (letfn [(f [result x]
             (if (and (map? result)
                      (map? x))
                 (merge-with f result x)
                 (return x)))]
         (if (some identity xyz)
             (reduce f n xyz)
             (return n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [deep-merge]]))

(map.api/deep-merge ...)
(deep-merge         ...)
```

</details>

---

### difference

```
@param (map) a
@param (map) b
```

```
@usage
(difference {:a "a" :b "b"} {:a "a"})
```

```
@example
(difference {:a "a" :b "b"} {:a "a"})
=>
{:b "b"}
```

```
@return (map)
Things only in a
```

<details>
<summary>Source code</summary>

```
(defn difference
  [a b]
  (first (data/diff a b)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [difference]]))

(map.api/difference ...)
(difference         ...)
```

</details>

---

### dissoc-in

```
@param (map) n
@param (vector) value-path
```

```
@usage
(dissoc-in {:a {:b "B"}} [:a :b])
```

```
@example
(dissoc-in {:a {:b "B"}} [:a :b])
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn dissoc-in
  [n [key & keys :as value-path]]
  (if keys (if-let [next-n (get n key)]
                   (let [new-n (dissoc-in next-n keys)]
                        (if (seq          new-n)
                            (assoc  n key new-n)
                            (dissoc n key)))
                   (return n))
           (dissoc n key)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [dissoc-in]]))

(map.api/dissoc-in ...)
(dissoc-in         ...)
```

</details>

---

### dissoc-items

```
@param (map) n
@param (* in vector) keys
```

```
@usage
(dissoc-items {:a "A" :b "B"} [:a])
```

```
@example
(dissoc-items {:a "A" :b "B" :c "C"} [:a :b])
=>
{:c "C"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn dissoc-items
  [n keys]
  (apply dissoc n keys))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [dissoc-items]]))

(map.api/dissoc-items ...)
(dissoc-items         ...)
```

</details>

---

### filter-values

```
@param (map) n
@param (function) filter-f
```

```
@usage
(filter-values {:a "A"} string?)
```

```
@example
(filter-values {:a 0 :b 1 :c 2} even?)
=>
{:a 0 :c 2}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn filter-values
  [n filter-f]
  (letfn [(f [%1 %2 %3] (if (filter-f %3) (assoc %1 %2 %3) %1))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [filter-values]]))

(map.api/filter-values ...)
(filter-values         ...)
```

</details>

---

### filter-values-by

```
@param (map) n
@param (function) filter-f
@param (function) value-f
```

```
@usage
(filter-values-by {:a {:value "A"}} #(= % "A") :value)
```

```
@example
(filter-values-by {:a {:value "A"} :b {:value "B"}}
                  #(= % "A") :value)
=>
{:a {:value "A"}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn filter-values-by
  [n filter-f value-f]
  (letfn [(f [%1 %2 %3] (if (filter-f (value-f %3)) (assoc %1 %2 %3) %1))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [filter-values-by]]))

(map.api/filter-values-by ...)
(filter-values-by         ...)
```

</details>

---

### get-first-key

```
@param (map) n
```

```
@usage
(get-first-key {:a "A" :b "B"})
```

```
@example
(get-first-key {:a {:c "C"} :b "B"})
=>
:a
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-key
  [n]
  (-> n keys first))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-first-key]]))

(map.api/get-first-key ...)
(get-first-key         ...)
```

</details>

---

### get-first-match-key

```
@param (map) n
@param (function) test-f
```

```
@usage
(get-first-match-key {:a "A"} keyword?)
```

```
@example
(get-first-match-key {:a "A" :b "B"} string?)
=>
nil
```

```
@example
(get-first-match-key {:a "A" "b" "B"} string?)
=>
"b"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match-key
  [n test-f]
  (letfn [(f [%] (if (test-f (second %)) (first %)))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-first-match-key]]))

(map.api/get-first-match-key ...)
(get-first-match-key         ...)
```

</details>

---

### get-first-match-value

```
@param (map) n
@param (function) test-f
```

```
@usage
(get-first-match-value {:a "A"} string?)
```

```
@example
(get-first-match-value {:a "A" :b "B"} keyword?)
=>
nil
```

```
@example
(get-first-match-value {:a :A :b "B"} string?)
=>
"B"
```

```
@example
(get-first-match-value {:a {:id "apple"} :b {:id "banana"}} #(= "apple" (:id %)))
=>
{:id "apple"}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match-value
  [n test-f]
  (letfn [(f [%] (if (test-f (second %)) (second %)))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-first-match-value]]))

(map.api/get-first-match-value ...)
(get-first-match-value         ...)
```

</details>

---

### get-first-value

```
@param (map) n
```

```
@usage
(get-first-value {:a "A" :b "B"})
```

```
@example
(get-first-value {:a "A" :b "B"})
=>
"A"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-value
  [n]
  (-> n vals first))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-first-value]]))

(map.api/get-first-value ...)
(get-first-value         ...)
```

</details>

---

### get-keys

```
@param (map) n
```

```
@usage
(get-keys {:a "A" :b "B"})
```

```
@example
(get-keys {:a {:c "C"} :b "B"})
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn get-keys
  [n]
  (-> n keys vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-keys]]))

(map.api/get-keys ...)
(get-keys         ...)
```

</details>

---

### get-keys-by

```
@param (map) n
@param (function) f
```

```
@usage
(get-keys-by {:a "A" :b :b} string?)
```

```
@example
(get-keys-by {:a "A" :b :b :c :c} string?)
=>
[:a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn get-keys-by
  [n get-f]
  (letfn [(f [%1 %2 %3] (if (get-f %3) (conj %1 %2) %1))]
         (reduce-kv f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-keys-by]]))

(map.api/get-keys-by ...)
(get-keys-by         ...)
```

</details>

---

### get-namespace

```
@param (map) n
```

```
@usage
(get-namespace {:a/b "A"})
```

```
@example
(get-namespace {:a "A"})
=>
nil
```

```
@example
(get-namespace {:a/b "A"})
=>
:a
```

```
@example
(get-namespace {:a   "A"
                :b   "B"
                :c/d "C"
                :e/f "E"})
=>
:c
```

```
@example
(get-namespace {"a/b" "A"})
=>
:a
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn get-namespace
  [n]
  (letfn [(f [item-key]
             (cond (string? item-key)
                   (if-let [namespace (-> item-key keyword namespace)]
                           (keyword namespace))
                   (keyword? item-key)
                   (if-let [namespace (-> item-key         namespace)]
                           (keyword namespace))))]
         (some f (keys n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-namespace]]))

(map.api/get-namespace ...)
(get-namespace         ...)
```

</details>

---

### get-ns

```
@param (map) n
@param (keyword) key
```

```
@example
(get-ns {:fruit/apple "red"} :apple)
=>
"red"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-ns
  [n key]
  (if-let [namespace (get-namespace n)]
          (let [key (keyword (name namespace) (name key))]
               (get n key))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-ns]]))

(map.api/get-ns ...)
(get-ns         ...)
```

</details>

---

### get-values

```
@param (map) n
```

```
@usage
(get-values {:a {:b "B"}})
```

```
@example
(get-values {:a {:b "B"} :c "C"})
=>
[{:b "B"} "C"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn get-values
  [n]
  (-> n vals vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [get-values]]))

(map.api/get-values ...)
(get-values         ...)
```

</details>

---

### inject-in

```
@param (map) n
@param (vector) inject-path
@param (*) key
@param (*) value
```

```
@usage
(inject-in {} [:a] :b "B")
```

```
@example
(inject-in {} [:a :b] :c "C")
=>
{:a {:b {:c "C"}}}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn inject-in
  [n inject-path key value]
  (assoc-in n (conj inject-path key) value))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [inject-in]]))

(map.api/inject-in ...)
(inject-in         ...)
```

</details>

---

### match-pattern?

```
@param (map) ns
@param (map) pattern
@param (map)(opt) options
{:strict-matching? (boolean)(opt)
  Default: false}
```

```
@usage
(match-pattern? {:a "A" :b "B"} {:a "A"})
```

```
@example
(match-pattern? {:a "A" :b "B"} {:a "A"})
=>
true
```

```
@example
(match-pattern? {:a "A" :b "B"} {:a "A" :c "C"})
=>
false
```

```
@example
(match-pattern? {:a "A" :b "B"} {:a "A"} {:strict-matching? true})
=>
false
```

```
@example
(match-pattern? {:a "A" :b "B"} {:a "A" :b "B"} {:strict-matching? true})
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn match-pattern?
  ([n pattern]
   (match-pattern? n pattern {}))

  ([n pattern {:keys [strict-matching?]}]
   (let [difference (core/difference n pattern)]
        (or (and (not strict-matching?)
                 (= (count n)
                    (+ (count difference)
                       (count pattern))))
            (and (boolean strict-matching?)
                 (= (count n)
                    (count pattern))
                 (empty? difference))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [match-pattern?]]))

(map.api/match-pattern? ...)
(match-pattern?         ...)
```

</details>

---

### merge-some

```
@param (list of maps) xyz
```

```
@usage
(merge-some {:a "A"} {:a nil})
```

```
@example
(merge-some {:a "A"} {:a nil})
=>
{:a "A"}
```

```
@example
(merge-some {:a "A"} {:a nil} {:a "C"})
=>
{:a "C"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn merge-some
  [& xyz]
  (letfn [(f0 [result x]   (reduce-kv f1 result x))
          (f1 [result k v] (if (some? v)
                               (assoc  result k v)
                               (return result)))]
         (reduce f0 {} xyz)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [merge-some]]))

(map.api/merge-some ...)
(merge-some         ...)
```

</details>

---

### namespaced?

```
@param (map) n
```

```
@usage
(namespaced? {:a/b "A"})
```

```
@example
(namespaced? {:a "A"})
=>
false
```

```
@example
(namespaced? {:a/b "A"})
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn namespaced?
  [n]
  (-> n get-namespace some?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [namespaced?]]))

(map.api/namespaced? ...)
(namespaced?         ...)
```

</details>

---

### nonempty?

```
@param (map) n
```

```
@usage
(nonempty? {})
```

```
@example
(nonempty? {})
=>
false
```

```
@example
(nonempty? {:a "A"})
=>
true
```

```
@example
(nonempty? [:a])
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonempty?
  [n]
  (and (-> n map?)
       (-> n empty? not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [nonempty?]]))

(map.api/nonempty? ...)
(nonempty?         ...)
```

</details>

---

### rekey-item

```
@param (map) n
@param (*) from
@param (*) to
```

```
@usage
(rekey-item {:a "A"} :a :b)
```

```
@example
(rekey-item {:a "A"} :a :b)
=>
{:b "A"}
```

```
@example
(rekey-item {:a "A"} :c :d)
=>
{:a "A"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn rekey-item
  [n from to]
  (if (contains? n from)
      (dissoc (assoc n to (get n from)) from)
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [rekey-item]]))

(map.api/rekey-item ...)
(rekey-item         ...)
```

</details>

---

### rekey-items

```
@param (map) n
@param (list of * pairs) key-pairs
```

```
@usage
(rekey-items {:a "A" :b "B"} :a :x :b :y)
```

```
@example
(rekey-items {:a "A" :b "B"} :a :x :b :y)
=>
{:x "A" :y "B"}
```

```
@example
(rekey-items {:a "A" :b "B"} :c :z)
=>
{:a "A" :b "B"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn rekey-items
  [n & key-pairs]
  (letfn [(f [n from to] (if (contains? n from)
                             (dissoc (assoc n to (get n from)) from)
                             (return n)))]
         (reduce-pairs f n key-pairs)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [rekey-items]]))

(map.api/rekey-items ...)
(rekey-items         ...)
```

</details>

---

### remove-keys

```
@param (map) n
@param (vector) keys
```

```
@usage
(remove-keys {:a "A" :b "B"} [:a])
```

```
@example
(remove-keys {:a "A" :b "B" :c "C"} [:a :c])
=>
{:b "B"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-keys
  [n keys]
  (reduce dissoc n keys))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [remove-keys]]))

(map.api/remove-keys ...)
(remove-keys         ...)
```

</details>

---

### remove-keys-by

```
@param (map) n
@param (function) r-f
```

```
@usage
(remove-keys-by {:a "A"} #(= % :a))
```

```
@example
(remove-keys-by {:a "A" :b "B" :c "C"} #(= % :a))
=>
{:b "B" :c "C"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-keys-by
  [n r-f]
  (letfn [(f [%1 %2 %3]
             (if (r-f    %2)
                 (return %1)
                 (assoc  %1 %2 %3)))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [remove-keys-by]]))

(map.api/remove-keys-by ...)
(remove-keys-by         ...)
```

</details>

---

### remove-namespace

```
@param (map) n
```

```
@usage
(remove-namespace {:a/b "A"})
```

```
@example
(remove-namespace {:a/b "A"})
=>
{:a "A"}
```

```
@example
(remove-namespace {"a/b" "A"})
=>
{"a" "A"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-namespace
  [n]
  (letfn [(f [n item-key item-value]
             (cond (string?  item-key) (assoc n (-> item-key keyword name)         item-value)
                   (keyword? item-key) (assoc n (-> item-key         name keyword) item-value)
                   :return n))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [remove-namespace]]))

(map.api/remove-namespace ...)
(remove-namespace         ...)
```

</details>

---

### remove-values

```
@param (map) n
@param (vector) values
```

```
@usage
(remove-values {:a "A"} ["A"])
```

```
@example
(remove-values {:a "A" :b "B" :c "C"} ["A" "B"])
=>
{:c "C"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-values
  [n values])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [remove-values]]))

(map.api/remove-values ...)
(remove-values         ...)
```

</details>

---

### remove-values-by

```
@param (map) n
@param (vector) r-f
```

```
@example
(remove-values-by {:a "A"} #(= % "A"))
  ;
@example
(remove-values-by {:a "A" :b "B" :c "C"} #(= % "A"))
=>
{:b "B" :c "C"}
```

```
@example
(remove-values-by {:a "A" :b "B" :c "C"} #(= % "A"))
=>
{:b "B" :c "C"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-values-by
  [n r-f]
  (letfn [(f [%1 %2 %3]
             (if (r-f    %3)
                 (return %1)
                 (assoc  %1 %2 %3)))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [remove-values-by]]))

(map.api/remove-values-by ...)
(remove-values-by         ...)
```

</details>

---

### reversed-merge

```
@param (list of maps) xyz
```

```
@usage
(reversed-merge {:a "A"} {:a "B"})
```

```
@example
(reversed-merge {:a "A"} {:a "B"})
=>
{:a "A"}
```

```
@example
(reversed-merge {:a "A"} {:a "B"} {:a "C"})
=>
{:a "A"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn reversed-merge
  [& xyz]
  (apply merge (reverse xyz)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [reversed-merge]]))

(map.api/reversed-merge ...)
(reversed-merge         ...)
```

</details>

---

### swap

```
@param (map) n
```

```
@usage
(swap {:a "A" :b "B"})
```

```
@example
(swap {:a "A" :b "B"})
=>
{"A" :a "B" :b}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn swap
  [n]
  (zipmap (vals n)
          (keys n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [swap]]))

(map.api/swap ...)
(swap         ...)
```

</details>

---

### to-vector

```
@param (map) n
```

```
@usage
(to-vector {:a "A" b "B"})
```

```
@example
(to-vector {:a "A" b "B" :c "C"})
=>
["A" "B" "C"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn to-vector
  [n]
  (letfn [(f [%1 _ %3] (conj %1 %3))]
         (reduce-kv f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [to-vector]]))

(map.api/to-vector ...)
(to-vector         ...)
```

</details>

---

### toggle

```
@param (map) n
@param (*) key
@param (*) value
```

```
@usage
(toggle {:a "A"} :a "A")
```

```
@example
(toggle {} :a "A")
=>
{:a "A"}
```

```
@example
(toggle {:a "A"} :a "A")
=>
{}
```

```
@example
(toggle {:a "B"} :a "A")
=>
{}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn toggle
  [n key value]
  (if-let [_ (get n key)]
          (dissoc n key)
          (assoc  n key value)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [toggle]]))

(map.api/toggle ...)
(toggle         ...)
```

</details>

---

### toggle-in

```
@param (map) n
@param (vector) value-path
@param (*) value
```

```
@usage
(toggle-in {:a {:b "B"}} [:a :b] "B")
```

```
@example
(toggle-in {} [:a :b] "B")
=>
{:a {:b "B"}}
```

```
@example
(toggle-in {:a "A"} [:a :b] "B")
=>
{:a {:b "B"}}
```

```
@example
(toggle-in {:a {:b "B"}} [:a :B] "B")
=>
{}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn toggle-in
  [n value-path value]
  (if-let [_ (get-in n value-path)]
          (dissoc-in n value-path)
          (assoc-in  n value-path value)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [toggle-in]]))

(map.api/toggle-in ...)
(toggle-in         ...)
```

</details>

---

### update-in-some

```
@description
Update-in the n map if the value is something.
```

```
@param (map) n
@param (vector) value-path
@param (function) update-f
@param (*) value
```

```
@usage
(update-in-some {:a []} [:a] conj :b)
```

```
@example
(update-in-some {:a {:b [:c :d]}} [:a :b] conj :e)
=>
{:a {:b [:c :d :e]}}
```

```
@example
(update-in-some {:a {:b [:c :d]}} [:a :b] conj nil)
=>
{:a {:b [:c :d]}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn update-in-some
  [n value-path update-f value]
  (if (some?                           value)
      (update-in n value-path update-f value)
      (return    n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [update-in-some]]))

(map.api/update-in-some ...)
(update-in-some         ...)
```

</details>

---

### update-some

```
@description
Update the n map if the value is something.
```

```
@param (map) n
@param (*) key
@param (function) f
@param (*) value
```

```
@usage
(update-some {:a []} :a conj :b)
```

```
@example
(update-some {:a [:b :c]} :a conj :d)
=>
{:a [:b :c :d]}
```

```
@example
(update-some {:a [:b :c]} :a conj nil)
=>
{:a [:b :c]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn update-some
  [n key f value]
  (if (some?          value)
      (update n key f value)
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [update-some]]))

(map.api/update-some ...)
(update-some         ...)
```

</details>

---

### values-equal?

```
@param (map) n
@param (vector) a-path
@param (vector) b-path
```

```
@usage
(values-equal? {:a "X" :b "X"} [:a] [:b])
```

```
@example
(values-equal? {:a {:b "X"}
                :c {:d "X"}}
               [:a :b] [:c :d])
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn values-equal?
  [n a-path b-path]
  (= (get-in n a-path)
     (get-in n b-path)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [map.api :refer [values-equal?]]))

(map.api/values-equal? ...)
(values-equal?         ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

