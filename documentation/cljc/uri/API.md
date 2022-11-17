
# <strong>uri.api</strong> namespace
<p>Documentation of the <strong>uri/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > uri.api</strong>



### path->match-template?

```
@param (string) path
@param (string) template
```

```
@example
(path->match-template? "/my-path/my-value" "/my-path/:my-param")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn path->match-template?
  [path template]
  (let [path-parts           (string/split path     #"/")
        template-parts       (string/split template #"/")
        path-parts-count     (count path-parts)
        template-parts-count (count template-parts)]
       (letfn [(f [dex] (cond (= dex template-parts-count)
                              (return true)
                              (= ":" (str (get-in template-parts [dex 0])))
                              (f (inc dex))
                              (= (get path-parts     dex)
                                 (get template-parts dex))
                              (f (inc dex))))]
              (boolean (if (= path-parts-count template-parts-count)
                           (f 0))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [path->match-template?]]))

(uri/path->match-template? ...)
(path->match-template?     ...)
```

</details>

---

### query-params->query-string

```
@param (map) query-params
```

```
@example
(query-params->query-string {:my-param "my-value" :your-param nil})
=>
"my-param=my-value&your-param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn query-params->query-string
  [query-params]
  (letfn [(f [o k v] (str o (if o "&") (name k)
                            (if v "=") v))]
         (reduce-kv f nil query-params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [query-params->query-string]]))

(uri/query-params->query-string ...)
(query-params->query-string     ...)
```

</details>

---

### query-string->query-params

```
@param (string) query-string
```

```
@example
(query-string->query-params "my-param=my-value&your-param")
=>
{:my-param "my-value" :your-param nil}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn query-string->query-params
  [query-string]
  (letfn [(f [o x] (let [[k v] (string/split x #"=")]
                        (assoc o (keyword k) v)))]
         (reduce f {} (string/split query-string #"&"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [query-string->query-params]]))

(uri/query-string->query-params ...)
(query-string->query-params     ...)
```

</details>

---

### string->uri

```
@param (string) n
```

```
@example
(string->uri "my-domain.com/my path?my param")
=>
"my-domain.com/my%20path?my%20param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn string->uri
  [n]
  #?(:cljs (.encodeURI js/window n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [string->uri]]))

(uri/string->uri ...)
(string->uri     ...)
```

</details>

---

### string->uri-part

```
@param (string) n
```

```
@example
(string->uri "my-domain.com/my path?my param")
=>
"my-domain.com%2Fmy%20path%3Fmy%20param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn string->uri-part
  [n]
  #?(:cljs (.encodeURIComponent js/window n)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [string->uri-part]]))

(uri/string->uri-part ...)
(string->uri-part     ...)
```

</details>

---

### uri->domain

```
@param (string) uri
```

```
@example
(uri->tld "https://my-domain.com/my-path")
=>
"my-domain.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->domain
  [uri]
  (-> uri (string/after-first-occurence  "://" {:return? true})
          (string/before-first-occurence "/"   {:return? true})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->domain]]))

(uri/uri->domain ...)
(uri->domain     ...)
```

</details>

---

### uri->fragment

```
@param (string) uri
```

```
@example
(uri->fragment "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-fragment"
```

```
@example
(uri->fragment "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->fragment
  [uri]
  (string/after-first-occurence uri "#" {:return? false}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->fragment]]))

(uri/uri->fragment ...)
(uri->fragment     ...)
```

</details>

---

### uri->local-uri

```
@param (string) uri
```

```
@example
(uri->local-uri "my-domain.com/my-path?my-param#my-fragment")
=>
"/my-path?my-param#my-fragment"
```

```
@example
(uri->local-uri "/my-path")
=>
"/my-path"
```

```
@example
(uri->local-uri "/")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->local-uri
  [uri]
  (let [domain (uri->domain uri)]
       (if (string/nonblank?                domain)
           (string/after-last-occurence uri domain)
           (return                      uri))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->local-uri]]))

(uri/uri->local-uri ...)
(uri->local-uri     ...)
```

</details>

---

### uri->parent-uri

```
@param (string) uri
```

```
@example
(uri->parent-uri "/my-path/your-path")
=>
"/my-path"
```

```
@example
(uri->parent-uri "/my-path")
=>
"/"
```

