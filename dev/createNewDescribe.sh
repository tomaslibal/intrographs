#!/usr/bin/env bash

# Creates a new JavaScript test case file for the Mochajs test runner and test framework.
#
# Usage:
#
#    for a module name src/properties/PlanarityTester.js
#    executed the script as > ./createNewDescribe.sh properties/planarityTester
#
#    this will create a file test/properties/planarityTesterTest.js
#    with the boilerplate from the below here-document.
#

name=${1:-'defaultModule'}
output=test/${name}Test.js

(
cat <<EOF
// import s.u.t
import DefaultModule from '../../src/';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('DefaultModule', () => {

});
EOF
) > $output

echo 'Done'
