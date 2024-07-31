package visualizer;

import javax.swing.*;
import java.awt.*;

public class VertexPanel extends JPanel {
    private JLabel idLabel;
    private String vertexId;

    public VertexPanel(String vertexID) {
        setLayout(new BorderLayout());
        this.vertexId = vertexID;
        // Create the JLabel that shows the Vertex ID
        idLabel = new JLabel(vertexID, SwingConstants.CENTER);
        idLabel.setName("VertexLabel " + vertexID); // Set the name for the JLabel
        idLabel.setForeground(Color.BLACK); // Set the label color to black for visibility
        add(idLabel);

        // Customize the appearance of the JPanel
        setOpaque(false); // Make sure the panel itself is transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE); // Set the vertex circle color to white for visibility
        g.fillOval(0, 0, getWidth(), getHeight());
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