import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn; 
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.Stopwatch;

public class ST {
    
    // Create an empty symbol table
    public ST() {
    }

    // Returns the value associated with the given key if the key is in the symbol table
    // and -1 if the key is not in the symbol table
    // Throws java.lang.NullPointerException - if key is null
    public int get(String key) {
        if (key == null) {
            throw new java.lang.NullPointerException("ST.get(): key is null");
        }
        // escreva seu método get() aqui
    }

    // Insert the key into the symbol table and increase its value
    // Throws java.lang.NullPointerException - if key is null
    public void put(String key) {
        if (key == null) {
            throw new java.lang.NullPointerException("ST.put(): key is null");
        }
        // escreva seu métod put() aqui
    }

    
    // Removes the key and associated value from the symbol table (if the key is in the symbol table).
    // Throws java.lang.NullPointerException - if key is null
    public void delete(String key) {
        if (key == null) {
            throw new java.lang.NullPointerException("ST.delete(): key is null");
        }
        // escreva seu método delete() aqui
    }

    // Does this symbol table contain the given key?
    // Throws  java.lang.NullPointerException - if key is null
    public boolean contains(String key) {
        if (key == null) {
            throw new java.lang.NullPointerException("ST.contains(): key is null");
        }
        // escreva seu método contains() aqui
    }

    // Returns the number of key-value pairs in this symbol table
    public int size() {
        // escreva seu métod size aqui
    }

    // Is this symbol table empty?
    // Returns: true if this symbol table is empty and false otherwise
    public boolean isEmpty() {
        // escreva seu métod isEmpty() aqui
    }

    // Returns the largest (= maior frequência) key in the symbol table
    // Throws java.util.NoSuchElementException - if the symbol table is empty
    public String max() {
        // escreva seu métod max() aqui
    }

    // Returns a string representing the symbol table
    // This string is used when we use StdOut.print*() to show the table
    // Veja como um cliente utiliza este método no main()
    public String toString() {
        // escreva seu método toString() aqui
    }


    // Move the symbol table to one of size k
    private void resize(int k) {
        // escreva seu método resize() aqui
    }


    //-----------------------------------------------------------------
    // Exemplo de unit test para a ST
    // Alterem à vontade, pois este método não será corrigido.
    public static void main(String[] args) {
        String PROMPT  = ">>> ";
        // neste unit test show, keys, size e max são palavras reservadas
        String SHOW    = "show"; // mostre a ST 
        String KEYS    = "keys"; // mostre as chaves na ST
        String SIZE    = "size"; // mostre o 'tamanho' da ST
        String MAX     = "max";  // mostre chave com maiosr valor
        String s;
        
        // Initializes an input stream from a filename or web page name.
        In in = new In(args[0]);
        // criamos duasST
        ST st1 = new ST();
        ST st2 = new ST();

        // teste isEmpty()
        assert st1.isEmpty() : "Vixe! não passou nem no primeiro teste. :-O";

        // teste size()
        assert st1.size() == 0 : "Nossa! size() devia retorna 0. :-(";

        // teste toString()
        StdOut.println("Vejamos a ST1 inicialmente vazia: " + st1);
        
        // teste put()
        st1.put("aaa");
        StdOut.println("Vejamos a ST1 com 1 par key-val: " + st1);
        assert st1.contains("aaa") : "Caraca! não passou nem no primeiro teste. :-O";
        st1.put("bbb");
        assert st1.contains("bbb") : "Hmm! não achou o 'bbb', :-(";
        st1.put("ccc");
        st1.put("dd");
        st1.put("bbb");
        st1.put("bbb");
        assert !st1.contains("xx") : "Vixe! achou o 'xx'... 8-|" ;

        // teste get()
        assert st1.get("xx") == -1 : "Putz! get('xx') devia retorna -1 X#@@$" ;
        assert st1.get("bbb") == 3 : "Vixe! get('bbb') devia retorna 3 X#@@$" ;
        
        // teste size()
        assert st1.size() == 4 : "Vixe! size errado..." ;

        // teste toString()
        StdOut.println("Vejamos a ST1 com 4 pares key-val: " + st1);
        
        // teste delete
        st1.delete("bbb");
        assert !st1.contains("bbb") : "Hmm. Devia ter removido 'bbb'...";
        assert st1.contains("aaa") : "'aaa' ainda devia estar na ST1 :-$";
        assert st1.size() == 3 : "... depois de remover 'bbb' o size devia ser 3";

        // teste delete
        st1.delete("bbb"); // não está na ST
        StdOut.println("Vejamos a ST1 com 3 pares key-val: " + st1);
        st1.delete("aaa"); // não está na ST
        StdOut.println("Vejamos a ST1 com 2 pares key-val: " + st1);
        st1.delete("ccc"); // não está na ST
        StdOut.println("Vejamos a ST1 com 1 par key-val: " + st1);
        st1.delete("dd"); // não está na ST

        // teste toString()
        StdOut.println("ST1 deve estar vazia novamente: " + st1);
        assert st1.size() == 0 : "... depois de remover tudo devia estar vazia X-|";


        // deixamos a ST2 com 5 chaves
        st1.put("Como"); //
        st1.put("é");
        assert st1.contains("é") : "'é' devia estar na ST.";
        st1.put("bom");
        st1.put("estudar");
        st1.put("MAC0323!");
        assert st1.size() == 5 : "... ainda está com problemas X-|";
        
        // teste st2
        assert st2.size() == 0 : "Inicialmente ST2 devia estar vazia";
        
        // disparamos o cronometro
        Stopwatch sw = new Stopwatch();

        // vamos povoar a ST2 com palavras de um arquivo 
        StdOut.println("Criando a ST2 com as palavras do arquivo '" + args[0] + "' ...");
        while (!in.isEmpty()) {
            // Read and return the next line.
            String linha = in.readLine();
            String[] chaves = linha.split("\\W+");
            for (int i = 0; i < chaves.length; i++) {
                // StdOut.print("'" + chaves[i] + "' ");
                st2.put(chaves[i]);
            }
        }
        // sw.elapsedTime(): returns elapsed time (in seconds) since this object was created.
        StdOut.println("ST2 criada em " + sw.elapsedTime() + " segundos");
        
        StdOut.println("ST2 contém " + st2.size() + " pares key-val");
        
        StdOut.println("Início da consulta interativa. Tecle ctrl+D encerrar");
        StdOut.print(PROMPT);
        // consultas à ST criada
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (s.equals(SHOW)) {
                StdOut.println(st2);
            }
            else if (s.equals(SIZE)) {
                StdOut.println(st2.size());
            }
            else if (s.equals(MAX)) {
                StdOut.println("'" + st2.max() + "'");
            }
            else if (s.equals(KEYS)) {
                for (String key: st2.keys()) {
                    StdOut.println(key);
                }
            }
            else {
                // consulte o número de ocorrências de s no arquivo
                StdOut.println(st2.get(s));
            }
            StdOut.print(PROMPT);
         }
    }
}


