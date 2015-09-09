export default class HTMLList {

	constructor(documentObj=null) {
		if (documentObj === null) {
			throw new Error('Constructor must be supplied with the Document object');
			return;
		}

		this.document = documentObj;
		this.list = [];
	}
}