
# <strong>normalize.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/normalize/api.cljc

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
(clean-text "1+2" "+")
=>
"1+2"
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
