(ns clj-roster.core)

(defrecord ServiceRequest [name
                           environment])

(defprotocol ServiceRegistry
  (get-endpoint [this ^ServiceRequest request])
  (put-endpoint [this ^ServiceRequest request endpoint])
  (delete-endpoint [this request]))

(defn in-memory-registry []
  (let [endpoints-by-request (atom {})]

    (reify ServiceRegistry
      (get-endpoint [_ request]
        (let [endpoint (get @endpoints-by-request request)]
          (if endpoint endpoint :unknown)))

      (put-endpoint [_ request endpoint]
        (swap! endpoints-by-request
               #(assoc % request endpoint)))

      (delete-endpoint [_ request]
        (swap! endpoints-by-request
               #(dissoc % request))))))