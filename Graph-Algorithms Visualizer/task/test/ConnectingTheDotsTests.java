import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.finder.JOptionPaneFinder;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SwingTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.swing.SwingComponent;
import visualizer.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;

@SuppressWarnings("unused")
public class ConnectingTheDotsTests extends SwingTest {

    @SwingComponent(name = "Graph")
    private JPanelFixture graph;

    @SwingComponent(name = "Mode")
    private JLabelFixture modeText;

    @SwingComponent(name = "Add a Vertex")
    private JMenuItemFixture addAVertexMode;

    @SwingComponent(name = "Add an Edge")
    private JMenuItemFixture addAnEdgeMode;

    @SwingComponent(name = "None")
    private JMenuItemFixture noneMode;

    public ConnectingTheDotsTests() {
        super(new MainFrame());
    }

    private final static class Vertex {
        private final String id;
        private final Point point;

        private Vertex(String id, Point point) {
            this.id = id;
            this.point = point;
        }

        public static Vertex of(String id) {
            return new Vertex(id, null);
        }

        public Point point() {
            return point;
        }

        public String id() {
            return id;
        }

        public String name() {
            return "Vertex " + id;
        }

        public String labelName() {
            return "VertexLabel " + id;
        }
    }

    private final static class Edge {
        private final String from;
        private final String to;
        private final int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public static Edge of(String from, String to) {
            return new Edge(from, to, 0);
        }

        public String from() {
            return from;
        }

        public String to() {
            return to;
        }

        public int weight() {
            return weight;
        }

        public String name() {
            return "Edge <" + from + " -> " + to + ">";
        }

        public String labelName() {
            return "EdgeLabel <" + from + " -> " + to + ">";
        }

        public Edge opposite() {
            return new Edge(this.to, this.from, this.weight);
        }
    }

    private enum ComponentType {
        VERTEX("Vertex "),
        EDGE("Edge "),
        EDGE_LABEL("EdgeLabel "),
        VERTEX_LABEL("VertexLabel "),
        ANY("");

        private final String prefix;

        ComponentType(String prefix) {
            this.prefix = prefix;
        }

        public String prefix() {
            return prefix;
        }
    }

    @DynamicTest(order = 1, feedback = "Title should be \"Graph-Algorithms Visualizer\"")
    CheckResult isCorrectFrameTitle() {

        assertThat(frame.getTitle())
                .containsIgnoringCase("Graph-Algorithms Visualizer");

        return correct();
    }

    @DynamicTest(order = 2, feedback = "Size of Frame Should be - (800 x 600)")
    CheckResult isCorrectFrameDimension() {

        Dimension size = frame.getSize();

        assertThat(size.getWidth())
                .isEqualTo(800);
        assertThat(size.getHeight())
                .isEqualTo(600);

        return correct();
    }

    @DynamicTest(order = 3, feedback = "Initially graph panel should not contain anything.")
    CheckResult isEmptyGraph() {
        assertThat(count(ComponentType.ANY))
                .isEqualTo(0);
        return correct();
    }

    @DynamicTest(order = 4, feedback = "Initial Mode must be \"Add a Vertex\"")
    CheckResult isCorrectCurrentMode() {
        modeText.requireText(Pattern.compile(".*add a vertex.*", Pattern.CASE_INSENSITIVE));
        return correct();
    }

    @DynamicTest(order = 5, feedback = "Mode text should change after switching modes")
    CheckResult isModeSwitchFunctioning() {

        addAnEdgeMode.click();
        modeText.requireText(Pattern.compile(".*add an edge.*", Pattern.CASE_INSENSITIVE));

        noneMode.click();
        modeText.requireText(Pattern.compile(".*none.*", Pattern.CASE_INSENSITIVE));

        addAVertexMode.click();
        modeText.requireText(Pattern.compile(".*add a vertex.*", Pattern.CASE_INSENSITIVE));

        return correct();
    }

    private final Vertex[] vertices = new Vertex[]{
            new Vertex("A", new Point(100, 200)),
            new Vertex("B", new Point(400, 300)),
            new Vertex("C", new Point(300, 400)),
            new Vertex("D", new Point(200, 100)),
            new Vertex("E", new Point(700, 200)),
            new Vertex("F", new Point(700, 100))
    };

    @DynamicTest(order = 5)
    CheckResult changeModeToAddAVertex() {
        addAVertexMode.click();
        return correct();
    }

