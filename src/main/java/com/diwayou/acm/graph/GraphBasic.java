package com.diwayou.acm.graph;

import com.diwayou.acm.book.util.StdDraw;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.*;

public class GraphBasic {

    private static MutableValueGraph<String, Integer> graph;

    private static final double NODE_R = .03;

    public static void main(String[] args) {
        String sanding = "三鼎春天";
        String qingniwaqiao = "青泥洼桥";
        String jichang = "机场";
        String zhongshan = "中山";
        String malan = "马栏";
        String penquan = "喷泉";
        String legou = "乐购";
        String xinzhaizi = "辛寨子";

        graph = ValueGraphBuilder.directed().build();


        graph.putEdgeValue(sanding, jichang, 20);
        graph.putEdgeValue(sanding, qingniwaqiao, 6);
        graph.putEdgeValue(sanding, malan, 23);
        graph.putEdgeValue(sanding, zhongshan, 3);
        graph.putEdgeValue(qingniwaqiao, zhongshan, 3);
        graph.putEdgeValue(zhongshan, penquan, 3);
        graph.putEdgeValue(legou, sanding, 10);
        graph.putEdgeValue(xinzhaizi, sanding, 5);

        List<String> topology = topologicalOrder(graph);
        System.out.println(topology);

        breathFirstPrint(graph, topology.get(0));
    }

    private static List<String> topologicalOrder(ValueGraph<String, Integer> valueGraph) {
        Set<String> visitedNodes = Sets.newHashSet();
        LinkedList<String> result = Lists.newLinkedList();

        for (String node : valueGraph.nodes()) {
            if (!visitedNodes.contains(node)) {
                doTopology(valueGraph, node, visitedNodes, result);
            }
        }

        return result;
    }

    private static void doTopology(ValueGraph<String, Integer> valueGraph, String node, Set<String> visitedNodes, LinkedList<String> result) {
        visitedNodes.add(node);

        for (String childNode : valueGraph.successors(node)) {
            if (!visitedNodes.contains(childNode)) {
                doTopology(valueGraph, childNode, visitedNodes, result);
            }
        }

        result.addFirst(node);
    }

    private static void drawNode(Node node) {
        System.out.println(node);

        StdDraw.filledCircle(node.getX(), node.getY(), NODE_R);
        StdDraw.textLeft(node.getX() + NODE_R, node.getY() + NODE_R, node.getName());
    }

    private static void drawLineParent(Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            StdDraw.line(node.getX(), node.getY(), parent.getX(), parent.getY());
        }
        Node brother = node.getBrother();
        if (brother != null) {
            StdDraw.line(node.getX(), node.getY(), brother.getX(), brother.getY());
        }
    }

    private static void breathFirstPrint(ValueGraph<String, Integer> valueGraph, String startNode) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(startNode, .5, .1, null));

        Set<Node> visited = Sets.newLinkedHashSet();
        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            drawNode(currentNode);
            drawLineParent(currentNode);

            Set<String> successors = valueGraph.successors(currentNode.getName());
            if (!successors.isEmpty()) {
                double x = .1;
                double y = currentNode.getY() + .2;
                Node brother = null;
                for (String successor : successors) {
                    Node node = new Node(successor, x, y, currentNode);
                    queue.add(node);

                    if (brother != null && (valueGraph.hasEdgeConnecting(brother.getName(), node.getName()) ||
                            valueGraph.hasEdgeConnecting(node.getName(), brother.getName()))) {
                        node.setBrother(brother);
                    }

                    x += .2;
                    brother = node;
                }
            }
        }
    }

    private static class Node {
        private String name;
        private double x;
        private double y;
        private Node parent;
        private Node brother;

        public Node(String name, double x, double y, Node parent) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getBrother() {
            return brother;
        }

        public void setBrother(Node brother) {
            this.brother = brother;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(name, node.name);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    ", parent=" + parent +
                    ", brother=" + brother +
                    '}';
        }
    }
}
