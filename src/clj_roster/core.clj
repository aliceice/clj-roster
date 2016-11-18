(ns clj-roster.core)

(defrecord ServiceRequest [name
                           environment])

(defprotocol ServiceRegistry
  (get-endpoint [this ^ServiceRequest request])
  (put-endpoint [this ^ServiceRequest request endpoint])
  (delete-endpoint [this request]))

(defn service-registry [get-endpoint-fn
                        put-endpoint-fn
                        delete-endpoint-fn]
  (reify ServiceRegistry
    (get-endpoint [_ request]
      (let [endpoint (get-endpoint-fn request)]
        (if endpoint endpoint :unknown)))

    (put-endpoint [_ request endpoint]
      (put-endpoint-fn request endpoint))

    (delete-endpoint [_ request]
      (delete-endpoint-fn request))))

(defn in-memory-registry []
  (let [endpoints-by-request (atom {})]
    (service-registry
      #(get @endpoints-by-request %)

      (fn [request endpoint]
        (swap! endpoints-by-request
               #(assoc % request endpoint)))

      (fn [request]
        (swap! endpoints-by-request
               #(dissoc % request))))))