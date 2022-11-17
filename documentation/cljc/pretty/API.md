
# <strong>pretty.api</strong> namespace
<p>Documentation of the <strong>pretty/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > pretty.api</strong>



### mixed->string

```
@param (*) n
@param (map) options
```

```
@usage
(mixed->string {:a {:b "a/b"}})
```

```
@usage
[:pre (mixed->string {:a {:b "a/b"}})]
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn mixed->string
  ([n]
   (mixed->string n {}))

  ([n {:keys [abc?]}]
   (letfn [(map->string
             [n {:keys [depth wrap-items?]}]
             (map-wrap (reduce-kv-indexed (fn [o dex k v]
                                              (append-map-kv o (mixed->string k {:depth 0})
                                                               (mixed->string v {:depth       (inc depth)
                                                                                 :wrap-items? (mixed->wrap-items? v)})
                                                               {:depth       depth
                                                                :first-item? (= dex 0)
                                                                :wrap-items? wrap-items?}))
                                         (param nil)
                                         (param n))))

           (map->ordered-string
             [n {:keys [depth wrap-items?]}]
             (let [ordered-keys (vector/abc-items (keys n))]
                  (map-wrap (reduce-indexed (fn [o dex k]
                                                (let [v (get n k)]
                                                     (append-map-kv o (mixed->string k {:depth 0})
                                                                      (mixed->string v {:depth       (inc depth)
                                                                                        :wrap-items? (mixed->wrap-items? v)})
                                                                      {:depth       depth
                                                                       :first-item? (= 0 dex)
                                                                       :wrap-items? wrap-items?})))
                                           (param nil)
                                           (param ordered-keys)))))

           (vector->string
             [n {:keys [depth wrap-items?]}]
             (vector-wrap (reduce-indexed (fn [o dex x]
                                              (append-vector-v o (mixed->string x {:depth       (inc depth)
                                                                                   :wrap-items? (mixed->wrap-items? x)})
                                                                 {:depth       depth
                                                                  :first-item? (= dex 0)
                                                                  :wrap-items? wrap-items?}))
                                         (param nil)
                                         (param n))))

           (mixed->string
             [n {:keys [depth wrap-items?] :as options}]
             (cond (and (map? n) abc?) (map->ordered-string n options)
                   (map?     n)        (map->string         n options)
                   (vector?  n)        (vector->string      n options)
                   (fn?      n)        (fn>string           n)
                   (float?   n)        (float->string       n)
                   (integer? n)        (integer->string     n)
                   (nil?     n)        (nil->string         n)
                   (string?  n)        (string->string      n)
                   (var?     n)        (var->string         n)
                   :return             (str                 n)))]

         (remove-unnecessary-breaks (mixed->string n {:depth       (param 0)
                                                      :wrap-items? (mixed->wrap-items? n)})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [pretty.api :as pretty :refer [mixed->string]]))

(pretty/mixed->string ...)
(mixed->string        ...)
```

</details>
