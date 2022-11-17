
# <strong>sequence.api</strong> namespace
<p>Documentation of the <strong>sequence/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > sequence.api</strong>



### next-dex

```
@param (integer) dex
@param (integer) min
@param (integer) max
```

```
@example
(next-dex 10 8 20)
=>
11
```

```
@example
(next-dex 20 8 20)
=>
8
```

```
@return (integer)
A dex utan kovetkezo index, ami nem lehet kisebb, mint min es nem lehet
nagyobb, mint max
```

<details>
<summary>Source code</summary>

```
(defn next-dex
  [dex min max]
  (cond (>= dex max) min
        (<  dex min) min
        :return (inc dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [sequence.api :as sequence :refer [next-dex]]))

(sequence/next-dex ...)
(next-dex          ...)
```

</details>

---

### prev-dex

```
@param (integer) dex
@param (integer) min
@param (integer) max
```

```
@example
(prev-dex 10 8 20)
=>
9
```

```
@example
(prev-dex 8 8 20)
=>
20
```

```
@return (integer)
A dex elotti index, ami nem lehet kisebb, mint min es nem lehet nagyobb,
mint max
```

<details>
<summary>Source code</summary>

```
(defn prev-dex
  [dex min max]
  (cond (<= dex min) max
        (>  dex max) max
        :return (dec dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [sequence.api :as sequence :refer [prev-dex]]))

(sequence/prev-dex ...)
(prev-dex          ...)
```

</details>

---

### prev-prev-dex

```
@param (integer) dex
@param (integer) min
@param (integer) max
```

```
@example
(prev-prev-dex 10 8 20)
=>
8
```

```
@example
(prev-prev-dex 9 8 20)
=>
20
```

```
@return (integer)
A dex elotti-elotti index, ami nem lehet kisebb, mint min es nem lehet
nagyobb, mint max
```

<details>
<summary>Source code</summary>

```
(defn prev-prev-dex
  [dex min max]
  (cond (not (> max min)) min        (>  dex max) (dec max)        (<= dex min) (dec max)        (= dex (inc min)) max        :return (- dex 2)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [sequence.api :as sequence :refer [prev-prev-dex]]))

(sequence/prev-prev-dex ...)
(prev-prev-dex          ...)
```

</details>
