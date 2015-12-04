# Graph language for the interpreter

Intrographs come with a console that can interpret text input which is written in the language described hereafter. I treat this language as a regular language and it can be thought of as a series of expressions. The interpreter then parses these expressions into function calls.

## Definitions

### Objects

- `graph`
- `vertex`
- `edge`

### Actions

- `add`
- `remove`

### Values

- `(val1, val2, ..., valN)`

## Basic syntax

The expressions are formed by combining objects, actions and values with the dot notation:

	graph.add.vertex('Bar', 'Foo Baz', 'Que')

*One word values which have no whitespace have optional quotes*. Therefore it is permissible to write `graph.add.edge(A, B)`. If there is whitespace the quotes must be included around the value.

*Escaping* is currently not defined.

## Another examples

Remove an edge incident on vertices X and Y

	graph.remove.edge(X, Y)