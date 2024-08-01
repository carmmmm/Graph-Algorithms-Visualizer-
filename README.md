# Graph-Algorithms Visualizer


## Overview
The Graph-Algorithms Visualizer is a Java Swing application designed to demonstrate and experiment with various graph algorithms. The application provides an interactive platform for creating and manipulating graphs, and visualizing results of algorithms such as shortest path, depth-first search, breadth-first search, and minimum spanning tree.

## Key Features
### Graph Visualization
* Interactive Graph Creation: Add vertices and edges by clicking on the canvas.
* Dynamic Edge Weights: Specify and view weights on edges.
### Algorithm Demonstration
* Shortest Path (Dijkstra's Algorithm): Visualize the shortest path between vertices.
* Graph Traversal (DFS and BFS): Explore the graph using depth-first and breadth-first search.
* Minimum Spanning Tree (Prim's Algorithm): Find and display the minimum spanning tree of the graph.
## Technologies Used
* Java Swing: For the graphical user interface.
* Java AWT: For handling graphics and events.
## Installation and Setup
1. Clone the Repository
```
git clone https://github.com/carmmmm/graph-algorithms-visualizer.git
```
2. Navigate to the Project Directory
```
cd graph-algorithms-visualizer
```
3. Compile and Run the Application
```
javac -d bin src/**/*.java
```
4. Run the Application
```
java -cp bin visualizer.ApplicationRunner
```
5. Accessing the Application
* Launch the Application
* Run the compiled application to open the Graph-Algorithms Visualizer.
## Interact with the Graph
* Add Vertices: Click on the graph area to create vertices. A dialog will prompt you to enter a vertex ID.
* Add Edges: Select "Add an Edge" from the menu, then click on two vertices to connect them. Specify the edge weight.
* Remove Vertices/Edges: Use the menu options to remove vertices or edges as needed.
* Reset the Graph: Choose "New" from the File menu to reset the graph.
## Run Algorithms
* Shortest Path (Dijkstra's Algorithm): Select from the Algorithms menu to compute and display the shortest path from a chosen vertex.
* Graph Traversal (DFS and BFS): Select either Depth-First Search or Breadth-First Search from the Algorithms menu to traverse the graph and view the result.
* Minimum Spanning Tree (Prim's Algorithm): Select Prim's Algorithm from the Algorithms menu to compute and display the minimum spanning tree, showing the parent-child relationships of vertices.
  
## Troubleshooting
* Ensure Proper Compilation: Check that all Java files are compiled and placed in the bin directory.
* Check Console Output: Look for any error messages or stack traces in the console.
* Verify Event Handling: Ensure that the correct events are being triggered for graph interactions and algorithm executions.
