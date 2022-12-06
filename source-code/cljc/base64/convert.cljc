
(ns base64.convert
    (:require #?(:clj [clojure.data.codec.base64 :as base64])
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-header
  ; @param (string) base64
  ;
  ; @usage
  ; (to-header "data:application/pdf;base64,...")
  ;
  ; @example
  ; (to-header "data:application/pdf;base64,...")
  ; =>
  ; "data:application/pdf;base64,"
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/to-first-occurence "," {:return? false})
             (string/if-contains-part   "data:")))

(defn to-body
  ; @param (string) base64
  ;
  ; @usage
  ; (to-body "data:application/pdf;base64,...")
  ;
  ; @example
  ; (to-body "data:application/pdf;base64,...")
  ; =>
  ; "..."
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/if-contains-part      "data:")
             (string/after-first-occurence "," {:return? false})))

(defn to-mime-type
  ; @param (string) base64
  ;
  ; @usage
  ; (to-mime-type "data:application/pdf;base64,...")
  ;
  ; @example
  ; (to-mime-type "data:application/pdf;base64,...")
  ; =>
  ; "application/pdf"
  ;
  ; @return (string)
  [base64]
  (-> base64 (string/after-first-occurence "data:" {:return? false})
             (string/after-first-occurence ";"     {:return? false})))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-byte-array
  ; @param (string) base64
  ;
  ; @usage
  ; (to-byte-array "data:application/pdf;base64,...")
  ;
  ; @return (byte array)
  [base64]
  #?(:clj (if-let [body (to-body base64)]
                  (-> body (.getBytes)
                           (base64/decode)))))

(defn to-blob
  ; @param (string) base64
  ;
  ; @usage
  ; (to-mime-blob "data:application/pdf;base64,...")
  ;
  ; @return (object)
  [base64]
  #?(:cljs (let [binary-string (.atob          js/window base64)
                 binary-length (.-length       binary-string)
                 integer-array (js/Uint8Array. binary-length)
                 mime-type     (to-mime-type   base64)]
                (doseq [i (range binary-length)]
                       (aset integer-array i (.charCodeAt binary-string i)))
                (js/Blob. (clj->js [integer-array])
                          (clj->js {:type mime-type})))))
