/*************************************************************************
 * Author:        Felix Mbikogbia
 * Date:          Nov 30, 2020
 * Course:        Software Engineering 2, Fall semester
 *  Compilation:  javac SchubsH.java
 *  Execution:    java SchubsH *.txt
 * Execution:     java SchubsH file1.txt file2.txt .....
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  modified:     change logging to true to enable debugging info to StdErr
 *
 *  Compress file input using Huffman algorighm.
 *
 *************************************************************************/

import java.io.File;

public class SchubsH {

    // alphabet size of extended ASCII
    private static final int R = 256;
    public static boolean logging = true;

    private static Boolean inputFileExists = true;
    private static Boolean numberOfArguments = true;
    private static Boolean fileIsEmpty = true;

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }


    public static void err_print(String msg)
    {
	if (logging)
	    System.err.print(msg);
    }

    public static void err_println(String msg)
    {
	if (logging)
	    {
		System.err.println(msg);
	    }
    }


    // compress bytes from standard input and write to standard output
    public static void compress(BinaryIn inputfile, BinaryOut outputfile) {
        // read the input
        String s = inputfile.readString();
        char[] input = s.toCharArray();

        // tabulate frequency counts
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
        String[] st = new String[R];
        buildCode(st, root, "");

        // print trie for decoder
        writeTrie(root, outputfile);
	//err_println("writeTrie");

        // print number of bytes in original uncompressed message
        outputfile.write(input.length);
	//err_println("writing input length " + input.length);

	//err_println("happily encoding... ");
        // use Huffman code to encode input
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
	   // err_print("Char " + input[i] + " ");
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    outputfile.write(false);
		   // err_print("0");
                }
                else if (code.charAt(j) == '1') {
                    outputfile.write(true);
		    //err_print("1");
                }
                else throw new RuntimeException("Illegal state");
            }
	    //err_println("");
        }

        // flush output stream
        outputfile.flush();
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(int[] freq) {

        // initialze priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
	   // err_println("buildTrie parent " + left.freq + " " + right.freq);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    // write bitstring-encoded trie to standard output
    private static void writeTrie(Node x, BinaryOut outputfile) {
        if (x.isLeaf()) {
            outputfile.write(true);
            outputfile.write(x.ch);
	    //err_println("T" + x.ch);
            return;
        }
        outputfile.write(false);
	//err_print("F");

        writeTrie(x.left,outputfile);
        writeTrie(x.right, outputfile) ;
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
	    //err_println("buildCode " + x.ch + " " + s);
        }
    }

    // expand Huffman-encoded input from standard input and write to standard output
    public static void expand() {

        // read in Huffman trie from input stream
        Node root = readTrie(); 

        // number of bytes to write
        int length = BinaryStdIn.readInt();

        // decode using the Huffman trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.flush();
    }

    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
	    char x = BinaryStdIn.readChar();
	    //err_println("t: " + x );
            return new Node(x, -1, null, null);
        }
        else {
	    //err_print("f");
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }

    public static void main(String[] args) {

        BinaryIn in = null;
        BinaryOut out = null;
        File currentFile;
        String extractZHfileName = "";
        String extractFiletype = "";

        if(args.length < 1){
            System.out.println("Error. Wrong number of arguments. Must pass at least one argument.");
            numberOfArguments = false;
            return;
        }


       for(int i = 0; i < args.length; i++)
       {
        for(int n = args[i].length()-3; n < args[i].length(); n++){
            
            extractFiletype = extractFiletype+ args[i].charAt(n);
        }
            try{
                currentFile = new File(args[i]);

                if(!currentFile.isFile() || !currentFile.exists()){
                    inputFileExists = false;
                    System.out.println(currentFile + " is not compressed because it is not a file");
                }
                else if(currentFile.length() == 0){
                    fileIsEmpty = true;
                    System.out.println(currentFile + " is not compressed because it is empty");
                }
                else if(extractFiletype.equals(".zh")){
                    for(int n = 0; n < args[i].length()-3; n++){
                        extractZHfileName = extractZHfileName + args[i].charAt(n);
                    }
                    in =  new BinaryIn(args[i]);
                    out = new  BinaryOut(extractZHfileName+".zh");
                    compress(in, out);
                }
                else {
                    in =  new BinaryIn(args[i]);
                    out = new  BinaryOut(args[i]+".hh");
                    compress(in, out);
                } 
                
            } finally{
                if(out != null){
                    out.close();
                }
            }
       }
       
    }

    public static  Boolean fileExists(){
		return inputFileExists;
	}
	public static  Boolean argumentNumber(){
		return numberOfArguments;
    }
    public static  Boolean fileIsEmpty(){
		return fileIsEmpty;
	}

}
