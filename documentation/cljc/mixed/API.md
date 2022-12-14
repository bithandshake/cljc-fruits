
# mixed.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > mixed.api

### Index

- [add-numbers](#add-numbers)

- [blank?](#blank)

- [multiply-numbers](#multiply-numbers)

- [natural-whole-number?](#natural-whole-number)

- [negative-whole-number?](#negative-whole-number)

- [nonempty?](#nonempty)

- [parse-number](#parse-number)

- [parse-rational-number](#parse-rational-number)

- [parse-whole-number](#parse-whole-number)

- [positive-whole-number?](#positive-whole-number)

- [rational-number?](#rational-number)

- [to-data-url](#to-data-url)

- [to-map](#to-map)

- [to-number](#to-number)

- [to-string](#to-string)

- [to-vector](#to-vector)

- [update-whole-number](#update-whole-number)

- [whole-number?](#whole-number)

### add-numbers

```
@param (list of *) abc
```

```
@example
(add-numbers 1 "3")
=>
4
```

```
@example
(add-numbers 1 "3" "a")
=>
4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn add-numbers
  [& abc]
  (letfn [(f [result x]
             (+ result (convert/to-number x)))]
         (reduce f 0 abc)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [add-numbers]]))

(mixed.api/add-numbers ...)
(add-numbers           ...)
```

</details>

---

### blank?

```
@param (*) n
```

```
@example
(blank? nil)
=>
true
```

```
@example
(blank? "")
=>
true
```

```
@example
(blank? [])
=>
true
```

```
@example
(=blank? {})
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
  (and (-> n seqable?)
       (-> n   empty?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [blank?]]))

(mixed.api/blank? ...)
(blank?           ...)
```

</details>

---

### multiply-numbers

```
@param (list of *) abc
```

```
@example
(multiply-numbers 1 "3")
=>
3
```

```
@example
(multiply-numbers 1 "3" "a")
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn multiply-numbers
  [& abc]
  (letfn [(f [result x]
             (* result (convert/to-number x)))]
         (reduce f 1 abc)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [multiply-numbers]]))

(mixed.api/multiply-numbers ...)
(multiply-numbers           ...)
```

</details>

---

### natural-whole-number?

```
@param (*) n
```

```
@example
(natural-whole-number? 12)
=>
true
```

```
@example
(natural-whole-number? "12")
=>
true
```

```
@example
(natural-whole-number? "+12")
=>
true
```

```
@example
(natural-whole-number? "-12")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn natural-whole-number?
  [n]
  (or (and (integer? n)
           (<= 0     n))
      (re-match? n #"^[+]{0,1}[\d]{1,}$")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [natural-whole-number?]]))

(mixed.api/natural-whole-number? ...)
(natural-whole-number?           ...)
```

</details>

---

### negative-whole-number?

```
@param (*) n
```

```
@example
(negative-whole-number? -12)
=>
true
```

```
@example
(negative-whole-number? "12")
=>
false
```

```
@example
(negative-whole-number? "-12")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn negative-whole-number?
  [n]
  (or (and (integer? n)
           (> 0      n))
      (re-match? n #"^[-][0-9]{1,}$")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [negative-whole-number?]]))

(mixed.api/negative-whole-number? ...)
(negative-whole-number?           ...)
```

</details>

---

### nonempty?

```
@param (*) n
```

```
@example
(nonempty? nil)
=>
false
```

```
@example
(nonempty? "")
=>
false
```

```
@example
(nonempty? [])
=>
false
```

```
@example
(nonempty? {})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonempty?
  [n]
  (or      (-> n seqable? not)
      (-> n empty?   not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [nonempty?]]))

(mixed.api/nonempty? ...)
(nonempty?           ...)
```

</details>

---

### parse-number

```
@param (*) n
@param (map)(opt) options
{:return? (boolean)(opt)
  Default: false}
```

```
@example
(parse-number 12)
=>
12
```

```
@example
(parse-number 12.1)
=>
12.1
```

```
@example
(parse-number "12.1")
=>
12.1
```

```
@example
(parse-number "abCd12.1")
=>
nil
```

```
@example
(parse-number "abCd12.1" {:return? true})
=>
"abCd12.1"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn parse-number
  ([n]
   (parse-number n {}))

  ([n options]
   (parse-rational-number n options)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [parse-number]]))

(mixed.api/parse-number ...)
(parse-number           ...)
```

</details>

---

### parse-rational-number

```
@param (*) n
@param (map)(opt) options
{:return? (boolean)(opt)
  Default: false}
```

```
@example
(parse-rational-number 12)
=>
12
```

```
@example
(parse-rational-number 12.1)
=>
12.1
```

```
@example
(parse-rational-number "12.1")
=>
12.1
```

```
@example
(parse-rational-number "abCd12.1")
=>
nil
```

```
@example
(parse-rational-number "abCd12.1" {:return? true})
=>
"abCd12.1"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn parse-rational-number
  ([n]
   (parse-rational-number n {}))

  ([n {:keys [return?]}]
   (cond (number?               n) (return               n)
         (type/rational-number? n) (reader/string->mixed n)
         return?                   (return               n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [parse-rational-number]]))

(mixed.api/parse-rational-number ...)
(parse-rational-number           ...)
```

</details>

---

### parse-whole-number

```
@param (*) n
@param (map)(opt) options
{:return? (boolean)(opt)
  Default: false}
```

```
@example
(parse-whole-number 12)
=>
12
```

```
@example
(parse-whole-number "12")
=>
12
```

```
@example
(parse-whole-number "abCd12")
=>
nil
```

```
@example
(parse-whole-number "abCd12" {:return? true})
=>
"abCd12"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn parse-whole-number
  ([n]
   (parse-whole-number n {}))

  ([n {:keys [return?]}]
   (cond (integer?           n) (return               n)
         (type/whole-number? n) (return               n)
         return?                (return               n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [parse-whole-number]]))

(mixed.api/parse-whole-number ...)
(parse-whole-number           ...)
```

</details>

---

### positive-whole-number?

```
@param (*) n
```

```
@example
(positive-whole-number? 12)
=>
true
```

```
@example
(positive-whole-number? "12")
=>
true
```

```
@example
(positive-whole-number? "+12")
=>
true
```

```
@example
(positive-whole-number? "0")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn positive-whole-number?
  [n]
  (or (and (integer?  n)
           (<    0    n))
      (and (not= 0    n)
           (re-match? n #"^[+]{0,1}[\d]{1,}$"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [positive-whole-number?]]))

(mixed.api/positive-whole-number? ...)
(positive-whole-number?           ...)
```

</details>

---

### rational-number?

```
@param (*) n
```

```
@example
(rational-number? 12)
=>
true
```

```
@example
(rational-number? 12.1)
=>
true
```

```
@example
(rational-number? "12")
=>
true
```

```
@example
(rational-number? "12.1")
=>
true
```

```
@example
(rational-number? "+12.1")
=>
true
```

```
@example
(rational-number? "-12.1")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn rational-number?
  [n]
  (or (number?   n)
      (re-match? n #"^[+-]{0,1}[\d]{1,}$")
      (re-match? n #"^[+-]{0,1}[\d]{1,}[\.][\d]{1,}$")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [rational-number?]]))

(mixed.api/rational-number? ...)
(rational-number?           ...)
```

</details>

---

### to-data-url

```
@param (*) n
```

```
@example
(to-data-url "My text file content")
=>
"data:text/plain;charset=utf-8,My text file content"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-data-url
  [n]
  (str "data:text/plain;charset=utf-8," n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [to-data-url]]))

(mixed.api/to-data-url ...)
(to-data-url           ...)
```

</details>

---

### to-map

```
@param (*) n
```

```
@example
(to-map {:a})
=>
{:a}
```

```
@example
(to-map nil)
=>
{}
```

```
@example
(to-map [:x :y :z])
=>
{0 :x 1 :y 2 :z}
```

```
@example
(to-map :x)
=>
{0 :x}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-map
  [n]
  (cond (vector? n) (vector/to-map n)
        (map?    n) (return        n)
        (nil?    n) (return        {})
        :return                    {0 n}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [to-map]]))

(mixed.api/to-map ...)
(to-map           ...)
```

</details>

---

### to-number

```
@param (*) n
```

```
@example
(to-number 3)
=>
3
```

```
@example
(to-number nil)
=>
0
```

```
@example
(to-number "a")
=>
0
```

```
@example
(to-number "-3")
=>
-3
```

```
@example
(to-number "1.1")
=>
1.1
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn to-number
  [n]
  (cond (nil?                  n) (return               0)
        (number?               n) (return               n)
        (type/whole-number?    n) (reader/string->mixed n)
        (type/rational-number? n) (reader/string->mixed n)
        :return 0))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [to-number]]))

(mixed.api/to-number ...)
(to-number           ...)
```

</details>

---

### to-string

```
@param (*) n
```

```
@example
(to-string [{:a "a"}])
=>
"[{:a a}]"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-string
  [n]
  (str n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [to-string]]))

(mixed.api/to-string ...)
(to-string           ...)
```

</details>

---

### to-vector

```
@param (*) n
```

```
@example
(to-vector [:a])
=>
[:a]
```

```
@example
(to-vector nil)
=>
[]
```

```
@example
(to-vector {:a "a" :b "b"})
=>
["a" "b"]
```

```
@example
(to-vector :x)
=>
[:x]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn to-vector
  [n]
  (cond (map?    n) (map/to-vector n)
        (vector? n) (return        n)
        (nil?    n) (return        [])
        :return                    [n]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [to-vector]]))

(mixed.api/to-vector ...)
(to-vector           ...)
```

</details>

---

### update-whole-number

```
@param (integer or string) n
@param (function) f
@param (*)(opt) x
```

```
@example
(update-whole-number "12" inc)
=>
"13"
```

```
@example
(update-whole-number "12" + 3)
=>
"15"
```

```
@example
(update-whole-number 12 + 3)
=>
15
```

```
@example
(update-whole-number "abCd12" + 3)
=>
"abCd12"
```

```
@return (integer or string)
```

<details>
<summary>Source code</summary>

```
(defn update-whole-number
  ([n f]
   (update-whole-number n f nil))

  ([n f x]
   (letfn [(update-f [n] (if x (f n x)
                               (f n)))]
          (cond (-> n           integer?)      (update-f n)
                (-> n type/whole-number?) (let [integer (reader/string->mixed n)]
                                               (update-f integer))
                (-> n              some?)      (return n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [update-whole-number]]))

(mixed.api/update-whole-number ...)
(update-whole-number           ...)
```

</details>

---

### whole-number?

```
@param (*) n
```

```
@example
(whole-number? 12)
=>
true
```

```
@example
(whole-number? "12")
=>
true
```

```
@example
(whole-number? "+12")
=>
true
```

```
@example
(whole-number? "-12")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn whole-number?
  [n]
  (or (integer?  n)
      (re-match? n #"^[+-]{0,1}[\d]{1,}$")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [mixed.api :refer [whole-number?]]))

(mixed.api/whole-number? ...)
(whole-number?           ...)
```

</details>

---

This documentation is generated by the [docs-api](https://github.com/bithandshake/docs-api) engine

