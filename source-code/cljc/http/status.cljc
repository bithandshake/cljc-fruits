
(ns http.status
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn status->info?
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->info? 100)
  ;
  ; @example
  ; (status->info? 100)
  ; =>
  ; true
  ;
  ; @example
  ; (status->info? 200)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "1")))

(defn status->success?
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->success? 200)
  ;
  ; @example
  ; (status->success? 200)
  ; =>
  ; true
  ;
  ; @example
  ; (status->success? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "2")))

(defn status->redirected?
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->redirected? 301)
  ;
  ; @example
  ; (status->redirected? 301)
  ; =>
  ; true
  ;
  ; @example
  ; (status->redirected? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "3")))

(defn status->client-error?
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->client-error? 429)
  ;
  ; @example
  ; (status->client-error? 429)
  ; =>
  ; true
  ;
  ; @example
  ; (status->client-error? 500)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "4")))

(defn status->server-error?
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->server-error? 500)
  ;
  ; @example
  ; (status->server-error? 500)
  ; =>
  ; true
  ;
  ; @example
  ; (status->server-error? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "5")))
