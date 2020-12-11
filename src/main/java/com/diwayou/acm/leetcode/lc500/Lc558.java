package com.diwayou.acm.leetcode.lc500;

/**
 * https://leetcode-cn.com/problems/quad-tree-intersection/https://leetcode-cn.com/problems/quad-tree-intersection/
 * <p>
 * 四叉树是一种树数据，其中每个结点恰好有四个子结点：topLeft、topRight、bottomLeft和bottomRight。四叉树通常被用来划分一个二维空间，递归地将其细分为四个象限或区域。
 * 我们希望在四叉树中存储 True/False 信息。四叉树用来表示 N * N 的布尔网格。对于每个结点, 它将被等分成四个孩子结点直到这个区域内的值都是相同的。
 * 每个节点都有另外两个布尔属性：isLeaf和val。当这个节点是一个叶子结点时isLeaf为真。val变量储存叶子结点所代表的区域的值。
 * <p>
 * 例如，下面是两个四叉树 A 和 B：
 * <p>
 * A:
 * +-------+-------+   T: true
 * |       |       |   F: false
 * |   T   |   T   |
 * |       |       |
 * +-------+-------+
 * |       |       |
 * |   F   |   F   |
 * |       |       |
 * +-------+-------+
 * topLeft: T
 * topRight: T
 * bottomLeft: F
 * bottomRight: F
 * <p>
 * B:
 * +-------+---+---+
 * |       | F | F |
 * |   T   +---+---+
 * |       | T | T |
 * +-------+---+---+
 * |       |       |
 * |   T   |   F   |
 * |       |       |
 * +-------+-------+
 * topLeft: T
 * topRight:
 * topLeft: F
 * topRight: F
 * bottomLeft: T
 * bottomRight: T
 * bottomLeft: T
 * bottomRight: F
 * <p>
 * <p>
 * 你的任务是实现一个函数，该函数根据两个四叉树返回表示这两个四叉树的逻辑或(或并)的四叉树。
 * <p>
 * A:                 B:                 C (A or B):
 * +-------+-------+  +-------+---+---+  +-------+-------+
 * |       |       |  |       | F | F |  |       |       |
 * |   T   |   T   |  |   T   +---+---+  |   T   |   T   |
 * |       |       |  |       | T | T |  |       |       |
 * +-------+-------+  +-------+---+---+  +-------+-------+
 * |       |       |  |       |       |  |       |       |
 * |   F   |   F   |  |   T   |   F   |  |   T   |   F   |
 * |       |       |  |       |       |  |       |       |
 * +-------+-------+  +-------+-------+  +-------+-------+
 * <p>
 * <p>
 * 提示：
 * A和B都表示大小为N * N的网格。
 * N将确保是 2 的整次幂。
 * 如果你想了解更多关于四叉树的知识，你可以参考这个wiki页面。
 * 逻辑或的定义如下：如果A 为 True ，或者B 为 True ，或者A 和 B 都为 True，则 "A 或 B" 为 True。
 */
public class Lc558 {

    public Node intersect(Node quadTree1, Node quadTree2) {
        //如果有一个叶子节点 递归终止
        //当叶子节点为True 合并为叶子节点 否则 合并结果为非叶子节点的节点(对方节点)
        if (quadTree1.isLeaf) {
            if (quadTree1.val) {
                return quadTree1;
            }
            return quadTree2;
        } else if (quadTree2.isLeaf) {
            if (quadTree2.val) {
                return quadTree2;
            }
            return quadTree1;
        }

        //合并之后的各个分支
        Node topLeft = intersect(quadTree1.topLeft, quadTree2.topLeft);
        Node topRight = intersect(quadTree1.topRight, quadTree2.topRight);
        Node bottomLeft = intersect(quadTree1.bottomLeft, quadTree2.bottomLeft);
        Node bottomRight = intersect(quadTree1.bottomRight, quadTree2.bottomRight);

        //因为可能存在返回的都叶子节点 因此要判断一下返回的节点是否全部叶子节点 并且 都为 True 或者 False 这样要合并为一个大的节点
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val && topLeft.val == bottomLeft.val && topLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true, null, null, null, null);
        }
        //否则的话保留各个分支节点 不进行合并
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    private static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {
        }

        public Node(boolean _val, boolean _isLeaf, Node _topLeft, Node _topRight, Node _bottomLeft, Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }
}
