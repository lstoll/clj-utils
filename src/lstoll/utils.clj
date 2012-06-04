(ns lstoll.utils
  (:import java.io.File)
  (:require [clojure.string :as str]))

;; Environment Variable Reading
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

;; More parallel map - modifed from clojure.core/pmap
(defn pmap2
  "Like map, except f is applied in parallel. Semi-lazy in that the
  parallel computation stays ahead of the consumption, but doesn't
  realize the entire result unless required. Processes with n concurrency
  This can be adjusted for IO bound loads"
  {:added "1.0"
   :static true}
  ([n f coll]
     (let [rets (map #(future (f %)) coll)
           step (fn step [[x & xs :as vs] fs]
                  (lazy-seq
                   (if-let [s (seq fs)]
                     (cons (deref x) (step xs (rest s)))
                     (map deref vs))))]
       (step rets (drop n rets))))
  ([n f coll & colls]
     (let [step (fn step [cs]
                  (lazy-seq
                   (let [ss (map seq cs)]
                     (when (every? identity ss)
                       (cons (map first ss) (step (map rest ss)))))))]
       (pmap2 n #(apply f %) (step (cons coll colls))))))

;; Log!
(defn- format-log-message
  [msg]
  (if (instance? clojure.lang.IPersistentMap msg)
    (apply str (interpose " " (doall (map (fn [[k v]] (str (name k) "=" v)) msg))))
    msg))

(defn log
  "Prints the passed in data to stdout. Can accept a variable length of string or map arguments,
  these are appended together with a space in between them. Maps are re-formatted in to k=v strings"
  [& msgs]
  (println (apply str (interpose " " (map format-log-message msgs)))))
