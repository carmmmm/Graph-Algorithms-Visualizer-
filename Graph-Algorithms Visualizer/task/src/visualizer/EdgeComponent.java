package visualizer;

import visualizer.VertexPanel;

import javax.swing.*;
import java.awt.*;

public class EdgeComponent extends JComponent {
    private final VertexPanel source;
    private final VertexPanel destination;
    private final int weight;

    public EdgeComponent(VertexPanel source, VertexPanel destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public VertexPanel getSource() {
        return source;
    }

    public VertexPanel getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        Point srcCenter = new Point(source.getX() + source.getWidth() / 2, source.getY() + source.getHeight() / 2);
        Point destCenter = new Point(destination.getX() + destination.getWidth() / 2, destination.getY() + destination.getHeight() / 2);
        g.drawLine(srcCenter.x, srcCenter.y, destCenter.x, destCenter.y);

        // Draw the weight label at the midpoint of the edge
        int midX = (srcCenter.x + destCenter.x) / 2;
        int midY = (srcCenter.y + destCenter.y) / 2;
        g.drawString(String.valueOf(weight), midX, midY);
    }

}