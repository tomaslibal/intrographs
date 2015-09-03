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

	beforeEach(() => {
		scene = new Scene(mockGraph, mockBg, mockMinidisplay);
	});

	it('has background, graph, and minidisplay properties', () => {
		chai.assert.isDefined(scene.background);
		chai.assert.isDefined(scene.graph);
		chai.assert.isDefined(scene.minidisplay);

});

	it('initializes with clock count 0', () => {
		chai.assert.equal(scene.clock, 0);
	});

	it('initializes with clock not going forward', () => {
		chai.assert.equal(scene.playState, 'paused');
	});

	it('renderAll() calls render() on background, graph and minidisplay properties', () => {
	    scene.renderAll();
        assert(mockBg.render.calledOnce);
        assert(mockGraph.render.calledOnce);
        assert(mockMinidisplay.render.calledOnce);	
	});

    it('renderAll() wont call thru to background, graph and minidisplay if they are null', () => {
        let emptyScene = new Scene();
        chai.assert.isNull(emptyScene.background);
        chai.assert.isNull(emptyScene.graph);
        chai.assert.isNull(emptyScene.minidisplay);
        
        emptyScene.renderAll();
    });	

});
