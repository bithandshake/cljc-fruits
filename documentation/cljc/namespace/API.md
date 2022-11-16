
# <strong>namespace.api</strong> namespace
<p>Documentation of the <strong>namespace/api.cljc</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > namespace.api



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
(ns my-namespace (:require [namespace.api :as namespace :refer [detect]]))

(namespace/detect ...)
(detect           ...)
```

</details>
