import edu.princeton.cs.algs4.LinearProbingHashST; 
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In; 
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stopwatch; 

public class MeuLinearProbingHashST<Key, Value> {
    private static final int[] PRIMES = {
        7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
        32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
        8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
        536870909, 1073741789, 2147483647
    };

    private static final int INIT_CAPACITY = PRIMES[0];
    private static final double ALFAINF_DEFAULT = 0.125;
    private static final double ALFASUP_DEFAULT = 0.5;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values
    private int iPrimes = 0;
    private final double alfaSup;
    private final double alfaInf;

    /** 
    * Construtor: cria uma tabela de espalhamento 
    * com resolução de colisões por encadeamento. 
    */
    public MeuLinearProbingHashST() {
        this(INIT_CAPACITY, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * com (pelo menos) m posições.
    */
    public MeuLinearProbingHashST(int m) {
        this(m, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    }

   /** 
    * Construtor: cria uma tabela de espalhamento 
    * em que a maior porcentagem de posiçõs preenchidas é 
    * alfaSup e a menor porcetagem é alfaInf (bem, se a tabela for 
    * muito pequena pode ser menor que alfaInf).
    */
    public MeuLinearProbingHashST(double alfaInf, double alfaSup) {
        this(INIT_CAPACITY, alfaInf, alfaSup);
    } 

    /** 
     * Construtor.
     *
     * Cria uma tabela de hash vazia com PRIMES[iPrimes] posições sendo
     * que iPrimes >= 0 e
     *         PRIMES[iPrimes-1] < m <= PRIMES[iPrimes]
     * (suponha que PRIMES[-1] = 0).
     * 
     * Além disso a tabela criada será tal que o fator de carga alfa
     * respeitará
     *
     *            alfaInf <= alfa <= alfaSup
     *
     * A primeira desigualdade pode não valer quando o tamanho da tabela
     * é INIT_CAPACITY.
     *
     * Pré-condição: o método supõe que alfaInf < alfaSup.
     */
    public MeuLinearProbingHashST(int m, double alfaInf, double alfaSup) {
        for (int i = 0; i < PRIMES.length && m >= PRIMES[i]; i++)
            iPrimes = i;

        this.m = PRIMES[iPrimes];
        this.n = 0;
        this.alfaSup = alfaSup;
        this.alfaInf = alfaInf;

        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m]; 
    }
    
    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /* return the value associated with the given key, null if no such value
     *
     * Supõe que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */
    public Value get(Key key) {
        for (int i = hash(key); vals[i] != null; i = (i + 1) % m) 
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    
    /** 
     *
     * Redimensiona a tabela de hash de modo que ela tenha PRIMES[k]
     * listas e reinsere todos os itens na nova tabela.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * Assim, o índice k corresponde ao valor PRIMES[k] que será o novo 
     * tamanho da tabela.
     */
    private void resize(int k) {
        MeuLinearProbingHashST<Key, Value> tmp = 
            new MeuLinearProbingHashST<>(PRIMES[k], this.alfaInf, this.alfaSup);
        
        for (int i = 0; i < this.m; i++)
            if(keys[i] != null)
                tmp.put(keys[i], vals[i]);

        keys = tmp.keys;
        vals = tmp.vals;
        m = tmp.m;
        iPrimes = k;
    }

    /**
     * put(): insert the key-value pair into the symbol table
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public void put(Key key, Value val) {
        if (key == null) 
            throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        if (load() > alfaSup)
            resize(++iPrimes);

        int i;
        for (i = hash(key); vals[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        
        keys[i] = key;
        vals[i] = val;
        n++;
    }
    
    // delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) 
            return;

        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % m;

        vals[i] = null;

        i = (i + 1) % m;
        while (vals[i] != null) {
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
   
        n--;

        if (load() < alfaInf) 
            resize(--iPrimes);
    }

    /**
     * return all of the keys as in Iterable
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (vals[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    /**
     * check():
     * integrity check - don't check after each put() because
     * integrity not maintained during a delete()
     */
    private boolean check() {
        
        if (load() > alfaSup || load() < alfaInf) {
            return false;
        }

        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i])
                return false;
        }
        return true;
    }

    public int sizeST() {
        return m;
    } 

    /** 
     * maxCluster(): retorna o maior comprimento de um cluster.
     *
     * O custo médio de sondagem linear depende da maneira em que
     * as chaves são agrupadas em pedaços contínuos da tabela.
     * Esses pedaços são chamados de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */ 
    public int maxCluster() {
        if (load() == 1) return m;

        int i, max = 0, count;
        for (i = 0; i < m && vals[i] != null; i++);
        while (i < m) {
            if (vals[i] != null) {
                for (count = 0; vals[(i+count)%m] != null; count++);
                i += count;
                if (max < count)
                    max = count;
            }
            else i++;
        }

        if (vals[m-1] != null) {
            for(i = 0, count = 0; vals[i] != null; i++, count++)
            if (count > max)
                max = count;
        }
    
        return max;
    }

    /** 
     * numCluster(): retorna o número de clusters.
     *
     * Suponha que uma posição i da tabela está disponível se 
     * vals[i] == null. Isso permite que seja utilizada uma estratégia 
     * de "lazy deletion", se desejarmos.
     * 
     */ 
    public int numClusters() {
        if(load() == 1) return 1;

        int count = 0;
        if (vals[0] == null && vals[m-1] != null)
            count++;

        for (int i = 1; i < m; i++) {
            if (vals[i] == null && vals[i-1] != null)
                count++;
        }

        return count;
    }
    
