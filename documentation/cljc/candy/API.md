
# <strong>candy.api</strong> namespace
<p>Documentation of the <strong>candy/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > candy.api</strong>



### none

```
@param (*) n
```

```
@example
(none "x")
=>
nil
```

```
@return (nil)
```

<details>
<summary>Source code</summary>

```
(defn none
  [_] nil)
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [candy.api :as candy :refer [none]]))

(candy/none ...)
(none       ...)
```

</details>

---

### param

```
@param (*) n
```

```
@example
(param "x")
=>
"x"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn param
  [n] n)
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [candy.api :as candy :refer [param]]))

(candy/param ...)
(param       ...)
```

</details>

---

### return

```
@param (*) n
```

```
@example
(return "x")
=>
"x"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn return
  [n] n)
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [candy.api :as candy :refer [return]]))

(candy/return ...)
(return       ...)
```

</details>
