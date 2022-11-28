
(ns uri.core
    (:require [string.api  :as string]
              [uri.convert :as convert]
              [uri.query   :as query]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn use-query-string
  ; @param (string) n
  ; @param (string) query-string
  ;
  ; @usage
  ; (use-query-string "my-domain.com/my-path" "my-param")
  ;
  ; @example
  ; (use-query-string "my-domain.com/my-path" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param"
  ;
  ; @example
  ; (use-query-string "my-domain.com/my-path" "my-param=my-value")
  ; =>
  ; "my-domain.com/my-path?my-param=my-value"
  ;
  ; @example
  ; (use-query-string "my-domain.com/my-path#my-fragment" "my-param")
  ; =>
  ; "my-domain.com/my-path?my-param#my-fragment"
  ;
  ; @example
  ; (use-query-string "my-domain.com/my-path?my-param" "your-param=your-value")
  ; =>
  ; "my-domain.com/my-path?my-param&your-param=your-value"
  ;
  ; @return (string)
  [uri query-string]
  (letfn [(remove-duplicates [%] (-> % query/query-string->query-params query/query-params->query-string))]
         (let [fragment     (convert/to-fragment uri)
               query-string (if-let [% (convert/to-query-string uri)] (str % "&" query-string) query-string)
               query-string (remove-duplicates query-string)]
              (str (-> uri (string/before-first-occurence "?" {:return? true})
                           (string/before-first-occurence "#" {:return? true}))
                   (if (string/nonblank? query-string) (str "?" query-string))
                   (if (string/nonblank? fragment)     (str "#" fragment))))))
