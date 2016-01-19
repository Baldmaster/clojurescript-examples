(ns svg.core)

(enable-console-print!)

(def paper (js/Raphael "app" 500 500))
(def circle (. paper (circle 200 150 100)))
(def anim (. js/Raphael (animation (identity #js {:cx 400 :fill "#0f0"}) 1000)))


(. circle (attr "fill" "#f00"))
(. circle (animate anim))



(defn on-js-reload []

)
