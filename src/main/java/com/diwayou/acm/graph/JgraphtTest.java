package com.diwayou.acm.graph;

import com.google.common.collect.Maps;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Administrator
 */
public class JgraphtTest {
    public static void main(String[] args) throws ExportException, IOException {
        // create a graph based on URI objects
        Graph<String, RelationshipEdge> hrefGraph = createHrefGraph();

        // use helper classes to define how vertices should be rendered,
        // adhering to the DOT language restrictions
        ComponentNameProvider<String> vertexIdProvider = s -> s.substring(0, s.indexOf("("));
        ComponentNameProvider<String> vertexLabelProvider = s -> s;
        ComponentNameProvider<RelationshipEdge> edgeLabelProvider = RelationshipEdge::getLabel;
        ComponentAttributeProvider<String> vertexAttributeProvider = s -> {
            Map<String, Attribute> attributeMap = Maps.newHashMap();
            attributeMap.put("style", DefaultAttribute.createAttribute("filled"));
            attributeMap.put("color", DefaultAttribute.createAttribute("lightgray"));

            return attributeMap;
        };
        ComponentAttributeProvider<RelationshipEdge> edgeAttributeProvider = e -> {
            Map<String, Attribute> attributeMap = Maps.newHashMap();
            //attributeMap.put("color", DefaultAttribute.createAttribute("lightblue2"));

            return attributeMap;
        };

        DOTExporter<String, RelationshipEdge> exporter = new DOTExporter<>(
                vertexIdProvider,
                vertexLabelProvider,
                edgeLabelProvider,
                vertexAttributeProvider,
                edgeAttributeProvider);

        exporter.putGraphAttribute("rankdir", "LR");

        try (Writer writer = new FileWriter("graph.gv")) {
            exporter.exportGraph(hrefGraph, writer);

            Runtime.getRuntime().exec("dot -Tsvg graph.gv -o graph.svg");
        }
    }

    /**
     * Creates a toy directed graph based on URI objects that represents link structure.
     *
     * @return a graph based on URI objects.
     */
    private static Graph<String, RelationshipEdge> createHrefGraph() {
        //@example:uriCreate:begin

        Graph<String, RelationshipEdge> g = new DefaultDirectedGraph<>(RelationshipEdge.class);

        String unused = "unused(1, 未使用)";
        String used = "used(2, 已使用)";
        String expired = "expired(3, 已过期)";
        String locked = "locked(5, 锁定)";
        String userDeleted = "userDeleted(6, 已退货)";
        String deleted = "deleted(-1, 已删除)";

        // add the vertices
        g.addVertex(unused);
        g.addVertex(used);
        g.addVertex(expired);
        g.addVertex(locked);
        g.addVertex(userDeleted);
        g.addVertex(deleted);

        // add edges to create linking structure
        g.addEdge(unused, used, new RelationshipEdge("用券"));
        g.addEdge(used, unused, new RelationshipEdge("退券"));
        g.addEdge(unused, expired, new RelationshipEdge("退已过期的券"));
        g.addEdge(unused, locked, new RelationshipEdge("买当券退券"));
        g.addEdge(locked, unused, new RelationshipEdge("买当券不同意退券"));
        g.addEdge(locked, userDeleted, new RelationshipEdge("已退货"));

        //@example:uriCreate:end

        return g;
    }

    static class RelationshipEdge
            extends
            DefaultEdge {
        private String label;

        /**
         * Constructs a relationship edge
         *
         * @param label the label of the new edge.
         */
        public RelationshipEdge(String label) {
            this.label = label;
        }

        /**
         * Gets the label associated with this edge.
         *
         * @return edge label
         */
        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
        }
    }
}
