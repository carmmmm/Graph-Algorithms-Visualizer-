package visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphVisualizer extends JFrame {
    private JLabel modeLabel;
    public JLabel resultLabel;
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
        JMenu algorithmMenu = new JMenu("Algorithms");
        JMenu fileMenu = new JMenu("File");

        JMenuItem addVertexItem = new JMenuItem("Add a Vertex");
        JMenuItem addEdgeItem = new JMenuItem("Add an Edge");
        JMenuItem noneItem = new JMenuItem("None");
        JMenuItem removeVertexItem = new JMenuItem("Remove a Vertex");
        JMenuItem removeEdgeItem = new JMenuItem("Remove an Edge");
        JMenuItem newFileItem = new JMenuItem("New");
        JMenuItem exitFileItem = new JMenuItem("Exit");
        JMenuItem dfsItem = new JMenuItem("Depth-First Search");
        JMenuItem bfsItem = new JMenuItem("Breadth-First Search");
        JMenuItem dijkstraItem = new JMenuItem("Dijkstra's Algorithm");
        JMenuItem primItem = new JMenuItem("Prim's Algorithm");

        modeMenu.add(addVertexItem);
        modeMenu.add(addEdgeItem);
        modeMenu.add(noneItem);
        modeMenu.add(removeVertexItem);
        modeMenu.add(removeEdgeItem);

        algorithmMenu.add(dfsItem);
        algorithmMenu.add(bfsItem);
        algorithmMenu.add(dijkstraItem);
        algorithmMenu.add(primItem);

        fileMenu.add(newFileItem);
        fileMenu.add(exitFileItem);

        menuBar.add(modeMenu);
        menuBar.add(algorithmMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Set up the mode label
        modeLabel = new JLabel("Mode: Add a Vertex");
        modeLabel.setName("Mode");
        add(modeLabel, BorderLayout.NORTH);

        // Set up the result label
        resultLabel = new JLabel("Result: ");
        add(resultLabel, BorderLayout.SOUTH);

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

        newFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphPanel.resetGraph();
                resultLabel.setText("Result: ");
            }
        });

        exitFileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dfsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runTraversal("DFS");
            }
        });

        bfsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runTraversal("BFS");
            }
        });

        dijkstraItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runShortestPath();
            }
        });

        primItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runMST();
            }
        });
    }

    private void runTraversal(String algorithm) {
        mode = "None";
        modeLabel.setText("Mode: None");
        resultLabel.setText("Result: Please choose a starting vertex");

        // Start algorithm execution after vertex selection
        graphPanel.setAlgorithm(algorithm);
    }

    private void runShortestPath() {
        mode = "None";
        modeLabel.setText("Mode: None");
        resultLabel.setText("Result: Please choose a starting vertex");

        // Start Dijkstra's algorithm execution after vertex selection
        graphPanel.setAlgorithm("Dijkstra");
    }

    private void runMST() {
        mode = "None";
        modeLabel.setText("Mode: None");
        resultLabel.setText("Result: Please choose a starting vertex");

        // Start Prim's algorithm execution after vertex selection
        graphPanel.setAlgorithm("Prim");
    }
}