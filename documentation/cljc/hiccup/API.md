
# hiccup.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > hiccup.api

### Index

- [content-length](#content-length)

- [explode](#explode)

- [get-attributes](#get-attributes)

- [get-style](#get-style)

- [hiccup?](#hiccup)

- [join-class](#join-class)

- [set-style](#set-style)

- [tag-name?](#tag-name)

- [to-string](#to-string)

- [unparse-css](#unparse-css)

- [value](#value)

- [walk](#walk)

### content-length

```
@param (hiccup) n
```

```
@example
(content-length [:div "Hello World!"])
=>
12
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn content-length
  [n]
  (-> n convert/to-string count))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [content-length]]))

(hiccup.api/content-length ...)
(content-length            ...)
```

</details>

---

### explode

```
@param (string) n
@param (hiccup) container
```

```
@example
(explode "ab" [:div])
=>
[:div [:span "a"]
      [:span "b"]
```

```
@return (nil or hiccup)
```

<details>
<summary>Source code</summary>

```
(defn explode
  [n container]
  (if (and (string? n)
           (type/hiccup? container))
      (letfn [(f [%1 %2] (conj %1 ^{:key (random/generate-uuid)} [:span %2]))]
             (reduce f container n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [explode]]))

(hiccup.api/explode ...)
(explode            ...)
```

</details>

---

### get-attributes

```
@param (hiccup) n
```

```
@example
(get-attributes [:div {:style {:color "red"}} "Hello World!"])
=>
{:style {:color "red"}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn get-attributes
  [n]
  (if (vector? n)
      (if-let [attributes (vector/nth-item n 1)]
              (if (map?   attributes)
                  (return attributes)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [get-attributes]]))

(hiccup.api/get-attributes ...)
(get-attributes            ...)
```

</details>

---

### get-style

```
@param (hiccup) n
```

```
@example
(get-style [:div {:style {:color "red"}} "Hello World!"])
=>
{:color "red"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn get-style
  [n]
  (if-let [attributes (get-attributes n)]
          (:style attributes)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [get-style]]))

(hiccup.api/get-style ...)
(get-style            ...)
```

</details>

---

### hiccup?

```
@param (*) n
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn hiccup?
  [n]
  (and (-> n vector?)
       (-> n first keyword?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [hiccup?]]))

(hiccup.api/hiccup? ...)
(hiccup?            ...)
```

</details>

---

### join-class

```
@param (list of keyword or keywords in vector) xyz
```

```
@example
(join-class :my-class [:your-class] :our-class)
=>
[:my-class :your-class :our-class]
```

```
@return (keywords in vector)
```

<details>
<summary>Source code</summary>

```
(defn join-class
  [& xyz]
  (letfn [(join-class-f [o x] (cond (vector?  x) (concat o x)
                                    (keyword? x) (conj   o x)
                                    :return   o))]
         (reduce join-class-f [] xyz)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [join-class]]))

(hiccup.api/join-class ...)
(join-class            ...)
```

</details>

---

### set-style

```
@param (hiccup) n
```

```
@example
(set-style [:div {:style {:color "red"}} "Hello World!"])
=>
{:style {:color "red"}}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn set-style
  [n style]
  (if (vector? n)
      (if-let [attributes (get-attributes n)]
              (assoc-in n [1 :style] style)
              (vector/inject-item n 1 {:style style}))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [set-style]]))

(hiccup.api/set-style ...)
(set-style            ...)
```

</details>

---

### tag-name?

```
@param (hiccup) n
@param (keyword) tag-name
```

```
@example
(tag-name? [:div "Hello World!"] :div)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn tag-name?
  [n tag-name]
  (= (first n) tag-name))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [tag-name?]]))

(hiccup.api/tag-name? ...)
(tag-name?            ...)
```

</details>

---

### to-string

```
@param (hiccup) n
@param (string)(opt) delimiter
Default: " "
```

```
@example
(to-string [:div "Hello" [:strong "World!"]])
=>
"Hello World!"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-string
  ([n]
   (to-string n " "))

  ([n delimiter]
   (letfn [(to-string-f [o x]
                        (cond (string? x) (str o delimiter  x)
                              (vector? x) (str o (to-string x))
                              :return  o))]
          (reduce to-string-f "" n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [to-string]]))

(hiccup.api/to-string ...)
(to-string            ...)
```

</details>

---

### unparse-css

```
@param (hiccup) n
```

```
@example
(unparse-css [:td [:p {:style {:color "red"}}]])
=>
[:td [:p {:style "color: red;"}]]
```

```
@example
(unparse-css [:td [:p {:style "color: red;"}]])
=>
[:td [:p {:style "color: red;"}]]
```

```
@return (hiccup)
```

<details>
<summary>Source code</summary>

```
(defn unparse-css
  [n]
  (letfn [(f [n] (let [style (attributes/get-style n)]
                      (if (map? style)
                          (attributes/set-style n (css/unparse style))
                          (return               n))))]
         (walk/walk n f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [unparse-css]]))

(hiccup.api/unparse-css ...)
(unparse-css            ...)
```

</details>

---

### value

```
@param (keyword or string) n
@param (string)(opt) flag
```

```
@example
(value "my-namespace/my-value?")
=>
"my-namespace--my-value"
```

```
@example
(value :your-namespace/your-value!)
=>
"your-namespace--your-value"
```

```
@example
(value :our-namespace/our-value "420")
=>
"our-namespace--our-value--420"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn value
  [n & [flag]]
  (let [x (cond (keyword? n) (keyword/to-string n)
                (string?  n) (return            n))]
       (letfn [(f [result tag] (case tag "." (str result "--")
                                         "/" (str result "--")
                                         "?" result
                                         "!" result
                                         ">" result
                                             (str result tag)))]
              (str (reduce f nil x)
                   (if flag (str "--" flag))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [value]]))

(hiccup.api/value ...)
(value            ...)
```

</details>

---

### walk

```
@param (hiccup) n
@param (function) f
```

```
@example
(walk [:td [:p {:style {:color "red"}}]]
      #(conj % "420"))
=>
[:td [:p {:style {:color "red"}} "420"] "420"]
```

```
@return (hiccup)
```

<details>
<summary>Source code</summary>

```
(defn walk
  [n f]
  (if (type/hiccup? n)
      (letfn [(walk-f [%1 %2] (conj %1 (walk %2 f)))]
             (reduce walk-f [] (f n)))
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [hiccup.api :refer [walk]]))

(hiccup.api/walk ...)
(walk            ...)
```

</details>

---

This documentation is generated by the [docs-api](https://github.com/bithandshake/docs-api) engine

