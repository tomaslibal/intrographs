/**
 * PolymerBridge enables the application to use Polymer library. Because Polymer
 * utilizes rel="import" which is not implemented by all browsers as of now, the
 * client code using Polymer needs to wait until WebComponentsReady events is 
 * fired on the document. This event means that the browser is ready to import
 * Polymer, either because it supports it natively, or the Web Components polyfill
 * has finished initializing. After this, the Polymer library is added.
 * 
 */
export default class PolymerBridge {
   
    constructor(document) {
        this.document = document;

        this.polymerReady = false;
        this.waitUntilPolymerReady(this.importPolymerElements);
    }

    /**
     * Appends ${element} as child to ${parentElement}
     */
    attachElement(parentElement, element) {
        parentElement.appendChild(element);
    }

    /**
     * Returns a <link rel="import" href="${href}"> element
     */
    createImportElement(href) {
        let element = this.document.createElement('link');
        element.rel = 'import';
        element.href = href;
        return element;
    }

    /**
     * Appends a <link rel="import" href="${href}"> to document's <head>
     */
    importElement(elementName, href) {
        let element = this.createImportElement(href);
        this.attachElement(this.document.head, element);
    }
    
    importPolymerElements() {
        if (!this.polymerReady) {
            return;
        }
        
        this.importElement('polymer', '/bower_components/polymer/polymer.html');
        this.importElement('font-roboto', '/bower_components/font-roboto/roboto.html');
        this.importElement('vertext-list', '/elements/vertexList.html');
        this.importElement('console', '/elements/console.html');
        
        let style = this.document.createElement('style');
        style.innerHTML = '* { font-family: Roboto, sans-serif; }';
        this.document.head.appendChild(style);
    }

    /**
     * Listens to WebComponentsReady event on document
     */
    waitUntilPolymerReady(fn) {
        this.document.addEventListener('WebComponentsReady', () => {
            this.polymerReady = true;
            fn.call(this);
        });
    }

    static getPolymerSync() {
        return typeof Polymer !== "undefined" ? Polymer : undefined;
    }   
    
    static getPolymerPromise() {
        /** TODO */
    }
} 
