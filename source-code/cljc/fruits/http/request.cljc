
(ns fruits.http.request
    (:require [fruits.http.check :as check]
              [fruits.http.utils :as utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request->header
  ; @description
  ; Returns the header of the given request map.
  ;
  ; @param (map) request
  ; @param (keyword) header-key
  ;
  ; @usage
  ; (request->header {:headers {:user-agent "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @usage
  ; (request->header {:headers {"user-agent" "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @usage
  ; (request->header {:headers {"User-Agent" "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @return (*)
  [{:keys [headers]} header-key]
  ; In Clojure, header names in requests are typically represented as lowercase keywords.
  ; However, the actual capitalization of header names depends on the HTTP client
  ; or middleware you are using to handle the requests.
  ;
  ; When receiving an HTTP request in Clojure, the header names are usually normalized to lowercase keywords.
  ; E.g., the 'Host' header would be represented as ':host', the 'Content-Type' as ':content-type', and so on.
  (or (->> headers header-key)
      (->> header-key name                         (get headers))
      (->> header-key name utils/capitalize-header (get headers))))

(defn request->cookies
  ; @description
  ; Returns the cookies of the given request map.
  ;
  ; @param (map) request
  ; {:cookies (map)}
  ;
  ; @usage
  ; (request->cookies {...})
  ;
  ; @return (map)
  [request]
  (:cookies request))

(defn request->cookie
  ; @description
  ; Returns a specific cookie of the given request map.
  ;
  ; @param (map) request
  ; {:cookies (map)}
  ; @param (string) cookie-id
  ;
  ; @usage
  ; (request->cookies {...} "my-cookie")
  ;
  ; @return (map)
  [request cookie-id]
  (get-in request [:cookies cookie-id]))

(defn request->ip-address
  ; @description
  ; Returns the IP address of the given request map.
  ;
  ; @param (map) request
  ;
  ; @usage
  ; (request->ip-address {...})
  ;
  ; @return (string)
  [request]
  ; - The 'X-Forwarded-For' header is untrustworthy when no trusted reverse proxy
  ;   (e.g., a load balancer) is between the client and server.
  ;   If the client and all proxies are benign and well-behaved, then the list of
  ;   IP addresses in the header has the meaning described in the Directives section.
  ;   But if there's a risk the client or any proxy is malicious or misconfigured,
  ;   then it's possible any part (or the entirely) of the header may have been spoofed
  ;   (and may not be a list or contain IP addresses at all).
  ; - If you access "http://localhost" in the browser, the request will not be forwarded
  ;   to the internet through the router, but will instead remain in your own system.
  ;   Localhost has the IP address 127.0.0.1, which refers back to your own server.
  (letfn [(fallback-f []  (if (check/local-request? request) "127.0.0.1"))
          (split-f    [%] (clojure.string/split % #",\s"))]
         (if-let [x-forwarded-for (request->header request :x-forwarded-for)]
                 (-> x-forwarded-for split-f first)
                 (fallback-f))))

(defn request->user-agent
  ; @description
  ; Returns the user agent of the given request map.
  ;
  ; @param (map) request
  ;
  ; @usage
  ; (request->user-agent {...})
  ;
  ; @return (string)
  [request]
  (request->header request :user-agent))

(defn request->query-string
  ; @description
  ; Returns the query string of the given request map.
  ;
  ; @param (map) request
  ; {:query-string (string)}
  ;
  ; @usage
  ; (request->query-string {...})
  ;
  ; @return (string)
  [request]
  (:query-string request))

(defn request->form-params
  ; @description
  ; Returns the form params of the given request map.
  ;
  ; @param (map) request
  ; {:form-params (map)}
  ;
  ; @usage
  ; (request->form-params {...})
  ;
  ; @return (map)
  [request]
  (:form-params request))

(defn request->form-param
  ; @description
  ; Returns a specific form param of the given request map.
  ;
  ; @param (map) request
  ; {:form-params (map)}
  ; @param (string) element-name
  ;
  ; @usage
  ; (request->form-param {...} "my-element")
  ;
  ; @return (*)
  [request element-name]
  (-> request :form-params (get element-name)))

(defn request->path-params
  ; @description
  ; Returns the path params of the given request map.
  ;
  ; @param (map) request
  ; {:path-params (map)}
  ;
  ; @usage
  ; (request->path-params {...})
  ;
  ; @return (map)
  [request]
  (:path-params request))

(defn request->path-param
  ; @description
  ; Returns a specific path param of the given request map.
  ;
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (request->path-param {...} :my-param)
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:path-params param-key]))

(defn request->params
  ; @description
  ; Returns the params of the given request map.
  ;
  ; @param (map) request
  ; {:params (map)}
  ;
  ; @usage
  ; (request->params {...})
  ;
  ; @return (map)
  [request]
  (:params request))

(defn request->param
  ; @description
  ; Returns a specific param of the given request map.
  ;
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (request->param {...} :my-param)
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:params param-key]))

(defn request->transit-params
  ; @description
  ; Returns the transit params of the given request map.
  ;
  ; @param (map) request
  ; {:transit-params (map)}
  ;
  ; @usage
  ; (request->transit-params {...})
  ;
  ; @return (map)
  [request]
  (:transit-params request))

(defn request->transit-param
  ; @description
  ; Returns a specific transit param of the given request map.
  ;
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (request->transit-param {...} :my-param)
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:transit-params param-key]))

(defn request->multipart-params
  ; @description
  ; Returns the multipart params of the given request map.
  ;
  ; @param (map) request
  ; {:multipart-params (map)}
  ;
  ; @usage
  ; (request->multipart-params {...})
  ;
  ; @return (map)
  [request]
  (:multipart-params request))

(defn request->multipart-param
  ; @description
  ; Returns a specific multipart param of the given request map.
  ;
  ; @param (map) request
  ; {:multipart-params (map)}
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (request->multipart-param {...} :my-param)
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:multipart-params param-key]))

(defn request->session
  ; @description
  ; Returns the session of the given request map.
  ;
  ; @param (map) request
  ; {:session (map)}
  ;
  ; @usage
  ; (request->session {...})
  ;
  ; @return (map)
  [request]
  (:session request))

(defn request->session-params
  ; @description
  ; Returns the session params of the given request map.
  ;
  ; @param (map) request
  ; {:session (map)}
  ;
  ; @usage
  ; (request->session-params {...})
  ;
  ; @return (map)
  [request]
  (request->session request))

(defn request->session-param
  ; @description
  ; Returns a specific session param of the given request map.
  ;
  ; @param (map) request
  ; @param (keyword) param-key
  ;
  ; @usage
  ; (request->session-param {...} :my-param)
  ;
  ; @return (*)
  [request param-key]
  (get-in request [:session param-key]))

(defn request->route-path
  ; @description
  ; Returns the route path of the given request map.
  ;
  ; @param (map) request
  ; {:uri (string)}
  ;
  ; @usage
  ; (request->route-path {...})
  ;
  ; @return (string)
  [{:keys [uri]}]
  (str uri))

(defn request->uri
  ; @description
  ; Returns the URI of the given request map.
  ;
  ; @param (map) request
  ; {:server-name (string)
  ;  :uri (string)}
  ;
  ; @usage
  ; (request->uri {...})
  ;
  ; @return (string)
  [{:keys [server-name uri]}]
  (str server-name uri))
