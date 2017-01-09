package mpimp.assemblxweb.teststuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InvokeVectorEditorTester {

	public static void main(String[] args) {
		// &fileData=
		InvokeVectorEditorTester ivet = new InvokeVectorEditorTester();
		try {
			String firstPartPath = "TestFiles" + File.separator
					+ "FirstPart.txt";
			String firstPart = ivet.readFile(firstPartPath);
			String secondPartPath = "TestFiles" + File.separator
					+ "SecondPart.txt";
			String secondPart = ivet.readFile(secondPartPath);
			String resultFilePath = "TestFiles" + File.separator
					+ "ppla00001.gb";
			String resultFile = ivet.readFile(resultFilePath);
			resultFile = resultFile.replace("\"", "'");
			resultFile = resultFile.replace("&", "");
			int length = resultFile.length();
			System.out.println(length);
			String content = firstPart + length + "&fileData=" + resultFile
					+ secondPart;
//			String htmlFilePath = "/home" + File.separator + "gremmels"
//					+ File.separator + "winhome" + File.separator
//					+ "Programmierung_BioInf" + File.separator + "AssemblX"
//					+ File.separator + "VectorEditorTester" + File.separator
//					+ "VectorEditor.html";
			String htmlFilePath = "H:\\Programmierung_BioInf" + File.separator + "AssemblX"
					+ File.separator + "VectorEditorTester" + File.separator
					+ "VectorEditor.html";
			ivet.writeFile(content, htmlFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readFile(String resultFilePath) throws IOException {
		String content = "";
		BufferedReader reader = new BufferedReader(new FileReader(
				resultFilePath));
		String line;
		while ((line = reader.readLine()) != null) {
			content += line + "\n";
		}
		reader.close();
		return content;
	}

	public void writeFile(String content, String filePath) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
		bw.write(content);
		bw.close();
	}

}
