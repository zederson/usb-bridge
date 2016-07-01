(ns usb-bridge.configuration
  (:require [clj-yaml.core :as yaml]))

(def path "./resources/usb_bridge.yml")

(def read-configuration
  (yaml/parse-string (slurp path)))

(def port (-> read-configuration :arduino :port))
(def baud-rate (-> read-configuration :arduino :baud_rate))
