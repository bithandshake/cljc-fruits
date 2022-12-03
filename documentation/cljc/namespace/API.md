
# <strong>namespace.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/namespace/api.cljc

### detect

```
@param (namespaced keyword) sample
```

```
@usage
(detect ::this)
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn detect
  [sample]
  (keyword/get-namespace sample))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [namespace.api :refer [detect]]))

(namespace.api/detect ...)
(detect               ...)
```

</details>
