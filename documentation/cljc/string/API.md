
# string.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > string.api

### Index

- [abc](#abc)

- [abc?](#abc)

- [after-first-occurence](#after-first-occurence)

- [after-last-occurence](#after-last-occurence)

- [append](#append)

- [before-first-occurence](#before-first-occurence)

- [before-last-occurence](#before-last-occurence)

- [between-occurences](#between-occurences)

- [blank?](#blank)

- [contains-lowercase-letter?](#contains-lowercase-letter)

- [contains-part?](#contains-part)

- [contains-uppercase-letter?](#contains-uppercase-letter)

- [count-occurences](#count-occurences)

- [ends-with!](#ends-with)

- [ends-with?](#ends-with)

- [filter-characters](#filter-characters)

- [first-dex-of](#first-dex-of)

- [from-first-occurence](#from-first-occurence)

- [from-last-occurence](#from-last-occurence)

- [get-nth-character](#get-nth-character)

- [if-contains-part](#if-contains-part)

- [insert-part](#insert-part)

- [join](#join)

- [last-dex-of](#last-dex-of)

- [length](#length)

- [length?](#length)

- [line-count](#line-count)

- [max-length](#max-length)

- [max-length?](#max-length)

- [max-lines](#max-lines)

- [max-occurence?](#max-occurence)

- [min-length?](#min-length)

- [min-occurence?](#min-occurence)

- [multiply](#multiply)

- [nonblank?](#nonblank)

- [not-ends-with!](#not-ends-with)

- [not-ends-with?](#not-ends-with)

- [not-pass-with?](#not-pass-with)

- [not-starts-with!](#not-starts-with)

- [not-starts-with?](#not-starts-with)

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

- [use-nil](#use-nil)

- [use-placeholder](#use-placeholder)

- [use-replacement](#use-replacement)

- [use-replacements](#use-replacements)

### abc

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
(abc? "" "abc")
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
  (if (check/abc? a b)
      (return       a)
      (return       b)))
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
  (>= 0 (compare (str a)
                 (str b))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n (+ dex (count x)))
                (if return? n)))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n (+ dex (count x)))
                (if return? n)))))
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

### before-first-occurence

```
@param (*) n
@param (*) x
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n 0 dex)
                (if return? n)))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n 0 dex)
                (if return? n)))))
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
  [n x y]
  (-> n (after-first-occurence x {:return? false})
        (before-last-occurence y {:return? false})))
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
  (clojure.string/blank? n))
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

### contains-lowercase-letter?

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
  (not= (-> n str)
        (-> n str clojure.string/upper-case)))
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
  (clojure.string/includes? (str n)
                            (str x)))
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
  (not= (-> n str)
        (-> n str clojure.string/lower-case)))
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
                                    (return match-count)))]
                        (f 0 0)))
            (return 0)))))
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
       (return n)
       (str    n x))))
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
  (letfn [(f [o x] (if (some #(= x %) allowed-characters)
                       (str o x) o))]
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

### first-dex-of

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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n dex)
                (if return? n)))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n dex)
                (if return? n)))))
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

### get-nth-character

```
@param (*) n
@param (integer) dex
@param (string) 
```

```
@usage
(get-nth-character "abc" 2)
```

```
@example
(get-nth-character "abc" 2)
=>
"c"
```

```
@example
(get-nth-character {:a "A"} 1)
=>
":"
```

<details>
<summary>Source code</summary>

```
(defn get-nth-character
  [n dex]
  (let [n (str n)]
       (if (math/between? dex 0 (-> n count dec))
           (nth n dex))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [get-nth-character]]))

(string.api/get-nth-character ...)
(get-nth-character            ...)
```

</details>

---

### if-contains-part

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
  (if (clojure.string/includes? (str n)
                                (str x))
      (str n)))
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
@param (integer) dex
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
  [n x dex]
  (let [n     (str   n)
        count (count n)
        dex   (math/between! dex 0 count)]
       (str (subs n 0 dex) x
            (subs n   dex))))
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
        (letfn [(separate?      [dex]  (and (not= dex last-dex)
                                            (-> (nth coll (inc dex)) str empty? not)))
                (join?          [part] (or join-empty? (-> part str empty? not)))
                (f [result dex part] (if (join? part)
                                         (if (separate? dex)
                                             (str result part separator)
                                             (str result part))
                                         (return result)))]
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

### last-dex-of

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
       (if (empty? n)
           (return 0)
           (count  n))))
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

### length?

```
@param (*) n
@param (integer) length / min
@param (integer)(opt) max
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
@example
(length? "abc" 2 4)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn length?
  ([n length]
   (= length (-> n str count)))

  ([n min max]
   (let [n (str n)]
        (and (<= min (count n))
             (>= max (count n))))))
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
                (-> limit integer?)
                (<  limit (count n)))
           (str (subs n 0 limit) suffix)
           (return n))))
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

### max-length?

```
@param (*) n
@param (integer) max
```

```
@usage
(max-length? "abc" 3)
```

```
@example
(max-length? "abc" 3)
=>
true
```

```
@example
(max-length? "abc" 2)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn max-length?
  [n max]
  (and (-> max integer?)
       (>= max (-> n str count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [max-length?]]))

(string.api/max-length? ...)
(max-length?            ...)
```

</details>

---

### max-lines

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
                       (return result)
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

### min-length?

```
@param (*) n
@param (integer) min
```

```
@usage
(min-length? "abc" 3)
```

```
@example
(min-length? "abc" 3)
=>
true
```

```
@example
(min-length? "abc" 4)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn min-length?
  [n min]
  (and (-> min integer?)
       (<= min (-> n str count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [string.api :refer [min-length?]]))

(string.api/min-length? ...)
(min-length?            ...)
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
       (return                    n))))
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
       (return                    n))))
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

### nth-dex-of

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
   (let [n (str n)]
        (part n start (count n))))

  ([n start end]
   (let [n (str n)]
        (if (and (-> n empty? not)
                 (math/between? end   0 (count n))
                 (math/between? start 0 (count n)))
            (subs   n start end)
            (return n)))))
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
        (if (empty?               n)
            (return               n)
            (str prefix separator n)))))
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
  [n x]
  (let [n (str n)
        x (str x)]
       (if-let [dex (clojure.string/index-of n x)]
               (str (subs n 0 dex)
                    (subs n (+ dex (count x))))
               (return n))))
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
  [n x]
  (let [n (str n)
        x (str x)]
       (if-let [dex (clojure.string/last-index-of n x)]
               (str (subs n 0 dex)
                    (subs n (+ dex (count x))))
               (return n))))
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
  (clojure.string/replace (str n) \newline ""))
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
"abc abc"
```

```
@example
(remove-part "///" "//")
=>
""
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
 {:recursive? (boolean)(opt)
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
(replace-part "///" "//" "/" {:recursive? true})
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

  ([n x y {:keys [recursive?]}]
   (letfn [(f [n] (clojure.string/replace (str n) x
                                          (str y)))
           (r [n] (if (= n (f n))
                      (return n)
                      (r (f n))))]
          (if recursive? (r n)
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
       (return       n)
       (str          x n))))
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
        (if (empty? n)
            (return n)
            (str    n separator x)))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/index-of n x)]
                (subs n 0 (+ dex (count x)))
                (if return? n)))))
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
  #?(:cljs (cond (string?  n) (js/parseInt n)
                 (integer? n) (return      n))
     :clj  (cond (string?  n) (Integer. (re-find #"\d+" n))
                 (integer? n) (return      n))))
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
@param (map) options
{:return? (boolean)(opt)
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

  ([n x {:keys [return?]}]
   (let [n (str n)
         x (str x)]
        (if-let [dex (clojure.string/last-index-of n x)]
                (subs n 0 (+ dex (count x)))
                (if return? n)))))
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
@param (*) n
```

```
@usage
(trim " abc")
```

```
@example
(trim " abc  ")
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
  (if (empty? n)
      (return nil)
      (return n)))
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
(use-placeholder "My content" "My placeholder")
```

```
@example
(use-placeholder "My content" "My placeholder")
=>
"My content"
```

```
@example
(use-placeholder "" "My placeholder")
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
  (if (check/nonblank? n)
      (return          n)
      (return          placeholder)))
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
(use-replacement "Hi, my name is %" "John")
```

```
@example
(use-replacement "Hi, my name is %" "John")
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
@param (*) n
@param (vector) replacements
@param (map)(opt) options
 {:ignore? (boolean)(opt)
   The function returns nil if any of the replacements is nil or empty.
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
                              (clojure.string/replace n marker replacement)))
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

This documentation is generated by the [docs-api](https://github.com/bithandshake/docs-api) engine

