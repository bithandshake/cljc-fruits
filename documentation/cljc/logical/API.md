
# <strong>logical.api</strong> namespace
<p>Documentation of the <strong>logical/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > logical.api</strong>



### =?

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
If a is equal b, returns c, else returns d.
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
(ns my-namespace (:require [logical.api :as logical :refer [=?]]))

(logical/=? ...)
(=?         ...)
```

</details>

---

### if-and

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
If a and b is true, returns c, else returns d.
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
(ns my-namespace (:require [logical.api :as logical :refer [if-and]]))

(logical/if-and ...)
(if-and         ...)
```

</details>

---

### if-or

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
If a or b is true, returns c, else returns d.
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
(ns my-namespace (:require [logical.api :as logical :refer [if-or]]))

(logical/if-or ...)
(if-or         ...)
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
(ns my-namespace (:require [logical.api :as logical :refer [nonfalse?]]))

(logical/nonfalse? ...)
(nonfalse?         ...)
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
(ns my-namespace (:require [logical.api :as logical :refer [nontrue?]]))

(logical/nontrue? ...)
(nontrue?         ...)
```

</details>

---

### nor

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
Returns true if all the parameters are false after convert them to boolean type.
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
(ns my-namespace (:require [logical.api :as logical :refer [nor]]))

(logical/nor ...)
(nor         ...)
```

</details>

---

### not=?

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
If a is NOT equal b, returns c, else returns d.
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
(ns my-namespace (:require [logical.api :as logical :refer [not=?]]))

(logical/not=? ...)
(not=?         ...)
```

</details>

---

### or=

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
Returns true if a is equal to any other parameter, false otherwise.
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
(ns my-namespace (:require [logical.api :as logical :refer [or=]]))

(logical/or= ...)
(or=         ...)
```

</details>

---

### swap

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
Returns b if x is equal to a, returns a if x is equal to b, returns x otherwise.
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
(ns my-namespace (:require [logical.api :as logical :refer [swap]]))

(logical/swap ...)
(swap         ...)
```

</details>
