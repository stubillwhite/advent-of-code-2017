(defproject advent-of-code-2017 "0.1.0-SNAPSHOT"

  :description "TODO"

  :url "TODO"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repl-options {:port 4555}

  :plugins []
  
  :dependencies [[org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/clojure "1.9.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/tools.trace "0.7.9"]
                 [com.rpl/specter "1.0.2"]
                 [mount "0.1.11"]
                 [com.taoensso/tufte "1.1.1"]
                 [com.taoensso/tufte "1.1.2"]]
  
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}})
