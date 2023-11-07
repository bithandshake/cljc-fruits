
### math.api

Functional documentation of the math.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > math.api

### Index

- [absolute](#absolute)

- [absolute-difference](#absolute-difference)

- [add](#add)

- [avarage](#avarage)

- [between!](#between)

- [between?](#between)

- [calc](#calc)

- [ceil](#ceil)

- [choose](#choose)

- [circum](#circum)

- [collection-average](#collection-average)

- [collection-maximum](#collection-maximum)

- [collection-minimum](#collection-minimum)

- [domain-ceil](#domain-ceil)

- [domain-floor](#domain-floor)

- [domain-inchoate](#domain-inchoate)

- [floor](#floor)

- [maximum](#maximum)

- [minimum](#minimum)

- [multiply](#multiply)

- [negative](#negative)

- [negative?](#negative)

- [nonnegative?](#nonnegative)

- [opposite](#opposite)

- [percent](#percent)

- [percent->angle](#percent-angle)

- [percent-add](#percent-add)

- [percent-diff](#percent-diff)

- [percent-rest](#percent-rest)

- [percent-result](#percent-result)

- [positive](#positive)

- [positive?](#positive)

- [power](#power)

- [round](#round)

- [subtract](#subtract)

---

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
(ns my-namespace (:require [math.api :refer [absolute]]))

(math.api/absolute ...)
(absolute          ...)
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
(ns my-namespace (:require [math.api :refer [absolute-difference]]))

(math.api/absolute-difference ...)
(absolute-difference          ...)
```

</details>

---

### add

```
@description
Adds the number values of the given parameters.
```

```
@param (list of *) abc
```

```
@usage
(add 7 42 69)
```

```
@example
(add 7 42 69)
=>
118
```

```
@example
(add 7 "My string" 69)
=>
76
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn add
  [& abc]
  (letfn [(f [total x] (if (-> x number?)
                           (+  total x)
                           (-> total)))]
         (reduce f 0 (vec abc))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [add]]))

(math.api/add ...)
(add          ...)
```

</details>

---

### avarage

```
@param (list of numbers) abc
```

```
@usage
(avarage 3 4 5)
```

```
@example
(avarage 100 30 20)
=>
50
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn avarage
  [& [abc]]
  (/ (apply * abc)
     (count   abc)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [avarage]]))

(math.api/avarage ...)
(avarage          ...)
```

</details>

---

### between!

```
@param (number) n
@param (number) x
@param (number) y
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
  [n x y]
  (cond (< n (min x y)) (min x y)
        (> n (max x y)) (max x y)
        :return n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [between!]]))

(math.api/between! ...)
(between!          ...)
```

</details>

---

### between?

```
@param (number) n
@param (number) x
@param (number) y
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
  [n x y]
  (and (<= n (max x y))
       (>= n (min x y))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [between?]]))

(math.api/between? ...)
(between?          ...)
```

</details>

---

### calc

```
@description
If 'n' is equal to or smaller than the input-min, returns the 'output-min'.
If 'n' is equal to or greater than the input-max, returns the 'output-max'.
If 'n' is in the input domain, takes the actual position of 'n' in the input
domain and projects the taken position to the output range.
```

```
@param (number) n
@param (vector) input-domain
[(integer) input-min
 (integer) input-max
@param (vector) output-range
[(integer) output-min
 (integer) output-max]
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
  [n [input-min input-max :as input-domain] [output-min output-max :as output-range]]
  (let [input-min     (min (first input-domain) (second input-domain))
        input-max     (max (first input-domain) (second input-domain))
        output-min    (min (first output-range) (second output-range))
        output-max    (max (first output-range) (second output-range))
        domain-length (- input-max input-min)
        domain-offset (- n input-min)
        range-length  (- output-max output-min)
        range-offset  (core/absolute output-min)
        ratio         (/ range-length domain-length)]
       (if (< n input-min)
           (-> output-min)
           (if (> n input-max)
               (-> output-max)
               (+ output-min (* domain-offset ratio))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [calc]]))

(math.api/calc ...)
(calc          ...)
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
(ns my-namespace (:require [math.api :refer [ceil]]))

(math.api/ceil ...)
(ceil          ...)
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
(ns my-namespace (:require [math.api :refer [choose]]))

(math.api/choose ...)
(choose          ...)
```

</details>

---

### circum

```
@param (number) radius
```

```
@usage
(circum 42)
```

```
@example
(circum 50000)
=>
314156
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn circum
  [radius]
  (* radius 2 config/PI))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [circum]]))

(math.api/circum ...)
(circum          ...)
```

</details>

---

### collection-average

```
@param (seqable) n
```

```
@example
(collection-average [100 14 3 55])
=>
43
```

```
@example
(collection-average ["0" 1 "a" nil])
=>
0.5
```

```
@example
(collection-average ["0" "a"])
=>
0
```

```
@return (nil or number)
```

<details>
<summary>Source code</summary>

```
(defn collection-average
  [n]
  (letfn [(f [[sum count] x] (if (number? x)
                                 [(+ sum x) (inc count)]
                                 [sum count]))]
         (let [[sum count] (reduce f [0 0] n)]
              (/ sum count))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [collection-average]]))

(math.api/collection-average ...)
(collection-average          ...)
```

</details>

---

### collection-maximum

```
@param (seqable) n
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
@return (nil or number)
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
(ns my-namespace (:require [math.api :refer [collection-maximum]]))

(math.api/collection-maximum ...)
(collection-maximum          ...)
```

</details>

---

### collection-minimum

```
@param (seqable) n
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
@return (nil or number)
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
(ns my-namespace (:require [math.api :refer [collection-minimum]]))

(math.api/collection-minimum ...)
(collection-minimum          ...)
```

</details>

---

### domain-ceil

```
@description
- Returns the ceil value of the domain that contains the 'n' value.
- E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5),
        and it returns the ceil value of the first domain: 5.
        If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
        and it returns the ceil value of the second domain: 10.
```

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
       (if (-> rem zero?)
           (*      quot  domain)
           (* (inc quot) domain))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [domain-ceil]]))

(math.api/domain-ceil ...)
(domain-ceil          ...)
```

</details>

---

### domain-floor

```
@description
- Returns the floor value of the domain that contains the 'n' value.
- E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5),
        and it returns the floor value of the first domain: 1.
        If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
        and it returns the floor value of the second domain: 6.
```

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
       (if (-> rem zero?)
           (inc (* (dec quot) domain))
           (inc (*      quot  domain)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [domain-floor]]))

(math.api/domain-floor ...)
(domain-floor          ...)
```

</details>

---

### domain-inchoate

```
@description
- Returns which domain contains the 'n' value.
- E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5).
        If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
```

```
@param (integer) n
@param (integer) domain
```

```
@example
(domain-inchoate 0 5)
=>
0
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
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn domain-inchoate
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (->  rem zero?)
           (->  quot)
           (inc quot))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [domain-inchoate]]))

(math.api/domain-inchoate ...)
(domain-inchoate          ...)
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
(ns my-namespace (:require [math.api :refer [floor]]))

(math.api/floor ...)
(floor          ...)
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
(ns my-namespace (:require [math.api :refer [maximum]]))

(math.api/maximum ...)
(maximum          ...)
```

</details>

---

### minimum

```
@param (list of numbers) xyz
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
(ns my-namespace (:require [math.api :refer [minimum]]))

(math.api/minimum ...)
(minimum          ...)
```

</details>

---

### multiply

```
@description
Subtracts the number values of the given parameters.
```

```
@param (list of *) abc
```

```
@usage
(multiply 7 42 69)
```

```
@example
(multiply 7 42 69)
=>
20286
```

```
@example
(multiply 7 "My string" 69)
=>
483
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn multiply
  [& abc]
  (letfn [(f [total x] (if (-> x number?)
                           (*  total x)
                           (-> total)))]
         (reduce f 0 (vec abc))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [multiply]]))

(math.api/multiply ...)
(multiply          ...)
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
  (if (>= 0 n)
      (-> n)
      (-  0 n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [negative]]))

(math.api/negative ...)
(negative          ...)
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
(ns my-namespace (:require [math.api :refer [negative?]]))

(math.api/negative? ...)
(negative?          ...)
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
(ns my-namespace (:require [math.api :refer [nonnegative?]]))

(math.api/nonnegative? ...)
(nonnegative?          ...)
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
(ns my-namespace (:require [math.api :refer [opposite]]))

(math.api/opposite ...)
(opposite          ...)
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
(ns my-namespace (:require [math.api :refer [percent]]))

(math.api/percent ...)
(percent          ...)
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
(ns my-namespace (:require [math.api :refer [percent->angle]]))

(math.api/percent->angle ...)
(percent->angle          ...)
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
(ns my-namespace (:require [math.api :refer [percent-add]]))

(math.api/percent-add ...)
(percent-add          ...)
```

</details>

---

### percent-diff

```
@param (number) a
@param (number) b
```

```
@example
(percent-diff 100 110)
=>
10
```

```
@example
(percent-diff 100 90)
=>
-10
```

```
@example
(percent-diff 50 55)
=>
10
```

```
@example
(percent-diff 50 45)
=>
-10
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn percent-diff
  [a b]
  (- (percent a b) 100))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [percent-diff]]))

(math.api/percent-diff ...)
(percent-diff          ...)
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
(ns my-namespace (:require [math.api :refer [percent-rest]]))

(math.api/percent-rest ...)
(percent-rest          ...)
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
(ns my-namespace (:require [math.api :refer [percent-result]]))

(math.api/percent-result ...)
(percent-result          ...)
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
  (if (<= 0 n)
      (-> n)
      (-  0 n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [positive]]))

(math.api/positive ...)
(positive          ...)
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
(ns my-namespace (:require [math.api :refer [positive?]]))

(math.api/positive? ...)
(positive?          ...)
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
(ns my-namespace (:require [math.api :refer [power]]))

(math.api/power ...)
(power          ...)
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
(ns my-namespace (:require [math.api :refer [round]]))

(math.api/round ...)
(round          ...)
```

</details>

---

### subtract

```
@description
Subtracts the number values of the given parameters.
```

```
@param (list of *) abc
```

```
@usage
(subtract 7 42 69)
```

```
@example
(subtract 7 42 69)
=>
-104
```

```
@example
(add 7 "My string" 69)
=>
-62
```

```
@return (number)
```

<details>
<summary>Source code</summary>

```
(defn subtract
  [& abc]
  (letfn [(f [total x] (if (-> x number?)
                           (-  total x)
                           (-> total)))]
         (reduce f 0 (vec abc))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [math.api :refer [subtract]]))

(math.api/subtract ...)
(subtract          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

