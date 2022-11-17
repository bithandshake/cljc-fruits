
# <strong>math.api</strong> namespace
<p>Documentation of the <strong>math/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > math.api</strong>



### absolute

```
@param (number) n
```

```
@example
(absolute -4.20)
=>
4.20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn absolute
  [n]
  (max n (- n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [absolute]]))

(math/absolute ...)
(absolute      ...)
```

</details>

---

### absolute-difference

```
@param (number) a
@param (number) b
```

```
@usage
(absolute-difference 4.20 42)
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn absolute-difference
  [a b]
  (absolute (- a b)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [absolute-difference]]))

(math/absolute-difference ...)
(absolute-difference      ...)
```

</details>

---

### between!

```
@param (number) n
@param (number) min
@param (number) max
```

```
@example
(between! 4.20 0 42)
=>
4.20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn between!
  [n min max]
  (cond (< n min) min
        (> n max) max
        :return   n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [between!]]))

(math/between! ...)
(between!      ...)
```

</details>

---

### between?

```
@param (number) n
@param (number) min
@param (number) max
```

```
@example
(between? 4.20 0 42)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn between?
  [n min max]
  (and (<= n max)
       (>= n min)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [between?]]))

(math/between? ...)
(between?      ...)
```

</details>

---

### calc

```
@param (float, int) n
@param (vector) domain
@param (vector) range
```

```
@example
(calc 42 [10 50] [100 500])
=>
420
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn calc
  [n [domain-from domain-to] [range-from range-to]]
  (let [domain-length (- domain-to domain-from)
        domain-offset (- n domain-from)
        range-length  (- range-to range-from)
        range-offset  (core/absolute range-from)
        ratio         (/ range-length domain-length)]
       (if (< n domain-from)
           (return range-from)
           (if (> n domain-to)
               (return range-to)
               (+ (param range-from)
                  (* domain-offset ratio))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [calc]]))

(math/calc ...)
(calc      ...)
```

</details>

---

### ceil

```
@param (number) n
```

```
@example
(ceil 4.20)
5
4
  ;
@example
(ceil 4.80)
=>
5
```

```
@example
(ceil 4.80)
=>
5
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn ceil
  [n]
  (Math/ceil n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [ceil]]))

(math/ceil ...)
(ceil      ...)
```

</details>

---

### choose

```
@param (integer) n
@param (integer) limit
@param (*) value-if-bigger
@param (*)(opt) value-if-smaller
```

```
@example
(choose 4.20 42 "A" "B")
=>
"B"
```

```
@example
(choose 42 4.20 "A" "B")
=>
"A"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn choose
  [n limit value-if-bigger & [value-if-smaller]]
  (if (>= limit n) value-if-smaller value-if-bigger))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [choose]]))

(math/choose ...)
(choose      ...)
```

</details>

---

### collection-maximum

```
@param (collection) n
```

```
@example
(collection-maximum [100 14 3 55])
=>
100
```

```
@example
(collection-maximum ["0" 1 "a" nil])
=>
1
```

```
@example
(collection-maximum ["0" "a"])
=>
nil
```

```
@return (nil or integer)
```

<details>
<summary>Source code</summary>

```
(defn collection-maximum
  [n]
  (apply max n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [collection-maximum]]))

(math/collection-maximum ...)
(collection-maximum      ...)
```

</details>

---

### collection-minimum

```
@param (collection) n
```

```
@example
(collection-minimum [100 14 3 55])
=>
3
```

```
@example
(collection-minimum ["0" 1 "a" nil])
=>
1
```

```
@example
(collection-minimum ["0" "a"])
=>
nil
```

```
@return (nil or integer)
```

<details>
<summary>Source code</summary>

```
(defn collection-minimum
  [n]
  (apply min n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [collection-minimum]]))

(math/collection-minimum ...)
(collection-minimum      ...)
```

</details>

---

### domain-ceil

```
@param (integer) n
@param (integer) domain
```

```
@example
(domain-ceil 9 5)
=>
10
```

```
@example
(domain-ceil 10 5)
=>
10
```

```
@example
(domain-ceil 11 5)
=>
15
```

```
@example
(domain-ceil 0 5)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn domain-ceil
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
           (*      quot  domain)
           (* (inc quot) domain))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [domain-ceil]]))

(math/domain-ceil ...)
(domain-ceil      ...)
```

</details>

---

### domain-floor

```
@param (integer) n
@param (integer) domain
```

```
@example
(domain-floor 9 5)
=>
6
```

```
@example
(domain-floor 10 5)
=>
6
```

```
@example
(domain-floor 11 5)
=>
11
```

```
@example
(domain-floor 0 5)
=>
-4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn domain-floor
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
           (inc (* (dec quot) domain))
           (inc (*      quot  domain)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [domain-floor]]))

(math/domain-floor ...)
(domain-floor      ...)
```

</details>

---

### domain-inchoate

