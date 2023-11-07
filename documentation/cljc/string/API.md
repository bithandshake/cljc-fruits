
### string.api

Functional documentation of the string.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > string.api

### Index

- [abc](#abc)

- [abc?](#abc)

- [after-first-occurence](#after-first-occurence)

- [after-last-occurence](#after-last-occurence)

- [append](#append)

- [apply-on-part](#apply-on-part)

- [before-first-occurence](#before-first-occurence)

- [before-last-occurence](#before-last-occurence)

- [between-occurences](#between-occurences)

- [blank?](#blank)

- [contains-digit?](#contains-digit)

- [contains-lowercase-letter?](#contains-lowercase-letter)

- [contains-part?](#contains-part)

- [contains-uppercase-letter?](#contains-uppercase-letter)

- [count-occurences](#count-occurences)

- [cover](#cover)

- [cursor-in-bounds?](#cursor-in-bounds)

- [cursor-out-of-bounds?](#cursor-out-of-bounds)

- [cut](#cut)

- [dex-in-bounds?](#dex-in-bounds)

- [dex-out-of-bounds?](#dex-out-of-bounds)

- [ends-with!](#ends-with)

- [ends-with?](#ends-with)

- [filter-characters](#filter-characters)

- [first-character](#first-character)

- [first-dex-of](#first-dex-of)

- [from-first-occurence](#from-first-occurence)

- [from-last-occurence](#from-last-occurence)

- [if-contains-part](#if-contains-part)

- [insert-part](#insert-part)

- [join](#join)

- [last-character](#last-character)

- [last-dex-of](#last-dex-of)

- [length](#length)

- [length-between?](#length-between)

- [length-max?](#length-max)

- [length-min?](#length-min)

- [length?](#length)

- [line-count](#line-count)

- [lowercase?](#lowercase)

- [max-length](#max-length)

- [max-lines](#max-lines)

- [max-occurence?](#max-occurence)

- [min-occurence?](#min-occurence)

- [multiply](#multiply)

- [nonblank?](#nonblank)

- [not-ends-with!](#not-ends-with)

- [not-ends-with?](#not-ends-with)

- [not-pass-with?](#not-pass-with)

- [not-starts-with!](#not-starts-with)

- [not-starts-with?](#not-starts-with)

- [nth-character](#nth-character)

- [nth-dex-of](#nth-dex-of)

- [part](#part)

- [pass-with?](#pass-with)

- [prefix](#prefix)

- [prepend](#prepend)

- [remove-first-occurence](#remove-first-occurence)

- [remove-last-occurence](#remove-last-occurence)

- [remove-newlines](#remove-newlines)

- [remove-part](#remove-part)

- [replace-part](#replace-part)

- [same-length?](#same-length)

- [second-character](#second-character)

- [split](#split)

- [starts-with!](#starts-with)

- [starts-with?](#starts-with)

- [suffix](#suffix)

- [to-capitalized](#to-capitalized)

- [to-first-occurence](#to-first-occurence)

- [to-integer](#to-integer)

- [to-last-occurence](#to-last-occurence)

- [to-lowercase](#to-lowercase)

- [to-uppercase](#to-uppercase)

- [trim](#trim)

- [trim-end](#trim-end)

- [trim-gaps](#trim-gaps)

- [trim-newlines](#trim-newlines)

- [trim-start](#trim-start)

- [uppercase?](#uppercase)

- [use-nil](#use-nil)

- [use-placeholder](#use-placeholder)

- [use-replacement](#use-replacement)

- [use-replacements](#use-replacements)

---

### abc

```
@description
Takes the value 'a' (converted to string) and value 'b' (converted to string)
and returns the one that is less than in alphabetical order.
```

```
@param (*) a
@param (*) b
```

```
@usage
(abc "abc" "def")
```

```
@example
(abc "abc" "def")
=>
"abc"
```

```
@example
(abc "def" "abc")
=>
"abc"
```

```
@example
(abc "abc" "abc")
=>
"abc"
```

```
@example
(abc 10 12)
=>
"10"
```

```
@example
(abc "" "abc")
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn abc
  [a b]
  (let [a (str a)
        b (str b)]
       (if (check/abc? a b)
           (-> a)
           (-> b))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [abc]]))

(string.api/abc ...)
(abc            ...)
```

</details>

---

### abc?

```
@description
Returns TRUE if the given 'a' and 'b' values (converted to string) are in alphabetical order.
```

```
@param (*) a
@param (*) b
```

```
@usage
(abc? "abc" "def")
```

```
@example
(abc? "abc" "def")
=>
true
```

```
@example
(abc? "abc" "abc")
=>
true
```

```
@example
(abc? 10 12)
=>
true
```

```
@example
(abc? "" "abc")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn abc?
  [a b]
  (let [a (str a)
        b (str b)]
       (>= 0 (compare a b))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [abc?]]))

(string.api/abc? ...)
(abc?            ...)
```

</details>

---

### after-first-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
```

```
@example
(after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
=>
" really awake; but you're never really asleep."
```

```
@example
(after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "abc")
=>
nil
```

```
@example
(after-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "abc"
                       {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(after-first-occurence nil "abc")
=>
nil
```

```
@example
(after-first-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/index-of o x)]
                              (subs n (+ dex (count x)))
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [after-first-occurence]]))

(string.api/after-first-occurence ...)
(after-first-occurence            ...)
```

</details>

---

### after-last-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "never")
```

```
@example
(after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "never")
=>
"really asleep."
```

```
@example
(after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "abc")
=>
nil
```

```
@example
(after-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "abc"
                      {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(after-last-occurence nil "abc")
=>
nil
```

```
@example
(after-last-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/last-index-of o x)]
                              (subs n (+ dex (count x)))
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [after-last-occurence]]))

(string.api/after-last-occurence ...)
(after-last-occurence            ...)
```

</details>

---

### append

```
@param (*) n
@param (*) x
@param (*)(opt) separator
```

```
@usage
(append "https://" "my-domain.com")
```

```
@example
(append "https://" "my-domain.com")
=>
"https://my-domain.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn append
  ([n x]
   (append n x nil))

  ([n x separator]
   (suffix n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [append]]))

(string.api/append ...)
(append            ...)
```

</details>

---

### apply-on-part

```
@description
Applies the given 'f' function on a specific part of the 'n' value (converted to string).
```

```
@param (*) n
@param (integer) from
@param (integer)(opt) to
@param (function) f
```

```
@usage
(apply-on-part "My string" 3 4 string.api/to-uppercase)
```

```
@example
(apply-on-part "My string" 3 4 string.api/to-uppercase)
=>
"My String"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn apply-on-part
  ([n from f]
   (apply-on-part n from (-> n str count) f))

  ([n from to f]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (-> n (cursor/cursor-in-bounds? from))
                 (-> n (cursor/cursor-in-bounds? to)))
            (str (-> n (subs 0 from))
                 (-> n (subs from to) f)
                 (-> n (subs to)))
            (-> n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [apply-on-part]]))

(string.api/apply-on-part ...)
(apply-on-part            ...)
```

</details>

---

### before-first-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "never")
```

```
@example
(before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "never")
=>
"With insomnia, you're "
```

```
@example
(before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "abc")
=>
nil
```

```
@example
(before-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "abc"
                        {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(before-first-occurence nil "abc")
=>
nil
```

```
@example
(before-first-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/index-of o x)]
                              (subs n 0 dex)
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [before-first-occurence]]))

(string.api/before-first-occurence ...)
(before-first-occurence            ...)
```

</details>

---

### before-last-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
```

```
@example
(before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
=>
"With insomnia, you're never really awake; but you're "
```

```
@example
(before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "abc")
=>
nil
```

```
@example
(before-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "abc"
                       {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(before-last-occurence nil "abc")
=>
nil
```

```
@example
(before-last-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/last-index-of o x)]
                              (subs n 0 dex)
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [before-last-occurence]]))

(string.api/before-last-occurence ...)
(before-last-occurence            ...)
```

</details>

---

### between-occurences

```
@param (*) n
@param (*) x
@param (*) y
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true}
```

```
@usage
(between-occurences "With insomnia, you're never really awake; but you're never really asleep."
                    "never" "never")
```

```
@example
(between-occurences "With insomnia, you're never really awake; but you're never really asleep."
                    "never" "asleep.")
=>
" really awake; but you're never really "
```

```
@example
(between-occurences "With insomnia, you're never really awake; but you're never really asleep."
                    "never" "never")
=>
" really awake; but you're "
```

```
@example
(between-occurences "With insomnia, you're never really awake; but you're never really asleep."
                    "abc" "never")
=>
nil
```

```
@example
(between-occurences "With insomnia, you're never really awake; but you're never really asleep."
                    "abc" "def")
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
  ([n x y]
   (between-occurences n x y {}))

  ([n x y {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (-> n (after-first-occurence x {:return? false :case-sensitive? case-sensitive?})
         (before-last-occurence y {:return? false :case-sensitive? case-sensitive?}))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [between-occurences]]))

(string.api/between-occurences ...)
(between-occurences            ...)
```

</details>

---

### blank?

```
@description
Returns TRUE if the given 'n' value is an empty string.
```

```
@param (*) n
```

```
@usage
(blank? "")
```

```
@example
(blank? "abc")
=>
false
```

```
@example
(blank? "")
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
  (and (-> n string?)
       (-> n empty?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [blank?]]))

(string.api/blank? ...)
(blank?            ...)
```

</details>

---

### contains-digit?

```
@description
Returns TRUE if the given 'n' value (converted to string) contains at least one digit.
```

```
@param (*) n
```

```
@usage
(contains-digit? "abc1")
```

```
@example
(contains-digit? "abc1")
=>
true
```

```
@example
(contains-digit? "abc")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-digit?
  [n]
  (let [n (str n)]
       (some? (re-find #"\d" n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [contains-digit?]]))

(string.api/contains-digit? ...)
(contains-digit?            ...)
```

</details>

---

### contains-lowercase-letter?

```
@description
Returns TRUE if the given 'n' value (converted to string) contains at least one lowercase letter.
```

```
@param (*) n
```

```
@usage
(contains-lowercase-letter? "abc")
```

```
@example
(contains-lowercase-letter? "abc")
=>
true
```

```
@example
(contains-lowercase-letter? "ABC")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-lowercase-letter?
  [n]
  (let [n (str n)]
       (not= n (clojure.string/upper-case n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [contains-lowercase-letter?]]))

(string.api/contains-lowercase-letter? ...)
(contains-lowercase-letter?            ...)
```

</details>

---

### contains-part?

```
@description
Returns TRUE if the given 'n' value (converted to string) contains the given 'x' value (converted to string).
```

```
@param (*) n
@param (*) x
```

```
@usage
(contains-part? "abc" "ab")
```

```
@example
(contains-part? "abc" "ab")
=>
true
```

```
@example
(contains-part? "abc" "cd")
=>
false
```

```
@example
(contains-part? "abc" "")
=>
true
```

```
@example
(contains-part? "abc" nil)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-part?
  [n x]
  (let [n (str n)
        x (str x)]
       (clojure.string/includes? n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [contains-part?]]))

(string.api/contains-part? ...)
(contains-part?            ...)
```

</details>

---

### contains-uppercase-letter?

```
@description
Returns TRUE if the given 'n' value (converted to string) contains at least one uppercase letter.
```

```
@param (*) n
```

```
@usage
(contains-uppercase-letter? "ABC")
```

```
@example
(contains-uppercase-letter? "ABC")
=>
true
```

```
@example
(contains-uppercase-letter? "abc")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-uppercase-letter?
  [n]
  (let [n (str n)]
       (not= n (clojure.string/lower-case n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [contains-uppercase-letter?]]))

(string.api/contains-uppercase-letter? ...)
(contains-uppercase-letter?            ...)
```

</details>

---

### count-occurences

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:separate-matches? (boolean)(opt)
  Default: false}
```

```
@usage
(count-occurences "abc" "a")
```

```
@example
(count-occurences "abca" "a")
=>
2
```

```
@example
(count-occurences "abca" "ab")
=>
1
```

```
@example
(count-occurences "aaaa" "aa")
=>
3
```

```
@example
(count-occurences "aaaa" "aa" {:separate-matches? true})
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn count-occurences
  ([n x]
   (count-occurences n x {}))

  ([n x {:keys [separate-matches?]}]
   (let [n (str n)
         x (str x)]
        (if (check/contains-part? n x)
            (let [step (if separate-matches? (count x) 1)]
                 (letfn [(f [cursor match-count]
                            (if-let [first-dex (dex/first-dex-of (cut/part n cursor) x)]
                                    (let [step (if separate-matches? (count x) 1)]
                                         (f (+   cursor first-dex step)
                                            (inc match-count)))
                                    (-> match-count)))]
                        (f 0 0)))
            (-> 0)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [count-occurences]]))

(string.api/count-occurences ...)
(count-occurences            ...)
```

</details>

---

### cover

```
@param (*) n
@param (*) x
@param (integer)(opt) offset
```

```
@usage
(cover "user@email.com" "**")
```

```
@example
(cover "user@email.com" "**")
=>
"**er@email.com"
```

```
@example
(cover "user@email.com" "**" 2)
=>
"us**@email.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn cover
  ([n x]
   (cover n x 0))

  ([n x offset]
   (let [n (str n)
         x (str x)]
        (str (subs n 0 offset)
             (subs x 0 (- (count n) offset))
             (subs n   (+ (count x) offset))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [cover]]))

(string.api/cover ...)
(cover            ...)
```

</details>

---

### cursor-in-bounds?

```
@description
- Returns TRUE if the given 'cursor' value is falls between 0 and the highest possible cursor value ('(count n)').
- Cursors are different from indexes in strings.
  A cursor could be at the very end of the string while an index could only point to the last character at the end.
```

```
@param (*) n
@param (integer) cursor
```

```
@usage
(cursor-in-bounds? "abc" 3)
```

```
@example
(cursor-in-bounds? "abc" 3)
=>
true
```

```
@example
(cursor-in-bounds? "abc" 4)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn cursor-in-bounds?
  [n cursor]
  (let [n (str n)]
       (and (-> cursor nat-int?)
            (-> n count (>= cursor)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [cursor-in-bounds?]]))

(string.api/cursor-in-bounds? ...)
(cursor-in-bounds?            ...)
```

</details>

---

### cursor-out-of-bounds?

```
@description
- Returns TRUE if the given 'cursor' value is NOT falls between 0 and the highest possible cursor value ('(count n)').
- Cursors are different from indexes in strings.
  A cursor could be at the very end of the string while an index could only point to the last character at the end.
```

```
@param (*) n
@param (integer) cursor
```

```
@usage
(cursor-out-of-bounds? "abc" 4)
```

```
@example
(cursor-out-of-bounds? "abc" 4)
=>
true
```

```
@example
(cursor-out-of-bounds? "abc" 3)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn cursor-out-of-bounds?
  [n cursor]
  (let [n (str n)]
       (or (-> cursor nat-int? not)
           (-> n count (< cursor)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [cursor-out-of-bounds?]]))

(string.api/cursor-out-of-bounds? ...)
(cursor-out-of-bounds?            ...)
```

</details>

---

### cut

```
@description
Returns a given 'n' value (converted to string) after a specific range is removed.
```

```
@param (*) n
@param (integer)(opt) start
@param (integer) end
```

```
@usage
(cut "abc" 0 2)
```

```
@example
(cut "abcdef" 2 4)
=>
"abef"
```

```
@example
(cut "abcdef" 4 2)
=>
"abef"
```

```
@example
(cut 12345 2 4)
=>
"125"
```

```
@example
(cut [:a :b] 0 3)
=>
" :b]"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn cut
  ([n end]
   (cut n 0 end))

  ([n start end]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (-> n (cursor/cursor-in-bounds? start))
                 (-> n (cursor/cursor-in-bounds? end)))
            (str (subs n 0 (min start end))
                 (subs n   (max start end)))
            (-> n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [cut]]))

(string.api/cut ...)
(cut            ...)
```

</details>

---

### dex-in-bounds?

```
@description
- Returns TRUE if the given 'dex' value is falls between 0 and the highest possible index value ('(-> n count dec)').
- Cursors are different from indexes in strings.
  A cursor could be at the very end of the string while an index could only point to the last character at the end.
```

```
@param (*) n
@param (integer) dex
```

```
@usage
(dex-in-bounds? "abc" 2)
```

```
@example
(dex-in-bounds? "abc" 2)
=>
true
```

```
@example
(dex-in-bounds? "abc" 3)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-in-bounds?
  [n dex]
  (let [n (str n)]
       (and (-> dex nat-int?)
            (-> n count (> dex)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [dex-in-bounds?]]))

(string.api/dex-in-bounds? ...)
(dex-in-bounds?            ...)
```

</details>

---

### dex-out-of-bounds?

```
@description
- Returns TRUE if the given 'dex' value is NOT falls between 0 and the highest possible index value ('(-> n count dec)').
- Cursors are different from indexes in strings.
  A cursor could be at the very end of the string while an index could only point to the last character at the end.
```

```
@param (*) n
@param (integer) dex
```

```
@usage
(dex-out-of-bounds? "abc" 3)
```

```
@example
(dex-out-of-bounds? "abc" 3)
=>
true
```

```
@example
(dex-out-of-bounds? "abc" 2)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-out-of-bounds?
  [n dex]
  (let [n (str n)]
       (or (-> dex nat-int? not)
           (-> n count (<= dex)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [dex-out-of-bounds?]]))

(string.api/dex-out-of-bounds? ...)
(dex-out-of-bounds?            ...)
```

</details>

---

### ends-with!

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(ends-with! "The things you used to own, now they own you." ".")
```

```
@example
(ends-with! "The things you used to own, now they own you." ".")
=>
"The things you used to own, now they own you."
```

```
@example
(ends-with! "The things you used to own, now they own you" ".")
=>
"The things you used to own, now they own you."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn ends-with!
  ([n x]
   (ends-with! n x {}))

  ([n x options]
   (if (ends-with? n x options)
       (->  n)
       (str n x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [ends-with!]]))

(string.api/ends-with! ...)
(ends-with!            ...)
```

</details>

---

### ends-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(ends-with? "The things you used to own, now they own you." ".")
```

```
@example
(ends-with? "The things you used to own, now they own you." ".")
=>
true
```

```
@example
(ends-with? "The things you used to own, now they own you." "")
=>
true
```

```
@example
(ends-with? "The things you used to own, now they own you." "!")
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
  ([n x]
   (ends-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (if case-sensitive? (clojure.string/ends-with? n x)
                            (clojure.string/ends-with? (clojure.string/lower-case n)
                                                       (clojure.string/lower-case x))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [ends-with?]]))

(string.api/ends-with? ...)
(ends-with?            ...)
```

</details>

---

### filter-characters

```
@param (*) n
@param (vector) allowed-characters
```

```
@example
(filter-characters "+3630 / 123 - 4567" ["+" "1" "2" "3" "4" "5" "6" "7" "8" "9" "0"])
=>
"+36301234567"
```

```
@example
(filter-characters [:a :b] [":" "a" "b"])
=>
":a:b"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn filter-characters
  [n allowed-characters]
  (letfn [(f [result x] (if (some #(= x %) allowed-characters)
                            (str result x) result))]
         (reduce f "" (str n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [filter-characters]]))

(string.api/filter-characters ...)
(filter-characters            ...)
```

</details>

---

### first-character

```
@description
- Returns the first character of the given 'n' value (converted to string.)
- Converts the output to string because one character long strings (in Java language) could be character types!
```

```
@param (*) n
@param (string) 
```

```
@usage
(first-character "abc")
```

```
@example
(first-character "abc")
=>
"a"
```

```
@example
(first-character {:a "A"})
=>
"{"
```

<details>
<summary>Source code</summary>

```
(defn first-character
  [n]
  (let [n (str n)]
       (-> n first str)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [first-character]]))

(string.api/first-character ...)
(first-character            ...)
```

</details>

---

### first-dex-of

```
@description
Returns the index of the first occurence of the given 'x' value (converted to string)
in the given 'n' value (converted to string).
```

```
@param (*) n
@param (*) x
```

```
@usage
(first-dex-of "abc abc" "a")
```

```
@example
(first-dex-of "abc abc" "a")
=>
0
```

```
@example
(first-dex-of "abc abc" "")
=>
0
```

```
@example
(first-dex-of "abc abc" nil)
=>
0
```

```
@example
(first-dex-of "abc abc" "d")
=>
nil
```

```
@example
(first-dex-of [[]] "]")
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn first-dex-of
  [n x]
  (clojure.string/index-of (str n)
                           (str x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [first-dex-of]]))

(string.api/first-dex-of ...)
(first-dex-of            ...)
```

</details>

---

### from-first-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "never")
```

```
@example
(from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "never")
=>
"never really awake; but you're never really asleep."
```

```
@example
(from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "abc")
=>
nil
```

```
@example
(from-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                      "abc"
                      {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(from-first-occurence nil "abc")
=>
nil
```

```
@example
(from-first-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/index-of o x)]
                              (subs n dex)
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [from-first-occurence]]))

(string.api/from-first-occurence ...)
(from-first-occurence            ...)
```

</details>

---

### from-last-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                     "never")
```

```
@example
(from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                     "never")
=>
"never really asleep."
```

```
@example
(from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                     "abc")
=>
nil
```

```
@example
(from-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                     "abc"
                     {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(from-last-occurence nil "abc")
=>
nil
```

```
@example
(from-last-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/last-index-of o x)]
                              (subs n dex)
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [from-last-occurence]]))

(string.api/from-last-occurence ...)
(from-last-occurence            ...)
```

</details>

---

### if-contains-part

```
@description
Returns the given 'n' value (converted to string) if it contains the given 'x' part.
```

```
@param (*) n
@param (*) x
```

```
@usage
(if-contains-part "abc" "ab")
```

```
@example
(if-contains-part "abc" "ab")
=>
"abc"
```

```
@example
(if-contains-part "abc" "cd")
=>
nil
```

```
@example
(if-contains-part "abc" "")
=>
"abc"
```

```
@example
(if-contains-part "abc" nil)
=>
"abc"
```

```
@example
(if-contains-part [:a] "[:")
=>
"[:a]"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn if-contains-part
  [n x]
  (let [n (str n)
        x (str x)]
       (if (-> n (clojure.string/includes? x))
           (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [if-contains-part]]))

(string.api/if-contains-part ...)
(if-contains-part            ...)
```

</details>

---

### insert-part

```
@param (*) n
@param (*) x
@param (integer) cursor
```

```
@usage
(insert-part "abcd" "xx" 2)
```

```
@example
(insert-part "abcd" "xx" 2)
=>
"abxxcd"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn insert-part
  [n x cursor]
  (let [n (str n)]
       (if (-> n (cursor/cursor-in-bounds? cursor))
           (str (subs n 0 cursor) x
                (subs n   cursor)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [insert-part]]))

(string.api/insert-part ...)
(insert-part            ...)
```

</details>

---

### join

```
@param (collection) coll
@param (*) separator
@param (map)(opt) options
{:join-empty? (boolean)(opt)
  Default: true}
```

```
@usage
(join ["filename" "extension"] ".")
```

```
@example
(join ["filename" "extension"] ".")
=>
"filename.extension"
```

```
@example
(join ["a" "b" ""] ".")
=>
"a.b."
```

```
@example
(join ["a" "b" ""] "." {:join-empty? false})
=>
"a.b"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn join
  ([coll separator]
   (join coll separator {}))

  ([coll separator {:keys [join-empty?] :or {join-empty? true}}]
   (let [last-dex (-> coll count dec)]
        (letfn [(separate? [dex] (and (not= dex last-dex)
                                      (-> (nth coll (inc dex)) str empty? not)))
                (join? [part] (or join-empty? (-> part str empty? not)))
                (f [result dex part] (if (join? part)
                                         (if (separate? dex)
                                             (str result part separator)
                                             (str result part))
                                         (-> result)))]
               (reduce-kv f "" (vec coll))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [join]]))

(string.api/join ...)
(join            ...)
```

</details>

---

### last-character

```
@description
- Returns the first character of the given 'n' value (converted to string.)
- Converts the output to string because one character long strings (in Java language) could be character types!
```

```
@param (*) n
@param (string) 
```

```
@usage
(last-character "abc")
```

```
@example
(last-character "abc")
=>
"c"
```

```
@example
(last-character {:a "A"})
=>
"}"
```

<details>
<summary>Source code</summary>

```
(defn last-character
  [n]
  (let [n (str n)]
       (-> n last str)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [last-character]]))

(string.api/last-character ...)
(last-character            ...)
```

</details>

---

### last-dex-of

```
@description
Returns the index of the last occurence of the given 'x' value (converted to string)
in the given 'n' value (converted to string).
```

```
@param (*) n
@param (*) x
```

```
@usage
(last-dex-of "abc abc" "a")
```

```
@example
(last-dex-of "abc abc" "a")
=>
4
```

```
@example
(last-dex-of "abc abc" "")
=>
7
```

```
@example
(last-dex-of "abc abc" nil)
=>
7
```

```
@example
(last-dex-of "abc abc" "d")
=>
nil
```

```
@example
(last-dex-of [[]] "]")
=>
3
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn last-dex-of
  [n x]
  (clojure.string/last-index-of (str n)
                                (str x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [last-dex-of]]))

(string.api/last-dex-of ...)
(last-dex-of            ...)
```

</details>

---

### length

```
@param (*) n
```

```
@usage
(length "One Flew Over the Cuckoo's Nest")
```

```
@example
(length "One Flew Over the Cuckoo's Nest")
=>
31
```

```
@example
(length [])
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn length
  [n]
  (let [n (str n)]
       (if (-> n empty?)
           (-> 0)
           (-> n count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [length]]))

(string.api/length ...)
(length            ...)
```

</details>

---

### length-between?

```
@description
Returns TRUE if the given 'n' value (converted to string) has a length between the given 'min' and 'max' values.
```

```
@param (*) n
@param (integer) min
@param (integer) max
```

```
@example
(length-between? "abc" 3 4)
=>
true
```

```
@example
(length-between? "abc" 2 4)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn length-between?
  [n min max]
  (let [n (str n)]
       (and (<= min (count n))
            (>= max (count n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [length-between?]]))

(string.api/length-between? ...)
(length-between?            ...)
```

</details>

---

### length-max?

```
@description
Returns TRUE if the given 'n' value (converted to string) has a length that is not greater than the given 'max'.
```

```
@param (*) n
@param (integer) max
```

```
@usage
(length-max? "abc" 3)
```

```
@example
(length-max? "abc" 3)
=>
true
```

```
@example
(length-max? "abc" 2)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn length-max?
  [n max]
  (let [n (str n)]
       (and (-> max integer?)
            (>= max (-> n count)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [length-max?]]))

(string.api/length-max? ...)
(length-max?            ...)
```

</details>

---

### length-min?

```
@description
Returns TRUE if the given 'n' value (converted to string) has a length that is not smaller than the given 'min'.
```

```
@param (*) n
@param (integer) min
```

```
@usage
(length-min? "abc" 3)
```

```
@example
(length-min? "abc" 3)
=>
true
```

```
@example
(length-min? "abc" 4)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn length-min?
  [n min]
  (let [n (str n)]
       (and (-> min integer?)
            (<= min (-> n count)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [length-min?]]))

(string.api/length-min? ...)
(length-min?            ...)
```

</details>

---

### length?

```
@description
Returns TRUE if the given 'n' value (converted to string) has the exact same length as the given 'length' value.
```

```
@param (*) n
@param (integer) length
```

```
@example
(length? "abc" 3)
=>
true
```

```
@example
(length? "abc" 2)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn length?
  [n length]
  (let [n (str n)]
       (= length (-> n count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [length?]]))

(string.api/length? ...)
(length?            ...)
```

</details>

---

### line-count

```
@description
Returns the count of newlines in the given string.
```

```
@param (*) n
```

```
@usage
(line-count "abc\n")
```

```
@example
(line-count "abc\n")
=>
1
```

```
@example
(line-count "abc")
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn line-count
  [n]
  (get (-> n str frequencies) \newline))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [line-count]]))

(string.api/line-count ...)
(line-count            ...)
```

</details>

---

### lowercase?

```
@description
Returns TRUE if the given 'n' value (converted to string) is lowercase.
```

```
@param (*) n
```

```
@usage
(lowercase? "abc")
```

```
@example
(lowercase? "abc")
=>
true
```

```
@example
(lowercase? "Abc")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn lowercase?
  [n]
  (let [n (str n)]
       (= n (clojure.string/lower-case n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [lowercase?]]))

(string.api/lowercase? ...)
(lowercase?            ...)
```

</details>

---

### max-length

```
@param (*) n
@param (integer) limit
@param (*)(opt) suffix
```

```
@usage
(max-length "One Flew Over the Cuckoo's Nest" 5)
```

```
@example
(max-length "One Flew Over the Cuckoo's Nest" 10)
=>
"One Flew O"
```

```
@example
(max-length "One Flew Over the Cuckoo's Nest" 10 " ...")
=>
"One Flew O ..."
```

```
@example
(max-length nil 10)
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn max-length
  [n limit & [suffix]]
  (let [n (str n)]
       (if (and (-> n empty? not)
                (-> n (dex/dex-in-bounds? limit)))
           (str (subs n 0 limit) suffix)
           (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [max-length]]))

(string.api/max-length ...)
(max-length            ...)
```

</details>

---

### max-lines

```
@description
- Limits the size of the given string by removing the end of it from the nth newline.
- With the '{:reverse? true}' setting it removes the beginning of the given string instead of the end.
```

```
@param (*) n
@param (integer) limit
@param (map)(opt) options
{:reverse? (boolean)(opt)
  Default: false}
```

```
@usage
(max-lines "abc\ndef" 1)
```

```
@example
(max-lines "abc\ndef\nghi" 2)
=>
"abc\ndef"
```

```
@example
(max-lines "abc\ndef\nghi" 2 {:reverse? true})
=>
"def\nghi"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn max-lines
  ([n limit]
   (max-lines n limit {}))

  ([n limit {:keys [reverse?]}]
   (let [n     (str        n)
         lines (core/split n #"\n")
         count (count      lines)
         limit (min        limit count)
         lines (if reverse? (subvec lines (- count limit) count)
                            (subvec lines 0 limit))]
        (letfn [(f [result dex]
                   (if (= dex limit)
                       (-> result)
                       (f (str result (if (not= dex 0) "\n") (nth lines dex))
                          (inc dex))))]
               (f "" 0)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [max-lines]]))

(string.api/max-lines ...)
(max-lines            ...)
```

</details>

---

### max-occurence?

```
@param (*) n
@param (*) x
@param (integer) max
@param (map)(opt) options
{:separate-matches? (boolean)(opt)
  Default: false}
```

```
@usage
(max-occurence? "abc" "a" 1)
```

```
@example
(max-occurence? "abc abc" "a" 2)
=>
true
```

```
@example
(max-occurence? "abc abc abc" "a" 2)
=>
false
```

```
@example
(max-occurence? "aaaa" "aa" 2)
=>
false
```

```
@example
(max-occurence? "aaaa" "aa" 2 {:separate-matches? true})
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn max-occurence?
  ([n x max]
   (max-occurence? n x max {}))

  ([n x max options]
   (let [occurence-count (count-occurences n x options)]
        (>= max occurence-count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [max-occurence?]]))

(string.api/max-occurence? ...)
(max-occurence?            ...)
```

</details>

---

### min-occurence?

```
@param (*) n
@param (*) x
@param (integer) min
@param (map)(opt) options
{:separate-matches? (boolean)(opt)
  Default: false}
```

```
@usage
(min-occurence? "abc" "a" 1)
```

```
@example
(min-occurence? "abc abc" "a" 2)
=>
true
```

```
@example
(min-occurence? "abc abc" "a" 3)
=>
false
```

```
@example
(min-occurence? "aaaa" "aa" 3)
=>
true
```

```
@example
(min-occurence? "aaaa" "aa" 3 {:separate-matches? true})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn min-occurence?
  ([n x min]
   (min-occurence? n x min {}))

  ([n x min options]
   (let [occurence-count (count-occurences n x options)]
        (<= min occurence-count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [min-occurence?]]))

(string.api/min-occurence? ...)
(min-occurence?            ...)
```

</details>

---

### multiply

```
@param (*) n
@param (integer) x
```

```
@usage
(multiply "a" 3)
```

```
@example
(multiply "a" 3)
=>
"aaa"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn multiply
  [n x]
  (when (integer? x)
        (letfn [(f [result _]
                   (str result n))]
               (reduce f "" (range 0 x)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [multiply]]))

(string.api/multiply ...)
(multiply            ...)
```

</details>

---

### nonblank?

```
@description
Returns TRUE if the given 'n' value is a nonempty string.
```

```
@param (*) n
```

```
@usage
(nonblank? "abc")
```

```
@example
(nonblank? "")
=>
false
```

```
@example
(nonblank? "abc")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonblank?
  [n]
  (and (-> n string?)
       (-> n empty? not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [nonblank?]]))

(string.api/nonblank? ...)
(nonblank?            ...)
```

</details>

---

### not-ends-with!

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(not-ends-with! "The things you used to own, now they own you." ".")
```

```
@example
(not-ends-with! "The things you used to own, now they own you" ".")
=>
"The things you used to own, now they own you"
```

```
@example
(not-ends-with! "The things you used to own, now they own you." ".")
=>
"The things you used to own, now they own you"
```

```
@example
(not-ends-with! nil ".")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn not-ends-with!
  ([n x]
   (not-ends-with! n x {}))

  ([n x options]
   (if (ends-with?                n x options)
       (cut/before-last-occurence n x)
       (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [not-ends-with!]]))

(string.api/not-ends-with! ...)
(not-ends-with!            ...)
```

</details>

---

### not-ends-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(not-ends-with? "The things you used to own, now they own you." ".")
```

```
@example
(not-ends-with? "The things you used to own, now they own you." "!")
=>
true
```

```
@example
(not-ends-with? "The things you used to own, now they own you." ".")
=>
false
```

```
@example
(not-ends-with? "The things you used to own, now they own you." "")
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
  (let [ends-with? (ends-with? n x)]
       (not ends-with?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [not-ends-with?]]))

(string.api/not-ends-with? ...)
(not-ends-with?            ...)
```

</details>

---

### not-pass-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@example
(not-pass-with? "abc" "ab")
=>
true
```

```
@example
(not-pass-with? "abc" "abc")
=>
false
```

```
@example
(not-pass-with? "abc" "Abc")
=>
true
```

```
@example
(not-pass-with? "abc" "Abc" {:case-sensitive? false})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn not-pass-with?
  ([n x]
   (not-pass-with? n x {}))

  ([n x options]
   (let [pass-with? (pass-with? n x options)]
        (not pass-with?))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [not-pass-with?]]))

(string.api/not-pass-with? ...)
(not-pass-with?            ...)
```

</details>

---

### not-starts-with!

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(not-starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
                  "On a")
```

```
@example
(not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
                  "On a")
=>
" long enough time line, the survival rate for everyone drops to zero."
```

```
@example
(not-starts-with! " long enough time line, the survival rate for everyone drops to zero."
                  "On a")
=>
" long enough time line, the survival rate for everyone drops to zero."
```

```
@example
(not-starts-with! nil ".")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn not-starts-with!
  ([n x]
   (not-starts-with! n x {}))

  ([n x options]
   (if (starts-with?              n x options)
       (cut/after-first-occurence n x)
       (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [not-starts-with!]]))

(string.api/not-starts-with! ...)
(not-starts-with!            ...)
```

</details>

---

### not-starts-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "On a")
```

```
@example
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "The ")
=>
true
```

```
@example
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "")
=>
false
```

```
@example
(not-starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
                  "On a")
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
  (let [starts-with? (starts-with? n x)]
       (not starts-with?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [not-starts-with?]]))

(string.api/not-starts-with? ...)
(not-starts-with?            ...)
```

</details>

---

### nth-character

```
@description
- Returns the nth character of the given 'n' value (converted to string.)
- Converts the output to string because in Java language one character long strings could be character types!
```

```
@param (*) n
@param (integer) dex
@param (string) 
```

```
@usage
(nth-character "abc" 2)
```

```
@example
(nth-character "abc" 2)
=>
"c"
```

```
@example
(nth-character {:a "A"} 1)
=>
":"
```

<details>
<summary>Source code</summary>

```
(defn nth-character
  [n dex]
  (let [n (str n)]
       (if (-> n (dex/dex-in-bounds? dex))
           (-> n (nth                dex) str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [nth-character]]))

(string.api/nth-character ...)
(nth-character            ...)
```

</details>

---

### nth-dex-of

```
@description
Returns the index of the nth occurence of the given 'x' value (converted to string)
in the given 'n' value (converted to string).
```

```
@param (*) n
@param (*) x
@param (integer) dex
```

```
@usage
(nth-dex-of "abc abc" "a" 1)
```

```
@example
(nth-dex-of "abc abc abc" "a" 1)
=>
4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn nth-dex-of
  [n x dex]
  (let [n (str n)
        x (str x)]
       (when (>= dex 0)
             (letfn [(f [cursor skip]
                        (if-let [first-dex (-> n (subs cursor)
                                                 (clojure.string/index-of x))]
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
(ns my-namespace (:require [string.api :refer [nth-dex-of]]))

(string.api/nth-dex-of ...)
(nth-dex-of            ...)
```

</details>

---

### part

```
@description
Returns a specific range of the given 'n' value (converted to string).
```

```
@param (*) n
@param (integer) start
@param (integer)(opt) end
```

```
@usage
(part "abc" 0 2)
```

```
@example
(part "abcdef" 2 4)
=>
"cd"
```

```
@example
(part "abcdef" 4 2)
=>
"cd"
```

```
@example
(part 12345 2 4)
=>
"34"
```

```
@example
(part [:a :b] 0 6)
=>
"[:a, :"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn part
  ([n start]
   (part n start (-> n str count)))

  ([n start end]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (-> n (cursor/cursor-in-bounds? start))
                 (-> n (cursor/cursor-in-bounds? end)))
            (subs n start end)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [part]]))

(string.api/part ...)
(part            ...)
```

</details>

---

### pass-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@example
(pass-with? "abc" "ab")
=>
false
```

```
@example
(pass-with? "abc" "abc")
=>
true
```

```
@example
(pass-with? "abc" "Abc")
=>
false
```

```
@example
(pass-with? "abc" "Abc" {:case-sensitive? false})
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn pass-with?
  ([n x]
   (pass-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (or (= n x)
            (and (not case-sensitive?)
                 (= (clojure.string/lower-case n)
                    (clojure.string/lower-case x)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [pass-with?]]))

(string.api/pass-with? ...)
(pass-with?            ...)
```

</details>

---

### prefix

```
@param (*) n
@param (*) x
@param (*)(opt) separator
```

```
@usage
(prefix "420" "$")
```

```
@example
(prefix "420" "$")
=>
"$420"
```

```
@example
(prefix 420 "$")
=>
"$420"
```

```
@example
(prefix "" "$")
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn prefix
  ([n x]
   (prefix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (->  n empty?)
            (->  n)
            (str x separator n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [prefix]]))

(string.api/prefix ...)
(prefix            ...)
```

</details>

---

### prepend

```
@param (*) n
@param (*) x
@param (*)(opt) separator
```

```
@usage
(prepend "my-domain.com" "https://")
```

```
@example
(prepend "my-domain.com" "https://")
=>
"https://my-domain.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn prepend
  ([n x]
   (prepend n x nil))

  ([n x separator]
   (prefix n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [prepend]]))

(string.api/prepend ...)
(prepend            ...)
```

</details>

---

### remove-first-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true}
```

```
@usage
(remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "never")
```

```
@example
(remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "never")
=>
"With insomnia, you're really awake; but you're never really asleep."
```

```
@example
(remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                        "abc")
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-first-occurence
  ([n x]
   (remove-first-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/index-of o x)]
                              (str (subs n 0 dex)
                                   (subs n (+ dex (count x))))
                              (-> n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [remove-first-occurence]]))

(string.api/remove-first-occurence ...)
(remove-first-occurence            ...)
```

</details>

---

### remove-last-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true}
```

```
@usage
(remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
```

```
@example
(remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "never")
=>
"With insomnia, you're never really awake; but you're really asleep."
```

```
@example
(remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                       "abc")
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-last-occurence
  ([n x]
   (remove-last-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/last-index-of o x)]
                              (str (subs n 0 dex)
                                   (subs n (+ dex (count x))))
                              (-> n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [remove-last-occurence]]))

(string.api/remove-last-occurence ...)
(remove-last-occurence            ...)
```

</details>

---

### remove-newlines

```
@param (*) n
```

```
@usage
(remove-newlines "abc\r\n")
```

```
@example
(remove-newlines "abc\r\n")
=>
"abc"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-newlines
  [n]
  (clojure.string/replace (str n) #"[\r\n]" ""))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [remove-newlines]]))

(string.api/remove-newlines ...)
(remove-newlines            ...)
```

</details>

---

### remove-part

```
@param (*) n
@param (regex pattern or string) x
```

```
@usage
(remove-part "abc" "b")
```

```
@example
(remove-part "abc" "b")
=>
"ac"
```

```
@example
(remove-part "abc abc" "b")
=>
"ac ac"
```

```
@example
(remove-part "abc abc 123" #"\d")
=>
"abc abc "
```

```
@example
(remove-part "///" "//")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-part
  [n x]
  (clojure.string/replace (str n) x ""))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [remove-part]]))

(string.api/remove-part ...)
(remove-part            ...)
```

</details>

---

### replace-part

```
@param (*) n
@param (regex pattern or string) x
@param (*) y
@param (map)(opt) options
{:recur? (boolean)(opt)
  Default: false}
```

```
@usage
(replace-part "abc" "b" "x")
```

```
@example
(replace-part "abc" "b" "x")
=>
"axc"
```

```
@example
(replace-part "abc" #"[b]{0,}" "x")
=>
"axc"
```

```
@example
(replace-part "abc" "b" nil)
=>
"ac"
```

```
@example
(replace-part "///" "//" "/")
=>
"//"
```

```
@example
(replace-part "///" "//" "/" {:recur? true})
=>
"/"
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

  ([n x y {:keys [recur?]}]
   (letfn [(f [n] (clojure.string/replace (str n) x
                                          (str y)))
           (r [n] (if (-> n f (= n))
                      (-> n)
                      (-> n f r)))]
          (if recur? (r n)
                     (f n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [replace-part]]))

(string.api/replace-part ...)
(replace-part            ...)
```

</details>

---

### same-length?

```
@description
Returns TRUE if the given 'a' and 'b' values (converted to string) have a the same length.
```

```
@param (*) a
@param (*) b
```

```
@usage
(same-length? "abc" "def")
```

```
@example
(same-length? "abc" "def")
=>
true
```

```
@example
(same-length? "abc" "defghi")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn same-length?
  [a b]
  (= (-> a str count)
     (-> b str count)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [same-length?]]))

(string.api/same-length? ...)
(same-length?            ...)
```

</details>

---

### second-character

```
@description
- Returns the second character of the given 'n' value (converted to string.)
- Converts the output to string because one character long strings (in Java language) could be character types!
```

```
@param (*) n
@param (string) 
```

```
@usage
(second-character "abc")
```

```
@example
(second-character "abc")
=>
"b"
```

```
@example
(second-character {:a "A"})
=>
":"
```

<details>
<summary>Source code</summary>

```
(defn second-character
  [n]
  (let [n (str n)]
       (-> n second str)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [second-character]]))

(string.api/second-character ...)
(second-character            ...)
```

</details>

---

### split

```
@param (*) n
@param (clj: regex, cljs: regex or string) delimiter
```

```
@example
(split "a.b.c" ".")
=>
["a" "b" "c"]
```

```
@example
(split "a.b.c" #".")
=>
[]
```

```
@example
(split "a.b.c" #"[.]")
=>
["a" "b" "c"]
```

```
@example
(split ".b.c" #"[.]")
=>
["" "b" "c"]
```

```
@example
(split "a.b.c" #"_")
=>
["a.b.c"]
```

```
@example
(split "" #".")
=>
[]
```

```
@return (strings in vector)
```

<details>
<summary>Source code</summary>

```
(defn split
  [n delimiter]
  (let [n (str n)]
       (cond (-> n empty?)        []
             (-> delimiter some?) (clojure.string/split n delimiter)
             :return              [n])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [split]]))

(string.api/split ...)
(split            ...)
```

</details>

---

### starts-with!

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
              "On a")
```

```
@example
(starts-with! "On a long enough time line, the survival rate for everyone drops to zero."
              "On a")
=>
"On a long enough time line, the survival rate for everyone drops to zero."
```

```
@example
(starts-with! " long enough time line, the survival rate for everyone drops to zero."
              "On a")
=>
"On a long enough time line, the survival rate for everyone drops to zero."
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn starts-with!
  ([n x]
   (starts-with! n x {}))

  ([n x options]
   (if (starts-with? n x options)
       (->  n)
       (str x n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [starts-with!]]))

(string.api/starts-with! ...)
(starts-with!            ...)
```

</details>

---

### starts-with?

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)
  Default: true}
```

```
@usage
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
              "On a")
```

```
@example
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
              "On a")
=>
true
```

```
@example
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
              "")
=>
true
```

```
@example
(starts-with? "On a long enough time line, the survival rate for everyone drops to zero."
              "The ")
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
  ([n x]
   (starts-with? n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (let [n (str n)
         x (str x)]
        (if case-sensitive? (clojure.string/starts-with? n x)
                            (clojure.string/starts-with? (clojure.string/lower-case n)
                                                         (clojure.string/lower-case x))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [starts-with?]]))

(string.api/starts-with? ...)
(starts-with?            ...)
```

</details>

---

### suffix

```
@param (*) n
@param (*) x
@param (*)(opt) separator
```

```
@usage
(suffix "420" "px")
```

```
@example
(suffix "420" "px")
=>
"420px"
```

```
@example
(suffix 420 "px")
=>
"420px"
```

```
@example
(suffix "" "px")
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn suffix
  ([n x]
   (suffix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (->  n empty?)
            (->  n)
            (str n separator x)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [suffix]]))

(string.api/suffix ...)
(suffix            ...)
```

</details>

---

### to-capitalized

```
@description
Makes the given 'n' value (converted to string) capitalized.
```

```
@param (*) n
```

```
@usage
(to-capitalized "abc")
```

```
@example
(to-capitalized "abc")
=>
"Abc"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-capitalized
  [n]
  (-> n str clojure.string/capitalize))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-capitalized]]))

(string.api/to-capitalized ...)
(to-capitalized            ...)
```

</details>

---

### to-first-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                    "never")
```

```
@example
(to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                    "never")
=>
"With insomnia, you're never"
```

```
@example
(to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                    "abc")
=>
nil
```

```
@example
(to-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
                    "abc"
                    {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(to-first-occurence nil "abc")
=>
nil
```

```
@example
(to-first-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/index-of o x)]
                              (subs n 0 (+ dex (count x)))
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-first-occurence]]))

(string.api/to-first-occurence ...)
(to-first-occurence            ...)
```

</details>

---

### to-integer

```
@description
Converts the given 'n' string to an integer.
```

```
@param (integer or string) n
```

```
@usage
(to-integer "420")
```

```
@example
(to-integer "420")
=>
420
```

```
@example
(to-integer 420)
=>
420
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn to-integer
  [n]
  #?(:cljs (cond (string?  n) (-> n js/parseInt)
                 (integer? n) (-> n))
     :clj  (cond (string?  n) (Integer. (re-find #"\d+" n))
                 (integer? n) (-> n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-integer]]))

(string.api/to-integer ...)
(to-integer            ...)
```

</details>

---

### to-last-occurence

```
@param (*) n
@param (*) x
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: true
 :return? (boolean)(opt)
  If true, returns the given 'n' value in case of no occurence has been found.
  Default: false}
```

```
@usage
(to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                   "never")
```

```
@example
(to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                   "never")
=>
"With insomnia, you're never really awake; but you're never"
```

```
@example
(to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                   "abc")
=>
nil
```

```
@example
(to-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
                   "abc"
                   {:return? true})
=>
"With insomnia, you're never really awake; but you're never really asleep."
```

```
@example
(to-last-occurence nil "abc")
=>
nil
```

```
@example
(to-last-occurence nil "abc" {:return? true})
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

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f [n o x] (if-let [dex (clojure.string/last-index-of o x)]
                              (subs n 0 (+ dex (count x)))
                              (if return? n)))]
          (if case-sensitive? (f (-> n str)
                                 (-> n str)
                                 (-> x str))
                              (f (-> n str)
                                 (-> n str clojure.string/lower-case)
                                 (-> x str clojure.string/lower-case))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-last-occurence]]))

(string.api/to-last-occurence ...)
(to-last-occurence            ...)
```

</details>

---

### to-lowercase

```
@description
Makes the given 'n' value (converted to string) lowercase.
```

```
@param (*) n
```

```
@usage
(to-lowercase "ABC")
```

```
@example
(to-lowercase "ABC")
=>
"abc"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-lowercase
  [n]
  (-> n str clojure.string/lower-case))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-lowercase]]))

(string.api/to-lowercase ...)
(to-lowercase            ...)
```

</details>

---

### to-uppercase

```
@description
Makes the given 'n' value (converted to string) uppercase.
```

```
@param (*) n
```

```
@usage
(to-uppercase "abc")
```

```
@example
(to-uppercase "abc")
=>
"ABC"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-uppercase
  [n]
  (-> n str clojure.string/upper-case))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [to-uppercase]]))

(string.api/to-uppercase ...)
(to-uppercase            ...)
```

</details>

---

### trim

```
@description
Trims both ends of the given 'n' value (converted to string) by removing the leading and trailing whitespaces.
```

```
@param (*) n
```

```
@usage
(trim "  abc  ")
```

```
@example
(trim "  abc  ")
=>
"abc"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trim
  [n]
  (-> n str clojure.string/trim))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [trim]]))

(string.api/trim ...)
(trim            ...)
```

</details>

---

### trim-end

```
@description
Trims the end of the given 'n' value (converted to string) by removing the trailing whitespaces.
```

```
@param (*) n
```

```
@usage
(trim-end "  abc  ")
```

```
@example
(trim-end "  abc  ")
=>
"abc  "
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trim-end
  [n]
  (-> n str clojure.string/trimr))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [trim-end]]))

(string.api/trim-end ...)
(trim-end            ...)
```

</details>

---

### trim-gaps

```
@description
Trims the whitespace gaps in the given 'n' value (converted to string) by removing the duplicated whitespaces.
```

```
@param (*) n
```

```
@usage
(trim-gaps "  a  b  c  ")
```

```
@example
(trim-gaps "  a  b  c  ")
=>
" a b c "
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trim-gaps
  [n]
  (-> n str (clojure.string/replace #"[\h]{1,}" " ")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [trim-gaps]]))

(string.api/trim-gaps ...)
(trim-gaps            ...)
```

</details>

---

### trim-newlines

```
@description
Trims the trailing newlines of the given 'n' value (converted to string) by removing the all newlines from the end.
```

```
@param (*) n
```

```
@usage
(trim-newlines "abc \r\n")
```

```
@example
(trim-newlines "abc \r\n")
=>
"abc "
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trim-newlines
  [n]
  (-> n str clojure.string/trim-newline))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [trim-newlines]]))

(string.api/trim-newlines ...)
(trim-newlines            ...)
```

</details>

---

### trim-start

```
@description
Trims the beginning of the given 'n' value (converted to string) by removing the leading whitespaces.
```

```
@param (*) n
```

```
@usage
(trim-start "  abc  ")
```

```
@example
(trim-start "  abc  ")
=>
"abc  "
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trim-start
  [n]
  (-> n str clojure.string/triml))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [trim-start]]))

(string.api/trim-start ...)
(trim-start            ...)
```

</details>

---

### uppercase?

```
@description
Returns TRUE if the given 'n' value (converted to string) is uppercase.
```

```
@param (*) n
```

```
@usage
(uppercase? "ABC")
```

```
@example
(uppercase? "ABC")
=>
true
```

```
@example
(uppercase? "Abc")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn uppercase?
  [n]
  (let [n (str n)]
       (= n (clojure.string/upper-case n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [uppercase?]]))

(string.api/uppercase? ...)
(uppercase?            ...)
```

</details>

---

### use-nil

```
@param (*) n
```

```
@usage
(use-nil "")
```

```
@example
(use-nil "")
=>
nil
```

```
@example
(use-nil "abc")
=>
"abc"
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn use-nil
  [n]
  (if (-> n empty?)
      (-> nil)
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [use-nil]]))

(string.api/use-nil ...)
(use-nil            ...)
```

</details>

---

### use-placeholder

```
@param (string) n
@param (string) placeholder
```

```
@usage
(use-placeholder "My content"
                 "My placeholder")
```

```
@example
(use-placeholder "My content"
                 "My placeholder")
=>
"My content"
```

```
@example
(use-placeholder ""
                 "My placeholder")
=>
"My placeholder"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn use-placeholder
  [n placeholder]
  (if (-> n check/nonblank?)
      (-> n)
      (-> placeholder)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [use-placeholder]]))

(string.api/use-placeholder ...)
(use-placeholder            ...)
```

</details>

---

### use-replacement

```
@param (*) n
@param (*) replacement
@param (map)(opt) options
{:ignore? (boolean)(opt)
  The function returns nil if the replacement is nil or empty.
  Default: true}
```

```
@usage
(use-replacement "Hi, my name is %"
                 "John")
```

```
@example
(use-replacement "Hi, my name is %"
                 "John")
=>
"Hi, my name is John"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn use-replacement
  ([n replacement]
   (use-replacement n replacement {}))

  ([n replacement {:keys [ignore?] :or {ignore? true}}]
   (if (or (-> replacement str empty? not)
           (not ignore?))
       (clojure.string/replace (str n) "%"
                               (str replacement)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [use-replacement]]))

(string.api/use-replacement ...)
(use-replacement            ...)
```

</details>

---

### use-replacements

```
@description
Replacement markers only contain numbers in case of more than one replacement passed.
If only one replacement passed, its marker is a single percent character.
```

```
@param (*) n
@param (vector) replacements
@param (map)(opt) options
{:ignore? (boolean)(opt)
  The function returns NIL if any of the replacements is NIL or empty.
  Default: true}
```

```
@usage
(use-replacements "Hi, my name is %" ["John"])
```

```
@example
(use-replacements "Hi, my name is %" ["John"])
=>
"Hi, my name is John"
```

```
@example
(use-replacements "My favorite colors are: %1, %2 and %3" ["red" "green" "blue"])
=>
"My favorite colors are: red, green and blue"
```

```
@example
(use-replacements "%1 / %2 items downloaded" [nil 3])
=>
""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn use-replacements
  ([n replacements]
   (use-replacements n replacements {}))

  ([n replacements {:keys [ignore?] :or {ignore? true}}]
   (let [n (str n)]
        (when (vector? replacements)
              (letfn [(f? [] (= 1 (count replacements)))
                      (f1 [n marker replacement]
                          (if (or (-> replacement str empty? not)
                                  (not ignore?))
                              (clojure.string/replace n marker (str replacement))))
                      (f2 [n dex replacement]
                          (let [marker (str "%" (inc dex))]
                               (f1 n marker replacement)))]
                     (if (f?) (f1 n "%" (first replacements))
                              (reduce-kv f2 n replacements)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [use-replacements]]))

(string.api/use-replacements ...)
(use-replacements            ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

