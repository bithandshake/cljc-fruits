
# <strong>integer.api</strong> namespace
<p>Documentation of the <strong>integer/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > integer.api</strong>



### to-keyword

```
@param (integer) n
```

```
@example
(to-keyword 2)
=>
:2
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn to-keyword
  [n]
  (-> n str keyword))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [integer.api :as integer :refer [to-keyword]]))

(integer/to-keyword ...)
(to-keyword         ...)
```

</details>
