
# <strong>vector.api</strong> namespace
<p>Documentation of the <strong>vector/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > vector.api</strong>



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
(ns my-namespace (:require [vector.api :as vector :refer [->>items]]))

(vector/->>items ...)
(->>items        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [->items]]))

(vector/->items ...)
(->items        ...)
```

</details>

---

### abc-items

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
  (letfn [(sort-item-f [o x] (cond (string?  x) (update o :string-items     conj x)
                                   (keyword? x) (update o :keyword-items    conj x)
                                   :return      (update o :unsortable-items conj x)))
          (sort-items-f [n] (reduce sort-item-f {} n))]
         (let [{:keys [string-items keyword-items unsortable-items]} (sort-items-f n)]
              (vec (concat unsortable-items (sort string-items)
                                            (sort keyword-items))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [abc-items]]))

(vector/abc-items ...)
(abc-items        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [all-items-match?]]))

(vector/all-items-match? ...)
(all-items-match?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [any-item-match?]]))

(vector/any-item-match? ...)
(any-item-match?        ...)
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
  (letfn [(f [o x]
             (if (= x a)
                 (conj-item o b)
                 (conj-item o x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [change-item]]))

(vector/change-item ...)
(change-item        ...)
```

</details>

---

### compared-items-sorted?

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
              (case max-count 0 (return true)
                                (f 0)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [compared-items-sorted?]]))

(vector/compared-items-sorted? ...)
(compared-items-sorted?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [concat-items]]))

(vector/concat-items ...)
(concat-items        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [concat-items-once]]))

(vector/concat-items-once ...)
(concat-items-once        ...)
```

</details>

---

### conj-item

```
@param (vector) n
@param (*) x
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
(ns my-namespace (:require [vector.api :as vector :refer [conj-item]]))

(vector/conj-item ...)
(conj-item        ...)
```

</details>

---

### conj-item-once

```
@param (vector) n
@param (*) x
```

```
@usage
(conj-item-once [:a :b] :b)
```

```
@example
(conj-item-once [:a :b] :b)
=>
[:a :b]
```

```
@example
(conj-item-once [:a :b] :c)
=>
[:a :b :c]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn conj-item-once
  [n x]
  (if (check/contains-item? n x)
      (return               n)
      (conj-item            n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [conj-item-once]]))

(vector/conj-item-once ...)
(conj-item-once        ...)
```

</details>

---

### conj-some

```
@param (vector) n
@param (*) x
```

```
@usage
(conj-some [:a :b] :c)
```

```
@example
(conj-some [:a :b] :c)
=>
[:a :b :c]
```

```
@example
(conj-some [:a :b] nil)
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn conj-some
  [n x]
  (if x (conj-item n x)
        (return    n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [conj-some]]))

(vector/conj-some ...)
(conj-some        ...)
```

</details>

---

### cons-item

```
@param (vector) n
@param (*) x
```

```
@usage
(cons-item [:a :b] :c)
```

```
@example
(cons-item [:a :b] :c)
=>
[:c :a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn cons-item
  [n x]
  (vec (cons x n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [cons-item]]))

(vector/cons-item ...)
(cons-item        ...)
```

</details>

---

### cons-item-once

```
@param (vector) n
@param (*) x
```

```
@usage
(cons-item-once [:a :b] :b)
```

```
@example
(cons-item-once [:a :b] :b)
=>
[:a :b]
```

```
@example
(cons-item-once [:a :b] :c)
=>
[:c :a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn cons-item-once
  [n x]
  (if (check/contains-item? n x)
      (return               n)
      (cons-item            n x)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [cons-item-once]]))

(vector/cons-item-once ...)
(cons-item-once        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [contains-item?]]))

(vector/contains-item? ...)
(contains-item?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [contains-similars?]]))

(vector/contains-similars? ...)
(contains-similars?        ...)
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
  (cond (= (count n) x) (return      n)
        (> (count n) x) (first-items n x)
        (< (count n) x) (vec (concat n (repeat nil (- x (count n)))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [count!]]))

(vector/count! ...)
(count!        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [count?]]))

