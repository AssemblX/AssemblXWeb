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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Level_0_Promoters;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.AssemblyMethod;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;

public class Level_1_ProtocolCreator {

	public Level_1_ProtocolCreator(Module module, AssemblXWebModel model) {
		module_ = module;
		model_ = model;
	}

	/*
	 * This method comprises also the generation of all level0 assemblies and
	 * writing level0 protocols again.
	 */
	public void createLevel_1_Protocol(ProtocolWriter protocolWriter) throws Exception {
		List<TranscriptionalUnit> tuUnits = module_.getTranscriptionalUnits();
		for (TranscriptionalUnit currentTuUnit : tuUnits) {
			if (currentTuUnit.isEvaluable()) {
				currentTuUnit.setIsMockAssembly(false);
				currentTuUnit.setAssemblyMethod(AssemblyMethod.SLIC_Gibson_CPEC);
				J5InputFileWriter j5InputFileWriter = new J5InputFileWriter();
				// remove annotation records from mock generation
				currentTuUnit.getAnnotationRecords().clear();
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
						// Revert directions - otherwise the sequence will not
						// be found when
						// making the annotations for the result file.
						if (currentTuSubunit.getDirection() == TuSubunitDirection.FORWARD) {
							direction = TuSubunitDirection.REVERSE;
						} else if (currentTuSubunit.getDirection() == TuSubunitDirection.REVERSE) {
							direction = TuSubunitDirection.FORWARD;
						}
					}
					// important for correct annotation - otherwise it will not
					// be found in the final construct sequence
					if (currentTuSubunit.getIsReverseComplement() == true ^ direction == TuSubunitDirection.REVERSE) {
						partSequence = SequenceCalculator.calculateReverseComplement(partSequence);
					}

					if (currentTuSubunit.getType() == TuSubunitType.BACKBONE) {
						currentTuUnit.getAnnotationRecords()
								.add(Level_0_Promoters.getModuleSpecificPromoter(currentTuUnit.getParentModule()));
					} else {
						AnnotationRecord annotationRecord = new AnnotationRecord(
								model_.getAssemblXWebEnumTranslator().getTuSubunitTypeName(currentTuSubunit.getType()),
								direction);
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
						// prepare data in tuSubunit for writing an own sequence
						// file from
						// the pasted sequence
						// we use the part name as part source since we have no
						// source file name - and if the user did not set a name
						// for it
						if (currentTuSubunit.getPartSource().equals("")) {
							currentTuSubunit.setPartSource(currentTuSubunit.getPartName());
						}

						// workaround for correct annotations
						if (currentTuSubunit.getIsReverseComplement() == true) {
							String sequence = currentTuSubunit.getPureRawSequence();
							sequence = sequence.replaceAll("\\s", "");
							sequence = SequenceCalculator.calculateReverseComplement(sequence);
							currentTuSubunit.setPureRawSequence(sequence);
							currentTuSubunit.setIsReverseComplement(false);
						}

						// currentTuSubunit.setSequenceFileName(currentTuSubunit.getPartSource()
						// + ".gb");
						currentTuSubunit.setSequenceFileName(currentTuSubunit.getPartName() + ".gb");
						currentTuSubunit.setSequenceFormat("Genbank");
						j5InputFileWriter.writeGenebankInputFile(model_, currentTuSubunit);
					}
				}
				try {
					List<AnnotationRecord> annotationsFromSequenceFiles = GenbankAnnotationParser
							.parseAnnotationsOfAllTUSequenceFiles(currentTuUnit, model_);
					currentTuUnit.getAnnotationRecords().addAll(annotationsFromSequenceFiles);
				} catch (Exception e) {
					String message = "Error during extracting annotations of sequence files. " + e.getMessage();
					throw new AssemblXException(message, this.getClass());
				}
				deleteMockData(currentTuUnit);
				j5InputFileWriter.writeJ5InputFiles(currentTuUnit, model_);
				J5Caller.callDesignAssemblyScript(model_, currentTuUnit);
				if (currentTuUnit.getJ5ErrorMessage().equals("") && currentTuUnit.getJ5FaultString().equals("")) {
					Integer tuCount = model_.getEvaluableTUCounter().getValueAndIncrement();
					currentTuUnit.setPlasmidName(OtherUtils.createMasterPlasmidEntryName(model_, tuCount));
					J5ResultAnalyzer.unpackResult(currentTuUnit, model_);
					J5ResultAnalyzer.findResultFileName(currentTuUnit, model_);
					J5ResultAnalyzer.parseResultFile(currentTuUnit, model_);
					protocolWriter.appendLevel_0_Section(currentTuUnit);
					J5ResultAnalyzer.collectDataForLevel_1_Protocol(currentTuUnit);
					exchangeMasterFiles(currentTuUnit);

				} else if (currentTuUnit.getActive() == true) {
					String errorMessage = "Error in TU Unit " + currentTuUnit.getName() + ": "
							+ currentTuUnit.getJ5ErrorMessage() + " " + currentTuUnit.getJ5FaultString();
					String globalErrorMessage = model_.getSubmitJobErrorMessage() + "; " + errorMessage;
					model_.setSubmitJobErrorMessage(globalErrorMessage);
				}
				model_.getNoOfProcessedTUs().increment();
			}
		}
		module_.setLevel_1_vectorName(createLevel_1_VectorSourceName(false));
		String vectorFilePath = model_.getServletContextRealPath() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("pathToLevel_1_vectorDirectory") + File.separator
				+ module_.getLevel_1_vectorName() + ".gb";
		try {
			List<AnnotationRecord> annotationsForLevel1Backbone = GenbankAnnotationParser
					.parseAnnotations(vectorFilePath, null, null);
			for (AnnotationRecord currentAnnotationRecord : annotationsForLevel1Backbone) {
				currentAnnotationRecord
						.setIdentifier(AssemblXWebProperties.getInstance().getProperty("idLevel1Backbone"));
			}
			tuUnits.get(0).getAnnotationRecords().addAll(annotationsForLevel1Backbone);
		} catch (Exception e) {
			// TODO: think about throwing an exception here - or rather creating
			// a kind off error log file
		}
		Integer vectorLength = SequenceFileParser.extractLengthFromVectorFile(vectorFilePath);
		module_.setLevel_1_vectorLength(vectorLength);
		createInsertSequence();
		String vectorSequence = SequenceFileParser.extractSequenceFromGenbankFile(vectorFilePath);
		String finalLevel_1_Sequence = vectorSequence + module_.getInsertSequence();
		module_.setFinalLevel_1_Construct(finalLevel_1_Sequence);
	}

	private void deleteMockData(TranscriptionalUnit currentTuUnit) {
		if (currentTuUnit.getResultDirectoryName().equals("")) {
			// we have not generated any mock, so we will not have to delete it
			return;
		}
		String mockZipPath = model_.getWorkingDirectory() + File.separator + currentTuUnit.getTuDirectoryName()
				+ File.separator + currentTuUnit.getResultDirectoryName() + ".zip";
		File mockZipFile = new File(mockZipPath);
		try {
			mockZipFile.delete();
		} catch (Exception e) {
			// TODO: think about what to do here - workflow needs not to stop if
			// file
			// could not be removed
		}
		String mockDirPath = model_.getWorkingDirectory() + File.separator + currentTuUnit.getTuDirectoryName()
				+ File.separator + currentTuUnit.getResultDirectoryName();
		try {
			J5FileUtils.removeDirRecursively(mockDirPath);
		} catch (Exception e) {
			// TODO: think about what to do here - workflow needs not to stop if
			// file
			// could not be removed
		}

	}

	private void exchangeMasterFiles(TranscriptionalUnit tuUnit) throws IOException {
		String resultDirectoryPath = model_.getWorkingDirectory() + File.separator + tuUnit.getTuDirectoryName()
				+ File.separator + tuUnit.getResultDirectoryName();
		// String masterPlasmidListPathCurrentResult = resultDirectoryPath +
		// File.separator
		// +
		// AssemblXWebProperties.getInstance().getProperty("masterPlasmidsListFileName");
		String masterOligoListPathCurrentResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterOligosListFileName");
		String masterDirectSynthesisListPathCurrentResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterDirectSynthesisListFileName");

		String masterFilesDirectoryPath = model_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterFilesDirectory");
		// String masterPlasmidListPathCommon = masterFilesDirectoryPath +
		// File.separator
		// +
		// AssemblXWebProperties.getInstance().getProperty("masterPlasmidsListFileName");
		String masterOligoListPathCommon = masterFilesDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterOligosListFileName");
		String masterDirectSynthesisPathCommon = masterFilesDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterDirectSynthesisListFileName");

		/*
		 * The following step (copying the master plasmid file) has been
		 * inactivated due to an unknown error which prevents the plasmid list
		 * from being completed as expected (and replaced by a workaround). It
		 * should be reactivated as soon as the error has been detected.
		 */
		// Files.copy(Paths.get(masterPlasmidListPathCurrentResult),
		// Paths.get(masterPlasmidListPathCommon),
		// StandardCopyOption.REPLACE_EXISTING);

		Files.copy(Paths.get(masterOligoListPathCurrentResult), Paths.get(masterOligoListPathCommon),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(Paths.get(masterDirectSynthesisListPathCurrentResult), Paths.get(masterDirectSynthesisPathCommon),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private String createLevel_1_VectorSourceName(Boolean withFileExtension) {
		String vectorFileName = "pL1" + module_.getName() + "-";
		if (module_.getCopyVariant() == Module.CopyVariant.HIGH) {
			vectorFileName += "hc";
		} else {
			vectorFileName += "lc";
		}
		if (withFileExtension) {
			vectorFileName += "_linear.gb";
		} else {
			vectorFileName += "_linear";
		}
		return vectorFileName;
	}

	private void createInsertSequence() {
		String insertSequence = "";
		List<TranscriptionalUnit> tuUnits = module_.getTranscriptionalUnits();
		for (TranscriptionalUnit currentTuUnit : tuUnits) {
			if (currentTuUnit.isEvaluable()) {
				String fragment = currentTuUnit.getLevel_0_Protocol().getFragment();
				if (currentTuUnit.getPosition() == 1) {
					if (currentTuUnit.getIsLastUnitInModule() == false) {
						fragment = fragment.substring(50);
						insertSequence += fragment;
					} else {
						Integer endIndex = fragment.length() - 52;
						fragment = fragment.substring(50, endIndex + 1);
						insertSequence += fragment;
					}
				} else {
					if (currentTuUnit.getIsLastUnitInModule() == false) {
						fragment = fragment.substring(36);
						insertSequence += fragment;
					} else {
						Integer endIndex = fragment.length() - 52;
						fragment = fragment.substring(36, endIndex + 1);
						insertSequence += fragment;
					}
				}
			}
		}
		module_.setInsertSequence(insertSequence);
	}

	private Module module_;
	private AssemblXWebModel model_;
}
