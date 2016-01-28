(ns physics.protocols)

(defprotocol Draw
  "Draw something"
  (draw [entity context]))
