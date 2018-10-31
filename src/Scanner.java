import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Scanner {

	public static void main(String args[]) throws Exception {

		// Here set your directory of where you want to scan
		File aDirectory = new File("src/data");

		String[] filesInDir = aDirectory.list();

		for (int i = 0; i < filesInDir.length; i++) {
			File toIdentify = new File("src/data/" + filesInDir[i]);
			readContent(toIdentify);
		}

	}

	public static void readContent(File file) throws IOException, Exception {

		// Print what file the program is scanning 
		System.out.println("reading file : " + file.getCanonicalPath());

		String st, def;
		File file2 = new File("src/VirusDefinition.txt"); // virus definition file goes here
					
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		boolean found = false;
		int lines = 0;
		while ((def = br2.readLine()) != null) {
			while ((st = br.readLine()) != null) {
				if (st.contains(def)) {
					System.out.println("Suspicious code found at line : " + lines);
					found = true;
				}
				lines++;
			}
		}
		if (found == false) {
			System.out.println("No virus found in this file\n");
		} else {
			quarantineFile(file);
		}
	}

	public static void quarantineFile(File file) throws Exception {

		System.out.println("\nRunning Quarantine");
		String st, def;
		File file2 = new File("src/VirusDefinition.txt"); // virus definition file goes here
														
		BufferedReader br = new BufferedReader(new FileReader(file)); //BR for file to be scan
		BufferedReader br2 = new BufferedReader(new FileReader(file2)); // BR for virus definition
		BufferedWriter br3 = new BufferedWriter(new FileWriter("src/quarantine/tmp.txt")); // BW to write quarantine file

		while ((def = br2.readLine()) != null) {

			// Replace first 8 character for virus definition with "x"
			String toReplace;
			toReplace = "xxxxxxxx" + def.substring(7, def.length());

			while ((st = br.readLine()) != null) {
				if (st.contains(def)) {
					st = st.replace(def, toReplace);
				}
				br3.write(st + "\n");
			}
		}
		br3.close();

		if (file.delete()) {
			System.out.println("File has been quarantine");
		} else {
			System.out.println("Error");
		}

	}
}
