
# namespace.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > namespace.api

### Index

- [detect](#detect)

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

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

