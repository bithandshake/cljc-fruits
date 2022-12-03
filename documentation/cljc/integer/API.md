
# <strong>integer.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/integer/api.cljc

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
(ns my-namespace (:require [integer.api :refer [to-keyword]]))

(integer.api/to-keyword ...)
(to-keyword             ...)
```

</details>
