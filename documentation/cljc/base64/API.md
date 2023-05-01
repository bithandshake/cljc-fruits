
# base64.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > base64.api

### Index

- [decode](#decode)

- [encode](#encode)

- [encoded?](#encoded)

- [save-as](#save-as)

- [to-blob](#to-blob)

- [to-mime-type](#to-mime-type)

- [wrap](#wrap)

- [wrapped?](#wrapped)

### decode

```
@warning
The function does not create the directory path of the output
if it does not exist!
```

```
@description
Reads the file from the source-filepath decodes the file's content to byte array
and writes the decoded content to the destination-filepath.
```

```
@param (string) source-filepath
@param (string) destination-filepath
```

```
@usage
(decode "my-document.b64" "my-document.pdf")
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn decode
  [source-filepath destination-filepath]
  #?(:clj (when-let [base64-body (slurp source-filepath)]
                    (let [base64 (str "data:decoder/b64                         (clojure.java.io/copy (convert/to-byte-array base64)
                                               (java.io.File. destination-filepath))
                         (-> destination-filepath slurp boolean)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [decode]]))

(base64.api/decode ...)
(decode            ...)
```

</details>

---

### encode

```
@warning
The function does not create the directory path of the output
if it does not exist!
```

```
@description
Reads the file from the source-filepath encodes the file's content to base64
and writes the encoded content to the destination-filepath.
```

```
@param (string) source-filepath
@param (string) destination-filepath
```

```
@usage
(encode "my-document.pdf" "my-document.b64")
```

```
@example
(encode "my-document.pdf" "my-document.b64")
=>
"data:application/pdf;base64,..."
```

```
@return (string)
Returns with the encoded content.
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
(ns my-namespace (:require [base64.api :refer [encode]]))

(base64.api/encode ...)
(encode            ...)
```

</details>

---

### encoded?

```
@param (*) n
```

```
@usage
(encoded? "...")
```

```
@return (boolean)
Returns with true if the given value is base64 encoded.
```

<details>
<summary>Source code</summary>

```
(defn encoded?
  [n])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [encoded?]]))

(base64.api/encoded? ...)
(encoded?            ...)
```

</details>

---

### save-as

```
@warning
The function does not create the directory path of the output
if it does not exist!
```

```
@description
Decodes the base64 to byte array and writes the decoded content to the destination-filepath.
```

```
@param (string) base64
@param (string) destination-filepath
```

```
@usage
(save-as "data:application/pdf;base64,..." "my-document.pdf")
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn save-as
  [base64 destination-filepath]
  #?(:clj (do (clojure.java.io/copy (convert/to-byte-array base64)
                                    (java.io.File. destination-filepath))
              (-> destination-filepath slurp boolean))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [save-as]]))

(base64.api/save-as ...)
(save-as            ...)
```

</details>

---

### to-blob

```
@param (string) base64
```

```
@usage
(to-mime-blob "data:application/pdf;base64,...")
```

```
@return (object)
```

<details>
<summary>Source code</summary>

```
(defn to-blob
  [base64]
  #?(:cljs (let [binary-string (.atob          js/window base64)
                 binary-length (.-length       binary-string)
                 integer-array (js/Uint8Array. binary-length)
                 mime-type     (to-mime-type   base64)]
                (doseq [i (range binary-length)]
                       (aset integer-array i (.charCodeAt binary-string i)))
                (js/Blob. (clj->js [integer-array])
                          (clj->js {:type mime-type})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [to-blob]]))

(base64.api/to-blob ...)
(to-blob            ...)
```

</details>

---

### to-mime-type

```
@param (string) base64
```

```
@usage
(to-mime-type "data:application/pdf;base64,...")
```

```
@example
(to-mime-type "data:application/pdf;base64,...")
=>
"application/pdf"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-mime-type
  [base64]
  (-> base64 (string/after-first-occurence "data:" {:return? false})
             (string/after-first-occurence ";"     {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [to-mime-type]]))

(base64.api/to-mime-type ...)
(to-mime-type            ...)
```

</details>

---

### wrap

```
@param (string) base64
@param (string) mime-type
```

```
@usage
(wrap "..." "application/pdf")
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
  (if (string/nonblank? base64)
      (str "data:"mime-type";base64,"base64)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [wrap]]))

(base64.api/wrap ...)
(wrap            ...)
```

</details>

---

### wrapped?

```
@param (*) n
```

```
@usage
(wrapped? "data:application/pdf;base64,...")
```

```
@example
(wrapped? "data:application/pdf;base64,...")
=>
true
```

```
@example
(wrapped? "...")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn wrapped?
  [n]
  (string/starts-with? n "data:"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [base64.api :refer [wrapped?]]))

(base64.api/wrapped? ...)
(wrapped?            ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

