(ns lstoll.test.utils
  (:use [lstoll.utils])
  (:use [clojure.test]))

;; (env) tests. this set requires the .env file in this project

(deftest env-no-default
  (is (nil? (env "SOME_RANDOM_ENV"))))

(deftest env-default
  (is "defval" (env "SOME_RANDOM_ENV" "defval")))

(deftest env-dotenv
  (is "abc" (env "DOTENV_TEST_VAR" "def")))

(deftest env-envvar-override
  "This also tests the override, as our mock .env includes a USER field"
  (is (System/getProperty "user.name") (env "USER")))