(vector/count? ...)
(count?        ...)
```

</details>

---

### dec-dex

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
  (if (dex-first? dex)
      (return     dex)
      (dec        dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [dec-dex]]))

(vector/dec-dex ...)
(dec-dex        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [dex-first?]]))

(vector/dex-first? ...)
(dex-first?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [dex-in-bounds?]]))

(vector/dex-in-bounds? ...)
(dex-in-bounds?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [dex-last?]]))

(vector/dex-last? ...)
(dex-last?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [dex-out-of-bounds?]]))

(vector/dex-out-of-bounds? ...)
(dex-out-of-bounds?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [dex-range]]))

(vector/dex-range ...)
(dex-range        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [difference]]))

(vector/difference ...)
(difference        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [duplicate-nth-item]]))

(vector/duplicate-nth-item ...)
(duplicate-nth-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [duplicate-nth-items]]))

(vector/duplicate-nth-items ...)
(duplicate-nth-items        ...)
```

</details>

---

### filter-items-by

```
@param (vector) n
@param (function) test-f
```

```
@usage
(filter-items-by [:a :b "c"] keyword?)
```

```
@example
(filter-items-by [:a :b "c"] keyword?)
=>
[:a :b]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn filter-items-by
  [n test-f]
  (vec (filter test-f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [filter-items-by]]))

(vector/filter-items-by ...)
(filter-items-by        ...)
```

</details>

---

### filtered-count

```
@param (vector) n
@param (function) test-f
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
  [n test-f]
  (count (filter test-f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [filtered-count]]))

(vector/filtered-count ...)
(filtered-count        ...)
```

</details>

---

### filtered-count?

```
@param (vector) n
@param (function) test-f
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
  [n test-f x]
  (= x (filtered-count n test-f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [filtered-count?]]))

(vector/filtered-count? ...)
(filtered-count?        ...)
```

</details>

---

### first-filtered

```
@param (vector) n
@param (function) test-f
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
(first-filtered ["a" :b "c" :d "e"] #(string? %1))
=>
"a"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn first-filtered
  [n test-f]
  (letfn [(f [%] (if (test-f %) %))]
         (some f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [first-filtered]]))

(vector/first-filtered ...)
(first-filtered        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [first-item]]))

(vector/first-item ...)
(first-item        ...)
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
  (if (and (-> length integer?)
           (>= length (count n)))
      (return n)
      (subvec n 0 length)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [first-items]]))

(vector/first-items ...)
(first-items        ...)
```

</details>

---

### get-first-match-item

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-first-match-item [:a :b :c "d"] string?)
```

```
@example
(get-first-match-item [:a :b :c] string?)
=>
nil
```

```
@example
(get-first-match-item [:a "b" :c] string?)
=>
"b"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match-item
  [n test-f]
  (some test-f n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [get-first-match-item]]))

(vector/get-first-match-item ...)
(get-first-match-item        ...)
```

</details>

---

### get-first-match-item-dex

```
@param (vector) n
@param (function) test-f
```

```
@usage
(get-first-match-item-dex [:a :b :c "d"] string?)
```

```
@example
(get-first-match-item-dex [:a :b :c] string?)
=>
nil
```

```
@example
(get-first-match-item-dex [:a "b" :c] string?)
=>
1
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-first-match-item-dex
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f) %2) %1)]
         (some-indexed f n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [get-first-match-item-dex]]))

(vector/get-first-match-item-dex ...)
(get-first-match-item-dex        ...)
```

</details>

---

### inc-dex

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
  (if (dex-last? n dex)
      (return      dex)
      (inc         dex)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [inc-dex]]))

(vector/inc-dex ...)
(inc-dex        ...)
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
        (nil? n) (return [x])
        :return n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [inject-item]]))

(vector/inject-item ...)
(inject-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [item-dex?]]))

(vector/item-dex? ...)
(item-dex?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [item-first-dex]]))

(vector/item-first-dex ...)
(item-first-dex        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [item-first?]]))

(vector/item-first? ...)
(item-first?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [item-last-dex]]))

(vector/item-last-dex ...)
(item-last-dex        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [item-last?]]))

(vector/item-last? ...)
(item-last?        ...)
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
          (return [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [items-after-first-occurence]]))

(vector/items-after-first-occurence ...)
(items-after-first-occurence        ...)
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
          (return [])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [items-before-first-occurence]]))

(vector/items-before-first-occurence ...)
(items-before-first-occurence        ...)
```

