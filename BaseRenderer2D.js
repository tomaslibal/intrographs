/*
 * Renderer is a high level interface for drawing into HTML canvas
 */
function BaseRenderer2D(args) {
    "use strict";

    args = args || {};

    this.canvas = args.canvas || null;
}


