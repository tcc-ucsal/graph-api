package br.graphpedia.graphapi.core.config;

public final class Routes {
    public static final String TERM_PREFIX = "/term";
    public static final String CREATE = "/create";
    public static final String FIND_ALL = "/find-all";
    public static final String DELETE_ALL = "/delete/all";

    private Routes() {
        throw new IllegalStateException("Utility class");
    }
}
