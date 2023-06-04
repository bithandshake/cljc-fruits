
(ns temp)

(defn import-changes
  ; @description
  ; Takes the original and the updated data and returns the
  ; updated data that contains the extractable changes that could be
  ; used for restore the original data.
  ;
  ; @param (map) original
  ; The original data.
  ; @param (map) updated
  ; The updated data.
  ; @param (map)(opt) meta
  ; Meta informations of the current change.
  ;
  ; @usage
  ; (import-changes {...} {...})
  ;
  ; @example
  ; (import-changes {:a "A" :b "B"}
  ;                 {:a "A" :b "B" :c "C"})
  ; =>
  ; {:a "A" :b "B" :c "C" :history [{:changes {:c nil}}]}
  ;
  ; @example
  ; (let [original {:a "A" :b "B"}
  ;       updated  (assoc original :c "C")])
  ;      (import-changes original updated)
  ; =>
  ; {:a "A" :b "B" :c "C" :history [{:changes {:c nil}}]}
  ;
  ; @example
  ; (let [original {:a "A" :b "B" :c "C" :history [{:changes {:c nil}}]}
  ;       updated  (dissoc original :c)])
  ;      (import-changes original updated)
  ; =>
  ; {:a "A" :b "B" :history [{:changes {:c nil}}
  ;                          {:changes {:c "C"}}]}
  ;
  ; @return (map)
  ; {:history (maps in vector)}
  [original updated]
  (letfn [(map-f [])
          (vector-f [])
          (recur-f [])]
         (recur-f)))

(defn extract-original
  ; @param (map) updated
  ;
  ; @usage
  ; (extract-original)
  [updated])


{:order/id          "xxx"
 :order/items       [{}
                     {}
                     {:xx "xx"}]
 :order/created-at  "xxx"
 :order/modified-at "xxx"
 :order/client-name "Arpad Toth"
 :order/history [{:history/client-name "Arpad Toht"
                  :history/changed-at  "xxx"
                  :history/changed-by  "my-user"
                  :history/items       {:9 {} :meta/type :persistent-vector}}]}
