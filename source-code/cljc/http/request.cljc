
(ns http.request
    (:require [noop.api :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request->cookies
  ; @param (map) request
  ; {:cookies (map)}
  ;
  ; @return (map)
  [{:keys [cookies]}]
  (return cookies))

(defn request->cookie
  ; @param (map) request
  ; {:cookies (map)}
  ; @param (string) cookie-id
  ;
  ; @return (map)
  [request cookie-id]
  (get-in request [:cookies cookie-id]))

(defn request->query-string
  ; @param (map) request
  ; {:query-string (string)}
  ;
  ; @return (string)
  [{:keys [query-string]}]
  (return query-string))

(defn request->form-params
  ; @param (map) request
  ; {:form-params (map)}
  ;
  ; @return (map)
  [{:keys [form-params]}]
  (return form-params))

(defn request->form-param
  ; @param (map) request
  ; {:form-params (map)}
  ; @param (string) element-name
  ;
  ; @return (*)
  [request element-name]
  (-> request :form-params (get element-name)))

(defn request->path-params
  ; @param (map) request
  ; {:path-params (map)}
  ;
  ; @return (map)
  [{:keys [path-params]}]
  (return path-params))

(defn request->path-param
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:path-params param-key]))

(defn request->params
  ; @param (map) request
  ; {:params (map)}
  ;
  ; @return (map)
  [{:keys [params]}]
  (return params))

(defn request->param
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:params param-key]))

(defn request->transit-params
  ; @param (map) request
  ; {:transit-params (map)}
  ;
  ; @return (map)
  [{:keys [transit-params]}]
  (return transit-params))

(defn request->transit-param
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:transit-params param-key]))

(defn request->multipart-params
  ; @param (map) request
  ; {:multipart-params (map)}
  ;
  ; @return (map)
  [{:keys [multipart-params]}]
  (return multipart-params))

(defn request->multipart-param
  ; @param (map) request
  ; {:multipart-params (map)}
  ; @param (keyword) param-key
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:multipart-params param-key]))

(defn request->session
  ; @param (map) request
  ; {:session (map)}
  ;
  ; @return (map)
  [{:keys [session]}]
  (return session))

(defn request->session-param
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:session param-key]))

(defn request->route-path
  ; @param (map) request
  ; {:uri (string)}
  ;
  ; @return (string)
  [{:keys [uri]}]
  (str uri))

(defn request->uri
  ; @param (map) request
  ; {:server-name (string)
  ;  :uri (string)}
  ;
  ; @return (string)
  [{:keys [server-name uri]}]
  (str server-name uri))
