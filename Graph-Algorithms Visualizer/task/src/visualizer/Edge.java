package visualizer;

import javax.swing.*;
import java.awt.*;

public class Edge extends JComponent {
    private VertexPanel vertex1;
    private VertexPanel vertex2;
    private int weight;

    public Edge(VertexPanel vertex1, VertexPanel vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        setName("Edge " + vertex1.getVertexId() + " -> " + vertex2.getVertexId());
        updateBounds();
    }

    public void updateBounds() {
        int x1 = vertex1.getX() + vertex1.getWidth() / 2;
        int y1 = vertex1.getY() + vertex1.getHeight() / 2;
        int x2 = vertex2.getX() + vertex2.getWidth() / 2;
        int y2 = vertex2.getY() + vertex2.getHeight() / 2;
        setBounds(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

        int x1 = vertex1.getX() + vertex1.getWidth() / 2 - getX();
        int y1 = vertex1.getY() + vertex1.getHeight() / 2 - getY();
        int x2 = vertex2.getX() + vertex2.getWidth() / 2 - getX();
        int y2 = vertex2.getY() + vertex2.getHeight() / 2 - getY();

        g2d.drawLine(x1, y1, x2, y2);

        // Draw weight label
        int labelX = (x1 + x2) / 2;
        int labelY = (y1 + y2) / 2;
        g2d.drawString(Integer.toString(weight), labelX, labelY);
    }
}
