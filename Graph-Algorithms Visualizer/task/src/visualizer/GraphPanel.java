package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {
    private String mode = "Add a Vertex";
    private VertexPanel selectedVertex;
    private final List<EdgeComponent> edges = new ArrayList<>();
    private int vertexCount = 0;

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
                    // Detect the edge under the mouse click and remove it
                    Component comp = getComponentAt(e.getPoint());
                    if (comp instanceof EdgeComponent) {
                        removeEdge((EdgeComponent) comp);
                    }
                }
            }
        });
    }

    public void setMode(String mode) {
        this.mode = mode;
        selectedVertex = null; // Reset the selected vertex when mode changes
    }

    private void addVertex(int x, int y, String vertexId) {
        VertexPanel vertex = new VertexPanel(vertexId);
        vertex.setBounds(x - 25, y - 25, 50, 50);
        add(vertex);
        repaint();
    }

    private void addEdge(VertexPanel source, VertexPanel destination, int weight) {
        EdgeComponent edge = new EdgeComponent(source, destination, weight);
        edges.add(edge); // Keep track of edges
        add(edge);
        edge.setBounds(0, 0, getWidth(), getHeight());
        repaint();
    }

    private void removeVertex(VertexPanel vertex) {
        // Remove edges connected to this vertex
        edges.removeIf(edge -> {
            if (edge.getSource() == vertex || edge.getDestination() == vertex) {
                remove(edge);
                return true;
            }
            return false;
        });
        // Remove the vertex itself
        remove(vertex);
        repaint();
    }

    private void removeEdge(EdgeComponent edge) {
        edges.remove(edge);
        remove(edge);
        repaint();
    }

    public void resetGraph() {
        removeAll(); // Remove all components from the panel
        edges.clear(); // Clear the list of edges
        repaint();
    }
}