(ns clock.core
  (:require [reagent.core :as r]))

(defonce timer (r/atom (js/Date.)))

(defonce time-color (r/atom "#f34"))

(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 1000))

(defn clock []
  (let [time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.example-clock
     {:style {:color @time-color
              :font-size "100px"}}
     time-str]))

(defn simple-example []
  [:div
   [clock]])

(defn ^:export run []
  (r/render [simple-example]
            (js/document.getElementById "app")))
