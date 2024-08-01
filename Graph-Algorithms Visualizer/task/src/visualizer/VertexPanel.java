package visualizer;

import javax.swing.*;
import java.awt.*;

public class VertexPanel extends JPanel {
    private JLabel idLabel;
    private String vertexId;

    public VertexPanel(String vertexID) {
        setLayout(new BorderLayout());
        this.vertexId = vertexID;

        // Create and configure the JLabel to show the Vertex ID
        idLabel = new JLabel(vertexID, SwingConstants.CENTER);
        idLabel.setName("VertexLabel " + vertexID); // Set the name for the JLabel
        idLabel.setForeground(Color.BLACK); // Set the label color to black for visibility
        add(idLabel, BorderLayout.CENTER); // Add label to center of the panel

        // Customize the appearance of the JPanel
        setOpaque(false); // Make sure the panel itself is transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE); // Set the vertex circle color to white for visibility
        g2d.fillOval(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.BLACK); // Set the color for the vertex ID label
        g2d.drawOval(0, 0, getWidth(), getHeight()); // Draw the outline of the circle
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50); // Set preferred size to 50x50
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public String getVertexId() {
        return vertexId;
    }
}