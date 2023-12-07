
(ns fruits.http.response
    (:require [fruits.http.status :as status]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn response->info?
  ; @param (integer) response
  ;
  ; @usage
  ; (response->info? {:body "..." :status 100})
  ;
  ; @example
  ; (response->info? {:body "..." :status 100})
  ; =>
  ; true
  ;
  ; @example
  ; (response->info? {:body "..." :status 200})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [response]
  (-> response :status status/status->info?))

(defn response->success?
  ; @param (integer) response
  ;
  ; @usage
  ; (response->success? {:body "..." :status 200})
  ;
  ; @example
  ; (response->success? {:body "..." :status 200})
  ; =>
  ; true
  ;
  ; @example
  ; (response->success? {:body "..." :status 404})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [response]
  (-> response :status status/status->success?))

(defn response->redirected?
  ; @param (integer) response
  ;
  ; @usage
  ; (response->redirected? {:body "..." :status 301})
  ;
  ; @example
  ; (response->redirected? {:body "..." :status 301})
  ; =>
  ; true
  ;
  ; @example
  ; (response->redirected? {:body "..." :status 404})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [response]
  (-> response :status status/status->redirected?))

(defn response->client-error?
  ; @param (integer) response
  ;
  ; @usage
  ; (response->client-error? {:body "..." :status 429})
  ;
  ; @example
  ; (response->client-error? {:body "..." :status 429})
  ; =>
  ; true
  ;
  ; @example
  ; (response->client-error? {:body "..." :status 500})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [response]
  (-> response :status status/status->client-error?))

(defn response->server-error?
  ; @param (integer) response
  ;
  ; @usage
  ; (response->server-error? {:body "..." :status 500})
  ;
  ; @example
  ; (response->server-error? {:body "..." :status 500})
  ; =>
  ; true
  ;
  ; @example
  ; (response->server-error? {:body "..." :status 404})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [response]
  (-> response :status status/status->server-error?))
