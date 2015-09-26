#!/usr/bin/env bash

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
