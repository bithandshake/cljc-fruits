
(ns fruits.uri.use
    (:require [fruits.string.api  :as string]
              [fruits.uri.convert :as convert]
              [fruits.uri.query   :as query]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn use-url-query-string
  ; @param (string) n
  ; @param (string) url-query-string
  ;
  ; @usage
  ; (use-url-query-string "my-domain.com/my-path" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param"
  ;
  ; @usage
  ; (use-url-query-string "my-domain.com/my-path" "my-param=my-value")
  ; =>
  ; "my-domain.com/my-path?my-param=my-value"
  ;
  ; @usage
  ; (use-url-query-string "my-domain.com/my-path#my-fragment" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param#my-fragment"
  ;
  ; @usage
  ; (use-url-query-string "my-domain.com/my-path?my-param" "another-param=another-value")
  ; =>
  ; "my-domain.com/my-path?my-param&another-param=another-value"
  ;
  ; @return (string)
  [n url-query-string]
  (letfn [; If the 'n' string already contains a query-string it will be
          ; prepended to the 'url-query-string' parameter and both query strings
          ; will be used up in the URL.
          (use-original [] (if-let [% (convert/to-url-query-string n)]
                                   (str % "&" url-query-string)
                                   (str       url-query-string)))

          ; Because of prepending the original query string from the 'n' string,
          ; it's important to remove duplicates.
          ; The easiest way to removing duplicates is that to convert the query
          ; string to a map and then convert it back to a string.
          (remove-duplicates [%] (-> % query/url-query-string->url-query-params
                                       query/url-query-params->url-query-string))]

         ; ...
         (let [url-fragment     (convert/to-url-fragment n)
               url-query-string (-> (use-original)
                                    (remove-duplicates))]
              (str (-> n (string/before-first-occurence "?" {:return? true})
                         (string/before-first-occurence "#" {:return? true}))
                   (if (string/not-empty? url-query-string) (str "?" url-query-string))
                   (if (string/not-empty? url-fragment)     (str "#" url-fragment))))))