    @DynamicTest(order = 6, feedback = "Could not insert vertex", data = "vertices")
    CheckResult insertVertex(Vertex vertex) {

        getWindow().robot().click(graph.target(), vertex.point());

        JOptionPaneFixture jOptionPaneFixture;
        try {

            jOptionPaneFixture = JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(200)
                    .using(getWindow().robot());

        } catch (WaitTimedOutError e) {
            return wrong("Timeout waiting for a dialog box to open");
        }

        try {

            jOptionPaneFixture.textBox();

        } catch (ComponentLookupException e) {
            return wrong("A text box was not found inside the dialog box");
        }

        try {

            jOptionPaneFixture.okButton();

        } catch (ComponentLookupException e) {
            return wrong("A ok button was not found inside the dialog box");
        }

        try {

            jOptionPaneFixture.cancelButton();

        } catch (ComponentLookupException e) {
            return wrong("A cancel button was not found inside the dialog box");
        }

        try {

            jOptionPaneFixture.requireTitle(Pattern.compile("vertex", Pattern.CASE_INSENSITIVE));

        } catch (Exception ignore) {
            return wrong("Dialog box title should contain \"Vertex\"");
        }


        try {

            jOptionPaneFixture
                    .textBox()
                    .setText(vertex.id());

            jOptionPaneFixture
                    .textBox()
                    .requireText(vertex.id());
        } catch (Exception e) {
            return wrong("Cannot write text '" + vertex.id() + "' in dialog box / Incorrect text after writing. " +
                    "Expected: '" + vertex.id() + "'");
        }

        JOptionPaneFinder
                .findOptionPane()
                .using(getWindow().robot())
                .okButton()
                .click();

        try {

            JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(200)
                    .using(getWindow().robot());

            return wrong("Dialog box did not disappear after clicking ok");
        } catch (WaitTimedOutError ignored) {
        }

        Optional<Component> vOp = getVertex(vertex.id());

        if (vOp.isEmpty()) {
            return wrong(vertex.name() + " was not created. It is not present in graph panel");
        }

        if (!(vOp.get() instanceof JPanel)) {
            throw new WrongAnswer("Each Vertex should be represented by a JPanel.");
        }

        JPanel v = (JPanel) vOp.get();

        if (v.getSize().getHeight() != 50 || v.getSize().getWidth() != 50) {
            Dimension expected = new Dimension(50, 50);
            Dimension got = v.getSize();
            throw new WrongAnswer("Incorrect Vertex Size. Expected: " + expected + ", Got: " + got);
        }

        Optional<Component> vl = getVertexLabel(vertex.id());

        if (vl.isEmpty()) {
            throw new WrongAnswer("Label of " + vertex.name() + " is not Present.");
        }

        if (!(vl.get() instanceof JLabel)) {
            throw new WrongAnswer("Each Vertex label must be represented by JLabel.");
        }

        JLabel vLabel = (JLabel) vl.get();

        if (!Objects.equals(vLabel.getParent().getName(), vertex.name())) {
            throw new WrongAnswer("Each Label of each vertex must be present inside it.");
        }

        if (!Objects.equals(vLabel.getText(), vertex.id())) {
            throw new WrongAnswer("Vertex Label Should Contain ID of Vertex");
        }

        Point got = v.getLocation();
        Point expected = new Point((int) (vertex.point().getX() - 25), (int) (vertex.point().getY() - 25));
        if (!expected.equals(got)) {
            throw new WrongAnswer("Incorrect Position. Expected: " + expected + ", Got: " + got);
        }

        return correct();
    }

    private final Edge[] edges = new Edge[]{
            new Edge("D", "B", 1),
            new Edge("A", "B", 1),
            new Edge("C", "A", 2),
            new Edge("A", "D", 3),
            new Edge("B", "C", 4),
            new Edge("C", "D", 6),
            new Edge("B", "F", 5),
            new Edge("C", "E", -1)
    };

    @DynamicTest(order = 9)
    CheckResult changeModeToAddAnEdge() {
        try {
            addAnEdgeMode.click();
        } catch (ActionFailedException e) {
            return wrong(e.getMessage());
        }
        return correct();
    }

    @DynamicTest(order = 10, data = "edges", feedback = "Could not insert edges")
    CheckResult insertEdge(Edge edge) {

        try {
            getWindow().robot().click(getVertex(edge.from()).orElseThrow());
            getWindow().robot().click(getVertex(edge.to()).orElseThrow());
        } catch (IllegalComponentStateException e) {
            return wrong("Make sure, that vertices are not in a 'hidden' state");
        }
        JOptionPaneFixture jOptionPaneFixture;

        try {
            jOptionPaneFixture = JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());
        } catch (WaitTimedOutError e) {
            return wrong("Clicking on two vertices in the \"Add an Edge\" mode; a new window must pop up prompting " +
                    "for edge weight");
        }

        try {
            jOptionPaneFixture.okButton();
        } catch (ComponentLookupException e) {
            return wrong("A ok button was not found in edge weight prompting input box.");
        }

        try {
            jOptionPaneFixture.cancelButton();
        } catch (ComponentLookupException e) {
            return wrong("A cancel button was not found in edge weight prompting input box.");
        }

        try {
            jOptionPaneFixture.textBox();
        } catch (ComponentLookupException e) {
            return wrong("A text box was not found in edge weight prompting input box.");
        }

        try {
            jOptionPaneFixture
                    .textBox()
                    .setText(edge.weight() + "");

            jOptionPaneFixture
                    .textBox()
                    .requireText(edge.weight() + "");
        } catch (Exception e) {
            return wrong("Could not enter text in the edge weight prompting dialog box.");
        }

        jOptionPaneFixture
                .okButton()
                .click();

        try {
            JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());

