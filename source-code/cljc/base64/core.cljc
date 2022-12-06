
(ns base64.core
    (:require #?(:clj [clojure.data.codec.base64 :as base64])
              #?(:clj [clojure.java.io           :as io])
              [base64.convert :as convert]
              [string.api     :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn wrap
  ; @param (string) base64
  ; @param (string) mime-type
  ;
  ; @usage
  ; (wrap "..." "application/pdf")
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
  (if (string/nonblank? base64)
      (str "data:"mime-type";base64,"base64)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn encode
  ; @warning
  ; The function does not create the directory path of the output
  ; if it does not exist!
  ;
  ; @description
  ; Reads the file from the source-filepath encodes the file's content to base64
  ; and writes the encoded content to the destination-filepath.
  ;
  ; @param (string) source-filepath
  ; @param (string) destination-filepath
  ;
  ; @usage
  ; (encode "my-document.pdf" "my-document.b64")
  ;
  ; @example
  ; (encode "my-document.pdf" "my-document.b64")
  ; =>
  ; "data:application/pdf;base64,..."
  ;
  ; @return (string)
  ; Returns with the encoded content.
  [source-filepath destination-filepath]
  #?(:clj (when source-filepath (with-open [i (io/input-stream       source-filepath)
                                            o (io/output-stream destination-filepath)]
                                           (base64/encoding-transfer i o))
                                (slurp destination-filepath))))

(defn decode
  ; @warning
  ; The function does not create the directory path of the output
  ; if it does not exist!
  ;
  ; @description
  ; Reads the file from the source-filepath decodes the file's content to byte array
  ; and writes the decoded content to the destination-filepath.
  ;
  ; @param (string) source-filepath
  ; @param (string) destination-filepath
  ;
  ; @usage
  ; (decode "my-document.b64" "my-document.pdf")
  ;
  ; @return (boolean)
  [source-filepath destination-filepath]
  ; The 'to-byte-array' function only works with wrapped (not just the body) base64 strings.
  #?(:clj (when-let [base64-body (slurp source-filepath)]
                    (let [base64 (str "data:decoder/b64;base64," base64-body)]
                         (clojure.java.io/copy (convert/to-byte-array base64)
                                               (java.io.File. destination-filepath))
                         (-> destination-filepath slurp boolean)))))

(defn save-as
  ; @warning
  ; The function does not create the directory path of the output
  ; if it does not exist!
  ;
  ; @description
  ; Decodes the base64 to byte array and writes the decoded content to the destination-filepath.
  ;
  ; @param (string) base64
  ; @param (string) destination-filepath
  ;
  ; @usage
  ; (save-as "data:application/pdf;base64,..." "my-document.pdf")
  ;
  ; @return (boolean)
  [base64 destination-filepath]
  #?(:clj (do (clojure.java.io/copy (convert/to-byte-array base64)
                                    (java.io.File. destination-filepath))
              (-> destination-filepath slurp boolean))))
