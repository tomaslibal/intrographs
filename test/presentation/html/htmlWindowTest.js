// import s.u.t.
import HTMLWindow from '../../../src/presentation/html/HTMLWindow';

let chai = require('chai');
let assert = require('assert');
let sinon = require('sinon');

describe('HTMLWindow', () => {

	let htmlWin;

	let mockWindow = {
		innerWidth: 640,
		innerHeight: 480
	};

	beforeEach(() => {
		htmlWin = new HTMLWindow(mockWindow);
	});

	describe('constructor', () => {
		it('throws an Error if the window object is not supplied', () => {
			let throws = () => {
				const htmlWinThrows = new HTMLWindow();
			}

			chai.expect(throws).to.throw('A window object must be passed to the constructor');
		});

		it('assigns the window object to .window property', () => {
			chai.assert.property(htmlWin, 'window');
			chai.assert.deepEqual(htmlWin.window, mockWindow);
		})
	});

	it('.dims() gets dimension as [width, height]', () => {
		const [width, height] = htmlWin.dims();

		chai.assert.equal(width, 640);
		chai.assert.equal(height, 480);
	});
});