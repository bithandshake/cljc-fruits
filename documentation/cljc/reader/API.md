
# <strong>reader.api</strong> namespace
<p>Documentation of the <strong>reader/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > reader.api</strong>



### mixed->string

```
@param (*) n
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn mixed->string
  [n]
  (str n))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :as reader :refer [mixed->string]]))

(reader/mixed->string ...)
(mixed->string        ...)
```

</details>

---

### read-str

```
@param (string) n
```

```
@example
(read-str "{:a \"b\"}")
=>
{:a "b"}
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn read-str
  [n]
  #?(:cljs (try (reader/read-string n) (catch :default  e (println e)))
     :clj  (try (edn/read-string    n) (catch Exception e (println e)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :as reader :refer [read-str]]))

(reader/read-str ...)
(read-str        ...)
```

</details>

---

### string->map

```
@param (string) n
```

```
@example
(string->map "foo")
=>
{:0 "foo"}
```

```
@example
(string->map nil)
=>
{}
```

```
@example
(string->map "{:foo :bar}")
=>
{:foo :bar}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn string->map
  [n]
  (if-let [x (string->mixed n)]
          (cond (map? x) x
                (nil? n) {}
                :return  {:0 (str n)})
          (return {})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :as reader :refer [string->map]]))

(reader/string->map ...)
(string->map        ...)
```

</details>

---

### string->mixed

```
@param (n) string
```

```
@example
(string->mixed "")
=>
nil
```

```
@example
(string->mixed ":foo")
=>
:foo
```

```
@example
(string->mixed "{:foo :bar}")
=>
{:foo :bar}
```

```
@example
(string->mixed "[:foo]")
=>
[:foo]
```

```
@return (nil, keyword, map, number, string or vector)
```

<details>
<summary>Source code</summary>

```
(defn string->mixed
  [n]
  (if (string/nonblank? n)
      (let [x (read-str n)]
           (if (some #(% x) [keyword? map? vector? number?])
               (return x)
               (return n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :as reader :refer [string->mixed]]))

(reader/string->mixed ...)
(string->mixed        ...)
```

</details>
