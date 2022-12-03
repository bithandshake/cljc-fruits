
# <strong>hash.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/hash/api.cljc

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
  #?(:clj (-> n (mac/hash {:key secret-key :alg :hmac+sha256})
                (codecs/bytes->hex))))
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
