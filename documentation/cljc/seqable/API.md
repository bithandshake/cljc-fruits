
# <strong>seqable.api</strong> namespace
<p>Documentation of the <strong>seqable/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > seqable.api</strong>



### nonempty?

```
@param (*) n
```

```
@example
(nonempty? 420)
=>
false
```

```
@example
(nonempty? "420")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonempty?
  [n]
  (and (-> n seqable?)
       (-> n empty? not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [seqable.api :as seqable :refer [nonempty?]]))

(seqable/nonempty? ...)
(nonempty?         ...)
```

</details>

---

### nonseqable?

```
@param (*) n
```

```
@example
(nonseqable? 420)
=>
true
```

```
@example
(nonseqable? "420")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonseqable?
  [n]
  (-> n seqable? not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [seqable.api :as seqable :refer [nonseqable?]]))

(seqable/nonseqable? ...)
(nonseqable?         ...)
```

</details>
