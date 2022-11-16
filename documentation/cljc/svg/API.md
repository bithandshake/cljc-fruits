
# <strong>svg.api</strong> namespace
<p>Documentation of the <strong>svg/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > <strong>[DOCUMENTATION](../../COVER.md) > svg.api</strong>



### element-attributes

```
@param (map) attributes
```

```
@usage
(element-attributes {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn element-attributes
  [attributes]
  (merge {}
         (param attributes)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [svg.api :as svg :refer [element-attributes]]))

(svg/element-attributes ...)
(element-attributes     ...)
```

</details>

---

### view-box

```
@param (px) width
@param (px) height
```

```
@example
(view-box 1024 1024)
=>
"0 0 1024 1024"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn view-box
  [width height]
  (str "0 0 " width " " height))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [svg.api :as svg :refer [view-box]]))

(svg/view-box ...)
(view-box     ...)
```

</details>

---

### wrapper-attributes

```
@param (map) attributes
```

```
@example
(wrapper-attributes {:height 256 :width 256})
=>
{:style {:height "100%" :width "100%"}
 :view-box "0 0 256 256"
 :xmlns "..."}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn wrapper-attributes
  [{:keys [height style width] :as attributes}]
  (merge (dissoc attributes :height :width)
         {:style    (merge {:height "100%" :width "100%"} style)
          :view-box (view-box width height)
          :xmlns    "http://www.w3.org/2000/svg"}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [svg.api :as svg :refer [wrapper-attributes]]))

(svg/wrapper-attributes ...)
(wrapper-attributes     ...)
```

</details>
