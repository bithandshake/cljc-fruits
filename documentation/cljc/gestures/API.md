
# <strong>gestures.api</strong> namespace
<p>Documentation of the <strong>gestures/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > gestures.api</strong>



### item-label->copy-label

```
@param (string) item-label
@param (strings in vector)(opt) concurent-labels
```

```
@example
(item-label->copy-label "My item" ["Your item" "Their item"])
=>
"My item #2"
```

```
@example
(item-label->copy-label "My item" ["My item" "My item #2"])
=>
"My item #3"
```

```
@example
(item-label->copy-label "My item #2" ["Your item"])
=>
"My item #3"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn item-label->copy-label
  ([item-label]
   (item-label->copy-label item-label []))

  ([item-label concurent-labels]
   (letfn [(test-f [n] (not (vector/contains-item? concurent-labels n)))
           (f      [n] (if (type/ordered-label? n)
                           (let [copy-dex      (string/after-last-occurence  n "#" {:return? false})
                                 label-base    (string/before-last-occurence n "#" {:return? true})
                                 next-copy-dex (mixed/update-whole-number copy-dex inc)]
                                (str label-base "#" next-copy-dex))
                           (str n " #2")))]
          (do-while f item-label test-f))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [gestures.api :as gestures :refer [item-label->copy-label]]))

(gestures/item-label->copy-label ...)
(item-label->copy-label          ...)
```

</details>

---

### ordered-label?

```
@param (string) n
```

```
@usage
(ordered-label? "My item #3")
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn ordered-label?
  [n]
  (re-match? n #".*\#\d$"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [gestures.api :as gestures :refer [ordered-label?]]))

(gestures/ordered-label? ...)
(ordered-label?          ...)
```

</details>

---

### resolve-variable

```
@param (string) text
@param (vectors in vector) variables
```

```
@example=>
```

```
@example=>
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn resolve-variable
  [text variables]
  (letfn [(f [result [variable-value & variable-names]]
             (letfn [(f [result variable-name]
                        (cond (nil?             variable-value) (return              result)
                              (number?          variable-value) (string/replace-part result variable-name variable-value)
                              (string/nonempty? variable-value) (string/replace-part result variable-name variable-value)
                              :return result))]
                    (reduce f result variable-names)))]
         (reduce f text variables)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [gestures.api :as gestures :refer [resolve-variable]]))

(gestures/resolve-variable ...)
(resolve-variable          ...)
```

</details>
