
(ns uri.convert
    (:require [candy.api  :refer [return]]
              [reader.api :as reader]
              [regex.api  :as regex :refer [re-match?]]
              [string.api :as string]
              [uri.config :as config]
              [uri.query  :as query]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-protocol
  ; @param (string) n
  ;
  ; @usage
  ; (to-protocol "https://my-domain.com/my-path")
  ;
  ; @example
  ; (to-protocol "https://my-domain.com/my-path")
  ; =>
  ; "https"
  ;
  ; @example
  ; (to-protocol "my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; The to-protocol function ...
  ; ... takes the first part of the n string which ends with the "://" part (including the "://"),
  ;     and if the taken part matches with the protocol pattern, cuts the trailing "://" part,
  ;     and converts the remaining protocol-name to a lowercase string.
  ; ... if the result is an empty string, converts it to nil.
  (as-> n % (string/to-first-occurence % "://" {:return? false})
            (if (re-match? % config/PROTOCOL-PATTERN)
                (-> % (string/not-ends-with! "://")
                      (string/to-lowercase)
                      (string/use-nil)))))

(defn to-domain
  ; @param (string) n
  ;
  ; @usage
  ; (to-domain "https://my-domain.com")
  ;
  ; @example
  ; (to-domain "https://my-domain.com/my-path")
  ; =>
  ; "my-domain.com"
  ;
  ; @return (string)
  [n]
  ; The to-domain function ...
  ; ... takes the part after the first "://" part (if possible),
  ;     then takes the part before the first "/" char (if possible),
  ;     and if the taken part matches with the strict domain pattern,
  ;     converts it to a lowercase string.
  ; ... if the result is an empty string, converts it to nil.
  (as-> n % (string/after-first-occurence  % "://" {:return? true})
            (string/before-first-occurence % "/"   {:return? true})
            (if (re-match? % config/STRICT-DOMAIN-PATTERN)
                (-> % (string/to-lowercase)
                      (string/use-nil)))))

(defn to-subdomain
  ; @param (string) n
  ;
  ; @usage
  ; (to-subdomain "https://subdomain.my-domain.com")
  ;
  ; @example
  ; (to-subdomain "https://subdomain.my-domain.com/my-path")
  ; =>
  ; "subdomain"
  ;
  ; @example
  ; (to-subdomain "https://my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
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
  ; (to-tld "https://my-domain.com/my-path")
  ; =>
  ; "com"
  ;
  ; @return (string)
  [n]
  (if-let [domain (to-domain n)]
          (string/after-last-occurence domain "." {:return? false})))

(defn to-tail
  ; @param (string) n
  ;
  ; @usage
  ; (to-tail "https://my-domain.com?my-param=my-value")
  ;
  ; @example
  ; (to-tail "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-param=my-value&your-param#my-fragment"
  ;
  ; @example
  ; (to-tail "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-tail "https://my-domain.com/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  (if (-> n (string/contains-part? "?"))
      (-> n (string/to-lowercase)
            (string/after-first-occurence "?" {:return? false}))
      (-> n (string/to-lowercase)
            (string/after-first-occurence "#" {:return? false}))))

(defn to-parent
  ; @param (string) n
  ;
  ; @usage
  ; (to-parent "https://my-domain.com/my-path")
  ;
  ; @example
  ; (to-parent "https://my-domain.com/my-path")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (to-parent "https://my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (to-parent "/my-path/your-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-parent "/my-path")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-parent "/")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-parent "my-path")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  (if (regex/starts-with? n config/DOMAIN-PATTERN)
      (-> n (string/to-lowercase)
            (string/before-last-occurence "/" {:return? true})
            (string/not-ends-with!        "/"))
      (-> n (string/to-lowercase)
            (string/before-last-occurence "/" {:return? false})
            (string/starts-with!          "/"))))

(defn to-relative
  ; @param (string) n
  ;
  ; @usage
  ; (to-relative "my-domain.com/my-path")
  ;
  ; @example
  ; (to-relative "my-domain.com/my-path?my-param#my-fragment")
  ; =>
  ; "/my-path?my-param#my-fragment"
  ;
  ; @example
  ; (to-relative "my-domain.com")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-relative "/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-relative "my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-relative "/")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  ; The to-relative function ...
  ; ... converts the n string to lowercase because the to-domain
  ;     function converts it's result to lowercase too!
  ; ... cuts the leading part of the string including the domain (if necessary).
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the leading slash (if necessary) (it must be after the trailing slash removing!).
  (let [n (string/to-lowercase n)]
       (if-let [domain (to-domain n)]
               (-> n (string/after-first-occurence domain)
                     (string/not-ends-with! "/")
                     (string/starts-with!   "/"))
               (-> n (string/not-ends-with! "/")
                     (string/starts-with!   "/")))))

(defn to-absolute
  ; @param (string) n
  ; @param (string) domain
  ;
  ; @usage
  ; (to-absolute "/my-path" "my-domain.com")
  ;
  ; @example
  ; (to-absolute "/my-path" "my-domain.com")
  ; =>
  ; "https://my-domain.com/my-path"
  ;
  ; @example
  ; (to-absolute "my-domain.com/my-path" "my-domain.com")
  ; =>
  ; "https://my-domain.com/my-path"
  ;
  ; @return (string)
  [n domain]
  ; The to-absolute function ...
  ; ... converts the n string to lowercase because the to-domain
  ;     function converts it's result to lowercase too!
  ; ... converts the domain string to lowercase because the function's result
  ;     must be a lowercase string and it may be used.
  ; ... prepends the given domain (if necessary).
  ; ... provides the slash after the prepended domain (if necessary).
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the protocol part (if necessary).
  (let [n      (string/to-lowercase n)
        domain (string/to-lowercase domain)]
       (if-let [absolute? (to-domain n)]
               (-> n (string/not-ends-with! "/")
                     (string/starts-with!   "https://"))
               (-> n (string/starts-with!   "/")
                     (string/prepend        domain)
                     (string/not-ends-with! "/")
                     (string/starts-with!   "https://")))))

(defn to-path
  ; @param (string) n
  ;
  ; @usage
  ; (to-path "https://my-domain.com/my-path")
  ;
  ; @example
  ; (to-path "https://my-domain.com/my-path")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-path "https://my-domain.com")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-path "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; "/my-path"
  ;
  ; @example
  ; (to-path "https://my-domain.com/?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @example
  ; (to-path "https://my-domain.com?my-param=my-value&your-param")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n]
  (-> n (to-relative)
        (string/before-first-occurence "?" {:return? true})
        (string/before-first-occurence "#" {:return? true})))

