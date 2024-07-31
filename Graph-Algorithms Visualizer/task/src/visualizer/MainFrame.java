package visualizer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        GraphPanel graphPanel = new GraphPanel();
        graphPanel.setName("Graph");
        graphPanel.setBackground(Color.BLACK);
        add(graphPanel);

        getContentPane().setBackground(Color.BLACK);

        setVisible(true);
    }
}