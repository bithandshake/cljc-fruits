
(ns http.wrap)

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
  ;
  ; @example
  ; (response-wrap {:body "foo"})
  ; =>
  ; {:body    "foo"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  200}
  ;
  ; @example
  ; (response-wrap {:body      "foo"
  ;                 :headers   {"Content-Disposition" "inline"}
  ;                 :mime-type "text/plain"})
  ; =>
  ; {:body    "foo"
  ;  :headers {"Content-Type"        "text/plain"
  ;            "Content-Disposition" "inline"}
  ;  :status  200}
  ;
  ; @return (map)
  ; {:body (string)
  ;  :headers (map)
  ;  :session (map)
  ;  :status (integer)}
  [{:keys [headers mime-type] :as response-props}]
  ; WARNING!
  ; A :body és :mime-type tulajdonságok megadása szükséges a response-wrap függvény használatához!
  (let [headers (merge {"Content-Type" (or mime-type "text/plain")} headers)]
       (merge {:headers headers :status 200}
              (select-keys response-props [:body :session :status]))))

;; -- Specific wrappers -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn error-wrap
  ; @param (map) response-props
  ; {:error-message (string or keyword)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
  ;
  ; @example
  ; (error-wrap {:error-message "File not found"
  ;              :status        404}
  ; =>
  ; {:body    ":file-not-found"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  404}
  ;
  ; @return (map)
  [{:keys [error-message] :as response-props}]
  (response-wrap (merge {:body   error-message
                         :status 500}
                        (select-keys response-props [:session :status]))))

(defn html-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
  ;
  ; @example
  ; (html-wrap {:body "<!DOCTYPE html> ..."})
  ; =>
  ; {:body    "<!DOCTYPE html> ..."
  ;  :headers {"Content-Type" "text/html"}
  ;  :status  200}
  ;
  ; @return (map)
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/html"}
                        (select-keys response-props [:session :status]))))

(defn json-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}

  ;
  ; @example
  ; (json-wrap {:body "{...}"})
  ; =>
  ; {:body    "{...}"
  ;  :headers {"Content-Type" "application/json"}
  ;  :status  200}
  ;
  ; @return (map)
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/json"}
                        (select-keys response-props [:session :status]))))

(defn map-wrap
  ; @param (map) response-props
  ; {:body (map)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
  ;
  ; @example
  ; (map-wrap {:body {...})
  ; =>
  ; {:body    "{...}"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  200}
  ;
  ; @return (map)
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body (str body)}
                        (select-keys response-props [:session :status]))))

(defn media-wrap
  ; @param (map) response-props
  ; {:body (java.io.File object)
  ;  :filename (string)(opt)
  ;  :mime-type (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
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
  [{:keys [body filename] :as response-props}]
  (response-wrap (merge {:body body
                         ; Az attachment beállítás használatával a böngésző akkor is felkínálja mentésre,
                         ; a szerver válaszát, ha azt különben képes lenne megjeleníteni.
                         ; :headers (if filename {"Content-Disposition" "attachment; filename=\""filename"\""})
                         :headers (if filename {"Content-Disposition" "inline; filename=\""filename"\""})}
                        (select-keys response-props [:mime-type :session :status]))))

(defn xml-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
  ;
  ; @example
  ; (xml-wrap {:body "foo"})
  ; =>
  ; {:body    "foo"
  ;  :headers {"Content-Type" "application/xml"}
  ;  :status  200}
  ;
  ; @return (map)
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "application/xml"}
                        (select-keys response-props [:session :status]))))

(defn text-wrap
  ; @param (map) response-props
  ; {:body (string)
  ;  :session (map)(opt)
  ;  :status (integer)(opt)}
  ;
  ; @example
  ; (text-wrap {:body "foo"})
  ; =>
  ; {:body    "foo"
  ;  :headers {"Content-Type" "text/plain"}
  ;  :status  200}
  ;
  ; @return (map)
  [{:keys [body] :as response-props}]
  (response-wrap (merge {:body      (str body)
                         :mime-type "text/plain"}
                        (select-keys response-props [:session :status]))))
