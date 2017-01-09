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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.GenbankData;
import mpimp.assemblxweb.db.GenbankData.Conformation;

public class GenbankWriter {

	public GenbankWriter() {
		content_ = new ArrayList<String>();
		// only a first - quick and dirty - workaround
		annotatedSequenceSnippets_ = new HashMap<String, List<Integer>>();
	}

	public void writeGenbankFile(GenbankData genbankData, String directoryPath) throws IOException {
		writeHeaderLine(genbankData);
		formatAnnotations(genbankData);
		formatOriginSection(genbankData.getSequence());
		content_.add("//");
		Path path = Paths.get(directoryPath + File.separator + genbankData.getLocusName() + ".gb");
		Files.write(path, content_, StandardCharsets.UTF_8);
	}

	public String getContentAsString() {
		String contentString = "";
		for (String line : content_) {
			contentString += line;
		}
		return contentString;
	}

	private void writeHeaderLine(GenbankData genbankData) {
		String headerLine = "LOCUS";
		for (int i = 0; i < 7; i++) {
			headerLine += " ";
		}
		headerLine += genbankData.getLocusName();
		for (int l = 0; l < 7; l++) {
			headerLine += " ";
		}
		headerLine += genbankData.getSequence().length();
		headerLine += " bp";
		for (int j = 0; j < 7; j++) {
			headerLine += " ";
		}
		headerLine += "DNA";
		for (int k = 0; k < 7; k++) {
			headerLine += " ";
		}
		if (genbankData.getConformation() == Conformation.LINEAR) {
			headerLine += "linear";
		} else if (genbankData.getConformation() == Conformation.CIRCULAR) {
			headerLine += "circular";
		}
		content_.add(headerLine);
	}

	protected void formatAnnotations(GenbankData genbankData) {
		List<AnnotationRecord> annotations = genbankData.getAnnotationRecords();
		String headerLine = "FEATURES";
		for (int i = 0; i < 13; i++) {
			headerLine += " ";
		}
		headerLine += "Location/Qualifiers";
		content_.add(headerLine);

		List<String> partSequences = new ArrayList<String>();

		for (AnnotationRecord currentAnnotationRecord : annotations) {
			String wholeSequence = genbankData.getSequence().toUpperCase();
			String partSequence = currentAnnotationRecord.getAnnotationPartItem().getPartSequence().toUpperCase();
			if (!wholeSequence.contains(partSequence) || partSequence.equals("")) {
				continue;
			}
			if (partSequences.contains(partSequence)) {
				// ensure that every part sequence is annotated only once
				continue;
			}
			partSequences.add(partSequence);
			Pattern pattern = Pattern.compile(partSequence);
			Matcher matcher = pattern.matcher(wholeSequence);
			while (matcher.find()) {

				if (matcher.end() - matcher.start() < 5) {
					// this is only a workaround to hide annotation of very
					// short sequence pieces
					// since they tend to occur very often and will mess up the
					// genbank file
					continue;
				}

				String line = "";
				// position of feature name is essential for being parsed by
				// vector editor (and other parsers)
				for (int n = 0; n < 5; n++) {
					line += " ";
				}
				String name = currentAnnotationRecord.getAnnotationName();
				line += name;
				if (name.length() > 16) {
					name = name.substring(0, 16);
				}
				int noOfBlancs = 16 - name.length();
				for (int m = 0; m < noOfBlancs; m++) {
					line += " ";
				}

				line += parseAnnotationPartItem(currentAnnotationRecord.getAnnotationPartItem(), wholeSequence);

				content_.add(line);

				List<String> qualifiers = currentAnnotationRecord.getQualifiers();
				for (String currentQualifier : qualifiers) {
					line = "";
					for (int o = 0; o < 21; o++) {
						line += " ";
					}
					line += currentQualifier;
					content_.add(line);
				}
			}
		}
	}

