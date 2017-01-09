package mpimp.assemblxweb.teststuff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileStuffTester {

	public static void main(String[] args) {
		String filePath = "/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir/A1/j5_Jgremmels_20160126081004LKL_/ppla00001.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = br.readLine()) != null) {
				Pattern pattern = Pattern.compile("\\w+");
				Pattern patternEmpty = Pattern.compile("^\\s");
				Matcher matcher = pattern.matcher(line);
				Matcher matcherEmpty = patternEmpty.matcher(line);
				if (matcher.find()) {
					System.out.println("String found");
				}
				if (matcherEmpty.find()) {
					System.out.println("Empty line found");
				}
				System.out.println(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
