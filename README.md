# Graph-Algorithms Visualizer
<img width="808" alt="Screenshot 2024-08-02 at 2 39 50 PM" src="https://github.com/user-attachments/assets/cea13c91-0898-44fd-b98d-a14b1d9d9c17">

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
  <img width="798" alt="Screenshot 2024-08-02 at 2 43 36 PM" src="https://github.com/user-attachments/assets/02fe0e8a-09a0-4a1c-9493-7fa79a1d68cb">
<img width="802" alt="Screenshot 2024-08-02 at 2 44 14 PM" src="https://github.com/user-attachments/assets/7544a919-79da-4f8c-90f8-e47dbb2f7983">

* Add Edges: Select "Add an Edge" from the menu, then click on two vertices to connect them. Specify the edge weight.
  <img width="865" alt="Screenshot 2024-08-02 at 2 37 30 PM" src="https://github.com/user-attachments/assets/f7459b47-7414-41cb-9916-74756b5bbb56">
<img width="828" alt="Screenshot 2024-08-02 at 2 37 42 PM" src="https://github.com/user-attachments/assets/8e30a595-5045-4cd0-85bf-eb46eec4a1be">
<img width="808" alt="Screenshot 2024-08-02 at 2 37 50 PM" src="https://github.com/user-attachments/assets/a9615424-8d05-4ec8-8b8e-e5567359c283">

* Remove Vertices/Edges: Use the menu options to remove vertices or edges as needed.

  <img width="831" alt="Screenshot 2024-08-02 at 2 38 00 PM" src="https://github.com/user-attachments/assets/5981ffcb-7abe-4c43-8840-443f84e6e69a">
<img width="814" alt="Screenshot 2024-08-02 at 2 38 18 PM" src="https://github.com/user-attachments/assets/a19f9f5d-1e76-426b-85df-139ed4bbd5ec">

* Reset the Graph: Choose "New" from the File menu to reset the graph.
  <img width="805" alt="Screenshot 2024-08-02 at 2 45 55 PM" src="https://github.com/user-attachments/assets/c7e7efcf-1fa8-4a02-bb56-17a53a51ab43">

## Run Algorithms
* Shortest Path (Dijkstra's Algorithm): Select from the Algorithms menu to compute and display the shortest path from a chosen vertex.
* Graph Traversal (DFS and BFS): Select either Depth-First Search or Breadth-First Search from the Algorithms menu to traverse the graph and view the result.
* Minimum Spanning Tree (Prim's Algorithm): Select Prim's Algorithm from the Algorithms menu to compute and display the minimum spanning tree, showing the parent-child relationships of vertices.
  
## Troubleshooting
* Ensure Proper Compilation: Check that all Java files are compiled and placed in the bin directory.
* Check Console Output: Look for any error messages or stack traces in the console.
* Verify Event Handling: Ensure that the correct events are being triggered for graph interactions and algorithm executions.
