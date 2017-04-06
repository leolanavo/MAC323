public class UnionFind {
   private int count;
    private int n;
    private int[] parent;
    private int[] size;

    public UnionFind (int n) {
        this.n = n;
        count = n*n;
        parent = new int[count];
        size = new int[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count () {
        return count;
    }

    public int find (int lin, int col) {
        if (p < 0 || p >= n*n) return -1;
        int index = lin*n + col;
        while (index != parent[index])
            index = parent[index];
    }

    public boolean connected (int lin1, int col1, int lin2, int col2) {
        return find(lin1, col1) == find(lin2, col2);
    }

    public void union (int lin1, int col1, int lin2, int col2) {
        rootP = find(lin1, col1);
        rootQ = find(lin2, col2);
        if (rootP == rootQ) return;
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
