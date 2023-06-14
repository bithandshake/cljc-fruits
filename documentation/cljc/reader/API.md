
# reader.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > reader.api

### Index

- [json->map](#json-map)

- [mixed->string](#mixed-string)

- [read-str](#read-str)

- [string->map](#string-map)

- [string->mixed](#string-mixed)

### json->map

```
@param (string) n
```

```
@usage
(json->map "{\"name\":\"value\"}")
```

```
@example
(json->map "{\"name\":\"value\"}")
=>
{"name" "value"}
```

```
@example
(json->map "{\"name\":[\"value\"]}")
=>
{"name" ["value"]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn json->map
  [n]
  (letfn [(remove-delimiter-colons-f [%] (string/replace-part % #"(?<=\"[a-zA-Z0-9\-\_]+\")\:" " "))]
         (-> n remove-delimiter-colons-f string->mixed)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :refer [json->map]]))

(reader.api/json->map ...)
(json->map            ...)
```

</details>

---

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
(ns my-namespace (:require [reader.api :refer [mixed->string]]))

(reader.api/mixed->string ...)
(mixed->string            ...)
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
(ns my-namespace (:require [reader.api :refer [read-str]]))

(reader.api/read-str ...)
(read-str            ...)
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
(ns my-namespace (:require [reader.api :refer [string->map]]))

(reader.api/string->map ...)
(string->map            ...)
```

</details>

---

### string->mixed

```
@param (string) n
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
(ns my-namespace (:require [reader.api :refer [string->mixed]]))

(reader.api/string->mixed ...)
(string->mixed            ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

