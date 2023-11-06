
### noop.api

Functional documentation of the noop.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > noop.api

### Index

- [none](#none)

- [param](#param)

- [return](#return)

---

### none

```
@description
A simple noop function for ignoring values.
Returns nil.
```

```
@param (*) n
```

```
@usage
(none "x")
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
(ns my-namespace (:require [noop.api :refer [none]]))

(noop.api/none ...)
(none          ...)
```

</details>

---

### param

```
@description
A simple noop function for wrapping parameters.
Returns the given n parameter.
```

```
@param (*) n
```

```
@usage
(param "x")
```

```
@usage
(defn my-function [a b] (str a b))
(my-function (param "x")
             (inc   42))
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
(ns my-namespace (:require [noop.api :refer [param]]))

(noop.api/param ...)
(param          ...)
```

</details>

---

### return

```
@description
A simple noop function for wrapping return values.
Returns the given n parameter.
```

```
@param (*) n
```

```
@usage
(return "x")
```

```
@usage
(let [my-value 42]
     (return my-value))
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
(ns my-namespace (:require [noop.api :refer [return]]))

(noop.api/return ...)
(return          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

