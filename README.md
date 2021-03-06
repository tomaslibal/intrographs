# Graphs

Inspired by the graph theory, this software is a graphical tool that renders graph embeddings on a plane.

The goal is to create a program that can model various graphs, lets the user
to modify them and explore their properties.

## Features

Brief overview of the features

![Intrographs Main Screen](http://libal.eu/imghost/intrographs/intrographs2_1920.png)

### Graphs

- add/remove vertex
- add/remove edge
- update vertex (incl. color)
- show labels
- translate vertices

### Layouts

- force directed algorithm
- random layout

### Files

- save/load graph

### Running the project

You will need `maven` and `jdk1.8.0_xx` to compile and package the project. To create a jar file with dependencies, execute
the maven package command in the root directory of the project

```
mvn package
```

This should place a file `intrographs-jar-with-dependencies.jar` which you can invoke from the command line and run the project

```
java -jar target/intrographs-jar-with-dependencies.jar
```

Alternatively, using IntelliJ IDE, execute the eu.libal.intrographs.Main class.

#### Tests

Run the tests using Maven

```
mvn test
```

## Difference between pure mathematical graphs and graphs in computer science

In pure mathematics a graph is an object that consists of the two sets:

1. Non-empty set of vertices, e.g. { A, B, C }
2. Set of edges, where each edge is defined as a subset of two vertices on which the edge is incident. This set may be empty.

From the set theory we know that the order of elements in a set does not matter.
Therefore all mathematical graphs are undirected.

Computer scientists usually use slightly different definition of a graph which
updates the second set with a set of ordered or unordered pairs. If the pair
(e.g. `(A, C)`) is ordered then the graph is directed as `(A, C)` and `(C, A)`
have different meanings in that case.

## Other tools like Gephi

[Gephi](https://gephi.github.io) is my favorite tool for graph network analysis. Although some (intended) features of `intrographs` are similar, I'm not trying to recreate Gephi. Instead of using it as a data lab for analyzing real-life data, I would like intrographs to be more an educational software which lets users run algorithms like A*, depth-first-search, etc. on various graphs.

## How-to's

This is a Java project with Maven dependencies.

### Install dependencies

mvn install

### Build

### Run

Run the Main class which contains main entry point.

### Tests


## Bibliography

1. Trudeau, J. R. (1993) *Introduction to Graph Theory.* Dover.
2. Weisstein, Eric W. "Simple Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/SimpleGraph.html. Retrieved 7th February 2015.
3. Weisstein, Eric W. (2009) "Planar Graph." From MathWorld--A Wolfram Web Resource. http://mathworld.wolfram.com/PlanarGraph.html
4. Auslander, L. and Parter, S. "On Embedding Graphs in the Sphere." J. Math. Mechanics 10, 517-523, 1961.
5. Skiena, S. "Planar Graphs." §6.5 in Implementing Discrete Mathematics: Combinatorics and Graph Theory with Mathematica. Reading, MA: Addison-Wesley, pp. 247-253, 1990.
6. Takao Nishizeki, Norishige Chibalanar (2008) *Graphs: Theory and Algorithm.* Dover.
7. Douglas B. West (1996) *Introduction to Graph Theory.* Prentice Hall.
8. Ming-Yang Kao (2008) *Encyclopedia of Algorithms.* Springer Science & Business Media.
9. Balakrishnan, V. K. (1996) *Introductory Discrete Mathematics.* Reprint, USA: Dover.
10. Chiba, N. and Nishizeki T. (2008) *Planar Graphs: Theory and Algorithms.* Reprint, USA: Courier Corporation.
11. Moehring, H. R. (1997) "Graph-Theoretic Concepts in Computer Science: 23rd International Workshop, WG'97, Berlin, Germany" June 18-20, 1997. Proceedings, Volume 23
12. Skiena, S. (2009) *The Algorithm Design Manual.* London: Springer.
13. Tamassia, R. (2013) *Handbook of Graph Drawing and Visualisation*. CRC Press.
