
# loop.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > loop.api

### Index

- [<-walk](#-walk)

- [apply-pairs](#apply-pairs)

- [do-indexed](#do-indexed)

- [do-while](#do-while)

- [reduce-indexed](#reduce-indexed)

- [reduce-kv-indexed](#reduce-kv-indexed)

- [reduce-pairs](#reduce-pairs)

- [reduce-range](#reduce-range)

- [some-indexed](#some-indexed)

### <-walk

```
@description
Takes the 'n' as initial value and iterates over the list of functions.
Every function takes the previous function's result as an only argument.
```

```
@param (*) n
@param (list of functions) fs
```

```
@usage
(<-walk {...}
        (fn [%] %)
        (fn [%] %))
```

```
@example
(<-walk {:a "A"}
        (fn [%] (merge {:b "B"} %))
        (fn [%] (merge {:c "C"} %)))
=>
{:a "A" :b "B" :c "C"}
```

```
@example
(<-walk [:a]
        (fn [%] (conj % :b))
        (fn [%] (conj % :c)))
=>
[:a :b :c]
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn <-walk
  [n & fs]
  (letfn [(f [result f] (f result))]
         (reduce f n fs)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [<-walk]]))

(loop.api/<-walk ...)
(<-walk          ...)
```

</details>

---

### apply-pairs

```
@description
Iterates over the given parameter pairs (pair by pair) and passes them
to the given 'f' function with the result of the previous iteration.
```

```
@param (function) f
@param (map) initial
@param (list of * pairs) pairs
```

```
@usage
(defn my-f [n k v] ...)
(apply-pairs my-f {} :a "A" :b "B")
```

```
@example
(defn my-f [n k v] (assoc n k v))
(apply-pairs my-f {} :a "A" :b "B")
=>
{:a "A" :b "B"}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn apply-pairs
  [f initial & pairs]
  (reduce-pairs f initial pairs))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [apply-pairs]]))

(loop.api/apply-pairs ...)
(apply-pairs          ...)
```

</details>

---

### do-indexed

```
@param (function) do-f
@param (collection) coll
```

```
@usage
(do-indexed (fn [dex x]) [...])
```

```
@usage
(do-indexed (fn [dex x] (println x "is the" dex "th item of the collection"))
            [:a :b :c :d :e])
```

<details>
<summary>Source code</summary>

```
(defn do-indexed
  [do-f coll]
  (letfn [(f [dex x]
             (do-f dex x)
             (inc  dex))]
         (reduce f 0 coll)
         (-> nil)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [do-indexed]]))

(loop.api/do-indexed ...)
(do-indexed          ...)
```

</details>

---

### do-while

```
@param (function) f
@param (*) n
The initial parameter of the 'f' function.
@param (function) test-f
If the 'test-f' functions returns true the iteration stops.
```

```
@example
(do-while (fn [{:keys [my-numbers x] :as n}]
              (if (vector/contains-item? my-numbers x)
                  (assoc  n :x (inc x))
                  (update n :my-numbers vector/conj-item x)))
          {:my-numbers [0 1 2 4]
           :x 0}
          (fn [%] (= (count (:my-numbers %1)) 5)))
=>
{:my-numbers [0 1 2 4 3] :x 3}
```

```
@example
(do-while #(inc %)
           0
          #(> % 3))
=>
4
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn do-while
  [f n test-f]
  (let [result (f n)]
       (if (-> result test-f)
           (-> result)
           (do-while f result test-f))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [do-while]]))

(loop.api/do-while ...)
(do-while          ...)
```

</details>

---

### reduce-indexed

```
@description
The 'f' function gets the current item's index as its second parameter.
```

```
@param (function) f
@param (*) initial
@param (collection) coll
```

```
@usage
(reduce-indexed (fn [result dex x]) nil [:a :b])
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-indexed
  [f initial coll]
  (reduce-kv f initial coll))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [reduce-indexed]]))

(loop.api/reduce-indexed ...)
(reduce-indexed          ...)
```

</details>

---

### reduce-kv-indexed

```
@description
The 'f' function gets the current item's index as its second parameter.
```

```
@param (function) f
@param (*) initial
@param (map) map
```

```
@usage
(reduce-kv-indexed (fn [result dex k v]) nil {})
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-kv-indexed
  [f initial map]
  (letfn [(fi [[result dex] k v]
              [(f result dex k v)
               (inc dex)])]
         (first (reduce-kv fi [initial 0] map))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [reduce-kv-indexed]]))

(loop.api/reduce-kv-indexed ...)
(reduce-kv-indexed          ...)
```

</details>

---

### reduce-pairs

```
@description
Iterates over (pair by pair) the given collection (must contain even number of items)
and passes them to the given 'f' function with the result of the previous iteration.
```

```
@param (function) f
@param (map) initial
@param (collection with even number of items) pairs
```

```
@usage
(defn my-f [n k v] ...)
(reduce-pairs my-f {} [:a "A" :b "B"])
```

```
@example
(defn my-f [n k v] (assoc n k v))
(reduce-pairs my-f {} [:a "A" :b "B"])
=>
{:a "A" :b "B"}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-pairs
  [f initial pairs]
  (let [pairs-count (count pairs)]
       (letfn [(fi [result lap] (let [cursor (* lap 2)]
                                     (if (> cursor pairs-count) result
                                         (let [a (nth pairs (- cursor 2))
                                               b (nth pairs (- cursor 1))]
                                              (fi (f result a b)
                                                  (inc lap))))))]

              (cond (-> pairs-count (< 2)) initial
                    (-> pairs-count odd?)  initial
                    :recursion (fi initial 1)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [reduce-pairs]]))

(loop.api/reduce-pairs ...)
(reduce-pairs          ...)
```

</details>

---

### reduce-range

```
@param (function) f
@param (*) initial
@param (integer)(opt) start
@param (integer) end
```

```
@usage
(reduce-range (fn [result x]) nil 10)
```

```
@usage
(reduce-range (fn [result x]) nil 10 20)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-range
  ([f initial end]
   (reduce f initial (range end)))

  ([f initial start end]
   (reduce f initial (range start end))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [reduce-range]]))

(loop.api/reduce-range ...)
(reduce-range          ...)
```

</details>

---

### some-indexed

```
@param (function) test-f
@param (collection) coll
```

```
@usage
(some-indexed (fn [dex x]) [...])
```

```
@example
(some-indexed #(if (= 3    %1)
                   (return %2))
               [:a :b :c :d :e])
=>
:d
```

```
@example
(some-indexed #(if (= :d   %2)
                   (return %1))
               [:a :b :c :d :e])
=>
3
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn some-indexed
  [test-f coll]
  (letfn [(fi [test-f coll dex]
              (if-let [result (test-f dex (get coll dex))]
                      (-> result)
                      (when-not (= dex (-> coll count dec))
                                (fi test-f coll (inc dex)))))]
         (fi test-f coll 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :refer [some-indexed]]))

(loop.api/some-indexed ...)
(some-indexed          ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

