
### reader.api

Functional documentation of the reader.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > reader.api

### Index

- [prepare-edn](#prepare-edn)

- [prepare-json](#prepare-json)

- [read-edn](#read-edn)

- [read-json](#read-json)

---

### prepare-edn

```
@param (string) n
```

```
@usage
(prepare-edn "{:my-object #object[...]}")
```

```
@example
(prepare-edn "{:my-object #object[...]}")
=>
"{:my-object :object-removed-by-reader}"
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn prepare-edn
  [n]
  (letfn [
          (remove-object-f [%] (if-let [open-pos (string/first-dex-of % "#object[")]
                                       (if-let [close-pos (syntax/close-bracket-position % {:offset open-pos})]
                                               (str (string/part % 0 open-pos)
                                                    ":object-removed-by-reader"
                                                    (string/part % (inc close-pos))))))

          (remove-objects-f [%] (if-let [% (remove-object-f %)]
                                        (-> % remove-objects-f)
                                        (-> %)))]

         (-> n remove-objects-f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :refer [prepare-edn]]))

(reader.api/prepare-edn ...)
(prepare-edn            ...)
```

</details>

---

### prepare-json

```
@param (string) n
```

```
@usage
(prepare-json "{\"name\":\"value\"}")
```

```
@example
(prepare-json "{\"name\":\"value\"}")
=>
"{\"name\" \"value\"}"
```

```
@example
(prepare-json "{\"name\":[\"value\"]}")
=>
"{\"name\" [\"value\"]}"
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn prepare-json
  [n]
  (letfn [(remove-delimiter-colons-f [%] (string/replace-part % #"(?<=\"[a-zA-Z0-9\-\_]+\")\:" " "))]
         (-> n remove-delimiter-colons-f)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :refer [prepare-json]]))

(reader.api/prepare-json ...)
(prepare-json            ...)
```

</details>

---

### read-edn

```
@description
Reads one object from the given 'n' string.
http://edn-format.org
```

```
@param (string) n
```

```
@example
(read-edn "")
=>
nil
```

```
@example
(read-edn ":foo")
=>
:foo
```

```
@example
(read-edn "{:foo :bar}")
=>
{:foo :bar}
```

```
@example
(read-edn "[:foo]")
=>
[:foo]
```

```
@return (nil, keyword, map, number, seqable (e.g., string, vector, etc.))
```

<details>
<summary>Source code</summary>

```
(defn read-edn
  [n]
  (letfn [(read-edn-f [%] #?(:cljs (try (-> % str reader/read-string) (catch :default  e (println e)))
                             :clj  (try (-> % str edn/read-string)    (catch Exception e (println e)))))]
         (let [output (-> n prepare/prepare-edn read-edn-f)]
              (if (some #(% output) [boolean? keyword? map? number? seqable?])                  (-> output)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :refer [read-edn]]))

(reader.api/read-edn ...)
(read-edn            ...)
```

</details>

---

### read-json

```
@param (string) n
```

```
@usage
(read-json "{\"name\":\"value\"}")
```

```
@example
(read-json "{\"name\":\"value\"}")
=>
{"name" "value"}
```

```
@example
(read-json "{\"name\":[\"value\"]}")
=>
{"name" ["value"]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn read-json
  [n]
  (-> n prepare/prepare-json read-edn))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [reader.api :refer [read-json]]))

(reader.api/read-json ...)
(read-json            ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

