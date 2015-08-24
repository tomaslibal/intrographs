test:
	./node_modules/mocha/bin/mocha --compilers js:babel/register

run: local openChrome

openChrome:
	-open -a "Google Chrome" http://localhost:8080

local:
	python -m SimpleHTTPServer 8080 &

.PHONY: test
