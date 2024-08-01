package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import visualizer.EdgeComponent;

public class GraphPanel extends JPanel {
    private String mode = "Add a Vertex";
    private VertexPanel selectedVertex;
    private final List<EdgeComponent> edges = new ArrayList<>();
    private final List<VertexPanel> vertices = new ArrayList<>();
    private String algorithm = "";
    private VertexPanel startVertex;

    public GraphPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mode.equals("Add a Vertex")) {
                    String vertexId = JOptionPane.showInputDialog(
                            GraphPanel.this,
                            "Enter Vertex ID:",
                            "Add Vertex",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (vertexId != null && vertexId.length() == 1) {
                        addVertex(e.getX(), e.getY(), vertexId);
                    } else {
                        JOptionPane.showMessageDialog(
                                GraphPanel.this,
                                "Invalid Vertex ID. Please enter a single character.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else if (mode.equals("Add an Edge")) {
                    Component comp = getComponentAt(e.getPoint());
                    if (comp instanceof VertexPanel) {
                        VertexPanel vertex = (VertexPanel) comp;
                        if (selectedVertex == null) {
                            selectedVertex = vertex;
                        } else {
                            String weightStr = JOptionPane.showInputDialog(
                                    GraphPanel.this,
                                    "Enter Edge Weight:",
                                    "Add Edge",
                                    JOptionPane.PLAIN_MESSAGE
                            );
                            if (weightStr != null && weightStr.matches("\\d+")) {
                                int weight = Integer.parseInt(weightStr);
                                addEdge(selectedVertex, vertex, weight);
                                selectedVertex = null;
                            } else {
                                JOptionPane.showMessageDialog(
                                        GraphPanel.this,
                                        "Invalid weight. Please enter a digit.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    }
                } else if (mode.equals("Remove a Vertex")) {
                    Component comp = getComponentAt(e.getPoint());
                    if (comp instanceof VertexPanel) {
                        removeVertex((VertexPanel) comp);
                    }
                } else if (mode.equals("Remove an Edge")) {
                    Component comp = getComponentAt(e.getPoint());
                    if (comp instanceof Edge) {
                        removeEdge((Edge) comp);
                    }
                } else if (algorithm.equals("DFS") || algorithm.equals("BFS") || algorithm.equals("Dijkstra") || algorithm.equals("Prim")) {
                    Component comp = getComponentAt(e.getPoint());
                    if (comp instanceof VertexPanel) {
                        startVertex = (VertexPanel) comp;
                        executeAlgorithm();
                    }
                }
            }
        });
    }

    public void setMode(String mode) {
        this.mode = mode;
        selectedVertex = null; // Reset the selected vertex when mode changes
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    private void addVertex(int x, int y, String vertexId) {
        VertexPanel vertex = new VertexPanel(vertexId);
        vertex.setBounds(x - 25, y - 25, 50, 50);
        vertices.add(vertex);
        add(vertex);
        repaint();
    }

    private void addEdge(VertexPanel source, VertexPanel destination, int weight) {
        System.out.println("Adding edge from " + source.getVertexId() + " to " + destination.getVertexId() + " with weight " + weight);
        Edge edge = new Edge(source, destination, weight);
        edge.updateBounds();
        System.out.println("Edge bounds: " + edge.getBounds());
        EdgeComponent edgeComponent = new EdgeComponent(source, destination, weight);
        edges.add(edgeComponent);
        add(edge);
        edge.setVisible(true);// Ensure this is being called
        revalidate();
        repaint();
    }

    private void removeVertex(VertexPanel vertex) {
        // Remove associated edges
        edges.removeIf(edge -> edge.getSource() == vertex || edge.getDestination() == vertex);
        vertices.remove(vertex);
        remove(vertex);
        repaint();
    }

    private void removeEdge(Edge edge) {
        edges.remove(edge);
        remove(edge);
        repaint();
    }

    public void resetGraph() {
        edges.clear();
        vertices.clear();
        removeAll();
        repaint();
    }

    private void executeAlgorithm() {
        if (startVertex == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a starting vertex.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JLabel resultLabel = ((GraphVisualizer) SwingUtilities.getWindowAncestor(this)).resultLabel;
            resultLabel.setText("Result: Please wait...");

            // Run the selected algorithm
            if (algorithm.equals("DFS")) {
                List<VertexPanel> traversalOrder = depthFirstSearch(startVertex);
                String result = "DFS : " + String.join(" -> ", getVertexIds(traversalOrder));
                resultLabel.setText("Result: " + result);
            } else if (algorithm.equals("BFS")) {
                List<VertexPanel> traversalOrder = breadthFirstSearch(startVertex);
                String result = "BFS : " + String.join(" -> ", getVertexIds(traversalOrder));
                resultLabel.setText("Result: " + result);
            } else if (algorithm.equals("Dijkstra")) {
                Map<VertexPanel, Integer> distances = dijkstraShortestPath(startVertex);
                StringBuilder result = new StringBuilder("Dijkstra's Algorithm : ");
                for (Map.Entry<VertexPanel, Integer> entry : distances.entrySet()) {
                    result.append(entry.getKey().getVertexId()).append("=").append(entry.getValue()).append(", ");
                }
                resultLabel.setText("Result: " + result.toString().replaceAll(", $", ""));
            } else if (algorithm.equals("Prim")) {
                Map<VertexPanel, VertexPanel> mst = primMinimumSpanningTree(startVertex);
                StringBuilder result = new StringBuilder("Prim's Algorithm : ");
                for (Map.Entry<VertexPanel, VertexPanel> entry : mst.entrySet()) {
                    result.append(entry.getKey().getVertexId()).append("=").append(entry.getValue().getVertexId()).append(", ");
                }
                resultLabel.setText("Result: " + result.toString().replaceAll(", $", ""));
            }
        });
    }

    private Map<VertexPanel, VertexPanel> primMinimumSpanningTree(VertexPanel start) {
        Map<VertexPanel, VertexPanel> parentMap = new HashMap<>();
        Map<VertexPanel, Integer> keyMap = new HashMap<>();
        PriorityQueue<VertexPanel> pq = new PriorityQueue<>(Comparator.comparingInt(keyMap::get));
        Set<VertexPanel> inMST = new HashSet<>();

        for (VertexPanel vertex : vertices) {
            keyMap.put(vertex, Integer.MAX_VALUE);
        }
        keyMap.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            VertexPanel u = pq.poll();
            inMST.add(u);

            for (EdgeComponent edge : edges) {
                VertexPanel v = null;
                if (edge.getSource() == u && !inMST.contains(edge.getDestination())) {
                    v = edge.getDestination();
                } else if (edge.getDestination() == u && !inMST.contains(edge.getSource())) {
                    v = edge.getSource();
                }
                if (v != null && keyMap.get(v) > edge.getWeight()) {
                    keyMap.put(v, edge.getWeight());
                    pq.add(v);
                    parentMap.put(v, u);
                }
            }
        }

        return parentMap;
    }

    private List<VertexPanel> depthFirstSearch(VertexPanel start) {
        List<VertexPanel> result = new ArrayList<>();
        Set<VertexPanel> visited = new HashSet<>();
        Stack<VertexPanel> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            VertexPanel vertex = stack.pop();
            if (visited.contains(vertex)) continue;
            visited.add(vertex);
            result.add(vertex);

            for (EdgeComponent edge : edges) {
                if (edge.getSource() == vertex && !visited.contains(edge.getDestination())) {
                    stack.push(edge.getDestination());
                } else if (edge.getDestination() == vertex && !visited.contains(edge.getSource())) {
                    stack.push(edge.getSource());
                }
            }
        }
        return result;
    }

    private List<VertexPanel> breadthFirstSearch(VertexPanel start) {
        List<VertexPanel> result = new ArrayList<>();
        Set<VertexPanel> visited = new HashSet<>();
        Queue<VertexPanel> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            VertexPanel vertex = queue.poll();
            result.add(vertex);

            for (EdgeComponent edge : edges) {
                if (edge.getSource() == vertex && !visited.contains(edge.getDestination())) {
                    queue.add(edge.getDestination());
                    visited.add(edge.getDestination());
                } else if (edge.getDestination() == vertex && !visited.contains(edge.getSource())) {
                    queue.add(edge.getSource());
                    visited.add(edge.getSource());
                }
            }
        }
        return result;
    }

    private Map<VertexPanel, Integer> dijkstraShortestPath(VertexPanel start) {
        Map<VertexPanel, Integer> distances = new HashMap<>();
        PriorityQueue<VertexPanel> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<VertexPanel> visited = new HashSet<>();

        for (VertexPanel vertex : vertices) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            VertexPanel current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            for (EdgeComponent edge : edges) {
                if (edge.getSource() == current && !visited.contains(edge.getDestination())) {
                    int newDist = distances.get(current) + edge.getWeight();
                    if (newDist < distances.get(edge.getDestination())) {
                        distances.put(edge.getDestination(), newDist);
                        queue.add(edge.getDestination());
                    }
                } else if (edge.getDestination() == current && !visited.contains(edge.getSource())) {
                    int newDist = distances.get(current) + edge.getWeight();
                    if (newDist < distances.get(edge.getSource())) {
                        distances.put(edge.getSource(), newDist);
                        queue.add(edge.getSource());
                    }
                }
            }
        }
        return distances;
    }

    private List<String> getVertexIds(List<VertexPanel> vertices) {
        List<String> ids = new ArrayList<>();
        for (VertexPanel vertex : vertices) {
            ids.add(vertex.getVertexId());
        }
        return ids;
    }
}
