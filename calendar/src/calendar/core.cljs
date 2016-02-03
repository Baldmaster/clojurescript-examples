(ns calendar.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(def days-in-month {:0 31
                    :1 28
                    :2 31
                    :3 30
                    :4 31
                    :5 30
                    :6 31
                    :7 31
                    :8 30
                    :9 31
                    :10 30
                    :11 31})



(defn leap-year? [year]
  (letfn [(not-div-by? [n] (->> n (rem year) (zero?) (not)))]
   (cond
    (not-div-by? 4) false
    (not-div-by? 100) true
    (not-div-by? 400) false
    :else true)))

(defn current-month []
   (let [current-date (js/Date.)
         day (.getDay current-date)
         date (.getDate current-date)
         month (.getMonth current-date)
         days (-> month (str) (keyword) (days-in-month))
         head (rem (Math/abs (- date day)) 7)
         tail (rem (- days (- 7 head)) 7)]
     
     (->> (concat (take head (range 1 8))
      (partition 7 (range (inc head) (inc (- days tail))))
      (take tail (range 1 8)))
         (map week-component)
         (cons :tbody)
         (vec))))
         
(defn hello-world []
  [:h1 (:text @app-state)])

(defn date-component [date]
  [:td.day date])

(defn week-component [dates]
  (->> (map date-component dates)
      (cons :tr)
      (vec)))

(reagent/render-component [:table#month [current-month]]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
