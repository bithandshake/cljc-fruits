
# http.api isomorphic namespace

##### [README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > http.api

### Index

- [capitalize-header](#capitalize-header)

- [css-wrap](#css-wrap)

- [html-wrap](#html-wrap)

- [json-wrap](#json-wrap)

- [local-request?](#local-request)

- [media-wrap](#media-wrap)

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

- [request->transit-param](#request-transit-param)

- [request->transit-params](#request-transit-params)

- [request->uri](#request-uri)

- [request->user-agent](#request-user-agent)

- [response-wrap](#response-wrap)

- [text-wrap](#text-wrap)

- [xml-wrap](#xml-wrap)

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
{:body (*)
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(css-wrap {:body "foo"})
=>
{:body    "foo"
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
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/css"
                         :status    200}
                        (select-keys response-props [:session :status]))))
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
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/html"
                         :status    200}
                        (select-keys response-props [:session :status]))))
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
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/json"
                         :status    200}
                        (select-keys response-props [:session :status]))))
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
  (response-wrap (merge {:body body
                         :headers (if filename {"Content-Disposition" "inline                         :status  200}
                        (select-keys response-props [:mime-type :session :status]))))
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

### remote-request?

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
  [{:keys [cookies]}]
  (return cookies))
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
  [{:keys [form-params]}]
  (return form-params))
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
  [{:keys [multipart-params]}]
  (return multipart-params))
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
  [{:keys [params]}]
  (return params))
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
  [{:keys [path-params]}]
  (return path-params))
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
  [{:keys [query-string]}]
  (return query-string))
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
  [{:keys [session]}]
  (return session))
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
  [{:keys [transit-params]}]
  (return transit-params))
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

### response-wrap

```
@param (map) response-props
{:body (string)
 :headers (map)(opt)
 :mime-type (string)(opt)
  Default: "text/plain"
 :session (map)(opt)
 :status (integer)(opt)
  Default: 200}
```

```
@example
(response-wrap {:body "foo"})
=>
{:body    "foo"
 :headers {"Content-Type" "text/plain"}
 :status  200}
```

```
@example
(response-wrap {:body      "foo"
                :headers   {"Content-Disposition" "inline"}
                :mime-type "text/plain"})
=>
{:body    "foo"
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
(defn response-wrap
  [{:keys [headers mime-type] :as response-props :or {mime-type "text/plain"}}]
  (let [headers (merge {"Content-Type" (or mime-type "text/plain")} headers)]
       (merge {:headers headers :status 200}
              (select-keys response-props [:body :session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :refer [response-wrap]]))

(http.api/response-wrap ...)
(response-wrap          ...)
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
```

```
@example
(text-wrap {:body "foo"})
=>
{:body    "foo"
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
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/plain"
                         :status    200}
                        (select-keys response-props [:session :status]))))
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
(xml-wrap {:body "foo"})
=>
{:body    "foo"
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
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/xml"
                         :status    200}
                        (select-keys response-props [:session :status]))))
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

This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.

