
# uri.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > uri.api

### Index

- [to-absolute-url](#to-absolute-url)

- [to-domain](#to-domain)

- [to-encoded-url](#to-encoded-url)

- [to-hostname](#to-hostname)

- [to-lowercase](#to-lowercase)

- [to-nonschemed](#to-nonschemed)

- [to-parent-url](#to-parent-url)

- [to-port](#to-port)

- [to-relative-url](#to-relative-url)

- [to-scheme](#to-scheme)

- [to-subdomain](#to-subdomain)

- [to-tld](#to-tld)

- [to-url-fragment](#to-url-fragment)

- [to-url-path](#to-url-path)

- [to-url-path-params](#to-url-path-params)

- [to-url-query-params](#to-url-query-params)

- [to-url-query-string](#to-url-query-string)

- [to-url-tail](#to-url-tail)

- [url-path-template-matches?](#url-path-template-matches)

- [url-query-params->url-query-string](#url-query-params-url-query-string)

- [url-query-string->url-query-params](#url-query-string-url-query-params)

- [use-url-query-string](#use-url-query-string)

- [valid-url](#valid-url)

- [valid-url-path](#valid-url-path)

### to-absolute-url

```
@param (string) n
@param (string) domain
```

```
@usage
(to-absolute-url "/my-path" "my-domain.com")
```

```
@example
(to-absolute-url "/my-path" "my-domain.com")
=>
"https://my-domain.com/my-path"
```

```
@example
(to-absolute-url "your-domain.com/my-path" "my-domain.com")
=>
"https://your-domain.com/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-absolute-url
  [n domain]
  (if-let [absolute-url? (to-domain n)]
          (-> n (string/not-ends-with! "/")
                (to-lowercase)
                (string/starts-with!   "https://"))
          (-> n (string/starts-with!   "/")
                (string/prepend        domain)
                (to-lowercase)
                (string/not-ends-with! "/")
                (string/starts-with!   "https://"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-absolute-url]]))

(uri.api/to-absolute-url ...)
(to-absolute-url         ...)
```

</details>

---

### to-domain

```
@param (string) n
```

```
@usage
(to-domain "https://my-domain.com")
```

```
@example
(to-domain "https://my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-domain "https://www.subs.my-domain.com:80/my-path")
=>
"sub.my-domain.com"
```

```
@example
(to-domain "mailto:johndoe@my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-domain "ftp://user@my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-domain "my-domain.com:80")
=>
"my-domain.com"
```

```
@example
(to-domain "192.0.2.16:80")
=>
nil
```

```
@example
(to-domain "/my-path?my-query#my-fragment")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-domain
  [n]
  (-> (to-nonschemed n)
      (string/to-lowercase)
      (string/after-first-occurence  "www." {:return? true})
      (string/before-first-occurence "/"    {:return? true})
      (string/before-first-occurence "?"    {:return? true})
      (string/before-first-occurence "#"    {:return? true})
      (string/after-first-occurence  "@"    {:return? true})
      (re-match config/DOMAIN-PATTERN)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-domain]]))

(uri.api/to-domain ...)
(to-domain         ...)
```

</details>

---

### to-encoded-url

```
@param (string) n
@param (map)(opt) options
{:strict? (boolean)(opt)
  Default: false}
```

```
@usage
(to-encoded-url "my-domain.com/my path")
```

```
@example
(to-encoded-url "my-domain.com/my path?my param")
=>
"my-domain.com/my%20path?my%20param"
```

```
@example
(to-encoded-url "my-domain.com/my path?my param" {:strict? true})
=>
"my-domain.com%2Fmy%20path%3Fmy%20param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-encoded-url
  ([n]
   (to-encoded-url n {}))

  ([n {:keys [strict?]}]
   #?(:cljs (if strict? (.encodeURIComponent js/window n)
                        (.encodeURI          js/window n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-encoded-url]]))

(uri.api/to-encoded-url ...)
(to-encoded-url         ...)
```

</details>

---

### to-hostname

```
@param (string) n
```

```
@usage
(to-hostname "https://my-domain.com")
```

```
@example
(to-hostname "https://my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-hostname "https://www.sub.my-domain.com:80/my-path")
=>
"www.sub.my-domain.com"
```

```
@example
(to-hostname "mailto:johndoe@my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-hostname "ftp://user@my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-hostname "http://192.0.2.16:80/my-path")
=>
"192.0.2.16"
```

```
@example
(to-hostname "/my-path?my-query#my-fragment")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-hostname
  [n]
  (-> (to-nonschemed n)
      (string/after-first-occurence  "@"  {:return? true})
      (string/before-first-occurence ":"  {:return? true})
      (string/before-first-occurence "/"  {:return? true})
      (string/before-first-occurence "?"  {:return? true})
      (string/before-first-occurence "#"  {:return? true})
      (string/to-lowercase)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-hostname]]))

(uri.api/to-hostname ...)
(to-hostname         ...)
```

</details>

---

### to-lowercase

```
@param (string) n
```

```
@usage
(to-lowercase "Https://My-domain.com")
```

```
@example
(to-lowercase "Https://My-domain.com")
=>
"https://my-domain.com"
```

```
@example
(to-lowercase "Https://My-domain.com/My-path?My-query#My-fragment")
=>
"https://my-domain.com/My-path?My-query#My-fragment"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-lowercase
  [n]
  (cond
        (and (string/contains-part? n "//")
             (string/nth-dex-of     n "/" 3))
        (let [dex (string/nth-dex-of n "/" 3)]
             (str (-> n (string/part 0 dex)
                        (string/to-lowercase))
                  (-> n (string/part dex))))

        (string/contains-part? n "/")
        (str (-> n (string/before-first-occurence "/" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "/" {:return? false})))

        (string/contains-part? n "?")
        (str (-> n (string/before-first-occurence "?" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "?" {:return? false})))

        (string/contains-part? n "#")
        (str (-> n (string/before-first-occurence "#" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "#" {:return? false})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-lowercase]]))

(uri.api/to-lowercase ...)
(to-lowercase         ...)
```

</details>

---

### to-nonschemed

```
@param (string) n
```

```
@usage
(to-nonschemed "https://my-domain.com")
```

```
@example
(to-nonschemed "https://my-domain.com")
=>
"my-domain.com"
```

```
@example
(to-nonschemed "mailto:johndoe@my-domain.com")
=>
"johndoe@my-domain.com"
```

```
@example
(to-nonschemed "ftp://user@my-domain.com")
=>
"user@my-domain.com"
```

```
@example
(to-nonschemed "my-domain.com:80")
=>
"my-domain.com:80"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-nonschemed
  [n]
  (if-let [scheme (to-scheme n)]
          (-> n (string/after-first-occurence ":"  {:return? false})
                (string/after-first-occurence "//" {:return? true}))
          (-> n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-nonschemed]]))

(uri.api/to-nonschemed ...)
(to-nonschemed         ...)
```

</details>

---

### to-parent-url

```
@param (string) n
```

```
@usage
(to-parent-url "https://my-domain.com/my-path")
```

```
@example
(to-parent-url "https://my-domain.com/my-path")
=>
"https://my-domain.com"
```

```
@example
(to-parent-url "https://my-domain.com")
=>
"https://my-domain.com"
```

```
@example
(to-parent-url "/my-path/your-path")
=>
"/my-path"
```

```
@example
(to-parent-url "/my-path")
=>
"/"
```

```
@example
(to-parent-url "/")
=>
"/"
```

```
@example
(to-parent-url "my-path")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-parent-url
  [n]
  (if-let [domain (to-domain n)]
          (-> n (string/not-ends-with!        "/")
                (string/before-last-occurence "/" {:return? true}))
          (-> n (string/not-ends-with!        "/")
                (string/before-last-occurence "/" {:return? false})
                (string/starts-with!          "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-parent-url]]))

(uri.api/to-parent-url ...)
(to-parent-url         ...)
```

</details>

---

### to-port

```
@param (string) n
```

```
@usage
(to-port "https://my-domain.com:80")
```

```
@example
(to-port "https://my-domain.com:80")
=>
"80"
```

```
@example
(to-port "https://my-domain.com")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-port
  [n]
  (-> (to-nonschemed n)
      (string/after-first-occurence  ":" {:return? false})
      (string/before-first-occurence "/" {:return? true})
      (string/before-first-occurence "?" {:return? true})
      (string/before-first-occurence "#" {:return? true})
      (re-match config/PORT-PATTERN)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-port]]))

(uri.api/to-port ...)
(to-port         ...)
```

</details>

---

### to-relative-url

```
@param (string) n
```

```
@usage
(to-relative-url "my-domain.com/my-path")
```

```
@example
(to-relative-url "my-domain.com/my-path")
=>
"/my-path?my-param#my-fragment"
```

```
@example
(to-relative-url "my-domain.com/my-path?my-query#my-fragment")
=>
"/my-path?my-query#my-fragment"
```

```
@example
(to-relative-url "my-domain.com")
=>
"/"
```

```
@example
(to-relative-url "/my-path")
=>
"/my-path"
```

```
@example
(to-relative-url "my-path")
=>
"/my-path"
```

```
@example
(to-relative-url "/")
=>
"/"
```

```
@example
(to-relative-url "")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-relative-url
  [n]
  (if-let [domain (to-domain n)]
          (-> n (to-lowercase)
                (string/after-first-occurence domain {:return? false})
                (string/not-ends-with! "/")
                (string/starts-with!   "/"))
          (-> n (to-lowercase)
                (string/not-ends-with! "/")
                (string/starts-with!   "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-relative-url]]))

(uri.api/to-relative-url ...)
(to-relative-url         ...)
```

</details>

---

### to-scheme

```
@param (string) n
```

```
@usage
(to-scheme "https://my-domain.com")
```

```
@example
(to-scheme "https://my-domain.com")
=>
"https"
```

```
@example
(to-scheme "mailto:johndoe@my-domain.com")
=>
"mailto"
```

```
@example
(to-scheme "ftp://user@my-domain.com")
=>
"ftp"
```

```
@example
(to-scheme "my-domain.com:80")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-scheme
  [n]
  (-> (string/before-first-occurence n ":" {:return? false})
      (re-match config/STRICT-SCHEME-PATTERN)
      (string/to-lowercase)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-scheme]]))

(uri.api/to-scheme ...)
(to-scheme         ...)
```

</details>

---

### to-subdomain

```
@param (string) n
```

```
@usage
(to-subdomain "https://sub.my-domain.com")
```

```
@example
(to-subdomain "https://sub.my-domain.com")
=>
"sub"
```

```
@example
(to-subdomain "https://my-domain.com")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-subdomain
  [n]
  (if-let [domain (to-domain n)]
          (if (-> domain (string/min-occurence?         "." 2))
              (-> domain (string/before-first-occurence ".")
                         (string/to-lowercase)
                         (string/use-nil)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-subdomain]]))

(uri.api/to-subdomain ...)
(to-subdomain         ...)
```

</details>

---

### to-tld

```
@param (string) n
```

```
@usage
(to-tld "https://my-domain.com")
```

```
@example
(to-tld "https://my-domain.com")
=>
"com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-tld
  [n]
  (if-let [domain (to-domain n)]
          (string/after-last-occurence domain "." {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-tld]]))

(uri.api/to-tld ...)
(to-tld         ...)
```

</details>

---

### to-url-fragment

```
@param (string) n
```

```
@usage
(to-url-fragment "/my-path#my-fragment")
```

```
@example
(to-url-fragment "/my-path#my-fragment")
=>
"my-fragment"
```

```
@example
(to-url-fragment "https://my-domain.com/my-path?my-query#my-fragment")
=>
"my-fragment"
```

```
@example
(to-url-fragment "/my-path#my-fragment")
=>
"my-fragment"
```

```
@example
(to-url-fragment "https://my-domain.com/my-path?my-query")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-url-fragment
  [n]
  (string/after-first-occurence n "#" {:return? false}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-fragment]]))

(uri.api/to-url-fragment ...)
(to-url-fragment         ...)
```

</details>

---

### to-url-path

```
@param (string) n
```

```
@usage
(to-url-path "https://my-domain.com/my-path")
```

```
@example
(to-url-path "https://my-domain.com/my-path")
=>
"/my-path"
```

```
@example
(to-url-path "https://my-domain.com")
=>
"/"
```

```
@example
(to-url-path "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
"/my-path"
```

```
@example
(to-url-path "https://my-domain.com/?my-param=my-value&your-param")
=>
"/"
```

```
@example
(to-url-path "https://my-domain.com?my-param=my-value&your-param")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-url-path
  [n]
  (-> n (to-relative-url)
        (string/before-first-occurence "?" {:return? true})
        (string/before-first-occurence "#" {:return? true})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-path]]))

(uri.api/to-url-path ...)
(to-url-path         ...)
```

</details>

---

### to-url-path-params

```
@param (string) n
@param (string) url-path-template
```

```
@usage
(to-url-path-params "/my-path" "/:a")
```

```
@example
(to-url-path-params "/my-path" "/:a")
=>
{:a "my-path"}
```

```
@example
(to-url-path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
=>
{:a "my-path" :b "your-path"}
```

```
@example
(to-url-path-params "https://my-domain.com/my-path/your-path" "/:a/b")
=>
{:a "my-path"}
```

```
@example
(to-url-path-params "/my-path/your-path" "/a/b")
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-url-path-params
  [n url-path-template]
  (letfn [(to-url-path-parts [n] (-> n (to-url-path)
                                       (string/not-starts-with!  "/")
                                       (string/not-ends-with!    "/")
                                       (string/split            #"/")))]
         (let [url-path                (to-url-path       n)
               url-path-parts          (to-url-path-parts url-path)
               url-path-template-parts (to-url-path-parts url-path-template)]
              (letfn [(f [result dex x] (let [x (reader/string->mixed x)]
                                             (if (keyword? x)
                                                 (let [url-path-part (nth url-path-parts dex)]
                                                      (assoc result x url-path-part))
                                                 (-> result))))]
                     (reduce-kv f {} url-path-template-parts)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-path-params]]))

(uri.api/to-url-path-params ...)
(to-url-path-params         ...)
```

</details>

---

### to-url-query-params

```
@param (string) n
```

```
@usage
(to-url-query-params "/my-path?my-query")
```

```
@example
(to-url-query-params "/my-path?my-query")
=>
{:my-query nil}
```

```
@example
(to-url-query-params "/my-path?my-param=my-value")
=>
{:my-param "my-value"}
```

```
@example
(to-url-query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
{:my-param "my-value" :your-param nil}
```

```
@example
(to-url-query-params "http://my-domain.com/my-path#my-fragment")
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-url-query-params
  [n]
  (-> n (to-url-query-string)
        (query/url-query-string->url-query-params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-query-params]]))

(uri.api/to-url-query-params ...)
(to-url-query-params         ...)
```

</details>

---

### to-url-query-string

```
@param (string) n
```

```
@usage
(to-url-query-string "/my-path?my-query")
```

```
@example
(to-url-query-string "/my-path?my-query")
=>
"my-query"
```

```
@example
(to-url-query-string "https://my-domain.com/my-path?my-query#my-fragment")
=>
"my-query"
```

```
@example
(to-url-query-string "https://my-domain.com/my-path#my-fragment")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-url-query-string
  [n]
  (-> n (string/after-first-occurence  "?" {:return? false})
        (string/before-first-occurence "#" {:return? true})
        (string/use-nil)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-query-string]]))

(uri.api/to-url-query-string ...)
(to-url-query-string         ...)
```

</details>

---

### to-url-tail

```
@param (string) n
```

```
@usage
(to-url-tail "https://my-domain.com?my-query")
```

```
@example
(to-url-tail "https://my-domain.com/my-path?my-query#my-fragment")
=>
"my-query#my-fragment"
```

```
@example
(to-url-tail "https://my-domain.com/my-path#my-fragment")
=>
"my-fragment"
```

```
@example
(to-url-tail "https://my-domain.com/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-url-tail
  [n]
  (if (string/contains-part?        n "?")
      (string/after-first-occurence n "?" {:return? false})
      (string/after-first-occurence n "#" {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [to-url-tail]]))

(uri.api/to-url-tail ...)
(to-url-tail         ...)
```

</details>

---

### url-path-template-matches?

```
@param (string) url-path
@param (string) url-path-template
@param (map)(opt) options
{:case-sensitive? (boolean)(opt)
  Default: false}
```

```
@usage
(url-path-template-matches? "/my-path/my-value" "/my-path/:my-param")
```

```
@example
(url-path-template-matches? "/my-path/my-value" "/my-path/:my-param")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn url-path-template-matches?
  ([url-path url-path-template]
   (url-path-template-matches? url-path url-path-template {}))

  ([url-path url-path-template {:keys [case-sensitive?]}]
   (letfn [(f1 [url-path url-path-template]
               (let [url-path-parts                (string/split url-path          #"/")
                     url-path-template-parts       (string/split url-path-template #"/")
                     url-path-parts-count          (count        url-path-parts)
                     url-path-template-parts-count (count        url-path-template-parts)]
                    (letfn [(f0 [dex] (cond
                                            (= dex url-path-template-parts-count)
                                            (-> true)

                                            (= ":" (str (get-in url-path-template-parts [dex 0])))
                                            (f0 (inc dex))

                                            (=  (get url-path-parts          dex)
                                                (get url-path-template-parts dex))
                                            (f0 (inc dex))

                                            :unmatching-parts nil))]

                           (if (= url-path-parts-count url-path-template-parts-count)
                               (f0 0)))))]

          (if case-sensitive? (f1 (str                 url-path)
                                  (str                 url-path-template))
                              (f1 (string/to-lowercase url-path)
                                  (string/to-lowercase url-path-template))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [url-path-template-matches?]]))

(uri.api/url-path-template-matches? ...)
(url-path-template-matches?         ...)
```

</details>

---

### url-query-params->url-query-string

```
@param (map) url-query-params
```

```
@usage
(url-query-params->url-query-string {:my-param "my-value"})
```

```
@example
(url-query-params->url-query-string {:my-param "my-value" :your-param nil})
=>
"my-param=my-value&your-param"
```

```
@example
(url-query-params->url-query-string {"my-param" "my-value" "your-param" nil})
=>
"my-param=my-value&your-param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn url-query-params->url-query-string
  [url-query-params]
  (letfn [(f [result k v] (str result (if result "&")
                                      (if (keyword? k)
                                          (name     k)
                                          (str      k))
                                      (if v "=") v))]
         (reduce-kv f nil url-query-params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [url-query-params->url-query-string]]))

(uri.api/url-query-params->url-query-string ...)
(url-query-params->url-query-string         ...)
```

</details>

---

### url-query-string->url-query-params

```
@param (string) url-query-string
```

```
@usage
(url-query-string->url-query-params "my-param=my-value")
```

```
@example
(url-query-string->url-query-params "my-param=my-value&your-param")
=>
{:my-param "my-value" :your-param nil}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn url-query-string->url-query-params
  [url-query-string]
  (letfn [(f [result x] (let [[k v] (string/split x #"=")]
                             (assoc result (keyword k) v)))]
         (reduce f {} (string/split url-query-string #"&"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [url-query-string->url-query-params]]))

(uri.api/url-query-string->url-query-params ...)
(url-query-string->url-query-params         ...)
```

</details>

---

### use-url-query-string

```
@param (string) n
@param (string) url-query-string
```

```
@usage
(use-url-query-string "my-domain.com/my-path" "my-param")
```

```
@example
(use-url-query-string "my-domain.com/my-path" "my-param")
=>
"my-domain.com/my-path?my-param"
```

```
@example
(use-url-query-string "my-domain.com/my-path" "my-param=my-value")
=>
"my-domain.com/my-path?my-param=my-value"
```

```
@example
(use-url-query-string "my-domain.com/my-path#my-fragment" "my-param")
=>
"my-domain.com/my-path?my-param#my-fragment"
```

```
@example
(use-url-query-string "my-domain.com/my-path?my-param" "your-param=your-value")
=>
"my-domain.com/my-path?my-param&your-param=your-value"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn use-url-query-string
  [n url-query-string]
  (letfn [
          (use-original [] (if-let [% (convert/to-url-query-string n)]
                                   (str % "&" url-query-string)
                                   (str       url-query-string)))

          (remove-duplicates [%] (-> % query/url-query-string->url-query-params
                                       query/url-query-params->url-query-string))]

         (let [url-fragment     (convert/to-url-fragment n)
               url-query-string (-> (use-original)
                                    (remove-duplicates))]
              (str (-> n (string/before-first-occurence "?" {:return? true})
                         (string/before-first-occurence "#" {:return? true}))
                   (if (string/nonblank? url-query-string) (str "?" url-query-string))
                   (if (string/nonblank? url-fragment)     (str "#" url-fragment))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [use-url-query-string]]))

(uri.api/use-url-query-string ...)
(use-url-query-string         ...)
```

</details>

---

### valid-url

```
@param (string) n
```

```
@usage
(valid-url "my-domain.com")
```

```
@example
(valid-url "my-domain.com")
=>
"https://my-domain.com"
```

```
@example
(valid-url "my-domain.com/")
=>
"https://my-domain.com"
```

```
@example
(valid-url "http://my-domain.com")
=>
"http://my-domain.com"
```

```
@example
(valid-url "/my-path")
=>
"/my-path"
```

```
@example
(valid-url "my-path")
=>
"/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-url
  [n]
  (if-let [domain (convert/to-domain n)]
          (if (string/contains-part? n "://")
              (-> n (convert/to-lowercase)
                    (string/not-ends-with! "/"))
              (-> n (convert/to-lowercase)
                    (string/not-ends-with! "/")
                    (string/starts-with!   "https://")))
          (-> n (string/not-ends-with! "/")
                (string/starts-with!   "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [valid-url]]))

(uri.api/valid-url ...)
(valid-url         ...)
```

</details>

---

### valid-url-path

```
@param (string) n
```

```
@usage
(valid-url-path "/my-path")
```

```
@example
(valid-url-path "my-path")
=>
"/my-path"
```

```
@example
(valid-url-path "/my-path")
=>
"/my-path"
```

```
@example
(valid-url-path "/my-path/")
=>
"/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-url-path
  [n]
  (if-let [url-path (convert/to-url-path n)]
          (-> url-path)
          (-> "/")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :refer [valid-url-path]]))

(uri.api/valid-url-path ...)
(valid-url-path         ...)
```

</details>

---

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

