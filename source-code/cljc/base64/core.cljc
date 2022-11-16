
(ns base64.core
    (:require #?(:clj [clojure.data.codec.base64 :as base64])
              #?(:clj [io.api                    :as io])
              [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wrap
  ; @param (string) base64
  ; @param (string) mime-type
  ;
  ; @example
  ; (wrap "..." "application/pdf")
  ; =>
  ; "data:application/pdf;base64,..."
  ;
  ; @example
  ; (wrap "" "application/pdf")
  ; =>
  ; nil
  ;
  ; @example
  ; (wrap nil "application/pdf")
  ; =>
  ; nil
  ;
  ; @return (string)
  [base64 mime-type]
  (if (string/nonempty? base64)
      (str "data:"mime-type";base64,"base64)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn encode
  ; @param (string) source-filepath
  ; @param (string) destination-filepath
  ;
  ; @usage
  ; (encode "my-document.pdf" "my-document.b64")
  ;
  ; @return (string)
  [source-filepath destination-filepath]
  #?(:clj (when source-filepath (with-open [i (io/input-stream       source-filepath)
                                            o (io/output-stream destination-filepath)]
                                           (base64/encoding-transfer i o))
                                (slurp destination-filepath))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-blob
  ; @param (string) base64
  ; @param (string) mime-type
  ;
  ; @usage
  ; (to-blob "..." "application/pdf")
  ;
  ; @return (object)
  [base64 mime-type]
  #?(:cljs (let [binary-string (.atob js/window base64)
                 binary-length (.-length binary-string)
                 integer-array (js/Uint8Array. binary-length)]
                (doseq [i (range binary-length)]
                       (aset integer-array i (.charCodeAt binary-string i)))
                (js/Blob. (clj->js [integer-array])
                          (clj->js {:type mime-type})))))
