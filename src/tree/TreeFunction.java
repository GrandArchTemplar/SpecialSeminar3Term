package tree;

/**
 * Created by grandarchtemplar on 16/11/16.
 * This code may work
 */
@FunctionalInterface
public interface TreeFunction<T, R> {
    R apply(T nonLeaf, R leftResult, R rightResult);
}
