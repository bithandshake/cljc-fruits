
# integer.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > integer.api

### Index

- [to-keyword](#to-keyword)

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

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

