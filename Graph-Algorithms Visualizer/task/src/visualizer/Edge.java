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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        Point p1 = new Point(vertex1.getX() + vertex1.getWidth() / 2, vertex1.getY() + vertex1.getHeight() / 2);
        Point p2 = new Point(vertex2.getX() + vertex2.getWidth() / 2, vertex2.getY() + vertex2.getHeight() / 2);

        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

        // Draw weight label
        int labelX = (p1.x + p2.x) / 2;
        int labelY = (p1.y + p2.y) / 2;
        g2d.drawString(Integer.toString(weight), labelX, labelY);
    }
}
