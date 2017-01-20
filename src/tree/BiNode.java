package tree;

import java.util.function.Function;

/**
 * Created by grandarchtemplar on 16/11/16.
 * This code may work
 */
public class BiNode<V, T> implements Node<V, T> {
    private T biInfo;
    private Node<V, T> left;
    private Node<V, T> right;

    public BiNode(T biInfo, Node<V, T> left, Node<V, T> right) {
        this.biInfo = biInfo;
        this.left = left;
        this.right = right;
    }



    @Override
    public <R> R process(Function<V, R> leafProcessor, TreeFunction<T, R> biNodeProcessor) {
        return biNodeProcessor.apply(
                biInfo,
                left.process(leafProcessor, biNodeProcessor),
                right.process(leafProcessor, biNodeProcessor)
        );
    }

}