    /**
     * NOTA:
     * Proposição: Em uma tabela de hash com sondagem linear, m posições e
     *   n = alfa*m chaves, o número médio de sondagens (probes), supondo
     *   que a função de hashing satisfaz a hipótese do hashing uniforme, é
     *
     *            0.5 * (1 + 1/(1-alfa)) 
     *
     *   para buscas bem-sucedidas e
     *
     *            0.5 * (1 + 1/(1-alfa)^2) 
     *
     *   para buscas malsucedidas.
     */


    /**
     * averageSearchHit(): retorna o custo médio de uma busca
     * bem-sucedida na tabela supondo que cada chave da tabela tem a
     * mesma probabilidade de ser buscada.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchHit() {
        return 0.5*(1 + 1/(1 - load()));
    }

    /**
     * averageSearchMiss(): retorna o custo médio de uma busca malsucedida 
     * (que é também o custo de uma inserção) na tabela supondo que a função de hashing 
     * satisfaz a hipótese de hashing uniforme.
     *
     * O custo a que se refere ao número de sondagens (probes), 
     * ou seja, o número de posições visitadas da tabela de hash. 
     */
    public double averageSearchMiss() {
        return 0.5*(1 + 1/((1 - load())*(1 - load())));
    }

    private double load() {
        return (n/(double) m);
    }

        
   /***********************************************************************
    *  Unit test client.
    *  Altere à vontade, pois este método não será corrigido.
    ***********************************************************************/
    public static void main(String[] args) {
        if (args.length != 3) {
            showUse();
            return;
        }
        
        String s;
        double alfaInf = Double.parseDouble(args[0]);
        double alfaSup = Double.parseDouble(args[1]);
        String fileName = args[2];

        //=========================================================
        // Testa LinearProbingingHashST
        In in = new In(fileName);
        
        // crie a ST
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        
        // dispare o cronometro
        Stopwatch sw = new Stopwatch();

        // povoe a ST com palavras do arquivo
        StdOut.println("Criando a LinearProbingingHashST com as palavras do arquivo '" + args[2] + "' ...");
        while (!in.isEmpty()) {
            // Read and return the next line.
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                if (!st.contains(chaves[i])) {
                    st.put(chaves[i], 1);
                }
                else {
                    st.put(chaves[i], st.get(chaves[i])+1);
                }
            }
        }
        
        StdOut.println("Hashing com LinearProbingingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + st.size() + " itens");
        in.close();

        //=================================================================================
        StdOut.println("\n=============================================");
        
        // reabra o arquivo
        in = new In(fileName);
        
        // crie uma ST
        MeuLinearProbingHashST<String, Integer> meuST = new MeuLinearProbingHashST<String, Integer>(alfaInf, alfaSup);

        // dispare o cronometro
        sw = new Stopwatch();

        // povoe  a ST com palavras do arquivo
        StdOut.println("Criando a MeuLinearProbingingHashST com as palavras do arquivo '" + args[2] + "' ...");
        while (!in.isEmpty()) {
            // Read and return the next line.
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                if (!meuST.contains(chaves[i])) {
                    meuST.put(chaves[i], 1);
                }
                else {
                    meuST.put(chaves[i], meuST.get(chaves[i])+1);
                }
            }
        }
        
        // sw.elapsedTime(): returns elapsed time (in seconds) since
        // this object was created.
        int n = meuST.size();
        int m = meuST.sizeST();
        double alfa = (double) n/m;
        int nClusters = meuST.numClusters();
        StdOut.println("Hashing com MeuLinearProbingingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + n + " itens");
        StdOut.println("Tabela hash tem " + m + " posições");
        StdOut.println("Maior comprimento de um cluster é " + meuST.maxCluster());
        StdOut.printf("Número de clusters é %d (media = %.2f)\n", nClusters, (double) n / nClusters);
        StdOut.printf("Fator de carga (= n/m) = %.2f\n", (double) n/m);
        StdOut.printf("Custo médio de uma busca bem-sucedida = %.2f (%.2f)\n",
                      meuST.averageSearchHit(), 0.5*(1+1/(1-alfa)));
        StdOut.printf("Custo médio de uma busca malsucedida = %.2f (%.2f)\n",
                      meuST.averageSearchMiss(), 0.5*(1+1/((1-alfa)*(1-alfa))));

        in.close();
        
        // Hmm. Não custa dar uma verificada ;-)
        for (String key: st.keys()) {
            if (!st.get(key).equals(meuST.get(key))) {
                StdOut.println("Opss... " + key + ": " + st.get(key) + " != " + meuST.get(key));
            }
        }
    }


    private static void showUse() {
        String msg = "Uso: meu_prompt> java MeuLinearProbingingHashST <alfa inf> <alfa sup> <nome arquivo>\n"
            + "    <alfa inf>: limite inferior para o comprimento médio das listas (= fator de carga)\n"
            + "    <alfa sup>: limite superior para o comprimento médio das listas (= fator de carga)\n"
            + "    <nome arquivo>: nome de um arquivo com um texto para que uma ST seja\n"
            + "                    criada com as palavras nesse texto.";
        StdOut.println(msg);
    }

}

