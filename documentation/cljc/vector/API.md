
# vector.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > vector.api

### Index

- [->>items](#-items)

- [->items](#-items)

- [->items-indexed](#-items-indexed)

- [abc-items](#abc-items)

- [align-items](#align-items)

- [all-items-match?](#all-items-match)

- [any-item-match?](#any-item-match)

- [change-item](#change-item)

- [compared-items-sorted?](#compared-items-sorted)

- [concat-items](#concat-items)

- [concat-items-once](#concat-items-once)

- [conj-item](#conj-item)

- [conj-item-once](#conj-item-once)

- [conj-some](#conj-some)

- [cons-item](#cons-item)

- [cons-item-once](#cons-item-once)

- [contains-item?](#contains-item)

- [contains-similars?](#contains-similars)

- [count!](#count)

- [count?](#count)

- [dec-dex](#dec-dex)

- [dex-first?](#dex-first)

- [dex-in-bounds?](#dex-in-bounds)

- [dex-last?](#dex-last)

- [dex-out-of-bounds?](#dex-out-of-bounds)

- [dex-range](#dex-range)

- [difference](#difference)

- [duplicate-nth-item](#duplicate-nth-item)

- [duplicate-nth-items](#duplicate-nth-items)

- [filter-items](#filter-items)

- [filter-items-by](#filter-items-by)

- [filtered-count](#filtered-count)

- [filtered-count?](#filtered-count)

- [first-filtered](#first-filtered)

- [first-filtered-by](#first-filtered-by)

- [first-item](#first-item)

- [first-items](#first-items)

- [gap-items](#gap-items)

- [get-first-match](#get-first-match)

- [get-first-match-dex](#get-first-match-dex)

- [get-last-match](#get-last-match)

- [get-last-match-dex](#get-last-match-dex)

- [inc-dex](#inc-dex)

- [inject-item](#inject-item)

- [item-dex?](#item-dex)

- [item-first-dex](#item-first-dex)

- [item-first?](#item-first)

- [item-last-dex](#item-last-dex)

- [item-last?](#item-last)

- [items-after-first-occurence](#items-after-first-occurence)

- [items-before-first-occurence](#items-before-first-occurence)

- [items-sorted?](#items-sorted)

- [keep-items](#keep-items)

- [keep-items-by](#keep-items-by)

- [last-dex](#last-dex)

- [last-filtered](#last-filtered)

- [last-filtered-by](#last-filtered-by)

- [last-item](#last-item)

- [last-items](#last-items)

- [longer?](#longer)

- [match-dex](#match-dex)

- [max?](#max)

- [min?](#min)

- [move-first-occurence](#move-first-occurence)

- [move-item-to-first](#move-item-to-first)

- [move-item-to-last](#move-item-to-last)

- [move-nth-item](#move-nth-item)

- [move-nth-item-bwd](#move-nth-item-bwd)

- [move-nth-item-fwd](#move-nth-item-fwd)

- [next-dex](#next-dex)

- [next-item](#next-item)

- [nonempty?](#nonempty)

- [not-contains-item?](#not-contains-item)

- [nth-filtered](#nth-filtered)

- [nth-filtered-by](#nth-filtered-by)

- [nth-item](#nth-item)

- [only-item?](#only-item)

- [prev-dex](#prev-dex)

- [prev-item](#prev-item)

- [ranged-items](#ranged-items)

- [remove-duplicates](#remove-duplicates)

- [remove-first-item](#remove-first-item)

- [remove-first-items](#remove-first-items)

- [remove-first-occurence](#remove-first-occurence)

- [remove-item](#remove-item)

- [remove-item-once](#remove-item-once)

- [remove-items](#remove-items)

- [remove-items-by](#remove-items-by)

- [remove-items-kv](#remove-items-kv)

- [remove-last-item](#remove-last-item)

- [remove-last-items](#remove-last-items)

- [remove-nth-item](#remove-nth-item)

- [remove-nth-items](#remove-nth-items)

- [repeat-item](#repeat-item)

- [replace-nth-item](#replace-nth-item)

- [reverse-items](#reverse-items)

- [similars](#similars)

- [sort-items](#sort-items)

- [sort-items-by](#sort-items-by)

- [sort-items-by-dexes](#sort-items-by-dexes)

- [sorted-dexes](#sorted-dexes)

- [to-map](#to-map)

- [toggle-item](#toggle-item)

- [trim](#trim)

### ->>items

```
@param (map) n
@param (function) update-f
```

```
@usage
(->>items [0 1 2 [3 4 {:x 6}]] inc)
```

```
@example
(->>items [:a :b :c [:d :e {:e :f}]] name)
=>
["a" "b" "c" ["d" "e" {:e "f"}]]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn ->>items
  [n update-f]
  (letfn [(f [n] (cond (vector? n) (reduce    #(conj  %1    (f %2)) [] n)
                       (map?    n) (reduce-kv #(assoc %1 %2 (f %3)) {} n)
                       :return     (update-f n)))]
         (f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [->>items]]))

(vector.api/->>items ...)
(->>items            ...)
```

</details>

---

### ->items

```
@param (map) n
@param (function) update-f
```

```
@usage
(->items [0 1 2] inc)
```

```
@example
(->items [:a :b :c] name)
=>
["a" "b" "c"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn ->items
  [n update-f]
  (letfn [(f [%1 %2] (conj %1 (update-f %2)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [->items]]))

(vector.api/->items ...)
(->items            ...)
```

</details>

---

### ->items-indexed

```
@param (map) n
@param (function) update-f
```

```
@usage
(->items-indexed [0 1 2] (fn [dex %] (inc %)))
```

```
@example
(->items-indexed [:a :b :c] (fn [dex %] (name %)))
=>
["a" "b" "c"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn ->items-indexed
  [n update-f]
  (letfn [(f [%1 %2 %3] (conj %1 (update-f %2 %3)))]
         (reduce-kv f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [->items-indexed]]))

(vector.api/->items-indexed ...)
(->items-indexed            ...)
```

</details>

---

### abc-items

```
@description
Returns the given vector but its items are in alphabetical order.
```

```
@param (vector) n
```

```
@usage
(abc-items [:a :d :c :b])
```

```
@example
(abc-items [:b "b" :a "a" nil])
=>
[nil "a" "b" :a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn abc-items
  [n]
  (letfn [(sort-item-f [result x] (cond (string?  x) (update result :string-items     conj x)
                                        (keyword? x) (update result :keyword-items    conj x)
                                        :return      (update result :unsortable-items conj x)))
          (sort-items-f [n] (reduce sort-item-f {} n))]
         (let [{:keys [string-items keyword-items unsortable-items]} (sort-items-f n)]
              (vec (concat unsortable-items (sort string-items)
                                            (sort keyword-items))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [abc-items]]))

(vector.api/abc-items ...)
(abc-items            ...)
```

</details>

---

### align-items

```
@description
Concatenate items of vectors with end alignment. If a vector's last items are
indentical with the next vector's first items the indentical items will be
merged to avoid duplications.
```

```
@param (list of vectors) abc
```

```
@usage
(align-items [:a :b :c :d] [:c :d :e :f])
```

```
@example
(align-items [:a :b :c :d] [:c :d :e :f])
=>
[:a :b :c :d :e :f]
```

```
@example
(align-items [:a :b :c] [:c :d :e] [:e :f :g])
=>
[:a :b :c :d :e :f :g]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn align-items
  [& abc]
  (letfn [
          (aligned? [a b shift] (let [a-size (- (count a) shift)
                                      b-size (min a-size (count b))]
                                     (= (subvec a shift)
                                        (subvec b 0 b-size))))

          (shiftable? [a b prev-shift] (< prev-shift (-> a count dec)))

          (align-f [a b shift] (cond (aligned?   a b shift) (concat-items (subvec a 0 shift) b)
                                     (shiftable? a b shift) (align-f a b (inc shift))
                                     :no-alignment-found    (concat-items a b)))

          (f [result dex x] (if (= dex 0) x (align-f result x 0)))]

         (reduce-kv f [] (vec abc))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [align-items]]))

(vector.api/align-items ...)
(align-items            ...)
```

</details>

---

### all-items-match?

```
@param (vector) n
@param (function) test-f
```

```
@usage
(all-items-match? ["a" "b" "c"] string?)
```

```
@example
(all-items-match? [:a "b" "c"] string?)
=>
false
```

```
@example
(all-items-match? ["a" "b" "c"] string?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn all-items-match?
  [n test-f]
  (every? test-f n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [all-items-match?]]))

(vector.api/all-items-match? ...)
(all-items-match?            ...)
```

</details>

---

### any-item-match?

```
@param (vector) n
@param (function) test-f
```

```
@usage
(any-item-match? ["a" "b" :c] keyword?)
```

```
@example
(any-item-match? [:a :b :c] string?)
=>
false
```

```
@example
(any-item-match? [:a "b" :c] string?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn any-item-match?
  [n test-f]
  (boolean (some test-f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [any-item-match?]]))

(vector.api/any-item-match? ...)
(any-item-match?            ...)
```

</details>

---

### change-item

```
@param (vector) n
@param (*) a
@param (*) b
```

```
@usage
(change-item [:a :b :c] :c :x)
```

```
@example
(change-item [:a :b :c :d :c] :c :x)
=>
[:a :b :x :d :x]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn change-item
  [n a b]
  (letfn [(f [result x]
             (if (= x a)
                 (conj-item result b)
                 (conj-item result x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [change-item]]))

(vector.api/change-item ...)
(change-item            ...)
```

</details>

---

### compared-items-sorted?

```
@description
- Compares two vectors by comparing their items (at the same index) with the comparator function.
- When iterating over the two vectors if items at the same index are not match, it returns the output
  of the comparator function that takes that two items.
- If the elements in vectors 'a' and 'b' match in value and the number of elements is the same, it returns TRUE.
- If the number of elements in vector 'a' is not equal to the number of elements in vector 'b', the comparison is
  performed up to the lower number of elements (if the compared elements match if vector 'a' has the smaller number
  of elements it returns TRUE, otherwise it returns FALSE).
```

```
@param (vector) a
@param (vector) b
@param (function) comparator-f
```

```
@usage
(compared-items-sorted? [0 1 3] [0 1 2] <)
```

```
@example
(compared-items-sorted? [0 1 3] [0 1 2] <)
=>
false
```

```
@example
(compared-items-sorted? [0 1 3] [0 1 4] <)
=>
true
```

```
@example
(compared-items-sorted? [0 1 3] [0 1 3] <)
=>
true
```

```
@example
(compared-items-sorted? [] [] <)
=>
true
```

```
@example
(compared-items-sorted? ["a" "b" "c"] ["d" "a"] string/abc?)
=>
true
```

```
@example
(compared-items-sorted? ["a" "b" "c"] ["a" "b"] string/abc?)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn compared-items-sorted?
  [a b comparator-f]
  (let [max-count (min (count a) (count b))]
       (letfn [(f [dex]
                  (let [x (get a dex)
                        y (get b dex)]
                       (if (= x y)
                           (if (= (inc dex) max-count)
                               (<= (count a) (count b))
                               (f (inc dex)))
                           (comparator-f x y))))]
              (case max-count 0 (-> true)
                                (f 0)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [compared-items-sorted?]]))

(vector.api/compared-items-sorted? ...)
(compared-items-sorted?            ...)
```

</details>

---

### concat-items

```
@param (list of vectors) abc
```

```
@usage
(concat-items [:a :b] [:c :d] [:e :f])
```

```
@example
(concat-items [:a :b] [:c :d] [:e :f])
=>
[:a :b :c :d :e :f]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn concat-items
  [& abc]
  (vec (apply concat abc)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [concat-items]]))

(vector.api/concat-items ...)
(concat-items            ...)
```

</details>

---

### concat-items-once

```
@param (list of vectors) abc
```

```
@usage
(concat-items-once [:a :b] [:a :c])
```

```
@example
(concat-items-once [:a :b :c] [:c :d :e :a])
=>
[:b :c :d :e :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn concat-items-once
  [& abc]
  (-> (apply concat abc) set vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [concat-items-once]]))

(vector.api/concat-items-once ...)
(concat-items-once            ...)
```

</details>

---

### conj-item

```
@param (vector) n
@param (list of *) xyz
```

```
@usage
(conj-item [:a :b] :c)
```

```
@usage
(conj-item [:a :b] :c :d)
```

```
@example
(conj-item [:a :b] :c)
=>
[:a :b :c]
```

```
@example
(conj-item [:a :b] :c :d)
=>
[:a :b :c :d]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn conj-item
  [n & xyz]
  (vec (apply conj n xyz)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [conj-item]]))

(vector.api/conj-item ...)
(conj-item            ...)
```

</details>

---

### conj-item-once

```
@description
Conj the item if the vector does not contain it.
```

```
@param (vector) n
@param (list of *) xyz
```

```
@usage
(conj-item-once [:a :b] :b)
```

```
@usage
(conj-item-once [:a :b] :b :c :d)
```

```
@example
(conj-item-once [:a :b] :b :c :d)
=>
[:a :b :c :d]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn conj-item-once
  [n & xyz]
  (letfn [(f [result x] (if (-> result (check/contains-item? x))
                            (-> result)
                            (-> result (conj x))))]
         (vec (reduce f n (vec xyz)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [conj-item-once]]))

(vector.api/conj-item-once ...)
(conj-item-once            ...)
```

</details>

---

### conj-some

```
@description
Conj the item if it is NOT nil.
```

```
@param (vector) n
@param (list of *) xyz
```

```
@usage
(conj-some [:a :b] :c)
```

```
@usage
(conj-some [:a :b] :c nil)
```

```
@example
(conj-some [:a :b] :c nil)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn conj-some
  [n & xyz]
  (letfn [(f [result x] (if x (-> result (conj x))
                              (-> result)))]
         (vec (reduce f n (vec xyz)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [conj-some]]))

(vector.api/conj-some ...)
(conj-some            ...)
```

</details>

---

### cons-item

```
@param (vector) n
@param (list of *) xyz
```

```
@usage
(cons-item [:a :b] :c :d :e)
```

```
@example
(cons-item [:a :b] :c :d :e)
=>
[:e :d :c :a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn cons-item
  [n & xyz]
  (letfn [(f [result x] (cons x result))]
         (vec (reduce f n (vec xyz)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [cons-item]]))

(vector.api/cons-item ...)
(cons-item            ...)
```

</details>

---

### cons-item-once

```
@description
Cons the item if the vector does not contain it.
```

```
@param (vector) n
@param (list of *) xyz
```

```
@usage
(cons-item-once [:a :b] :b)
```

```
@usage
(cons-item-once [:a :b] :b :c :d :e)
```

```
@example
(cons-item-once [:a :b] :b :c :d :e)
=>
[:e :d :c :a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn cons-item-once
  [n & xyz]
  (letfn [(f [result x] (if (-> result (check/contains-item? x))
                            (-> result)
                            (-> x (cons result))))]
         (vec (reduce f n (vec xyz)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [cons-item-once]]))

(vector.api/cons-item-once ...)
(cons-item-once            ...)
```

</details>

---

### contains-item?

```
@param (vector) n
@param (*) x
```

```
@usage
(contains-item? [:a :b] :a)
```

```
@example
(contains-item? [:a :b] :a)
=>
true
```

```
@example
(contains-item? [:a :b] :c)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-item?
  [n x]
  (letfn [(f [%] (= % x))]
         (boolean (some f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [contains-item?]]))

(vector.api/contains-item? ...)
(contains-item?            ...)
```

</details>

---

### contains-similars?

```
@param (vector) a
@param (vector) b
```

```
@usage
(contains-similars? [:a :b :c] [:c :d :e])
```

```
@example
(contains-similars? [:a :b :c] [:c :d :e])
=>
true
```

```
@example
(contains-similars? [:a :b :c] [:d :e :f])
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn contains-similars?
  [a b]
  (-> a (similars b)
        (empty?)
        (not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [contains-similars?]]))

(vector.api/contains-similars? ...)
(contains-similars?            ...)
```

</details>

---

### count!

```
@param (vector) n
@param (integer) x
```

```
@usage
(count! [:a :b :c] 3)
```

```
@example
(count! [:a :b :c] 3)
=>
[:a :b :c]
```

```
@example
(count! [:a :b :c] 2)
=>
[:a :b]
```

```
@example
(count! [:a :b :c] 5)
=>
[:a :b :c nil nil]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn count!
  [n x]
  (cond (= (count n) x) (-> n)
        (> (count n) x) (first-items n x)
        (< (count n) x) (vec (concat n (repeat nil (- x (count n)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [count!]]))

(vector.api/count! ...)
(count!            ...)
```

</details>

---

### count?

```
@param (vector) n
@param (integer) x
```

```
@usage
(count? [:a :b :c] 3)
```

```
@example
(count? [:a :b :c] 3)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn count?
  [n x]
  (or (and (-> n vector?)
           (=  x (count n)))
      (and (= n [])
           (= x 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [count?]]))

(vector.api/count? ...)
(count?            ...)
```

</details>

---

### dec-dex

```
@description
Returns with the previous item's index before the given dex.
At the beginning of the vector it stops.
```

```
@param (vector) n
@param (integer) dex
```

```
@usage
(dec-dex [:a :b :c :d] 2)
```

```
@example
(dec-dex [:a :b :c :d] 3)
=>
2
```

```
@example
(dec-dex [:a :b :c :d] 0)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn dec-dex
  [n dex]
  (if (-> dex dex-first?)
      (-> dex)
      (-> dex dec)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dec-dex]]))

(vector.api/dec-dex ...)
(dec-dex            ...)
```

</details>

---

### dex-first?

```
@param (integer) dex
```

```
@usage
(dex-first? 1)
```

```
@example
(dex-first? 1)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-first?
  [dex]
  (= dex 0))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dex-first?]]))

(vector.api/dex-first? ...)
(dex-first?            ...)
```

</details>

---

### dex-in-bounds?

```
@param (vector) n
@param (integer) dex
```

```
@usage
(dex-out-in-bounds? [:a :b :c] 0)
```

```
@example
(dex-out-in-bounds? [:a :b :c] 2)
=>
true
```

```
@example
(dex-out-in-bounds? [:a :b :c] 3)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-in-bounds?
  [n dex]
  (and (>= dex 0)
       (<  dex (count n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dex-in-bounds?]]))

(vector.api/dex-in-bounds? ...)
(dex-in-bounds?            ...)
```

</details>

---

### dex-last?

```
@param (vector) n
@param (integer) dex
```

```
@usage
(dex-last? [:a :b :c] 2)
```

```
@example
(dex-last? [:a :b :c] 2)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-last?
  [n dex]
  (= (inc   dex)
     (count n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dex-last?]]))

(vector.api/dex-last? ...)
(dex-last?            ...)
```

</details>

---

### dex-out-of-bounds?

```
@param (vector) n
@param (integer) dex
```

```
@usage
(dex-out-of-bounds? [:a :b :c] 0)
```

```
@example
(dex-out-of-bounds? [:a :b :c] 3)
=>
true
```

```
@example
(dex-out-of-bounds? [:a :b :c] 2)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn dex-out-of-bounds?
  [n dex]
  (or (<  dex 0)
      (>= dex (count n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dex-out-of-bounds?]]))

(vector.api/dex-out-of-bounds? ...)
(dex-out-of-bounds?            ...)
```

</details>

---

### dex-range

```
@param (vector) n
```

```
@usage
(dex-range [:a :b :c])
```

```
@example
(dex-range [:a :b :c])
=>
[0 1 2]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn dex-range
  [n]
  (-> n count range vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [dex-range]]))

(vector.api/dex-range ...)
(dex-range            ...)
```

</details>

---

### difference

```
@param (vector) a
@param (vector) b
```

```
@usage
(difference [:a :b :c] [:b :c])
```

```
@example
(difference [:a :b :c] [:b :c])
=>
[:a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn difference
  [a b]
  (remove/remove-items a b))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [difference]]))

(vector.api/difference ...)
(difference            ...)
```

</details>

---

### duplicate-nth-item

```
@param (vector) n
@param (integer) dex
```

```
@usage
(duplicate-nth-item [:a :b :c] 0)
```

```
@example
(duplicate-nth-item [:a :b :c :d :e] 2)
=>
[:a :b :c :c :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn duplicate-nth-item
  [n dex]
  (letfn [(f [result item-dex item]
             (if (= item-dex dex)
                 (conj result item item)
                 (conj result item)))]
         (reduce-kv f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [duplicate-nth-item]]))

(vector.api/duplicate-nth-item ...)
(duplicate-nth-item            ...)
```

</details>

---

### duplicate-nth-items

```
@param (vector) n
@param (integers in vector) dexes
```

```
@usage
(duplicate-nth-items [:a :b :c] [0 2])
```

```
@example
(duplicate-nth-items [:a :b :c :d :e] [0 2])
=>
[:a :a :b :c :c :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn duplicate-nth-items
  [n dexes])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [duplicate-nth-items]]))

(vector.api/duplicate-nth-items ...)
(duplicate-nth-items            ...)
```

</details>

---

### filter-items

```
@param (vector) n
@param (function) filter-f
```

```
@usage
(filter-items [:a :b "c"] keyword?)
```

```
@example
(filter-items [:a :b "c"] keyword?)
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn filter-items
  [n filter-f]
  (vec (filter filter-f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [filter-items]]))

(vector.api/filter-items ...)
(filter-items            ...)
```

</details>

---

### filter-items-by

```
@param (vector) n
@param (function) filter-f
@param (function) value-f
```

```
@usage
(filter-items-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
```

```
@example
(filter-items-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
=>
[{:value :a} {:value :b}]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn filter-items-by
  [n filter-f value-f])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [filter-items-by]]))

(vector.api/filter-items-by ...)
(filter-items-by            ...)
```

</details>

---

### filtered-count

```
@param (vector) n
@param (function) filter-f
```

```
@usage
(filtered-count [:a :b "c"] string?)
```

```
@example
(filtered-count [:a :b "c"] keyword?)
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn filtered-count
  [n filter-f]
  (count (filter filter-f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [filtered-count]]))

(vector.api/filtered-count ...)
(filtered-count            ...)
```

</details>

---

### filtered-count?

```
@param (vector) n
@param (function) filter-f
@param (integer) x
```

```
@usage
(filtered-count? [:a :b "c"] string? 1)
```

```
@example
(filtered-count? [:a :b "c"] keyword? 2)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn filtered-count?
  [n filter-f x]
  (= x (filtered-count n filter-f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [filtered-count?]]))

(vector.api/filtered-count? ...)
(filtered-count?            ...)
```

</details>

---

### first-filtered

```
@param (vector) n
@param (function) filter-f
```

```
@usage
(first-filtered ["a" :b "c"] keyword?)
```

```
@example
(first-filtered ["a" :b "c" :d "e"] keyword?)
=>
:b
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn first-filtered
  [n filter-f]
  (letfn [(f [%] (if (filter-f %) %))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [first-filtered]]))

(vector.api/first-filtered ...)
(first-filtered            ...)
```

</details>

---

### first-filtered-by

```
@param (vector) n
@param (function) filter-f
@param (function) value-f
```

```
@usage
(first-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
```

```
@example
(first-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
=>
{:value :a}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn first-filtered-by
  [n filter-f value-f]
  (letfn [(f [%] (if (-> % value-f filter-f) %))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [first-filtered-by]]))

(vector.api/first-filtered-by ...)
(first-filtered-by            ...)
```

</details>

---

### first-item

```
@param (vector) n
```

```
@usage
(first-item [:a :b :c])
```

```
@example
(first-item [:a :b :c])
=>
:a
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn first-item
  [n]
  (first n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [first-item]]))

(vector.api/first-item ...)
(first-item            ...)
```

</details>

---

### first-items

```
@param (vector) n
@param (integer) length
```

```
@usage
(first-items [:a :b :c] 2)
```

```
@example
(first-items [:a :b :c :d :e] 3)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn first-items
  [n length]
  (cond (-> length integer? not) (-> n)
        (>= length (count n))    (-> n)
        :return (subvec n 0 length)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [first-items]]))

(vector.api/first-items ...)
(first-items            ...)
```

</details>

---

### gap-items

```
@param (*) n
@param (*) delimiter
```

```
@usage
(gap-items [:A :B] :x)
```

```
@example
(gap-items [:A :B :C :D] :x)
=>
[:A :x :B :x :C :x :D]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn gap-items
  [n delimiter]
  (letfn [(f [result dex x]
             (if (-> dex zero?)
                 [x]
                 (conj result delimiter x)))]
         (reduce-kv f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [gap-items]]))

(vector.api/gap-items ...)
(gap-items            ...)
```

</details>

---

### get-first-match

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-first-match [:a :b :c "d"] string?)
```

```
@example
(get-first-match [:a :b :c] string?)
=>
nil
```

```
@example
(get-first-match [:a "b" :c] string?)
=>
"b"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match
  [n test-f]
  (letfn [(f [%] (if (-> % test-f)
                     (-> %)))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [get-first-match]]))

(vector.api/get-first-match ...)
(get-first-match            ...)
```

</details>

---

### get-first-match-dex

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-first-match-dex [:a :b :c "d"] string?)
```

```
@example
(get-first-match-dex [:a :b :c] string?)
=>
nil
```

```
@example
(get-first-match-dex [:a "b" :c] string?)
=>
1
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match-dex
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %1))]
         (some-indexed f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [get-first-match-dex]]))

(vector.api/get-first-match-dex ...)
(get-first-match-dex            ...)
```

</details>

---

### get-last-match

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-last-match [:a :b :c "d"] string?)
```

```
@example
(get-last-match [:a :b :c] string?)
=>
nil
```

```
@example
(get-last-match [:a "b" :c] string?)
=>
"b"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-last-match
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %2 %1))]
         (reduce f nil n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [get-last-match]]))

(vector.api/get-last-match ...)
(get-last-match            ...)
```

</details>

---

### get-last-match-dex

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-last-match-dex [:a :b :c "d"] string?)
```

```
@example
(get-last-match-dex [:a :b :c] string?)
=>
nil
```

```
@example
(get-last-match-dex [:a "b" :c] string?)
=>
1
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-last-match-dex
  [n test-f]
  (letfn [(f [%1 %2 %3] (if (test-f %3) %2 %1))]
         (reduce-kv f nil n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [get-last-match-dex]]))

(vector.api/get-last-match-dex ...)
(get-last-match-dex            ...)
```

</details>

---

### inc-dex

```
@description
Returns with the next item's index after the given dex.
At the end of the vector it stops.
```

```
@param (vector) n
@param (integer) dex
```

```
@usage
(inc-dex [:a :b :c :d] 3)
```

```
@example
(inc-dex [:a :b :c :d] 1)
=>
2
```

```
@example
(inc-dex [:a :b :c :d] 3)
=>
3
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn inc-dex
  [n dex]
  (if (-> n (dex-last? dex))
      (-> dex)
      (-> dex inc)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [inc-dex]]))

(vector.api/inc-dex ...)
(inc-dex            ...)
```

</details>

---

### inject-item

```
@param (vector) n
@param (integer) dex
@param (*) x
```

```
@usage
(inject-item [:a :b :c] 0 :x)
```

```
@example
(inject-item [:a :b :c] 2 :d)
=>
[:a :b :d :c]
```

```
@example
(inject-item [:a :b :c] 999 :d)
=>
[:a :b :d :c]
```

```
@example
(inject-item nil 999 :d)
=>
[:d]
```

```
@example
(inject-item {:a "b"} 1 :d)
=>
{:a "b"}
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn inject-item
  [n dex x]
  (cond (vector? n)
        (if (dex/dex-out-of-bounds? n dex)
            (conj-item n x)
            (concat-items (subvec n 0 dex)
                          [x]
                          (subvec n dex)))
        (nil? n) (-> [x])
        :return n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [inject-item]]))

(vector.api/inject-item ...)
(inject-item            ...)
```

</details>

---

### item-dex?

```
@param (*) n
```

```
@usage
(item-dex? 42)
```

```
@example
(item-dex? 42)
=>
true
```

```
@example
(item-dex? -3)
=>
false
```

```
@example
(item-dex? :a)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn item-dex?
  [n]
  (and (-> n integer?)
       (>= n 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [item-dex?]]))

(vector.api/item-dex? ...)
(item-dex?            ...)
```

</details>

---

### item-first-dex

```
@param (vector) n
@param (*) x
```

```
@example
(item-first-dex [:a :b :a :b] :b)
=>
1
```

```
@return (nil or integer)
```

<details>
<summary>Source code</summary>

```
(defn item-first-dex
  [n x]
  (when (vector? n)
        (letfn [(f [%1 %2] (if (= %2 x) %1))]
               (some-indexed f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [item-first-dex]]))

(vector.api/item-first-dex ...)
(item-first-dex            ...)
```

</details>

---

### item-first?

```
@param (vector) n
@param (*) x
```

```
@usage
(item-first? [:a :b :c] :a)
```

```
@example
(item-first? [:a :b] :a)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn item-first?
  [n x]
  (= (first n) x))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [item-first?]]))

(vector.api/item-first? ...)
(item-first?            ...)
```

</details>

---

### item-last-dex

```
@param (vector) n
@param (*) x
```

```
@usage
(item-last-dex [:a :b :a :b] :b)
```

```
@example
(item-last-dex [:a :b :a :b] :b)
=>
3
```

```
@return (nil or integer)
```

<details>
<summary>Source code</summary>

```
(defn item-last-dex
  [n x]
  (when (vector? n)
        (letfn [(f [%1 %2 %3] (if (= %3 x) %2 %1))]
               (reduce-kv f nil n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [item-last-dex]]))

(vector.api/item-last-dex ...)
(item-last-dex            ...)
```

</details>

---

### item-last?

```
@param (vector) n
@param (*) x
```

```
@usage
(item-last? [:a :b :c] :c)
```

```
@example
(item-last? [:a :b] :b)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn item-last?
  [n x]
  (= (last n) x))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [item-last?]]))

(vector.api/item-last? ...)
(item-last?            ...)
```

</details>

---

### items-after-first-occurence

```
@param (vector) n
@param (*) x
```

```
@example
(items-after-first-occurence [:a :b :c] :b)
  ;
@example
(items-after-first-occurence [:a :b :c :d :d :e] :d)
=>
[:d :e]
```

```
@example
(items-after-first-occurence [:a :b :c :d :d :e] :d)
=>
[:d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn items-after-first-occurence
  [n x]
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (if (number? item-first-dex)
              (subvec n (inc item-first-dex)))
          (-> [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [items-after-first-occurence]]))

(vector.api/items-after-first-occurence ...)
(items-after-first-occurence            ...)
```

</details>

---

### items-before-first-occurence

```
@param (vector) n
@param (*) x
```

```
@usage
(items-before-first-occurence [:a :b :c] :c)
```

```
@example
(items-before-first-occurence [:a :b :c :d :d :e] :d)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn items-before-first-occurence
  [n x]
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (subvec n 0 item-first-dex)
          (-> [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [items-before-first-occurence]]))

(vector.api/items-before-first-occurence ...)
(items-before-first-occurence            ...)
```

</details>

---

### items-sorted?

```
@description
Returns TRUE if the given vector's items are ordered with the given comparator function.
```

```
@param (vector) n
@param (function) comparator-f
```

```
@usage
(items-sorted? ["a" "c" "b"] string/abc?)
```

```
@example
(items-sorted? ["a" "c" "b"] string/abc?)
=>
false
```

```
@example
(items-sorted? ["a" "b" "c"] string/abc?)
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn items-sorted?
  [n comparator-f]
  (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
         (= n (sort-items n compare-f))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [items-sorted?]]))

(vector.api/items-sorted? ...)
(items-sorted?            ...)
```

</details>

---

### keep-items

```
@param (vector) n
@param (vector) xyz
```

```
@usage
(keep-items [:a :b :c :d] [:b :c])
```

```
@example
(keep-items [:a :b :c :d] [:b :c])
=>
[:b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn keep-items
  [n xyz]
  (letfn [(f [result x]
             (if (check/contains-item? xyz x)
                 (conj result x)
                 (->   result)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [keep-items]]))

(vector.api/keep-items ...)
(keep-items            ...)
```

</details>

---

### keep-items-by

```
@param (vector) n
@param (function) f
```

```
@usage
(keep-items-by [:a :b "c" "d"] keyword?)
```

```
@example
(keep-items-by [:a :b "c" "d"] keyword?)
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn keep-items-by
  [n f]
  (letfn [(f0 [result x]
              (if (f x)
                  (conj result x)
                  (->   result)))]
         (reduce f0 [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [keep-items-by]]))

(vector.api/keep-items-by ...)
(keep-items-by            ...)
```

</details>

---

### last-dex

```
@param (vector) n
```

```
@usage
(last-dex [:a :b :c])
```

```
@example
(last-dex [:a :b :c])
=>
2
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn last-dex
  [n]
  (if (-> n type/nonempty?)
      (-> n count dec)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [last-dex]]))

(vector.api/last-dex ...)
(last-dex            ...)
```

</details>

---

### last-filtered

```
@param (vector) n
@param (function) filter-f
```

```
@usage
(last-filtered ["a" :b "c"] string?)
```

```
@example
(last-filtered ["a" :b "c" :d "e"] keyword?)
=>
:d
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn last-filtered
  [n filter-f]
  (letfn [(f [%1 %2] (if (filter-f %2) %2 %1))]
         (reduce f nil n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [last-filtered]]))

(vector.api/last-filtered ...)
(last-filtered            ...)
```

</details>

---

### last-filtered-by

```
@param (vector) n
@param (function) filter-f
@param (function) value-f
```

```
@usage
(last-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
```

```
@example
(last-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? :value)
=>
{:value :b}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn last-filtered-by
  [n filter-f value-f]
  (letfn [(f [%1 %2] (if (-> %2 value-f filter-f) %2 %1))]
         (reduce f nil n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [last-filtered-by]]))

(vector.api/last-filtered-by ...)
(last-filtered-by            ...)
```

</details>

---

### last-item

```
@param (vector) n
```

```
@usage
(last-item [:a :b :c])
```

```
@example
(last-item [:a :b :c])
=>
:c
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn last-item
  [n]
  (last n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [last-item]]))

(vector.api/last-item ...)
(last-item            ...)
```

</details>

---

### last-items

```
@param (vector) n
@param (integer) length
```

```
@usage
(last-items [:a :b :c] 2)
```

```
@example
(last-items [:a :b :c :d :e] 2)
=>
[:d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn last-items
  [n length]
  (cond (-> length integer? not) (-> n)
        (>= length (count n))    (-> n)
        :return (subvec n (-> n count (- length)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [last-items]]))

(vector.api/last-items ...)
(last-items            ...)
```

</details>

---

### longer?

```
@param (vector) a
@param (vector) b
```

```
@usage
(longer? [:a :b] [:a :b :c])
```

```
@example
(longer? [:a :b :c] [:a :b :c :d])
=>
true
```

```
@example
(longer? [:a :b :c :d] [:a :b])
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn longer?
  [a b]
  (and (vector? a)
       (vector? b)
       (> (count b)
          (count a))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [longer?]]))

(vector.api/longer? ...)
(longer?            ...)
```

</details>

---

### match-dex

```
@param (vector) n
@param (integer) dex
```

```
@example
(match-dex ["a" "b" "c"] 0)
=>
0
```

```
@example
(match-dex ["a" "b" "c"] 1)
=>
1
```

```
@example
(match-dex ["a" "b" "c"] 2)
=>
2
```

```
@example
(match-dex ["a" "b" "c"] 3)
=>
0
```

```
@example
(match-dex ["a" "b" "c"] 4)
=>
1
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn match-dex
  [n dex]
  (let [count (count n)
        x     (-> dex (/ count)
                      (math/floor))
        y     (* x count)]
       (- dex y)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [match-dex]]))

(vector.api/match-dex ...)
(match-dex            ...)
```

</details>

---

### max?

```
@param (vector) n
@param (integer) x
```

```
@usage
(max? [:a :b :c] 2)
```

```
@example
(max? [:a :b :c] 3)
=>
true
```

```
@example
(max? [:a :b :c :d] 3)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn max?
  [n x]
  (and (-> n vector?)
       (>= x (count n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [max?]]))

(vector.api/max? ...)
(max?            ...)
```

</details>

---

### min?

```
@param (vector) n
@param (integer) x
```

```
@usage
(min? [:a :b :c] 2)
```

```
@example
(min? [:a :b :c] 3)
=>
true
```

```
@example
(min? [:a :b] 3)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn min?
  [n x]
  (and (-> n vector?)
       (<= x (count n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [min?]]))

(vector.api/min? ...)
(min?            ...)
```

</details>

---

### move-first-occurence

```
@param (vector) n
@param (*) x
@param (integer) dex
```

```
@usage
(move-first-occurence [:a :b :c] :c 0)
```

```
@example
(move-first-occurence [:a :b :c :a :b :c] :b 3)
=>
[:a :c :b :a :b :c]
```

```
@example
(move-first-occurence [:a :b :c :a :b :c] :b 1)
=>
[:a :b :c :a :b :c]
```

```
@example
(move-first-occurence [:a :b :c :a :b :c] :b 20)
=>
[:a :c :a :b :c :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-first-occurence
  [n x dex]
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (move-nth-item n item-first-dex dex)
          (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-first-occurence]]))

(vector.api/move-first-occurence ...)
(move-first-occurence            ...)
```

</details>

---

### move-item-to-first

```
@param (vector) n
@param (*) x
```

```
@example
(move-item-to-first [:a :b :c] :c)
  ;
@example
(move-item-to-first [:a :b] :b)
=>
[:b :a]
```

```
@example
(move-item-to-first [:a :b] :b)
=>
[:b :a]
```

```
@example
(move-item-to-first [:a] :b)
=>
[:b :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-item-to-first
  [n x]
  (vec (cons x (remove/remove-item n x))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-item-to-first]]))

(vector.api/move-item-to-first ...)
(move-item-to-first            ...)
```

</details>

---

### move-item-to-last

```
@param (vector) n
@param (*) x
```

```
@usage
(move-item-to-last [:a :b :c] :a)
```

```
@example
(move-item-to-last [:a :b] :a)
=>
[:b :a]
```

```
@example
(move-item-to-last [:b] :a)
=>
[:b :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-item-to-last
  [n x]
  (conj (remove/remove-item n x) x))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-item-to-last]]))

(vector.api/move-item-to-last ...)
(move-item-to-last            ...)
```

</details>

---

### move-nth-item

```
@param (vector) n
@param (integer) from
@param (integer) to
```

```
@usage
(move-nth-item [:a :b :c] 0 2)
```

```
@example
(move-nth-item [:a :b :c :d :e :f :g :h] 2 2)
=>
[:a :b :c :d :e :f :g :h]
```

```
@example
(move-nth-item [:a :b :c :d :e :f :g :h] 2 5)
=>
[:a :b :d :e :c :f :g :h]
```

```
@example
(move-nth-item [:a :b :c :d :e :f :g :h] 5 2)
=>
[:a :b :f :c :d :e :g :h]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-nth-item
  [n from to]
  (if (dex/dex-in-bounds? n from)
      (let [to (math/between! to 0 (count n))]
           (cond
                 (= from to) (-> n)

                 (< from to) (vec (concat (range/ranged-items n 0 from)
                                          (range/ranged-items n (inc from) (inc to))
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n (inc to))))

                 (> from to) (vec (concat (range/ranged-items n 0 to)
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n to from)
                                          (range/ranged-items n (inc from))))))
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-nth-item]]))

(vector.api/move-nth-item ...)
(move-nth-item            ...)
```

</details>

---

### move-nth-item-bwd

```
@param (vector) n
@param (integer) dex
```

```
@usage
(move-nth-item-bwd [:a :b :c] 1)
```

```
@example
(move-nth-item-bwd [:a :b :c :d] 2)
=>
[:a :c :b :d]
```

```
@example
(move-nth-item-bwd [:a :b :c :d] 0)
=>
[:b :c :d :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-nth-item-bwd
  [n dex]
  (move-nth-item n dex (dex/prev-dex n dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-nth-item-bwd]]))

(vector.api/move-nth-item-bwd ...)
(move-nth-item-bwd            ...)
```

</details>

---

### move-nth-item-fwd

```
@param (vector) n
@param (integer) dex
```

```
@usage
(move-nth-item-fwd [:a :b :c] 1)
```

```
@example
(move-nth-item-fwd [:a :b :c :d] 2)
=>
[:a :b :d :c]
```

```
@example
(move-nth-item-fwd [:a :b :c :d] 3)
=>
[:d :a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-nth-item-fwd
  [n dex]
  (move-nth-item n dex (dex/next-dex n dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [move-nth-item-fwd]]))

(vector.api/move-nth-item-fwd ...)
(move-nth-item-fwd            ...)
```

</details>

---

### next-dex

```
@description
Returns with the next item's index after the given dex.
At the end of the vector it jumps to the first index.
```

```
@param (vector) n
@param (integer) dex
```

```
@usage
(next-dex [:a :b :c :d] 0)
```

```
@example
(next-dex [:a :b :c :d] 1)
=>
2
```

```
@example
(next-dex [:a :b :c :d] 3)
=>
0
```

```
@example
(next-dex [] 3)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn next-dex
  [n dex]
  (if (-> n type/nonempty?)
      (-> dex (sequence/next-dex 0 (-> n count dec)))
      (-> 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [next-dex]]))

(vector.api/next-dex ...)
(next-dex            ...)
```

</details>

---

### next-item

```
@param (vector) n
@param (*) x
```

```
@usage
(next-item [:a :b :c] :a)
```

```
@example
(next-item [:a :b :c :d] :a)
=>
:b
```

```
@example
(next-item [:a :b :c :d] nil)
=>
:a
```

```
@example
(next-item [] :a)
=>
?
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn next-item
  [n x]
  (let [item-first-dex (dex/item-first-dex n x)
        next-item-dex  (dex/next-dex       n item-first-dex)]
       (nth/nth-item n next-item-dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [next-item]]))

(vector.api/next-item ...)
(next-item            ...)
```

</details>

---

### nonempty?

```
@param (*) n
```

```
@usage
(nonempty? [:a])
```

```
@example
(nonempty? [])
=>
false
```

```
@example
(nonempty? [:a])
=>
true
```

```
@return (boolean)
Is n a nonempty vector?
```

<details>
<summary>Source code</summary>

```
(defn nonempty?
  [n]
  (and (-> n vector?)
       (-> n empty? not)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [nonempty?]]))

(vector.api/nonempty? ...)
(nonempty?            ...)
```

</details>

---

### not-contains-item?

```
@param (vector) n
@param (*) x
```

```
@usage
(not-contains-item? [:a :b] :a)
```

```
@example
(not-contains-item? [:a :b] :c)
=>
true
```

```
@example
(not-contains-item? [:a :b] :a)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn not-contains-item?
  [n x]
  (letfn [(f [%] (= % x))]
         (not (some f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [not-contains-item?]]))

(vector.api/not-contains-item? ...)
(not-contains-item?            ...)
```

</details>

---

### nth-filtered

```
@param (vector) n
@param (function) filter-f
@param (integer) dex
```

```
@usage
(nth-filtered ["a" :b "c"] keyword? 1)
```

```
@example
(nth-filtered ["a" :b "c" :d "e"] keyword? 2)
=>
:d
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn nth-filtered
  [n filter-f dex]
  (letfn [(f [x match-count f-dex]
             (if (-> x filter-f not)
                 (if (dex/dex-last? n f-dex)
                     (-> nil)
                     (f (get n (inc f-dex)) match-count (inc f-dex)))
                 (if (< match-count dex)
                     (if (dex/dex-last? n f-dex)
                         (-> nil)
                         (f (get n (inc f-dex)) (inc match-count) (inc f-dex)))
                     (-> x))))]
         (f (first n) 0 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [nth-filtered]]))

(vector.api/nth-filtered ...)
(nth-filtered            ...)
```

</details>

---

### nth-filtered-by

```
@param (vector) n
@param (function) filter-f
@param (function) value-f
@param (integer) dex
```

```
@usage
(nth-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? 1)
```

```
@example
(nth-filtered-by [{:value :a} {:value :b} {:value "c"}] keyword? 2)
=>
{:value :b}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn nth-filtered-by
  [n filter-f value-f dex])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [nth-filtered-by]]))

(vector.api/nth-filtered-by ...)
(nth-filtered-by            ...)
```

</details>

---

### nth-item

```
@param (vector) n
@param (integer) dex
```

```
@usage
(nth-item [:a :b :c] 1)
```

```
@example
(nth-item [:a :b :c] 2)
=>
:c
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn nth-item
  [n dex]
  (when (and (vector?            n)
             (dex/dex-in-bounds? n dex))
        (nth n dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [nth-item]]))

(vector.api/nth-item ...)
(nth-item            ...)
```

</details>

---

### only-item?

```
@param (vector) n
@param (*) x
```

```
@usage
(only-item? [:c] :c)
```

```
@example
(only-item? [:b] :b)
=>
true
```

```
@example
(only-item? [:a :b] :b)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn only-item?
  [n x]
  (= n [x]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [only-item?]]))

(vector.api/only-item? ...)
(only-item?            ...)
```

</details>

---

### prev-dex

```
@description
Returns with the previous item's index before the given dex.
At the beginning of the vector it jumps to the last index.
```

```
@param (vector) n
@param (integer) dex
```

```
@example
(prev-dex [:a :b :c :d] 0)
=>
3
```

```
@example
(prev-dex [:a :b :c :d] 0)
=>
3
```

```
@example
(prev-dex [] 3)
=>
0
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn prev-dex
  [n dex]
  (if (-> n vector?)
      (-> dex (sequence/prev-dex 0 (-> n count dec)))
      (-> 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [prev-dex]]))

(vector.api/prev-dex ...)
(prev-dex            ...)
```

</details>

---

### prev-item

```
@param (vector) n
@param (*) x
```

```
@example
(prev-item [:a :b :c] :c)
  ;
@example
(prev-item [:a :b :c :d] :b)
=>
:a
```

```
@example
(prev-item [:a :b :c :d] :b)
=>
:a
```

```
@example
(prev-item [:a :b :c :d] nil)
=>
:d
```

```
@example
(prev-item [] :a)
=>
?
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn prev-item
  [n x])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [prev-item]]))

(vector.api/prev-item ...)
(prev-item            ...)
```

</details>

---

### ranged-items

```
@param (vector) n
@param (integer) low
@param (integer)(opt) high
```

```
@usage
(ranged-items [:a :b :c] 1 3)
```

```
@usage
(ranged-items [:a :b :c] 1)
```

```
@example
(ranged-items [:a :b :c :d :e :f] 1 3)
=>
[:b :c]
```

```
@example
(ranged-items [:a :b :c :d :e :f] 2)
=>
[:c :d :e :f]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn ranged-items
  ([n low]
   (let [high (count n)]
        (ranged-items n low high)))

  ([n low high]
   (let [high (min high (count n))]
        (if (and (vector? n)
                 (<  low high)
                 (>= low 0))
            (subvec n low high)
            (-> [])))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [ranged-items]]))

(vector.api/ranged-items ...)
(ranged-items            ...)
```

</details>

---

### remove-duplicates

```
@param (vector) n
```

```
@usage
(remove-duplicates [:a :b :c :a])
```

```
@example
(remove-duplicates [:a :b :c :a])
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-duplicates
  [n]
  (letfn [(f [result x]
             (if (check/contains-item? result x)
                 (->   result)
                 (conj result x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-duplicates]]))

(vector.api/remove-duplicates ...)
(remove-duplicates            ...)
```

</details>

---

### remove-first-item

```
@param (vector) n
```

```
@usage
(remove-first-item [:a :b :c])
```

```
@example
(remove-first-item [:a :b :c :d :e])
=>
[:b :c :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-first-item
  [n]
  (vec (drop 1 n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-first-item]]))

(vector.api/remove-first-item ...)
(remove-first-item            ...)
```

</details>

---

### remove-first-items

```
@param (vector) n
@param (integer) cut
```

```
@usage
(remove-first-items [:a :b :c] 2)
```

```
@example
(remove-first-items [:a :b :c :d :e] 2)
=>
[:c :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-first-items
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n cut)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-first-items]]))

(vector.api/remove-first-items ...)
(remove-first-items            ...)
```

</details>

---

### remove-first-occurence

```
@param (vector) n
@param (*) x
```

```
@usage
(remove-first-occurence [:a :b :b :c] :b)
```

```
@example
(remove-first-occurence [:a :b :c :d :d :e] :d)
=>
[:a :b :c :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-first-occurence
  [n x]
  (if-let [item-dex (dex/item-first-dex n x)]
          (if (number? item-dex)
              (vec (concat (subvec n 0 item-dex)
                           (subvec n (inc item-dex)))))
          (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-first-occurence]]))

(vector.api/remove-first-occurence ...)
(remove-first-occurence            ...)
```

</details>

---

### remove-item

```
@param (vector) n
@param (*) dex
```

```
@usage
(remove-item [:a :b] :b)
```

```
@example
(remove-item [:a :b] :b)
=>
[:a]
```

```
@example
(remove-item [:a :b :a] :a)
=>
[:b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-item
  [n x]
  (letfn [(f [%] (= % x))]
         (vec (remove f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-item]]))

(vector.api/remove-item ...)
(remove-item            ...)
```

</details>

---

### remove-item-once

```
@param (vector) n
@param (*) dex
```

```
@usage
(remove-item-once [:a :b :b] :b)
```

```
@example
(remove-item-once [:a :b :b] :b)
=>
[:a :b]
```

```
@example
(remove-item-once [:a :b :a] :a)
=>
[:b :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-item-once
  [n x]
  (let [count (count n)]
       (letfn [(f [dex]
                  (cond (and (= dex count)
                             (= x (nth n dex)))
                        (subvec n 0 (-> n count dec))
                        (= dex count)
                        (-> n)
                        (= x (nth n dex))
                        (vec (concat (subvec n 0 dex)
                                     (subvec n (inc dex))))
                        :else
                        (-> dex inc f)))]
              (f 0))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-item-once]]))

(vector.api/remove-item-once ...)
(remove-item-once            ...)
```

</details>

---

### remove-items

```
@param (vector) n
@param (vector) xyz
```

```
@usage
(remove-items [:a :b :c] [:b :c])
```

```
@example
(remove-items [:a :b :c] [:b :c])
=>
[:a]
```

```
@example
(remove-items [:a :b :b :c ] [:b :c])
=>
[:a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-items
  [n xyz]
  (vec (remove (set xyz) n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-items]]))

(vector.api/remove-items ...)
(remove-items            ...)
```

</details>

---

### remove-items-by

```
@param (vector) n
@param (function) f
```

```
@usage
(remove-items-by [:a :b :c] keyword?)
```

```
@example
(remove-items-by [:a :b :c] keyword?)
=>
[]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-items-by
  [n test-f]
  (letfn [(f [result x]
             (if (-> x test-f)
                 (->   result)
                 (conj result x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-items-by]]))

(vector.api/remove-items-by ...)
(remove-items-by            ...)
```

</details>

---

### remove-items-kv

```
@param (maps in vector) n
@param (*) k
@param (*) v
```

```
@example
(remove-items-kv [{:a "a"} {:b "b"} {:c "c"}] :b "b")
=>
[{:a "a"} {:c "c"}]
```

```
@return (maps in vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-items-kv
  [n k v]
  (letfn [(f [%] (= (k %) v))]
         (vec (remove f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-items-kv]]))

(vector.api/remove-items-kv ...)
(remove-items-kv            ...)
```

</details>

---

### remove-last-item

```
@param (vector) n
```

```
@usage
(remove-last-item [:a :b :c])
```

```
@example
(remove-last-item [:a :b :c :d :e])
=>
[:a :b :c :d]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-last-item
  [n]
  (-> n drop-last vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-last-item]]))

(vector.api/remove-last-item ...)
(remove-last-item            ...)
```

</details>

---

### remove-last-items

```
@param (vector) n
@param (integer) cut
```

```
@usage
(remove-last-items [:a :b :c] 2)
```

```
@example
(remove-last-items [:a :b :c :d :e] 2)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-last-items
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n 0 (-> n count (- cut)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-last-items]]))

(vector.api/remove-last-items ...)
(remove-last-items            ...)
```

</details>

---

### remove-nth-item

```
@param (vector) n
@param (integer) dex
```

```
@usage
(remove-nth-item [:a :b :c] 2)
```

```
@example
(remove-nth-item [:a :b :c :d :e] 2)
=>
[:a :b :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-nth-item
  [n dex]
  (when (and (vector?            n)
             (dex/dex-in-bounds? n dex))
        (vec (concat (subvec n 0 dex)
                     (subvec n (inc dex))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-nth-item]]))

(vector.api/remove-nth-item ...)
(remove-nth-item            ...)
```

</details>

---

### remove-nth-items

```
@param (vector) n
@param (integers in vector) dexes
```

```
@usage
(remove-nth-item [:a :b :c] [0 2])
```

```
@example
(remove-nth-item [:a :b :c :d :e] [0 2])
=>
[:b :d :e]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn remove-nth-items
  [n dexes]
  (letfn [(remove-nth-items-f [result dex x]
                              (if (check/contains-item? dexes dex)
                                  (->   result)
                                  (conj result x)))]
         (reduce-kv remove-nth-items-f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [remove-nth-items]]))

(vector.api/remove-nth-items ...)
(remove-nth-items            ...)
```

</details>

---

### repeat-item

```
@param (*) n
@param (integer) count
```

```
@usage
(repeat-item :a 5)
```

```
@example
(repeat-item :a 5)
=>
[:a :a :a :a :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn repeat-item
  [n count]
  (vec (repeat count n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [repeat-item]]))

(vector.api/repeat-item ...)
(repeat-item            ...)
```

</details>

---

### replace-nth-item

```
@param (vector) n
@param (integer) dex
@param (*) x
```

```
@usage
(replace-nth-item [:a :b :c] 0 :x)
```

```
@example
(replace-nth-item [:a :b :c :d] 1 :x)
=>
[:a :x :c :d]
```

```
@example
(replace-nth-item [:a :b :c :d] 999 :x)
=>
[:a :b :c :d]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn replace-nth-item
  [n dex x]
  (if (and (vector?            n)
           (dex/dex-in-bounds? n dex))
      (vec (concat (subvec n 0 dex)
                   [x]
                   (subvec n (inc dex))))
      (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [replace-nth-item]]))

(vector.api/replace-nth-item ...)
(replace-nth-item            ...)
```

</details>

---

### reverse-items

```
@description
Returns the given vector but its items are in reversed order.
```

```
@param (vector) n
```

```
@usage
(reverse-items [:a :b :c])
```

```
@example
(reverse-items [:a :b :c])
=>
[:c :b :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn reverse-items
  [n]
  (-> n reverse vec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [reverse-items]]))

(vector.api/reverse-items ...)
(reverse-items            ...)
```

</details>

---

### similars

```
@param (vector) a
@param (vector) b
```

```
@usage
(similars [:a :b :c] [:c :d :e])
```

```
@example
(similars [:a :b :c] [:c :d :e])
=>
[:c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn similars
  [a b]
  (letfn [(f [result x]
             (if (check/contains-item? b x)
                 (vec (conj result x))
                 (-> result)))]
         (reduce f [] a)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [similars]]))

(vector.api/similars ...)
(similars            ...)
```

</details>

---

### sort-items

```
@description
Returns the given vector but its items are ordered with the given comparator function.
```

```
@param (vector) n
@param (function)(opt) comparator-f
```

```
@usage
(sort-items ["a" "c" "b"] string/abc?)
```

```
@example
(sort-items ["a" "c" "b"] string/abc?)
=>
["a" "b" "c"]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn sort-items
  ([n]
   (-> n sort vec))

  ([n comparator-f]
   (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
          (vec (sort compare-f n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [sort-items]]))

(vector.api/sort-items ...)
(sort-items            ...)
```

</details>

---

### sort-items-by

```
@description
Returns the given vector but its items are ordered with the given comparator function
that compares not the items but their versions converted by the 'convert-f' function.
```

```
@param (vector) n
@param (function)(opt) comparator-f
@param (function) convert-f
```

```
@usage
(sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
```

```
@example
(sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
=>
[{:a 1} {:a 2} {:a 3}]
```

```
@example
(sort-items-by [[1 2] [2 2] [2 3]] > first)
=>
[[2 2] [2 3] [1 2]]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn sort-items-by
  ([n convert-f]
   (vec (sort-by convert-f n)))

  ([n comparator-f convert-f]
   (letfn [(compare-f [a b] (boolean (comparator-f a b)))]
          (vec (sort-by convert-f compare-f n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [sort-items-by]]))

(vector.api/sort-items-by ...)
(sort-items-by            ...)
```

</details>

---

### sort-items-by-dexes

```
@description
Returns the given vector but its items are ordered by the given index vector
that tells the function which items to keep in what order.
```

```
@param (vector) n
@param (integers in vector) dexes
```

```
@usage
(sort-items-by-dexes [:a :b :c] [2 0 1])
=>
[:c :a :b]
```

```
@usage
(sort-items-by-dexes [:a :b :c] [2 0])
=>
[:c :a]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn sort-items-by-dexes
  [n dexes]
  (when (and (vector? n)
             (vector? dexes))
        (letfn [(f [result dex]
                   (if-let [item (nth/nth-item n dex)]
                           (conj result item)
                           (->   result)))]
               (reduce f [] dexes))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [sort-items-by-dexes]]))

(vector.api/sort-items-by-dexes ...)
(sort-items-by-dexes            ...)
```

</details>

---

### sorted-dexes

```
@description
Takes two vectors and returns a new one that contains the positions of the second
vector's items in the first vector. If an item of the second vector is not represented
in the first vector, it's position won't be in the return vector.
```

```
@param (vector) a
@param (vector) b
```

```
@usage
(sorted-dexes [:a :b :c] [:c :a :b])
=>
[2 0 1]
```

```
@usage
(sorted-dexes [:a :b :c] [:c :a])
=>
[2 0]
```

```
@usage
(sorted-dexes [:a :b :c] [:c :a :b :d])
=>
[2 0 1]
```

```
@return (integers in vector)
```

<details>
<summary>Source code</summary>

```
(defn sorted-dexes
  [a b]
  (when (and (vector? a)
             (vector? b))
        (letfn [(f [dexes x]
                   (if-let [dex (dex/item-first-dex a x)]
                           (conj dexes dex)
                           (->   dexes)))]
               (reduce f [] b))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [sorted-dexes]]))

(vector.api/sorted-dexes ...)
(sorted-dexes            ...)
```

</details>

---

### to-map

```
@param (vector) n
```

```
@usage
(to-map [:a :b :c])
```

```
@example
(to-map [:a :b :c])
=>
{:0 :a :1 :b :2 :c}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-map
  [n]
  (letfn [(f [%1 %2 %3] (assoc %1 (keyword (str %2)) %3))]
         (reduce-kv f {} n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [to-map]]))

(vector.api/to-map ...)
(to-map            ...)
```

</details>

---

### toggle-item

```
@param (vector) n
@param (*) x
```

```
@usage
(toggle-item [:a :b] :a)
```

```
@example
(toggle-item [:a :b] :c)
=>
[:a :b :c]
```

```
@example
(toggle-item [:a :b :c] :c)
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn toggle-item
  [n x]
  (if (check/contains-item? n x)
      (remove/remove-item   n x)
      (conj-item            n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [toggle-item]]))

(vector.api/toggle-item ...)
(toggle-item            ...)
```

</details>

---

### trim

```
@param (vector) n
@param (integer) length
```

```
@usage
(trim [:a :b :c] 2)
```

```
@example
(trim [:a :b :c :d :e] 3)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn trim
  [n length]
  (first-items n length))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :refer [trim]]))

(vector.api/trim ...)
(trim            ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

