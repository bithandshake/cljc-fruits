
### syntax.api

Functional documentation of the syntax.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > syntax.api

### Index

- [ToCamelCase](#tocamelcase)

- [brace](#brace)

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

- [position-commented?](#position-commented)

- [position-escaped?](#position-escaped)

- [position-quoted?](#position-quoted)

- [quotes](#quotes)

- [remove-comments](#remove-comments)

- [tag-count](#tag-count)

- [tag-position](#tag-position)

- [tags-balanced?](#tags-balanced)

- [to-snake-case](#to-snake-case)

---

### ToCamelCase

```
@description
Converts the given 'n' string from snake-case to CamelCase.
```

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
  [n]
  (letfn [
          (f0 [result cursor] (= "-" (subs result (dec cursor) cursor)))

          (f1 [result cursor] (= " " (subs result (dec cursor) cursor)))

          (f2 [result cursor]
              (let [char (subs result cursor (inc cursor))]
                   (if (re-find #"[a-z]" char)
                       (cond (= cursor 0)       (str                              (string/to-uppercase char) (subs result (inc cursor)))
                             (f0 result cursor) (str (subs result 0 (dec cursor)) (string/to-uppercase char) (subs result (inc cursor)))
                             (f1 result cursor) (str (subs result 0 cursor)       (string/to-uppercase char) (subs result (inc cursor)))
                             :return result)
                       (-> result))))

          (f3 [result cursor]
              (if (= (count result) cursor)
                  (-> result)
                  (let [new-result (f2 result cursor)]
                       (f3 new-result (+ (inc cursor) (- (count new-result) (count result)))))))]

         (f3 n 0)))
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

### brace

```
@description
Wraps the given 'n' string with a brace pair.
```

```
@param (string) n
```

```
@example
(brace ":x 420")
=>
"{:x 420}"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn brace
  [n]
  (str (when n (str "{"n"}"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [brace]]))

(syntax.api/brace ...)
(brace            ...)
```

</details>

---

### bracket

```
@description
Wraps the given 'n' string with a bracket pair.
```

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
  (str (when n (str "["n"]"))))
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
- Returns the position of the closing brace that corresponds to the first opening brace character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and does not depend on the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (close-brace-position n {}))

  ([n options]
   (close-tag-position n "{" "}" options)))
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
- Returns the position of the closing bracket that corresponds to the first opening bracket character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and does not depend on the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (close-bracket-position n {}))

  ([n options]
   (close-tag-position n "[" "]" options)))
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
- Returns the position of the closing parenthesis that corresponds to the first opening parenthesis character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and does not depend on the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (close-paren-position n {}))

  ([n options]
   (close-tag-position n "(" ")" options)))
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
- Returns the position of the close pair of the first occurence of the 'open-tag' string in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (string) open-tag
@param (string) close-tag
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
12 <- DEPRECTATED BEHAVIOUR
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn close-tag-position
  ([n open-tag close-tag]
   (close-tag-position n open-tag close-tag {}))

  ([n open-tag close-tag {:keys [offset] :or {offset 0} :as options}]
   (letfn [(f [cursor]
              (if (string/cursor-in-bounds? n cursor)
                  (if-let [observed-close-pos (tag-position n close-tag (assoc options :offset cursor))]
                          (let [observed-part (string/part n 0 (+ observed-close-pos (count close-tag)))]
                               (if (tags-balanced? observed-part open-tag close-tag options)
                                   (-> observed-close-pos)
                                   (f (+ observed-close-pos (count close-tag))))))))]
          (f offset))))
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
- Returns the position of the first opening brace character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (open-brace-position n {}))

  ([n options]
   (open-tag-position n "{" options)))
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
- Returns the position of the first opening bracket character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (open-bracket-position n {}))

  ([n options]
   (open-tag-position n "[" options)))
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
- Returns the position of the first opening parenthesis character in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n]
   (open-paren-position n {}))

  ([n options]
   (open-tag-position n "(" options)))
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
- Returns the position of the first 'open-tag' string in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (string) open-tag
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
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
  ([n open-tag]
   (open-tag-position n open-tag {}))

  ([n open-tag options]
   (tag-position n open-tag options)))
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
@description
Wraps the given 'n' string with a parenthesis pair.
```

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
  (str (when n (str "("n")"))))
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
@description
Appends a percentage symbol to the given 'n' string.
```

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
  (str (when n (str n"%"))))
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

