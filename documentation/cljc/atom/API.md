
### atom.api

Functional documentation of the atom.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > atom.api

### Index

- [apply!](#apply)

- [dec!](#dec)

- [inc!](#inc)

- [not!](#not)

---

### apply!

```
@param (atom) atom
@param (function) f
@param (list of *)(opt) params
```

```
@usage
(def my-atom (atom 42))
(defn my-f [x] (inc x))
(apply! my-atom my-f)
```

```
@return (atom)
```

<details>
<summary>Source code</summary>

```
(defn apply!
  [atom f & params])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [atom.api :refer [apply!]]))

(atom.api/apply! ...)
(apply!          ...)
```

</details>

---

### dec!

```
@param (atom) atom
```

```
@usage
(def my-atom (atom 42))
(dec! my-atom)
```

```
@return (atom)
```

<details>
<summary>Source code</summary>

```
(defn dec!
  [atom]
  (swap! atom dec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [atom.api :refer [dec!]]))

(atom.api/dec! ...)
(dec!          ...)
```

</details>

---

### inc!

```
@param (atom) atom
```

```
@usage
(def my-atom (atom 42))
(inc! my-atom)
```

```
@return (atom)
```

<details>
<summary>Source code</summary>

```
(defn inc!
  [atom]
  (swap! atom inc))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [atom.api :refer [inc!]]))

(atom.api/inc! ...)
(inc!          ...)
```

</details>

---

### not!

```
@param (atom) atom
```

```
@usage
(def my-atom (atom true))
(not! my-atom)
```

```
@return (atom)
```

<details>
<summary>Source code</summary>

```
(defn not!
  [atom]
  (swap! atom not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [atom.api :refer [not!]]))

(atom.api/not! ...)
(not!          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