```
@example
(uri->parent-uri "/")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->parent-uri
  [uri]
  (-> uri (string/before-last-occurence "/" {:return? false})
          (string/starts-with!          "/")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->parent-uri]]))

(uri/uri->parent-uri ...)
(uri->parent-uri     ...)
```

</details>

---

### uri->path

```
@param (string) uri
```

```
@example
(uri->path "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
"/my-path"
```

```
@example
(uri->path "https://my-domain.com/?my-param=my-value&your-param")
=>
"/"
```

```
@example
(uri->path "https://my-domain.com?my-param=my-value&your-param")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->path
  [uri]
  (let [trimmed-uri (uri->trimmed-uri uri)]
       (if (string/contains-part? trimmed-uri "/")
           (str    "/" (string/after-first-occurence trimmed-uri "/"))
           (return "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->path]]))

(uri/uri->path ...)
(uri->path     ...)
```

</details>

---

### uri->path-params

```
@param (string) uri
@param (string) template
```

```
@example
(uri->path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
=>
{:a "my-path" :b "your-path"}
```

```
@example
(uri->path-params "https://my-domain.com/my-path/your-path" "/:a/b")
=>
{:a "my-path"}
```

```
@example
(uri->path-params "/my-path/your-path" "/:a/:b")
=>
{:a "my-path" :b "your-path"}
```

```
@example
(uri->path-params "/my-path/your-path" "/a/b")
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn uri->path-params
  [uri template]
  (let [path           (uri->path       uri)
        path-parts     (uri->path-parts path)
        template-parts (uri->path-parts template)]
       (letfn [(f [o dex x] (let [x (reader/string->mixed x)]
                                 (if (keyword? x)
                                     (let [path-part (nth path-parts dex)]
                                          (assoc o x path-part))
                                     (return o))))]
              (reduce-kv f {} template-parts))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->path-params]]))

(uri/uri->path-params ...)
(uri->path-params     ...)
```

</details>

---

### uri->path-parts

```
@param (string) uri
```

```
@example
(uri->path-parts "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
["my-path"]
```

```
@example
(uri->path-parts "https://my-domain.com/my-path/your-path")
=>
["my-path" "your-path"]
```

```
@example
(uri->path-parts "https://my-domain.com/")
=>
[]
```

```
@return (vector)
```

<details>
<summary>Source code</summary>

```
(defn uri->path-parts
  [uri]
  (let [trimmed-path (uri->trimmed-path uri)]
       (string/split trimmed-path #"/")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->path-parts]]))

(uri/uri->path-parts ...)
(uri->path-parts     ...)
```

</details>

---

### uri->protocol

```
@param (string) uri
```

```
@example
(uri->protocol "https://my-domain.com/my-path")
=>
"https"
```

```
@example
(uri->protocol "my-domain.com/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->protocol
  [uri]
  (string/before-first-occurence uri "://" {:return? false}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->protocol]]))

(uri/uri->protocol ...)
(uri->protocol     ...)
```

</details>

---

### uri->query-params

```
@param (string) uri
```

```
@example
(uri->query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
{:my-param "my-value" :your-param nil}
```

```
@example
(uri->query-params "http://my-domain.com/my-path#my-fragment")
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn uri->query-params
  [uri]
  (let [query-string (uri->query-string uri)]
       (query-string->query-params query-string)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->query-params]]))

(uri/uri->query-params ...)
(uri->query-params     ...)
```

</details>

---

### uri->query-string

```
@param (string) uri
```

```
@example
(uri->query-string "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-param=my-value&your-param"
```

```
@example
(uri->query-string "https://my-domain.com/my-path#my-fragment")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->query-string
  [uri]
  (-> uri (string/after-first-occurence  "?" {:return? false})
          (string/before-first-occurence "#" {:return? true})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->query-string]]))

(uri/uri->query-string ...)
(uri->query-string     ...)
```

</details>

---

### uri->subdomain

```
@param (string) uri
```

```
@example
(uri->tld "https://subdomain.my-domain.com/my-path")
=>
"subdomain"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->subdomain
  [uri]
  (let [domain (uri->domain uri)]
       (if (and (string/nonblank?      domain)
                (string/min-occurence? domain "." 2))
           (string/before-first-occurence domain "."))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->subdomain]]))

(uri/uri->subdomain ...)
(uri->subdomain     ...)
```

</details>

---

### uri->tail

```
@param (string) uri
```

