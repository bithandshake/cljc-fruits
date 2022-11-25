
# <strong>css.api</strong> namespace
<p>Documentation of the <strong>css/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > css.api</strong>



### calc

```
@param (string) n
```

```
@usage
(calc "100% - 100px")
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
@usage
(horizontal-margin "420px")
```

```
@example
(horizontal-margin "420px")
=>
"420px 0"
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
@usage
(horizontal-padding "420px")
```

```
@example
(horizontal-padding "420px")
=>
"420px 0"
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
@usage
(ms 420)
```

```
@example
(ms 420)
=>
"420ms"
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
@usage
(percent 420)
```

```
@example
(percent 420)
=>
"420%"
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
@param (px) n
```

```
@usage
(px 420)
```

```
@example
(px "420")
=>
"420px"
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
@param (deg) n
```

```
@usage
(rotate 420)
```

```
@example
(rotate 420)
=>
"rotate(420deg)"
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
@param (deg) n
```

```
@usage
(rotate-x 420)
```

```
@example
(rotate-x 420)
=>
"rotateX(420deg)"
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
@param (deg) n
```

```
@usage
(rotate-y 420)
```

```
@example
(rotate-y 420)
=>
"rotateY(420deg)"
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
@param (deg) n
```

```
@usage
(rotate-z 420)
```

```
@example
(rotate-z 420)
=>
"rotateZ(420deg)"
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
@usage
(s 420)
```

```
@example
(s 420)
=>
"420s"
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
@param (number or string) n
```

```
@usage
(scale 1.1)
```

```
@example
(scale 1.1)
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
@param (number or string) n
@param (string)(opt) suffix
```

```
@usage
(translate 420 "px")
```

```
@example
(translate 420 "px")
=>
"translate(420px)"
```

```
@example
(translate "420px")
=>
"translate(420px)"
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
@param (number or string) n
@param (string)(opt) suffix
```

```
@usage
(translate-x 420 "px")
```

```
@example
(translate-x 420 "px")
=>
"translateX(420px)"
```

```
@example
(translate-x "420px")
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
@param (number or string) n
@param (string)(opt) suffix
```

```
@usage
(translate-y 420 "px")
```

```
@example
(translate-y 420 "px")
=>
"translateY(420px)"
```

```
@example
(translate-y "420px")
=>
"translateY(420px)"
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
@param (number or string) n
@param (string)(opt) suffix
```

```
@usage
(translate-z 420 "px")
```

```
@example
(translate-z 420 "px")
=>
"translateZ(420px)"
```

```
@example
(translate-z "420px")
=>
"translateZ(420px)"
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
@usage
(url "/my-style.css")
```

```
@example
(url "/my-style.ext")
=>
"url(/my-style.ext)"
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
@param (number or string) n
@param (string) unit
"%", "px", "rem", ...
```

```
@usage
(value 420 "px")
```

```
@example
(value 420 "px")
=>
"180px"
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
@param (keyword or string) n
```

```
@usage
(var "my-var")
```

```
@example
(var "my-var")
=>
"var( --my-var )"
```

```
@example
(var :my-var)
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
  (cond (string?  n) (str "var( --"       n  " )")
        (keyword? n) (str "var( --" (name n) " )")))
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

### var-key

```
@param (keyword or string) n
```

```
@usage
(var-key "my-key")
```

```
@example
(var-key "my-key")
=>
"--my-key"
```

```
@example
(var-key :my-key)
=>
"--my-key"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn var-key
  [n]
  (cond (string?  n) (str "--"       n)
        (keyword? n) (str "--" (name n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [css.api :as css :refer [var-key]]))

(css/var-key ...)
(var-key     ...)
```

</details>

---

### vertical-margin

```
@param (string) n
```

```
@usage
(vertical-margin "420px")
```

```
@example
(vertical-margin "420px")
=>
"0 420px"
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
@usage
(vertical-padding "420px")
```

```
@example
(vertical-padding "420px")
=>
"0 420px"
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
