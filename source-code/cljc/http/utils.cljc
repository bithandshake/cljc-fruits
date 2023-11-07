
(ns http.utils
    (:require [clojure.string]
              [string.api :as string]))

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
  ; (unsensitive-body {:body :file-not-found :status 404}
  ;                   {...})
  ;
  ; @example
  ; (unsensitive-body {:body :file-not-found :status 404}
  ;                   {...})
  ; =>
  ; ":client-error"
  ;
  ; @example
  ; (unsensitive-body {:body :unable-to-save-uploaded-file :status 500}
  ;                   {...})
  ; =>
  ; ":server-error"
  ;
  ; @example
  ; (unsensitive-body {:body :unable-to-save-uploaded-file :status 500}
  ;                   {:allowed-errors [:unable-to-save-uploaded-file]})
  ; =>
  ; ":server-error"
  ;
  ; @return (map)
  ; {:body (*)}
  [{:keys [body status] :as response-props}]
  (cond-> response-props (-> status string/first-character (= "4")) (assoc :body ":client-error")
                         (-> status string/first-character (= "5")) (assoc :body ":server-error")))

(defn error-allowed?
  ; @ignore
  ;
  ; @description
  ;
  ; @param (*) body
  ; @param (vector) allowed-errors
  ;
  ; @usage
  ; (error-allowed? :file-not-found [:file-not-found :permission-denied])
  ;
  ; @example
  ; (error-allowed? :file-not-found [:file-not-found :permission-denied])
  ; =>
  ; true
  ;
  ; @example
  ; (error-allowed? :file-not-found [:permission-denied])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [body allowed-errors]
  (letfn [(f [%] (= (str %)
                    (str body)))]
         (some f allowed-errors)))
