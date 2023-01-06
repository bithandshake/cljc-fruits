
(ns uri.query
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn url-query-params->url-query-string
  ; @param (map) url-query-params
  ;
  ; @usage
  ; (url-query-params->url-query-string {:my-param "my-value"})
  ;
  ; @example
  ; (url-query-params->url-query-string {:my-param "my-value" :your-param nil})
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @example
  ; (url-query-params->url-query-string {"my-param" "my-value" "your-param" nil})
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @return (string)
  [url-query-params]
  (letfn [(f [o k v] (str o (if o "&")
                            (if (keyword? k)
                                (name     k)
                                (str      k))
                            (if v "=") v))]
         (reduce-kv f nil url-query-params)))

(defn url-query-string->url-query-params
  ; @param (string) url-query-string
  ;
  ; @usage
  ; (url-query-string->url-query-params "my-param=my-value")
  ;
  ; @example
  ; (url-query-string->url-query-params "my-param=my-value&your-param")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @return (map)
  [url-query-string]
  (letfn [(f [o x] (let [[k v] (string/split x #"=")]
                        (assoc o (keyword k) v)))]
         (reduce f {} (string/split url-query-string #"&"))))
