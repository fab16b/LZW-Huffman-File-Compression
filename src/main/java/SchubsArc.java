 /**
 * Date: Nov 30, 2020
 * Course: Software Engineering II
 * Semester: Fall 2020
 * Author: Felix Mbikogbia
 * Compile: javac SchubsArc.java
 * Execute: java SchubsArch *.txt
 * Execute: java SchubsArch file1 file2 file3 file4 ... 
 * Dependencies: BinaryOut.java, BinaryIn.java
 * This program creates an archive of text files, then compresses using expandHuffman function.
 *
 */

import java.io.File;

public class SchubsArc
{
    private static Boolean inputFileExists = true;
    private static Boolean numberOfArguments = true;

    public static void main(String[] args) {
        File inputfile = null;
        File archievefile = null;
        BinaryIn in = null;
        // BinaryIn in2 = null;
        BinaryOut out = null;
        char separator = (char) 255; // it is all ones

        String filesToTar[] = new String[args.length-1];
        long filesize[] = new long[args.length-1]; 
        int filenamesize[] =  new int[args.length-1];

        if (args.length <= 1) {
            System.out.println("Incorrect argument numbers. Input at least 2 arguments.");
            numberOfArguments = false;
            return;
        } 

        try {
            // notive that the input files start at agr[1], not arg[0]
            archievefile = new File(args[0]);

            for (int i = 1; i < args.length; i++) {
                inputfile = new File(args[i]);

                if (!inputfile.exists() || !inputfile.isFile() || args.length <= 1) {
                    System.out.println("Invalide input file name.");
                    inputFileExists = false;
                    numberOfArguments = false;
                    return;
                } 
                filesToTar[i-1] = args[i];
                filesize[i-1] = inputfile.length();
                filenamesize[i-1] = args[i].length();
            }
            
            if(!archievefile.exists() || !archievefile.isFile()){
                if(archievefile.exists() || archievefile.isFile()){
                    archievefile.delete();
                }
            }

            try{
                archievefile.createNewFile();
            } catch (Exception e){
                return;
            }

            //archive file is at args[0]
            //layout: file-name-lenth, separator, filename, file-size, file

            out = new BinaryOut (args[0]);

            for(int i = 0; i < filesToTar.length; i++){
                out.write(filenamesize[i]);
                out.write(separator);

                out.write(filesToTar[i]);
                out.write(separator);

                out.write(filesize[i]);
                out.write(separator);

                in = new BinaryIn (args[i+1]);

                while(!in.isEmpty()){
                    out.write(in.readChar());
                }
            }


        } finally{
            if(out != null){
                out.close();
            }
        }

        String [] huffmanArray = {args[0]};
        SchubsH.main(huffmanArray);
    }

    public static Boolean fileExists(){
        return inputFileExists;
    }
   
    public static Boolean argumentNumber(){
        return numberOfArguments;
    }
    
}
