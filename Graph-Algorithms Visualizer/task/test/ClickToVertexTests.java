import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.finder.JOptionPaneFinder;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.fixture.JPanelFixture;
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
public class ClickToVertexTests extends SwingTest {

    @SwingComponent(name = "Graph")
    private JPanelFixture graph;

    public ClickToVertexTests() {
        super(new MainFrame());
    }

    private final static class Vertex {
        private final String id;
        private final Point point;

        private Vertex(String id, Point point) {
            this.id = id;
            this.point = point;
        }

        public Point point() {
            return point;
        }

        public String id() {
            return id;
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
        assertThat(getAllComponents(graph.target()).size())
                .isEqualTo(0);
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

    @DynamicTest(order = 4, feedback = "Could not insert vertex", data = "vertices")
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

        } catch (AssertionError ignore) {
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
            return wrong("Cannot write text '" + vertex.id() + "' in dialog box / Incorrect text after writing. Expected: '" + vertex.id() + "'");
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

        Optional<Component> vOp = getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(it.getName(), "Vertex " + vertex.id()))
                .findFirst();

        if (vOp.isEmpty()) {
            return wrong("Vertex " + vertex.id() + " was not created. It is not present in graph panel");
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

        Optional<Component> vl = getAllComponents(graph.target())
                .stream()
                .filter(it -> Objects.equals(it.getName(), "VertexLabel " + vertex.id()))
                .findFirst();

        if (vl.isEmpty()) {
            throw new WrongAnswer("Label of Vertex " + vertex.id() + " is not Present.");
        }

        if (!(vl.get() instanceof JLabel)) {
            throw new WrongAnswer("Each Vertex label must be represented by JLabel.");
        }

        JLabel vLabel = (JLabel) vl.get();

        if (!Objects.equals(vLabel.getParent().getName(), "Vertex " + vertex.id())) {
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

    String[] incorrectIds = new String[]{"", " ", "  ", "AB", "ABC"};

    @DynamicTest(order = 5, data = "incorrectIds", feedback = "Incorrect state after inserting illegal vertex id.")
    CheckResult incorrectVertexInsertion(String id) {
        try {

            graph.click();

            JOptionPaneFixture jOptionPaneFixture = JOptionPaneFinder
                    .findOptionPane()
                    .using(getWindow().robot());
            jOptionPaneFixture
                    .textBox()
                    .setText(id);
            jOptionPaneFixture
                    .okButton()
                    .click();

            JOptionPaneFinder
                    .findOptionPane()
                    .using(getWindow().robot());

            return correct();

        } catch (WaitTimedOutError e) {
            return wrong("Timeout waiting for new dialog box to appear");
        } catch (ComponentLookupException e) {
            return wrong("");
        }
    }

    @DynamicTest(order = 6, feedback = "Cancel button should close the dialog box and won't appear again. No new vertex shall be created")
    CheckResult isCancelButtonFunctioning() {
        graph.click();

        JOptionPaneFixture jOptionPaneFixture = JOptionPaneFinder
                .findOptionPane()
                .withTimeout(1000)
                .using(getWindow().robot());

        jOptionPaneFixture
                .textBox()
                .setText("A");
        jOptionPaneFixture
                .cancelButton()
                .click();

        try {
            JOptionPaneFinder
                    .findOptionPane()
                    .withTimeout(1000)
                    .using(getWindow().robot());
        } catch (WaitTimedOutError e) {
            return correct();
        }

        return wrong("Dialog box should not appear after clicking on cancel button.");
    }

    @DynamicTest(order = 7, feedback = "Incorrect number of vertex present")
    CheckResult checkVertexCount() {
        int got = getAllComponents(graph.target()).size() / 2;
        int expected = 6;

        if (got != expected) {
            throw new WrongAnswer("Expect Vertex Count = " + expected + ", but got = " + got);
        }

        return correct();
    }
}
