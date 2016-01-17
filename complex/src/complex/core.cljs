(ns complex.core
  (:require ))

(enable-console-print!)

(deftype Complex [real im])

;; Define getters, equivalent to this js code:
;; Complex.prototype.getSmth = function () { return this.smth };
(set! (.. Complex -prototype -getReal) (fn [] (this-as this (.-real this))))
(set! (.. Complex -prototype -getIm) (fn [] (this-as this (.-im this))))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
