(ns physics.protocols)

(defprotocol Draw
  "Draw something"
  (draw [entity context]))

(defprotocol Kinematics
  (move [body w h]))
