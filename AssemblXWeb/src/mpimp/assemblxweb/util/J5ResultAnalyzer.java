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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.HomologyRegions;
import mpimp.assemblxweb.db.Level_0_Protocol;
import mpimp.assemblxweb.db.Level_0_ProtocolSubunit;
import mpimp.assemblxweb.db.Level_0_Protocol_WetLabSubunit;
import mpimp.assemblxweb.db.RestrictionEnzymes;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;

public class J5ResultAnalyzer {

	public static void unpackResult(TranscriptionalUnit currentTuUnit,
			AssemblXWebModel model) throws ZipException, IOException {
		String zipFilePath = model.getWorkingDirectory() + File.separator
				+ currentTuUnit.getTuDirectoryName() + File.separator
				+ currentTuUnit.getResultDirectoryName() + ".zip";
		File fsourceZipFile = new File(zipFilePath);

		String resultDirectoryPath = model.getWorkingDirectory()
				+ File.separator + currentTuUnit.getTuDirectoryName()
				+ File.separator + currentTuUnit.getResultDirectoryName();
		File resultDirectory = new File(resultDirectoryPath);
		resultDirectory.mkdir();

		ZipFile sourceZipFile = new ZipFile(fsourceZipFile);
		Enumeration<? extends ZipEntry> en = sourceZipFile.entries();

		while (en.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) en.nextElement();
			File destinationFilePath = new File(resultDirectoryPath,
					entry.getName());

			BufferedInputStream bis = new BufferedInputStream(
					sourceZipFile.getInputStream(entry));

			int b;
			byte buffer[] = new byte[1024];

			FileOutputStream fos = new FileOutputStream(destinationFilePath);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

			while ((b = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, b);
			}

			bos.flush();
			bos.close();

			bis.close();
		}

