
# <strong>keyword.api</strong> namespace
<p>Documentation of the <strong>keyword/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > keyword.api</strong>



### add-namespace

```
@param (keyword)(opt) namespace
@param (keyword) n
```

```
@usage
(add-namespace :a :b)
```

```
@example
(add-namespace :a :b)
=>
:a/b
```

```
@example
(add-namespace :a)
=>
:ko4983l3-i8790-j93l3-lk8385u591o2/a
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn add-namespace
  ([n]
   (keyword (random/generate-uuid) (name n)))

  ([namespace n]
   (keyword (name namespace) (name n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [add-namespace]]))

(keyword/add-namespace ...)
(add-namespace         ...)
```

</details>

---

### append

```
@param (keyword) n
@param (keyword) x
@param (string)(opt) separator
```

```
@example
(append :a :b)
=>
:ab
```

```
@example
(append :a/b :c)
=>
:a/bc
```

```
@example
(append :a/b :c "--")
=>
:a/b--c
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn append
  ([n x]
   (if-let [namespace (namespace n)]
           (keyword namespace (str (name n) (name x)))
           (keyword (str (name n) (name x)))))

  ([n x separator]
   (if-let [namespace (namespace n)]
           (keyword namespace (str (name n) separator (name x)))
           (keyword (str (name n) separator (name x))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [append]]))

(keyword/append ...)
(append         ...)
```

</details>

---

### get-name

```
@param (keyword) n
```

```
@usage
(get-name :a)
```

```
@example
(get-name :a)
=>
:a
```

```
@example
(get-name :a/b)
=>
:b
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn get-name
  [n]
  (if (-> n keyword?)
      (-> n name keyword)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [get-name]]))

(keyword/get-name ...)
(get-name         ...)
```

</details>

---

### get-namespace

```
@param (keyword) n
```

```
@usage
(get-namespace :a/b)
```

```
@example
(get-namespace :a)
=>
nil
```

```
@example
(get-namespace :a/b)
=>
:a
```

```
@return (keyword or nil)
```

<details>
<summary>Source code</summary>

```
(defn get-namespace
  [n]
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (keyword namespace))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [get-namespace]]))

(keyword/get-namespace ...)
(get-namespace         ...)
```

</details>

---

### join

```
@param (keywords and/or strings) abc
```

```
@usage
(join :a :b "c")
```

```
@example
(join :a :b "c" :d)
=>
:abcd
```

```
@example
(join :x/a :x/b "c" :d)
=>
:abcd
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn join
  [& abc]
  (letfn [(f [result x] (if (keyword? x) (str result (name x))
                                         (str result x)))]
         (keyword (reduce f abc))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [join]]))

(keyword/join ...)
(join         ...)
```

</details>

---

### namespaced?

```
@param (keyword) n
```

```
@usage
(namespaced? :a/b)
```

```
@example
(namespaced? :a)
=>
false
```

```
@example
(namespaced? :a/b)
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
  (and (keyword?  n)
       (namespace n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [namespaced?]]))

(keyword/namespaced? ...)
(namespaced?         ...)
```

</details>

---

### to-string

```
@param (keyword) n
```

```
@usage
(to-string :a/b)
```

```
@example
(to-string :a/b)
=>
"a/b"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-string
  [n]
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (str namespace "/" (name n))
              (name n))
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [keyword.api :as keyword :refer [to-string]]))

(keyword/to-string ...)
(to-string         ...)
```

</details>