### position-commented?

```
@description
- Returns TRUE if the given position in the 'n' string is in a commented section.
- Quoted comment tags might cause false output.
```

```
@param (string) n
@param (dex) position
@param (string) comment-open-tag
Default: ";"
@param (string)(opt) comment-close-tag
Default: "\n"
```

```
@usage
(position-commented? "(defn my-function [])\n ; My comment\n" 5)
```

```
@example
(position-commented? "(defn my-function [])\n ; My comment\n" 5)
=>
false
```

```
@example
(position-commented? "(defn my-function [])\n ; My comment\n" 25)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn position-commented?
  ([n position]
   (position-commented? n position "
  ([n position comment-open-tag]
   (position-commented? n position comment-open-tag "\n"))

  ([n position comment-open-tag comment-close-tag]
   (boolean (let [observed-part (string/part n 0 position)]
                 (if-let [last-open-pos (string/last-dex-of observed-part comment-open-tag)]
                         (-> n (string/part last-open-pos position)
                               (string/contains-part? comment-close-tag)
                               (not)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [position-commented?]]))

(syntax.api/position-commented? ...)
(position-commented?            ...)
```

</details>

---

### position-escaped?

<details>
<summary>Source code</summary>

```
(defn position-escaped?
  [n position])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [position-escaped?]]))

(syntax.api/position-escaped?)
(position-escaped?)
```

</details>

---

### position-quoted?

```
@description
Returns TRUE if the given position in the 'n' string is in a quoted section.
```

```
@param (string) n
@param (dex) position
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false}
```

```
@usage
(position-quoted? "\"My quote\" My string" 3)
```

```
@example
(position-quoted? "\"My quote\" My string" 3)
=>
true
```

```
@example
(position-quoted? "\"My quote\" My string" 13)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn position-quoted?
  [n position])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [position-quoted?]]))

(syntax.api/position-quoted? ...)
(position-quoted?            ...)
```

</details>

---

### quotes

```
@description
Wraps the given 'n' string with a quote pair.
```

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
  (str (when n (str "\""n"\""))))
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
@description
Removes the commented parts of the given 'n' string.
```

```
@param (string) n
@param (string) comment-open-tag
@param (string)(opt) comment-close-tag
 Default: "\n"
```

```
@usage
(remove-comments "(defn my-function [])\n ; My comment\n"
                 ";")
```

```
@example
(remove-comments "(defn my-function [])\n ; My comment\n"
                 ";")