```
@example
(uri->tail "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-param=my-value&your-param#my-fragment"
```

```
@example
(uri->tail "https://my-domain.com/my-path#my-fragment")
=>
"my-fragment"
```

```
@example
(uri->tail "https://my-domain.com/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->tail
  [uri]
  (if (string/contains-part?        uri "?")
      (string/after-first-occurence uri "?")
      (string/after-first-occurence uri "#" {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->tail]]))

(uri/uri->tail ...)
(uri->tail     ...)
```

</details>

---

### uri->tld

```
@param (string) uri
```

```
@example
(uri->tld "https://my-domain.com/my-path")
=>
"com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->tld
  [uri]
  (let [domain (uri->domain uri)]
       (if (string/nonblank?            domain)
           (string/after-last-occurence domain "." {:return? false}))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->tld]]))

(uri/uri->tld ...)
(uri->tld     ...)
```

</details>

---

### uri->trimmed-path

```
@param (string) uri
```

```
@example
(uri->trimmed-path "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
"my-path"
```

```
@example
(uri->trimmed-path "https://my-domain.com/my-path/")
=>
"my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->trimmed-path
  [uri]
  (let [path (uri->path uri)]
       (-> path (string/not-starts-with! "/")
                (string/not-ends-with!   "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->trimmed-path]]))

(uri/uri->trimmed-path ...)
(uri->trimmed-path     ...)
```

</details>

---

### uri->trimmed-uri

```
@param (string) uri
```

```
@example
(uri->trimmed-uri "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-domain.com/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri->trimmed-uri
  [uri]
  (-> uri (string/after-first-occurence  "://"  {:return? true})
          (string/after-first-occurence  "www." {:return? true})
          (string/before-first-occurence "?"    {:return? true})
          (string/before-first-occurence "#"    {:return? true})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri->trimmed-uri]]))

(uri/uri->trimmed-uri ...)
(uri->trimmed-uri     ...)
```

</details>

---

### uri<-query-string

```
@param (string) uri
@param (string) query-string
```

```
@example
(uri<-query-param "my-domain.com/my-path" "my-param")
=>
"my-domain.com/my-path?my-param"
```

```
@example
(uri<-query-param "my-domain.com/my-path" "my-param=my-value")
=>
"my-domain.com/my-path?my-param=my-value"
```

```
@example
(uri<-query-param "my-domain.com/my-path#my-fragment" "my-param")
=>
"my-domain.com/my-path?my-param#my-fragment"
```

```
@example
(uri<-query-param "my-domain.com/my-path?my-param" "your-param=your-value")
=>
"my-domain.com/my-path?my-param&your-param=your-value"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn uri<-query-string
  [uri query-string]
  (let [fragment     (uri->fragment uri)
        query-string (if-let [x (uri->query-string uri)] (str x "&" query-string) query-string)
        query-string (-> query-string query-string->query-params query-params->query-string)]
       (str (-> uri (string/before-first-occurence "?" {:return? true})
                    (string/before-first-occurence "#" {:return? true}))
            (if (string/nonblank? query-string) (str "?" query-string))
            (if (string/nonblank? fragment)     (str "#" fragment)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [uri<-query-string]]))

(uri/uri<-query-string ...)
(uri<-query-string     ...)
```

</details>

---

### valid-path

```
@param (string) path
```

```
@example
(valid-path "my-path")
=>
"/my-path"
```

```
@example
(valid-path "/my-path")
=>
"/my-path"
```

```
@example
(valid-path "/my-path/")
=>
"/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-path
  [path]
  (-> path (string/not-ends-with! "/")
           (string/starts-with!   "/")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [valid-path]]))

(uri/valid-path ...)
(valid-path     ...)
```

</details>

---

### valid-uri

```
@param (string) uri
```

```
@example
(valid-uri "my-domain.com")
=>
"https://my-domain.com"
```

```
@example
(valid-uri "my-domain.com/")
=>
"https://my-domain.com"
```

```
@example
(valid-uri "http://my-domain.com")
=>
"http://my-domain.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-uri
  [uri]
  (let [protocol (uri->protocol uri)]
       (if (string/nonblank? protocol)
           (string/not-ends-with! uri "/")
           (str protocol "https://" (string/not-ends-with! uri "/")))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [valid-uri]]))

(uri/valid-uri ...)
(valid-uri     ...)
```

</details>
