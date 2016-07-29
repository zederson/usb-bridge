(ns usb-bridge.hardware.usb
  (:require [usb-bridge.configuration :as configuration]
            [clojure.java.io :as io]
            [serial.core :as serial])
  (:import [java.io BufferedReader InputStreamReader InputStream]))

(def ^:private usb (serial/open configuration/port :baud-rate configuration/baud-rate))

(defn ^:private process-reader [handler]
  (fn [stream]
    (let [buff-reader (BufferedReader. (InputStreamReader. stream))]
      (handler (.readLine buff-reader)))))



(defn reader [handler]
  (serial/listen usb (process-reader handler)))

(defn close [] (serial/close! usb))

(defn write [message] (serial/write usb (.getBytes message)))

