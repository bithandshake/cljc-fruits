
# <strong>uri.api</strong> namespace
<p>Documentation of the <strong>uri/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > uri.api</strong>



### domain?

```
@param (*) n
@param (map) 
 {:strict? (boolean)(opt)
   Default: false}
```

```
@usage
(domain? "my-domain.com")
```

```
@example
(domain? "https://my-domain.com")
=>
true
```

```
@example
(domain? "https://my-domain.com/")
=>
true
```

```
@example
(domain? www."my-domain.com/")
=>
true
```

```
@example
(domain? "my-domain.com/")
=>
true
```

```
@example
(domain? "sub.my-domain.com/")
=>
true
```

```
@example
(domain? "https://my-domain.com" {:strict? true})
=>
false
```

```
@example
(domain? "my-domain.com/my-path")
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn domain?
  ([n]
   (domain? n {}))

  ([n {:keys [strict?] :or {strict? false}}]
   (if strict? (re-match? n config/STRICT-DOMAIN-PATTERN)
               (re-match? n config/DOMAIN-PATTERN))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [domain?]]))

(uri/domain? ...)
(domain?     ...)
```

</details>

---

### match-template?

```
@param (string) path
@param (string) template
```

```
@usage
(match-template? "/my-path/my-value" "/my-path/:my-param")
```

```
@example
(match-template? "/my-path/my-value" "/my-path/:my-param")
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn match-template?
  [path template]
  (let [path                 (string/to-lowercase path)
        template             (string/to-lowercase template)
        path-parts           (string/split path     #"/")
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
(ns my-namespace (:require [uri.api :as uri :refer [match-template?]]))

(uri/match-template? ...)
(match-template?     ...)
```

</details>

---

### query-params->query-string

```
@param (map) query-params
```

```
@usage
(query-params->query-string {:my-param "my-value"})
```

```
@example
(query-params->query-string {:my-param "my-value" :your-param nil})
=>
"my-param=my-value&your-param"
```

```
@example
(query-params->query-string {"my-param" "my-value" "your-param" nil})
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
  (letfn [(f [o k v] (str o (if o "&")
                            (if (keyword? k)
                                (name     k)
                                (str      k))
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
@usage
(query-string->query-params "my-param=my-value")
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
  (let [query-string (string/to-lowercase query-string)]
       (letfn [(f [o x] (let [[k v] (string/split x #"=")]
                             (assoc o (keyword k) v)))]
              (reduce f {} (string/split query-string #"&")))))
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

### to-absolute

```
@param (string) n
@param (string) domain
```

```
@usage
(to-absolute "/my-path" "my-domain.com")
```

```
@example
(to-absolute "/my-path" "my-domain.com")
=>
"https://my-domain.com/my-path"
```

```
@example
(to-absolute "my-domain.com/my-path" "my-domain.com")
=>
"https://my-domain.com/my-path"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-absolute
  [n domain]
  (let [n      (string/to-lowercase n)
        domain (string/to-lowercase domain)]
       (if-let [absolute? (to-domain n)]
               (-> n (string/not-ends-with! "/")
                     (string/starts-with!   "https://"))
               (-> n (string/starts-with!   "/")
                     (string/prepend        domain)
                     (string/not-ends-with! "/")
                     (string/starts-with!   "https://")))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-absolute]]))

(uri/to-absolute ...)
(to-absolute     ...)
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
(to-domain "https://my-domain.com/my-path")
=>
"my-domain.com"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-domain
  [n]
  (as-> n % (string/after-first-occurence  % "://" {:return? true})
            (string/before-first-occurence % "/"   {:return? true})
            (if (re-match? % config/STRICT-DOMAIN-PATTERN)
                (-> % (string/to-lowercase)
                      (string/use-nil)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-domain]]))

(uri/to-domain ...)
(to-domain     ...)
```

</details>

---

### to-encoded

```
@param (string) n
@param (map)(opt) options
 {:strict? (boolean)(opt)
   Default: false}
