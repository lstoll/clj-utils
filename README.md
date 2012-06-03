# utils [![Build Status](https://secure.travis-ci.org/lstoll/clj-utils.png)](http://travis-ci.org/lstoll/clj-utils)

A set of common utility functions I keep re-using

## env

This will retrieve a value from the current environment. If it's not there, it will fall back in to looking for the value in a .env file in the project root (in [foreman](https://github.com/ddollar/foreman/) key=value format). If the value doesn't exist there, it will fall back to either the default passed in, or nil

## License

Copyright (C) 2012 Lincoln Stoll

Distributed under the Eclipse Public License, the same as Clojure.
