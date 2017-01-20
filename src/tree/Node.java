package tree;

import java.util.function.Function;

/**
 * Created by grandarchtemplar on 16/11/16.
 * This code may work
 */
@FunctionalInterface
public interface Node<V, T> {
    <R> R process(Function<V, R> leafProcessor, TreeFunction<T, R> biNodeProcessor);
}
