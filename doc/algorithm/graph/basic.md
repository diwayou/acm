###概念
* G = (V, E)，其中V是顶点集合，E是边的集合，每条边由两个顶点连接表示
* 每对顶点之间都只有一条边关联的图称为**简单图**(simple graph)，有多条边关联一对顶点的图称为**多重图**(multigraph)，包含环(loop)的多重图称为**伪图**(pseudograph)
* 图分为无向图和有向图，具体如下表
类型 | 边 | 是否允许多条边 | 是否允许环
-|-|-|-
简单图(simple graph) | 无向 | 否 | 否
多重图(multigraph) | 无向 | 是 | 否
伪图(pseudograph) | 无向 | 是 | 是
简单有向图(simple directed graph) | 有向 | 否 | 否
有向多重图(Directed multigraph) | 有向 | 是 | 是
混合图(mixed graph) | 有向和无向 | 是 | 是