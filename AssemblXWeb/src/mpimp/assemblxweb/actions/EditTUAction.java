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
package mpimp.assemblxweb.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gxp.com.google.common.io.Files;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Level_0_Promoters;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.AssemblyMethod;
import mpimp.assemblxweb.db.TranscriptionalUnit.EditingStage;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;
import mpimp.assemblxweb.db.util.AssemblXWebDBUtil;
import mpimp.assemblxweb.util.AssemblXException;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.GenbankAnnotationParser;
import mpimp.assemblxweb.util.J5Caller;
import mpimp.assemblxweb.util.J5FileUtils;
import mpimp.assemblxweb.util.J5InputFileWriter;
import mpimp.assemblxweb.util.J5ResultAnalyzer;
import mpimp.assemblxweb.util.SequenceCalculator;
import mpimp.assemblxweb.util.SequenceFileParser;
import mpimp.assemblxweb.util.VectorEditorTool;

public class EditTUAction extends AbstractAssemblXAction implements ScopedModelDriven<AssemblXWebModel> {

	private static final long serialVersionUID = -3936987031873040578L;

	public String execute() {
		if (buttonName_.equals("goToLogin")) {
			return "logout";
		} else if (buttonName_.equals("addSubunit")) {
			addTuSubunit();
			return INPUT;
		} else if (buttonName_.equals("generateMock") && assemblXWebModel_.getOperator().getIsTestOperator() == false) {
			try {
				processVectorPreview();
			} catch (Exception e) {
				addActionError(e.getMessage());
			}
			return INPUT;
		} else if (buttonName_.equals("backToOverview")) {
			setEditStage();
			return SUCCESS;
		} else if (buttonName_.startsWith("delete_")) {
			String identifier = buttonName_.replace("delete_", "");
			try {
				deleteTuSubunit(identifier);
			} catch (Exception e) {
				addActionError(e.getMessage());
			}
			return INPUT;
		} else if (buttonName_.startsWith("loadSequenceFile_")) {
			String identifier = buttonName_.replace("loadSequenceFile_", "");
			try {
				saveSequenceFile(identifier);
			} catch (Exception e) {
				addActionError(e.getMessage());
			}
			return INPUT;
		} else if (buttonName_.equals("showHelpText")) {
			assemblXWebModel_.setShowHelpTextEditModule(true);
			return INPUT;
		} else if (buttonName_.equals("hideHelpText")) {
			assemblXWebModel_.setShowHelpTextEditModule(false);
			return INPUT;
		} else {
			return INPUT;
		}
	}

	public void setButtonName(String buttonName) {
		buttonName_ = buttonName;
	}

	public void setSequenceFile(String sequenceFile) {
		sequenceFile_ = sequenceFile;
	}

	public void setSequenceFileFileName(String sequenceFileFileName) {
		sequenceFileFileName_ = sequenceFileFileName;
	}

	private void setEditStage() {
		if (assemblXWebModel_.getCurrentTuUnit().getEditingFinished() == true) {
			assemblXWebModel_.getCurrentTuUnit().setEditingStage(EditingStage.READY);
		}
	}

	public Boolean getMockPresent() {
		return mockPresent_;
	}

	private void addTuSubunit() {
		TranscriptionalUnit currentTuUnit = assemblXWebModel_.getCurrentTuUnit();
		List<TuSubunit> tuSubunits = assemblXWebModel_.getCurrentTuUnit().getTuSubunits();
		TuSubunit newTuSubunit = new TuSubunit(currentTuUnit, insertTuPosition_);
		tuSubunits.add(insertTuPosition_ - 1, newTuSubunit);
		Integer position = 1;
		for (TuSubunit currentSubunit : tuSubunits) {
			currentSubunit.setPosition(position);
			position++;
		}
		currentTuUnit.updateTuSubunitPositionList();
		insertTuPosition_ = tuSubunits.size() + 1;
		currentTuUnit.setEditingStage(EditingStage.IN_PROGRESS);
		currentTuUnit.setEditingFinished(false);
	}

