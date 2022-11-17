
# <strong>random.api</strong> namespace
<p>Documentation of the <strong>random/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > random.api</strong>



### generate-boolean

```
@param (boolean) 
```

```
@usage
(generate-boolean)
```

```
@example
(generate-boolean)
=>
true
```

<details>
<summary>Source code</summary>

```
(defn generate-boolean
  []
  (-> 2 rand-int zero?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-boolean]]))

(random/generate-boolean ...)
(generate-boolean        ...)
```

</details>

---

### generate-keyword

```
@param (string)(opt) namespace
```

```
@usage
(generate-keyword)
```

```
@example
(generate-keyword)
=>
:ko4983l3-i8790-j93l3-lk8385u591o2
```

```
@example
(generate-keyword :my-namespace)
=>
:my-namespace/ko4983l3-i8790-j93l3-lk8385u591o2
```

```
@return (keyword)
```

<details>
<summary>Source code</summary>

```
(defn generate-keyword
  ([]
   (keyword (generate-uuid)))

  ([namespace]
   (keyword (str namespace "/" (generate-uuid)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-keyword]]))

(random/generate-keyword ...)
(generate-keyword        ...)
```

</details>

---

### generate-namespaced-keyword

```
@usage
(generate-namespaced-keyword)
```

```
@example
(generate-namespaced-keyword)
=>
:ko4983l3-i8790-j93l3-lk8385u591o2/ab5069i3-z8700-l89z6-op4450p510p4
```

```
@return (namespaced keyword)
```

<details>
<summary>Source code</summary>

```
(defn generate-namespaced-keyword
  []
  (keyword (str (generate-uuid) "/" (str config/NAME-PREFIX (generate-uuid)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-namespaced-keyword]]))

(random/generate-namespaced-keyword)
(generate-namespaced-keyword)
```

</details>

---

### generate-number

```
@param (integer) digits
```

```
@usage
(generate-number 5)
```

```
@example
(generate-number 3)
=>
420
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn generate-number
  [digits]
  (int (* (math/power 10 (dec digits)) (min 9.999 (-> 9 rand inc)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-number]]))

(random/generate-number ...)
(generate-number        ...)
```

</details>

---

### generate-react-key

```
@usage
(generate-react-key)
```

```
@example
(generate-react-key)
=>
"ko4983l3-i8790-j93l3-lk8385u591o2"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-react-key
  []
  (generate-uuid))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-react-key]]))

(random/generate-react-key)
(generate-react-key)
```

</details>

---

### generate-string

```
@usage
(generate-string)
```

```
@example
(generate-string)
=>
"ko4983l3-i8790-j93l3-lk8385u591o2"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-string
  []
  (generate-uuid))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-string]]))

(random/generate-string)
(generate-string)
```

</details>

---

### generate-uuid

```
@usage
(generate-uuid)
```

```
@example
(generate-uuid)
=>
"ko4983l3-i8790-j93l3-lk8385u591o2"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn generate-uuid
  []
  #?(:cljs (str config/NAME-PREFIX (random-uuid))
     :clj  (str config/NAME-PREFIX (java.util.UUID/randomUUID))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [random.api :as random :refer [generate-uuid]]))

(random/generate-uuid)
(generate-uuid)
```

</details>
