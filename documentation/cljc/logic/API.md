
# logic.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > logic.api

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
(ns my-namespace (:require [logic.api :refer [=?]]))

(logic.api/=? ...)
(=?           ...)
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
(ns my-namespace (:require [logic.api :refer [if-and]]))

(logic.api/if-and ...)
(if-and           ...)
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
(ns my-namespace (:require [logic.api :refer [if-or]]))

(logic.api/if-or ...)
(if-or           ...)
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
(ns my-namespace (:require [logic.api :refer [nonfalse?]]))

(logic.api/nonfalse? ...)
(nonfalse?           ...)
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
(ns my-namespace (:require [logic.api :refer [nontrue?]]))

(logic.api/nontrue? ...)
(nontrue?           ...)
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
(ns my-namespace (:require [logic.api :refer [nor]]))

(logic.api/nor ...)
(nor           ...)
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
(ns my-namespace (:require [logic.api :refer [not=?]]))

(logic.api/not=? ...)
(not=?           ...)
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
(ns my-namespace (:require [logic.api :refer [or=]]))

(logic.api/or= ...)
(or=           ...)
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
(ns my-namespace (:require [logic.api :refer [swap]]))

(logic.api/swap ...)
(swap           ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

