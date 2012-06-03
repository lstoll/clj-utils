(ns lstoll.utils
  (:import java.io.File)
  (:require [clojure.string :as str]))

(defn dotenv []
  (try (->> (str/split (slurp (.getAbsolutePath (File. ".env"))) #"\n")
            (map #(str/split % #"="))
            (map (fn [[k v]] [k v]))
            (into {}))
       (catch Exception e {}))) ; No env if no file.

(defn env [k & [v]]
  (or (System/getenv k)
      (get (dotenv) k)
      v))
