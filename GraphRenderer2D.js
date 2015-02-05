/*
 * This renderer extends the BaseRenderer2D and knows how to render graphs in
 * a 2D space.
 *
 */
function graphRenderer2D(BaseRenderer2D) {
    "use strict";

    if("function" !== typeof BaseRenderer2D) {
        return new Error("BaseRenderer2D must be a function");
    }

    this.renderer = new BaseRenderer2D();
}
