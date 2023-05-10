
# print.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > print.api

### Index

- [table-line](#table-line)

- [table-row](#table-row)

### table-line

```
@param (integer) size
```

```
@usage
(table-line 50)
```

<details>
<summary>Source code</summary>

```
(defn table-line
  [size]
  (as-> size % (string/multiply "-" %)
               (str "|" % "|")
               (println %)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [print.api :refer [table-line]]))

(print.api/table-line ...)
(table-line           ...)
```

</details>

---

### table-row

```
@param (vectors in vector) columns
[[(integer) size
  (string)(opt) content
  (keyword)(opt) align
   :center, :left, :right
   Default: :center]]
```

```
@usage
(table-row [[8 "..."] [...]])
```

<details>
<summary>Source code</summary>

```
(defn table-row
  [columns]
  (letfn [(f [[size content align]]
             (let [space (- size (-> content str count))]
                  (case align :left  (cond (>  2 space) (str     content (string/multiply " " space))
                                           (<= 2 space) (str " " content (string/multiply " " (dec space))))
                              :right (cond (>  2 space) (str (string/multiply " " space)       content)
                                           (<= 2 space) (str (string/multiply " " (dec space)) content " "))
                                     (cond (even? space) (str (string/multiply " " (/ space 2))       content (string/multiply " " (/ space 2)))
                                           (odd?  space) (str (string/multiply " " (/ (dec space) 2)) content (string/multiply " " (/ (inc space) 2)))))))]
         (as-> columns % (vector/->items % f)
                         (string/join % "|")
                         (str "|" % "|")
                         (println %))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [print.api :refer [table-row]]))

(print.api/table-row ...)
(table-row           ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

