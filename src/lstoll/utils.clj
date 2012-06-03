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
  "This will retrieve a value from the current environment. If it's not there, it will fall back in to looking for the value in a .env file in the project root (in 'foreman' key=value format). If the value doesn't exist there, it will fall back to either the default passed in, or nil"
  (or (System/getenv k)
      (get (dotenv) k)
      v))