	private void deleteTuSubunit(String identifier) throws IOException {
		List<TuSubunit> tuSubunits = assemblXWebModel_.getCurrentTuUnit().getTuSubunits();
		int indexToDelete = 0;
		for (TuSubunit currentSubunit : tuSubunits) {
			if (currentSubunit.getIdentifier().equals(identifier)) {
				indexToDelete = tuSubunits.indexOf(currentSubunit);
				J5FileUtils.deleteTuSubunitRelatedFiles(currentSubunit, assemblXWebModel_);
				currentSubunit.setSequenceFileLoaded(false);
				break;
			}
		}
		tuSubunits.remove(indexToDelete);
		Integer position = 1;
		for (TuSubunit currentSubunit : tuSubunits) {
			currentSubunit.setPosition(position);
			position++;
		}
		assemblXWebModel_.getCurrentTuUnit().updateTuSubunitPositionList();
		insertTuPosition_ = tuSubunits.size() + 1;
		if (assemblXWebModel_.getCurrentTuUnit().getTuSubunits().size() == 1) {
			assemblXWebModel_.getCurrentTuUnit().setEditingStage(EditingStage.NEW);
			assemblXWebModel_.getCurrentTuUnit().setEditingFinished(false);
		}
	}

	private void saveSequenceFile(String subunitIdentifier) throws Exception {
		List<TuSubunit> tuSubunits = assemblXWebModel_.getCurrentTuUnit().getTuSubunits();
		TuSubunit tuSubunit = null;
		for (TuSubunit currentSubunit : tuSubunits) {
			if (currentSubunit.getIdentifier().equals(subunitIdentifier)) {
				tuSubunit = currentSubunit;
				break;
			}
		}
		// check if an uploaded file replaces another one and delete it if
		// necessary
		try {
			J5FileUtils.deleteTuSubunitRelatedFiles(tuSubunit, assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error during deleting of unnecessaray sequence files. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}

		sequenceFileFileName_ = J5FileUtils.removeForbiddenCharctersFromFileName(sequenceFileFileName_);
		tuSubunit.setSequenceFileName(sequenceFileFileName_);

		String tuSequencesDirectoryName = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ assemblXWebModel_.getCurrentTuUnit().getTuDirectoryName() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory");

		File tuDirectory = new File(tuSequencesDirectoryName);
		if (!tuDirectory.exists()) {
			if (!tuDirectory.mkdirs()) {
				String message = "Error during creation of subdirectory " + tuSequencesDirectoryName + ".";
				throw new AssemblXException(message, this.getClass());
			}
		}

		String sequenceFilePathName = tuSequencesDirectoryName + File.separator + sequenceFileFileName_;
		File sequenceFile = new File(sequenceFilePathName);
		File sequenceFileTemp = new File(sequenceFile_);
		try {
			Files.move(sequenceFileTemp, sequenceFile);
		} catch (Exception e) {
			String message = "Error during moving uploaded sequence file to working directory. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		tuSubunit.setSequenceFileLoaded(true);
		try {
			SequenceFileParser.parseSequenceFile(assemblXWebModel_, tuSubunit, false);
		} catch (Exception e) {
			String message = "Error during parsing of sequence file " + tuSubunit.getSequenceFileName() + ". "
					+ e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void processVectorPreview() throws Exception {
		mockPresent_ = false;
		J5InputFileWriter j5InputFileWriter = new J5InputFileWriter();
		TranscriptionalUnit currentTuUnit = assemblXWebModel_.getCurrentTuUnit();
		List<TuSubunit> tuSubunits = currentTuUnit.getTuSubunits();
		for (TuSubunit currentTuSubunit : tuSubunits) {
			String partSequence = "";
			if (currentTuSubunit.getSequenceInputOption() == SequenceInputOption.LOAD_FROM_FILE) {
				String rawSequence = SequenceFileParser
						.extractSequenceFromGenbankFileContent(currentTuSubunit.getSequenceFileContent());
				Integer start = currentTuSubunit.getStart() - 1;
				Integer end = currentTuSubunit.getEnd();

				if (start > -1 && end > -1) {
					partSequence = rawSequence.substring(start, end);
				} else {
					partSequence = rawSequence;
				}
			} else {
				partSequence = currentTuSubunit.getPureRawSequence();
				partSequence = partSequence.replaceAll("\\s", "");
			}
			TuSubunitDirection direction = null;
			if (currentTuUnit.getOrientation() == TUOrientation.SENSE) {
				direction = currentTuSubunit.getDirection();
			} else {
				// Revert directions - otherwise the sequence will not be found
				// when
				// making the annotations for the result file.
				if (currentTuSubunit.getDirection() == TuSubunitDirection.FORWARD) {
					direction = TuSubunitDirection.REVERSE;
				} else if (currentTuSubunit.getDirection() == TuSubunitDirection.REVERSE) {
					direction = TuSubunitDirection.FORWARD;
				}
			}
			// important for correct annotation - otherwise it will not be found
			// in the final construct sequence
			if (currentTuSubunit.getIsReverseComplement() == true ^ direction == TuSubunitDirection.REVERSE) {
				partSequence = SequenceCalculator.calculateReverseComplement(partSequence);
			}

			if (currentTuSubunit.getType() == TuSubunitType.BACKBONE) {
				currentTuUnit.getAnnotationRecords()
						.add(Level_0_Promoters.getModuleSpecificPromoter(currentTuUnit.getParentModule()));
			} else {
				AnnotationRecord annotationRecord = new AnnotationRecord(assemblXWebModel_
						.getAssemblXWebEnumTranslator().getTuSubunitTypeName(currentTuSubunit.getType()), direction);
				AnnotationPartItem annotationPartItem = new AnnotationPartItem();
				partSequence = partSequence.toUpperCase();
				annotationPartItem.setPartSequence(partSequence);
				if (currentTuUnit.getOrientation() == TUOrientation.ANTI_SENSE) {
					annotationPartItem.setIsReverseComplement(true);
				}
				annotationRecord.setAnnotationPartItem(annotationPartItem);
				annotationRecord.getQualifiers().add("/label=" + currentTuSubunit.getPartName());
				currentTuUnit.getAnnotationRecords().add(annotationRecord);
			}

			if (currentTuSubunit.getSequenceInputOption() == SequenceInputOption.PASTE_AS_TEXT) {
				// prepare data in tuSubunit for writing an own sequence file
				// from
				// the pasted sequence
				// we use the part name as part source since we have no source
				// source file name - and if the user did not set a name for it
				if (currentTuSubunit.getPartSource().equals("")) {
					currentTuSubunit.setPartSource(currentTuSubunit.getPartName());
				}
				// currentTuSubunit.setSequenceFileName(currentTuSubunit.getPartSource()
				// + ".gb");
				currentTuSubunit.setSequenceFileName(currentTuSubunit.getPartName() + ".gb");
				currentTuSubunit.setSequenceFormat("Genbank");

				// workaround for correct annotations
				if (currentTuSubunit.getIsReverseComplement() == true) {
					String sequence = currentTuSubunit.getPureRawSequence();
					sequence = sequence.replaceAll("\\s", "");
					sequence = SequenceCalculator.calculateReverseComplement(sequence);
					currentTuSubunit.setPureRawSequence(sequence);
					currentTuSubunit.setIsReverseComplement(false);
				}

				try {
					j5InputFileWriter.writeGenebankInputFile(assemblXWebModel_, currentTuSubunit);
				} catch (Exception e) {
					String message = "Error during writing of genbank input file for " + currentTuSubunit.getPartName();
					throw new AssemblXException(message, this.getClass());
				}
			}
		}
		try {
			List<AnnotationRecord> annotationsFromSequenceFiles = GenbankAnnotationParser
					.parseAnnotationsOfAllTUSequenceFiles(currentTuUnit, assemblXWebModel_);
			currentTuUnit.getAnnotationRecords().addAll(annotationsFromSequenceFiles);
		} catch (Exception e) {
			String message = "Error during extracting annotations of sequence files. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			j5InputFileWriter.writeJ5InputFiles(currentTuUnit, assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error during writing of j5 input files. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		currentTuUnit.setIsMockAssembly(true);
		currentTuUnit.setAssemblyMethod(AssemblyMethod.Mock_Assembly);
		try {
			J5Caller.callDesignAssemblyScript(assemblXWebModel_, currentTuUnit);
		} catch (Exception e) {
			String message = "Error while calling j5. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			J5ResultAnalyzer.unpackResult(currentTuUnit, assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error while unpacking j5 results. ";
			throw new AssemblXException(message, this.getClass());
		}
		try {
			J5ResultAnalyzer.findResultFileName(currentTuUnit, assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error while extracting the result file name. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}

		if (currentTuUnit.getJ5ErrorMessage().equals("") && currentTuUnit.getJ5FaultString().equals("")) {
			prepareVectorEditor();
			mockPresent_ = true;
		}
	}

	public Integer getInsertTuPosition() {
		return insertTuPosition_;
	}

	public void setInsertTuPosition(Integer insertTuPosition) {
		insertTuPosition_ = insertTuPosition;
	}

	private void prepareVectorEditor() throws Exception {
		TranscriptionalUnit currentTuUnit = assemblXWebModel_.getCurrentTuUnit();
		String mockPath = assemblXWebModel_.getWorkingDirectory() + File.separator + currentTuUnit.getIdentifier()
				+ File.separator + currentTuUnit.getResultDirectoryName() + File.separator
				+ currentTuUnit.getResultFileName();
		if (mockPath.endsWith(".csv")) {
			mockPath = mockPath.replace(".csv", ".gb");
		}
		try {
			VectorEditorTool.createVectorEditorFile(mockPath, assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error while preparing vector editor. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void checkSequence(String sequence, String tuSubunitIdentifier) {
		Pattern checkSequencePattern = Pattern.compile("[^AGCTagct]");
		Matcher checkSequenceMatcher = checkSequencePattern.matcher(sequence);
		String forbiddenCharacters = "";
		Boolean foundForbiddenCharacters = false;
		while (checkSequenceMatcher.find()) {
			foundForbiddenCharacters = true;
			Integer position = checkSequenceMatcher.start() + 1;
			forbiddenCharacters += "'";
			forbiddenCharacters += checkSequenceMatcher.group(0);
			forbiddenCharacters += "' at position " + position + ", ";
		}
		if (foundForbiddenCharacters == true) {
			String errorMessage = "Sequence contains foreign characters: " + forbiddenCharacters;
			addFieldError(tuSubunitIdentifier, errorMessage);
		}
	}

	private Boolean checkUnambigousnessOfPartName(String partName) {
		List<TuSubunit> tuSubunits = assemblXWebModel_.getCurrentTuUnit().getTuSubunits();
		int count = 0;
		for (TuSubunit currentTuSubunit : tuSubunits) {
			if (currentTuSubunit.getPartName().equals(partName)) {
				count++;
				if (count > 1) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void validate() {
		List<TuSubunit> tuSubunits = assemblXWebModel_.getCurrentTuUnit().getTuSubunits();
		if (buttonName_.startsWith("loadSequenceFile_")) {
			if (sequenceFile_ == null || sequenceFile_.equals("")) {
				String identifier = buttonName_.replace("loadSequenceFile_", "");
				addFieldError(identifier, "no sequence file selected");
			}
		} else if (buttonName_.equals("generateMock") || buttonName_.equals("backToOverview")) {
			for (TuSubunit currentTuSubunit : tuSubunits) {
				if (currentTuSubunit.getPartName().equals("")) {
					addFieldError(currentTuSubunit.getIdentifier(), "field 'part name' cannot be empty");
				}
				if (currentTuSubunit.getType() != TuSubunitType.BACKBONE
						&& currentTuSubunit.getSequenceInputOption() == SequenceInputOption.PASTE_AS_TEXT
						&& currentTuSubunit.getPureRawSequence().equals("")) {
					addFieldError(currentTuSubunit.getIdentifier(), "sequence field cannot be empty");
				}
				checkSequence(currentTuSubunit.getPureRawSequence(), currentTuSubunit.getIdentifier());
				if (!checkUnambigousnessOfPartName(currentTuSubunit.getPartName())) {
					String message = "part name '" + currentTuSubunit.getPartName() + "' appears more than once";
					addFieldError(currentTuSubunit.getIdentifier(), message);
				}
				if (currentTuSubunit.getSequenceOption() == TuSubunitSequenceOption.SPECIFIED_SEQUENCE
						&& (currentTuSubunit.getStart() == -1 || currentTuSubunit.getEnd() == -1)) {
					addFieldError(currentTuSubunit.getIdentifier(), "please specify start and end of sequence");
				}
			}
		}
	}

	private Integer insertTuPosition_ = 2;
	private String buttonName_ = "";
	private String sequenceFile_ = "";
	private String sequenceFileFileName_ = "";
	private Boolean mockPresent_ = false;

}
