
# <strong>eql.api</strong> namespace
<p>Documentation of the <strong>eql/api.cljc</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > eql.api



### append-to-query

```
@param (nil vector) query
@param (keyword, map, string or vector) query-parts
```

```
@example
(append-to-query nil :all-users)
=>
[:all-users]
```

```
@example
(append-to-query [] :all-users)
=>
[:all-users]
```

```
@example
(append-to-query [:all-users]
                 [:directory/id :my-directory])
=>
[:all-users [:directory/id :my-directory]]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn append-to-query
  [query & query-parts]
  (cond (vector?  query) (vec (concat query   query-parts))
        (nil?     query) (vec (concat []      query-parts))
        :return          (vec (concat [query] query-parts))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [append-to-query]]))

(eql/append-to-query ...)
(append-to-query     ...)
```

</details>

---

### document-entity->id

```
@param (vector) document-entity
```

```
@example
(document-entity->id [:directory/id "my-directory"])
=>
"my-directory"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn document-entity->id
  [document-entity]
  (second document-entity))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [document-entity->id]]))

(eql/document-entity->id ...)
(document-entity->id     ...)
```

</details>

---

### document-entity?

```
@param (*) n
```

```
@example
(document-entity? [:directory/id "my-directory"])
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn document-entity?
  [n]
  (boolean (and      (vector? n)
                (= 2 (count   n))
                (keyword? (first  n))
                (string?  (second n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [document-entity?]]))

(eql/document-entity? ...)
(document-entity?     ...)
```

</details>

---

### document-link->id

```
@param (map) document-link
```

```
@example
(document-link->id {:directory/id "my-directory"})
=>
"my-directory"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn document-link->id
  [document-link]
  (-> document-link vals first))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [document-link->id]]))

(eql/document-link->id ...)
(document-link->id     ...)
```

</details>

---

### document-link?

```
@param (*) n
```

```
@example
(document-link? {:directory/id "my-directory"})
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn document-link?
  [n]
  (and      (map?  n)
       (= 1 (count n))
       (-> n keys first keyword?)
       (-> n vals first string?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [document-link?]]))

(eql/document-link? ...)
(document-link?     ...)
```

</details>

---

### id->document-entity

```
@param (string) id
@param (keyword)(opt) namespace
```

```
@example
(id->document-entity "my-directory")
=>
[:id "my-directory"]
```

```
@example
(id->document-entity "my-directory" :directory)
=>
[:directory/id "my-directory"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn id->document-entity
  ([id]           [:id id])
  ([id namespace] [(keyword (name namespace) "id") id]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [id->document-entity]]))

(eql/id->document-entity ...)
(id->document-entity     ...)
```

</details>

---

### id->document-link

```
@param (string) id
@param (keyword)(opt) namespace
```

```
@example
(id->document-link "my-directory")
=>
{:id "my-directory"}
```

```
@example
(id->document-link "my-directory" :directory)
=>
{:directory/id "my-directory"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn id->document-link
  ([id]           {:id id})
  ([id namespace] {(keyword (name namespace) "id") id}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [id->document-link]]))

(eql/id->document-link ...)
(id->document-link     ...)
```

</details>

---

### id->placeholder

```
@param (string) id
```

```
@example
(id->placeholder "my-id")
=>
:>/my-id
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn id->placeholder
  [id]
  (keyword (str ">/" (name id))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [eql.api :as eql :refer [id->placeholder]]))

(eql/id->placeholder ...)
(id->placeholder     ...)
```

</details>
