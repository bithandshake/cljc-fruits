
### http.api

Functional documentation of the http.api isomorphic namespace

---

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > http.api

### Index

- [capitalize-header](#capitalize-header)

- [css-wrap](#css-wrap)

- [default-wrap](#default-wrap)

- [error-wrap](#error-wrap)

- [html-wrap](#html-wrap)

- [json-wrap](#json-wrap)

- [local-request?](#local-request)

- [media-wrap](#media-wrap)

- [redirect-wrap](#redirect-wrap)

- [remote-request?](#remote-request)

- [request->cookie](#request-cookie)

- [request->cookies](#request-cookies)

- [request->form-param](#request-form-param)

- [request->form-params](#request-form-params)

- [request->header](#request-header)

- [request->ip-address](#request-ip-address)

- [request->multipart-param](#request-multipart-param)

- [request->multipart-params](#request-multipart-params)

- [request->param](#request-param)

- [request->params](#request-params)

- [request->path-param](#request-path-param)

- [request->path-params](#request-path-params)

- [request->query-string](#request-query-string)

- [request->route-path](#request-route-path)

- [request->route-path](#request-route-path)

- [request->session](#request-session)

- [request->session-param](#request-session-param)

- [request->session-params](#request-session-params)

- [request->transit-param](#request-transit-param)

- [request->transit-params](#request-transit-params)

- [request->uri](#request-uri)

- [request->user-agent](#request-user-agent)

- [response->client-error?](#response-client-error)

- [response->info?](#response-info)

- [response->redirected?](#response-redirected)

- [response->server-error?](#response-server-error)

- [response->success?](#response-success)

- [status->client-error?](#status-client-error)

- [status->info?](#status-info)

- [status->redirected?](#status-redirected)

- [status->server-error?](#status-server-error)

- [status->success?](#status-success)

- [text-wrap](#text-wrap)

- [xml-wrap](#xml-wrap)

---

### capitalize-header

```
@param (keyword) header-key
```

```
@usage
(capitalize-header :user-agent)
```

```
@example
(capitalize-header :user-agent)
=>
"User-Agent"
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn capitalize-header
  [header-key]
  (as-> header-key % (name %)
                     (clojure.string/split % #"\-")
                     (map clojure.string/capitalize %)
                     (clojure.string/join "-" %)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [capitalize-header]]))

(http.api/capitalize-header ...)
(capitalize-header          ...)
```

</details>

---

### css-wrap

```
@param (map) response-props
{:body (string)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(css-wrap {:body ".class {}"})
=>
{:body    ".class {}"
 :headers {"Content-Type" "text/css"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn css-wrap
  [{:keys [body] :as response-props}]
  (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                       (merge {:mime-type "text/css" :status 200} %)
                                       (update % :body str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [css-wrap]]))

(http.api/css-wrap ...)
(css-wrap          ...)
```

</details>

---

### default-wrap

```
@param (map) response-props
{:body (string)
 :headers (map)(opt)
 :mime-type (string)(opt)
  Default: "text/plain"
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
@param (map)(opt) options
{:allowed-errors (vector)(opt)
  If the {:hide-errors? true} setting is passed, values in the 'allowed-errors'
  vector are allowed as response body.
 :hide-errors? (boolean)(opt)
  Replaces the body with an unsensitive value ('":client-error"' or '":server-error"')
  in case of client error (4**) or server error (5**) status code is passed.
  Default: false}
```

```
@example
(default-wrap {:body "My text"})
=>
{:body    "My text"
 :headers {"Content-Type" "text/plain"}
 :status  200}
```

```
@example
(default-wrap {:body      "My text"
               :headers   {"Content-Disposition" "inline"}
               :mime-type "text/plain"})
=>
{:body    "My text"
 :headers {"Content-Type"        "text/plain"
           "Content-Disposition" "inline"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn default-wrap
  ([response-props]
   (default-wrap response-props {}))

  ([{:keys [body mime-type session status] :as response-props :or {mime-type "text/plain" status 200}}
    {:keys [allowed-errors hide-errors?]}]
   (cond-> response-props :select-keys           (select-keys     [:body :headers :session :status])
                          :use-default-mime-type (map/assoc-in-or [:headers "Content-Type"] mime-type)
                          :use-default-status    (map/assoc-in-or [:status] status)

                          (and hide-errors? (not (utils/error-allowed? body allowed-errors))) (utils/unsensitive-body)

                          (nil? session) (dissoc :session))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [default-wrap]]))

(http.api/default-wrap ...)
(default-wrap          ...)
```

</details>

---

### error-wrap

```
@param (map) response-props
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 500}
@param (map)(opt) options
{:allowed-errors (vector)(opt)
  If the {:hide-errors? true} setting is passed, values in the 'allowed-errors'
  vector are allowed as response body.
 :hide-errors? (boolean)(opt)
  Replaces the body with an unsensitive value ('":client-error"' or '":server-error"')
  in case of client error (4**) or server error (5**) status code is passed.
  Default: false}
```

```
@example
(error-wrap {:body   :file-not-found
             :status 404})
=>
{:body    ":file-not-found"
 :headers {"Content-Type" "text/plain"}
 :status  404}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn error-wrap
  ([response-props]
   (error-wrap response-props {}))

  ([{:keys [body] :as response-props} options]
   (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                        (merge {:mime-type "text/plain" :status 500} %)
                                        (update % :body str))
                 options)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [error-wrap]]))

(http.api/error-wrap ...)
(error-wrap          ...)
```

</details>

---

### html-wrap

```
@param (map) response-props
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(html-wrap {:body "<!DOCTYPE html> ..."})
=>
{:body    "<!DOCTYPE html> ..."
 :headers {"Content-Type" "text/html"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn html-wrap
  [{:keys [body] :as response-props}]
  (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                       (merge {:mime-type "text/html" :status 200} %)
                                       (update % :body str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [html-wrap]]))

(http.api/html-wrap ...)
(html-wrap          ...)
```

</details>

---

### json-wrap

```
@param (map) response-props
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(json-wrap {:body "{...}"})
=>
{:body    "{...}"
 :headers {"Content-Type" "application/json"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn json-wrap
  [{:keys [body] :as response-props}]
  (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                       (merge {:mime-type "application/json" :status 200} %)
                                       (update % :body str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [json-wrap]]))

(http.api/json-wrap ...)
(json-wrap          ...)
```

</details>

---

### local-request?

```
@description
Checks if a request is local based on the server-name attribute, returning
TRUE if it is 'localhost' and FALSE otherwise.
```

```
@param (map) request
{:server-name (string)}
```

```
@usage
(local-request? {...})
```

```
@example
(local-request? {:server-name "localhost" ...})
=>
true
```

```
@example
(local-request? {:server-name "hostname.com" ...})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn local-request?
  [{:keys [server-name]}]
  (= server-name "localhost"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [local-request?]]))

(http.api/local-request? ...)
(local-request?          ...)
```

</details>

---

### media-wrap

```
@param (map) response-props
{:body (java.io.File object)
 :filename (string)(opt)
 :mime-type (string)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(media-wrap {:body      #object[java.io.File 0x4571e67a "/my-file.png"
             :mime-type "image/png"})
=>
{:body    #object[java.io.File 0x4571e67a "/my-file.png"
 :headers {"Content-Type" "image/png"}
 :status  200}
```

```
@example
(media-wrap {:body      #object[java.io.File 0x4571e67a "/my-file.png"
             :filename  "my-file.png"
             :mime-type "image/png"})
=>
{:body    #object[java.io.File 0x4571e67a "/my-file.png"
 :headers {"Content-Type"        "image/png"
           "Content-Disposition" "inline; filename=\"my-file.png\""}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn media-wrap
  [{:keys [body filename] :as response-props}]
  (let [headers (if filename {"Content-Disposition" "inline       (default-wrap (as-> response-props % (select-keys % [:body :mime-type :session :status])
                                            (merge {:headers headers :status 200} %)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [media-wrap]]))

(http.api/media-wrap ...)
(media-wrap          ...)
```

</details>

---

### redirect-wrap

```
@param (map) response-props
{:location (string)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 302}
```

```
@example
(redirect-wrap {:location "/my-page"
                :status   303})
=>
{:headers {"Content-Type" "text/plain"
           "Location"     "/my-page"}
 :status  303}
```

```
@return (map)
{:headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn redirect-wrap
  [{:keys [location] :as response-props}]
  (default-wrap (as-> response-props % (select-keys % [:session :status])
                                       (merge {:headers {"Location" location} :status 302} %)
                                       (update % :body str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [redirect-wrap]]))

(http.api/redirect-wrap ...)
(redirect-wrap          ...)
```

</details>

---

### remote-request?

```
@description
Checks if a request is remote based on the server-name attribute, returning
TRUE if it is not 'localhost' and FALSE if it is.
```

```
@param (map) request
{:server-name (string)}
```

```
@usage
(remote-request? {...})
```

```
@example
(remote-request? {:server-name "hostname.com" ...})
=>
true
```

```
@example
(remote-request? {:server-name "localhost" ...})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn remote-request?
  [{:keys [server-name]}]
  (not= server-name "localhost"))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [remote-request?]]))

(http.api/remote-request? ...)
(remote-request?          ...)
```

</details>

---

### request->cookie

```
@param (map) request
{:cookies (map)}
@param (string) cookie-id
```

```
@usage
(request->cookies {...} "my-cookie")
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->cookie
  [request cookie-id]
  (get-in request [:cookies cookie-id]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->cookie]]))

(http.api/request->cookie ...)
(request->cookie          ...)
```

</details>

---

### request->cookies

```
@param (map) request
{:cookies (map)}
```

```
@usage
(request->cookies {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->cookies
  [request]
  (:cookies request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->cookies]]))

(http.api/request->cookies ...)
(request->cookies          ...)
```

</details>

---

### request->form-param

```
@param (map) request
{:form-params (map)}
@param (string) element-name
```

```
@usage
(request->form-param {...} "my-element")
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->form-param
  [request element-name]
  (-> request :form-params (get element-name)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->form-param]]))

(http.api/request->form-param ...)
(request->form-param          ...)
```

</details>

---

### request->form-params

```
@param (map) request
{:form-params (map)}
```

```
@usage
(request->form-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->form-params
  [request]
  (:form-params request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->form-params]]))

(http.api/request->form-params ...)
(request->form-params          ...)
```

</details>

---

### request->header

```
@param (map) request
@param (keyword) header-key
```

```
@usage
(request->header {...} :user-agent)
```

```
@example
(request->header {:headers {:user-agent "My User Agent" ...} ...}
                 :user-agent)
=>
"My User Agent"
```

```
@example
(request->header {:headers {"user-agent" "My User Agent" ...} ...}
                 :user-agent)
=>
"My User Agent"
```

```
@example
(request->header {:headers {"User-Agent" "My User Agent" ...} ...}
                 :user-agent)
=>
"My User Agent"
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->header
  [{:keys [headers]} header-key]
  (or (->> headers header-key)
      (->> header-key name                         (get headers))
      (->> header-key name utils/capitalize-header (get headers))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->header]]))

(http.api/request->header ...)
(request->header          ...)
```

</details>

---

### request->ip-address

```
@param (map) request
```

```
@usage
(request->ip-address {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->ip-address
  [request]
  (letfn [(fallback-f []  (if (check/local-request? request) "127.0.0.1"))
          (split-f    [%] (clojure.string/split % #",\s"))]
         (if-let [x-forwarded-for (request->header request :x-forwarded-for)]
                 (-> x-forwarded-for split-f first)
                 (fallback-f))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->ip-address]]))

(http.api/request->ip-address ...)
(request->ip-address          ...)
```

</details>

---

### request->multipart-param

```
@param (map) request
{:multipart-params (map)}
@param (keyword) param-key
```

```
@usage
(request->multipart-param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->multipart-param
  [request param-key]
  (get-in request [:multipart-params param-key]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->multipart-param]]))

(http.api/request->multipart-param ...)
(request->multipart-param          ...)
```

</details>

---

### request->multipart-params

```
@param (map) request
{:multipart-params (map)}
```

```
@usage
(request->multipart-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->multipart-params
  [request]
  (:multipart-params request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->multipart-params]]))

(http.api/request->multipart-params ...)
(request->multipart-params          ...)
```

</details>

---

### request->param

```
@param (map) request
@param (keyword) param-key
```

```
@usage
(request->param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->param
  [request param-key]
  (get-in request [:params param-key]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->param]]))

(http.api/request->param ...)
(request->param          ...)
```

</details>

---

### request->params

```
@param (map) request
{:params (map)}
```

```
@usage
(request->params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->params
  [request]
  (:params request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->params]]))

(http.api/request->params ...)
(request->params          ...)
```

</details>

---

### request->path-param

```
@param (map) request
@param (keyword) param-key
```

```
@usage
(request->path-param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->path-param
  [request param-key]
  (get-in request [:path-params param-key]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->path-param]]))

(http.api/request->path-param ...)
(request->path-param          ...)
```

</details>

---

### request->path-params

```
@param (map) request
{:path-params (map)}
```

```
@usage
(request->path-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->path-params
  [request]
  (:path-params request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->path-params]]))

(http.api/request->path-params ...)
(request->path-params          ...)
```

</details>

---

### request->query-string

```
@param (map) request
{:query-string (string)}
```

```
@usage
(request->query-string {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->query-string
  [request]
  (:query-string request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->query-string]]))

(http.api/request->query-string ...)
(request->query-string          ...)
```

</details>

---

### request->route-path

```
@param (map) request
{:uri (string)}
```

```
@usage
(request->route-path {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->route-path
  [{:keys [uri]}]
  (str uri))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->route-path]]))

(http.api/request->route-path ...)
(request->route-path          ...)
```

</details>

---

### request->route-path

```
@param (map) request
{:uri (string)}
```

```
@usage
(request->route-path {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->route-path
  [{:keys [uri]}]
  (str uri))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->route-path]]))

(http.api/request->route-path ...)
(request->route-path          ...)
```

</details>

---

### request->session

```
@param (map) request
{:session (map)}
```

```
@usage
(request->session {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->session
  [request]
  (:session request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->session]]))

(http.api/request->session ...)
(request->session          ...)
```

</details>

---

### request->session-param

```
@param (map) request
@param (keyword) param-key
```

```
@usage
(request->session-param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->session-param
  [request param-key]
  (get-in request [:session param-key]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->session-param]]))

(http.api/request->session-param ...)
(request->session-param          ...)
```

</details>

---

### request->session-params

```
@param (map) request
{:session (map)}
```

```
@usage
(request->session-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->session-params
  [request]
  (request->session request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->session-params]]))

(http.api/request->session-params ...)
(request->session-params          ...)
```

</details>

---

### request->transit-param

```
@param (map) request
@param (keyword) param-key
```

```
@usage
(request->transit-param {...} :my-param)
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn request->transit-param
  [request param-key]
  (get-in request [:transit-params param-key]))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->transit-param]]))

(http.api/request->transit-param ...)
(request->transit-param          ...)
```

</details>

---

### request->transit-params

```
@param (map) request
{:transit-params (map)}
```

```
@usage
(request->transit-params {...})
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn request->transit-params
  [request]
  (:transit-params request))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->transit-params]]))

(http.api/request->transit-params ...)
(request->transit-params          ...)
```

</details>

---

### request->uri

```
@param (map) request
{:server-name (string)
 :uri (string)}
```

```
@usage
(request->uri {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->uri
  [{:keys [server-name uri]}]
  (str server-name uri))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->uri]]))

(http.api/request->uri ...)
(request->uri          ...)
```

</details>

---

### request->user-agent

```
@param (map) request
```

```
@usage
(request->user-agent {...})
```

```
@return (string)
```

<details>
<summary>Source code</summary>

```
(defn request->user-agent
  [request]
  (request->header request :user-agent))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [request->user-agent]]))

(http.api/request->user-agent ...)
(request->user-agent          ...)
```

</details>

---

### response->client-error?

```
@param (integer) response
```

```
@usage
(response->client-error? {:body "..." :status 429})
```

```
@example
(response->client-error? {:body "..." :status 429})
=>
true
```

```
@example
(response->client-error? {:body "..." :status 500})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response->client-error?
  [response]
  (-> response :status status/status->client-error?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response->client-error?]]))

(http.api/response->client-error? ...)
(response->client-error?          ...)
```

</details>

---

### response->info?

```
@param (integer) response
```

```
@usage
(response->info? {:body "..." :status 100})
```

```
@example
(response->info? {:body "..." :status 100})
=>
true
```

```
@example
(response->info? {:body "..." :status 200})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response->info?
  [response]
  (-> response :status status/status->info?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response->info?]]))

(http.api/response->info? ...)
(response->info?          ...)
```

</details>

---

### response->redirected?

```
@param (integer) response
```

```
@usage
(response->redirected? {:body "..." :status 301})
```

```
@example
(response->redirected? {:body "..." :status 301})
=>
true
```

```
@example
(response->redirected? {:body "..." :status 404})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response->redirected?
  [response]
  (-> response :status status/status->redirected?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response->redirected?]]))

(http.api/response->redirected? ...)
(response->redirected?          ...)
```

</details>

---

### response->server-error?

```
@param (integer) response
```

```
@usage
(response->server-error? {:body "..." :status 500})
```

```
@example
(response->server-error? {:body "..." :status 500})
=>
true
```

```
@example
(response->server-error? {:body "..." :status 404})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response->server-error?
  [response]
  (-> response :status status/status->server-error?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response->server-error?]]))

(http.api/response->server-error? ...)
(response->server-error?          ...)
```

</details>

---

### response->success?

```
@param (integer) response
```

```
@usage
(response->success? {:body "..." :status 200})
```

```
@example
(response->success? {:body "..." :status 200})
=>
true
```

```
@example
(response->success? {:body "..." :status 404})
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn response->success?
  [response]
  (-> response :status status/status->success?))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response->success?]]))

(http.api/response->success? ...)
(response->success?          ...)
```

</details>

---

### status->client-error?

```
@param (integer or string) status
```

```
@usage
(status->client-error? 429)
```

```
@example
(status->client-error? 429)
=>
true
```

```
@example
(status->client-error? 500)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn status->client-error?
  [status]
  (-> status str first str (= "4")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [status->client-error?]]))

(http.api/status->client-error? ...)
(status->client-error?          ...)
```

</details>

---

### status->info?

```
@param (integer or string) status
```

```
@usage
(status->info? 100)
```

```
@example
(status->info? 100)
=>
true
```

```
@example
(status->info? 200)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn status->info?
  [status]
  (-> status str first str (= "1")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [status->info?]]))

(http.api/status->info? ...)
(status->info?          ...)
```

</details>

---

### status->redirected?

```
@param (integer or string) status
```

```
@usage
(status->redirected? 301)
```

```
@example
(status->redirected? 301)
=>
true
```

```
@example
(status->redirected? 404)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn status->redirected?
  [status]
  (-> status str first str (= "3")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [status->redirected?]]))

(http.api/status->redirected? ...)
(status->redirected?          ...)
```

</details>

---

### status->server-error?

```
@param (integer or string) status
```

```
@usage
(status->server-error? 500)
```

```
@example
(status->server-error? 500)
=>
true
```

```
@example
(status->server-error? 404)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn status->server-error?
  [status]
  (-> status str first str (= "5")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [status->server-error?]]))

(http.api/status->server-error? ...)
(status->server-error?          ...)
```

</details>

---

### status->success?

```
@param (integer or string) status
```

```
@usage
(status->success? 200)
```

```
@example
(status->success? 200)
=>
true
```

```
@example
(status->success? 404)
=>
false
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn status->success?
  [status]
  (-> status str first str (= "2")))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [status->success?]]))

(http.api/status->success? ...)
(status->success?          ...)
```

</details>

---

### text-wrap

```
@param (map) response-props
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
@param (map)(opt) options
{:allowed-errors (vector)(opt)
  If the {:hide-errors? true} setting is passed, values in the 'allowed-errors'
  vector are allowed as response body.
 :hide-errors? (boolean)(opt)
  Replaces the body with an unsensitive value ('":client-error"' or '":server-error"')
  in case of client error (4**) or server error (5**) status code is passed.
  Default: false}
```

```
@example
(text-wrap {:body "My text"})
=>
{:body    "My text"
 :headers {"Content-Type" "text/plain"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn text-wrap
  ([response-props]
   (text-wrap response-props {}))

  ([{:keys [body] :as response-props} options]
   (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                        (merge {:mime-type "text/plain" :status 200} %)
                                        (update % :body str))
                 options)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [text-wrap]]))

(http.api/text-wrap ...)
(text-wrap          ...)
```

</details>

---

### xml-wrap

```
@param (map) response-props
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(xml-wrap {:body "<?xml version="1.0" ...?>"})
=>
{:body    "<?xml version="1.0" ...?>"
 :headers {"Content-Type" "application/xml"}
 :status  200}
```

```
@return (map)
{:body (string)
 :headers (map)
 :session (map)
 :status (integer)}
```

<details>
<summary>Source code</summary>

```
(defn xml-wrap
  [{:keys [body] :as response-props}]
  (default-wrap (as-> response-props % (select-keys % [:body :session :status])
                                       (merge {:mime-type "application/xml" :status 200} %)
                                       (update % :body str))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [xml-wrap]]))

(http.api/xml-wrap ...)
(xml-wrap          ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

