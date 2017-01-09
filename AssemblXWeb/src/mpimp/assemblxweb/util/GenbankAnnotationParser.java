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
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;

public class GenbankAnnotationParser {

	public static List<AnnotationRecord> parseAnnotationsOfAllTUSequenceFiles(TranscriptionalUnit tuUnit,
			AssemblXWebModel model) throws Exception {
		List<AnnotationRecord> annotationRecords = new ArrayList<AnnotationRecord>();
		String pathToSequenceFilesDirectory = model.getWorkingDirectory() + File.separator + tuUnit.getTuDirectoryName()
				+ File.separator + AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory");

		for (TuSubunit currentTuSubunit : tuUnit.getTuSubunits()) {
			String currentFileWithPath = pathToSequenceFilesDirectory + File.separator
					+ currentTuSubunit.getSequenceFileName();
			annotationRecords.addAll(parseAnnotations(currentFileWithPath, currentTuSubunit));
		}

		return annotationRecords;
	}

	public static List<AnnotationRecord> parseAnnotations(String filePath, TuSubunit tuSubunit) throws Exception {
		Integer startOfPart = 0;
		Integer endOfPart = 0;
		if (tuSubunit.getType() != TuSubunitType.BACKBONE) {
			if (tuSubunit.getSequenceInputOption() == SequenceInputOption.LOAD_FROM_FILE) {
				if (tuSubunit.getSequenceOption() == TuSubunitSequenceOption.SPECIFIED_SEQUENCE) {
					startOfPart = tuSubunit.getStart();
					endOfPart = tuSubunit.getEnd();
				} else {
					startOfPart = 1;
					endOfPart = tuSubunit.getPureRawSequence().length();
				}
			}
		}
		return parseAnnotations(filePath, startOfPart, endOfPart);
	}

