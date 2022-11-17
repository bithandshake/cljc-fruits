
# <strong>format.api</strong> namespace
<p>Documentation of the <strong>format/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > format.api</strong>



### decimals

```
@param (integer or string) n
@param (integer)(opt) length
```

```
@usage
(decimals "420" 2)
```

```
@example
(decimals nil 2)
=>
""
```

```
@example
(decimals "1" 2)
=>
"1.00"
```

```
@example
(decimals "11.0000" 3)
=>
"11.000"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn decimals
  ([n]
   (decimals n 2))

  ([n length]
   (let [x     (str   n)
         count (count x)]
        (if            (< count 1) x
            (if-let [separator-index (string/first-dex-of x ".")]
                    (let [diff (- count separator-index length 1)]
                         (cond                               (> diff 0)
                               (subs x 0 (+ separator-index (inc length)))
                               (< diff 0)
                               (str x (trailing-zeros nil (- 0 diff)))
                               (= diff 0) x))
                    (str x "." (trailing-zeros nil length)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [decimals]]))

(format/decimals ...)
(decimals        ...)
```

</details>

---

### group-number

```
@param (number or string) n
```

```
@usage
(group-number 4200.5)
```

```
@example
(group-number 4200.5)
=>
"4 200.5"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn group-number
  [n]
  (let [        base        (re-find #"\d+" n)
        group-count (quot (count base) 3)
        offset      (-    (count base) (* 3 group-count))]
       (str            (string/trim (reduce (fn [result dex]
                                     (let [x (+ offset (* 3 dex))]
                                          (str result " " (subs base x (+ x 3)))))
                                 (subs base 0 offset)
                                 (range group-count)))
            (subs n (count base)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [group-number]]))

(format/group-number ...)
(group-number        ...)
```

</details>

---

### inc-version

```
@param (string) n
```

```
@usage
(inc-version "0.0.1")
```

```
@example
(inc-version "1.2.19")
=>
"1.2.20"
```

```
@example
(inc-version "0.0.99")
=>
"0.1.00"
```

```
@example
(inc-version "9.9")
=>
"10.0"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn inc-version
  [n]
  (letfn [(implode-f                     [n separators]
                     (if (vector/nonempty? separators)
                         (implode-f (string/insert-part n "." (last separators))
                                    (vector/remove-last-item separators))
                         (return n)))
          (explode-f                     [n separators]
                     (if-let [separator (string/first-dex-of n ".")]
                             (explode-f (string/remove-first-occurence n ".")
                                        (conj separators separator))
                             (implode-f                                        (let [bugfix (remove-leading-zeros n)]
                                             (leading-zeros (mixed/update-whole-number bugfix inc) (count n)))
                                        (if (re-match? n #"^[9]{1,}$")
                                            (vector/->items separators inc)
                                            (param          separators)))))]
         (explode-f n [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [inc-version]]))

(format/inc-version ...)
(inc-version        ...)
```

</details>

---

### leading-zeros

```
@param (integer or string) n
@param (integer) length
```

```
@usage
(leading-zeros "420" 5)
```

```
@example
(leading-zeros 7 3)
=>
"007"
```

```
@example
(leading-zeros 420 3)
=>
"420"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn leading-zeros
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str "0" x))
            (return x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [leading-zeros]]))

(format/leading-zeros ...)
(leading-zeros        ...)
```

</details>

---

### remove-leading-zeros

```
@param (integer or string) n
```

```
@usage
(remove-leading-zeros "042")
```

```
@example
(remove-leading-zeros 42)
=>
"42"
```

```
@example
(remove-leading-zeros "0042")
=>
"42"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn remove-leading-zeros
  [n]
  (letfn [(f [n]
             (if-not (= "0" (-> n first str))
                     (return n)
                     (f (subs n 1))))]
         (-> n str f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [remove-leading-zeros]]))

(format/remove-leading-zeros ...)
(remove-leading-zeros        ...)
```

</details>

---

### round

```
@param (number) n
```

```
@usage
(round 1234)
```

```
@example
(round 1740)
=>
"2K"
```

```
@example
(round 1000420)
=>
"1M"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn round
  [n]
  (cond (>= n 1000000) (str (Math/round (/ n 1000000)) "M")
        (>= n 1000)    (str (Math/round (/ n 1000))    "K")
        :return        (str (Math/round n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [round]]))

(format/round ...)
(round        ...)
```

</details>

---

### trailing-zeros

```
@param (integer or string) n
@param (integer)(opt) length
```

```
@usage
(trailing-zeros "420" 5)
```

```
@example
(trailing-zeros 7 3)
=>
"700"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn trailing-zeros
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str x "0"))
            (return x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [format.api :as format :refer [trailing-zeros]]))

(format/trailing-zeros ...)
(trailing-zeros        ...)
```

</details>
