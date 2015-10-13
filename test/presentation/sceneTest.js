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

	let mockMenubar = {
		render: sinon.stub()
	};

	beforeEach(() => {
		scene = new Scene(mockGraph, mockBg, mockMinidisplay, mockControls, mockMenubar);
	});

	it('has background, graph, minidisplay, and controls properties', () => {
		chai.assert.property(scene, 'background');
		chai.assert.property(scene, 'graph');
		chai.assert.property(scene, 'minidisplay');
		chai.assert.property(scene, 'controls');
		chai.assert.property(scene, 'menubar');
	});

	it('initializes with clock count 0', () => {
		chai.assert.equal(scene.clock, 0);
	});

	it('initializes with clock not going forward', () => {
		chai.assert.equal(scene.playState, 'paused');
	});

	it('renderAll() calls render() on background, graph, minidisplay, controls, and menubar properties', () => {
	    scene.renderAll();
        assert(mockBg.render.calledOnce);
        assert(mockGraph.render.calledOnce);
        assert(mockMinidisplay.render.calledOnce);
		assert(mockControls.render.calledOnce);
		assert(mockMenubar.render.calledOnce);
	});

    it('renderAll() wont call thru to background, graph, minidisplay, controls, and menubar if they are null', () => {
        let emptyScene = new Scene();
        chai.assert.isNull(emptyScene.background);
        chai.assert.isNull(emptyScene.graph);
        chai.assert.isNull(emptyScene.minidisplay);
		chai.assert.isNull(emptyScene.controls);
		chai.assert.isNull(emptyScene.menubar);

		mockBg.render.reset();
		mockGraph.render.reset();
		mockMinidisplay.render.reset();
		mockControls.render.reset();
		mockMenubar.render.reset();

        emptyScene.renderAll();

		assert(mockBg.render.notCalled);
		assert(mockGraph.render.notCalled);
		assert(mockMinidisplay.render.notCalled);
		assert(mockControls.render.notCalled);
		assert(mockMenubar.render.notCalled);
    });

	it.skip(`adds a reference of the "controls" object to the graph object because the graph will subscribe to
		events in controls (like add new vertex, edge etc.)`, () => {
		chai.assert.deepEqual(scene.graph.controls, mockControls);
	});

});
