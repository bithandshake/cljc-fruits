
# html.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > html.api

### Index

- [blank?](#blank)

- [nonblank?](#nonblank)

### blank?

```
@param (string) n
```

```
@usage
(blank? "<p> </p><p>\n</p>")
```

```
@example
(blank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
=>
false
```

```
@example
(blank? "<p> </p><p>\n</p>")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn blank?
  [n]
  (-> n (string/remove-part #"<.*>")
        (string/remove-part #"</.*>")
        (string/remove-part #"<.*/>")
        (string/remove-part #" ")
        (string/remove-part #"\r")
        (string/remove-part #"\t")
        (string/remove-part #"\n")
        (empty?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [html.api :refer [blank?]]))

(html.api/blank? ...)
(blank?          ...)
```

</details>

---

### nonblank?

```
@param (string) n
```

```
@usage
(nonblank? "<p> </p><p>\n</p>")
```

```
@example
(nonblank? "<p>Paragraph #1</p><p>Paragraph #2</p>")
=>
true
```

```
@example
(nonblank? "<p> </p><p>\n</p>")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonblank?
  [n]
  (-> n blank? not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [html.api :refer [nonblank?]]))

(html.api/nonblank? ...)
(nonblank?          ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