            return wrong("Dialog box must close after clicking on ok button.");
        } catch (WaitTimedOutError ignored) {
        }

        Component aTob = getEdge(edge.from(), edge.to()).orElseThrow(() -> new WrongAnswer("\"" + edge.name() + "\" " +
                "was not found!"));

        Component bToa =
                getEdge(edge.to(), edge.from()).orElseThrow(() -> new WrongAnswer("\"" + edge.opposite().name() + "\"" +
                        " was not found!"));

        Component label = getEdgeLabel(edge.from(), edge.to()).orElseThrow(() -> new WrongAnswer("Edge Label which " +
                "should contain the edge weight was not found in graph."));

        if (!(aTob instanceof JComponent) || !(bToa instanceof JComponent))
            throw new WrongAnswer("Edge must be represented by JComponent");

        if (!(label instanceof JLabel))
            throw new WrongAnswer("EdgeLabel must be represented by JLabel");

        if (!Objects.equals(graph.target(), label.getParent()))
            throw new WrongAnswer("Parent of each edge label is the graph itself not the edge.");


        try {
            assertThat(((JLabel) label).getText())
                    .containsIgnoringCase(edge.weight() + "");
        } catch (AssertionError e) {
            return wrong("Edge Label should hold the weight of the respective edge");
        }

        return correct();
    }

    private final String[] incorrectWeights = new String[]{"", " ", "  ", "A", "BC", "!", "Cancel"};

    @DynamicTest(order = 11, data = "incorrectWeights")
    CheckResult incorrectEdgeWeights(String weight) {

        getWindow().robot().click(getVertex("D").orElseThrow());
        getWindow().robot().click(getVertex("F").orElseThrow());

        try {
            JOptionPaneFixture jOptionPaneFixture = JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());
            jOptionPaneFixture.textBox().setText(weight);
            if (!weight.equals("Cancel"))
                jOptionPaneFixture.okButton().click();
            else
                jOptionPaneFixture.cancelButton().click();

            if (!weight.equals("Cancel"))
                JOptionPaneFinder
                        .findOptionPane()
                        .withTimeout(1000)
                        .using(getWindow().robot());

            return correct();
        } catch (WaitTimedOutError e) {
            return wrong("Dialog box should appear again and again if invalid input is given");
        } catch (ComponentLookupException e) {
            return wrong("The program does not work correctly when the test data has an invalid edge weight");
        }
    }

    @DynamicTest(order = 12)
    CheckResult vertexInsertionInIncorrectMode() {

        changeModeToAddAnEdge();
        graph.click();

        try {
            JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());
            return wrong("Vertex should not be inserted in other modes");
        } catch (WaitTimedOutError e) {
            return correct();
        } catch (Exception e) {
            return wrong("Vertex should not be inserted in other modes");
        }
    }

    @DynamicTest(order = 13)
    CheckResult edgeInsertionInIncorrectMode() {

        changeModeToAddAVertex();
        getWindow().robot().click(getVertex("D").orElseThrow());
        getWindow().robot().click(getVertex("F").orElseThrow());

        try {
            JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());
            return wrong("Edge should not be inserted in other modes");
        } catch (WaitTimedOutError e) {
            return correct();
        } catch (Exception e) {
            return wrong("Edge should not be inserted in other modes");
        }
    }

    @DynamicTest(order = 14)
    CheckResult checkVertexCount() {
        int got = count(ComponentType.VERTEX);
        int expected = vertices.length;
        if (expected != got)
            return wrong("Incorrect Vertex Count. Expected = " + expected + ", Got = " + got);
        return correct();
    }

    @DynamicTest(order = 15)
    CheckResult checkEdgeCount() {
        int got = count(ComponentType.EDGE);
        int expected = edges.length * 2;
        if (expected != got)
            return wrong("Incorrect Edge Count. Expected = " + expected + ", Got = " + got);
        return correct();
    }

    @DynamicTest(order = 16)
    CheckResult checkVertexLabelCount() {
        int got = count(ComponentType.VERTEX_LABEL);
        int expected = vertices.length;
        if (expected != got)
            return wrong("Incorrect Vertex Label (id) Count. Expected = " + expected + ", Got = " + got);
        return correct();
    }

    @DynamicTest(order = 17)
    CheckResult checkEdgeLabelCount() {
        int got = count(ComponentType.EDGE_LABEL);
        int expected = edges.length;
        if (expected != got)
            return wrong("Incorrect Edge Label Count. Expected = " + expected + ", Got = " + got);
        return correct();
    }

    private int count(ComponentType type) {
        return (int) getAllComponents(graph.target())
                .stream()
                .filter(it -> it.getName() != null && it.getName().startsWith(type.prefix()))
                .count();
    }

    private Optional<Component> getVertexLabel(String id) {
        String name = Vertex.of(id).labelName();

        return getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst();

    }

    private Optional<Component> getEdgeLabel(String from, String to) {
        String name = Edge.of(from, to).labelName();

        return getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst();

    }

    private Optional<Component> getEdge(String from, String to) {
        String name = Edge.of(from, to).name();

        return getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst();
    }

    private Optional<Component> getVertex(String id) {
        String name = Vertex.of(id).name();

        return getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(name, it.getName()))
                .findFirst();
    }
}
