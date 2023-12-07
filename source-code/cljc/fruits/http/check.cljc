
(ns fruits.http.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn local-request?
  ; @description
  ; Returns TRUE if the given 'request' map is local (based on the server-name attribute).
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
  ; Returns TRUE if the given 'request' map is remote (based on the server-name attribute).
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