```

```
@usage
(to-encoded "my-domain.com/my path")
```

```
@example
(to-encoded "my-domain.com/my path?my param")
=>
"my-domain.com/my%20path?my%20param"
```

```
@example
(to-encoded "my-domain.com/my path?my param" {:strict? true})
=>
"my-domain.com%2Fmy%20path%3Fmy%20param"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-encoded
  ([n]
   (to-encoded n {}))

  ([n {:keys [strict?]}]
   #?(:cljs (if strict? (.encodeURIComponent js/window n)
                        (.encodeURI          js/window n)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-encoded]]))

(uri/to-encoded ...)
(to-encoded     ...)
```

</details>

---

### to-fragment

```
@param (string) n
```

```
@usage
(to-fragment "/my-path#my-fragment")
```

```
@example
(to-fragment "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-fragment"
```

```
@example
(to-fragment "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
nil
```

```
@example
(to-fragment "/my-path#my-fragment")
=>
"my-fragment"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-fragment
  [n]
  (-> n (string/to-lowercase)
        (string/after-first-occurence "#" {:return? false})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-fragment]]))

(uri/to-fragment ...)
(to-fragment     ...)
```

</details>

---

### to-parent

```
@param (string) n
```

```
@usage
(to-parent "https://my-domain.com/my-path")
```

```
@example
(to-parent "https://my-domain.com/my-path")
=>
"https://my-domain.com"
```

```
@example
(to-parent "https://my-domain.com")
=>
"https://my-domain.com"
```

```
@example
(to-parent "/my-path/your-path")
=>
"/my-path"
```

```
@example
(to-parent "/my-path")
=>
"/"
```

```
@example
(to-parent "/")
=>
"/"
```

```
@example
(to-parent "my-path")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-parent
  [n]
  (if (regex/starts-with? n config/DOMAIN-PATTERN)
      (-> n (string/to-lowercase)
            (string/before-last-occurence "/" {:return? true})
            (string/not-ends-with!        "/"))
      (-> n (string/to-lowercase)
            (string/before-last-occurence "/" {:return? false})
            (string/starts-with!          "/"))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-parent]]))

(uri/to-parent ...)
(to-parent     ...)
```

</details>

---

### to-path

```
@param (string) n
```

```
@usage
(to-path "https://my-domain.com/my-path")
```

```
@example
(to-path "https://my-domain.com/my-path")
=>
"/my-path"
```

```
@example
(to-path "https://my-domain.com")
=>
"/"
```

```
@example
(to-path "https://my-domain.com/my-path?my-param=my-value&your-param")
=>
"/my-path"
```

```
@example
(to-path "https://my-domain.com/?my-param=my-value&your-param")
=>
"/"
```

```
@example
(to-path "https://my-domain.com?my-param=my-value&your-param")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-path
  [n]
  (-> n (to-relative)
        (string/before-first-occurence "?" {:return? true})
        (string/before-first-occurence "#" {:return? true})))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-path]]))

