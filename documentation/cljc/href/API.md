
# href.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > href.api

### Index

- [email-address](#email-address)

- [google-maps-address](#google-maps-address)

- [https-address](#https-address)

- [phone-number](#phone-number)

### email-address

```
@param (string) email-address
```

```
@usage
(email-address "Hello@my-site.com")
```

```
@example
(email-address "Hello@my-site.com")
=>
"mailto:hello@my-site.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn email-address
  ([email-address]
   (str "mailto:" (string/to-lowercase email-address)))

  ([email-address subject]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" subject))

  ([email-address subject body]
   (str "mailto:"   (string/to-lowercase email-address)
        "?subject=" subject
        "&body="    body)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [href.api :refer [email-address]]))

(href.api/email-address ...)
(email-address          ...)
```

</details>

---

### google-maps-address

```
@param (string) address
```

```
@usage
(google-maps-address "My City, My Address street 42.")
```

```
@example
(google-maps-address "My City, My Address street 42.")
=>
"https://www.google.com/maps/search/?api=1&query=My%20City,%20My%20Address%20street%2042."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn google-maps-address
  [address]
  (str "https://www.google.com/maps/search/?api=1&query=" (string/replace-part address " " "%20")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [href.api :refer [google-maps-address]]))

(href.api/google-maps-address ...)
(google-maps-address          ...)
```

</details>

---

### https-address

```
@param (string) address
```

```
@usage
(https-address "my-website.com")
```

```
@example
(https-address "my-website.com")
=>
"https://my-website.com"
```

```
@example
(https-address "http://my-website.com")
=>
"https://my-website.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn https-address
  [address]
  (as-> address % (string/after-first-occurence % "://" {:return? true})
                  (str "https://"%)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [href.api :refer [https-address]]))

(href.api/https-address ...)
(https-address          ...)
```

</details>

---

### phone-number

```
@param (integer or string) phone-number
```

```
@usage
(phone-number "+3630 / 123 - 4567")
```

```
@example
(phone-number "+3630 / 123 - 4567")
=>
"tel:+36301234567"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn phone-number
  [phone-number]
  (if (-> phone-number str string/nonblank?)
      (str "tel:" (string/filter-characters phone-number ["+" "1" "2" "3" "4" "5" "6" "7" "8" "9" "0"]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [href.api :refer [phone-number]]))

(href.api/phone-number ...)
(phone-number          ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

