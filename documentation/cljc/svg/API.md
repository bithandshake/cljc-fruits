
# svg.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > svg.api

### Index

- [view-box](#view-box)

- [wrapper-attributes](#wrapper-attributes)

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
(ns my-namespace (:require [svg.api :refer [view-box]]))

(svg.api/view-box ...)
(view-box         ...)
```

</details>

---

### wrapper-attributes

```
@param (map) attributes
{:height (px)
 :style (map)(opt)
 :width (px)}
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
{:style (map)
  {:height (string)
   :width (string)}
 :view-box (string)
 :xmlns (string)}
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
(ns my-namespace (:require [svg.api :refer [wrapper-attributes]]))

(svg.api/wrapper-attributes ...)
(wrapper-attributes         ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