(uri/to-path ...)
(to-path     ...)
```

</details>

---

### to-path-params

```
@param (string) n
@param (string) template
```

```
@usage
(to-path-params "/my-path" "/:a")
```

```
@example
(to-path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
=>
{:a "my-path" :b "your-path"}
```

```
@example
(to-path-params "https://my-domain.com/my-path/your-path" "/:a/b")
=>
{:a "my-path"}
```

```
@example
(to-path-params "/my-path/your-path" "/:a/:b")
=>
{:a "my-path" :b "your-path"}
```

```
@example
(to-path-params "/my-path/your-path" "/a/b")
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-path-params
  [n template]
  (letfn [(to-path-parts [n] (-> n (to-path)
                                   (string/not-starts-with!  "/")
                                   (string/not-ends-with!    "/")
                                   (string/split            #"/")))]
         (let [path           (to-path       n)
               path-parts     (to-path-parts path)
               template-parts (to-path-parts template)]
              (letfn [(f [o dex x] (let [x (reader/string->mixed x)]
                                        (if (keyword? x)
                                            (let [path-part (nth path-parts dex)]
                                                 (assoc o x path-part))
                                            (return o))))]
                     (reduce-kv f {} template-parts)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-path-params]]))

(uri/to-path-params ...)
(to-path-params     ...)
```

</details>

---

### to-protocol

```
@param (string) n
```

```
@usage
(to-protocol "https://my-domain.com/my-path")
```

```
@example
(to-protocol "https://my-domain.com/my-path")
=>
"https"
```

```
@example
(to-protocol "my-domain.com/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-protocol
  [n]
  (as-> n % (string/to-first-occurence % "://" {:return? false})
            (if (re-match? % config/PROTOCOL-PATTERN)
                (-> % (string/not-ends-with! "://")
                      (string/to-lowercase)
                      (string/use-nil)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-protocol]]))

(uri/to-protocol ...)
(to-protocol     ...)
```

</details>

---

### to-query-params

```
@param (string) n
```

```
@usage
(to-query-params "/my-path?my-param=my-value")
```

```
@example
(to-query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
{:my-param "my-value" :your-param nil}
```

```
@example
(to-query-params "http://my-domain.com/my-path#my-fragment")
=>
{}
```

```
@example
(to-query-params "/my-path?my-param=my-value")
=>
{:my-param "my-value"}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn to-query-params
  [n]
  (-> n (to-query-string)
        (query/query-string->query-params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-query-params]]))

(uri/to-query-params ...)
(to-query-params     ...)
```

</details>

---

### to-query-string

```
@param (string) n
```

```
@usage
(to-query-string "/my-path?my-param=my-value")
```

```
@example
(to-query-string "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-param=my-value&your-param"
```

```
@example
(to-query-string "https://my-domain.com/my-path#my-fragment")
=>
nil
```

```
@example
(to-query-string "/my-path?my-param=my-value")
=>
"my-param=my-value"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-query-string
  [n]
  (-> n (string/to-lowercase)
        (string/after-first-occurence  "?" {:return? false})
        (string/before-first-occurence "#" {:return? true})
        (string/use-nil)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-query-string]]))

(uri/to-query-string ...)
(to-query-string     ...)
```

</details>

---

### to-relative

```
@param (string) n
```

```
@usage
(to-relative "my-domain.com/my-path")
```

```
@example
(to-relative "my-domain.com/my-path?my-param#my-fragment")
=>
"/my-path?my-param#my-fragment"
```

```
@example
(to-relative "my-domain.com")
=>
"/"
```

```
@example
(to-relative "/my-path")
=>
"/my-path"
```

```
@example
(to-relative "my-path")
=>
"/my-path"
```

```
@example
(to-relative "/")
=>
"/"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-relative
  [n]
  (let [n (string/to-lowercase n)]
       (if-let [domain (to-domain n)]
               (-> n (string/after-first-occurence domain)
                     (string/not-ends-with! "/")
                     (string/starts-with!   "/"))
               (-> n (string/not-ends-with! "/")
                     (string/starts-with!   "/")))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-relative]]))

(uri/to-relative ...)
(to-relative     ...)
```

</details>

---

### to-subdomain

```
@param (string) n
```

```
@usage
(to-subdomain "https://subdomain.my-domain.com")
```

```
@example
(to-subdomain "https://subdomain.my-domain.com/my-path")
=>
"subdomain"
```

```
@example
(to-subdomain "https://my-domain.com/my-path")
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
(ns my-namespace (:require [uri.api :as uri :refer [to-subdomain]]))

(uri/to-subdomain ...)
(to-subdomain     ...)
```

</details>

---

### to-tail

```
@param (string) n
```

```
@usage
(to-tail "https://my-domain.com?my-param=my-value")
```

```
@example
(to-tail "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
=>
"my-param=my-value&your-param#my-fragment"
```

```
@example
(to-tail "https://my-domain.com/my-path#my-fragment")
=>
"my-fragment"
```

```
@example
(to-tail "https://my-domain.com/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn to-tail
  [n]
  (if (-> n (string/contains-part? "?"))
      (-> n (string/to-lowercase)
            (string/after-first-occurence "?" {:return? false}))
      (-> n (string/to-lowercase)
            (string/after-first-occurence "#" {:return? false}))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [to-tail]]))

