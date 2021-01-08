package com.diwayou.acm.graph;

import com.google.common.collect.Maps;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author diwayou
 */
public class JgraphtTest {

    public static void main(String[] args) throws IOException {
        Graph<String, RelationshipEdge> hrefGraph = createHrefGraph();

        DOTExporter<String, RelationshipEdge> exporter = new DOTExporter<>();

        exporter.setVertexIdProvider(s -> s.substring(0, s.indexOf("(")));
        exporter.setEdgeIdProvider(RelationshipEdge::getLabel);
        exporter.setVertexAttributeProvider(s -> {
            Map<String, Attribute> attributeMap = Maps.newHashMap();
            attributeMap.put("style", DefaultAttribute.createAttribute("filled"));
            attributeMap.put("color", DefaultAttribute.createAttribute("lightgray"));

            return attributeMap;
        });
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> attributeMap = Maps.newHashMap();
            attributeMap.put("label", DefaultAttribute.createAttribute(e.getLabel()));
            return attributeMap;
        });
        exporter.setGraphAttributeProvider(() -> {
            Map<String, Attribute> attributeMap = Maps.newHashMap();
            attributeMap.put("rankdir", DefaultAttribute.createAttribute("LR"));

            return attributeMap;
        });

        try (Writer writer = new FileWriter("graph.gv")) {
            exporter.exportGraph(hrefGraph, writer);

            Runtime.getRuntime().exec("dot -Tsvg graph.gv -o graph.svg");
        }
    }

    private static Graph<String, RelationshipEdge> createHrefGraph() {
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

    static class RelationshipEdge extends DefaultEdge {

        private String label;

        public RelationshipEdge(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
        }
    }
}
