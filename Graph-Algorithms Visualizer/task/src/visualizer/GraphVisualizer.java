package visualizer;
import visualizer.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphVisualizer extends JFrame {
    private JLabel modeLabel;
    private String mode = "Add a Vertex";
    private GraphPanel graphPanel;

    public GraphVisualizer() {
        setTitle("Graph Algorithms Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");

        JMenuItem addVertexItem = new JMenuItem("Add a Vertex");
        JMenuItem addEdgeItem = new JMenuItem("Add an Edge");
        JMenuItem noneItem = new JMenuItem("None");

        modeMenu.add(addVertexItem);
        modeMenu.add(addEdgeItem);
        modeMenu.add(noneItem);

        menuBar.add(modeMenu);
        setJMenuBar(menuBar);

        // Set up the mode label
        modeLabel = new JLabel("Mode: Add a Vertex");
        add(modeLabel, BorderLayout.NORTH);

        // Set up the graph panel
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Handle menu actions
        addVertexItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = "Add a Vertex";
                modeLabel.setText("Mode: Add a Vertex");
                graphPanel.setMode(mode);
            }
        });

        addEdgeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = "Add an Edge";
                modeLabel.setText("Mode: Add an Edge");
                graphPanel.setMode(mode);
            }
        });

        noneItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = "None";
                modeLabel.setText("Mode: None");
                graphPanel.setMode(mode);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphVisualizer().setVisible(true);
            }
        });
    }
}