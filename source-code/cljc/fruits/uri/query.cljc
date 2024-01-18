
(ns fruits.uri.query
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn url-query-params->url-query-string
  ; @param (map) url-query-params
  ;
  ; @usage
  ; (url-query-params->url-query-string {:my-param "my-value" :another-param nil})
  ; =>
  ; "my-param=my-value&another-param"
  ;
  ; @usage
  ; (url-query-params->url-query-string {"my-param" "my-value" "another-param" nil})
  ; =>
  ; "my-param=my-value&another-param"
  ;
  ; @return (string)
  [url-query-params]
  (letfn [(f0 [result k v]
              (str result (if result "&")
                          (if (keyword? k)
                              (name     k)
                              (str      k))
                          (if v "=") v))]
         (reduce-kv f0 nil url-query-params)))

(defn url-query-string->url-query-params
  ; @param (string) url-query-string
  ;
  ; @usage
  ; (url-query-string->url-query-params "my-param=my-value&another-param")
  ; =>
  ; {:my-param "my-value" :another-param nil}
  ;
  ; @return (map)
  [url-query-string]
  (letfn [(f0 [result x]
              (let [[k v] (string/split x #"=")]
                   (assoc result (keyword k) v)))]
         (reduce f0 {} (string/split url-query-string #"&"))))
