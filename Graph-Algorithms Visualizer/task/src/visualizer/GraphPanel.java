package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphPanel extends JPanel {
    private final int vertexSize = 50;
    private String mode = "Add a Vertex";
    private List<VertexPanel> vertices = new ArrayList<>();
    private Map<String, VertexPanel> vertexMap = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    public GraphPanel() {
        setLayout(null); // Using null layout for absolute positioning
        setBackground(Color.BLACK); // Set background color of the graph panel to black

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mode.equals("Add a Vertex")) {
                    String vertexID = showInputDialog();
                    if (vertexID != null && isValidVertexID(vertexID)) {
                        addVertex(vertexID, e.getX(), e.getY());
                    } else if (vertexID != null) {
                        JOptionPane.showMessageDialog(GraphPanel.this,
                                "Invalid Vertex ID. Please enter a non-empty single character.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (mode.equals("Add an Edge")) {
                    addEdge(e.getX(), e.getY());
                }
            }
        });
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String showInputDialog() {
        while (true) {
            String input = JOptionPane.showInputDialog(this,
                    "Enter Vertex ID:",
                    "Vertex",
                    JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                return null; // Exit if the user canceled the dialog
            }

            if (isValidVertexID(input)) {
                return input;
            }
        }
    }

    private boolean isValidVertexID(String id) {
        return id != null && !id.trim().isEmpty() && id.length() == 1 && !id.isBlank();
    }

    private void addVertex(String vertexID, int x, int y) {
        VertexPanel vertex = new VertexPanel(vertexID);
        vertex.setName("Vertex " + vertexID);
        vertex.setBounds(x - vertexSize / 2, y - vertexSize / 2, vertexSize, vertexSize);
        add(vertex);
        vertices.add(vertex);
        vertexMap.put(vertexID, vertex);
        revalidate();
        repaint();
    }

    private void addEdge(int x, int y) {
        VertexPanel vertex1 = getNearestVertex(x, y);
        if (vertex1 == null) return;

        VertexPanel vertex2 = getSecondVertex(vertex1, x, y);
        if (vertex2 == null) return;

        String weightStr = JOptionPane.showInputDialog(this, "Enter edge weight:");
        if (weightStr == null || weightStr.isEmpty() || !weightStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Invalid weight. Please enter a numeric value.");
            return;
        }

        int weight = Integer.parseInt(weightStr);

        Edge edge = new Edge(vertex1, vertex2, weight);
        edges.add(edge);
        add(edge);
        revalidate();
        repaint();
    }

    private VertexPanel getNearestVertex(int x, int y) {
        for (VertexPanel vertex : vertices) {
            if (vertex.getBounds().contains(x, y)) {
                return vertex;
            }
        }
        return null;
    }

    private VertexPanel getSecondVertex(VertexPanel firstVertex, int x, int y) {
        VertexPanel vertex2 = getNearestVertex(x, y);
        if (vertex2 == null || vertex2.equals(firstVertex)) {
            JOptionPane.showMessageDialog(this, "Invalid vertex. Please select a different vertex.");
            return null;
        }
        return vertex2;
    }

    public void clearGraph() {
        removeAll();
        vertices.clear();
        vertexMap.clear();
        edges.clear();
        revalidate();
        repaint();
    }
}