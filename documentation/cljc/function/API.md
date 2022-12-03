
# <strong>function.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/function/api.cljc

### ->js

```
@param (string) var-name
```

```
@example
(->js "vector.api/conj-item")
=>
"vector.api.conj_item"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn ->js
  [var-name]
  #?(:cljs (-> var-name (string/replace-part #"/" ".")
                        (string/replace-part #"-" "_"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [function.api :refer [->js]]))

(function.api/->js ...)
(->js              ...)
```

</details>

---

### invoke

```
@param (string) function-name
@param (list of *) args
```

```
@example
(invoke "vector.api/conj-item" [:a :b] :c)
=>
[:a :b :c]
```

<details>
<summary>Source code</summary>

```
(defn invoke
  [function-name & args])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [function.api :refer [invoke]]))

(function.api/invoke ...)
(invoke              ...)
```

</details>
