package br.graphpedia.graphapi.app.interfaces;


public abstract class LayoutProcessor<R, T> {

    public static <R, T> R applyLayout(LayoutProcessor<R, T> processor, T root) {
        return processor.processLayout(root);
    }
    public abstract R processLayout(T root);

}
