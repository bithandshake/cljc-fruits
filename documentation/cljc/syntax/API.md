
# <strong>syntax.api</strong> namespace
<p>Documentation of the <strong>syntax/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > syntax.api</strong>



### CamelCase

```
@param (string) n
```

```
@example
(CamelCase "camel-case")
=>
"CamelCase"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn CamelCase
  [n])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [CamelCase]]))

(syntax/CamelCase ...)
(CamelCase        ...)
```

</details>

---

### bracket

```
@param (string) n
```

```
@example
(bracket "420")
=>
"[420]"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn bracket
  [n]
  (str (when n (str "[" n "]"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [bracket]]))

(syntax/bracket ...)
(bracket        ...)
```

</details>

---

### close-brace-position

```
@param (n) 
```

```
@example
(close-brace-position "{:a 0}")
=>
5
```

```
@example
(close-brace-position "([] {:a {:b 0}})")
=>
14
```

```
@example
(close-brace-position "} {}")
=>
3
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn close-brace-position
  [n]
  (close-tag-position n "{" "}"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [close-brace-position]]))

(syntax/close-brace-position ...)
(close-brace-position        ...)
```

</details>

---

### close-bracket-position

```
@param (n) 
```

```
@example
(close-bracket-position "[1 2]")
=>
4
```

```
@example
(close-bracket-position "({} [[0 1]])")
=>
10
```

```
@example
(close-bracket-position "] []")
=>
3
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn close-bracket-position
  [n]
  (close-tag-position n "[" "]"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [close-bracket-position]]))

(syntax/close-bracket-position ...)
(close-bracket-position        ...)
```

</details>

---

### close-paren-position

```
@param (n) 
```

```
@example
(close-paren-position "(+ 1 2)")
=>
6
```

```
@example
(close-paren-position "[{} (+ 1 (inc 2) (inc 3))]")
=>
24
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn close-paren-position
  [n]
  (close-tag-position n "(" ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [close-paren-position]]))

(syntax/close-paren-position ...)
(close-paren-position        ...)
```

</details>

---

### close-tag-position

```
@param (string) n
@param (string) open-tag
@param (string) close-tag
```

```
@example
(close-tag-position "<div>My content</div>" "<div>" "</div>")
=>
15
```

```
@example
(close-tag-position "<div><div></div></div>" "<div>" "</div>")
=>
16
```

```
@example
(close-tag-position "</div> <div></div>" "<div>" "</div>")
=>
12
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn close-tag-position
  [n open-tag close-tag]
  (if (and (string/contains-part? n  open-tag)
           (string/contains-part? n close-tag))
      (letfn [(f [cursor]
                 (let [a               (string/part             n  0 cursor)
                       b               (string/part             n    cursor)
                        open-tag-count (string/count-occurences a  open-tag)
                       close-tag-count (string/count-occurences a close-tag)]
                      (if (and (>  close-tag-count 0)
                               (>= close-tag-count open-tag-count))
                          (do                              (string/last-dex-of a close-tag))
                          (if-let [close-tag-position (string/first-dex-of b close-tag)]
                                  (do                                      (f (+ close-tag-position (count close-tag) cursor)))))))]
             (f (string/first-dex-of n open-tag)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [close-tag-position]]))

(syntax/close-tag-position ...)
(close-tag-position        ...)
```

</details>

---

### open-brace-position

```
@param (n) 
```

```
@example
(open-brace-position "{:a 0}")
=>
0
```

```
@example
(open-brace-position "([] {:a {:b 0}})")
=>
4
```

```
@example
(open-brace-position "} {}")
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn open-brace-position
  [n]
  (open-tag-position n "{"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [open-brace-position]]))

(syntax/open-brace-position ...)
(open-brace-position        ...)
```

</details>

---

### open-bracket-position

```
@param (n) 
```

```
@example
(open-bracket-position "[1 2]")
=>
0
```

```
@example
(open-bracket-position "({} [[0 1]])")
=>
4
```

```
@example
(open-bracket-position "] []")
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn open-bracket-position
  [n]
  (open-tag-position n "["))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [open-bracket-position]]))

(syntax/open-bracket-position ...)
(open-bracket-position        ...)
```

</details>

---

### open-paren-position

```
@param (n) 
```

```
@example
(open-paren-position "(+ 1 2)")
=>
0
```

```
@example
(open-paren-position "[{} (+ 1 (inc 2) (inc 3))]")
=>
4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn open-paren-position
  [n]
  ; páratlan zárójel(ek) vannak benne, ezért szükésges idetenni egy szmájlit :)
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [open-paren-position]]))

(syntax/open-paren-position ...)
(open-paren-position        ...)
```

</details>

---

### open-tag-position

```
@param (string) n
@param (string) open-tag
```

```
@example
(open-tag-position "<div>My content</div>" "<div>")
=>
0
```

```
@example
(open-tag-position "<div><div></div></div>" "<div>")
=>
0
```

```
@example
(open-tag-position "</div> <div></div>" "<div>")
=>
7
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn open-tag-position
  [n open-tag]
  (string/first-dex-of n open-tag))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [open-tag-position]]))

(syntax/open-tag-position ...)
(open-tag-position        ...)
```

</details>

---

### paren

```
@param (string) n
```

```
@example
(paren "420")
=>
"(420)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn paren
  [n]
  (str (when n (str "(" n ")"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [paren]]))

(syntax/paren ...)
(paren        ...)
```

</details>

---

### percent

```
@param (string) n
```

```
@example
(percent "99.999")
=>
"99.999%"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn percent
  [n]
  (str (when n (str n "%"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [percent]]))

(syntax/percent ...)
(percent        ...)
```

</details>

---

### quotes

```
@param (string) n
```

```
@example
(quotes "420")
=>
"\"420\""
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn quotes
  [n]
  (str (when n (str "\"" n "\""))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [quotes]]))

(syntax/quotes ...)
(quotes        ...)
```

</details>

---

### snake-case

```
@param (string) n
```

```
@example
(snake-case "SnakeCase")
=>
"snake-case"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn snake-case
  [n]
  (let [count (count n)]
       (letfn [(f [result cursor]
                  (if (= count cursor)
                      (return result)
                      (let [char (subs n cursor (inc cursor))]
                           (if (= char (string/uppercase char))
                               (f (str (subs n 0 cursor)
                                       (if (not= cursor 0) "-")
                                       (string/lowercase char)
                                       (subs n (inc cursor)))
                                  (inc cursor))
                               (f result (inc cursor))))))]
              (f n 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :as syntax :refer [snake-case]]))

(syntax/snake-case ...)
(snake-case        ...)
```

</details>
