// import s.u.t.
import HTMLScene from "../../../src/presentation/html/HTMLScene";

import HTMLBackground from "../../../src/presentation/html/HTMLBackground";
import HTMLMinidisplay from "../../../src/presentation/html/HTMLMinidisplay";
import HTMLGraph from "../../../src/presentation/html/HTMLGraph";
import HTMLControls from "../../../src/presentation/html/HTMLControls";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLScene', () => {

	let htmlScene = null;

	let mockGraph = {

	};

	let mockDocument = {
		createElement: sinon.stub()
	};

	beforeEach(() => {
		htmlScene = new HTMLScene(mockGraph, mockDocument);
	});

	it('uses instances of HTMLBackground, HTMLMinidsplay, HTMLGraph, and HTMLControls', () => {
		assert(htmlScene.background instanceof HTMLBackground);
		assert(htmlScene.minidisplay instanceof HTMLMinidisplay);
		assert(htmlScene.graph instanceof HTMLGraph);
		assert(htmlScene.controls instanceof HTMLControls);
	});
});