=>
"(defn my-function [])\n"
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
  ([n comment-open-tag]
   (remove-comments n comment-open-tag "\n"))

  ([n comment-open-tag comment-close-tag]
   (letfn [
           (f0 [n] (if-let [comment-open-pos (tags/open-tag-position n comment-open-tag)]
                           (if-let [comment-close-pos (tags/close-tag-position n comment-open-tag comment-close-tag)]
                                   (if (number? comment-close-pos)
                                       (string/cut n comment-open-pos (+ comment-close-pos (count comment-close-tag))))
                                   (string/part n 0 comment-open-pos))))

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

### tag-count

```
@description
- Returns the found occurence count of the 'tag' string in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
```

```
@param (string) n
@param (string) tag
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
```

```
@example
(tag-count "<div><div></div></div>" "<div>")
"** ***"
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn tag-count
  ([n tag]
   (tag-count n tag {}))

  ([n tag {:keys [offset] :or {offset 0} :as options}]
   (letfn [(f [cursor found-tag-count]
              (if (string/cursor-in-bounds? n cursor)
                  (if-let [first-tag-pos (tag-position n tag (assoc options :offset cursor))]
                          (f (+ first-tag-pos (count tag)) (inc found-tag-count))
                          (-> found-tag-count))
                  (-> found-tag-count)))]
          (f offset 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [tag-count]]))

(syntax.api/tag-count ...)
(tag-count            ...)
```

</details>

---

### tag-position

```
@description
- Returns the position of the first 'tag' string in the 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
- The returned position is an absolute value and is independent from the offset.
```

```
@param (string) n
@param (string) tag
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
```

```
@example
(tag-position "<div>My content</div>" "<div>")
=>
0
```

```
@example
(tag-position "<div><div></div></div>" "<div>")
=>
0
```

```
@example
(tag-position "</div> <div></div>" "<div>")
=>
7
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn tag-position
  ([n tag]
   (tag-position n tag {}))

  ([n tag {:keys [comment-close-tag comment-open-tag ignore-comments? offset]
           :or   {comment-close-tag "\n" comment-open-tag "           :as   options}]
   (if (string/cursor-in-bounds? n offset)
       (let [observed-part (string/part n offset)]
            (if-let [observed-tag-pos (string/first-dex-of observed-part tag)]
                    (let [observed-tag-pos (+ offset observed-tag-pos)]
                         (if ignore-comments? (if (check/position-commented? n observed-tag-pos comment-open-tag comment-close-tag)
                                                  (tag-position n tag (assoc options :offset (+ observed-tag-pos (count tag))))
                                                  (-> observed-tag-pos))
                                              (-> observed-tag-pos))))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [tag-position]]))

(syntax.api/tag-position ...)
(tag-position            ...)
```

</details>

---

### tags-balanced?

```
@description
- Returns TRUE if the given 'open-tag' and 'close-tag' pairs are balanced in their quantity in the given 'n' string.
- If the 'offset' parameter is passed, the search starts from the offset position.
```

```
@param (string) n
@param (string) open-tag
@param (string) close-tag
@param (map)(opt) options
{:comment-close-tag (string)(opt)
  Default: "\n"
 :comment-open-tag (string)(opt)
  Default ";"
 :ignore-comments? (boolean)(opt)
  Default: false
 :ignore-quotes? (boolean)(opt)
  Default: false
 :offset (integer)(opt)
  Default: 0}
```

```
@example
(tags-balanced? "<div><div></div>" "<div>" "</div>")
=>
false
```

```
@example
(tags-balanced? "<div><div></div></div>" "<div>" "</div>")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn tags-balanced?
  ([n open-tag close-tag]
   (tags-balanced? n open-tag close-tag {}))

  ([n open-tag close-tag options]
   (= (tag-count n open-tag  options)
      (tag-count n close-tag options))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [syntax.api :refer [tags-balanced?]]))

(syntax.api/tags-balanced? ...)
(tags-balanced?            ...)
```

</details>

---

### to-snake-case

```
@description
Converts the given 'n' string from CamelCase to snake-case.
```

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
  (letfn [
          (f0 [result cursor] (= "-" (subs result (dec cursor) cursor)))

          (f1 [result cursor] (= " " (subs result (dec cursor) cursor)))

          (f2 [result cursor]
              (let [char (subs result cursor (inc cursor))]
                   (if (re-find #"[A-Z]" char)
                       (cond (= cursor 0)       (str                            (string/to-lowercase char) (subs result (inc cursor)))
                             (f0 result cursor) (str (subs result 0 cursor)     (string/to-lowercase char) (subs result (inc cursor)))
                             (f1 result cursor) (str (subs result 0 cursor)     (string/to-lowercase char) (subs result (inc cursor)))
                             :else              (str (subs result 0 cursor) "-" (string/to-lowercase char) (subs result (inc cursor))))
                       (-> result))))

          (f3 [result cursor]
              (if (= (count result) cursor)
                  (-> result)
                  (let [new-result (f2 result cursor)]
                       (f3 new-result (+ (inc cursor) (- (count new-result) (count result)))))))]

         (f3 n 0)))
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

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

