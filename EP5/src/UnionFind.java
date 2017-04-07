public class UnionFind {
   private int count;
    private int n;
    public int[] parent;
    private int[] size;
    
    public UnionFind (int n) {
        this.n = n;
        count = n*n;
        
        parent = new int[count + 2];
        size = new int[count + 2];
        
        for (int i = 0; i < count + 2; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        
        size[0] = n;
        size[count + 1] = n;
        
        for(int i = 1; i <= n; i++) {
            parent[i] = 0;
            parent[count - i + 1] = count + 1;
        }
    }
    
    public int find (int p) {
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }
    
    public boolean connected (int row1, int col1, int row2, int col2) {
        return find(n*row1 + col1 + 1) == find(n*row2 + col2 + 1);
    }

    public void union (int row1, int col1, int row2, int col2) {
        int rootP = find(n*row1 + col1 + 1);
        int rootQ = find(n*row2 + col2 + 1);

        if (rootP == rootQ) return;
        
        if (size[rootP] < size[rootQ] && rootP != 0) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }

        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}
