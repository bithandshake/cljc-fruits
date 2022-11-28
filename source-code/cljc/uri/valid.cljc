
(ns uri.valid
    (:require [candy.api   :refer [return]]
              [string.api  :as string]
              [uri.convert :as convert]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn valid-uri
  ; @param (string) n
  ;
  ; @usage
  ; (valid-uri "my-domain.com")
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
  ; @example
  ; (valid-uri "/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; The valid-uri function ...
  ; ... checks if the n string contains a valid domain.
  ; ... converts the value to a lowercase string.
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the protocol (if necessary).
  (if-let [domain (convert/to-domain n)]
          (if-let [protocol (convert/to-protocol n)]
                  (-> n (string/to-lowercase)
                        (string/not-ends-with! "/"))
                  (-> n (string/to-lowercase)
                        (string/not-ends-with! "/")
                        (string/starts-with!   "https://")))))

(defn valid-domain
  ; @param (string) n
  ;
  ; @usage
  ; (valid-domain "my-domain.com")
  ;
  ; @example
  ; (valid-domain "my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (valid-domain "my-domain.com/")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (valid-domain "http://my-domain.com")
  ; =>
  ; "http://my-domain.com"
  ;
  ; @example
  ; (valid-domain "my-domain.com?my-param")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @example
  ; (valid-domain "/my-path")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n]
  ; The valid-domain function ...
  ; ... checks if the n string contains a valid domain.
  ; ... converts the value to a lowercase string.
  ; ... removes the trailing slash (if necessary).
  ; ... prepends the protocol (if necessary).
  ; ... removes the query-string (if necessary).
  ; ... removes the fragment (if necessary).
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

(defn valid-path
  ; @param (string) n
  ;
  ; @usage
  ; (valid-path "/my-path")
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
  [n]
  ; The valid-path function ...
  ; ... takes the path from the given n string.
  ; ... if given string not contains path, returns with the root path ("/").
  (if-let [path (convert/to-path n)]
          (return path)
          (return "/")))