	public static List<AnnotationRecord> parseAnnotations(String filePath, Integer startOfPart, Integer endOfPart)
			throws Exception {
		String rawSequence = SequenceFileParser.extractSequenceFromGenbankFile(filePath);
		rawSequence = rawSequence.toUpperCase();

		BufferedReader bufferedReader = null;

		Pattern annotationNameLinePattern = Pattern.compile("^( {5})([a-zA-Z_]{1,15})( +)([a-z0-9\\(\\)\\.,\\^]+)");
		Pattern qualifierPattern = Pattern.compile("^( {21})(/.+)");

		List<AnnotationRecord> annotationRecords = new ArrayList<AnnotationRecord>();
		String line = "";

		AnnotationRecord annotationRecord = null;
		AnnotationPartItem annotationPartItem = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			while ((line = bufferedReader.readLine()) != null) {
				Matcher annotationNameLineMatcher = annotationNameLinePattern.matcher(line);
				if (annotationNameLineMatcher.matches()) {
					annotationRecord = new AnnotationRecord(annotationNameLineMatcher.group(2),
							TuSubunitDirection.FORWARD);
					annotationPartItem = new AnnotationPartItem();
					annotationRecord.setAnnotationPartItem(annotationPartItem);
					analyseRegion(rawSequence, annotationNameLineMatcher.group(4), annotationPartItem);
					if ((startOfPart == null && endOfPart == null)
							|| checkPresenceOfAnnotationOnPart(annotationRecord, startOfPart, endOfPart)) {
						annotationRecords.add(annotationRecord);
					}
				}
				Matcher qualifierMatcher = qualifierPattern.matcher(line);
				if (qualifierMatcher.find()) {
					if (annotationRecord != null) {
						annotationRecord.getQualifiers().add(qualifierMatcher.group(2));
					}
				}
			}
		} catch (Exception e) {
			String message = "Error parsing annotations from " + filePath + ". " + e.getMessage();
			throw new AssemblXException(message, GenbankAnnotationParser.class);
		}
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return annotationRecords;
	}

	private static boolean checkPresenceOfAnnotationOnPart(AnnotationRecord annotationRecord, Integer start,
			Integer end) {
		Integer startOfAnnotation = annotationRecord.getAnnotationPartItem().getStartPosition();
		Integer endOfAnnotation = annotationRecord.getAnnotationPartItem().getEndPosition();
		if (startOfAnnotation >= start && endOfAnnotation <= end) {
			return true;
		} else {
			return false;
		}
	}

	private static String extractPartSequence(String rawSequence, Integer begin, Integer end) {
		String partSequence = "";
		if (end > rawSequence.length()) {
			partSequence = rawSequence.substring(begin - 1);
		}
		partSequence = rawSequence.substring(begin - 1, end);
		partSequence = partSequence.toUpperCase();
		return partSequence;
	}

	private static void analyseRegion(String rawSequence, String region, AnnotationPartItem annotationPartItem) {

		Pattern specialRegionPattern = Pattern.compile("(complement|join)\\(([a-z0-9.,^\\(\\)]+)\\)");
		Pattern simpleRegionPattern = Pattern.compile("([0-9]+)\\.\\.([0-9]+)");
		Pattern siteRegionPattern = Pattern.compile("([0-9]+)\\^([0-9]+)");
		Pattern joinRegionPattern = Pattern.compile(
				"((complement|join)?\\(?[0-9]+(\\.{2}|\\^)[0-9]+\\)?)(,(complement|join)?\\(?([0-9]+(\\.{2}|\\^)[0-9]+\\)?))+");

		Matcher specialRegionMatcher = specialRegionPattern.matcher(region);
		Matcher simpleRegionMatcher = simpleRegionPattern.matcher(region);
		Matcher siteRegionMatcher = siteRegionPattern.matcher(region);
		Matcher joinRegionMatcher = joinRegionPattern.matcher(region);

		if (annotationPartItem.getIsJoinItem() && joinRegionMatcher.matches()) {
			annotationPartItem.setIsJoinItem(true);// TODO: does this still make
													// sense?
			String[] joinParts = joinRegionMatcher.group(0).split(",");
			Integer joinItemOverallStartPosition = -1;
			Integer joinItemOverallEndPosition = -1;
			for (int i = 0; i < joinParts.length; i++) {
				AnnotationPartItem childItem = new AnnotationPartItem();
				childItem.setPositionOfItem(i);
				childItem.setParentItem(annotationPartItem);
				annotationPartItem.getChildItems().add(childItem);
				analyseRegion(rawSequence, joinParts[i], childItem);
				if (joinItemOverallStartPosition == -1 || ((joinItemOverallStartPosition > childItem.getStartPosition())
						&& childItem.getStartPosition() != -1)) {
					joinItemOverallStartPosition = childItem.getStartPosition();
				}
				if (joinItemOverallEndPosition == -1 || ((joinItemOverallEndPosition < childItem.getEndPosition())
						&& childItem.getEndPosition() != -1)) {
					joinItemOverallEndPosition = childItem.getEndPosition();
				}
			}
			String joinItemOverallSequence = extractPartSequence(rawSequence, joinItemOverallStartPosition,
					joinItemOverallEndPosition);
			annotationPartItem.setPartSequence(joinItemOverallSequence);
			for (AnnotationPartItem currentChild : annotationPartItem.getChildItems()) {
				if (currentChild.getIsSiteItem()) {
					Integer relativeSiteStart = joinItemOverallSequence.indexOf(currentChild.getPartSequence()) + 1;// conversion
																													// to
																													// 1..n
					currentChild.setRelativeSiteStart(relativeSiteStart);
				}
			}
		}

		if (specialRegionMatcher.matches()) { // join(....) or complement(....)
			if (specialRegionMatcher.group(1).equalsIgnoreCase("join")) {
				annotationPartItem.setIsJoinItem(true);
			} else if (specialRegionMatcher.group(1).equalsIgnoreCase("complement")) {
				annotationPartItem.setIsReverseComplement(true);
			}
			analyseRegion(rawSequence, specialRegionMatcher.group(2), annotationPartItem);
		} else if (simpleRegionMatcher.matches()) { // 12345..12345
			Integer startPosition = new Integer(simpleRegionMatcher.group(1));
			Integer endPosition = new Integer(simpleRegionMatcher.group(2));
			String partSequence = extractPartSequence(rawSequence, startPosition, endPosition);

			annotationPartItem.setStartPosition(startPosition);
			annotationPartItem.setEndPosition(endPosition);
			annotationPartItem.setPartSequence(partSequence);
		} else if (siteRegionMatcher.matches()) { // 345^346
			Integer startPosition = new Integer(siteRegionMatcher.group(1));
			Integer endPosition = new Integer(siteRegionMatcher.group(2));
			String partSequence = extractPartSequence(rawSequence, startPosition, endPosition);

			annotationPartItem.setStartPosition(startPosition);
			annotationPartItem.setEndPosition(endPosition);
			annotationPartItem.setPartSequence(partSequence);
			annotationPartItem.setIsSiteItem(true);
		}

	}

}
