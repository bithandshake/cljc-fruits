
# <strong>loop.api</strong> namespace
<p>Documentation of the <strong>loop/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > loop.api</strong>



### do-while

<details>
<summary>Source code</summary>

```

```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :as loop :refer [do-while]]))

(loop/do-while)
(do-while)
```

</details>

---

### reduce-indexed

```
@param (function) f
@param (*) initial
@param (collection) coll
```

```
@usage
(reduce-indexed (fn [o dex x]) nil [:a :b])
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-indexed
  [f initial coll]
  (first (reduce (fn [[o dex] x]
                     [(f o dex x)
                      (inc dex)])
                 [initial 0]
                 (param coll))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :as loop :refer [reduce-indexed]]))

(loop/reduce-indexed ...)
(reduce-indexed      ...)
```

</details>

---

### reduce-kv-indexed

```
@param (function) f
@param (*) initial
@param (map) map
```

```
@usage
(reduce-kv-indexed (fn [o dex k v]) nil {})
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn reduce-kv-indexed
  [f initial map]
  (first (reduce-kv (fn [[o dex] k v]
                        [(f o dex k v)
                         (inc dex)])
                    [initial 0]
                    (param map))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :as loop :refer [reduce-kv-indexed]]))

(loop/reduce-kv-indexed ...)
(reduce-kv-indexed      ...)
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
  (letfn [(some-indexed-f [test-f coll dex]
                          (if-let [result (test-f dex (get coll dex))]
                                  (return result)
                                  (when-not (= dex (-> coll count dec))
                                            (some-indexed-f test-f coll (inc dex)))))]
         (some-indexed-f test-f coll 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [loop.api :as loop :refer [some-indexed]]))

(loop/some-indexed ...)
(some-indexed      ...)
```

</details>
