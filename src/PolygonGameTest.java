import java.util.LinkedList;

public class PolygonGameTest {

    enum Edge {
        MULTIPLE, PLUS
    }

    // 暴力递归版本
    static int playPolygonGame1(int[] vector, Edge[] edge) {
        int max = Integer.MIN_VALUE;
        //删除一条边再进行合并
        for (int i = 0; i < edge.length; i++) {
            LinkedList<Integer> vLinkedList = new LinkedList<>();
            LinkedList<Edge> eLinkedList = new LinkedList<>();
            int cur = (i + 1) % vector.length;
            while (cur != i) {
                vLinkedList.push(vector[cur]);
                eLinkedList.push(edge[cur]);
                cur = (cur + 1) % vector.length;
            }
            vLinkedList.push(vector[cur]);

            max = Integer.max(max, getMaxResult(vLinkedList, eLinkedList));
        }

        return max;
    }

    private static int getMaxResult(LinkedList<Integer> v, LinkedList<Edge> e) {
        if (v.size() < 2) {
            throw new RuntimeException();
        }
        if (v.size() == 2) {
            return calculate(v.pop(), e.pop(), v.pop());
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < e.size(); i++) {
            LinkedList<Integer> vectors = (LinkedList<Integer>) (v.clone());        //浅复制
            LinkedList<Edge> edges = (LinkedList<Edge>) (e.clone());

            int a = vectors.remove(i);
            Edge op = edges.remove(i);
            int b = vectors.remove(i);
            vectors.add(i, calculate(a, op, b));
            max = Integer.max(max, getMaxResult((LinkedList<Integer>) vectors.clone(), (LinkedList<Edge>) edges.clone()));

        }
        return max;
    }

    private static int calculate(int a, Edge op, int b) {
        switch (op) {
            case PLUS:
                return a + b;
            case MULTIPLE:
                return a * b;
        }
        throw new RuntimeException("Error operation!!!");
    }

    // 动态递归版本
    static class MaxMinPair{
        int max;
        int min;

        MaxMinPair(int max, int min){
            this.max = max;
            this.min = min;
        }
    }

    static int playPolygonGame2(int[] vectors, Edge[] edges){
        if (vectors == null || edges == null || vectors.length != edges.length + 1){
            throw new RuntimeException("输入数组有误");
        }
        int eNum = edges.length;
        int[][] dp = new int[eNum][eNum];   // row + 1 代表处理接连多少条边，col 代表 从哪条边开始计算
        // 处理base
        for (int i = 0; i < eNum; i++) {
            dp[0][i] = calculate(vectors[i], edges[i], vectors[i + 1]);
        }
        // 开始处理
    }

    public static void main(String[] args) {
        int[] vectors = {1, 4, -2, 7, -3};
        Edge[] edges = {Edge.PLUS, Edge.MULTIPLE, Edge.PLUS, Edge.MULTIPLE, Edge.PLUS};
        System.out.println(playPolygonGame1(vectors, edges));


    }
}
