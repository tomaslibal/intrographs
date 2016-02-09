import * as tsUnit from '../tsUnit/tsUnit/bin/Scripts/tsUnit/tsUnit';
import * as Main from '../../src-ts/main';
import { Scene } from '../../src-ts/presentation/Scene';
import { Graph } from '../../src-ts/graphs/Graph';

export class MainTest extends tsUnit.TestClass {
    initFunction_callsRender_onTheSceneObject() {
        let called: boolean = false;
        let g: Graph = new Graph();
        let mockScene: MockScene = new MockScene(g);

        Main.init(mockScene);

        this.isTrue(mockScene.methodCalls.indexOf("render") > -1, "render() has been called");
    }
}

class MockScene extends Scene {
    canvas;
	graph: Graph;
	console;

    methodCalls: Array<String> = [];

	constructor(g: Graph) {
        super(g);
		this.graph = g;
        this.methodCalls.push("new");
	}

	render() {
        this.methodCalls.push("render");
    }
}