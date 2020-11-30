import java.io.File;

/*************************************************************************
 *  Author:         Felix Mbikogbia
 *  Course name:    Software Engineering 2
 *  Term:           Fall 2020
 *  Compilation:    javac SchubsL.java
 *  Execution:      java SchubsL file.txt //file to be compressed. file.txt is compressed to file.txt.ll
 *  Dependencies:   BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using SchubsL.
 *
 *
 *************************************************************************/

public class SchubsL {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    private static Boolean inputFileExists = true;
    private static Boolean numberOfArguments = true;
    private static Boolean fileIsEmpty = true;

    public static void compress(BinaryIn inputfile, BinaryOut outputfile) { 
        String input = inputfile.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            outputfile.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);            // Scan past s in input.
        }
        outputfile.write(R, W);
        outputfile.close();
    } 

    public static void main(String[] args) {

        BinaryIn in = null;
        BinaryOut out = null;
        File currentFile;


       for(int i = 0; i < args.length; i++)
       {
            try{
                currentFile = new File(args[i]);

                if(!currentFile.isFile() || !currentFile.exists()){
                    inputFileExists = false;
                    System.out.println(currentFile + " is not compressed because it does not exist");
                }
                else if(currentFile.length() == 0){
                    fileIsEmpty = true;
                    System.out.println(currentFile + " is not compressed because it is empty");
                }
                else {
                    in =  new BinaryIn(args[i]);
                    out = new  BinaryOut(args[i]+".ll");
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
