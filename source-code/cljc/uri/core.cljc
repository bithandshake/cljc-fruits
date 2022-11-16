
(ns uri.core
    (:require [candy.api         :refer [return]]
              [reader.api        :as reader]
              [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn uri->protocol
  ; @param (string) uri
  ;
  ; @example
  ; (uri->protocol "https://my-domain.com/my-path")
  ; =>
  ; "https"
  ;
  ; @example
  ; (uri->protocol "my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [uri]
  (string/before-first-occurence uri "://" {:return? false}))

(defn uri->domain
  ; @param (string) uri
  ;
  ; @example
  ; (uri->tld "https://my-domain.com/my-path")
  ; =>
  ; "my-domain.com"
  ;
  ; @return (string)
  [uri]
  (-> uri (string/after-first-occurence  "://" {:return? true})
          (string/before-first-occurence "/"   {:return? true})))

(defn uri->subdomain
  ; @param (string) uri
  ;
  ; @example
  ; (uri->tld "https://subdomain.my-domain.com/my-path")
  ; =>
  ; "subdomain"
  ;
  ; @return (string)
  [uri]
  (let [domain (uri->domain uri)]
       (if (and (string/nonempty?      domain)
                (string/min-occurence? domain "." 2))
           (string/before-first-occurence domain "."))))

(defn uri->tld
  ; @param (string) uri
  ;
  ; @example
  ; (uri->tld "https://my-domain.com/my-path")
  ; =>
  ; "com"
  ;
  ; @return (string)
  [uri]
  (let [domain (uri->domain uri)]
       (if (string/nonempty?            domain)
           (string/after-last-occurence domain "." {:return? false}))))

(defn uri->tail
  ; @param (string) uri
  ;
  ; @example
  ; (uri->tail "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-param=my-value&your-param#my-fragment"
  ;
  ; @example
  ; (uri->tail "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (uri->tail "https://my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [uri]
  (if (string/contains-part?        uri "?")
      (string/after-first-occurence uri "?")
      (string/after-first-occurence uri "#" {:return? false})))

(defn uri->parent-uri
  ; @param (string) uri
  ;
  ; @example
  ; (uri->parent-uri "/my-path/your-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (uri->parent-uri "/my-path")
  ; =>
  ; "/"
  ;
  ; @example
  ; (uri->parent-uri "/")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [uri]
  (-> uri (string/before-last-occurence "/" {:return? false})
          (string/starts-with!          "/")))

(defn uri->local-uri
  ; @param (string) uri
  ;
  ; @example
  ; (uri->local-uri "my-domain.com/my-path?my-param#my-fragment")
  ; =>
  ; "/my-path?my-param#my-fragment"
  ;
  ; @example
  ; (uri->local-uri "/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (uri->local-uri "/")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [uri]
  (let [domain (uri->domain uri)]
       (if (string/nonempty?                domain)
           (string/after-last-occurence uri domain)
           (return                      uri))))

(defn uri->trimmed-uri
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (string) uri
  ;
  ; @example
  ; (uri->trimmed-uri "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-domain.com/my-path"
  ;
  ; @return (string)
  [uri]
  (-> uri (string/after-first-occurence  "://"  {:return? true})
          (string/after-first-occurence  "www." {:return? true})
          (string/before-first-occurence "?"    {:return? true})
          (string/before-first-occurence "#"    {:return? true})))

(defn uri->path
  ; @param (string) uri
  ;
  ; @example
  ; (uri->path "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (uri->path "https://my-domain.com/?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @example
  ; (uri->path "https://my-domain.com?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [uri]
  (let [trimmed-uri (uri->trimmed-uri uri)]
       (if (string/contains-part? trimmed-uri "/")
           (str    "/" (string/after-first-occurence trimmed-uri "/"))
           (return "/"))))

(defn uri->trimmed-path
  ; WARNING! NON-PUBLIC! DO NOT USE!
  ;
  ; @param (string) uri
  ;
  ; @example
  ; (uri->trimmed-path "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; "my-path"
  ;
  ; @example
  ; (uri->trimmed-path "https://my-domain.com/my-path/")
  ; =>
  ; "my-path"
  ;
  ; @return (string)
  [uri]
  (let [path (uri->path uri)]
       (-> path (string/not-starts-with! "/")
                (string/not-ends-with!   "/"))))

(defn uri->path-parts
  ; @param (string) uri
  ;
  ; @example
  ; (uri->path-parts "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; ["my-path"]
  ;
  ; @example
  ; (uri->path-parts "https://my-domain.com/my-path/your-path")
  ; =>
  ; ["my-path" "your-path"]
  ;
  ; @example
  ; (uri->path-parts "https://my-domain.com/")
  ; =>
  ; []
  ;
  ; @return (vector)
  [uri]
  (let [trimmed-path (uri->trimmed-path uri)]
       (string/split trimmed-path #"/")))

(defn uri->path-params
  ; @param (string) uri
  ; @param (string) template
  ;
  ; @example
  ; (uri->path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
  ; =>
  ; {:a "my-path" :b "your-path"}
  ;
  ; @example
  ; (uri->path-params "https://my-domain.com/my-path/your-path" "/:a/b")
  ; =>
  ; {:a "my-path"}
  ;
  ; @example
  ; (uri->path-params "/my-path/your-path" "/:a/:b")
  ; =>
  ; {:a "my-path" :b "your-path"}
  ;
  ; @example
  ; (uri->path-params "/my-path/your-path" "/a/b")
  ; =>
  ; {}
  ;
  ; @return (map)
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

(defn uri->fragment
  ; @param (string) uri
  ;
  ; @example
  ; (uri->fragment "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (uri->fragment "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; nil
  ;
  ; @return (string)
  [uri]
  (string/after-first-occurence uri "#" {:return? false}))

(defn uri->query-string
  ; @param (string) uri
  ;
  ; @example
  ; (uri->query-string "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @example
  ; (uri->query-string "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; nil
  ;
  ; @return (string)
  [uri]
  (-> uri (string/after-first-occurence  "?" {:return? false})
          (string/before-first-occurence "#" {:return? true})))

(defn query-params->query-string
  ; @param (map) query-params
  ;
  ; @example
  ; (query-params->query-string {:my-param "my-value" :your-param nil})
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @return (string)
  [query-params]
  (letfn [(f [o k v] (str o (if o "&") (name k)
                            (if v "=") v))]
         (reduce-kv f nil query-params)))

(defn query-string->query-params
  ; @param (string) query-string
  ;
  ; @example
  ; (query-string->query-params "my-param=my-value&your-param")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @return (map)
  [query-string]
  (letfn [(f [o x] (let [[k v] (string/split x #"=")]
                        (assoc o (keyword k) v)))]
         (reduce f {} (string/split query-string #"&"))))

(defn uri->query-params
  ; @param (string) uri
  ;
  ; @example
  ; (uri->query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @example
  ; (uri->query-params "http://my-domain.com/my-path#my-fragment")
  ; =>
  ; {}
  ;
  ; @return (map)
  [uri]
  (let [query-string (uri->query-string uri)]
       (query-string->query-params query-string)))

(defn string->uri
  ; @param (string) n
  ;
  ; @example
  ; (string->uri "my-domain.com/my path?my param")
  ; =>
  ; "my-domain.com/my%20path?my%20param"
  ;
  ; @return (string)
  [n]
  #?(:cljs (.encodeURI js/window n)))
    ;:clj TODO ...

(defn string->uri-part
  ; @param (string) n
  ;
  ; @example
  ; (string->uri "my-domain.com/my path?my param")
  ; =>
  ; "my-domain.com%2Fmy%20path%3Fmy%20param"
  ;
  ; @return (string)
  [n]
  #?(:cljs (.encodeURIComponent js/window n)))
    ;:clj TODO ...

(defn uri<-query-string
  ; @param (string) uri
  ; @param (string) query-string
  ;
  ; @example
  ; (uri<-query-param "my-domain.com/my-path" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param"
  ;
  ; @example
  ; (uri<-query-param "my-domain.com/my-path" "my-param=my-value")
  ; =>
  ; "my-domain.com/my-path?my-param=my-value"
  ;
  ; @example
  ; (uri<-query-param "my-domain.com/my-path#my-fragment" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param#my-fragment"
  ;
  ; @example
  ; (uri<-query-param "my-domain.com/my-path?my-param" "your-param=your-value")
  ; =>
  ; "my-domain.com/my-path?my-param&your-param=your-value"
  ;
  ; @return (string)
  [uri query-string]
  (let [fragment     (uri->fragment uri)
        query-string (if-let [x (uri->query-string uri)] (str x "&" query-string) query-string)
        ; Szükséges eltávolítani a duplikációkat!
        query-string (-> query-string query-string->query-params query-params->query-string)]
       (str (-> uri (string/before-first-occurence "?" {:return? true})
                    (string/before-first-occurence "#" {:return? true}))
            (if (string/nonempty? query-string) (str "?" query-string))
            (if (string/nonempty? fragment)     (str "#" fragment)))))

(defn path->match-template?
  ; @param (string) path
  ; @param (string) template
  ;
  ; @example
  ; (path->match-template? "/my-path/my-value" "/my-path/:my-param")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [path template]
  (let [path-parts           (string/split path     #"/")
        template-parts       (string/split template #"/")
        path-parts-count     (count path-parts)
        template-parts-count (count template-parts)]
       (letfn [(f [dex] (cond ; Ha a dex nagyobb, mint a template-parts vektor elemeinek száma, ...
                              (= dex template-parts-count)
                              ; ... akkor a path-parts és template-parts vektorok elemeinek
                              ; összeillesztése sikeres volt.
                              (return true)
                              ; Ha a vizsgált template-part első karaktere ":", ...
                              (= ":" (str (get-in template-parts [dex 0])))
                              ; ... akkor a vizsgált path-part értéke egy path-param, aminek
                              ; nem szükséges egyeznie, ezért átlép a következő iterációba.
                              (f (inc dex))
                              ; Ha a vizsgált path-part és template-part értéke megegyezik, ...
                              (= (get path-parts     dex)
                                 (get template-parts dex))
                              ; ... akkor átlép a következő iterációba.
                              (f (inc dex))))]
              (boolean (if ; Ha a path-parts és template-parts vektorok elemeinek száma megegyezik ...
                           (= path-parts-count template-parts-count)
                           (f 0))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn valid-uri
  ; @param (string) uri
  ;
  ; @example
  ; (valid-uri "my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (valid-uri "my-domain.com/")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (valid-uri "http://my-domain.com")
  ; =>
  ; "http://my-domain.com"
  ;
  ; @return (string)
  [uri]
  (let [protocol (uri->protocol uri)]
       (if (string/nonempty? protocol)
           (string/not-ends-with! uri "/")
           (str protocol "https://" (string/not-ends-with! uri "/")))))

(defn valid-path
  ; @param (string) path
  ;
  ; @example
  ; (valid-path "my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (valid-path "/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (valid-path "/my-path/")
  ; =>
  ; "/my-path"
  ;
  ; @return (string)
  [path]   ; 1.
  (-> path (string/not-ends-with! "/")
           ; 2.
           (string/starts-with!   "/")))
