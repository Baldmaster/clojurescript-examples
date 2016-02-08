(ns guestbook.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(def comments (atom []))

(defn add-comment [name message]
  (POST "/save" {:params {:message message
                          :name name}
                 :handler (fn [] (swap! comments conj {:message message
                                                       :name name}))
                 :error-handler (fn
                                  [{:keys [status status-text]}]
                                  (println status status-text))
                 :format :raw}))

(defn load-comments
  "Json input data expected"
  []
  (POST "/loadcomments" {:handler (fn [cmnts] (->> cmnts
                                                   (.parse js/JSON)
                                                   (js->clj)
                                                   (map assoc-keys)
                                                   (vec)
                                                   (reset! comments)))}))


(defn assoc-keys
  "Keywordize keys in associative collections
   returns map
   ['key' value ...] => {:key value ...}
   {'key' value ...} => {:key value ...}"
  [col]
  (reduce (fn [acc [k v]]
            (assoc acc (keyword k) v)) {} col))


(defn guestbook-comments []
  (let [_ (load-comments)]
    (fn []
      [:ul
       (for [{:keys [name message]} @comments]
         [:li
          [:blockquote message]
          [:p [:cite name]]])])))


(defn name-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn message-input [value]
  [:textarea {:rows 10
              :cols 40
              :value @value
              :on-change #(reset! value (-> % .-target .-value))}])


(defn message-form []
  (let [name (atom "")
        message (atom "")]
    (fn []
      [:div#message-form
       [:p "name"]
       [name-input name]
       [:p "message"]
       [message-input message]
       [:br]
       [:input {:type "submit"
                :value "comment"
                :onClick #(add-comment @name @message)}]])))


(reagent/render-component [:div [(guestbook-comments)] [(message-form)]]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
