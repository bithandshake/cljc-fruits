
# blob.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > blob.api

### Index

- [to-object-url](#to-object-url)

### to-object-url

```
@param (object) blob
```

```
@usage
(to-object-url ...)
```

```
@return (object)
```

<details>
<summary>Source code</summary>

```
(defn to-object-url
  [blob]
  #?(:cljs (.createObjectURL js/URL blob)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [blob.api :refer [to-object-url]]))

(blob.api/to-object-url ...)
(to-object-url          ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

