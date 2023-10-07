
(ns http.utils
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn capitalize-header
  ; @param (keyword) header-key
  ;
  ; @usage
  ; (capitalize-header :user-agent)
  ;
  ; @example
  ; (capitalize-header :user-agent)
  ; =>
  ; "User-Agent"
  ;
  ; @return (string)
  [header-key]
  (as-> header-key % (name %)
                     (clojure.string/split % #"\-")
                     (map clojure.string/capitalize %)
                     (clojure.string/join "-" %)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unsensitive-body
  ; @ignore
  ;
  ; @description
  ; Replaces the body value with an unsensitive keyword such as :client-error
  ; or :server-error in case of client or server error status code passed.
  ;
  ; @param (map) response-props
  ; {:status (integer)}
  ;
  ; @usage
  ; (unsensitive-body {:body :file-not-found :status 404})
  ;
  ; @example
  ; (unsensitive-body {:body :file-not-found :status 404})
  ; =>
  ; ":client-error"
  ;
  ; @example
  ; (unsensitive-body {:body :unable-to-save-uploaded-file :status 500})
  ; =>
  ; ":server-error"
  ;
  ; @return (map)
  ; {:body (*)}
  [{:keys [status] :as response-props}]
  (cond-> response-props (-> status str first str (= "4")) (assoc :body ":client-error")
                         (-> status str first str (= "5")) (assoc :body ":server-error")))
