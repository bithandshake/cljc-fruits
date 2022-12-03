
# <strong>geometry.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/geometry/api.cljc

### column-count

```
@param (integer) item-count
@param (px) column-width
@param (integer) max-column-count
@param (px) max-width
```

```
@example
(column-count 13 200 8 1980)
=>
8
```

```
@example
(column-count 13 200 8 1240)
=>
6
```

```
@example
(column-count 2 200 8 1980)
=>
2
```

```
@example
(column-count 0 200 8 1980)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn column-count
  [item-count column-width max-column-count max-width]
  (let [max-columns-fit  (math/floor (/ max-width column-width))
        max-column-count (math/minimum max-column-count max-columns-fit)]
       (math/between! item-count 0 max-column-count)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [geometry.api :refer [column-count]]))

(geometry.api/column-count ...)
(column-count              ...)
```

</details>

---

### columns-width

```
@param (integer) item-count
@param (px) column-width
@param (integer) max-column-count
@param (px) max-width
```

```
@example
(columns-width 13 200 8 1980)
=>
1600
```

```
@example
(columns-width 13 200 8 1240)
=>
1200
```

```
@example
(columns-width 2 200 8 1980)
=>
400
```

```
@example
(columns-width 0 200 8 1980)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn columns-width
  [item-count column-width max-column-count max-width]
  (let [column-count (column-count item-count column-width max-column-count max-width)]
       (* column-width column-count)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [geometry.api :refer [columns-width]]))

(geometry.api/columns-width ...)
(columns-width              ...)
```

</details>
