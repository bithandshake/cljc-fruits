
(ns fruits.http.status
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn status->info?
  ; @description
  ; Returns TRUE if the given status code is '1**'.
  ;
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->info? 100)
  ; =>
  ; true
  ;
  ; @usage
  ; (status->info? 200)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "1")))

(defn status->success?
  ; @description
  ; Returns TRUE if the given status code is '2**'.
  ;
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->success? 200)
  ; =>
  ; true
  ;
  ; @usage
  ; (status->success? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "2")))

(defn status->redirected?
  ; @description
  ; Returns TRUE if the given status code is '3**'.
  ;
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->redirected? 301)
  ; =>
  ; true
  ;
  ; @usage
  ; (status->redirected? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "3")))

(defn status->client-error?
  ; @description
  ; Returns TRUE if the given status code is '4**'.
  ;
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->client-error? 429)
  ; =>
  ; true
  ;
  ; @usage
  ; (status->client-error? 500)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "4")))

(defn status->server-error?
  ; @description
  ; Returns TRUE if the given status code is '5**'.
  ;
  ; @param (integer or string) status
  ;
  ; @usage
  ; (status->server-error? 500)
  ; =>
  ; true
  ;
  ; @usage
  ; (status->server-error? 404)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [status]
  (-> status string/first-character (= "5")))
