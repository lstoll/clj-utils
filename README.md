# utils [![Build Status](https://secure.travis-ci.org/lstoll/clj-utils.png)](http://travis-ci.org/lstoll/clj-utils)

A set of common utility functions I keep re-using

## env

This will retrieve a value from the current environment. If it's not there, it will fall back in to looking for the value in a .env file in the project root (in [foreman](https://github.com/ddollar/foreman/) key=value format). If the value doesn't exist there, it will fall back to either the default passed in, or nil

## pmap2

The standard pmap, but takes a value as the first argument that determines the concurrency level to map at. Useful for IO bound mapping, like processing a series of URLs concurrently.

## log

Prints the passed in data to stdout. Can accept a variable length of string or map arguments, these are appended together with a space in between them. Maps are re-formatted in to k=v strings e.g:

    lstoll.utils> (log "message" "more" {:a "b" :c "d"} "final")
    message more a=b c=d final

## License

Copyright (C) 2012 Lincoln Stoll

Distributed under the Eclipse Public License, the same as Clojure.
