
# <strong>html.api</strong> namespace
<p>Documentation of the <strong>html/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > html.api</strong>



### to-hiccup

```
@param (string) n
```

```
@usage
 (to-hiccup "<div")
```

```
@example=>
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn to-hiccup
  [n]
  #?(:cljs (if (and (-> n string?)
                    (-> n  empty? not))
               (try (->> (hc/parse-fragment n)
                         (map #(-> % hc/as-hiccup hiccup->sablono))
                         (filter identity)
                         first)
                    (catch :default e (.log js/console e))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [html.api :as html :refer [to-hiccup]]))

(html/to-hiccup ...)
(to-hiccup      ...)
```

</details>
