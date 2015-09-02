export default class MatrixPrinter {

	static ascii(matrix) {
		let printRow = (row) => {
			let str = '[ ';
			row.forEach(val => {
				str += val + '  ';
			});
			// replace the last space char with a closing square bracket "]"
			str = str.substr(0, str.length - 1);
			str += ']\n';

			return str;
		};

		let str = '';

		matrix.forEach(row => {
			str += printRow(row);
		});

		return str;
	}

}