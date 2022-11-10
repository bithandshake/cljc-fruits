
(ns error.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn try!
  ; @param (function) f
  ; @param (list of *) abc
  ;
  ; @usage
  ;  (try! #(my-function "Apple"))
  ;
  ; @usage
  ;  (try! my-function "Apple")
  ;
  ; @return (*)
  [f & abc]
  #?(:clj  (try (apply f abc) (catch Exception e (println e)))
     :cljs (try (apply f abc) (catch :default  e (println e)))))

(defn throw!
  ; @param (string) e
  ;
  ; @usage
  ;  (throw! "Something went wrong ...")
  ;
  ; @return (?)
  [e]
  #?(:clj  (throw (Exception. e))
     :cljs (throw (js/Error.  e))))
