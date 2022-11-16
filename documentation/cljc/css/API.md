
# <strong>css.api</strong> namespace
<p>Documentation of the <strong>css/api.cljc</strong> file</p>

[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > css.api



### calc

```
@param (string) n
```

```
@example
(calc "100% - 100px")
=>
"calc(100% - 100px)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn calc
  [n]
  (str "calc(" n ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [calc]]))

(css/calc ...)
(calc     ...)
```

</details>

---

### horizontal-margin

```
@param (string) n
```

```
@example
(horizontal-margin "12px")
=>
"12px 0"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn horizontal-margin
  [n]
  (str n " 0"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [horizontal-margin]]))

(css/horizontal-margin ...)
(horizontal-margin     ...)
```

</details>

---

### horizontal-padding

```
@param (string) n
```

```
@example
(horizontal-padding "12px")
=>
"12px 0"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn horizontal-padding
  [n]
  (str n " 0"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [horizontal-padding]]))

(css/horizontal-padding ...)
(horizontal-padding     ...)
```

</details>

---

### linear-gradient

```
@param (string) direction
@param (list of strings) color-stops
```

```
@example
(linear-gradient "0deg" "red" "green" "blue")
=>
"linear-gradient(0deg, red, green, blue)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn linear-gradient
  [direction & color-stops]
  (let [color-stops (string/join color-stops ", ")]
       (str "linear-gradient("direction", "color-stops")")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [linear-gradient]]))

(css/linear-gradient ...)
(linear-gradient     ...)
```

</details>

---

### ms

```
@param (ms) n
```

```
@example
(ms 500)
=>
"500ms"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn ms
  [n]
  (str n "ms"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [ms]]))

(css/ms ...)
(ms     ...)
```

</details>

---

### percent

```
@param (percent) n
```

```
@example
(percent 100)
=>
"100%"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn percent
  [n]
  (str n "%"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [percent]]))

(css/percent ...)
(percent     ...)
```

</details>

---

### px

```
@param (string) n
```

```
@example
(px "100")
=>
"100px"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn px
  [n]
  (str n "px"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [px]]))

(css/px ...)
(px     ...)
```

</details>

---

### rotate

```
@param (string) n
```

```
@example
(rotate "120")
=>
"rotate(120deg)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn rotate
  [n]
  (str "rotate(" n "deg)"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [rotate]]))

(css/rotate ...)
(rotate     ...)
```

</details>

---

### rotate-x

```
@param (string) n
```

```
@example
(rotate-x "120")
=>
"rotateX(120deg)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn rotate-x
  [n]
  (str "rotateX(" n "deg)"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [rotate-x]]))

(css/rotate-x ...)
(rotate-x     ...)
```

</details>

---

### rotate-y

```
@param (string) n
```

```
@example
(rotate-y "120")
=>
"rotateY(120deg)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn rotate-y
  [n]
  (str "rotateY(" n "deg)"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [rotate-y]]))

(css/rotate-y ...)
(rotate-y     ...)
```

</details>

---

### rotate-z

```
@param (string) n
```

```
@example
(rotate-z "120")
=>
"rotateZ(120deg)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn rotate-z
  [n]
  (str "rotateZ(" n "deg)"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [rotate-z]]))

(css/rotate-z ...)
(rotate-z     ...)
```

</details>

---

### s

```
@param (s) n
```

```
@example
(s 3)
=>
"3s"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn s
  [n]
  (str n "s"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [s]]))

(css/s ...)
(s     ...)
```

</details>

---

### scale

```
@param (string) n
```

```
@example
(scale "1.1")
=>
"scale(1.1)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn scale
  [n]
  (str "scale(" n ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [scale]]))

(css/scale ...)
(scale     ...)
```

</details>

---

### translate

```
@param (string) n
@param (string)(opt) suffix
```

```
@example
(translate "120" "px")
=>
"translate(120px)"
```

```
@example
(translate "120px")
=>
"translate(120px)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn translate
  [n & [suffix]]
  (str "translate(" n suffix ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [translate]]))

(css/translate ...)
(translate     ...)
```

</details>

---

### translate-x

```
@param (string) n
@param (string)(opt) suffix
```

```
@example
(translate-x "120" "px")
=>
"translateX(120px)"
```

```
@example
(translate-x "120px")
=>
"translateX(120px)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn translate-x
  [n & [suffix]]
  (str "translateX(" n suffix ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [translate-x]]))

(css/translate-x ...)
(translate-x     ...)
```

</details>

---

### translate-y

```
@param (string) n
@param (string)(opt) suffix
```

```
@example
(translate-y "120" "px")
=>
"translateY(120px)"
```

```
@example
(translate-y "120px")
=>
"translateY(120px)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn translate-y
  [n & [suffix]]
  (str "translateY(" n suffix ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [translate-y]]))

(css/translate-y ...)
(translate-y     ...)
```

</details>

---

### translate-z

```
@param (string) n
@param (string)(opt) suffix
```

```
@example
(translate-z "120" "px")
=>
"translateZ(120px)"
```

```
@example
(translate-z "120px")
=>
"translateZ(120px)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn translate-z
  [n & [suffix]]
  (str "translateZ(" n suffix ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [translate-z]]))

(css/translate-z ...)
(translate-z     ...)
```

</details>

---

### unparse

```
@param (map) n
```

```
@example
(unparse {:opacity 1 :width "100%"})
=>
"opacity: 1; width: 100%;"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn unparse
  [n]
  (letfn [(f [style k v] (str style (name k) ": " (if (keyword? v) (name v) v) "         (string/trim (reduce-kv f "" n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [unparse]]))

(css/unparse ...)
(unparse     ...)
```

</details>

---

### url

```
@param (string) n
```

```
@example
(url "/my-file.ext")
=>
"url(/my-file.ext)"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn url
  [n]
  (str "url(" n ")"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [url]]))

(css/url ...)
(url     ...)
```

</details>

---

### value

```
@param (string or integer) n
@param (string) unit
```

```
@example
(value 180 "%")
=>
"180%"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn value
  [n unit]
  (str n unit))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [value]]))

(css/value ...)
(value     ...)
```

</details>

---

### var

```
@param (string) n
```

```
@example
(var "my-var")
=>
"var( --my-var )"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn var
  [n]
  (str "var( --" n " )"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [var]]))

(css/var ...)
(var     ...)
```

</details>

---

### vertical-margin

```
@param (string) n
```

```
@example
(vertical-margin "12px")
=>
"0 12px"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn vertical-margin
  [n]
  (str "0 " n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [vertical-margin]]))

(css/vertical-margin ...)
(vertical-margin     ...)
```

</details>

---

### vertical-padding

```
@param (string) n
```

```
@example
(vertical-padding "12px")
=>
"0 12px"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn vertical-padding
  [n]
  (str "0 " n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [vertical-padding]]))

(css/vertical-padding ...)
(vertical-padding     ...)
```

</details>
