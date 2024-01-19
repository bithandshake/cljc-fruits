
(ns fruits.http.api
    (:require [fruits.http.check    :as check]
              [fruits.http.request  :as request]
              [fruits.http.response :as response]
              [fruits.http.status   :as status]
              [fruits.http.utils    :as utils]
              [fruits.http.wrap     :as wrap]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (fruits.http.check/*)
(def local-request?  check/local-request?)
(def remote-request? check/remote-request?)

; @redirect (fruits.http.request/*)
(def request->headers          request/request->headers)
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

; @redirect (fruits.http.response/*)
(def response->info?         response/response->info?)
(def response->success?      response/response->success?)
(def response->redirected?   response/response->redirected?)
(def response->client-error? response/response->client-error?)
(def response->server-error? response/response->server-error?)

; @redirect (fruits.http.status/*)
(def status->info?         status/status->info?)
(def status->success?      status/status->success?)
(def status->redirected?   status/status->redirected?)
(def status->client-error? status/status->client-error?)
(def status->server-error? status/status->server-error?)

; @redirect (fruits.http.utils/*)
(def capitalize-header utils/capitalize-header)

; @redirect (fruits.http.wrap/*)
(def default-wrap  wrap/default-wrap)
(def redirect-wrap wrap/redirect-wrap)
(def text-wrap     wrap/text-wrap)
(def error-wrap    wrap/error-wrap)
(def html-wrap     wrap/html-wrap)
(def json-wrap     wrap/json-wrap)
(def media-wrap    wrap/media-wrap)
(def xml-wrap      wrap/xml-wrap)
(def css-wrap      wrap/css-wrap)
