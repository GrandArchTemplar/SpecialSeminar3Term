package tree;

import java.util.function.Function;

/**
 * Created by grandarchtemplar on 16/11/16.
 * This code may work
 */
public class Leaf<V, T> implements Node<V, T> {
    private V leafInfo;

    public Leaf(V leaf) {
        leafInfo = leaf;
    }

    @Override
    public <R> R process(Function<V, R> leafProcessor, TreeFunction<T, R> biNodeProcessor) {
        return leafProcessor.apply(leafInfo);
    }
}
