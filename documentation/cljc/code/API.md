
### code.api

Functional documentation of the code.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > code.api

### Index

- [run-code!](#run-code)

---

### run-code!

```
@param (string) source-code
@param (vectors in vector)(opt) env-vars
[[(string) var-name
  (*) var-value]
 [...]]
```

```
@usage
(run-code! "(println (my-function my-var))"
           [["my-function" "my-namespace/my-function"]
            ["my-var"      :my-value]])
```

<details>
<summary>Source code</summary>

```
(defn run-code!
  ([source-code]
   (run-code! source-code []))

  ([source-code env-vars]
   #?(:clj (if (string/nonblank? source-code)
               (letfn [(environment-f [environment [var-name var-value]]
                                      (str environment "(def ^{:private true} "var-name" "var-value")\n"))]
                      (let [environment (reduce environment-f "" env-vars)
                            source-code (str environment source-code)]
                           (try (load-string source-code)
                                (catch Exception e (println e)))))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [code.api :refer [run-code!]]))

(code.api/run-code! ...)
(run-code!          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

