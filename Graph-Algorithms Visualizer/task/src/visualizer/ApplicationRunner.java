package visualizer;

import javax.swing.*;

public class ApplicationRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GraphVisualizer().setVisible(true);
            }
            });
        }
    }