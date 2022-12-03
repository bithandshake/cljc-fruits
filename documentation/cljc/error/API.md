
# <strong>error.api</strong> namespace

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > </strong>source-code/cljc/error/api.cljc

### throw!

```
@param (string) e
```

```
@usage
(throw! "Something went wrong ...")
```

```
@return (?)
```

<details>
<summary>Source code</summary>

```
(defn throw!
  [e]
  #?(:clj  (throw (Exception. e))
     :cljs (throw (js/Error.  e))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [error.api :refer [throw!]]))

(error.api/throw! ...)
(throw!           ...)
```

</details>

---

### try!

```
@param (function) f
@param (list of *) abc
```

```
@usage
(try! #(my-function "Apple"))
```

```
@usage
(try! my-function "Apple")
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn try!
  [f & abc]
  #?(:clj  (try (apply f abc) (catch Exception e (println e)))
     :cljs (try (apply f abc) (catch :default  e (println e)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [error.api :refer [try!]]))

(error.api/try! ...)
(try!           ...)
```

</details>
