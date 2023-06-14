
# function.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > function.api

### Index

- [->js](#-js)

- [invoke](#invoke)

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
@warning
It's really sad, but the compiler cannot resolve functions like this :(
This function will be commented until the problem is being solved.
```

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
;  #?(:clj  (apply (resolve (symbol function-name)) args)))
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

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

