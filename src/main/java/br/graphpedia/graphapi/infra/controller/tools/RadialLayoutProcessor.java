package br.graphpedia.graphapi.infra.controller.tools;

import br.graphpedia.graphapi.app.abstractions.LayoutProcessor;
import br.graphpedia.graphapi.infra.controller.responses.ConnectionWithResponse;
import br.graphpedia.graphapi.infra.controller.responses.Coordinates;
import br.graphpedia.graphapi.infra.controller.responses.SimpleTermResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;

import java.util.Set;

public class RadialLayoutProcessor extends LayoutProcessor<TermResponse> {

    public RadialLayoutProcessor() {}

    @Override
    public void processLayout(TermResponse root) {
        processNode(root);
    }

    private void processNode(TermResponse vertex) {
        vertex.setCoordinates(getCoordinatesHelper(vertex.getConnectionWiths(), 0, 0, TWO_PI));
    }

    private static void processNode(SimpleTermResponse simpleTerm, int layer, double minA, double maxA) {
        simpleTerm.setCoordinates(getCoordinatesHelper(simpleTerm.getConnectionWiths(), layer, minA, maxA));
    }


    private static <T extends ConnectionWithResponse> Coordinates getCoordinatesHelper(Set<T> vertexConnections, int layer, double minA, double maxA) {
        double angle = (minA + maxA) / 2;
        double distance = BASE_DISTANCE * layer;

        if (layer > 0) {
            double arc = Math.acos(layer / (layer + 1.0));
            minA = Math.max(minA, (angle - arc));
            maxA = Math.min(maxA, (angle + arc));
        }

        double left = minA;
        if (vertexConnections != null) {
            for (T conn : vertexConnections) {
                SimpleTermResponse childTerm = conn.getTerm();
                double portion = calculatePortion(childTerm.getConnectionWiths(), vertexConnections);
                double right = left + (portion * (maxA - minA));

                processNode(childTerm, (layer + 1), left, right);

                left = right;
            }
        }

        return new Coordinates(getXCoord(distance, angle), getYCoord(distance, angle));
    }


    private static double getYCoord(double distance, double angle) {
        return distance * Math.sin(angle);
    }

    private static double getXCoord(double distance, double angle) {
        return distance * Math.cos(angle);
    }

    private static double calculatePortion(Set<ConnectionWithResponse> childTermConn, Set<? extends ConnectionWithResponse> vertexConn) {
        double childSize = getConnectionsSize(childTermConn);
        double vertexSize = getConnectionsSize(vertexConn);

        return vertexSize > 1 ? (childSize / (vertexSize - 1.0)) : 0;
    }

    private static double getConnectionsSize(Set<? extends ConnectionWithResponse> connectionWiths) {
        return connectionWiths != null ? (connectionWiths.size() + 1) : 1;
    }
}
