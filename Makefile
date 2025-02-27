run-tests:
	clojure -X:run-tests

release:
	clojure -T:release uberjar
