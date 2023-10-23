
(ns uri.convert
    (:require [reader.api :as reader]
              [regex.api  :refer [re-match]]
              [string.api :as string]
              [uri.config :as config]
              [uri.query  :as query]))

;; -- URI functions -----------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-lowercase
  ; @param (string) n
  ;
  ; @usage
  ; (to-lowercase "Https://My-domain.com")
  ;
  ; @example
  ; (to-lowercase "Https://My-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (to-lowercase "Https://My-domain.com/My-path?My-query#My-fragment")
  ; =>
  ; "https://my-domain.com/My-path?My-query#My-fragment"
  ;
  ; @return (string)
  [n]
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; Schemes and hostnames are case-insensitive.
  (cond
        ; If the URI contains a double-slash ("http://my-domain.com/my-path"),
        ; the path part starts from the 3rd "/" character.
        (and (string/contains-part? n "//")
             (string/nth-dex-of     n "/" 3))
        (let [dex (string/nth-dex-of n "/" 3)]
             (str (-> n (string/part 0 dex)
                        (string/to-lowercase))
                  (-> n (string/part dex))))

        ; If the URI does NOT contain a double-slash ("my-domain.com/my-path"),
        ; the path part starts from the 1st "/" character.
        (string/contains-part? n "/")
        (str (-> n (string/before-first-occurence "/" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "/" {:return? false})))

        ; If the URI does not contain path part ...
        (string/contains-part? n "?")
        (str (-> n (string/before-first-occurence "?" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "?" {:return? false})))

        ; If the URI does not contain path part ...
        (string/contains-part? n "#")
        (str (-> n (string/before-first-occurence "#" {:return? false})
                   (string/to-lowercase))
             (-> n (string/from-first-occurence   "#" {:return? false})))))

(defn to-scheme
  ; @param (string) n
  ;
  ; @usage
  ; (to-scheme "https://my-domain.com")
  ;
  ; @example
  ; (to-scheme "https://my-domain.com")
  ; =>
  ; "https"
  ;
  ; @example
  ; (to-scheme "mailto:johndoe@my-domain.com")
  ; =>
  ; "mailto"
  ;
  ; @example
  ; (to-scheme "ftp://user@my-domain.com")
  ; =>
  ; "ftp"
  ;
  ; @example
  ; (to-scheme "my-domain.com:80")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; ...
  ;
  ; BUG#6610 (source-code/cljc/uri/config.cljc)
  ;
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; ... schemes are case-insensitive, the canonical form is lowercase and documents
  ;     that specify schemes must do so with lowercase letters.
  (-> (string/before-first-occurence n ":" {:return? false})
      (re-match config/STRICT-SCHEME-PATTERN)
      (string/to-lowercase)))

