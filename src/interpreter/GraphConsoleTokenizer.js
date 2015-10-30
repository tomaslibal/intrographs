class GraphConsoleToken {
	constructor(isObject, isAction, isValue, tokType, value) {
		this.isObject = isObject;
		this.isAction = isAction;
		this.isValue = isValue;
		this.tokType = tokType;
		this.value = value;
	}
}

export default class GraphConsoleTokenizer {
	
	constructor() {
		this.delimiters = ['.', '(', ')', ','];
		this.stripChars = ['"'];
		this.actions = [
			'add',
			'remove'
		]
	}
	
	tok(input='') {
		let tokens = [];
		let token = '';
		
		let insideParens = false;
		let insideDoubleQuotes = false;
		
		for(let i = 0; i < input.length; i++) {
			let ch = input[i];
			
			if (!insideDoubleQuotes && ch === '"') {
				insideDoubleQuotes = true;
			} else if (insideDoubleQuotes && ch === '"') {
				insideDoubleQuotes = false;
			}
			
			// fast forward until delimiter found
			while (this.delimiters.indexOf(ch) == -1) {
				token += ch;
				i++;
				ch = input[i];
			}
			
			// end token if delimiter found
			if (this.delimiters.indexOf(ch) > -1) {
				const isObject = (token === 'g') ? true : false;
				const isAction = (this.actions.indexOf(token) > -1) ? true : false;
				const isValue = (!isObject && !isAction) ? true : false;
				const tokType = -1;
				const value = token;
				
				const tok = new GraphConsoleToken(isObject, isAction, isValue, tokType, value);
				
				tokens.push(tok);
				token = '';
			}
		}
		
		return tokens;
	}
}