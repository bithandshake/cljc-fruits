
(ns uri.type
    (:require [regex.api  :refer [re-match?]]
              [uri.config :as config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn domain?
  ; @param (*) n
  ; @param (map)(opt)
  ;  {:strict? (boolean)(opt)
  ;    Default: false}
  ;
  ; @usage
  ; (domain? "my-domain.com")
  ;
  ; @example
  ; (domain? "https://my-domain.com")
  ; =>
  ; true
  ;
  ; @example
  ; (domain? "https://my-domain.com/")
  ; =>
  ; true
  ;
  ; @example
  ; (domain? www."my-domain.com/")
  ; =>
  ; true
  ;
  ; @example
  ; (domain? "my-domain.com/")
  ; =>
  ; true
  ;
  ; @example
  ; (domain? "sub.my-domain.com/")
  ; =>
  ; true
  ;
  ; @example
  ; (domain? "https://my-domain.com" {:strict? true})
  ; =>
  ; false
  ;
  ; @example
  ; (domain? "my-domain.com/my-path")
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n]
   (domain? n {}))

  ([n {:keys [strict?] :or {strict? false}}]
   (if strict? (re-match? n config/STRICT-DOMAIN-PATTERN)
               (re-match? n config/DOMAIN-PATTERN))))
