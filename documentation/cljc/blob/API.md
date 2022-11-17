
# <strong>blob.api</strong> namespace
<p>Documentation of the <strong>blob/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > blob.api</strong>



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
(ns my-namespace (:require [blob.api :as blob :refer [to-object-url]]))

(blob/to-object-url ...)
(to-object-url      ...)
```

</details>