	public String parseAnnotationPartItem(AnnotationPartItem item, String wholeSequence) {
		String part = "";
		Boolean partWritten = false;
		if (item.getIsReverseComplement()) {
			part += "complement(";
		}
		if (item.getIsJoinItem()) {
			part += "join(";
		}

		int parentStart = -1;
		int parentEnd = -1;

		if (item.getParentItem() != null) {
			String parentSequence = item.getParentItem().getPartSequence();
			Pattern parentPattern = Pattern.compile(parentSequence);
			Matcher parentMatcher = parentPattern.matcher(wholeSequence);
			while (parentMatcher.find()) {
				parentStart = parentMatcher.start();
				parentEnd = parentMatcher.end();
			}
		}

		String partSequence = item.getPartSequence();
		if (partSequence != "" && item.getChildItems().size() == 0) {
			Pattern pattern = Pattern.compile(partSequence);
			Matcher matcher = pattern.matcher(wholeSequence);
			while (matcher.find()) {
				if (item.getIsSiteItem()) {
					if (matcher.start() == parentStart + item.getRelativeSiteStart() - 1) {
						part += parentStart + item.getRelativeSiteStart();
						part += "^";
						part += parentStart + item.getRelativeSiteStart() + 1;
					}
				} else {
					// ensure that found location of part sequence is within the
					// parent sequence
					if (((parentStart == -1 || parentEnd == -1)
							|| (matcher.start() >= parentStart && matcher.end() <= parentEnd))
							&& (partWritten == false)) {
						if (!annotatedSequenceSnippets_.containsKey(partSequence)) {
							partWritten = true;
							part += matcher.start() + 1;
							part += "..";
							part += matcher.end();
							List<Integer> startPositions = new ArrayList<Integer>();
							startPositions.add(matcher.start());
							annotatedSequenceSnippets_.put(partSequence, startPositions);
						} else if (annotatedSequenceSnippets_.containsKey(partSequence)
								&& !annotatedSequenceSnippets_.get(partSequence).contains(matcher.start())) {
							partWritten = true;
							part += matcher.start() + 1;
							part += "..";
							part += matcher.end();
							annotatedSequenceSnippets_.get(partSequence).add(matcher.start());
						}
					}
				}
			}
		}

		final int childCount = item.getChildItems().size();
		int itemCounter = 0;
		for (AnnotationPartItem child : item.getChildItems()) {
			itemCounter++;
			part += parseAnnotationPartItem(child, wholeSequence);
			if (itemCounter < childCount) {
				part += ",";
			}
		}
		if (item.getIsReverseComplement()) {
			part += ")";
		}
		if (item.getIsJoinItem()) {
			part += ")";
		}
		return part;
	}

	private void formatOriginSection(String pureSequence) {
		String headerLine = "ORIGIN";
		content_.add(headerLine);
		Integer overallIndex = 0;
		while (overallIndex < pureSequence.length()) {
			String line = "";
			int noOfBlancs = 8 - calculateLeadingBlancs(overallIndex + 1);
			for (int n = 0; n < noOfBlancs; n++) {
				line += " ";
			}
			line += overallIndex + 1;
			for (int i = 0; i < 6; i++) {
				String group = "";
				group += " ";
				for (int j = 0; j < 10; j++) {
					if (overallIndex < pureSequence.length()) {
						group += pureSequence.substring(overallIndex, overallIndex + 1);
						overallIndex++;
					}
				}
				line += group;
			}
			content_.add(line);
		}
	}

	private int calculateLeadingBlancs(Integer counter) {
		Double counterDouble = counter.doubleValue();
		int noOfBlancs = (int) (java.lang.Math.log10(counterDouble) - java.lang.Math.log10(counterDouble) % 1);
		return noOfBlancs;
	}

	private List<String> content_;
	// only a first - quick and dirty - workaround
	private Map<String, List<Integer>> annotatedSequenceSnippets_;

}