(ns calendar.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def days-in-month {:0  ["January"   31]
                    :1  ["February"  28]
                    :2  ["March"     31]
                    :3  ["April"     30]
                    :4  ["May"       31]
                    :5  ["June"      30]
                    :6  ["July"      31]
                    :7  ["August"    31]
                    :8  ["September" 30]
                    :9  ["October"   31]
                    :10 ["November"  30]
                    :11 ["December"  31]})


(defn days-of-week []
  (let [abr ["Mo" "Tu" "We" "Th" "Fr" "Sa" "Su"]]
  (->> abr
       (map #(identity [:th %]))
       (cons :tr#days-of-week)
       (vec))))
      

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
         [month-name days]  (-> month
                                (str)
                                (keyword)
                                (days-in-month))
         total-days (if (and (= 1 month) (leap-year? year))
                      (inc days)
                      days)
         head (rem (Math/abs (- date day)) 7)
         tail (rem (- total-days (- 7 head)) 7)
         head-dummies (repeat (rem 7 (- 7 head)) nil)
         tail-dummies (repeat (- 7 tail) nil)
         weeks (concat [(->> (take head (range 1 8))
                             (into head-dummies))]
                       (partition 7
                                  (range (inc head) (inc (- total-days tail))))
                       [(concat
                         (range (inc (- total-days tail)) (inc total-days))
                         tail-dummies)])
         headings (days-of-week)]
     (do
       (println headings)
       [:table#month
        [:caption month-name " " year]
        (->> weeks
          (filter not-empty)
          (map week)
          (cons headings)
          (cons :tbody)
          (vec))])))

(defn date-cell [date]
  [:td.day date])

(defn week [dates]
  (->> (map date-cell dates)
      (cons :tr)
      (vec)))

(reagent/render-component [current-month]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
