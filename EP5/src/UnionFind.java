public class UnionFind {
   private int count;
    private int n;
    public int[] parent;
    private int[] size;
    public int[] parent2;
    private int[] size2;
    
    public UnionFind (int n) {
        this.n = n;
        count = n*n;
        
        parent = new int[count + 2];
        size = new int[count + 2];
        
        parent2 = new int[count + 2];
        size2 = new int[count + 2];

        for (int i = 0; i < count + 2; i++) {
            parent[i] = i;
            size[i] = 1;
            parent2[i] = i;
            size2[i] = 1;
        }
       
        size2[0] = n;
        size[0] = n;
        size[count + 1] = n;
        
        for(int i = 1; i <= n; i++) {
            parent[i] = 0;
            parent2[i] = 0;
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
    
    public int find2 (int p) {
        int root = p;
        while (root != parent2[root])
            root = parent2[root];
        while (p != root) {
            int newp = parent2[p];
            parent2[p] = root;
            p = newp;
        }
        return root;
    }
    
    public boolean connected (int row1, int col1, int row2, int col2, boolean full) {
        if (full) return find(n*row1 + col1 + 1) == find(n*row2 + col2 + 1);
        return find2(n*row1 + col1 + 1) == find2(n*row2 + col2 + 1);
    }

    public void union (int row1, int col1, int row2, int col2) {
        int rootP = find2(n*row1 + col1 + 1);
        int rootQ = find2(n*row2 + col2 + 1);

        if (rootP == rootQ) return;
        
        if (size2[rootP] < size2[rootQ] && rootP != 0) {
            parent2[rootP] = rootQ;
            size2[rootQ] += size2[rootP];
        }

        else {
            parent2[rootQ] = rootP;
            size2[rootP] += size2[rootQ];
        }
    }
}
