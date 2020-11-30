import java.io.File;

import jdk.internal.agent.resources.agent;

//import jdk.internal.net.http.PullPublisher;

/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

public class Deschubs {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    public static boolean logging = true;

    private static Boolean inputFileExists = true;
	private static Boolean numberOfArguments = true;


    //Trie node for uncompressing SchubsH, 
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        //is the node a leave node?
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        //compare based on frequency;
        public int compareTo (Node that) {
            return this.freq - that.freq;
        }
    }

    public static void err_print(String msg){
        if(logging){
            System.err.println(msg);
        }
    }
    
    public static void err_println(String mes){
        if(logging){
            System.err.println(mes);
        }
    }
    //expand huffman encoded fild input
    public static void expandHuffman(BinaryIn in, BinaryOut out) {
        //read in Huffman trie from input file
        Node root = readTrie(in);

        //number of bytes to write
        int length = in.readInt();

        //decode using the huffman trie
        for (int i = 0; i < length; i++){
            Node x = root;
            while(!x.isLeaf()) {
                boolean bit = in.readBoolean();
                if (bit){
                    x = x.right;
                } else{
                    x = x.left;
                }
            }
            out.write(x.ch);
        }
        out.flush();
    }

    private static Node readTrie(BinaryIn input){
        boolean isLeaf = input.readBoolean();
        if (isLeaf){
            char x = input.readChar();
            //err_println("t: " + x);
                return new Node(x, -1, null, null);
        } else{
            //err_print("f");
                return new Node('\0', -1, readTrie(input), readTrie(input));
        }
    }

    //expand LZW encoded file input
    public static void expandLZW(BinaryIn in, BinaryOut out) {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = in.readInt(W);
        String val = st[codeword];

        while (true) {
            out.write(val);
            codeword = in.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        out.close();
    }

    public static void untar(String archiveFileName) {
		
        File tarFile = null;
        BinaryIn in = null;
        BinaryOut out = null;

        char sep =  (char) 255;  // all ones 11111111

        // nerf through archive, extracting files
        // int lengthoffilename, sep, filename, sep, lengthoffile, sep, bits

        tarFile = new File(archiveFileName);
        if (!tarFile.exists() || !tarFile.isFile()) {
            System.out.println("Invalide input file name.");
            inputFileExists = false;
            return;
        }

        in = new BinaryIn( archiveFileName );
        while(!in.isEmpty()){
            try {
                int filenamesize = in.readInt();
                sep = in.readChar();
                String filename="";
                for (int i=0; i<filenamesize; i++){
                // concatenate characters to string
                filename = filename+in.readChar();
                }
                
                sep = in.readChar();
                long filesize = in.readLong();
                sep = in.readChar();
                System.out.println("Extracting file: " + filename + " ("+ filesize +").");
                out = new BinaryOut( filename );
                for (int i=0; i<filesize; i++){
                    // copy input to output
                    out.write(in.readChar());
                }			

            } finally {
                if (out != null)
                out.close();
            }
        }	
        
    }

    public static  Boolean fileExists(){
		return inputFileExists;
	}
	public static  Boolean argumentNumber(){
		return numberOfArguments;
	}


    public static void main(String[] args) {

        BinaryIn in = null;
        BinaryOut out = null;
        String extractFilename = "";
        String extractFiletype = "";
        
       for(int i = 0; i < args.length; i++)
       {
            // if arg[0] = lz.txt.ll, then args[0].length()-3 = lz.txt;
            for(int n = 0; n < args[i].length()-3; n++){
            
                extractFilename = extractFilename + args[i].charAt(n);
            }

            //file type is whatever the extention the file has. .hh, .ll. .zl, and .zh
            for(int n = args[i].length()-3; n < args[i].length(); n++){
                extractFiletype = extractFiletype+ args[i].charAt(n);
            }

            //first thing is expand with Huffman, then Untar it.
            try
            {
                in =  new BinaryIn(args[i]);
                out = new  BinaryOut(extractFilename);

                if(extractFiletype.equals(".ll")){
                    expandLZW(in, out);
                } 
                else if (extractFiletype.equals(".hh")){
                    expandHuffman(in, out);  
                } 
                else if (extractFiletype.equals(".zh")){
                    expandHuffman(in, out);
                    untar(extractFilename);  
                }
                else {
                    System.out.println("Illigal file type.");
                }

            } finally
            {
                if(out != null){
                    out.close();
                }
            } 
            extractFilename = "";
            extractFiletype = "";
       }
    }

}