(defn to-path-params
  ; @param (string) n
  ; @param (string) template
  ;
  ; @usage
  ; (to-path-params "/my-path" "/:a")
  ;
  ; @example
  ; (to-path-params "https://my-domain.com/my-path/your-path" "/:a/:b")
  ; =>
  ; {:a "my-path" :b "your-path"}
  ;
  ; @example
  ; (to-path-params "https://my-domain.com/my-path/your-path" "/:a/b")
  ; =>
  ; {:a "my-path"}
  ;
  ; @example
  ; (to-path-params "/my-path/your-path" "/:a/:b")
  ; =>
  ; {:a "my-path" :b "your-path"}
  ;
  ; @example
  ; (to-path-params "/my-path/your-path" "/a/b")
  ; =>
  ; {}
  ;
  ; @return (map)
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

(defn to-fragment
  ; @param (string) n
  ;
  ; @usage
  ; (to-fragment "/my-path#my-fragment")
  ;
  ; @example
  ; (to-fragment "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @example
  ; (to-fragment "https://my-domain.com/my-path?my-param=my-value&your-param")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-fragment "/my-path#my-fragment")
  ; =>
  ; "my-fragment"
  ;
  ; @return (string)
  [n]
  (-> n (string/to-lowercase)
        (string/after-first-occurence "#" {:return? false})))

(defn to-query-string
  ; @param (string) n
  ;
  ; @usage
  ; (to-query-string "/my-path?my-param=my-value")
  ;
  ; @example
  ; (to-query-string "https://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @example
  ; (to-query-string "https://my-domain.com/my-path#my-fragment")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-query-string "/my-path?my-param=my-value")
  ; =>
  ; "my-param=my-value"
  ;
  ; @return (string)
  [n]
  ; The {:return? true} setting of the second step cause that the result
  ; of the step may be an empty string, so it is important to apply the use-nil
  ; function to prevent the function returns with an empty string!
  (-> n (string/to-lowercase)
        (string/after-first-occurence  "?" {:return? false})
        (string/before-first-occurence "#" {:return? true})
        (string/use-nil)))

(defn to-query-params
  ; @param (string) n
  ;
  ; @usage
  ; (to-query-params "/my-path?my-param=my-value")
  ;
  ; @example
  ; (to-query-params "http://my-domain.com/my-path?my-param=my-value&your-param#my-fragment")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @example
  ; (to-query-params "http://my-domain.com/my-path#my-fragment")
  ; =>
  ; {}
  ;
  ; @example
  ; (to-query-params "/my-path?my-param=my-value")
  ; =>
  ; {:my-param "my-value"}
  ;
  ; @return (map)
  [n]
  (-> n (to-query-string)
        (query/query-string->query-params)))

(defn to-encoded
  ; @param (string) n
  ; @param (map)(opt) options
  ;  {:strict? (boolean)(opt)
  ;    Default: false}
  ;
  ; @usage
  ; (to-encoded "my-domain.com/my path")
  ;
  ; @example
  ; (to-encoded "my-domain.com/my path?my param")
  ; =>
  ; "my-domain.com/my%20path?my%20param"
  ;
  ; @example
  ; (to-encoded "my-domain.com/my path?my param" {:strict? true})
  ; =>
  ; "my-domain.com%2Fmy%20path%3Fmy%20param"
  ;
  ; @return (string)
  ([n]
   (to-encoded n {}))

  ([n {:keys [strict?]}]
   #?(:cljs (if strict? (.encodeURIComponent js/window n)
                        (.encodeURI          js/window n)))))
     ;:clj TODO
