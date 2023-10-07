
(ns http.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn local-request?
  ; @description
  ; Checks if a request is local based on the server-name attribute, returning
  ; TRUE if it is 'localhost' and FALSE otherwise.
  ;
  ; @param (map) request
  ; {:server-name (string)}
  ;
  ; @usage
  ; (local-request? {...})
  ;
  ; @example
  ; (local-request? {:server-name "localhost" ...})
  ; =>
  ; true
  ;
  ; @example
  ; (local-request? {:server-name "hostname.com" ...})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [{:keys [server-name]}]
  (= server-name "localhost"))

(defn remote-request?
  ; @description
  ; Checks if a request is remote based on the server-name attribute, returning
  ; TRUE if it is not 'localhost' and FALSE if it is.
  ;
  ; @param (map) request
  ; {:server-name (string)}
  ;
  ; @usage
  ; (remote-request? {...})
  ;
  ; @example
  ; (remote-request? {:server-name "hostname.com" ...})
  ; =>
  ; true
  ;
  ; @example
  ; (remote-request? {:server-name "localhost" ...})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [{:keys [server-name]}]
  (not= server-name "localhost"))
