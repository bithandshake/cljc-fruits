
### alphabet.api

Functional documentation of the alphabet.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > alphabet.api

### Index

- [integer->lowercase](#integer-lowercase)

- [integer->uppercase](#integer-uppercase)

---

### integer->lowercase

```
@param (integer) n
```

```
@usage
(integer->lowercase 3)
```

```
@example
(integer->lowercase 3)
=>
"c"
```

```
@example
(integer->lowercase 26)
=>
"z"
```

```
@example
(integer->lowercase 27)
=>
"aa"
```

```
@example
(integer->lowercase 702)
=>
"zz"
```

```
@example
(integer->lowercase 703)
=>
"aaa"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn integer->lowercase
  [n]
  (let [base-char (int \a)]
       (loop [num n result ""]
             (if (-> num zero?)
                 (-> result)
                 (let [char (char (+ base-char (mod (dec num) 26)))]
                      (recur (quot (dec num) 26) (str char result)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [alphabet.api :refer [integer->lowercase]]))

(alphabet.api/integer->lowercase ...)
(integer->lowercase              ...)
```

</details>

---

### integer->uppercase

```
@param (integer) n
```

```
@usage
(integer->uppercase 3)
```

```
@example
(integer->uppercase 3)
=>
"C"
```

```
@example
(integer->uppercase 26)
=>
"Z"
```

```
@example
(integer->uppercase 27)
=>
"AA"
```

```
@example
(integer->uppercase 702)
=>
"ZZ"
```

```
@example
(integer->uppercase 703)
=>
"AAA"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn integer->uppercase
  [n]
  (let [base-char (int \A)]
       (loop [num n result ""]
             (if (-> num zero?)
                 (-> result)
                 (let [char (char (+ base-char (mod (dec num) 26)))]
                      (recur (quot (dec num) 26) (str char result)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [alphabet.api :refer [integer->uppercase]]))

(alphabet.api/integer->uppercase ...)
(integer->uppercase              ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

