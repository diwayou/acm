package com.diwayou.acm.graph;

import org.jgrapht.Graph;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.util.SupplierUtil;

public class GraphStudy {
    public static void main(String[] args) {
        GnmRandomGraphGenerator<String, DefaultEdge> generator = new GnmRandomGraphGenerator<>(20, 40);

        Graph<String, DefaultEdge> graph = new DefaultDirectedWeightedGraph<>(SupplierUtil.createStringSupplier(), SupplierUtil.createDefaultEdgeSupplier());
        generator.generateGraph(graph);

        System.out.println(graph);
    }
}
