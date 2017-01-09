/*
AssemblX: A three step assembly process for up to 25 genes

To express large sets of proteins in yeast or other host organisms, we
have developed a cloning framework which allows the modular cloning of
up to 25 genes into one circular artificial yeast chromosome.
	
AssemblXWeb assists the user with all design and assembly 
steps and therefore greatly reduces the time required to complete complex 
assemblies.
	
Copyright (C) 2016,  gremmels(at)mpimp-golm.mpg.de

AssemblXWeb is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

Contributors:
gremmels(at)mpimp-golm.mpg.de - initial API and implementation
*/
package mpimp.assemblxweb.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import mpimp.assemblxweb.db.AssemblXWebModel;

public class VectorEditorTool {

	public static void createVectorEditorFile(String genbankFilePath, AssemblXWebModel model) throws Exception {
		try {
		String firstPartPath = model.getServletContextRealPath() + File.separator + "VectorEditorParts" + File.separator + "FirstPart.txt";
		String firstPart = readFile(firstPartPath);
		String secondPartPath = model.getServletContextRealPath() + File.separator + "VectorEditorParts" + File.separator + "SecondPart.txt";
		String secondPart = readFile(secondPartPath);
		String genbankFileContent = readFile(genbankFilePath);
		genbankFileContent = genbankFileContent.replace("\"", "'");
		genbankFileContent = genbankFileContent.replace("&", "");
		int length = genbankFileContent.length();
		String content = firstPart + length + "&fileData=" + genbankFileContent + secondPart;
		String vectorEditorPath = model.getServletContextRealPath() + File.separator + "VectorEditor.html";
		deleteOldVectorEditorFile(vectorEditorPath);
		writeFile(content, vectorEditorPath);
		} catch (Exception e) {
			String message = "Error during creation of vector editor file. " + e.getMessage();
			throw new AssemblXException(message, VectorEditorTool.class);
		}
	}
	
	private static String readFile(String filePath) throws Exception {
		String content = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = reader.readLine()) != null) {
				content += line + "\n";
			}
			reader.close();
		} catch (Exception e) {
			if (reader != null) {
				reader.close();
			}
			String message = "Error during reading result file " + filePath + ". " + e.getMessage();
			throw new AssemblXException(message, VectorEditorTool.class);
		}
		return content;
	}

	private static void deleteOldVectorEditorFile(String filePath) {
		File vectorEditor = new File(filePath);
		if (vectorEditor.exists()) {
			vectorEditor.delete();
		}
	}
	
	private static void writeFile(String content, String filePath) throws Exception {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			if (bw != null) {
				bw.close();
			}
			String message = "Error writing vector editor file. " + e.getMessage();
			throw new AssemblXException(message, VectorEditorTool.class);
		}
	}

}
