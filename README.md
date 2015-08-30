# Graphs

Inspired by the graph theory, this software is a graphical tool that models
graphs.

## Install

    npm run install

## Run

    npm run server && npm run firefox

## Difference between pure mathematical graphs and graphs in computer science

In pure mathematics a graph is an object that consists of the two sets:

1. Set of vertices, e.g. { A, B, C }
2. Set of edges, where each edge is defined as a set of two vertices which it connects.

From the set theory we know that the order of elements in a set does not matter.
Therefore all mathematical graphs are undirected.

Computer scientists usually use slightly different definition of a graph which
updates the second set with a set of ordered or unordered pairs. If the pair
(e.g. `(A, C)`) is ordered then the graph is directed as `(A, C)` and `(C, A)`
have different meanings.

## Bibliography

1. Trudeau, J. R. (1993) Introduction to Graph Theory. Published by Dover.
2. Weisstein, Eric W. "Simple Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/SimpleGraph.html. Retrieved 7th February 2015.
3. Weisstein, Eric W. "Planar Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/PlanarGraph.html
4. Auslander, L. and Parter, S. "On Imbedding Graphs in the Sphere." J. Math. Mechanics 10, 517-523, 1961.
5. Skiena, S. "Planar Graphs." §6.5 in Implementing Discrete Mathematics: Combinatorics and Graph Theory with Mathematica. Reading, MA: Addison-Wesley, pp. 247-253, 1990.
6. Takao Nishizeki, Norishige Chibalanar (2008) Graphs: Theory and Algorithm. Dover.
