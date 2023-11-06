
### bson.api

Functional documentation of the bson.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > bson.api

### Index

- [undot-key](#undot-key)

- [undot-keys](#undot-keys)

---

### undot-key

```
@description
Replaces dot characters with hyphens in the given key.
Dot characters are not allowed to presence in BSON keys:
https://www.mongodb.com/docs/manual/core/document/#dot-notation
```

```
@param (*) n
```

```
@usage
(undot-key :my.keyword)
```

```
@usage
(undot-key "my.string")
```

```
@example
(undot-key :my.keyword)
=>
:my-keyword
```

```
@example
(undot-key "my.string")
=>
"my-string"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn undot-key
  [n]
  (cond (string?  n) (string/replace-part n "." "-")
        (keyword? n) (-> n keyword/to-string undot-key keyword)
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bson.api :refer [undot-key]]))

(bson.api/undot-key ...)
(undot-key          ...)
```

</details>

---

### undot-keys

```
@description
Recursively replaces dot characters with hyphens in keys found in the given data.
Dot characters are not allowed to presence in BSON keys:
https://www.mongodb.com/docs/manual/core/document/#dot-notation
```

```
@param (*) n
```

```
@usage
(undot-keys {:my.keyword :my-value})
```

```
@usage
(undot-keys {"my.string" "My value"})
```

```
@example
(undot-keys {:my.keyword :my-value})
=>
{:my-keyword :my-value}
```

```
@example
(undot-keys {"my.string" "My value"})
=>
{"my-string" "My value"}
```

```
@example
(undot-keys [{"my.string" "My value"}
             {:my.keyword :my-value}])
=>
[{"my-string" "My value"}
 {:my-keyword :my-value}]
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn undot-keys
  [n]
  (cond (map?    n) (map/->>keys    n undot-keys)
        (vector? n) (vector/->items n undot-keys)
        :return     (undot-key      n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [bson.api :refer [undot-keys]]))

(bson.api/undot-keys ...)
(undot-keys          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

