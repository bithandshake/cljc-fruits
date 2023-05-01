
# loop.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > loop.api

### Index

- [<-walk](#-walk)

- [do-indexed](#do-indexed)

- [do-while](#do-while)

- [reduce-indexed](#reduce-indexed)

- [reduce-kv-indexed](#reduce-kv-indexed)

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
         (return nil)))
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
If the 'test-f' functions returns with true the iteration stops.
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
       (if (test-f     result)
           (return     result)
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
                      (return result)
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

