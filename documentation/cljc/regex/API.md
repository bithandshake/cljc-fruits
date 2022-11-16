
# <strong>regex.api</strong> namespace
<p>Documentation of the <strong>regex/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > <strong>[DOCUMENTATION](../../COVER.md) > regex.api</strong>



### first-dex-of

```
@param (string) n
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
  (when (string? n)
        (let [match (re-find pattern n)]
             (string/first-dex-of n match))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :as regex :refer [first-dex-of]]))

(regex/first-dex-of ...)
(first-dex-of       ...)
```

</details>

---

### last-dex-of

```
@param (string) n
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
  (when (string? n)
        (let [match (re-find pattern n)]
             (string/last-dex-of n match))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :as regex :refer [last-dex-of]]))

(regex/last-dex-of ...)
(last-dex-of       ...)
```

</details>

---

### nth-dex-of

```
@param (string) n
@param (regex pattern) pattern
```

```
@example
(nth-dex-of "abc 123 def 456" #"[\d]{1,}" 2)
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
  (when (and (string? n)
             (>= dex 1))
        (letfn [(f [cursor lap]
                   (if-let [first-dex (-> n (string/part  cursor)
                                            (first-dex-of pattern))]
                           (if (= lap dex)
                               (+ cursor first-dex)
                               (f (+ first-dex cursor 1)
                                  (inc lap)))))]
               (f 0 1))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :as regex :refer [nth-dex-of]]))

(regex/nth-dex-of ...)
(nth-dex-of       ...)
```

</details>

---

### re-match?

```
@param (string) n
@param (regex pattern) pattern
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
  (and (string? n)
       (some?              (re-matches pattern n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :as regex :refer [re-match?]]))

(regex/re-match? ...)
(re-match?       ...)
```

</details>

---

### re-mismatch?

```
@param (string) n
@param (regex pattern) pattern
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
  (or (not (string? n))
      (nil?            (re-matches pattern n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [regex.api :as regex :refer [re-mismatch?]]))

(regex/re-mismatch? ...)
(re-mismatch?       ...)
```

</details>
