
# audit.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > audit.api

### Index

- [email-address-pattern](#email-address-pattern)

- [email-address-valid?](#email-address-valid)

- [generate-password](#generate-password)

- [generate-pin-code](#generate-pin-code)

- [ip-address-pattern](#ip-address-pattern)

- [ip-address-valid?](#ip-address-valid)

- [password-pattern](#password-pattern)

- [password-valid?](#password-valid)

- [phone-number-pattern](#phone-number-pattern)

- [phone-number-valid?](#phone-number-valid)

- [pin-code-pattern](#pin-code-pattern)

- [pin-code-valid?](#pin-code-valid)

### email-address-pattern

```
@description
Returns a regex pattern that matches with valid email addresses.
```

```
@usage
(email-address-pattern)
```

```
@example
(email-address-pattern)
=>
#"[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn email-address-pattern
  []
  (re-pattern "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [email-address-pattern]]))

(audit.api/email-address-pattern)
(email-address-pattern)
```

</details>

---

### email-address-valid?

```
@description
Returns true if the given value is a valid email address.
```

```
@param (*) n
```

```
@usage
(email-address-valid? "user@email.com")
```

```
@example
(email-address-valid? "user@email.com")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn email-address-valid?
  [n]
  (let [pattern (patterns/email-address-pattern)]
       (re-match? (str n) pattern)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [email-address-valid?]]))

(audit.api/email-address-valid? ...)
(email-address-valid?           ...)
```

</details>

---

### generate-password

```
@param (integer)(opt) length
Default: 8
Min: 4
```

```
@usage
(generate-password)
```

```
@example
(generate-password 4)
=>
"Yi4_"
```

```
@example
(generate-password 5)
=>
"YQi4_"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-password
  ([]
   (generate-password 8))

  ([length]
   (let [length (max 4 length)]
        (letfn [(set-f  [from to]    (map char (range (int from) (inc (int to)))))
                (part-f [set length] (reduce-range (fn [%1 _] (str %1 (rand-nth set))) nil length))
                (rem-f  [pos] (if (<= pos (rem length 4)) 1 0))]
               (let [lower-chars (set-f \a \z)
                     upper-chars (set-f \A \Z)
                     digits      (set-f \0 \9)
                     specials    [\.\-\_\!\?\#\*]]
                    (str (part-f upper-chars (+ (math/floor (/ length 4)) (rem-f 1)))
                         (part-f lower-chars (+ (math/floor (/ length 4)) (rem-f 2)))
                         (part-f digits      (+ (math/floor (/ length 4)) (rem-f 3)))
                         (part-f specials    (+ (math/floor (/ length 4)) (rem-f 4)))))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [generate-password]]))

(audit.api/generate-password ...)
(generate-password           ...)
```

</details>

---

### generate-pin-code

```
@param (integer)(opt) length
Default: 4
```

```
@usage
(generate-pin-code)
```

```
@example
(generate-pin-code)
=>
"0420"
```

```
@example
(generate-pin-code 6)
=>
"042069"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-pin-code
  ([]
   (generate-pin-code 4))

  ([length]
   (-> length random/generate-number str)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [generate-pin-code]]))

(audit.api/generate-pin-code ...)
(generate-pin-code           ...)
```

</details>

---

### ip-address-pattern

```
@description
Returns a regex pattern that matches with valid IP address.
```

```
@usage
(ip-address-pattern)
```

```
@example
(ip-address-pattern)
=>
#"[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn ip-address-pattern
  []
  (re-pattern (str "[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [ip-address-pattern]]))

(audit.api/ip-address-pattern)
(ip-address-pattern)
```

</details>

---

### ip-address-valid?

```
@description
Returns true if the given value is a valid IP address.
```

```
@param (*) n
```

```
@usage
(ip-address-valid? "0.0.0.0")
```

```
@example
(ip-address-valid? "0.0.0.0")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn ip-address-valid?
  [n]
  (let [pattern (patterns/ip-address-pattern)]
       (re-match? (str n) pattern)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [ip-address-valid?]]))

(audit.api/ip-address-valid? ...)
(ip-address-valid?           ...)
```

</details>

---

### password-pattern

```
@description
Returns a regex pattern that matches with valid passwords.
Password qualified as valid if ...
... its length is in a certain domain.
... contains at least one uppercase letter.
... contains at least one lowercase letter.
... contains at least one digit.
Accented characters and the following special characters are allowed: .-_!?#*
```

```
@param (integer)(opt) min
Default: 8
@param (integer)(opt) max
Default: 32
```

```
@usage
(password-pattern)
```

```
@usage
(password-pattern 6)
```

```
@usage
(password-pattern 6 16)
```

```
@example
(password-pattern 6 16)
=>
#"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\.\-\_\!\?\#\*]{6,16}$"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn password-pattern
  ([]
   (password-pattern 8 32))

  ([min]
   (password-pattern min 32))

  ([min max]
   (re-pattern (str "^(?=.*[a-ÿ])(?=.*[A-Ÿ])(?=.*\\d)[a-ÿA-Ÿ\\d\\.\\-\\_\\!\\?\\#\\*]{"min","max"}$"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [password-pattern]]))

(audit.api/password-pattern ...)
(password-pattern           ...)
```

</details>

---

### password-valid?

```
@description
Returns true if the given value is a valid password.
Password qualified as valid if ...
... its length is in a certain domain.
... contains at least one uppercase letter.
... contains at least one lowercase letter.
... contains at least one digit.
Accented characters and the following special characters are allowed: .-_!?#*
```

```
@param (*) n
@param (integer)(opt) min
Default: 8
@param (integer)(opt) max
Default: 32
```

```
@usage
(password-valid? "Password1")
```

```
@usage
(password-valid? "Password1" 6)
```

```
@usage
(password-valid? "Password1" 6 16)
```

```
@example
(password-valid? "Password1" 6 16)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn password-valid?
  ([n]
   (password-valid? 8 32))

  ([n min]
   (password-valid? min 32))

  ([n min max]
   (let [pattern (patterns/password-pattern min max)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [password-valid?]]))

(audit.api/password-valid? ...)
(password-valid?           ...)
```

</details>

---

### phone-number-pattern

```
@description
Returns a regex pattern that matches with valid phone numbers.
Phone number qualified as valid if ...
... its length is in a certain domain.
... its first letter is a plus sign.
```

```
@param (integer)(opt) min
Default: 4
@param (integer)(opt) max
Default: 20
```

```
@usage
(phone-number-pattern)
```

```
@usage
(phone-number-pattern 6)
```

```
@usage
(phone-number-pattern 6 24)
```

```
@example
(phone-number-pattern 6 24)
=>
#"\+\d{6,24}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn phone-number-pattern
  ([]
   (phone-number-pattern 4 20))

  ([min]
   (phone-number-pattern min 20))

  ([min max]
   (re-pattern (str "\\+\\d{"min","max"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [phone-number-pattern]]))

(audit.api/phone-number-pattern ...)
(phone-number-pattern           ...)
```

</details>

---

### phone-number-valid?

```
@description
Returns true if the given value is a valid phone number.
Phone number qualified as valid if ...
... its length is in a certain domain.
... its first character is a plus sign.
```

```
@param (*) n
@param (integer)(opt) min
Default: 4
@param (integer)(opt) max
Default: 20
```

```
@usage
(phone-number-valid? "+36420001234")
```

```
@usage
(phone-number-valid? "+36420001234" 6)
```

```
@usage
(phone-number-valid? "+36420001234" 6 24)
```

```
@example
(phone-number-valid? "+36420001234" 6 24)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn phone-number-valid?
  ([n]
   (phone-number-valid? 4 20))

  ([n min]
   (phone-number-valid? min 20))

  ([n min max]
   (let [pattern (patterns/phone-number-pattern min max)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [phone-number-valid?]]))

(audit.api/phone-number-valid? ...)
(phone-number-valid?           ...)
```

</details>

---

### pin-code-pattern

```
@description
Returns a regex pattern that matches with valid PIN codes.
PIN code qualified as valid if ...
... only contains digits.
... it has a certain length.
```

```
@param (integer)(opt) length
Default: 4
```

```
@usage
(pin-code-pattern)
```

```
@usage
(pin-code-pattern 6)
```

```
@example
(pin-code-pattern 6)
=>
#"[\d]{6,6}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn pin-code-pattern
  ([]
   (pin-code-pattern 4))

  ([length]
   (re-pattern (str "[\\d]{"length","length"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [pin-code-pattern]]))

(audit.api/pin-code-pattern ...)
(pin-code-pattern           ...)
```

</details>

---

### pin-code-valid?

```
@description
Returns true if the given value is a valid PIN code.
PIN code qualified as valid if ...
... only contains digits.
... it has a certain length.
```

```
@param (*) n
@param (integer)(opt) length
Default: 4
```

```
@usage
(pin-code-valid? "0042")
```

```
@usage
(pin-code-valid? "420069" 6)
```

```
@example
(pin-code-valid? "420069" 6)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn pin-code-valid?
  ([n]
   (pin-code-valid? n 4))

  ([n length]
   (let [pattern (patterns/pin-code-pattern length)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [pin-code-valid?]]))

(audit.api/pin-code-valid? ...)
(pin-code-valid?           ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

