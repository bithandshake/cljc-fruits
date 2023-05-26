
(ns http.request
    (:require [http.check :as check]
              [http.utils :as utils]
              [noop.api   :refer [return]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request->header
  ; @param (map) request
  ; @param (keyword) header-key
  ;
  ; @usage
  ; (request->header {...} :user-agent)
  ;
  ; @example
  ; (request->header {:headers {:user-agent "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @example
  ; (request->header {:headers {"user-agent" "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @example
  ; (request->header {:headers {"User-Agent" "My User Agent" ...} ...}
  ;                  :user-agent)
  ; =>
  ; "My User Agent"
  ;
  ; @return (*)
  [{:keys [headers]} header-key]
  ; In Clojure, the header names in requests are typically represented as lowercase keywords.
  ; However, the actual capitalization of header names depends on the HTTP client
  ; or middleware you are using to handle the requests.
  ;
  ; When receiving an HTTP request in Clojure, the header names are usually normalized
  ; to lowercase keywords.
  ; For example, the Host header would be represented as :host, Content-Type as :content-type, and so on.
  (or (->> headers header-key)
      (->> header-key name                         (get headers))
      (->> header-key name utils/capitalize-header (get headers))))

(defn request->cookies
  ; @param (map) request
  ; {:cookies (map)}
  ;
  ; @usage
  ; (request->cookies {...})
  ;
  ; @return (map)
  [{:keys [cookies]}]
  (return cookies))

(defn request->cookie
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
  ; @param (map) request
  ;
  ; @usage
  ; (request->ip-address {...})
  ;
  ; @return (string)
  [request]
  ; The X-Forwarded-For header is untrustworthy when no trusted reverse proxy
  ; (e.g., a load balancer) is between the client and server.
  ; If the client and all proxies are benign and well-behaved, then the list of
  ; IP addresses in the header has the meaning described in the Directives section.
  ; But if there's a risk the client or any proxy is malicious or misconfigured,
  ; then it's possible any part (or the entirety) of the header may have been spoofed
  ; (and may not be a list or contain IP addresses at all).
  ;
  ; If you acces "http://localhost" in the browser, the request will not be forwarded
  ; to the internet through the router, but will instead remain in your own system.
  ; Localhost has the IP address 127.0.0.1, which refers back to your own server.
  (letfn [(fallback-f []  (if (check/local-request? request) "127.0.0.1"))
          (split-f    [%] (clojure.string/split % #",\s"))]
         (if-let [x-forwarded-for (request->header request :x-forwarded-for)]
                 (-> x-forwarded-for split-f first)
                 (fallback-f))))

(defn request->user-agent
  ; @param (map) request
  ;
  ; @usage
  ; (request->user-agent {...})
  ;
  ; @return (string)
  [request]
  (request->header request :user-agent))

(defn request->query-string
  ; @param (map) request
  ; {:query-string (string)}
  ;
  ; @usage
  ; (request->query-string {...})
  ;
  ; @return (string)
  [{:keys [query-string]}]
  (return query-string))

(defn request->form-params
  ; @param (map) request
  ; {:form-params (map)}
  ;
  ; @usage
  ; (request->form-params {...})
  ;
  ; @return (map)
  [{:keys [form-params]}]
  (return form-params))

(defn request->form-param
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
  ; @param (map) request
  ; {:path-params (map)}
  ;
  ; @usage
  ; (request->path-params {...})
  ;
  ; @return (map)
  [{:keys [path-params]}]
  (return path-params))

(defn request->path-param
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
  ; @param (map) request
  ; {:params (map)}
  ;
  ; @usage
  ; (request->params {...})
  ;
  ; @return (map)
  [{:keys [params]}]
  (return params))

(defn request->param
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
  ; @param (map) request
  ; {:transit-params (map)}
  ;
  ; @usage
  ; (request->transit-params {...})
  ;
  ; @return (map)
  [{:keys [transit-params]}]
  (return transit-params))

(defn request->transit-param
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
  ; @param (map) request
  ; {:multipart-params (map)}
  ;
  ; @usage
  ; (request->multipart-params {...})
  ;
  ; @return (map)
  [{:keys [multipart-params]}]
  (return multipart-params))

(defn request->multipart-param
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
  ; @param (map) request
  ; {:session (map)}
  ;
  ; @usage
  ; (request->session {...})
  ;
  ; @return (map)
  [{:keys [session]}]
  (return session))

(defn request->session-param
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
