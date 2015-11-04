# dataflow-modeling-for-ENM155

This repository provides a skeleton of the data-flow modelling part in course ENM155. It is written in Java (1.8) and supports:

  * Generating graphs from a JSON file.
  * Performing graph-based algorithms.
  * Visualisation of graphs through dot files.

An example of the entire development process, that is, writing a configuration file, generating a graph from that file and outputting its input/output solution, can be found in the class `Main`. Be sure to read through the example to get overview of how the library works.

## Writing configuration files.

Reading JSON files is supported by the classes found under `enm155/src/json`. Understanding these classes is not required in order to complete the assignment, but helpful comments are included if you wish to extend them for the assignment’s second part. The important part is how to write such files.

An example configuration file is inculded under `enm155/example`, but the general idea is to wrap objects in `{}`, arrays in `[]` and create key-value mappings by writing `“key” : value`. Multiple bindings are supported by separating them with a `,`.

Once the configuration file has been parsed, as shown in `readExample`, a graph can be constructed from its JSON representation, as show by `parseExample`.

## Performing graph-based algorithms.

Once a configuration file has been read and parsed, the generated graph is ready to be used for calculating input/output requirements.

Graphs are specified by the interfaces `Graph`, `Vertex`, and `Edge`, these can all be found under `enm155/src/graph`. An example implementation of the interfaces is included under `enm155/src/graph/directed`, make sure to read through and understand them. Also, an implementation of the input/output algorithm is supplied by the `calculateDemands` method in `MyGraph`.

## Visualisation of graphs.

In order to avoid depending on some Java library for visualising graphs, we make use of the DOT language. DOT is a plain text graph description language and provides a simple way of describing graphs.

DOT files can be generated by running the `generateDot` command found in `MyGraph`. Once generated, these descriptions can be visualised using various programs. A simpler option, that avoids having to install new packages,  is to use [this](http://webgraphviz.com/) webpage as it allows you to view your dot graphs in a browser.
