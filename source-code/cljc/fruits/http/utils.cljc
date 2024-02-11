
(ns fruits.http.utils
    (:require [clojure.string]
              [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn capitalize-header
  ; @description
  ; Returns the given 'header-key' value as a capitalized string.
  ;
  ; @param (keyword or string) header-key
  ;
  ; @usage
  ; (capitalize-header :user-agent)
  ; =>
  ; "User-Agent"
  ;
  ; @usage
  ; (capitalize-header "user-agent")
  ; =>
  ; "User-Agent"
  ;
  ; @return (string)
  [header-key]
  (letfn [(f0 [%]
              (as-> % % (clojure.string/split % #"\-")
                        (map clojure.string/capitalize %)
                        (clojure.string/join "-" %)))]
         (cond (-> header-key keyword?) (-> header-key name f0)
               (-> header-key string?)  (-> header-key      f0))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unsensitive-body
  ; @ignore
  ;
  ; @description
  ; Replaces the body value with an unsensitive keyword such as ':client-error' or ':server-error'
  ; in case of client or server error status code is provided.
  ;
  ; @param (map) response-props
  ; {:status (integer)}
  ;
  ; @usage
  ; (unsensitive-body {:body :file-not-found :status 404}
  ;                   {...})
  ; =>
  ; ":client-error"
  ;
  ; @usage
  ; (unsensitive-body {:body :unable-to-save-uploaded-file :status 500}
  ;                   {...})
  ; =>
  ; ":server-error"
  ;
  ; @usage
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
  ; Returns TRUE if the given 'body' is an error value that is present in the given 'allowed-errors' vector.
  ;
  ; @param (*) body
  ; @param (vector) allowed-errors
  ;
  ; @usage
  ; (error-allowed? :file-not-found [:file-not-found :permission-denied])
  ; =>
  ; true
  ;
  ; @usage
  ; (error-allowed? :file-not-found [:permission-denied])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [body allowed-errors]
  (letfn [(f0 [%] (= (str %) (str body)))]
         (some f0 allowed-errors)))
