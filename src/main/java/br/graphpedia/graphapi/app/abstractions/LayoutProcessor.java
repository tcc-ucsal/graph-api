package br.graphpedia.graphapi.app.abstractions;


public abstract class LayoutProcessor<R, T> {

    public static <R, T> R applyLayout(LayoutProcessor<R, T> processor, T root) {
        R result = processor.processLayout(root);
        return result;
    }
    public abstract R processLayout(T root);

}
