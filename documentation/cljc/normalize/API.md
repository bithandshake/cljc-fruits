
# normalize.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > normalize.api

### Index

- [clean-text](#clean-text)

### clean-text

```
@param (*) n
@param (string)(opt) exceptions
Default: "-"
```

```
@usage
(clean-text "a b  c")
```

```
@example
(clean-text "a b  c")
=>
"a-b-c"
```

```
@example
(clean-text "aá AÁ")
=>
"aa-AA"
```

```
@example
(clean-text "1+2")
=>
"12"
```

```
@example
(clean-text "1+2-4" "+-")
=>
"1+2-4"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn clean-text
  ([n]
   (clean-text n "-"))

  ([n exceptions]
   (-> n (str)
         (deaccent)
         (cut-special-chars exceptions)
         (replace-white-chars)
         (string/lower-case))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [normalize.api :refer [clean-text]]))

(normalize.api/clean-text ...)
(clean-text               ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

