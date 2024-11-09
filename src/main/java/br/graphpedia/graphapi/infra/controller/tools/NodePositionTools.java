package br.graphpedia.graphapi.infra.controller.tools;

import br.graphpedia.graphapi.infra.controller.responses.ConnectionWithResponse;
import br.graphpedia.graphapi.infra.controller.responses.Coordinates;
import br.graphpedia.graphapi.infra.controller.responses.SimpleTermResponse;
import br.graphpedia.graphapi.infra.controller.responses.TermResponse;

import java.util.Set;

public class NodePositionTools {

    private NodePositionTools(){}

    public static void radialTreeLayout(TermResponse root) {
        preOrder(root, 0, 0, 2 * Math.PI);
    }

    public static void preOrder(TermResponse vertex, int layer, double minA, double maxA) {
        vertex.setCoordinates(preOrderHelper(vertex.getConnectionWiths(), layer, minA, maxA));
    }

    private static void preOrder(SimpleTermResponse simpleTerm, int layer, double minA, double maxA) {
        simpleTerm.setCoordinates(preOrderHelper(simpleTerm.getConnectionWiths(), layer, minA, maxA));
    }

    private static <T extends ConnectionWithResponse> Coordinates preOrderHelper(Set<T> vertexConnections, int layer, double minA, double maxA) {
        double angle = (minA + maxA) / 2;
        double distance = 100.0 * layer;

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

                preOrder(childTerm, (layer + 1), left, right);

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
