# LZW-Huffman-File-Compression

--
classes
--
- SchubsL.java
  compresses files using LZW compression algorigthm
  Compilation:    javac SchubsL.java
 *  Execution:      java SchubsL file.txt file2.txt ....
 * //file to be compressed. file.txt is compressed to file.txt.ll
 *  Execution:      java SchubsL *.txt
 *  Dependencies:   BinaryIn.java BinaryOut.java
 *
 *  Compress input files using the LZW compression algorithm
- SchubsH.java
    compresses files using Huffman compression algorigthm
- SchubsArch
    Creates an archives file, then compresses the archive using Huffman compression algorithm
- Deschubs.java
    - uncompresses huffman compressed files
    - uncompressses lzw compressed files
    - untars and uncompresses archive files

    Compilation:  javac Deschubs.java
 *  Execution:    java Deschubs *.ll
 * Execution:    java Deschubs *.hh
 * Execution:    java Deschubs *.zh
 * Execution:    java Deschubs file.ll file.hh file.zh ....
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 * Decompresses Huffman compressed files
 * Decompresses LZW compressed files
 * Decompresses and Untars compressed tar files



