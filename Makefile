test:
	./node_modules/mocha/bin/mocha graphTest.js

local:
	python -m SimpleHTTPServer 8080

.PHONY: test
