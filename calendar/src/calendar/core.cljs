(ns calendar.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

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



(defn- leap-year? [year]
  (letfn [(not-div-by? [n] (->> n (rem year) (zero?) (not)))]
   (cond
    (not-div-by? 4) false
    (not-div-by? 100) true
    (not-div-by? 400) false
    :else true)))

(defn current-month []
   (let [current-date (js/Date.)
         day   (.getDay current-date)
         date  (.getDate current-date)
         month (.getMonth current-date)
         year  (.getFullYear current-date)
         days  (+ (-> month
                     (str)
                     (keyword)
                     (days-in-month))
                  (if (and (= month 1) (leap-year? year))
                  1
                  0))         
         head (rem (Math/abs (- date day)) 7)
         tail (rem (- days (- 7 head)) 7)
         head-dummies (repeat (rem 7 (- 7 head)) nil)
         tail-dummies (repeat (- 7 tail) nil)
         weeks (concat [(->> (take head (range 1 8))
                             (into head-dummies))]
                       (partition 7 (range (inc head) (inc (- days tail))))
                       [(concat (range (inc (- days tail)) (inc days)) tail-dummies)])]
     (->> weeks
          (filter not-empty)
          (map week)
          (cons :tbody)
          (vec))))

(defn date-cell [date]
  [:td.day date])

(defn week [dates]
  (->> (map date-cell dates)
      (cons :tr)
      (vec)))

(reagent/render-component [:table#month [current-month]]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
