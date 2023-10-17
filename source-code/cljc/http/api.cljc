
(ns http.api
    (:require [http.check    :as check]
              [http.request  :as request]
              [http.response :as response]
              [http.status   :as status]
              [http.utils    :as utils]
              [http.wrap     :as wrap]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; http.check
(def local-request?  check/local-request?)
(def remote-request? check/remote-request?)

; http.request
(def request->header           request/request->header)
(def request->cookies          request/request->cookies)
(def request->cookie           request/request->cookie)
(def request->ip-address       request/request->ip-address)
(def request->user-agent       request/request->user-agent)
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
(def request->session-params   request/request->session-params)
(def request->session-param    request/request->session-param)
(def request->route-path       request/request->route-path)
(def request->uri              request/request->uri)
(def request->route-path       request/request->route-path)

; http.response
(def response->info?         response/response->info?)
(def response->success?      response/response->success?)
(def response->redirected?   response/response->redirected?)
(def response->client-error? response/response->client-error?)
(def response->server-error? response/response->server-error?)

; http.status
(def status->info?         status/status->info?)
(def status->success?      status/status->success?)
(def status->redirected?   status/status->redirected?)
(def status->client-error? status/status->client-error?)
(def status->server-error? status/status->server-error?)

; http.utils
(def capitalize-header utils/capitalize-header)

; http.wrap
(def response-wrap wrap/response-wrap)
(def redirect-wrap wrap/redirect-wrap)
(def text-wrap     wrap/text-wrap)
(def error-wrap    wrap/error-wrap)
(def html-wrap     wrap/html-wrap)
(def json-wrap     wrap/json-wrap)
(def media-wrap    wrap/media-wrap)
(def xml-wrap      wrap/xml-wrap)
(def css-wrap      wrap/css-wrap)
