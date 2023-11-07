
### regex.api

Functional documentation of the regex.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > regex.api

### Index

- [after-first-occurence](#after-first-occurence)

- [after-last-occurence](#after-last-occurence)

- [before-first-occurence](#before-first-occurence)

- [before-last-occurence](#before-last-occurence)

- [between-occurences](#between-occurences)

- [ends-with?](#ends-with)

- [first-dex-of](#first-dex-of)

- [from-first-occurence](#from-first-occurence)

- [from-last-occurence](#from-last-occurence)

- [last-dex-of](#last-dex-of)

- [not-ends-with!](#not-ends-with)

- [not-ends-with?](#not-ends-with)

- [not-starts-with!](#not-starts-with)

- [not-starts-with?](#not-starts-with)

- [nth-dex-of](#nth-dex-of)

- [re-count](#re-count)

- [re-first](#re-first)

- [re-last](#re-last)

- [re-match](#re-match)

- [re-match?](#re-match)

- [re-mismatch?](#re-mismatch)

- [remove-first-occurence](#remove-first-occurence)

- [remove-last-occurence](#remove-last-occurence)

- [replace-part](#replace-part)

- [starts-with?](#starts-with)

- [to-first-occurence](#to-first-occurence)

- [to-last-occurence](#to-last-occurence)

---

### after-first-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(after-first-occurence "abc123def" #"\d")
```

```
@example
(after-first-occurence "abc123def" #"\d")
=>
"23def"
```

```
@example
(after-first-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(after-first-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(after-first-occurence nil #"\d")
=>
nil
```

```
@example
(after-first-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn after-first-occurence
  ([n x]
   (after-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [first-match (core/re-first n x)]
                (subs n (+ (clojure.string/index-of n first-match)
                           (count                     first-match)))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [after-first-occurence]]))

(regex.api/after-first-occurence ...)
(after-first-occurence           ...)
```

</details>

---

### after-last-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(after-last-occurence "abc123def" #"\d")
```

```
@example
(after-last-occurence "abc123def" #"\d")
=>
"def"
```

```
@example
(after-last-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(after-last-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(after-last-occurence nil #"\d")
=>
nil
```

```
@example
(after-last-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn after-last-occurence
  ([n x]
   (after-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [last-match (core/re-last n x)]
                (subs n (+ (clojure.string/last-index-of n last-match)
                           (count last-match)))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [after-last-occurence]]))

(regex.api/after-last-occurence ...)
(after-last-occurence           ...)
```

</details>

---

### before-first-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(before-first-occurence "abc123def" #"\d")
```

```
@example
(before-first-occurence "abc123def" #"\d")
=>
"abc"
```

```
@example
(before-first-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(before-first-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(before-first-occurence nil #"\d")
=>
nil
```

```
@example
(before-first-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn before-first-occurence
  ([n x]
   (before-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [first-match (core/re-first n x)]
                (subs n 0 (clojure.string/index-of n first-match))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [before-first-occurence]]))

(regex.api/before-first-occurence ...)
(before-first-occurence           ...)
```

</details>

---

### before-last-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(before-last-occurence "abc123def" #"\d")
```

```
@example
(before-last-occurence "abc123def" #"\d")
=>
"abc12"
```

```
@example
(before-last-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(before-last-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(before-last-occurence nil #"\d")
=>
nil
```

```
@example
(before-last-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn before-last-occurence
  ([n x]
   (before-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [last-match (core/re-last n x)]
                (subs n 0 (clojure.string/last-index-of n last-match))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [before-last-occurence]]))

(regex.api/before-last-occurence ...)
(before-last-occurence           ...)
```

</details>

---

### between-occurences

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (regex pattern) y
```

```
@usage
(between-occurences "abc123def" #"a" #"f")
```

```
@example
(between-occurences "abc123def" #"a" #"f")
=>
"bc123de"
```

```
@example
(between-occurences "abc123def" #"a" #"x")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn between-occurences
  [n x y]
  (-> n (after-first-occurence x {:return? false})
        (before-last-occurence y {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [between-occurences]]))

(regex.api/between-occurences ...)
(between-occurences           ...)
```

</details>

---

### ends-with?

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(ends-with? "The things you used to own, now they own you." #"\.")
```

```
@example
(ends-with? "The things you used to own, now they own you." #"\.")
=>
true
```

```
@example
(ends-with? "The things you used to own, now they own you." #"!")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn ends-with?
  [n x]
  (let [n (str n)]
       (if-let [last-match (re-last n x)]
               (clojure.string/ends-with? n last-match))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [ends-with?]]))

(regex.api/ends-with? ...)
(ends-with?           ...)
```

</details>

---

### first-dex-of

```
@param (*) n
@param (regex pattern) pattern
```

```
@example
(first-dex-of "abc 123" #"[\d]{1,}")
=>
4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn first-dex-of
  [n pattern]
  (let [n (str n)]
       (if-let [match (re-find pattern n)]
               (cond (vector? match) (string/first-dex-of n (first match))
                     (string? match) (string/first-dex-of n        match)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [first-dex-of]]))

(regex.api/first-dex-of ...)
(first-dex-of           ...)
```

</details>

---

### from-first-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(from-first-occurence "abc123def" #"\d")
```

```
@example
(from-first-occurence "abc123def" #"\d")
=>
"123def"
```

```
@example
(from-first-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(from-first-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(from-first-occurence nil #"\d")
=>
nil
```

```
@example
(from-first-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn from-first-occurence
  ([n x]
   (from-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [first-match (core/re-first n x)]
                (subs n (clojure.string/index-of n first-match))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [from-first-occurence]]))

(regex.api/from-first-occurence ...)
(from-first-occurence           ...)
```

</details>

---

### from-last-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(from-last-occurence "abc123def" #"\d")
```

```
@example
(from-last-occurence "abc123def" #"\d")
=>
"def"
```

```
@example
(from-last-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(from-last-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(from-last-occurence nil #"\d")
=>
nil
```

```
@example
(from-last-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn from-last-occurence
  ([n x]
   (from-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [last-match (core/re-last n x)]
                (subs n (clojure.string/last-index-of n last-match))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [from-last-occurence]]))

(regex.api/from-last-occurence ...)
(from-last-occurence           ...)
```

</details>

---

### last-dex-of

```
@param (*) n
@param (regex pattern) pattern
```

```
@example
(last-dex-of "abc 123 def 456" #"[\d]{1,}")
=>
12
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn last-dex-of
  [n pattern]
  (let [n (str n)]
       (if-let [match (re-find pattern n)]
               (cond (vector? match) (string/last-dex-of n (first match))
                     (string? match) (string/last-dex-of n        match)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [last-dex-of]]))

(regex.api/last-dex-of ...)
(last-dex-of           ...)
```

</details>

---

### not-ends-with!

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(not-ends-with! "The things you used to own, now they own you." #"\.")
```

```
@example
(not-ends-with! "The things you used to own, now they own you" #"\.")
=>
"The things you used to own, now they own you"
```

```
@example
(not-ends-with! "The things you used to own, now they own you." #"\.")
=>
"The things you used to own, now they own you"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn not-ends-with!
  [n x])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [not-ends-with!]]))

(regex.api/not-ends-with! ...)
(not-ends-with!           ...)
```

</details>

---

### not-ends-with?

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(not-ends-with? "The things you used to own, now they own you." #"\.")
```

```
@example
(not-ends-with? "The things you used to own, now they own you." #"!")
=>
true
```

```
@example
(not-ends-with? "The things you used to own, now they own you." #"\.")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn not-ends-with?
  [n x]
  (not (ends-with? n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [not-ends-with?]]))

(regex.api/not-ends-with? ...)
(not-ends-with?           ...)
```

</details>

---

### not-starts-with!

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(not-starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
                 #"[a-z]")
```

```
@example
(not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
                 #"[a-z]")
=>
"n a long enough time line, the survival rate for everyone drops to zero."
```

```
@example
(not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
                 #"[/d]")
=>
"On a long enough time line, the survival rate for everyone drops to zero."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn not-starts-with!
  [n x])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [not-starts-with!]]))

(regex.api/not-starts-with! ...)
(not-starts-with!           ...)
```

</details>

---

### not-starts-with?

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                 #"[\d]")
```

```
@example
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "[\d]")
=>
true
```

```
@example
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "[a-z]")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn not-starts-with?
  [n x]
  (not (starts-with? n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [not-starts-with?]]))

(regex.api/not-starts-with? ...)
(not-starts-with?           ...)
```

</details>

---

### nth-dex-of

```
@param (*) n
@param (regex pattern) pattern
```

```
@example
(nth-dex-of "abc 123 def 456" #"[\d]{3,}" 1)
=>
12
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn nth-dex-of
  [n pattern dex]
  (let [n (str n)]
       (when (>= dex 0)
             (letfn [(f [cursor skip]
                        (if-let [first-dex (-> n (string/part  cursor)
                                                 (first-dex-of pattern))]
                                (if (= skip dex)
                                    (+ cursor first-dex)
                                    (f (+ first-dex cursor 1)
                                       (inc skip)))))]
                    (f 0 0)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [nth-dex-of]]))

(regex.api/nth-dex-of ...)
(nth-dex-of           ...)
```

</details>

---

### re-count

```
@description
Returns the match count.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-count "123" #"\d")
```

```
@example
(re-count "123" #"\d")
=>
3
```

```
@example
(re-count "abc" #"\d")
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn re-count
  [n pattern]
  (count (re-seq pattern (str n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-count]]))

(regex.api/re-count ...)
(re-count           ...)
```

</details>

---

### re-first

```
@description
Returns the first match.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-first "123" #"\d")
```

```
@example
(re-first "123" #"\d")
=>
"1"
```

```
@example
(re-first "abc" #"\d")
=>
nil
```

```
@return (map, string or vector)
```

<details>
<summary>Source code</summary>

```
(defn re-first
  [n pattern]
  (first (re-seq pattern (str n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-first]]))

(regex.api/re-first ...)
(re-first           ...)
```

</details>

---

### re-last

```
@description
Returns the last match.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-last "123" #"\d")
```

```
@example
(re-last "123" #"\d")
=>
"3"
```

```
@example
(re-last "abc" #"\d")
=>
nil
```

```
@return (map, string or vector)
```

<details>
<summary>Source code</summary>

```
(defn re-last
  [n pattern]
  (last (re-seq pattern (str n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-last]]))

(regex.api/re-last ...)
(re-last           ...)
```

</details>

---

### re-match

```
@description
Returns the given 'n' string if any match is found.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-match "123" #"\d{1,}")
```

```
@example
(re-match "123" #"^[\d]{1,}$")
=>
"123"
```

```
@example
(re-match "abc" #"^[\d]{1,}$")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn re-match
  [n pattern]
  (let [n (str n)]
       (if (re-find pattern n)
           (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-match]]))

(regex.api/re-match ...)
(re-match           ...)
```

</details>

---

### re-match?

```
@description
Returns TRUE if any matches found.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-match? "123" #"\d{1,}")
```

```
@example
(re-match? "123" #"^[\d]{1,}$")
=>
true
```

```
@example
(re-match? "abc" #"^[\d]{1,}$")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn re-match?
  [n pattern]
  (let [n (str n)]
       (some? (re-find pattern n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-match?]]))

(regex.api/re-match? ...)
(re-match?           ...)
```

</details>

---

### re-mismatch?

```
@description
Returns TRUE if no matches found.
```

```
@param (*) n
@param (regex pattern) pattern
```

```
@usage
(re-mismatch? "123" #"\d{1,}")
```

```
@example
(re-mismatch? "123" #"^[\d]{1,}$")
=>
false
```

```
@example
(re-mismatch? "abc" #"^[\d]{1,}$")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn re-mismatch?
  [n pattern]
  (let [n (str n)]
       (nil? (re-find pattern n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [re-mismatch?]]))

(regex.api/re-mismatch? ...)
(re-mismatch?           ...)
```

</details>

---

### remove-first-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(remove-first-occurence "abc123def" #"\d")
```

```
@example
(remove-first-occurence "abc123def" #"\d")
=>
"abc23def"
```

```
@example
(remove-first-occurence "abcdef" #"\d")
=>
"abcdef"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-first-occurence
  [n x]
  (let [n (str n)]
       (if-let [first-match (core/re-first n x)]
               (let [dex (clojure.string/index-of n first-match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count first-match)))))
               (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [remove-first-occurence]]))

(regex.api/remove-first-occurence ...)
(remove-first-occurence           ...)
```

</details>

---

### remove-last-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (*) x
```

```
@usage
(remove-last-occurence "abc123def" #"\d")
```

```
@example
(remove-last-occurence "abc123def" #"\d")
=>
"abc12def"
```

```
@example
(remove-last-occurence "abcdef" #"\d")
=>
"abcdef"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-last-occurence
  [n x]
  (let [n (str n)]
       (if-let [last-match (core/re-last n x)]
               (let [dex (clojure.string/last-index-of n last-match)]
                    (str (subs n 0 dex)
                         (subs n (+ dex (count last-match)))))
               (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [remove-last-occurence]]))

(regex.api/remove-last-occurence ...)
(remove-last-occurence           ...)
```

</details>

---

### replace-part

```
@param (*) n
@param (regex pattern) x
@param (*) y
@param (map)(opt) options
{:recur? (boolean)(opt)
  Default: false}
```

```
@example
(replace-part "abc" #"[b]{0,}" "x")
=>
"axc"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn replace-part
  ([n x y]
   (replace-part n x y {}))

  ([n x y options]
   (string/replace-part n x y options)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [replace-part]]))

(regex.api/replace-part ...)
(replace-part           ...)
```

</details>

---

### starts-with?

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
```

```
@usage
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
             #"[a-z]")
```

```
@example
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
             #"[a-z]")
=>
true
```

```
@example
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
             #"[\d]")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn starts-with?
  [n x]
  (let [n (str n)]
       (if-let [first-match (re-first n x)]
               (clojure.string/starts-with? n first-match))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [starts-with?]]))

(regex.api/starts-with? ...)
(starts-with?           ...)
```

</details>

---

### to-first-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(to-first-occurence "abc123def" #"\d")
```

```
@example
(to-first-occurence "abc123def" #"\d")
=>
"abc1"
```

```
@example
(to-first-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(to-first-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(to-first-occurence nil #"\d")
=>
nil
```

```
@example
(to-first-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-first-occurence
  ([n x]
   (to-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [first-match (core/re-first n x)]
                (subs n 0 (+ (clojure.string/index-of n first-match)
                             (count                     first-match)))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [to-first-occurence]]))

(regex.api/to-first-occurence ...)
(to-first-occurence           ...)
```

</details>

---

### to-last-occurence

```
@warning
Do not use capturing groups in you pattern, otherwise it generates multiple
matches for the occurence!
```

```
@param (*) n
@param (regex pattern) x
@param (map) options
{:return? (boolean)(opt)
  Default: false}
```

```
@usage
(to-last-occurence "abc123def" #"\d")
```

```
@example
(to-last-occurence "abc123def" #"\d")
=>
"abc123"
```

```
@example
(to-last-occurence "abcdef" #"\d")
=>
nil
```

```
@example
(to-last-occurence "abcdef" #"\d" {:return? true})
=>
"abcdef"
```

```
@example
(to-last-occurence nil #"\d")
=>
nil
```

```
@example
(to-last-occurence nil "\d" {:return? true})
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-last-occurence
  ([n x]
   (to-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (str n)]
        (if-let [last-match (core/re-last n x)]
                (subs n 0 (+ (clojure.string/last-index-of n last-match)
                             (count last-match)))
                (if return? n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :refer [to-last-occurence]]))

(regex.api/to-last-occurence ...)
(to-last-occurence           ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

