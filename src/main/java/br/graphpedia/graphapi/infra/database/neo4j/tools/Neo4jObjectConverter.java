package br.graphpedia.graphapi.infra.database.neo4j.tools;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Neo4jObjectConverter {

    public static Map<String, Object> convertToMap(Object obj) {
        Map<String, Object> result = new HashMap<>();

        if (obj == null) {
            return result;
        }

        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors()) {
                Method getter = propertyDescriptor.getReadMethod();
                if (getter != null && !"class".equals(propertyDescriptor.getName())) {
                    Object value = getter.invoke(obj);

                    if (value != null) {
                        if (value instanceof Set) {
                            Set<?> set = (Set<?>) value;
                            Set<Map<String, Object>> convertedSet = set.stream()
                                    .map(Neo4jObjectConverter::convertToMap)
                                    .collect(Collectors.toSet());
                            result.put(propertyDescriptor.getName(), convertedSet);
                        } else if (isPrimitiveOrWrapper(value.getClass())) {
                            result.put(propertyDescriptor.getName(), value);
                        } else {
                            result.put(propertyDescriptor.getName(), convertToMap(value));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getCause());
        }

        return result;
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() || type == String.class || type == Boolean.class ||
                type == Integer.class || type == Long.class || type == Short.class ||
                type == Byte.class || type == Double.class || type == Float.class ||
                type == Character.class;
    }

    private Neo4jObjectConverter() {
        throw new IllegalStateException("Utility class");
    }

}
