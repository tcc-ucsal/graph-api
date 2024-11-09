package br.graphpedia.graphapi.app.abstractions;

public abstract class LayoutProcessor<T> {

    public static final double TWO_PI = 2 * Math.PI;
    public static final double BASE_DISTANCE = 100.0;

    public static <T> void applyLayout(LayoutProcessor<T> processor, T root) {
        processor.preProcess(root);
        processor.processLayout(root);
        processor.postProcess(root);
    }
    protected void preProcess(T root) {
    }

    public abstract void processLayout(T root);

    protected void postProcess(T root) {
    }

}
