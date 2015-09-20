import IRenderable from "./IRenderable";
import assert from "../common/assert";

export default class VertexRenderable extends IRenderable {

    constructor({ 'posX': x, 'posY': y }) {

        super({ 'posX': x, 'posY': y });

        this.radius = 3;
    }

    render(graphRenderer) {

        let ctx = graphRenderer.getContext(graphRenderer.canvas);

        assert(ctx != 1);

        graphRenderer.paint.dot(ctx, this.x, this.y, this.radius);
    }

}
