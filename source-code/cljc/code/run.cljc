
(ns code.run
    (:require [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn run-code!
  ; @param (string) source-code
  ; @param (vectors in vector)(opt) env-vars
  ;  [[(string) var-name
  ;    (*) var-value]
  ;   [...]]
  ;
  ; @usage
  ;  (run-code! "(println (my-function my-var))"
  ;             [["my-function" "my-namespace/my-function"]
  ;              ["my-var"      :my-value]])
  ([source-code]
   (run-code! source-code []))

  ([source-code env-vars]
   ; A load-string függvény számára paraméterként átadott forráskód a clojure.core
   ; névtérben kerül értelmezésre, ezért a definiált környezeti változókat {:private true}
   ; beállítással szükséges létrehozni, ellenkező esetben a definiált változók nevei
   ; az összes névtér számára foglaltak lennének a clojure.core névtér által az egyes
   ; névterek wrap-reload általi újratöltésekor.
   #?(:clj (if (string/nonempty? source-code)
               (letfn [(environment-f [environment [var-name var-value]]
                                      (str environment "(def ^{:private true} "var-name" "var-value")\n"))]
                      (let [environment (reduce environment-f "" env-vars)
                            source-code (str environment source-code)]
                           (try (load-string source-code)
                                (catch Exception e (println e)))))))))
