
(ns http.api
    (:require [http.request :as request]
              [http.wrap    :as wrap]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; http.request
(def request->cookies          request/request->cookies)
(def request->cookie           request/request->cookie)
(def request->query-string     request/request->query-string)
(def request->form-params      request/request->form-params)
(def request->form-param       request/request->form-param)
(def request->path-params      request/request->path-params)
(def request->path-param       request/request->path-param)
(def request->params           request/request->params)
(def request->param            request/request->param)
(def request->transit-params   request/request->transit-params)
(def request->transit-param    request/request->transit-param)
(def request->multipart-params request/request->multipart-params)
(def request->multipart-param  request/request->multipart-param)
(def request->session          request/request->session)
(def request->session-param    request/request->session-param)
(def request->route-path       request/request->route-path)
(def request->uri              request/request->uri)
(def request->route-path       request/request->route-path)

; http.wrap
(def response-wrap wrap/response-wrap)
(def redirect-wrap wrap/redirect-wrap)
(def error-wrap    wrap/error-wrap)
(def html-wrap     wrap/html-wrap)
(def json-wrap     wrap/json-wrap)
(def map-wrap      wrap/map-wrap)
(def media-wrap    wrap/media-wrap)
(def xml-wrap      wrap/xml-wrap)
(def text-wrap     wrap/text-wrap)