```
@param (integer) n
@param (integer) domain
```

```
@example
(domain-inchoate 9 5)
=>
2
```

```
@example
(domain-inchoate 10 5)
=>
2
```

```
@example
(domain-inchoate 11 5)
=>
3
```

```
@example
(domain-inchoate 0 5)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn domain-inchoate
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
           (return quot)
           (inc    quot))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [domain-inchoate]]))

(math/domain-inchoate ...)
(domain-inchoate      ...)
```

</details>

---

### floor

```
@param (number) n
```

```
@example
(floor 4.20)
=>
4
```

```
@example
(floor 4.80)
=>
4
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn floor
  [n]
  (Math/floor n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [floor]]))

(math/floor ...)
(floor      ...)
```

</details>

---

### maximum

```
@param (list of numbers) xyz
```

```
@example
(maximum -4.20 2 0)
=>
2
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn maximum
  [& xyz]
  (collection-maximum xyz))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [maximum]]))

(math/maximum ...)
(maximum      ...)
```

</details>

---

### minimum

```
@param (list of number) xyz
```

```
@example
(minimum -4.20 2 0)
=>
2
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn minimum
  [& xyz]
  (collection-minimum xyz))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [minimum]]))

(math/minimum ...)
(minimum      ...)
```

</details>

---

### negative

```
@param (number) n
```

```
@example
(negative 4.20)
=>
-4.20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn negative
  [n]
  (if (>=   0 n)
      (return n)
      (-    0 n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [negative]]))

(math/negative ...)
(negative      ...)
```

</details>

---

### negative?

```
@param (number) n
```

```
@example
(negative? -4.20)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn negative?
  [n]
  (> 0 n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [negative?]]))

(math/negative? ...)
(negative?      ...)
```

</details>

---

### nonnegative?

```
@param (number) n
```

```
@example
(nonnegative? 4.20)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn nonnegative?
  [n]
  (or (zero?     n)
      (positive? n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [nonnegative?]]))

(math/nonnegative? ...)
(nonnegative?      ...)
```

</details>

---

### opposite

```
@param (number) n
```

```
@example
(opposite 4.20)
=>
-4.20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn opposite
  [n]
  (- 0 n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [opposite]]))

(math/opposite ...)
(opposite      ...)
```

</details>

---

### percent

```
@param (number) total
@param (number) value
```

```
@example
(percent 50 20)
=>
40
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent
  [total value]
  (/ value (/ total 100)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [percent]]))

(math/percent ...)
(percent      ...)
```

</details>

---

### percent->angle

```
@param (number) n
```

```
@example
(percent->angle 50)
=>
180
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent->angle
  [n]
  (* 360 (/ n 100)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [percent->angle]]))

(math/percent->angle ...)
(percent->angle      ...)
```

</details>

---

### percent-add

```
@param (number) total
@param (number) percentage
```

```
@example
(percent-add 50 40)
=>
70
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent-add
  [total percentage]
  (+ total (percent-result total percentage)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [percent-add]]))

(math/percent-add ...)
(percent-add      ...)
```

</details>

---

### percent-rest

```
@param (number) total
@param (number) percentage
```

```
@example
(percent-rest 50 40)
=>
30
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent-rest
  [total percentage]
  (- total (percent-result total percentage)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [percent-rest]]))

(math/percent-rest ...)
(percent-rest      ...)
```

</details>

---

### percent-result

```
@param (number) total
@param (number) percentage
```

```
@example
(percent 50 40)
=>
20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent-result
  [total percentage]
  (/ (* total percentage) 100))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [percent-result]]))

(math/percent-result ...)
(percent-result      ...)
```

</details>

---

### positive

```
@param (number) n
```

```
@example
(positive -4.20)
=>
4.20
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn positive
  [n]
  (if (<=   0 n)
      (return n)
      (-    0 n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [positive]]))

(math/positive ...)
(positive      ...)
```

</details>

---

### positive?

```
@param (number) n
```

```
@example
(positive? 4.20)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn positive?
  [n]
  (< 0 n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [positive?]]))

(math/positive? ...)
(positive?      ...)
```

</details>

---

### power

```
@param (number) x
@param (integer) n
```

```
@example
(power 2 3)
=>
8
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn power
  [x n]
  (if (zero? n) 1
      (* x (power x (dec n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [power]]))

(math/power ...)
(power      ...)
```

</details>

---

### round

```
@param (number) n
@param (integer) precision
```

```
@example
(round 4.20)
=>
4
```

```
@example
(round 4.80)
=>
5
```

```
@example
(round 420 100)
=>
400
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn round
  ([n]
   (if (> 0.5 (rem n 1)) (quot n 1)
                    (inc (quot n 1))))

  ([n precision]
   (-> n (/ precision)
         (round)
         (* precision))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :as math :refer [round]]))

(math/round ...)
(round      ...)
```

</details>
