
(ns http.wrap
    (:require [http.utils :as utils]
              [map.api    :as map]))

;; -- Default wrapper ---------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn response-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :headers (map)(opt)
  ;  :mime-type (string)(opt)
  ;   Default: "text/plain"
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ; @param (map)(opt) options
  ; {:hide-errors? (boolean)(opt)
  ;   Replaces the body with an unsensitive keyword (':client-error' or ':server-error')
  ;   in case of client error (4**) or server error (5**) status code is passed.
  ;   Default: false}
  ;
  ; @example
  ; (response-wrap {:body "My text"})
  ; =>
  ; {:body    "My text"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  200}
  ;
  ; @example
  ; (response-wrap {:body      "My text"
  ;                 :headers   {"Content-Disposition" "inline"}
  ;                 :mime-type "text/plain"})
  ; =>
  ; {:body    "My text"
  ;  :headers {"Content-Type"        "text/plain"
  ;            "Content-Disposition" "inline"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  ([response-props]
   (response-wrap response-props {}))

  ([{:keys [mime-type session status] :as response-props :or {mime-type "text/plain" status 200}}
    {:keys [hide-errors?]}]
   (cond-> response-props :select-keys           (select-keys     [:body :headers :session :status])
                          :use-default-mime-type (map/assoc-in-or [:headers "Content-Type"] mime-type)
                          :use-default-status    (map/assoc-in-or [:status] status)
                          hide-errors?           (utils/unsensitive-body)

                          ; If the session value is NIL, this function removes it from the response in order to avoid
                          ; invalid anti-forgery token errors.
                          ; https://clojureverse.org/t/how-do-you-do-csrf-protection-in-your-clojure-webapps/5752
                          (nil? session)         (dissoc :session))))

;; -- Basic wrappers ----------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn text-wrap
  ; @param (map) response-props
  ; {:body (*)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ; @param (map)(opt) options
  ; {:hide-errors? (boolean)(opt)
  ;   Replaces the body with an unsensitive keyword (':client-error' or ':server-error')
  ;   in case of client error (4**) or server error (5**) status code is passed.
  ;   Default: false}
  ;
  ; @example
  ; (text-wrap {:body "My text"})
  ; =>
  ; {:body    "My text"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  ([response-props]
   (text-wrap response-props {}))

  ([{:keys [body] :as response-props} options]
   (response-wrap (merge {:body      (str body)
                          :mime-type "text/plain"
                          :status    200}
                         (select-keys response-props [:session :status]))
                  options)))

(defn error-wrap
  ; @param (map) response-props
  ; {:body (*)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 500}
  ; @param (map)(opt) options
  ; {:hide-errors? (boolean)(opt)
  ;   Replaces the body with an unsensitive keyword (':client-error' or ':server-error')
  ;   in case of client error (4**) or server error (5**) status code is passed.
  ;   Default: false}
  ;
  ; @example
  ; (error-wrap {:body   :file-not-found
  ;              :status 404}
  ; =>
  ; {:body    ":file-not-found"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  404}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  ([response-props]
   (error-wrap response-props {}))

  ([{:keys [body] :as response-props} options]
   (response-wrap (merge {:body      (str body)
                          :mime-type "text/plain"
                          :status    500}
                         (select-keys response-props [:session :status]))
                  options)))

;; -- Redirection wrappers ----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn redirect-wrap
  ; @param (map) response-props
  ; {:location (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 302}
  ;
  ; @example
  ; (redirect-wrap {:location "/my-page"
  ;                 :status   303}
  ; =>
  ; {:headers {"Content-Type" "text/plain"
  ;            "Location"     "/my-page"}
  ;  :status  303}
  ;
  ; @return (map)
  ; {:headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [location] :as response-props}]
  (response-wrap (merge {:status  302
                         :headers {"Location" location}}
                        (select-keys response-props [:session :status]))))

;; -- Specific wrappers -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn html-wrap
  ; @param (map) response-props
  ; {:body (*)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ;
  ; @example
  ; (html-wrap {:body "<!DOCTYPE html> ..."})
  ; =>
  ; {:body    "<!DOCTYPE html> ..."
  ;  :headers {"Content-Type" "text/html"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/html"
                         :status    200}
                        (select-keys response-props [:session :status]))))

(defn json-wrap
  ; @param (map) response-props
  ; {:body (*)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ;
  ; @example
  ; (json-wrap {:body "{...}"})
  ; =>
  ; {:body    "{...}"
  ;  :headers {"Content-Type" "application/json"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/json"
                         :status    200}
                        (select-keys response-props [:session :status]))))

(defn media-wrap
  ; @param (map) response-props
  ; {:body (java.io.File object)
  ;  :filename (string)(opt)
  ;  :mime-type (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ;
  ; @example
  ; (media-wrap {:body      #object[java.io.File 0x4571e67a "/my-file.png"
  ;              :mime-type "image/png"})
  ; =>
  ; {:body    #object[java.io.File 0x4571e67a "/my-file.png"
  ;  :headers {"Content-Type" "image/png"}
  ;  :status  200}
  ;
  ; @example
  ; (media-wrap {:body      #object[java.io.File 0x4571e67a "/my-file.png"
  ;              :filename  "my-file.png"
  ;              :mime-type "image/png"})
  ; =>
  ; {:body    #object[java.io.File 0x4571e67a "/my-file.png"
  ;  :headers {"Content-Type"        "image/png"
  ;            "Content-Disposition" "inline; filename=\"my-file.png\""}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [body filename] :as response-props}]
  (response-wrap (merge {:body body
                         ; By using the attachment header, the browser asks for
                         ; saving the file on the client device, even if its content
                         ; can be displayed.
                         ; :headers (if filename {"Content-Disposition" "attachment; filename=\""filename"\""})
                         :headers (if filename {"Content-Disposition" "inline; filename=\""filename"\""})
                         :status  200}
                        (select-keys response-props [:mime-type :session :status]))))

(defn xml-wrap
  ; @param (map) response-props
  ; {:body (*)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ;
  ; @example
  ; (xml-wrap {:body "<?xml version="1.0" ...?>"})
  ; =>
  ; {:body    "<?xml version="1.0" ...?>"
  ;  :headers {"Content-Type" "application/xml"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/xml"
                         :status    200}
                        (select-keys response-props [:session :status]))))

(defn css-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)
  ;   Default: 200}
  ;
  ; @example
  ; (css-wrap {:body ".class {}"})
  ; =>
  ; {:body    ".class {}"
  ;  :headers {"Content-Type" "text/css"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/css"
                         :status    200}
                        (select-keys response-props [:session :status]))))
