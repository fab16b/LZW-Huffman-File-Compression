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


import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class HuffmanTest 
{
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    // String filesPath = "Files" +File.separator;
    // String testsArchivePath = "Files" +File.separator + "TestsArchive"+File.separator;
    // String solutionArchivePath = "Files" +File.separator + "SolutionsArchive"+File.separator;
    String randomSchubsH = "Resources"+File.separator + "RandomTestGenerator"+File.separator+"SchubsH"+File.separator;
    String randomSchubsL = "Resources"+File.separator + "RandomTestGenerator"+File.separator+"SchubsL"+File.separator;
    String randomSchubsArc = "Resources"+File.separator + "RandomTestGenerator"+File.separator+"SchubsArc"+File.separator;

    String pathDeschubs_Files = "Resources"+File.separator + "Deschubs"+File.separator+"OriginalFiles"+File.separator;
    String pathDeschubs_HuffCompressed = "Resources"+File.separator + "Deschubs"+File.separator+"HuffCompressed"+File.separator;
    String pathDeschubs_LZWCompressed = "Resources"+File.separator + "Deschubs"+File.separator+"LZCompressed"+File.separator;
    String pathDeschubs_HuffSolution= "Resources"+File.separator + "Deschubs"+File.separator+"HuffSolution"+File.separator;
    String pathDeschubs_LZWSolution = "Resources"+File.separator + "Deschubs"+File.separator+"LZWSolution"+File.separator;
    String pathDeschubs_CompressedArchive = "Resources"+File.separator + "Deschubs"+File.separator+"CompressedArchive"+File.separator;
    String pathDeschubs_UnArchive = "Resources"+File.separator + "Deschubs"+File.separator+"UnArchiveSolution"+File.separator;

    String pathSchubsArc_Files = "Resources"+File.separator + "SchubsArc"+File.separator+"OriginalFiles"+File.separator;
    String pathSchubsArc_Archive = "Resources"+File.separator + "SchubsArc"+File.separator+"HuffCompressedArchive"+File.separator;
    String pathSchubsArc_Solution = "Resources"+File.separator + "SchubsArc"+File.separator+"HuffCompressSolution"+File.separator;

    String pathSchubsH_TestFiles = "Resources"+File.separator + "SchubsH"+File.separator+"TestFiles"+File.separator;
    String pathSchubsH_SolutionFiles = "Resources"+File.separator + "SchubsH"+File.separator+"SolutionFiles"+File.separator;

    String pathSchubsL_TestFiles= "Resources"+File.separator + "SchubsL"+File.separator+"TestFiles"+File.separator;
    String pathSchubs__SolutionFiles = "Resources"+File.separator + "SchubsL"+File.separator+"SolutionFiles"+File.separator;


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
        File out = new File(randomSchubsH+"random1.txt");
        File out2 = new File(randomSchubsH+"random2.txt");
        File out3 = new File(randomSchubsH+"random3.txt");
        File out4 = new File(randomSchubsH+"random4.txt");
        File out5 = new File(randomSchubsH+"random5.txt");

        File out6 = new File(randomSchubsL+"random1.txt");
        File out7 = new File(randomSchubsL+"random2.txt");
        File out8 = new File(randomSchubsL+"random3.txt");
        File out9 = new File(randomSchubsL+"random4.txt");
        File out10 = new File(randomSchubsL+"random5.txt");

        File out11 = new File(randomSchubsArc+"random1.txt");
        File out12 = new File(randomSchubsArc+"random2.txt");
        File out13 = new File(randomSchubsArc+"random3.txt");
        File out14 = new File(randomSchubsArc+"random4.txt");
        File out15 = new File(randomSchubsArc+"random5.txt");
        FileWriter fileWriter1, fileWriter2, fileWriter3, fileWriter4, fileWriter5 = null;
        FileWriter fileWriter6, fileWriter7, fileWriter8, fileWriter9, fileWriter10 = null;
        FileWriter fileWriter11, fileWriter12, fileWriter13, fileWriter14, fileWriter15 = null;
        Random random = new Random();
        int n = random.nextInt(100);

        try{
            fileWriter1 = new FileWriter(out);
            fileWriter2 = new FileWriter(out2);
            fileWriter3 = new FileWriter(out3);
            fileWriter4 = new FileWriter(out4);
            fileWriter5 = new FileWriter(out5);

            fileWriter6 = new FileWriter(out6);
            fileWriter7 = new FileWriter(out7);
            fileWriter8 = new FileWriter(out8);
            fileWriter9 = new FileWriter(out9);
            fileWriter10 = new FileWriter(out10);
            fileWriter11 = new FileWriter(out11);
            fileWriter12 = new FileWriter(out12);
            fileWriter13 = new FileWriter(out13);
            fileWriter14 = new FileWriter(out14);
            fileWriter15 = new FileWriter(out15);
            // Wrap the writer with buffered streams
            BufferedWriter writer1 = new BufferedWriter(fileWriter1);
            BufferedWriter writer2 = new BufferedWriter(fileWriter2);
            BufferedWriter writer3 = new BufferedWriter(fileWriter3);
            BufferedWriter writer4 = new BufferedWriter(fileWriter4);
            BufferedWriter writer5 = new BufferedWriter(fileWriter5);
            BufferedWriter writer6 = new BufferedWriter(fileWriter6);
            BufferedWriter writer7 = new BufferedWriter(fileWriter7);
            BufferedWriter writer8 = new BufferedWriter(fileWriter8);
            BufferedWriter writer9 = new BufferedWriter(fileWriter9);
            BufferedWriter writer10 = new BufferedWriter(fileWriter10);
            BufferedWriter writer11 = new BufferedWriter(fileWriter11);
            BufferedWriter writer12 = new BufferedWriter(fileWriter12);
            BufferedWriter writer13 = new BufferedWriter(fileWriter13);
            BufferedWriter writer14 = new BufferedWriter(fileWriter14);
            BufferedWriter writer15 = new BufferedWriter(fileWriter15);

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
                line = (char) (' '+ random.nextInt(94));
                writer6.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer7.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer8.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer9.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer10.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer11.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer12.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer13.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer14.write(line);
                line = (char) (' '+ random.nextInt(94));
                writer15.write(line);
                n--;                
            }
            // Close the stream
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            writer5.close();
            writer6.close();
            writer7.close();
            writer8.close();
            writer9.close();
            writer10.close();
            writer11.close();
            writer12.close();
            writer13.close();
            writer14.close();
            writer15.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String [] argsH = {randomSchubsH+"random1.txt", randomSchubsH+"random2.txt", randomSchubsH+"random3.txt", randomSchubsH+"random4.txt", randomSchubsH+"random5.txt"};
        String [] argsL = {randomSchubsL+"random1.txt", randomSchubsL+"random2.txt", randomSchubsL+"random3.txt", randomSchubsL+"random4.txt", randomSchubsL+"random5.txt"};
        String [] argsARC = {randomSchubsArc+"ShubsArchiveHuff.zh", randomSchubsArc+"random1.txt", randomSchubsArc+"random2.txt", randomSchubsArc+"random3.txt", randomSchubsArc+"random4.txt", randomSchubsArc+"random5.txt"};
        SchubsH.main(argsH);
        SchubsL.main(argsL);
        SchubsArc.main(argsARC);
    }

    //Test for when an input file doesn't exist
    @Test
    public void fileDoNotExist(){
        String [] argsH = {pathSchubsH_TestFiles+"testinghere.txt", pathSchubsH_TestFiles+"donotexist1.txt", pathSchubsH_TestFiles+"donotexist2.txt", pathSchubsH_TestFiles+"donotexist3.txt", pathSchubsH_TestFiles+"donotexist4.txt", pathSchubsH_TestFiles+"donotexist5.txt",pathSchubsH_TestFiles+"donotexist6.txt"};
        String [] argsL = {pathSchubsL_TestFiles+"Archive.txt", pathSchubsL_TestFiles+"donotexist1.txt", pathSchubsL_TestFiles+"donotexist2.txt", pathSchubsL_TestFiles+"donotexist3.txt", pathSchubsL_TestFiles+"donotexist4.txt", pathSchubsL_TestFiles+"donotexist5.txt",pathSchubsL_TestFiles+"donotexist6.txt"};
        String [] argsArc = {pathSchubsArc_Archive+"Archive.zh", pathSchubsArc_Files+"donotexist1.txt", pathSchubsArc_Files+"donotexist2.txt", pathSchubsArc_Files+"donotexist3.txt", pathSchubsArc_Files+"donotexist4.txt", pathSchubsArc_Files+"donotexist5.txt",pathSchubsArc_Files+"donotexist6.txt"};
        String [] argsDes = {pathDeschubs_Files+"Archive.zl", pathDeschubs_Files+"donotexist1.txt.hh", pathDeschubs_Files+"donotexist2.txt.ll", pathDeschubs_Files+"donotexist3.txt.ll", pathDeschubs_Files+"donotexist4.txt.hh", pathDeschubs_Files+"donotexist5.zh",pathDeschubs_Files+"donotexist6.txt.hh"};
        SchubsH.main(argsH);
        SchubsL.main(argsL);
        SchubsArc.main(argsArc);
        Deschubs.main(argsDes);
        assertEquals(false, SchubsH.fileExists());
        assertEquals(false, SchubsL.fileExists());
        assertEquals(false, SchubsArc.fileExists());
        assertEquals(false, Deschubs.fileExists());
    }

    // //Test for when the User Passes in wrong number of arguments
    @Test
    public void wrongNumberOfArguments(){
        //THe only time that number of arguments will be wrong is when one passes 1 argument
        String [] argsArc = {pathSchubsArc_Files+"Test1a.txt"};

        //THe only time that number of arguments will be wrong is no argument is passed
        String [] argsH = {}; 
        String [] argsL = {}; 
        String [] argsDes = {}; 

        SchubsArc.main(argsArc);
        SchubsH.main(argsH);
        SchubsL.main(argsL);
        Deschubs.main(argsDes);

        assertEquals(false, SchubsArc.argumentNumber());
        assertEquals(false, SchubsH.argumentNumber());
        assertEquals(false, SchubsL.argumentNumber());
        assertEquals(false, Deschubs.argumentNumber());
    }

    // /**
    //  *  This cases are satisfied in the passingTestCases() function
    //  * Test1 a, b, c: Normal case
    //  * Test2 a, b, c: The file(s) to be Tars'd are empty
    //  * Test3 a, b, c: The destination archive already exists
    //  * Test4 a, b, c: The file(s) to be Tars'd contain many characters
    //  * Test5 a, b, c: The file(s) to be Tars'd contain characters such as spaces and line endings
    //  */

    // @Test
    // public void passingTestCases() {
    //     String [] tests = {testsArchivePath+"archive1.txt", filesPath+"Test1a.txt",filesPath+"Test1b.txt", filesPath+"Test1c.txt",
    //     testsArchivePath+"archive2.txt", filesPath+"Test2a.txt", filesPath+"Test2b.txt", filesPath+"Test2c.txt",
    //     testsArchivePath+ "archive3.txt", filesPath+"Test3a.txt", filesPath+"Test3b.txt", filesPath+"Test3c.txt",
    //     testsArchivePath+ "archive4.txt", filesPath+"Test4a.txt", filesPath+"Test4b.txt", filesPath+"Test4c.txt",
    //     testsArchivePath+ "archive5.txt", filesPath+"Test5a.txt", filesPath+"Test5b.txt", filesPath+"Test5c.txt"};

    //     for(int i = 0; i < tests.length; i=i+4){
    //         String [] args = {tests[i], tests[i+1], tests[i+2], tests[i+3]};
    //         Tarsn.main(args);   
    //     }
    //     String [] folders = {filesPath+"TestsArchive", filesPath+"SolutionsArchive"};
    //     assertTrue(foldersEqual(folders));
    // }

    // //The file to be Tars'd is instead a directory and should therefore fail
    // @Test
    // public void fileIsDirectory() {
    //     String [] tests = {testsArchivePath+ "archive.txt", filesPath+"isFolder1", filesPath+"Test1b.txt", filesPath+"isFolder2", filesPath+"Test1a.txt"};
    //     Tarsn.main(tests);
    //     assertEquals(false, Tarsn.fileExists());
    // }


    //  /**
    //  * Test1a, 2a, 3a, 4a: Any combination of the files to be Tars'd containing spaces, line endings, being empty, not existing, being directories, containing many characters, or containing few characters.
    //  */

    //  @Test
    //  public void compbination(){
    //     String [] tests = {testsArchivePath+ "archive.txt", filesPath+"isFolder1", filesPath+"Test1b.txt", filesPath+"Test2a", filesPath+"Test5a.txt", filesPath+"Test4a.txt", filesPath+"doesnotexist.txt", };
    //     Tarsn.main(tests);
    //     assertEquals(false, Tarsn.fileExists());
    //  }

}
