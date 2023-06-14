
# format.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > format.api

### Index

- [decimals](#decimals)

- [group-number](#group-number)

- [hide-email](#hide-email)

- [inc-version](#inc-version)

- [leading-zeros](#leading-zeros)

- [remove-leading-zeros](#remove-leading-zeros)

- [round](#round)

- [sign-number](#sign-number)

- [trailing-zeros](#trailing-zeros)

### decimals

```
@param (number or string) n
@param (integer)(opt) length
Default: 2
```

```
@usage
(decimals "420" 2)
```

```
@example
(decimals "1" 2)
=>
"1.00"
```

```
@example
(decimals "11.0000" 3)
=>
"11.000"
```

```
@example
(decimals nil 2)
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn decimals
  ([n]
   (decimals n 2))

  ([n length]
   (let [x     (str   n)
         count (count x)]
        (if            (< count 1) x
            (if-let [separator-index (string/first-dex-of x ".")]
                    (let [diff (- count separator-index length 1)]
                         (cond                               (> diff 0)
                               (subs x 0 (+ separator-index (inc length)))
                               (< diff 0)
                               (str x (trailing-zeros nil (- 0 diff)))
                               (= diff 0) x))
                    (str x "." (trailing-zeros nil length)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [decimals]]))

(format.api/decimals ...)
(decimals            ...)
```

</details>

---

### group-number

```
@param (number or string) n
@param (string)(opt) delimiter
Default: ","
```

```
@usage
(group-number 4200.5)
```

```
@example
(group-number 4200.5)
=>
"4 200.5"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn group-number
  ([n]
   (group-number n ","))

  ([n delimiter]
   (let [base        (re-find #"\d+" n)
         group-count (quot (count base) 3)
         offset      (-    (count base) (* 3 group-count))]
        (str (string/trim (reduce (fn [result dex]
                                      (let [x (+ offset (* 3 dex))]
                                           (str result delimiter (subs base x (+ x 3)))))
                                  (subs base 0 offset)
                                  (range group-count)))
             (subs n (count base))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [group-number]]))

(format.api/group-number ...)
(group-number            ...)
```

</details>

---

### hide-email

```
@param (string) n
```

```
@usage
(hide-email "user@email.com")
```

```
@example
(hide-email "user@email.com")
=>
"u**r@email.com"
```

```
@example
(hide-email "username@email.com")
=>
"u******e@email.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn hide-email
  [n]
  (if-let [user (string/before-first-occurence n "@" {:return? false})]
          (case (count user) 0 (str n)
                             1 (string/cover n "*")
                             2 (string/cover n "*" 1)
                               (string/cover n (string/multiply "*" (-> user count dec dec)) 1))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [hide-email]]))

(format.api/hide-email ...)
(hide-email            ...)
```

</details>

---

### inc-version

```
@param (string) n
```

```
@usage
(inc-version "0.0.1")
```

```
@example
(inc-version "1.2.19")
=>
"1.2.20"
```

```
@example
(inc-version "0.0.99")
=>
"0.1.00"
```

```
@example
(inc-version "9.9")
=>
"10.0"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn inc-version
  [n]
  (letfn [
          (implode-f [n separators]
                     (if (vector/nonempty? separators)
                         (implode-f (string/insert-part n "." (last separators))
                                    (vector/remove-last-item separators))
                         (return n)))

          (explode-f [n separators]
                     (if-let [separator (string/first-dex-of n ".")]
                             (explode-f (string/remove-first-occurence n ".")
                                        (conj separators separator))
                             (implode-f
                                        (let [bugfix (remove-leading-zeros n)]
                                             (leading-zeros (mixed/update-whole-number bugfix inc) (count n)))

                                        (if (re-match? n #"^[9]{1,}$")
                                            (vector/->items separators inc)
                                            (param          separators)))))]
         (explode-f n [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [inc-version]]))

(format.api/inc-version ...)
(inc-version            ...)
```

</details>

---

### leading-zeros

```
@param (number or string) n
@param (integer) length
```

```
@usage
(leading-zeros "420" 5)
```

```
@example
(leading-zeros 7 3)
=>
"007"
```

```
@example
(leading-zeros 420 3)
=>
"420"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn leading-zeros
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str "0" x))
            (return x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [leading-zeros]]))

(format.api/leading-zeros ...)
(leading-zeros            ...)
```

</details>

---

### remove-leading-zeros

```
@param (number or string) n
```

```
@usage
(remove-leading-zeros "042")
```

```
@example
(remove-leading-zeros 42)
=>
"42"
```

```
@example
(remove-leading-zeros "0042")
=>
"42"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-leading-zeros
  [n]
  (letfn [(f [n]
             (if-not (= "0" (-> n first str))
                     (return n)
                     (f (subs n 1))))]
         (-> n str f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [remove-leading-zeros]]))

(format.api/remove-leading-zeros ...)
(remove-leading-zeros            ...)
```

</details>

---

### round

```
@param (number) n
```

```
@usage
(round 1234)
```

```
@example
(round 1740)
=>
"2K"
```

```
@example
(round 1000420)
=>
"1M"
```

```
@example
(round 1000420069)
=>
"1B"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn round
  [n]
  (cond (>= n 1000000000) (str (Math/round (/ n 1000000)) "B")
        (>= n 1000000)    (str (Math/round (/ n 1000000)) "M")
        (>= n 1000)       (str (Math/round (/ n 1000))    "K")
        :return           (str (Math/round n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [round]]))

(format.api/round ...)
(round            ...)
```

</details>

---

### sign-number

```
@param (number or string) n
```

```
@usage
(sign-number 4200.5)
```

```
@example
(sign-number 4200.5)
=>
"+4200.5"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn sign-number
  [n]
  (let [n (str n)]
       (if (= (-> "-"     str)
              (-> n first str))
           (return n)
           (str "+"n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [sign-number]]))

(format.api/sign-number ...)
(sign-number            ...)
```

</details>

---

### trailing-zeros

```
@param (integer or string) n
@param (integer)(opt) length
```

```
@usage
(trailing-zeros "420" 5)
```

```
@example
(trailing-zeros 7 3)
=>
"700"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trailing-zeros
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str x "0"))
            (return x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :refer [trailing-zeros]]))

(format.api/trailing-zeros ...)
(trailing-zeros            ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

