
# hash.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > hash.api

### Index

- [hmac-sha256](#hmac-sha256)

### hmac-sha256

```
@param (string) n
@param (string) secret-key
```

```
@usage
(hmac-sha256 "My text" "my-secret-key")
```

```
@return (hex string)
```

<details>
<summary>Source code</summary>

```
(defn hmac-sha256
  [n secret-key]
  #?(:clj (-> n str (mac/hash {:key secret-key :alg :hmac+sha256}) codecs/bytes->hex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hash.api :refer [hmac-sha256]]))

(hash.api/hmac-sha256 ...)
(hmac-sha256          ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

