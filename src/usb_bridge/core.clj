(ns usb-bridge.core
  (:gen-class)
  (:require [usb-bridge.configuration :as configuration] ))

(use 'serial.core)
(import '(java.io BufferedReader InputStreamReader))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def usb (open configuration/port :baud-rate configuration/baud-rate))

(defn process-content[content]
  (let [[_ sensor_type value] (re-find #"^(\w*)\[(.*?)\]$" content)]
      (if sensor_type
        (println sensor_type "  |  " value))))

(defn content-read[stream]
  (let [new-stream (BufferedReader. (InputStreamReader. stream))]
    (doseq [line (line-seq new-stream) :when(.ready new-stream)]
      (process-content line)))
      (.close stream))


(listen! usb(fn [in-stream] (content-read in-stream)))

;; (close! usb)