</details>

---

### items-sorted?

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
  (= n (sort-items n comparator-f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [items-sorted?]]))

(vector/items-sorted? ...)
(items-sorted?        ...)
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
  (letfn [(f [o x]
             (if (check/contains-item? xyz x)
                 (conj   o x)
                 (return o)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [keep-items]]))

(vector/keep-items ...)
(keep-items        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [last-dex]]))

(vector/last-dex ...)
(last-dex        ...)
```

</details>

---

### last-filtered

```
@param (vector) n
@param (function) test-f
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
(last-filtered ["a" :b "c" :d "e"] #(string? %1))
=>
"e"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn last-filtered
  [n test-f]
  (letfn [(f [%1 %2] (if (test-f %2) %2 %1))]
         (reduce f nil n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [last-filtered]]))

(vector/last-filtered ...)
(last-filtered        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [last-item]]))

(vector/last-item ...)
(last-item        ...)
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
  (if (and (-> length integer?)
           (>= length (count n)))
      (return n)
      (subvec n (-> n count (- length)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [last-items]]))

(vector/last-items ...)
(last-items        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [longer?]]))

(vector/longer? ...)
(longer?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [match-dex]]))

(vector/match-dex ...)
(match-dex        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [max?]]))

(vector/max? ...)
(max?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [min?]]))

(vector/min? ...)
(min?        ...)
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
          (move-item n item-first-dex dex)
          (return    n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [move-first-occurence]]))

(vector/move-first-occurence ...)
(move-first-occurence        ...)
```

</details>

---

### move-item

```
@param (vector) n
@param (integer) from
@param (integer) to
```

```
@usage
(move-item [:a :b :c] 0 2)
```

```
@example
(move-item [:a :b :c :d :e :f :g :h] 2 2)
=>
[:a :b :c :d :e :f :g :h]
```

```
@example
(move-item [:a :b :c :d :e :f :g :h] 2 5)
=>
[:a :b :d :e :c :f :g :h]
```

```
@example
(move-item [:a :b :c :d :e :f :g :h] 5 2)
=>
[:a :b :f :c :d :e :g :h]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn move-item
  [n from to]
  (if (dex/dex-in-bounds? n from)
      (let [to (math/between! to 0 (count n))]
           (cond                 (= from to) (return n)
                 (< from to) (vec (concat (range/ranged-items n 0 from)
                                          (range/ranged-items n (inc from) (inc to))
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n (inc to))))
                 (> from to) (vec (concat (range/ranged-items n 0 to)
                                          (range/ranged-items n from (inc from))
                                          (range/ranged-items n to from)
                                          (range/ranged-items n (inc from))))))
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [move-item]]))

(vector/move-item ...)
(move-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [move-item-to-first]]))

(vector/move-item-to-first ...)
(move-item-to-first        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [move-item-to-last]]))

(vector/move-item-to-last ...)
(move-item-to-last        ...)
```

</details>

---

### next-dex

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
  (if (type/nonempty?    n)
      (sequence/next-dex dex 0 (-> n count dec))
      (return            0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [next-dex]]))

(vector/next-dex ...)
(next-dex        ...)
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
  [n x])
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [next-item]]))

(vector/next-item ...)
(next-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [nonempty?]]))

(vector/nonempty? ...)
(nonempty?        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [not-contains-item?]]))

(vector/not-contains-item? ...)
(not-contains-item?        ...)
```

</details>

---

### nth-filtered

