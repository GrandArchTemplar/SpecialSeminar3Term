package test;

import org.junit.Before;
import org.testng.annotations.Test;
import process.Processes;
import tree.BiNode;
import tree.Leaf;
import tree.Node;

import static org.testng.Assert.*;


/**
 * Created by grandarchtemplar on 17/11/16.
 * This code may work
 */
public class ProcessesTest {
    private Node<Double, Character> testNode, node0, node1, node2, node3, node4;
    @Before
    public void setUp() throws Exception {
        node0 = new Leaf<>(0.0);
        node1 = new BiNode<>('+', new Leaf<>(3.0), new Leaf<>(4.0)); //3.0 + 4.0 = 7.0
        node2 = new BiNode<>('-', new Leaf<>(8.0), new Leaf<>(2.5)); //8.0 - 2.5 = 5.5
        node3 = new BiNode<>('*', node1, node2); //7.0 * 5.5 = 38.5
        node4 = new BiNode<>('/', node3, new Leaf<>(3.85)); //38.5 / 3.85 = 10.0
        testNode = new BiNode<>('/', node4, new Leaf<>(-5.0)); //10.0 - -5.0 = -2.0
    }

    private void eval() throws Exception {
        assertEquals(Processes.eval(node0), 0.0);
        assertEquals(Processes.eval(node1), 7.0);
        assertEquals(Processes.eval(node2), 5.5);
        assertEquals(Processes.eval(node3), 38.5);
        assertEquals(Processes.eval(node4), 10.0);
        assertEquals(Processes.eval(testNode), -2.0);
    }

    private void asString() throws Exception {
        assertEquals(Processes.asString(node0), "0.0");
        assertEquals(Processes.asString(node1), "(3.0 + 4.0)");
        assertEquals(Processes.asString(node2), "(8.0 - 2.5)");
        assertEquals(Processes.asString(node3), "((3.0 + 4.0) * (8.0 - 2.5))");
        assertEquals(Processes.asString(node4), "(((3.0 + 4.0) * (8.0 - 2.5)) / 3.85)");
        assertEquals(Processes.asString(testNode), "((((3.0 + 4.0) * (8.0 - 2.5)) / 3.85) / -5.0)");
    }

    private void optimizeAsString() {
        assertEquals(Processes.optimizeAsString(node0), "0.0");
        assertEquals(Processes.optimizeAsString(node1), "3.0 + 4.0");
        assertEquals(Processes.optimizeAsString(node2), "8.0 - 2.5");
        assertEquals(Processes.optimizeAsString(node3), "(3.0 + 4.0) * (8.0 - 2.5)");
        assertEquals(Processes.optimizeAsString(node4), "((3.0 + 4.0) * (8.0 - 2.5)) / 3.85");
        assertEquals(Processes.optimizeAsString(testNode), "(((3.0 + 4.0) * (8.0 - 2.5)) / 3.85) / -5.0");
    }

    private void invert() throws Exception {
        assertEquals(Processes.optimizeAsString(Processes.invert(node0)),
                "-0.0");
        assertEquals(Processes.optimizeAsString(Processes.invert(node1)),
                "-3.0 + -4.0");
        assertEquals(Processes.optimizeAsString(Processes.invert(node2)),
                "-8.0 - -2.5");
        assertEquals(Processes.optimizeAsString(Processes.invert(node3)),
                "(-3.0 + -4.0) * (-8.0 - -2.5)");
        assertEquals(Processes.optimizeAsString(Processes.invert(node4)),
                "((-3.0 + -4.0) * (-8.0 - -2.5)) / -3.85");
        assertEquals(Processes.optimizeAsString(Processes.invert(testNode)),
                "(((-3.0 + -4.0) * (-8.0 - -2.5)) / -3.85) / 5.0");
    }

    @Test
    public void mainTest() throws Exception {
        setUp();
        eval();
        asString();
        optimizeAsString();
        invert();
    }
}