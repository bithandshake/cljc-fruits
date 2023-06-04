
(ns code.run
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn run-code!
  ; @param (string) source-code
  ; @param (vectors in vector)(opt) env-vars
  ; [[(string) var-name
  ;   (*) var-value]
  ;  [...]]
  ;
  ; @usage
  ; (run-code! "(println (my-function my-var))"
  ;            [["my-function" "my-namespace/my-function"]
  ;             ["my-var"      :my-value]])
  ([source-code]
   (run-code! source-code []))

  ([source-code env-vars]
   ; Source code that passed to the 'load-string' function will be evaluated in
   ; the clojure.core namespace, therefore the given environment variables must
   ; be defined with {:private true} setting to prevent the name conflicts in
   ; other namespaces.
   ;
   ; Tools like Ring wrap-reload redefines constants and functions in watched
   ; namespaces when the code changes and it can cause name conflicts if the
   ; 'run-code!' function doesn't define vars only in private scope.
   #?(:clj (if (string/nonblank? source-code)
               (letfn [(environment-f [environment [var-name var-value]]
                                      (str environment "(def ^{:private true} "var-name" "var-value")\n"))]
                      (let [environment (reduce environment-f "" env-vars)
                            source-code (str environment source-code)]
                           (try (load-string source-code)
                                (catch Exception e (println e)))))))))
