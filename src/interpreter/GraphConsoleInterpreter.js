import GraphConsoleTokenizer from './GraphConsoleTokenizer';
import GraphConsoleParser from './GraphConsoleParser';

export default class GraphConsoleInterpreter {

	constructor(eventBus) {
		this.eventBus = eventBus;

		this.eventBus.on('interpreter.input', this.onInputHandler.bind(this));

		this.tokenizer = new GraphConsoleTokenizer();
		this.parser = new GraphConsoleParser();
	}

	onInputHandler(ev) {
		const input = ev.payload;
		console.log('got input: ' + input);

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
		this.eventBus.dispatch({ type: 'interpreter.add.vertex', id: parseTree[3].value, 'label': 'n/a'});
	}
}