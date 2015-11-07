import GraphConsoleTokenizer from './GraphConsoleTokenizer';

class ParserException extends Error {
	constructor(msg='Parser Error') {
		super(msg);
	}
}

export default class GraphConsoleParser {

	constructor() {
		this.tokenizer = new GraphConsoleTokenizer();
	}

	objectAction(object, action, values) {

	}

	fromSentence(s='') {
		const tokens = this.tokenizer.tok(s);
		return this.parse(tokens);
	}

	parse(tokens=[]) {

		let statements = [];
		let current = [];

		tokens.forEach(tok => {
			if (tok.isDelimiter === true) {
				statements.push(current);
				current = [];
			} else {
				// check that an object is followed by an action
				if (current.length > 0) {
					if (current[current.length - 1].isObject === true && tok.isAction !== true) {
						throw new ParserException('Parse Error: object word must be followed by an action word');
					}
				}
				current.push(tok);
			}
		});

		// if there are no delimiters or the last statement has no delimiter, push it manually
		statements.push(current);

		return statements;
	}

}