(uri/to-tail ...)
(to-tail     ...)
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
(to-tld "https://my-domain.com/my-path")
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
(ns my-namespace (:require [uri.api :as uri :refer [to-tld]]))

(uri/to-tld ...)
(to-tld     ...)
```

</details>

---

### use-query-string

```
@param (string) n
@param (string) query-string
```

```
@usage
(use-query-string "my-domain.com/my-path" "my-param")
```

```
@example
(use-query-string "my-domain.com/my-path" "my-param")
=>
"my-domain.com/my-path?my-param"
```

```
@example
(use-query-string "my-domain.com/my-path" "my-param=my-value")
=>
"my-domain.com/my-path?my-param=my-value"
```

```
@example
(use-query-string "my-domain.com/my-path#my-fragment" "my-param")
=>
"my-domain.com/my-path?my-param#my-fragment"
```

```
@example
(use-query-string "my-domain.com/my-path?my-param" "your-param=your-value")
=>
"my-domain.com/my-path?my-param&your-param=your-value"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn use-query-string
  [uri query-string]
  (letfn [(remove-duplicates [%] (-> % query/query-string->query-params query/query-params->query-string))]
         (let [fragment     (convert/to-fragment uri)
               query-string (if-let [% (convert/to-query-string uri)] (str % "&" query-string) query-string)
               query-string (remove-duplicates query-string)]
              (str (-> uri (string/before-first-occurence "?" {:return? true})
                           (string/before-first-occurence "#" {:return? true}))
                   (if (string/nonblank? query-string) (str "?" query-string))
                   (if (string/nonblank? fragment)     (str "#" fragment))))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [use-query-string]]))

(uri/use-query-string ...)
(use-query-string     ...)
```

</details>

---

### valid-domain

```
@param (string) n
```

```
@usage
(valid-domain "my-domain.com")
```

```
@example
(valid-domain "my-domain.com")
=>
"https://my-domain.com"
```

```
@example
(valid-domain "my-domain.com/")
=>
"https://my-domain.com"
```

```
@example
(valid-domain "http://my-domain.com")
=>
"http://my-domain.com"
```

```
@example
(valid-domain "my-domain.com?my-param")
=>
"https://my-domain.com"
```

```
@example
(valid-domain "/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-domain
  [n]
  (if-let [domain (convert/to-domain n)]
          (if-let [protocol (convert/to-protocol n)]
                  (-> n (string/to-lowercase)
                        (string/not-ends-with!         "/")
                        (string/before-first-occurence "?" {:return? true})
                        (string/before-first-occurence "#" {:return? true}))
                  (-> n (string/to-lowercase)
                        (string/not-ends-with!         "/")
                        (string/starts-with!           "https://")
                        (string/before-first-occurence "?" {:return? true})
                        (string/before-first-occurence "#" {:return? true})))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [uri.api :as uri :refer [valid-domain]]))

(uri/valid-domain ...)
(valid-domain     ...)
```

</details>

---

### valid-path

```
@param (string) n
```

```
@usage
(valid-path "/my-path")
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
  [n]
  (if-let [path (convert/to-path n)]
          (return path)
          (return "/")))
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
@param (string) n
```

```
@usage
(valid-uri "my-domain.com")
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
@example
(valid-uri "/my-path")
=>
nil
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn valid-uri
  [n]
  (if-let [domain (convert/to-domain n)]
          (if-let [protocol (convert/to-protocol n)]
                  (-> n (string/to-lowercase)
                        (string/not-ends-with! "/"))
                  (-> n (string/to-lowercase)
                        (string/not-ends-with! "/")
                        (string/starts-with!   "https://")))))
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
