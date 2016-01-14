(ns hello_world.core
  (:require [reagent.core :as r]))



;;Get headers h6 to h1
(defn headers [text] (->> (range 1 7)
                          (map #(vector (keyword (str "h" %)) text)) 
                          ))

(defn hello-headers [] (->> (headers "Hello, world!")
                            (cons :div)
                            (vec)))

(defn ^:export run []
  (r/render [hello-headers] 
            (js/document.getElementById "app")))

(def on-js-reload run)
