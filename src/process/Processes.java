package process;

import org.jetbrains.annotations.NotNull;
import tree.BiNode;
import tree.Leaf;
import tree.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

/**
 * Created by grandarchtemplar on 16/11/16.
 * This code may work
 */
public class Processes {
    //array of binary operations
    //can be added any binary operation Double -> Double -> Double
    private static final Map<Character, DoubleBinaryOperator> binaryOps = new HashMap<>();

    static {
        binaryOps.put('+', (a, b) -> a + b);
        binaryOps.put('-', (a, b) -> a - b);
        binaryOps.put('*', (a, b) -> a * b);
        binaryOps.put('/', (a, b) -> a / b);
    }

    //calculates the value of expression which converted in tree
    @NotNull
    public static Double eval(@NotNull Node<Double, Character> root) {
        //leaves no change so used Function.identity()
        return root.process(Function.identity(), (c, a, b) -> binaryOps.get(c).applyAsDouble(a, b));
    }

    ;

    //convert to string with saving order
    @NotNull
    public static String asString(@NotNull Node<Double, Character> root) {
        return root.process(Object::toString, (c, a, b) -> "(" + a + " " + c.toString() + " " + b + ")");
    }

    //delete extra brackets at the beginning and end of the expression
    @NotNull
    public static String optimizeAsString(@NotNull Node<Double, Character> root) {
        //very simple algorithm of optimization length
        String s = Processes.asString(root);
        if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')') {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    //create tree with inverted leaves
    @NotNull
    public static Node<Double, Character> invert(@NotNull Node<Double, Character> root) {
        //java can't catch type R without explicit casting :c
        return root.process(i -> (Node<Double, Character>) new Leaf<Double, Character>(-i), BiNode::new);
    }
}