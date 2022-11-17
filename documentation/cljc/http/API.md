
# <strong>http.api</strong> namespace
<p>Documentation of the <strong>http/api.cljc</strong> file</p>

<strong>[README](../../../README.md) > [DOCUMENTATION](../../COVER.md) > http.api</strong>



### html-wrap

```
@param (map) response-props
{:body (string)
 :session (map)(opt)
 :status (integer)(opt)}
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
                         :mime-type "text/html"}
                        (select-keys response-props [:session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [html-wrap]]))

(http/html-wrap ...)
(html-wrap      ...)
```

</details>

---

### json-wrap

```
@param (map) response-props
{:body (string)
 :session (map)(opt)
 :status (integer)(opt)}

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
                         :mime-type "application/json"}
                        (select-keys response-props [:session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [json-wrap]]))

(http/json-wrap ...)
(json-wrap      ...)
```

</details>

---

### map-wrap

```
@param (map) response-props
{:body (map)
 :session (map)(opt)
 :status (integer)(opt)}
```

```
@example
(map-wrap {:body {...})
=>
{:body    "{...}"
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
(defn map-wrap
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body (str body)}
                        (select-keys response-props [:session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [map-wrap]]))

(http/map-wrap ...)
(map-wrap      ...)
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
 :status (integer)(opt)}
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
                         :headers (if filename {"Content-Disposition" "inline                        (select-keys response-props [:mime-type :session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [media-wrap]]))

(http/media-wrap ...)
(media-wrap      ...)
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
(ns my-namespace (:require [http.api :as http :refer [request->cookie]]))

(http/request->cookie ...)
(request->cookie      ...)
```

</details>

---

### request->cookies

```
@param (map) request
{:cookies (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->cookies]]))

(http/request->cookies ...)
(request->cookies      ...)
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
(ns my-namespace (:require [http.api :as http :refer [request->form-param]]))

(http/request->form-param ...)
(request->form-param      ...)
```

</details>

---

### request->form-params

```
@param (map) request
{:form-params (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->form-params]]))

(http/request->form-params ...)
(request->form-params      ...)
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
(ns my-namespace (:require [http.api :as http :refer [request->multipart-param]]))

(http/request->multipart-param ...)
(request->multipart-param      ...)
```

</details>

---

### request->multipart-params

```
@param (map) request
{:multipart-params (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->multipart-params]]))

(http/request->multipart-params ...)
(request->multipart-params      ...)
```

</details>

---

### request->param

```
@param (map) request
@param (keyword) param-key
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
(ns my-namespace (:require [http.api :as http :refer [request->param]]))

(http/request->param ...)
(request->param      ...)
```

</details>

---

### request->params

```
@param (map) request
{:params (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->params]]))

(http/request->params ...)
(request->params      ...)
```

</details>

---

### request->path-param

```
@param (map) request
@param (keyword) param-key
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
(ns my-namespace (:require [http.api :as http :refer [request->path-param]]))

(http/request->path-param ...)
(request->path-param      ...)
```

</details>

---

### request->path-params

```
@param (map) request
{:path-params (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->path-params]]))

(http/request->path-params ...)
(request->path-params      ...)
```

</details>

---

### request->query-string

```
@param (map) request
{:query-string (string)}
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
(ns my-namespace (:require [http.api :as http :refer [request->query-string]]))

(http/request->query-string ...)
(request->query-string      ...)
```

</details>

---

### request->route-path

```
@param (map) request
{:uri (string)}
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
(ns my-namespace (:require [http.api :as http :refer [request->route-path]]))

(http/request->route-path ...)
(request->route-path      ...)
```

</details>

---

### request->route-path

```
@param (map) request
{:uri (string)}
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
(ns my-namespace (:require [http.api :as http :refer [request->route-path]]))

(http/request->route-path ...)
(request->route-path      ...)
```

</details>

---

### request->session

```
@param (map) request
{:session (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->session]]))

(http/request->session ...)
(request->session      ...)
```

</details>

---

### request->session-param

```
@param (map) request
@param (keyword) param-key
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
(ns my-namespace (:require [http.api :as http :refer [request->session-param]]))

(http/request->session-param ...)
(request->session-param      ...)
```

</details>

---

### request->transit-param

```
@param (map) request
@param (keyword) param-key
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
(ns my-namespace (:require [http.api :as http :refer [request->transit-param]]))

(http/request->transit-param ...)
(request->transit-param      ...)
```

</details>

---

### request->transit-params

```
@param (map) request
{:transit-params (map)}
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
(ns my-namespace (:require [http.api :as http :refer [request->transit-params]]))

(http/request->transit-params ...)
(request->transit-params      ...)
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
(ns my-namespace (:require [http.api :as http :refer [request->uri]]))

(http/request->uri ...)
(request->uri      ...)
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
  [{:keys [headers mime-type] :as response-props}]
  (let [headers (merge {"Content-Type" (or mime-type "text/plain")} headers)]
       (merge {:headers headers :status 200}
              (select-keys response-props [:body :session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [response-wrap]]))

(http/response-wrap ...)
(response-wrap      ...)
```

</details>

---

### text-wrap

```
@param (map) response-props
{:body (string)
 :session (map)(opt)
 :status (integer)(opt)}
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
                         :mime-type "text/plain"}
                        (select-keys response-props [:session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [text-wrap]]))

(http/text-wrap ...)
(text-wrap      ...)
```

</details>

---

### xml-wrap

```
@param (map) response-props
{:body (string)
 :session (map)(opt)
 :status (integer)(opt)}
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
                         :mime-type "application/xml"}
                        (select-keys response-props [:session :status]))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [http.api :as http :refer [xml-wrap]]))

(http/xml-wrap ...)
(xml-wrap      ...)
```

</details>
