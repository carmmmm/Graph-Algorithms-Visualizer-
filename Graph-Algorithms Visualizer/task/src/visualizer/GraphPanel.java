package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphPanel extends JPanel {
    public GraphPanel() {
        setLayout(null); // Using null layout for absolute positioning

        // Add a mouse listener to handle clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    private void handleMouseClick(MouseEvent e) {
        // Prompt the user for a Vertex ID
        String vertexID = JOptionPane.showInputDialog(this, "Enter Vertex ID (1 character):");

        if (vertexID != null && vertexID.length() == 1) {
            addVertex(vertexID, "VertexLabel " + vertexID, e.getX(), e.getY());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a single character.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addVertex(String vertexID, String labelID, int x, int y) {
        VertexPanel vertex = new VertexPanel(vertexID, labelID);
        vertex.setName("Vertex " + vertexID);
        vertex.setBounds(x - 25, y - 25, 50, 50); // Adjust for centering the vertex
        add(vertex);
        revalidate();
        repaint();
    }
}