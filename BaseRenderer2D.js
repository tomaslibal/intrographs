/*
 * Renderer is a high level interface for drawing into HTML canvas
 */
function Renderer(args) {
    "use strict";

    args = args || {};

    this.canvas = args.canvas || null;
}