(defn to-nonschemed
  ; @param (string) n
  ;
  ; @usage
  ; (to-nonschemed "https://my-domain.com")
  ;
  ; @example
  ; (to-nonschemed "https://my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-nonschemed "mailto:johndoe@my-domain.com")
  ; =>
  ; "johndoe@my-domain.com"
  ;
  ; @example
  ; (to-nonschemed "ftp://user@my-domain.com")
  ; =>
  ; "user@my-domain.com"
  ;
  ; @example
  ; (to-nonschemed "my-domain.com:80")
  ; =>
  ; "my-domain.com:80"
  ;
  ; @return (string)
  [n]
  ; The 'to-nonschemed' function removes the scheme part of the URI, the ":"
  ; character right after the scheme part and the "//" part (if necessary).
  ; By removing the scheme part and its ":" character from the URI, it makes
  ; easier to determine the meaning of the other ":" characters.
  ;
  ; BUG#6610 (source-code/cljc/uri/config.cljc)
  (if-let [scheme (to-scheme n)]
          (-> n (string/after-first-occurence ":"  {:return? false})
                (string/after-first-occurence "//" {:return? true}))
          (-> n)))

(defn to-hostname
  ; @param (string) n
  ;
  ; @usage
  ; (to-hostname "https://my-domain.com")
  ;
  ; @example
  ; (to-hostname "https://my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-hostname "https://www.sub.my-domain.com:80/my-path")
  ; =>
  ; "www.sub.my-domain.com"
  ;
  ; @example
  ; (to-hostname "mailto:johndoe@my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-hostname "ftp://user@my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-hostname "http://192.0.2.16:80/my-path")
  ; =>
  ; "192.0.2.16"
  ;
  ; @example
  ; (to-hostname "/my-path?my-query#my-fragment")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; ...
  ;
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; The host subcomponent is case-insensitive.
  (-> (to-nonschemed n)
      (string/after-first-occurence  "@"  {:return? true})
      (string/before-first-occurence ":"  {:return? true})
      (string/before-first-occurence "/"  {:return? true})
      (string/before-first-occurence "?"  {:return? true})
      (string/before-first-occurence "#"  {:return? true})
      (string/to-lowercase)))

(defn to-port
  ; @param (string) n
  ;
  ; @usage
  ; (to-port "https://my-domain.com:80")
  ;
  ; @example
  ; (to-port "https://my-domain.com:80")
  ; =>
  ; "80"
  ;
  ; @example
  ; (to-port "https://my-domain.com")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; ...
  (-> (to-nonschemed n)
      (string/after-first-occurence  ":" {:return? false})
      (string/before-first-occurence "/" {:return? true})
      (string/before-first-occurence "?" {:return? true})
      (string/before-first-occurence "#" {:return? true})
      (re-match config/PORT-PATTERN)))

(defn to-domain
  ; @param (string) n
  ;
  ; @usage
  ; (to-domain "https://my-domain.com")
  ;
  ; @example
  ; (to-domain "https://my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-domain "https://www.subs.my-domain.com:80/my-path")
  ; =>
  ; "sub.my-domain.com"
  ;
  ; @example
  ; (to-domain "mailto:johndoe@my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-domain "ftp://user@my-domain.com")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-domain "my-domain.com:80")
  ; =>
  ; "my-domain.com"
  ;
  ; @example
  ; (to-domain "192.0.2.16:80")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-domain "/my-path?my-query#my-fragment")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; The host subcomponent is case-insensitive.
  ;
  ; Converting to lowercase must done before cutting the "www." part, because
  ; the 'n' string might contains an uppercase "WWW."!
  ;
  ; The '@' character might be placed before the domain as user information
  ; or might be placed in the query part as a special character.
  ; "ftp://user@my-domain.com"
  ; "https://my-domain.com?my-query=@hello"
  ; Cutting the part before the first "@" character has to happen after the
  ; tail has been removed!
  (-> (to-nonschemed n)
      (string/to-lowercase)
      (string/after-first-occurence  "www." {:return? true})
      (string/before-first-occurence "/"    {:return? true})
      (string/before-first-occurence "?"    {:return? true})
      (string/before-first-occurence "#"    {:return? true})
      (string/after-first-occurence  "@"    {:return? true})
      (re-match config/DOMAIN-PATTERN)))

(defn to-subdomain
  ; @param (string) n
  ;
  ; @usage
  ; (to-subdomain "https://sub.my-domain.com")
  ;
  ; @example
  ; (to-subdomain "https://sub.my-domain.com")
  ; =>
  ; "sub"
  ;
  ; @example
  ; (to-subdomain "https://my-domain.com")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; The host subcomponent is case-insensitive.
  (if-let [domain (to-domain n)]
          (if (-> domain (string/min-occurence?         "." 2))
              (-> domain (string/before-first-occurence ".")
                         (string/to-lowercase)
                         (string/use-nil)))))

(defn to-tld
  ; @param (string) n
  ;
  ; @usage
  ; (to-tld "https://my-domain.com")
  ;
  ; @example
  ; (to-tld "https://my-domain.com")
  ; =>
  ; "com"
  ;
  ; @return (string)
  [n]
  ; https://www.rfc-editor.org/rfc/rfc3986
  ; The host subcomponent is case-insensitive.
  (if-let [domain (to-domain n)]
          (string/after-last-occurence domain "." {:return? false})))

;; -- URL functions -----------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-url-tail
  ; @param (string) n
  ;
  ; @usage
  ; (to-url-tail "https://my-domain.com?my-query")
  ;
  ; @example
  ; (to-url-tail "https://my-domain.com/my-path?my-query#my-fragment")
  ; =>
  ; "my-query#my-fragment"
  ;
  ; @example
  ; (to-url-tail "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-url-tail "https://my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  (if (string/contains-part?        n "?")
      (string/after-first-occurence n "?" {:return? false})
      (string/after-first-occurence n "#" {:return? false})))

(defn to-parent-url
  ; @param (string) n
  ;
  ; @usage
  ; (to-parent-url "https://my-domain.com/my-path")
  ;
  ; @example
  ; (to-parent-url "https://my-domain.com/my-path")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (to-parent-url "https://my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (to-parent-url "/my-path/your-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-parent-url "/my-path")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-parent-url "/")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-parent-url "my-path")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  ; ...
  ; It might be useful if the domain, scheme and other case-insensitive parts
  ; would be converted to lowercase like in other functions.
  (if-let [domain (to-domain n)]
          (-> n (string/not-ends-with!        "/")
                (string/before-last-occurence "/" {:return? true}))
          (-> n (string/not-ends-with!        "/")
                (string/before-last-occurence "/" {:return? false})
                (string/starts-with!          "/"))))

(defn to-relative-url
  ; @param (string) n
  ;
  ; @usage
  ; (to-relative-url "my-domain.com/my-path")
  ;
  ; @example
  ; (to-relative-url "my-domain.com/my-path")
  ; =>
  ; "/my-path?my-param#my-fragment"
  ;
  ; @example
  ; (to-relative-url "my-domain.com/my-path?my-query#my-fragment")
  ; =>
  ; "/my-path?my-query#my-fragment"
  ;
  ; @example
  ; (to-relative-url "my-domain.com")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-relative-url "/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-relative-url "my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-relative-url "/")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-relative-url "")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  ; The 'to-relative-url' function ...
  ; ... cuts the leading part of the string including the domain (if necessary).
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the leading slash (if necessary) (it must be after the trailing slash removing!).
  (if-let [domain (to-domain n)]
          (-> n (to-lowercase)
                (string/after-first-occurence domain {:return? false})
                (string/not-ends-with! "/")
                (string/starts-with!   "/"))
          (-> n (to-lowercase)
                (string/not-ends-with! "/")
                (string/starts-with!   "/"))))

