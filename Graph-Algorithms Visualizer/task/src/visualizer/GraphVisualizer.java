package visualizer;

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
        JMenu fileMenu = new JMenu("File");

        JMenuItem addVertexItem = new JMenuItem("Add a Vertex");
        JMenuItem addEdgeItem = new JMenuItem("Add an Edge");
        JMenuItem removeVertexItem = new JMenuItem("Remove a Vertex");
        JMenuItem removeEdgeItem = new JMenuItem("Remove an Edge");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem exitItem = new JMenuItem("Exit");

        modeMenu.add(addVertexItem);
        modeMenu.add(addEdgeItem);
        modeMenu.add(removeVertexItem);
        modeMenu.add(removeEdgeItem);

        fileMenu.add(newItem);
        fileMenu.add(exitItem);

        menuBar.add(modeMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Set up the mode label
        modeLabel = new JLabel("Mode: Add a Vertex");
        modeLabel.setName("Mode");
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

        removeVertexItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = "Remove a Vertex";
                modeLabel.setText("Mode: Remove a Vertex");
                graphPanel.setMode(mode);
            }
        });

        removeEdgeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = "Remove an Edge";
                modeLabel.setText("Mode: Remove an Edge");
                graphPanel.setMode(mode);
            }
        });

        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphPanel.resetGraph();
                mode = "Add a Vertex"; // Reset mode to default
                modeLabel.setText("Mode: Add a Vertex");
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}