```
@param (vector) n
@param (function) test-f
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
(nth-filtered ["a" :b "c" :d "e"] #(string? %1) 2)
=>
"c"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn nth-filtered
  [n test-f dex]
  (letfn [(f [x match-count f-dex]
             (if (-> x test-f not)
                 (if (dex/dex-last? n f-dex)
                     (return nil)
                     (f (get n (inc f-dex)) match-count (inc f-dex)))
                 (if (< match-count dex)
                     (if (dex/dex-last? n f-dex)
                         (return nil)
                         (f (get n (inc f-dex)) (inc match-count) (inc f-dex)))
                     (return x))))]
         (f (first n) 0 0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [nth-filtered]]))

(vector/nth-filtered ...)
(nth-filtered        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [nth-item]]))

(vector/nth-item ...)
(nth-item        ...)
```

</details>

---

### prev-dex

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
  (if (vector?           n)
      (sequence/prev-dex dex 0 (-> n count dec))
      (return            0)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [prev-dex]]))

(vector/prev-dex ...)
(prev-dex        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [prev-item]]))

(vector/prev-item ...)
(prev-item        ...)
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
            (return [])))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [ranged-items]]))

(vector/ranged-items ...)
(ranged-items        ...)
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
  (letfn [(f [o x]
             (if (check/contains-item? o x)
                 (return               o)
                 (conj                 o x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [remove-duplicates]]))

(vector/remove-duplicates ...)
(remove-duplicates        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-first-item]]))

(vector/remove-first-item ...)
(remove-first-item        ...)
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
          (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [remove-first-occurence]]))

(vector/remove-first-occurence ...)
(remove-first-occurence        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-item]]))

(vector/remove-item ...)
(remove-item        ...)
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
                        (return n)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-item-once]]))

(vector/remove-item-once ...)
(remove-item-once        ...)
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
  (letfn [(f [o x]
             (if (check/contains-item? xyz x)
                 (return               o)
                 (conj                 o x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [remove-items]]))

(vector/remove-items ...)
(remove-items        ...)
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
  (letfn [(f [o x]
             (if (test-f x)
                 (return o)
                 (conj   o x)))]
         (reduce f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [remove-items-by]]))

(vector/remove-items-by ...)
(remove-items-by        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-items-kv]]))

(vector/remove-items-kv ...)
(remove-items-kv        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-last-item]]))

(vector/remove-last-item ...)
(remove-last-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [remove-nth-item]]))

(vector/remove-nth-item ...)
(remove-nth-item        ...)
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
  (letfn [(remove-nth-items-f [o dex x]
                              (if (check/contains-item? dexes dex)
                                  (return o)
                                  (conj   o x)))]
         (reduce-kv remove-nth-items-f [] n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [remove-nth-items]]))

(vector/remove-nth-items ...)
(remove-nth-items        ...)
```

</details>

---

### repeat-item

```
@param (*) n
@param (integer) x
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
  [n x]
  (vec (repeat x n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [repeat-item]]))

(vector/repeat-item ...)
(repeat-item        ...)
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
      (return n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [replace-nth-item]]))

(vector/replace-nth-item ...)
(replace-nth-item        ...)
```

</details>

---

### reverse-items

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
(ns my-namespace (:require [vector.api :as vector :refer [reverse-items]]))

(vector/reverse-items ...)
(reverse-items        ...)
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
  (letfn [(f [o x]
             (if (check/contains-item? b x)
                 (vec (conj            o x))
                 (return               o)))]
         (reduce f [] a)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [similars]]))

(vector/similars ...)
(similars        ...)
```

</details>

---

### sort-items

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
   (vec (sort comparator-f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [sort-items]]))

(vector/sort-items ...)
(sort-items        ...)
```

</details>

---

### sort-items-by

```
@param (vector) n
@param (function)(opt) comparator-f
@param (function) value-f
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
  ([n value-f]
   (vec (sort-by value-f n)))

  ([n comparator-f value-f]
   (vec (sort-by value-f comparator-f n))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [vector.api :as vector :refer [sort-items-by]]))

(vector/sort-items-by ...)
(sort-items-by        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [to-map]]))

(vector/to-map ...)
(to-map        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [toggle-item]]))

(vector/toggle-item ...)
(toggle-item        ...)
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
(ns my-namespace (:require [vector.api :as vector :refer [trim]]))

(vector/trim ...)
(trim        ...)
```

</details>