(defn to-absolute-url
  ; @param (string) n
  ; @param (string) domain
  ;
  ; @usage
  ; (to-absolute-url "/my-path" "my-domain.com")
  ;
  ; @example
  ; (to-absolute-url "/my-path" "my-domain.com")
  ; =>
  ; "https://my-domain.com/my-path"
  ;
  ; @example
  ; (to-absolute-url "your-domain.com/my-path" "my-domain.com")
  ; =>
  ; "https://your-domain.com/my-path"
  ;
  ; @return (string)
  [n domain]
  ; The 'to-absolute-url' function ...
  ; ... prepends the given 'domain' (if necessary).
  ; ... puts a slash after the prepended domain (if necessary).
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the protocol part (if necessary).
  (if-let [absolute-url? (to-domain n)]
          (-> n (string/not-ends-with! "/")
                (to-lowercase)
                (string/starts-with!   "https://"))
          (-> n (string/starts-with!   "/")
                (string/prepend        domain)
                (to-lowercase)
                (string/not-ends-with! "/")
                (string/starts-with!   "https://"))))

(defn to-url-path
  ; @param (string) n
  ;
  ; @usage
  ; (to-url-path "https://my-domain.com/my-path")
  ;
  ; @example
  ; (to-url-path "https://my-domain.com/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-url-path "https://my-domain.com")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-url-path "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-url-path "https://my-domain.com/?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-url-path "https://my-domain.com?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  (-> n (to-relative-url)
        (string/before-first-occurence "?" {:return? true})
        (string/before-first-occurence "#" {:return? true})))

