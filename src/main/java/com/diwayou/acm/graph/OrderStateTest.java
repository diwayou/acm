package com.diwayou.acm.graph;

import com.google.common.collect.Maps;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.io.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Administrator
 */
public class OrderStateTest {
    public static void main(String[] args) throws ExportException, IOException {
//        // orderItemFsmList
//        String stateConfig = "Start+Create=Processing,Start+Verify=End,Processing+Cancel=Canceled,Processing+Out=Stockout,Processing+Pick=Shipping,Processing+Ship=Shipped,Processing+Verify=End,Shipping+Cancel=Canceled,Shipping+Ship=Shipped,Shipping+Verify=End,Shipped+Receive=End,Shipped+Return=Returned,End+Return=Returned,Stockout+Cancel=Canceled,Start+AutoProcess=Shipping,Processing+AutoProcess=Shipping,Processing+AutoVerify=End";
//        // tgouPackageFsmList
//        String stateConfig = "Start+Create=Pending,Pending+Cancel=Canceled,Pending+Print=Picking,Pending+Ship=Shipped,Pending+Verify=End,Picking+Cancel=Canceled,Picking+Pick=Shipping,Picking+Ship=Shipped,Picking+Verify=End,Shipping+Cancel=Canceled,Shipping+Ship=Shipped,Shipping+Verify=End,Shipped+Receive=End,Shipped+Return=Returned,End+Return=Returned,Shipping+PointReceive=PointShipped,PointShipped+Verify=End,PointShipped+Cancel=Canceled,Pending+AutoProcess=Shipping";
//        // tgouOrderFsmList
//        String stateConfig = "Start+Create=Waiting,Waiting+Cancel=Canceled,Waiting+Pay=Pending,Pending+Confirm=Processing,Waiting+Mistake=Abnormal,Waiting+Verify=End,Pending+Cancel=Canceled,Pending+Confirm=Processing,Pending+Verify=End,Processing+Cancel=Canceled,Processing+Out=Stockout,Processing+Pick=Shipping,Processing+Ship=Shipped,Processing+Verify=End,Shipping+Cancel=Canceled,Shipping+Ship=Shipped,Shipping+Verify=End,Shipping+PointReceive=PointShipped,PointShipped+Receive=End,PointShipped+Verify=End,Shipped+Receive=End,Shipped+Return=Returned,End+Return=Returned,Stockout+Cancel=Canceled,Stockout+CancelOut=Processing,Abnormal+Cancel=Canceled,PointShipped+Cancel=Canceled,Start+Verify=End,Processing+AutoProcess=PointShipped,Processing+AutoVerify=End,Pending+AutoProcess=PointShipped,Pending+AutoVerify=End,Waiting+Split=Processing,Processing+Split=Splited,PointShipped+toWait=Waiting,Splited+toWait=Waiting";
//        // orderRequestFsmList
//        String stateConfig = "Start+Create=Pending,Pending+Confirm=Confirmed,Pending+Reject=Rejected,Pending+Close=Closed";
//        // returnRequestFsmList
        String stateConfig = "Start+Create=Pending,Pending+Confirm=Confirmed,Confirmed+SendBack=Receiving,Confirmed+AirConfirm=Receiving,Pending+AirApply=Received,Receiving+Receive=Received,Pending+FinalConfirm=End,Received+FinalConfirm=End,Confirmed+Reject=Rejected,End+Reject=Rejected,Received+Reject=Rejected,Pending+Reject=Rejected,Receiving+Reject=Rejected,Rejected+Complain=Arbitrating,Arbitrating+SupportSeller=Failed,Arbitrating+SupportCustomer=Confirmed,Pending+ConfirmSendReceive=End,Confirmed+SendReceive=Received,Received+TurnDown=Receiving,Pending+AirConfirm=Receiving,Pending+PendingToClosed=Closed,Confirmed+PendingToClosed=Closed,Receiving+PendingToClosed=Closed,Received+PendingToClosed=Closed,Arbitrating+RejectToEnd=End";

        Graph<String, RelationshipEdge> hrefGraph = createHrefGraph(stateConfig);

        // use helper classes to define how vertices should be rendered,
        // adhering to the DOT language restrictions
        ComponentNameProvider<String> vertexIdProvider = s -> s;
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
    private static Graph<String, RelationshipEdge> createHrefGraph(String stateConfig) {
        // 因为所画的状态图既有环loop也需要同时存在多条边 所以需要使用DirectedPseudograph
        Graph<String, RelationshipEdge> g = new DirectedPseudograph<>(RelationshipEdge.class);

        String[] stateEventConfigArr = stateConfig.split(",");
        for (String stateEventConfig : stateEventConfigArr) {
            int plusSep = stateEventConfig.indexOf("+");
            int equalSep = stateEventConfig.indexOf("=", plusSep);

            String startState = stateEventConfig.substring(0, plusSep);
            String event = stateEventConfig.substring(plusSep + 1, equalSep);
            String nextState = stateEventConfig.substring(equalSep + 1);

            g.addVertex(startState);
            g.addVertex(nextState);
            g.addEdge(startState, nextState, new RelationshipEdge(event));
        }

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
