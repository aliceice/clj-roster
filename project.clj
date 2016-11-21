(defproject clj-roster "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [compojure "1.5.1"]]
  :profiles {:dev {:dependencies [[midje "1.8.3"]
                                  [org.clojure/test.check "0.9.0"]
                                  [ring/ring-mock "0.3.0"]]}})
