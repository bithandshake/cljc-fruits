
### audit.api

Functional documentation of the audit.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > audit.api

### Index

- [email-address-pattern](#email-address-pattern)

- [email-address-valid?](#email-address-valid)

- [generate-password](#generate-password)

- [generate-pin-code](#generate-pin-code)

- [generate-security-code](#generate-security-code)

- [ip-address-pattern](#ip-address-pattern)

- [ip-address-valid?](#ip-address-valid)

- [latin-name-pattern](#latin-name-pattern)

- [latin-name-valid?](#latin-name-valid)

- [password-pattern](#password-pattern)

- [password-valid?](#password-valid)

- [phone-number-pattern](#phone-number-pattern)

- [phone-number-valid?](#phone-number-valid)

- [pin-code-pattern](#pin-code-pattern)

- [pin-code-valid?](#pin-code-valid)

- [security-code-pattern](#security-code-pattern)

- [security-code-valid?](#security-code-valid)

- [user-agent-pattern](#user-agent-pattern)

- [user-agent-valid?](#user-agent-valid)

- [username-pattern](#username-pattern)

- [username-valid?](#username-valid)

---

### email-address-pattern

```
@description
Returns a regex pattern that matches valid email addresses.
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
Returns TRUE if the given value is a valid email address.
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

### generate-security-code

```
@param (integer)(opt) length
Default: 6
```

```
@usage
(generate-security-code)
```

```
@example
(generate-security-code)
=>
"042069"
```

```
@example
(generate-security-code 8)
=>
"04206900"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-security-code
  ([]
   (generate-security-code 6))

  ([length]
   (-> length random/generate-number str)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [generate-security-code]]))

(audit.api/generate-security-code ...)
(generate-security-code           ...)
```

</details>

---

### ip-address-pattern

```
@description
Returns a regex pattern that matches valid IP addresses.
```

```
@usage
(ip-address-pattern)
```

```
@example
(ip-address-pattern)
=>
#"^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})$"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn ip-address-pattern
  []
  (re-pattern (str "^(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})$")))
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
Returns TRUE if the given value is a valid IP address.
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

### latin-name-pattern

```
@description
Returns a regex pattern that matches valid latin names.
Latin name is declared as valid if ...
... its length is in a certain domain,
... contains only latin characters, accented latin characters, digits,
    underscrores, hyphens, apostrophes, periods and spaces.
```

```
@param (integer)(opt) min
Default: 2
@param (integer)(opt) max
Default: 32
```

```
@usage
(latin-name-pattern)
```

```
@usage
(latin-name-pattern 3)
```

```
@usage
(latin-name-pattern 3 18)
```

```
@example
(latin-name-pattern 3 18)
=>
#"[A-Za-zÀ-Ýà-ý0-9_\-\']{3,18}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn latin-name-pattern
  ([]
   (latin-name-pattern 2 32))

  ([min]
   (latin-name-pattern min 32))

  ([min max]
   (re-pattern (str "[A-Za-zÀ-Ýà-ý0-9\\_\\-\\'\\.\\s]{"min","max"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [latin-name-pattern]]))

(audit.api/latin-name-pattern ...)
(latin-name-pattern           ...)
```

</details>

---

### latin-name-valid?

```
@description
Returns TRUE if the given value is a valid latin name.
Latin name is declared as valid if ...
... its length is in a certain domain,
... contains only latin characters, accented latin characters, digits,
    underscrores, hyphens, apostrophes, periods and spaces.
```

```
@param (integer)(opt) min
Default: 2
@param (integer)(opt) max
Default: 32
```

```
@usage
(latin-name-valid? "John O'Reilly")
```

```
@example
(latin-name-valid? "John O'Reilly")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn latin-name-valid?
  ([n]
   (latin-name-valid? n 2 32))

  ([n min]
   (latin-name-valid? n min 32))

  ([n min max]
   (let [pattern (patterns/latin-name-pattern min max)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [latin-name-valid?]]))

(audit.api/latin-name-valid? ...)
(latin-name-valid?           ...)
```

</details>

---

### password-pattern

```
@description
Returns a regex pattern that matches valid passwords.
Password is declared as valid if ...
... its length is in a certain domain,
... contains at least one uppercase letter,
... contains at least one lowercase letter,
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
Returns TRUE if the given value is a valid password.
Password is declared as valid if ...
... its length is in a certain domain,
... contains at least one uppercase letter,
... contains at least one lowercase letter,
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
   (password-valid? n 8 32))

  ([n min]
   (password-valid? n min 32))

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
Returns a regex pattern that matches valid phone numbers.
Phone number is declared as valid if ...
... its length is in a certain domain,
... its first letter is a "+" character.
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
Returns TRUE if the given value is a valid phone number.
Phone number is declared as valid if ...
... its length is in a certain domain,
... its first character is a "+" character.
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
   (phone-number-valid? n 4 20))

  ([n min]
   (phone-number-valid? n min 20))

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
Returns a regex pattern that matches valid PIN codes.
PIN code is declared as valid if ...
... only contains digits,
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
Returns TRUE if the given value is a valid PIN code.
PIN code is declared as valid if ...
... only contains digits,
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

### security-code-pattern

```
@description
Returns a regex pattern that matches valid security codes.
Security code is declared as valid if ...
... only contains digits,
... it has a certain length.
```

```
@param (integer)(opt) length
Default: 6
```

```
@usage
(security-code-pattern)
```

```
@usage
(security-code-pattern 8)
```

```
@example
(security-code-pattern 8)
=>
#"[\d]{8,8}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn security-code-pattern
  ([]
   (security-code-pattern 6))

  ([length]
   (re-pattern (str "[\\d]{"length","length"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [security-code-pattern]]))

(audit.api/security-code-pattern ...)
(security-code-pattern           ...)
```

</details>

---

### security-code-valid?

```
@description
Returns TRUE if the given value is a valid security code.
Security code is declared as valid if ...
... only contains digits,
... it has a certain length.
```

```
@param (*) n
@param (integer)(opt) length
Default: 6
```

```
@usage
(security-code-valid? "004269")
```

```
@usage
(security-code-valid? "420069" 6)
```

```
@example
(security-code-valid? "420069" 6)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn security-code-valid?
  ([n]
   (security-code-valid? n 6))

  ([n length]
   (let [pattern (patterns/security-code-pattern length)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [security-code-valid?]]))

(audit.api/security-code-valid? ...)
(security-code-valid?           ...)
```

</details>

---

### user-agent-pattern

```
@description
Returns a regex pattern that matches valid user agent strings.
```

```
@param (strings in vector)(opt) allowed-agents
Default: ["Mozilla" "Chrome" "Safari"]
```

```
@usage
(user-agent-pattern)
```

```
@usage
(user-agent-pattern ["Mozilla" "Chrome" "Safari" "My-agent"])
```

```
@example
(user-agent-pattern ["Mozilla" "Chrome" "Safari" "My-agent"])
=>
#""
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn user-agent-pattern
  ([]
   (user-agent-pattern ["Mozilla" "Chrome" "Safari"]))

  ([allowed-agents]
   (as-> allowed-agents % (string/join % "|")
                          (str "^("%")")
                          (re-pattern %))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [user-agent-pattern]]))

(audit.api/user-agent-pattern ...)
(user-agent-pattern           ...)
```

</details>

---

### user-agent-valid?

```
@description
Returns TRUE if the given value is a valid user agent string.
```

```
@param (*) n
@param (strings in vector)(opt) allowed-agents
Default: ["Mozilla" "Chrome" "Safari"]
```

```
@usage
(user-agent-valid? "Mozilla/5.0 (Linux; Android 10; SM-G960U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Mobile Safari/537.36")
```

```
@usage
(user-agent-valid? "Mozilla/5.0 (Linux; Android 10; SM-G960U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Mobile Safari/537.36"
                   ["Mozilla" "Chrome" "Safari" "My-agent"])
```

```
@example
(user-agent-valid? "Mozilla/5.0 (Linux; Android 10; SM-G960U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Mobile Safari/537.36")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn user-agent-valid?
  ([n]
   (user-agent-valid? n ["Mozilla" "Chrome" "Safari"]))

  ([n allowed-agents]
   (let [pattern (patterns/user-agent-pattern allowed-agents)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [user-agent-valid?]]))

(audit.api/user-agent-valid? ...)
(user-agent-valid?           ...)
```

</details>

---

### username-pattern

```
@description
Returns a regex pattern that matches valid usernames.
Username is declared as valid if ...
... its length is in a certain domain,
... contains only latin characters, digits, underscrores and hyphens.
```

```
@param (integer)(opt) min
Default: 4
@param (integer)(opt) max
Default: 16
```

```
@usage
(username-pattern)
```

```
@usage
(username-pattern 6)
```

```
@usage
(username-pattern 6 24)
```

```
@example
(username-pattern 6 24)
=>
#"[A-Za-z0-9_\-]{6,24}"
```

```
@return (regex pattern)
```

<details>
<summary>Source code</summary>

```
(defn username-pattern
  ([]
   (username-pattern 4 16))

  ([min]
   (username-pattern min 16))

  ([min max]
   (re-pattern (str "[A-Za-z0-9\\_\\-]{"min","max"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [username-pattern]]))

(audit.api/username-pattern ...)
(username-pattern           ...)
```

</details>

---

### username-valid?

```
@description
Returns TRUE if the given value is a valid username.
Username is declared as valid if ...
... its length is in a certain domain,
... contains only latin characters, digits, underscrores and hyphens.
```

```
@param (*) n
@param (integer)(opt) min
Default: 4
@param (integer)(opt) max
Default: 16
```

```
@usage
(username-valid? "WinnieThePooh_69")
```

```
@usage
(username-valid? "WinnieThePooh_69" 6)
```

```
@usage
(username-valid? "WinnieThePooh_69" 6 32)
```

```
@example
(username-valid? "WinnieThePooh_69" 6 32)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn username-valid?
  ([n]
   (username-valid? n 4 16))

  ([n min]
   (username-valid? n min 16))

  ([n min max]
   (let [pattern (patterns/username-pattern min max)]
        (re-match? (str n) pattern))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [audit.api :refer [username-valid?]]))

(audit.api/username-valid? ...)
(username-valid?           ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

