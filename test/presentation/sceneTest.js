// import s.u.t.
import Scene from "../../src/presentation/Scene";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('Scene', () => {

	let scene = null;

	let mockBg = {
		render: sinon.stub()
	};

    let mockGraph = {
        render: sinon.stub()
    };

    let mockMinidisplay = {
        render: sinon.stub()
    };

	let mockControls = {
		render: sinon.stub()
	};

	beforeEach(() => {
		scene = new Scene(mockGraph, mockBg, mockMinidisplay, mockControls);
	});

	it('has background, graph, minidisplay, and controls properties', () => {
		chai.assert.isDefined(scene.background);
		chai.assert.isDefined(scene.graph);
		chai.assert.isDefined(scene.minidisplay);
		chai.assert.property(scene, 'controls');

});

	it('initializes with clock count 0', () => {
		chai.assert.equal(scene.clock, 0);
	});

	it('initializes with clock not going forward', () => {
		chai.assert.equal(scene.playState, 'paused');
	});

	it('renderAll() calls render() on background, graph, minidisplay, and controls properties', () => {
	    scene.renderAll();
        assert(mockBg.render.calledOnce);
        assert(mockGraph.render.calledOnce);
        assert(mockMinidisplay.render.calledOnce);
		assert(mockControls.render.calledOnce);
	});

    it('renderAll() wont call thru to background, graph, minidisplay, and controls if they are null', () => {
        let emptyScene = new Scene();
        chai.assert.isNull(emptyScene.background);
        chai.assert.isNull(emptyScene.graph);
        chai.assert.isNull(emptyScene.minidisplay);
		chai.assert.isNull(emptyScene.controls);

        emptyScene.renderAll();
    });

});
