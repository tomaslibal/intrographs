// import s.u.t.
import HTMLGraph from "../../../src/presentation/html/HTMLGraph";

import Observable from "../../../src/common/Observable";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLGraph', () => {

	let htmlGraph = null;

	let mockControls = {
		addEventListener: sinon.stub()
	};

	let mockGraph = {
		controls: mockControls
	};

	beforeEach('setup test fixtures', () => {
		htmlGraph = new HTMLGraph(mockGraph);
	});

	describe('setUp', () => {
		it('subscribes to "controls.add.vertex" event on the controls object', () => {
			chai.assert.property(htmlGraph, 'ctrlObservable');
			assert(htmlGraph.ctrlObservable instanceof Observable);
		});
		it('does not create a new observable if graph.controls is undefined', () => {
			let mockGraphWithoutControls = {};

			let htmlGraphWithoutControls = new HTMLGraph(mockGraphWithoutControls);

			chai.assert.notProperty(htmlGraphWithoutControls, 'ctrlObservable');
		});
	});


});