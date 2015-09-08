// import s.u.t.
import CSSStyles from "../../../src/presentation/css/CSSStyles";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('CSSStyles', () => {

	let cssStyles = null;

	let mockWindow = {
		getComputedStyle: sinon.stub()
	};

	let mockElement = {

	};

	beforeEach(() => {
		cssStyles = new CSSStyles(mockWindow);
	});

	describe('constructor', () => {
		it('throws an Error if the first argument is not the window object', () => {
			let throws = () => {
				let willThrow = new CSSStyles();
			};
			let wontThrow = () => {
				let wontThrow = new CSSStyles(mockWindow);
			}

			chai.expect(throws).to.throw(Error);
			chai.expect(wontThrow).not.to.throw();
		});
		it('assigns property .window to the window object', () => {
			chai.assert.property(cssStyles, 'window');
			chai.assert.deepEqual(cssStyles.window, mockWindow);
		});
	});

	describe('getAllStyles', () => {
		it('returns CSS Properties list of the given element using the window.getComputedStyle method', () => {
			cssStyles.getAllStyles(mockElement);

			assert(mockWindow.getComputedStyle.calledWith(mockElement));
		});
	});
});