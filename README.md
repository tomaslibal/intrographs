# Graphs

Inspired by the graph theory, this software is a graphical tool that models
graphs.

The goal is to create a program that prints various graphs, lets the user
to modify them, and it should also be able to give information on some graph
properties like what type of graph it is, whether it is planar and so on.

## Difference between pure mathematical graphs and graphs in computer science

In pure mathematics a graph is an object that consists of the two sets:

1. Set of vertices, e.g. { A, B, C }
2. Set of edges, where each edge is defined as a set of two vertices which it connects.

From the set theory we know that the order of elements in a set does not matter.
Therefore all mathematical graphs are undirected.

Computer scientists usually use slightly different definition of a graph which
updates the second set with a set of ordered or unordered pairs. If the pair
(e.g. `(A, C)`) is ordered then the graph is directed as `(A, C)` and `(C, A)`
have different meanings in that case.

## How-to's

This is a JavaScript software tested to run in a browser. Because this is a development version, you will have to build the target on your machine.

1. Install dependencies
2. Run a local server
3. Launch in a browser

### Install dependencies

Use `npm` or install dependencies individually in `node_modules`. Your system should have Node.js installed and have it in the PATH environmental variable (on *NIX systems). Additionally, Python version 2.x is used for running a local HTTP server but it is not necessary.

    npm run install

### Run

This launches a Python SimpleHTTPServer on the local machine's http://0.0.0.0:8080. Visit `http://0.0.0.0:8080/demo/`
in a browser to use the demo.

    npm run server

### Tests

    npm run test

## Bibliography

1. Trudeau, J. R. (1993) *Introduction to Graph Theory.* Dover.
2. Weisstein, Eric W. "Simple Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/SimpleGraph.html. Retrieved 7th February 2015.
3. Weisstein, Eric W. (2009) "Planar Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/PlanarGraph.html
4. Auslander, L. and Parter, S. "On Imbedding Graphs in the Sphere." J. Math. Mechanics 10, 517-523, 1961.
5. Skiena, S. "Planar Graphs." §6.5 in Implementing Discrete Mathematics: Combinatorics and Graph Theory with Mathematica. Reading, MA: Addison-Wesley, pp. 247-253, 1990.
6. Takao Nishizeki, Norishige Chibalanar (2008) *Graphs: Theory and Algorithm.* Dover.
7. Douglas B. West (1996) *Introduction to Graph Theory.* Prentice Hall.
8. Ming-Yang Kao (2008) *Encyclopedia of Algorithms.* Springer Science & Business Media.
9. Balakrishnan, V. K. (1996) *Introductory Discrete Mathematics.* Reprint, USA: Dover.
