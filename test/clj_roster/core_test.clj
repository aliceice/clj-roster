(ns clj-roster.core-test
  (:require [midje.sweet :refer [facts fact]]
            [clj-roster.core :refer :all]))

(def VALID_CHARS
  (map char (concat (range 48 58)
                    (range 65 91)
                    (range 97 123))))

(defn random-char []
  (rand-nth VALID_CHARS))

(defn random-string [length]
  (apply str (take length (repeatedly random-char))))

(defn random-request []
  (map->ServiceRequest {:name (random-string 5)
                        :environment (random-string 5)}))

(defn test-service-registry [subject]
  (facts "about a service registry"
         (fact "get-endpoint returns :unknown for an unknown registration"
               (get-endpoint subject (random-request))
               => :unknown)

         (fact "get-endpoint returns registered endpoint if present"
               (let [request (random-request)
                     endpoint (random-string 20)]
                 (put-endpoint subject request endpoint)
                 (get-endpoint subject request)
                 => endpoint))

         (fact "get-endpoint for unknown environment returns :unknown"
               (put-endpoint subject (random-request) "some-endpoint")
               (get-endpoint subject (random-request))
               => :unknown)

         (fact "get-endpoint returns :unknown after deleting it"
               (let [request (random-request)
                     endpoint (random-string 20)]
                 (put-endpoint subject request endpoint)
                 (delete-endpoint subject request)
                 (get-endpoint subject request)
                 => :unknown))))

(test-service-registry (in-memory-registry))