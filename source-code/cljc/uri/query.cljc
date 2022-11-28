
(ns uri.query
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn query-params->query-string
  ; @param (map) query-params
  ;
  ; @usage
  ; (query-params->query-string {:my-param "my-value"})
  ;
  ; @example
  ; (query-params->query-string {:my-param "my-value" :your-param nil})
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @example
  ; (query-params->query-string {"my-param" "my-value" "your-param" nil})
  ; =>
  ; "my-param=my-value&your-param"
  ;
  ; @return (string)
  [query-params]
  (letfn [(f [o k v] (str o (if o "&")
                            (if (keyword? k)
                                (name     k)
                                (str      k))
                            (if v "=") v))]
         (reduce-kv f nil query-params)))

(defn query-string->query-params
  ; @param (string) query-string
  ;
  ; @usage
  ; (query-string->query-params "my-param=my-value")
  ;
  ; @example
  ; (query-string->query-params "my-param=my-value&your-param")
  ; =>
  ; {:my-param "my-value" :your-param nil}
  ;
  ; @return (map)
  [query-string]
  (let [query-string (string/to-lowercase query-string)]
       (letfn [(f [o x] (let [[k v] (string/split x #"=")]
                             (assoc o (keyword k) v)))]
              (reduce f {} (string/split query-string #"&")))))
