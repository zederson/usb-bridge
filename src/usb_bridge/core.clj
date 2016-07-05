(ns usb-bridge.core
  (:gen-class)
  (:require [usb-bridge.configuration :as configuration])
  (:import  [java.io BufferedReader InputStreamReader]))

(use 'serial.core)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn process-content[content]
  (let [[_ sensor_type value] (re-find #"^(\w*)\[(.*?)\]$" content)]
      (if sensor_type
        (println sensor_type "  |  " value))))

(defn content-read[stream]
  (let [new-stream (BufferedReader. (InputStreamReader. stream))]
    (doseq [line (line-seq new-stream)]
      (loop [s stream]
        (if (.ready new-stream)
            (process-content line)
            (recur stream))))
    (.close stream)))

(def usb (open configuration/port :baud-rate configuration/baud-rate))

(listen! usb(fn [in-stream] (content-read in-stream)))

(close! usb)
