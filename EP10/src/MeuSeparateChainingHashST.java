import edu.princeton.cs.algs4.SeparateChainingHashST; 
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stopwatch;

public class MeuSeparateChainingHashST<Key, Value> {
    private static final int[] PRIMES = {
        7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
        32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
        8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
        536870909, 1073741789, 2147483647
    };

    private static final int INIT_CAPACITY = PRIMES[0];
    private static final double ALFAINF_DEFAULT = 2;
    private static final double ALFASUP_DEFAULT = 10;
    private int iPrimes = 0;
    private final double alfaSup;
    private final double alfaInf;
    private int n; 
    private int m; 
    private SequentialSearchST<Key, Value>[] st;
    private double load;
    
    public MeuSeparateChainingHashST() {
        this(INIT_CAPACITY, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    } 
    
    public MeuSeparateChainingHashST(int m) {
        this(m, ALFAINF_DEFAULT, ALFASUP_DEFAULT);
    } 

    public MeuSeparateChainingHashST(double alfaInf, double alfaSup) {
        this(INIT_CAPACITY, alfaInf, alfaSup);
    } 
    
    /** 
     * Construtor.
     *
     * Cria uma tabela de hash vazia com PRIMES[iPrimes] listas sendo
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
    public MeuSeparateChainingHashST(int m, double alfaInf, double alfaSup) {
        if (alfaInf > alfaSup || m > PRIMES[PRIMES.length - 1])
            throw new java.lang.IllegalArgumentException();
        
        this.alfaInf = alfaInf;
        this.alfaSup = alfaSup;
        this.n = 0;

        for (int i = 0; i < PRIMES.length && m > PRIMES[i]; i++)
            iPrimes = i;

        this.m = PRIMES[iPrimes];
        
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[PRIMES[iPrimes]];

        for (int i = 0; i < this.m; i++)
            st[i] = new SequentialSearchST<>();
    } 
   

    /** 
     * Redimensiona a tabela de hash de modo que ela tenha PRIMES[k]
     * listas e reinsere todos os itens na nova tabela.
     *
     * Assim, o índice k corresponde ao valor PRIMES[k] que será o novo 
     * tamanho da tabela.
     */
    private void resize(int k) {
        MeuSeparateChainingHashST<Key, Value> tmp = new MeuSeparateChainingHashST(PRIMES[k], alfaInf, alfaSup);
        
        for (int i = 0; i < this.m; i++)
            for (Key key: st[i].keys())
                tmp.put(key, st[i].get(key));

        this.m = tmp.m;
        this.n = tmp.n;
        this.st = tmp.st;
    }

    // hash function: returns a hash value between 0 and M-1
    // função de espalhamento: devolve um valor hash entre 0 e M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    } 

    // return number of key-value pairs in symbol table
    public int size() {
        return n;
    } 

    
    // is the symbol table empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // is the key in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    } 

    // return value associated with key, null if no such key
    public Value get(Key key) {
        int i = hash(key);
        return st[i].get(key);
    } 

    // insert key-value pair into the table
    public void put(Key key, Value val) {
        if (key == null)
            throw new java.lang.IllegalArgumentException();

        if (val == null) {
            delete(key);
            return;
        }

        if (load() > this.alfaSup)
            resize(++iPrimes);
        
        int i = hash(key);
        if (!st[i].contains(key)) n++;
        st[i].put(key, val);
    } 

    // delete key (and associated value) if key is in the table
    public void delete(Key key) {
        if (key == null)
            throw new java.lang.IllegalArgumentException();
        
        int i = hash(key);
        
        if (!st[i].contains(key)) n--;
        st[i].delete(key);
        
        
        if (load() < this.alfaInf && iPrimes >= 0)
            resize(iPrimes--);
    } 

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    } 

    // retorna o tamanho da tabela de hash
    public int sizeST() {
        return m;
    } 

    // retorna o maior comprimento de uma lista
    public int maxLista() {
        int max = 0;
        
        for (SequentialSearchST list: st)
            if (list.size() >= max)
                max = list.size();
             
        return max;
    }

    /** Exercício 3.4.30 S&W
     *  TAREFA
     *  Teste do Chi quadrado (chi-square statistic).
     *  Este método deve retorna o valor de chi^2 dado por
     *
     *  (m/n)((f_0-n/m)^2 + (f_1-n/m)^2 + ... + (f_{m-1}-n/m)^2)
     * 
     *  onde f_i é o número de chaves na tabela com valor de hash i.
     *  Este mecanismo fornece uma maneira de testarmos a hipótese 
     *  de que a função de hash produz valores aleatórios. 
     *  Se isto for verdade, para n > c*m, o valor calculado deveria 
     *  estar no intervalo [m-sqrt(n),m+sqrt(n)] com probabilidade 1-1/c  
     */
    public double chiSquare() {
        double sum = 0;
        for (int i = 0; i < m; i++) {
            double size = (double) st[i].size();
            sum += (size - load())*(size - load());
        }
        return (m/(double) n)*sum;
    }

    private double load() {
        return this.n/(double) this.m;
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
        // Testa SeparateChainingHashST
        In in = new In(fileName);
        
        // crie a ST
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        
        // dispare o cronometro
        Stopwatch sw = new Stopwatch();

        // povoe a ST com palavras do arquivo
        StdOut.println("Criando a SeparateChainingHashST com as palavras do arquivo '" + args[2] + "' ...");
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
        
        StdOut.println("Hashing com SeparateChainingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + st.size() + " itens");
        in.close();

        //=================================================================================
        StdOut.println("\n=============================================");
        
        // reabra o arquivo
        in = new In(fileName);
        
        // crie uma ST
        MeuSeparateChainingHashST<String, Integer> meuST = new MeuSeparateChainingHashST<String, Integer>(alfaInf, alfaSup);

        // dispare o cronometro
        sw = new Stopwatch();

        // povoe  a ST com palavras do arquivo
        StdOut.println("Criando a MeuSeparateChainingHashST com as palavras do arquivo '" + args[2] + "' ...");
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
        double chi2 = meuST.chiSquare();    
        StdOut.println("Hashing com MeuSeparateChainingHashST");
        StdOut.println("ST criada em " + sw.elapsedTime() + " segundos");
        StdOut.println("ST contém " + n + " itens");
        StdOut.println("Tabela hash tem " + m + " listas");
        StdOut.println("Maior comprimento de uma lista é " + meuST.maxLista());
        StdOut.println("Fator de carga (= n/m) = " + (double) n/m);
        StdOut.printf("Chi^2 = %.2f, [m-sqrt(m),m+sqrt(m)] = [%.2f, %.2f]\n",
                       chi2, (m-Math.sqrt(m)), (m+Math.sqrt(m)));

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
