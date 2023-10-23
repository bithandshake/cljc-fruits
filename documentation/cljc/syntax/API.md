
# syntax.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > syntax.api

### Index

- [ToCamelCase](#tocamelcase)

- [bracket](#bracket)

- [close-brace-position](#close-brace-position)

- [close-bracket-position](#close-bracket-position)

- [close-paren-position](#close-paren-position)

- [close-tag-position](#close-tag-position)

- [open-brace-position](#open-brace-position)

- [open-bracket-position](#open-bracket-position)

- [open-paren-position](#open-paren-position)

- [open-tag-position](#open-tag-position)

- [paren](#paren)

- [percent](#percent)

- [quotes](#quotes)

- [remove-comments](#remove-comments)

- [to-snake-case](#to-snake-case)

### ToCamelCase

```
@param (string) n
```

```
@example
(ToCamelCase "camel-case")
=>
"CamelCase"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn ToCamelCase
  [n])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [ToCamelCase]]))

(syntax.api/ToCamelCase ...)
(ToCamelCase            ...)
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
(ns my-namespace (:require [syntax.api :refer [bracket]]))

(syntax.api/bracket ...)
(bracket            ...)
```

</details>

---

### close-brace-position

```
@description
Returns the close pair position of the first opening brace character
in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [close-brace-position]]))

(syntax.api/close-brace-position ...)
(close-brace-position            ...)
```

</details>

---

### close-bracket-position

```
@description
Returns the close pair position of the first opening bracket character
in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [close-bracket-position]]))

(syntax.api/close-bracket-position ...)
(close-bracket-position            ...)
```

</details>

---

### close-paren-position

```
@description
Returns the close pair position of the first opening parenthesis character
in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [close-paren-position]]))

(syntax.api/close-paren-position ...)
(close-paren-position            ...)
```

</details>

---

### close-tag-position

```
@description
Returns the close pair position of the first occurence of the 'open-tag'
in the string 'n'.
```

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
  (if-let [offset (string/first-dex-of n open-tag)]
          (letfn [
                  (f0 [observed-part open-tag-found]
                      (+ open-tag-found (string/count-occurences observed-part open-tag)))

                  (f1 [observed-part close-tag-found]
                      (+ close-tag-found (string/count-occurences observed-part close-tag)))

                  (f2 [from] (if-let [close-tag-pos (string/first-dex-of (string/part n from) close-tag)]
                                     (+ from close-tag-pos (count close-tag))))

                  (f3 [from open-tag-found close-tag-found]


                      (if-let [to (f2 from)]
                              (let [observed-part   (string/part n from to)
                                    open-tag-found  (f0 observed-part open-tag-found)
                                    close-tag-found (f1 observed-part close-tag-found)]


                                   (if (<= open-tag-found close-tag-found)
                                       (- to (count close-tag))
                                       (f3 to open-tag-found close-tag-found)))))]

                 (f3 offset 0 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [close-tag-position]]))

(syntax.api/close-tag-position ...)
(close-tag-position            ...)
```

</details>

---

### open-brace-position

```
@description
Returns the first position of the opening brace character in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [open-brace-position]]))

(syntax.api/open-brace-position ...)
(open-brace-position            ...)
```

</details>

---

### open-bracket-position

```
@description
Returns the first position of the opening bracket character in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [open-bracket-position]]))

(syntax.api/open-bracket-position ...)
(open-bracket-position            ...)
```

</details>

---

### open-paren-position

```
@description
Returns the first position of the opening parenthesis character in the string 'n'.
```

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
  ; aren't balanced. That's why I have to put a closing parenthesis here :)
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [open-paren-position]]))

(syntax.api/open-paren-position ...)
(open-paren-position            ...)
```

</details>

---

### open-tag-position

```
@description
Returns the first position of the 'open-tag' in the string 'n'.
```

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
(ns my-namespace (:require [syntax.api :refer [open-tag-position]]))

(syntax.api/open-tag-position ...)
(open-tag-position            ...)
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
(ns my-namespace (:require [syntax.api :refer [paren]]))

(syntax.api/paren ...)
(paren            ...)
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
(ns my-namespace (:require [syntax.api :refer [percent]]))

(syntax.api/percent ...)
(percent            ...)
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
(ns my-namespace (:require [syntax.api :refer [quotes]]))

(syntax.api/quotes ...)
(quotes            ...)
```

</details>

---

### remove-comments

```
@param (string) n
@param (string) open-tag
@param (string)(opt) close-tag
 Default: "\n"
```

```
@usage
(remove-comments "(defn my-function []) \n ; My comment \n"
                 ";")
```

```
@example
(remove-comments "(defn my-function []) \n ; My comment \n"
                 ";")
=>
"(defn my-function []) \n"
```

```
@example
(remove-comments "body { /* My comment */ color: blue; }"
                 "/*"
                 "*/")
=>
"body {  color: blue; }"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-comments
  ([n open-tag]
   (remove-comments n open-tag "\n"))

  ([n open-tag close-tag]
   (letfn [
           (f0 [n] (if-let [open-pos (tags/open-tag-position n open-tag)]
                           (if-let [close-pos (tags/close-tag-position n open-tag close-tag)]
                                   (if (number? close-pos)
                                       (string/cut n open-pos (+ close-pos (count close-tag)))))))

           (f1 [n] (if-let [n (f0 n)]
                           (-> n f1)
                           (-> n)))]

          (f1 n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [remove-comments]]))

(syntax.api/remove-comments ...)
(remove-comments            ...)
```

</details>

---

### to-snake-case

```
@param (string) n
```

```
@example
(to-snake-case "SnakeCase")
=>
"snake-case"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-snake-case
  [n]
  (let [count (count n)]
       (letfn [(f [result cursor]
                  (if (= count cursor)
                      (-> result)
                      (let [char (subs n cursor (inc cursor))]
                           (if (= char (string/to-uppercase char))
                               (f (str (subs n 0 cursor)
                                       (if (not= cursor 0) "-")
                                       (string/to-lowercase char)
                                       (subs n (inc cursor)))
                                  (inc cursor))
                               (f result (inc cursor))))))]
              (f n 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [to-snake-case]]))

(syntax.api/to-snake-case ...)
(to-snake-case            ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