(defn to-url-path-params
  ; @param (string) n
  ; @param (string) url-path-template
  ;
  ; @usage
  ; (to-url-path-params "/my-path" "/:a")
  ;
  ; @example
  ; (to-url-path-params "/my-path" "/:a")
  ; =>
  ; {:a "my-path"}
  ;
  ; @example
  ; (to-url-path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
  ; =>
  ; {:a "my-path" :b "your-path"}
  ;
  ; @example
  ; (to-url-path-params "https://my-domain.com/my-path/your-path" "/:a/b")
  ; =>
  ; {:a "my-path"}
  ;
  ; @example
  ; (to-url-path-params "/my-path/your-path" "/a/b")
  ; =>
  ; {}
  ;
  ; @return (map)
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

(defn to-url-fragment
  ; @param (string) n
  ;
  ; @usage
  ; (to-url-fragment "/my-path#my-fragment")
  ;
  ; @example
  ; (to-url-fragment "/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-url-fragment "https://my-domain.com/my-path?my-query#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-url-fragment "/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-url-fragment "https://my-domain.com/my-path?my-query")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; https://en.wikipedia.org/wiki/URI_fragment
  ; In URIs, a hash mark # introduces the optional fragment near the end of the URL.
  (string/after-first-occurence n "#" {:return? false}))

(defn to-url-query-string
  ; @param (string) n
  ;
  ; @usage
  ; (to-url-query-string "/my-path?my-query")
  ;
  ; @example
  ; (to-url-query-string "/my-path?my-query")
  ; =>
  ; "my-query"
  ;
  ; @example
  ; (to-url-query-string "https://my-domain.com/my-path?my-query#my-fragment")
  ; =>
  ; "my-query"
  ;
  ; @example
  ; (to-url-query-string "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; The {:return? true} setting of the second step cause that the result
  ; of the step might be an empty string, so it is important to apply the 'use-nil'
  ; function to prevent the function returns with an empty string!
  (-> n (string/after-first-occurence  "?" {:return? false})
        (string/before-first-occurence "#" {:return? true})
        (string/use-nil)))

(defn to-url-query-params
  ; @param (string) n
  ;
  ; @usage
  ; (to-url-query-params "/my-path?my-query")
  ;
  ; @example
  ; (to-url-query-params "/my-path?my-query")
  ; =>
  ; {:my-query nil}
  ;
  ; @example
  ; (to-url-query-params "/my-path?my-param=my-value")
  ; =>
  ; {:my-param "my-value"}
  ;
  ; @example
  ; (to-url-query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @example
  ; (to-url-query-params "http://my-domain.com/my-path#my-fragment")
  ; =>
  ; {}
  ;
  ; @return (map)
  [n]
  (-> n (to-url-query-string)
        (query/url-query-string->url-query-params)))

(defn to-encoded-url
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:strict? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (to-encoded-url "my-domain.com/my path")
  ;
  ; @example
  ; (to-encoded-url "my-domain.com/my path?my param")
  ; =>
  ; "my-domain.com/my%20path?my%20param"
  ;
  ; @example
  ; (to-encoded-url "my-domain.com/my path?my param" {:strict? true})
  ; =>
  ; "my-domain.com%2Fmy%20path%3Fmy%20param"
  ;
  ; @return (string)
  ([n]
   (to-encoded-url n {}))

  ([n {:keys [strict?]}]
   #?(:cljs (if strict? (.encodeURIComponent js/window n)
                        (.encodeURI          js/window n)))))
     ;:clj TODO
