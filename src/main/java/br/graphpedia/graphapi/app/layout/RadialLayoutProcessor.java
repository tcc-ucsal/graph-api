package br.graphpedia.graphapi.app.layout;

import br.graphpedia.graphapi.core.entity.ConnectionWith;
import br.graphpedia.graphapi.core.entity.Term;
import br.graphpedia.graphapi.infra.controller.response.Coordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RadialLayoutProcessor extends LayoutProcessor<Map<String, Coordinates>, Term> {

    public static final double TWO_PI = 2 * Math.PI;
    public static final double BASE_DISTANCE = 100.0;


    @Override
    public Map<String, Coordinates> processLayout(Term root) {
        Map<String, Coordinates> result = new HashMap<>();
        processNode(root, result);
        return result;
    }

    private void processNode(Term root, Map<String, Coordinates> result) {
        result.put(root.getTitle(), getCoordinatesHelper(root.getConnectionWiths(), 0, 0, TWO_PI, result));
    }

    private static void processNode(Term term, int layer, double minA, double maxA, Map<String, Coordinates> result) {
        result.put(term.getTitle(), getCoordinatesHelper(term.getConnectionWiths(), layer, minA, maxA, result));
    }


    private static Coordinates getCoordinatesHelper(Set<ConnectionWith> vertexConnections, int layer, double minA, double maxA, Map<String, Coordinates> result) {
        double angle = (minA + maxA) / 2;
        double distance = BASE_DISTANCE * layer;

        if (layer > 0) {
            double arc = Math.acos(layer / (layer + 1.0));
            minA = Math.max(minA, (angle - arc));
            maxA = Math.min(maxA, (angle + arc));
        }

        double left = minA;
        if (vertexConnections != null) {
            for (ConnectionWith conn : vertexConnections) {
                Term childTerm = conn.getTargetTerm();
                double portion = calculatePortion(childTerm.getConnectionWiths(), vertexConnections);
                double right = left + (portion * (maxA - minA));

                processNode(childTerm, (layer + 1), left, right, result);

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

    private static double calculatePortion(Set<ConnectionWith> childTermConn, Set<ConnectionWith> vertexConn) {
        double childSize = getConnectionsSize(childTermConn);
        double vertexSize = getConnectionsSize(vertexConn);

        return vertexSize > 1 ? (childSize / (vertexSize - 1.0)) : 0;
    }

    private static double getConnectionsSize(Set<ConnectionWith> connectionWiths) {
        return connectionWiths != null ? (connectionWiths.size() + 1) : 1;
    }

}
