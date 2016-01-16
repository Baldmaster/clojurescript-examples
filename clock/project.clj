(defproject clock "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.6.0-alpha"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-3"]]

  :source-paths ["src"]

  :profiles
  {:dev {:dependencies [[figwheel-sidecar "0.5.0-1"]
                        [com.cemerick/piggieback "0.2.1"]]
         :source-paths ["cljs_src" "dev"]}
   :repl {:plugins [[cider/cider-nrepl "0.9.1"]] }}
  
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]} 
  
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                :figwheel {:on-jsload "clock.core/on-js-reload"}

                :compiler {:main clock.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/clock.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/clock.js"
                           :main clock.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             :css-dirs ["resources/public/css"]})
