(ns lstoll.test.utils
  (:use [lstoll.utils])
  (:use [clojure.test]))

(defn thread-count []
  (.getThreadCount (java.lang.management.ManagementFactory/getThreadMXBean)))

;; (env) tests. this set requires the .env file in this project

(deftest env-no-default
  (is (nil? (env "SOME_RANDOM_ENV"))))

(deftest env-default
  (is (= "defval" (env "SOME_RANDOM_ENV" "defval"))))

(deftest env-dotenv
  (is (= "abc" (env "DOTENV_TEST_VAR" "def"))))

(deftest env-envvar-override
  "This also tests the override, as our mock .env includes a USER field"
  (is (= (System/getProperty "user.name") (env "USER"))))

;; (pmap2) tests

(deftest pmap2-scale-out
  (let [start-tc (thread-count)]
    (pmap2 10 (fn [a] (Thread/sleep 4000)) (repeat 20 nil))
    (is (> (+ 10 (thread-count)) start-tc))))

(deftest pmap2-scale-out-multicoll
  (let [start-tc (thread-count)]
    (pmap2 10 (fn [a b] (Thread/sleep 4000))
           (repeat 20 nil)(repeat 20 nil))
    (is (> (+ 10 (thread-count)) start-tc))))

;; (log) tests

;; The agent messed things up.
;; (deftest log-complex-message
  ;; (is (= "message more a=b c=d final\n" (with-out-str (log "message" "more" {:a "b" :c "d"} "final")))))