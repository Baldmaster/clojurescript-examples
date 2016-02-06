(ns guestbook.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [POST]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce comments (atom []))

(defn add-comment [name message]
  (POST "/save" {:params {:message message
                          :name name}
                 :handler (fn [] (swap! comments conj {:message message
                                                       :name name}))
                 :error-handler (fn
                                  [{:keys [status status-text]}]
                                  (println status status-text))
                 :format :raw}))

(defn guestbook-comments []
  [:ul
   (for [{:keys [name message]} @comments]
     [:li
      [:blockquote message]
      [:p [:cite name]]])])


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


(reagent/render-component [:div [guestbook-comments] [(message-form)]]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
