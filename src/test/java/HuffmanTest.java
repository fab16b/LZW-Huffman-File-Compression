/**
 * Date: Nov 13, 2020
 * Course: Software Engineering II
 * Semester: Fall 2020
 * Author: Felix Mbikogbia
 * Programs creates and runs test cases for Tarsn.java
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import org.junit.Rule;
import org.junit.runner.Description;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;


//import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class HuffmanTest 
{
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    String filesPath = "Files" +File.separator;
    String testsArchivePath = "Files" +File.separator + "TestsArchive"+File.separator;
    String solutionArchivePath = "Files" +File.separator + "SolutionsArchive"+File.separator;


    //foldersEqual compares two folders to see if they are equal size.
	public static boolean foldersEqual(String[] args) {

		try {
			File file1 = new File(args[0]);
			File file2 = new File(args[1]);

			//return false if any of files passed isn't a directory nor exists.
			if(!(file1.exists() && file1.isDirectory() && file2.exists() && file2.isDirectory())){
				return false;
			}

			String[] sourceContent = file1.list();
			String[] destContent = file2.list();

			for (int i = 0; i < sourceContent.length && i < destContent.length; i++) {
				file1 = new File(sourceContent[i]);
				file2 = new File(destContent[i]);

				boolean isTwoEqual = FileUtils.contentEquals(file1, file2);

				if(!isTwoEqual){
					return false;
				}
			}

		} catch(Exception e){
			return false;
		}
		return true;
    }
    
    @Rule
   public TestRule watcher = new TestWatcher(){
      protected void starting(Description description){
         System.out.println("Starting test: "+ description.getMethodName());
      }
   };

    @Before
    public void SetUpStreams() throws IOException{
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() throws IOException{
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    //Dynamic text content generator test. randomly generates file content and Tars it.
    @Test
    public void RandomContentGenerator(){

        //generating random files
        File out = new File(filesPath+"random1.txt");
        File out2 = new File(filesPath+"random2.txt");
        File out3 = new File(filesPath+"random3.txt");
        File out4 = new File(filesPath+"random4.txt");
        File out5 = new File(filesPath+"random5.txt");
        FileWriter fileWriter1, fileWriter2, fileWriter3, fileWriter4, fileWriter5 = null;
        Random random = new Random();
        int n = random.nextInt(100);

        try{
            fileWriter1 = new FileWriter(out);
            fileWriter2 = new FileWriter(out2);
            fileWriter3 = new FileWriter(out3);
            fileWriter4 = new FileWriter(out4);
            fileWriter5 = new FileWriter(out5);
            // Wrap the writer with buffered streams
            BufferedWriter writer1 = new BufferedWriter(fileWriter1);
            BufferedWriter writer2 = new BufferedWriter(fileWriter2);
            BufferedWriter writer3 = new BufferedWriter(fileWriter3);
            BufferedWriter writer4 = new BufferedWriter(fileWriter4);
            BufferedWriter writer5 = new BufferedWriter(fileWriter5);

            char line;
            while (n > 0) {
                // Randomize an integer and write it to the output file
                line = (char) (' '+ random.nextInt(94));
                writer1.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer2.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer3.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer4.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer5.write(line);
                n--;                
            }
            // Close the stream
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            writer5.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String [] argsH = {filesPath+"random1.txt", filesPath+"random2.txt", filesPath+"random3.txt", filesPath+"random4.txt", filesPath+"random5.txt"};
        String [] argsL = {filesPath+"random1.txt", filesPath+"random2.txt", filesPath+"random3.txt", filesPath+"random4.txt", filesPath+"random5.txt"};
        String [] argsARC = {filesPath+"ShubsArchiveHuff.zh", filesPath+"random1.txt", filesPath+"random2.txt", filesPath+"random3.txt", filesPath+"random4.txt", filesPath+"random5.txt"};
        SchubsH.main(argsH);
        SchubsL.main(argsL);
    }

    //Test for when the file to be Tars'd doesn't exist
    @Test
    public void fileDoNotExist(){
        String [] args = {filesPath+"Archive.txt", filesPath+"donotexist1.txt", filesPath+"donotexist2.txt", filesPath+"donotexist3.txt", filesPath+"donotexist4.txt", filesPath+"donotexist5.txt",filesPath+"donotexist6.txt"};
        Tarsn.main(args);
        assertEquals(false, Tarsn.fileExists());
    }

    //Test for when the User Passes in wrong number of arguments
    @Test
    public void wrongNumberOfArguments(){
    //THe only time that number of arguments will be wrong is when one passes 1 argument
        String [] args = {filesPath+"1.txt"};        
        Tarsn.main(args);
        assertEquals(false, Tarsn.argumentNumber());
    }

    /**
     *  This cases are satisfied in the passingTestCases() function
     * Test1 a, b, c: Normal case
     * Test2 a, b, c: The file(s) to be Tars'd are empty
     * Test3 a, b, c: The destination archive already exists
     * Test4 a, b, c: The file(s) to be Tars'd contain many characters
     * Test5 a, b, c: The file(s) to be Tars'd contain characters such as spaces and line endings
     */

    @Test
    public void passingTestCases() {
        String [] tests = {testsArchivePath+"archive1.txt", filesPath+"Test1a.txt",filesPath+"Test1b.txt", filesPath+"Test1c.txt",
        testsArchivePath+"archive2.txt", filesPath+"Test2a.txt", filesPath+"Test2b.txt", filesPath+"Test2c.txt",
        testsArchivePath+ "archive3.txt", filesPath+"Test3a.txt", filesPath+"Test3b.txt", filesPath+"Test3c.txt",
        testsArchivePath+ "archive4.txt", filesPath+"Test4a.txt", filesPath+"Test4b.txt", filesPath+"Test4c.txt",
        testsArchivePath+ "archive5.txt", filesPath+"Test5a.txt", filesPath+"Test5b.txt", filesPath+"Test5c.txt"};

        for(int i = 0; i < tests.length; i=i+4){
            String [] args = {tests[i], tests[i+1], tests[i+2], tests[i+3]};
            Tarsn.main(args);   
        }
        String [] folders = {filesPath+"TestsArchive", filesPath+"SolutionsArchive"};
        assertTrue(foldersEqual(folders));
    }

    //The file to be Tars'd is instead a directory and should therefore fail
    @Test
    public void fileIsDirectory() {
        String [] tests = {testsArchivePath+ "archive.txt", filesPath+"isFolder1", filesPath+"Test1b.txt", filesPath+"isFolder2", filesPath+"Test1a.txt"};
        Tarsn.main(tests);
        assertEquals(false, Tarsn.fileExists());
    }


     /**
     * Test1a, 2a, 3a, 4a: Any combination of the files to be Tars'd containing spaces, line endings, being empty, not existing, being directories, containing many characters, or containing few characters.
     */

     @Test
     public void compbination(){
        String [] tests = {testsArchivePath+ "archive.txt", filesPath+"isFolder1", filesPath+"Test1b.txt", filesPath+"Test2a", filesPath+"Test5a.txt", filesPath+"Test4a.txt", filesPath+"doesnotexist.txt", };
        Tarsn.main(tests);
        assertEquals(false, Tarsn.fileExists());
     }

}
