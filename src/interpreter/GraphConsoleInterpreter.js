import GraphConsoleTokenizer from './GraphConsoleTokenizer';
import GraphConsoleParser from './GraphConsoleParser';

export default class GraphConsoleInterpreter {

	constructor(eventBus) {
		this.eventBus = eventBus;

		this._boundOnInputHandler = this.onInputHandler.bind(this);
		this.eventBus.on('interpreter.input', this._boundOnInputHandler);

		this.tokenizer = new GraphConsoleTokenizer();
		this.parser = new GraphConsoleParser();
	}

	onInputHandler(ev) {
		const input = ev.payload;
		const toks = this.tokenizer.tok(input);
		const statements = this.parser.parse(toks);

		statements.forEach(stmt => {
			// add vertex or edge to the graph
			if (stmt[0].isObject && stmt[1].isAction && stmt[1].value === 'add') {
				// vertex
				if (stmt[2].value === 'vertex') {
					this.addNewVertex(stmt);
				}
			}
		});
	}

	addNewVertex(parseTree) {
		const addVertexEvent = 'interpreter.add.vertex';

		// one vertex
		this.eventBus.dispatch({ type: addVertexEvent, id: parseTree[3].value, 'label': 'n/a' });
		// optional var.args vertices
		if (parseTree.length === 4) {
			return;
		}
		for(let i = 4; i < parseTree.length; i++) {
			this.eventBus.dispatch({ type: addVertexEvent, id: parseTree[i].value, 'label': 'n/a' });
		}
	}
}