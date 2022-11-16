
# <strong>hash.api</strong> namespace
<p>Documentation of the <strong>hash/api.cljc</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > hash.api



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
(ns my-namespace (:require [hash.api :as hash :refer [hmac-sha256]]))

(hash/hmac-sha256 ...)
(hmac-sha256      ...)
```

</details>
