export default class PolymerBridge {
    constructor(document) {
        this.document = document;

        this.waitUntilPolymerReady();
    }

    attachElement(parentElement, element) {
        parentElement.appendChild(element);
    }

    createImportElement(href) {
        let element = this.document.createElement('link');
        element.rel = 'import';
        element.href = href;
        return element;
    }

    importElement(elementName, href) {
        let element = this.createImportElement(href);
        this.attachElement(this.document.head, element);
    }

    waitUntilPolymerReady() {
        this.document.addEventListener('WebComponentsReady', () => {
            this.importElement('polymer', '/bower_components/polymer/polymer.html');
            this.importElement('font-roboto', '/bower_components/font-roboto/roboto.html');
            this.importElement('vertext-list', '/elements/vertexList.html');
            
            let style = this.document.createElement('style');
            style.innerHTML = '* { font-family: Roboto, sans-serif; }';
            this.document.head.appendChild(style);
        });
    }
   
} 
