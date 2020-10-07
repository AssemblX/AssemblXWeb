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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TuSubunit;

public class SequenceFileParser {

	public static void parseSequenceFile(AssemblXWebModel model,
			TuSubunit currentTuSubunit, Boolean isBackbone) throws IOException {
		String sequenceFilePathName = model.getWorkingDirectory()
				+ File.separator
				+ currentTuSubunit.getParentTuUnit().getTuDirectoryName()
				+ File.separator + AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory")
				+ File.separator + currentTuSubunit.getSequenceFileName();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(sequenceFilePathName));
			String line;
			String content = "";
			Pattern patternGB = Pattern.compile("(LOCUS)( *)(\\S*)( *)");
			Pattern patternFA = Pattern.compile("(>)(\\w*)");
			Boolean headerFound = false;
			while ((line = reader.readLine()) != null) {
				if (isBackbone == false) {
					String sequenceSource = null;
					Matcher matcherGB = patternGB.matcher(line);
					Matcher matcherFA = patternFA.matcher(line);
					while (matcherGB.find() && headerFound == false) {
						headerFound = true;
						sequenceSource = matcherGB.group(3);
						if (sequenceSource != null) {
							currentTuSubunit.setPartSource(sequenceSource);
							currentTuSubunit.setSequenceFormat("Genbank");
						}
					}
					while (matcherFA.find() && headerFound == false) {
						headerFound = true;
						sequenceSource = matcherFA.group(2);
						if (sequenceSource != null) {
							currentTuSubunit.setPartSource(sequenceSource);
							currentTuSubunit.setSequenceFormat("FASTA");
						}
					}
				}
				content += line + "\n";
			}
			currentTuSubunit.setSequenceFileContent(content);
		} catch (IOException ioe) {
			if (reader != null) {
				reader.close();
			}
			throw new IOException(ioe);
		}
		if (reader != null) {
			reader.close();
		}
	}

	public static Integer extractLengthFromVectorFile(String pathToVectorFile)
			throws IOException {
		BufferedReader reader = null;
		Integer vectorLength = 0;
		try {
			reader = new BufferedReader(new FileReader(pathToVectorFile));
			String line;
			Pattern pattern = Pattern
					.compile("(LOCUS)( +)(.+)( +)(\\d+)( +)(bp)");
			while ((line = reader.readLine()) != null) {
				String vectorLengthString = null;
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					vectorLengthString = matcher.group(5);
					if (vectorLengthString != null) {
						vectorLength = new Integer(vectorLengthString);
					}
				}
			}
		} catch (IOException ioe) {
			if (reader != null) {
				reader.close();
			}
			throw new IOException(ioe);
		}
		if (reader != null) {
			reader.close();
		}
		return vectorLength;
	}

	public static String extractSequenceFromGenbankFileContent(
			String fileContent) {
		String[] contentLines = fileContent.split("\\n");
		String output = "";
		Pattern patternOrigin = Pattern.compile("ORIGIN");
		Pattern pattern = Pattern.compile("(.+\\d+)([ATGCatgc ]*)");
		String seqLine = "";
		Boolean inSequencePart = false;
		for (int i = 0; i < contentLines.length; i++) {
			Matcher matcherOrigin = patternOrigin.matcher(contentLines[i]);
			if (matcherOrigin.matches()) {
				inSequencePart = true;
			}
			Matcher matcher = pattern.matcher(contentLines[i]);
			if (matcher.find() && inSequencePart == true) {
				seqLine = matcher.group(2);
				seqLine = seqLine.replace(" ", "");
				output += seqLine;
			}
		}
		return output;
	}

	public static String extractSequenceFromGenbankFile(String filePath)
			throws IOException {
		String output = "";
		Pattern patternOrigin = Pattern.compile("ORIGIN");
		Pattern pattern = Pattern.compile("(.+\\d+)([ATGCatgc ]*)");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			String seqLine = "";
			Boolean inSequencePart = false;
			while ((line = reader.readLine()) != null) {
				Matcher matcherOrigin = patternOrigin.matcher(line);
				if (matcherOrigin.matches()) {
					inSequencePart = true;
				}
				Matcher matcher = pattern.matcher(line);
				if (matcher.find() && inSequencePart == true) {
					seqLine = matcher.group(2);
					seqLine = seqLine.replace(" ", "");
					output += seqLine;
				}
			}
			reader.close();
		} catch (IOException ioe) {
			if (reader != null) {
				reader.close();
			}
			throw new IOException(ioe);
		}
		if (reader != null) {
			reader.close();
		}
		return output;
	}
}