		sourceZipFile.close();
	}

	public static void findResultFileName(TranscriptionalUnit currentTuUnit,
			AssemblXWebModel model) throws IOException {
		String plasmidsListPath = model.getWorkingDirectory()
				+ File.separator
				+ currentTuUnit.getTuDirectoryName()
				+ File.separator
				+ currentTuUnit.getResultDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"masterPlasmidsListFileName");

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(plasmidsListPath));
			String line = "";
			String currentLine = "";
			while ((line = reader.readLine()) != null) {
				currentLine = line;
			}
			String[] elements = currentLine.split(",");
			String resultFileName = elements[0] + ".csv";
			currentTuUnit.setResultFileName(resultFileName);
			String resultGenbankFileName = elements[0] + ".gb";
			currentTuUnit.setResultGenbankFileName(resultGenbankFileName);
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

	public static void parseResultFile(TranscriptionalUnit currentTuUnit,
			AssemblXWebModel model) throws IOException {
		String resultFilePath = model.getWorkingDirectory() + File.separator
				+ currentTuUnit.getTuDirectoryName() + File.separator
				+ currentTuUnit.getResultDirectoryName() + File.separator
				+ currentTuUnit.getResultFileName();

		BufferedReader reader = null;
		// Regexp patterns
		Pattern patternPartsListAnchor = Pattern
				.compile("Target Part Ordering/Selection/Strategy");
		Pattern patternPartsListContent = Pattern
				.compile("\\d+,\\d+,.+,\\w+,.+,*");
		Pattern patternAnnealedOligos = Pattern
				.compile("\"?Annealed Oligos\"?,\"?Top Oligo\"?,,\"?Bottom Oligo\"?");
		Pattern patternAnnealedOligosContent = Pattern
				.compile("\\d+,\\d+,[^,]+,\\d+,[^,]+,.*");
		Pattern patternPcrReactions = Pattern
				.compile("\"?PCR Reactions\"?,,,\"?Forward Oligo\"?,,\"?Reverse Oligo\"?");
		Pattern patternPcrReactionsContent = Pattern.compile("\\d+,\\w+,.*");
		Pattern patternFinalAssembledVector = Pattern
				.compile("Final Assembled Vector");
		Pattern patternFinalAssembledVectorContent = Pattern
				.compile("\\d+,[AGCTagct]+");
		Pattern patternNonDegeneratePartsAndSources = Pattern
				.compile("Non-degenerate Part IDs and Sources");
		Pattern patternNonDegeneratePartsAndSourcesContent = Pattern
				.compile("\\d+,\\S+,\\S+,(TRUE|FALSE),\\d+,\\d+,\\d+,[AGCTagct]+");

		Level_0_Protocol protocol = new Level_0_Protocol();
		//protocol.setTranscriptionalUnitName(currentTuUnit.getName());
		protocol.setTranscriptionalUnitPosition(currentTuUnit.getPosition());
		protocol.setTuParentModuleName(currentTuUnit.getParentModule()
				.getName());
		protocol.setResultFileName(currentTuUnit.getResultFileName());
		List<Level_0_ProtocolSubunit> partsListUnits = new ArrayList<Level_0_ProtocolSubunit>();
		List<Level_0_Protocol_WetLabSubunit> annealedOligoEntries = new ArrayList<Level_0_Protocol_WetLabSubunit>();
		List<Level_0_Protocol_WetLabSubunit> pcrReactionEntries = new ArrayList<Level_0_Protocol_WetLabSubunit>();
		try {
			reader = new BufferedReader(new FileReader(resultFilePath));
			String line = "";
			// flags for marking the current position while file parsing
			boolean foundPartsList = false;
			boolean foundPartsListContent = false;
			boolean foundAnnealedOligos = false;
			boolean foundAnnealedOligosContent = false;
			boolean foundPCRReactions = false;
			boolean foundPCRReactionsContent = false;
			boolean foundFinalAssembledVector = false;
			boolean foundFinalAssembledVectorContent = false;
			boolean foundNonDegeneratePartsAndSources = false;
			boolean foundNonDegeneratePartsAndSourcesContent = false;
			// counters
			int partsListEntryCounter = 0;
			int nonDegeneratePartsCounter = 0;
			// read the file line wise and check each line if it depicts the
			// beginning of a block to read and
			// get now the data from the block
			while ((line = reader.readLine()) != null) {
				Matcher matcherPartsList = patternPartsListAnchor.matcher(line);
				Matcher matcherPartsListContent = patternPartsListContent
						.matcher(line);
				Matcher matcherAnnealedOligos = patternAnnealedOligos
						.matcher(line);
				Matcher matcherAnnealedOligosContent = patternAnnealedOligosContent
						.matcher(line);
				Matcher matcherPcrReactions = patternPcrReactions.matcher(line);
				Matcher matcherPcrReactionsContent = patternPcrReactionsContent
						.matcher(line);
				Matcher matcherFinalAssembledVector = patternFinalAssembledVector
						.matcher(line);
				Matcher matcherFinalAssembledVectorContent = patternFinalAssembledVectorContent
						.matcher(line);
				Matcher matcherNonDegeneratePartsAndSources = patternNonDegeneratePartsAndSources
						.matcher(line);
				Matcher matcherNonDegeneratePartsAndSourcesContent = patternNonDegeneratePartsAndSourcesContent
						.matcher(line);

				if (matcherNonDegeneratePartsAndSources.find()) {
					foundNonDegeneratePartsAndSources = true;
				}
				if (foundNonDegeneratePartsAndSources == true) {
					if (matcherNonDegeneratePartsAndSourcesContent.find()) {
						foundNonDegeneratePartsAndSourcesContent = true;
						nonDegeneratePartsCounter++;
						if (nonDegeneratePartsCounter == 1) {
							String[] parts = line.split(",");
							if (parts.length == 8) {
								Integer backboneSize = new Integer(
										stripQuotes(parts[6]));
								protocol.setBackboneSize(backboneSize);
							} else {
								protocol.setBackboneSize(0);
							}
						}
					} else if (foundNonDegeneratePartsAndSourcesContent == true) {
						foundNonDegeneratePartsAndSources = false;
						foundNonDegeneratePartsAndSourcesContent = false;
					}
				}

				if (matcherPartsList.find()) {
					foundPartsList = true;
				}
				if (foundPartsList == true) {
					if (matcherPartsListContent.find()) {
						foundPartsListContent = true;
						partsListEntryCounter++;
						String[] parts = line.split(",");
						// Order, ID Number,Name,Direction,Strategy
						if (parts.length == 5) {
							Integer idNumber = new Integer(
									stripQuotes(parts[1]));
							TuSubunitDirection direction = model
									.getAssemblXWebEnumTranslator()
									.getTuSubunitTypeDirection(parts[3]);
							TuSubunitAssemblyStrategy strategy = model
									.getAssemblXWebEnumTranslator()
									.getTuSubunitAssemblyStrategy(parts[4]);
							Level_0_ProtocolSubunit part = new Level_0_ProtocolSubunit(
									idNumber, parts[2], direction, strategy);
							partsListUnits.add(part);

							if (partsListEntryCounter == 1) {
								protocol.setBackboneName(parts[2]);
							}
						} else {
							// do some error handling!
						}
					} else if (foundPartsListContent == true) {
						foundPartsList = false;
						foundPartsListContent = false;
						protocol.setTargetParts(partsListUnits);
					}
				}

				if (matcherAnnealedOligos.find()) {
					foundAnnealedOligos = true;
				}
				if (foundAnnealedOligos == true) {
					if (matcherAnnealedOligosContent.find()) {
						foundAnnealedOligosContent = true;
						String[] parts = line.split(",");
						Level_0_Protocol_WetLabSubunit subunit = new Level_0_Protocol_WetLabSubunit(
								parts[2], parts[4]);
						annealedOligoEntries.add(subunit);
					} else if (foundAnnealedOligosContent == true) {
						foundAnnealedOligos = false;
						foundAnnealedOligosContent = false;
						protocol.setAnnealedOligos(annealedOligoEntries);
					}
				}

				if (matcherPcrReactions.find()) {
					foundPCRReactions = true;
				}
				if (foundPCRReactions == true) {
					if (matcherPcrReactionsContent.find()) {
						foundPCRReactionsContent = true;
						String[] parts = line.split(",");
						Integer length = new Integer(stripQuotes(parts[14]));
						Double meanOligoTm = new Double(stripQuotes(parts[12]));
						Level_0_Protocol_WetLabSubunit subunit = new Level_0_Protocol_WetLabSubunit(
								parts[1], parts[4], parts[6], meanOligoTm,
								length);
						pcrReactionEntries.add(subunit);
					} else if (foundPCRReactionsContent == true) {
						foundPCRReactions = false;
						foundPCRReactionsContent = false;
						protocol.setPcrReactions(pcrReactionEntries);
					}
				}
				if (matcherFinalAssembledVector.find()) {
					foundFinalAssembledVector = true;
				}
				if (foundFinalAssembledVector == true) {
					if (matcherFinalAssembledVectorContent.find()) {
						foundFinalAssembledVectorContent = true;
						String[] parts = line.split(",");
						Integer finalVectorLength = new Integer(
								stripQuotes(parts[0]));
						protocol.setLengthOfFinalVector(finalVectorLength);
						protocol.setFinalVectorSequence(parts[1]);
					} else if (foundFinalAssembledVectorContent == true) {
						foundFinalAssembledVector = false;
						foundFinalAssembledVectorContent = false;
					}
				}
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
		if (reader != null) {
			reader.close();
		}
		currentTuUnit.setLevel_0_Protocol(protocol);
	}

	public static void collectDataForLevel_1_Protocol(
			TranscriptionalUnit currentTuUnit) {
		parseFinalVectorSequence(currentTuUnit);
		findRestrictionEnzyme(currentTuUnit);
	}

	public static List<String> parseMasterPlasmidFile(String filePath) throws Exception {
		BufferedReader bufferedReader = null;
		List<String> plasmidList = new ArrayList<String>();
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			Pattern plasmidNamePattern = Pattern.compile("^([a-zA-Z]{4}[0-9]{5})(.*)");
			String line = "";
			
			while ((line = bufferedReader.readLine()) != null) {
				Matcher plasmidNameMatcher = plasmidNamePattern.matcher(line);
				if (plasmidNameMatcher.matches()) {
					plasmidList.add(plasmidNameMatcher.group(2));
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			String message = "Error during parsing of master plasmid file " + filePath + ". " + e.getMessage();
			throw new AssemblXException(message, J5ResultAnalyzer.class);
		}
		return plasmidList;
	}
	
	private static void parseFinalVectorSequence(
			TranscriptionalUnit currentTuUnit) {
		String finalVectorSequence = currentTuUnit.getLevel_0_Protocol()
				.getFinalVectorSequence();
		String moduleName = currentTuUnit.getParentModule().getName();
		Integer positionIndex = currentTuUnit.getPosition() - 1;
		String startHRName = moduleName + positionIndex;
		String endHRName = moduleName;
		if (currentTuUnit.getIsLastUnitInModule()) {
			endHRName += "R";
		} else {
			endHRName += positionIndex + 1;
		}
		String startHR = HomologyRegions.getHomologyRegion(startHRName);
		String endHR = HomologyRegions.getHomologyRegion(endHRName);
		Integer indexStartHR = finalVectorSequence.indexOf(startHR);
		Integer indexEndHR = finalVectorSequence.indexOf(endHR);

		while (indexStartHR == -1 || indexEndHR == -1 || indexEndHR < indexStartHR) {
			String firstBase = finalVectorSequence.substring(0,1);
			finalVectorSequence = finalVectorSequence.substring(1);
			finalVectorSequence += firstBase;
			indexStartHR = finalVectorSequence.indexOf(startHR);
			indexEndHR = finalVectorSequence.indexOf(endHR);
		}
		
		String fragment = finalVectorSequence.substring(indexStartHR, indexEndHR + endHR.length());
		
		currentTuUnit.getLevel_0_Protocol()
				.setFragmentLength(fragment.length());
		currentTuUnit.getLevel_0_Protocol().setFragment(fragment);
		// store information about homology regions
		AnnotationRecord annotationRecord = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		AnnotationPartItem annotationPartItem = new AnnotationPartItem();
		annotationPartItem.setPartSequence(startHR);
		annotationRecord.setAnnotationPartItem(annotationPartItem);
		annotationRecord.getQualifiers().add("/label=" + startHRName + "*");
		currentTuUnit.getAnnotationRecords().add(annotationRecord);
		annotationRecord = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		annotationPartItem = new AnnotationPartItem();
		annotationPartItem.setPartSequence(endHR);
		annotationRecord.setAnnotationPartItem(annotationPartItem);
		annotationRecord.getQualifiers().add("/label=" + endHRName + "*");
		currentTuUnit.getAnnotationRecords().add(annotationRecord);
	}

	private static void findRestrictionEnzyme(TranscriptionalUnit currentTuUnit) {
		String fragment = currentTuUnit.getLevel_0_Protocol().getFragment().toUpperCase();
		if ((currentTuUnit.getIsLastUnitInModule())
				&& ((fragment.length() >= 4000 && fragment.length() <= 5000) || (fragment
						.length() >= 5000 && fragment.length() <= 6500))) {
			if (!fragment.contains(RestrictionEnzymes.Sbfl)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Sbfl");
			} else if (!fragment.contains(RestrictionEnzymes.Swal)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Swal");
			} else if (!fragment.contains(RestrictionEnzymes.Pmel)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Pmel");
				currentTuUnit.getLevel_0_Protocol().setSimilarLengths(true);
			} else if (!fragment.contains(RestrictionEnzymes.Ascl)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Ascl");
				currentTuUnit.getLevel_0_Protocol().setSimilarLengths(true);
			} else if (!fragment.contains(RestrictionEnzymes.Fsel)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Fsel");
				currentTuUnit.getLevel_0_Protocol().setSimilarLengths(true);
			} else {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("none found");
				currentTuUnit.getLevel_0_Protocol().setNoFreeEnzyme(true);
			}
		} else {
			if (!fragment.contains(RestrictionEnzymes.Pmel)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Pmel");
			} else if (!fragment.contains(RestrictionEnzymes.Ascl)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Ascl");
			} else if (!fragment.contains(RestrictionEnzymes.Swal)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Swal");
			} else if (!fragment.contains(RestrictionEnzymes.Sbfl)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Sbfl");
			} else if (!fragment.contains(RestrictionEnzymes.Fsel)) {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("Fsel");
			} else {
				currentTuUnit.getLevel_0_Protocol().setEnzyme("none found");
				currentTuUnit.getLevel_0_Protocol().setNoFreeEnzyme(true);
			}
		}
	}

	private static String stripQuotes(String input) {
		if (input.startsWith("\"") && input.endsWith("\"")) {
			input = input.replace("\"", "");
		}
		if (input.startsWith("'") && input.endsWith("'")) {
			input = input.replace("'", "");
		}
		return input;
	}
	
}
