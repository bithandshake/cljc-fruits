
# <strong>base64.api</strong> namespace
<p>Documentation of the <strong>base64/api.cljc</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > base64.api



### encode

```
@param (string) source-filepath
@param (string) destination-filepath
```

```
@usage
(encode "my-document.pdf" "my-document.b64")
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn encode
  [source-filepath destination-filepath]
  #?(:clj (when source-filepath (with-open [i (io/input-stream       source-filepath)
                                            o (io/output-stream destination-filepath)]
                                           (base64/encoding-transfer i o))
                                (slurp destination-filepath))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :as base64 :refer [encode]]))

(base64/encode ...)
(encode        ...)
```

</details>

---

### to-blob

```
@param (string) base64
@param (string) mime-type
```

```
@usage
(to-blob "..." "application/pdf")
```

```
@return (object)
```

<details>
<summary>Source code</summary>

```
(defn to-blob
  [base64 mime-type]
  #?(:cljs (let [binary-string (.atob js/window base64)
                 binary-length (.-length binary-string)
                 integer-array (js/Uint8Array. binary-length)]
                (doseq [i (range binary-length)]
                       (aset integer-array i (.charCodeAt binary-string i)))
                (js/Blob. (clj->js [integer-array])
                          (clj->js {:type mime-type})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :as base64 :refer [to-blob]]))

(base64/to-blob ...)
(to-blob        ...)
```

</details>

---

### wrap

```
@param (string) base64
@param (string) mime-type
```

```
@example
(wrap "..." "application/pdf")
=>
"data:application/pdf;base64,..."
```

```
@example
(wrap "" "application/pdf")
=>
nil
```

```
@example
(wrap nil "application/pdf")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn wrap
  [base64 mime-type]
  (if (string/nonempty? base64)
      (str "data:"mime-type";base64,"base64)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :as base64 :refer [wrap]]))

(base64/wrap ...)
(wrap        ...)
```

</details>