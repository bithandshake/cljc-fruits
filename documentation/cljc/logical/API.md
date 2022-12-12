
# logical.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > logical.api

### Index

- [=?](#)

- [if-and](#if-and)

- [if-or](#if-or)

- [nonfalse?](#nonfalse)

- [nontrue?](#nontrue)

- [nor](#nor)

- [not=?](#not)

- [or=](#or)

- [swap](#swap)

### =?

```
@description
If 'a' is equal 'b', then returns with 'c', else returns with 'd'.
```

```
@param (*) a
@param (*) b
@param (*) c
@param (*) d
```

```
@example
(=? "A" "B" "equal" "not equal")
=>
"not equal"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn =?
  ([a b c]
   (when (= a b) c))

  ([a b c d]
   (if (= a b) c d)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [=?]]))

(logical.api/=? ...)
(=?             ...)
```

</details>

---

### if-and

```
@description
If 'a' and 'b' is true, then returns with 'c', else returns with 'd'.
```

```
@param (*) a
@param (*) b
@param (*) c
@param (*) d
```

```
@example
(if-and true false "C" "D")
=>
"D"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn if-and
  ([a b c]
   (when (and a b) c))

  ([a b c d]
   (if (and a b) c d)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [if-and]]))

(logical.api/if-and ...)
(if-and             ...)
```

</details>

---

### if-or

```
@description
If 'a' or 'b' is true, then returns with 'c', else returns with 'd'.
```

```
@param (*) a
@param (*) b
@param (*) c
@param (*) d
```

```
@example
(if-or true false "C" "D")
=>
"C"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn if-or
  ([a b c]
   (when (or a b) c))

  ([a b c d]
   (if (or a b) c d)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [if-or]]))

(logical.api/if-or ...)
(if-or             ...)
```

</details>

---

### nonfalse?

```
@param (*) n
```

```
@usage
(nonfalse? nil)
```

```
@example
(nonfalse? nil)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonfalse?
  [n]
  (not= n false))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [nonfalse?]]))

(logical.api/nonfalse? ...)
(nonfalse?             ...)
```

</details>

---

### nontrue?

```
@param (*) n
```

```
@usage
(nontrue? :x)
```

```
@example
(nontrue? nil)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nontrue?
  [n]
  (not= n true))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [nontrue?]]))

(logical.api/nontrue? ...)
(nontrue?             ...)
```

</details>

---

### nor

```
@description
Returns true if all the parameters are false after converting them to boolean type.
```

```
@param (list of *) abc
```

```
@example
(nor true false false)
=>
false
```

```
@example
(nor false false false)
=>
true
```

```
@example
(nor false nil)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nor
  [& abc]
  (not-any? boolean abc))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [nor]]))

(logical.api/nor ...)
(nor             ...)
```

</details>

---

### not=?

```
@description
If 'a' is NOT equal 'b', then returns with 'c', else returns with 'd'.
```

```
@param (*) a
@param (*) b
@param (*) c
@param (*) d
```

```
@example
(not=? "A" "B" "not equal" "equal")
=>
"not equal"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn not=?
  ([a b c]
   (when-not (= a b) c))

  ([a b c d]
   (if-not (= a b) c d)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [not=?]]))

(logical.api/not=? ...)
(not=?             ...)
```

</details>

---

### or=

```
@description
Returns true if 'a' is equal to any other parameter, false otherwise.
```

```
@param (*) a
@param (list of *) bcd
```

```
@example
(or= :a :b :c)
=>
false
```

```
@example
(or= :a :b :a)
=>
true
```

```
@example
(or= :a :a)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn or=
  [a & bcd]
  (boolean (some #(= a %) bcd)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [or=]]))

(logical.api/or= ...)
(or=             ...)
```

</details>

---

### swap

```
@description
Returns with 'b' if 'x' is equal to 'a', returns with 'a' if 'x' is equal to 'b',
returns with 'x' otherwise.
```

```
@param (*) x
@param (*) a
@param (*) b
```

```
@example
(swap "A" "A" "B")
=>
"B"
```

```
@example
(swap "B" "A" "B")
=>
"A"
```

```
@example
(swap "C" "A" "B")
=>
"C"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn swap
  [x a b]
  (cond (= x a) b
        (= x b) a
        :return x))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [logical.api :refer [swap]]))

(logical.api/swap ...)
(swap             ...)
```

</details>

---

This documentation is generated by the [docs-api](https://github.com/bithandshake/docs-api) engine

