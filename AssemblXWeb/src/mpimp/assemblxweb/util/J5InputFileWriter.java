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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.GenbankData;
import mpimp.assemblxweb.db.GenbankData.Conformation;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit;

public class J5InputFileWriter {

	public void writeJ5InputFiles(TranscriptionalUnit currentTuUnit,
			AssemblXWebModel model) throws Exception {
		String inputFileDirectoryName = model.getWorkingDirectory()
				+ File.separator
				+ currentTuUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory");
		File inputFileDirectory = new File(inputFileDirectoryName);
		if (!inputFileDirectory.exists()) {
			inputFileDirectory.mkdirs();
		}
		currentTuUnit.setJ5ParametersFileName(AssemblXWebProperties
				.getInstance().getProperty("j5ParametersFileName"));
		writeJ5ParameterFile(model, currentTuUnit);
		currentTuUnit.setSequencesListFileName(AssemblXWebProperties
				.getInstance().getProperty("sequencesListFileName"));
		writeJ5SequencesListFile(model, currentTuUnit);
		currentTuUnit.setZippedSequencesFileName(AssemblXWebProperties
				.getInstance().getProperty("zippedSequencesFileName"));
		writeJ5ZippedSequencesArchive(model, currentTuUnit);
		currentTuUnit.setPartsListFileName(AssemblXWebProperties.getInstance()
				.getProperty("partsListFileName"));
		writeJ5PartsListFile(model, currentTuUnit);
		currentTuUnit.setTargetPartOrderListFileName(AssemblXWebProperties
				.getInstance().getProperty("targetPartsOrderListFileName"));
		writeJ5TargetPartsOrderListFile(model, currentTuUnit);
		currentTuUnit.setEugeneRulesListFileName(AssemblXWebProperties
				.getInstance().getProperty("eugeneRulesListFileName"));
		writeJ5EugeneRulesListFile(model, currentTuUnit);
	}

	public void writeJ5ParameterFile(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String j5ParameterFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getJ5ParametersFileName();
		List<String> content = new ArrayList<String>();
		content.add("Parameter Name,Value,Default Value,Description");
		content.add("MASTEROLIGONUMBEROFDIGITS,5,5,The default number of digits used to number an oligo; e.g. j5_00001_primer_description uses 5 digits");
		content.add("MASTERPLASMIDNUMBEROFDIGITS,5,5,The default number of digits used to number a plasmid; e.g. pj5_00001 uses 5 digits");
		content.add("GIBSONOVERLAPBPS,36,26,The minimum number of bps for SLIC/Gibson/CPEC overlaps (should be an even number); this is also the starting design length for the annealing portion of primers");
		content.add("GIBSONOVERLAPMINTM,60,60,The minimum desired Tm for SLIC/Gibson/CPEC overlaps");
		content.add("GIBSONOVERLAPMAXTM,70,70,The maximum desired Tm for SLIC/Gibson/CPEC overlaps");
		content.add("MAXIMUMOLIGOLENGTHBPS,120,110,The maximum oligo length to be ordered");
		content.add("MINIMUMFRAGMENTSIZEGIBSONBPS,50,250,The minimum fragment size for SLIC/Gibson assembly");
		content.add("GOLDENGATEOVERHANGBPS,4,4,The number of bps of the overhang resulting from the Golden-gate type IIs endonuclease digestion");
		content.add("GOLDENGATERECOGNITIONSEQ,GGTCTC,GGTCTC,The Golden-gate type IIs endonuclease recognition site sequence");
		content.add("GOLDENGATETERMINIEXTRASEQ,CACACCAGGTCTCA,CACACCAGGTCTCA,The extra 5' sequence required at each end of a Golden-gate assembly piece; e.g. NNNNNNNGGCTCTN for BsaI (Eco31I)");
		content.add("MAXIMUM_IDENTITIES_GOLDEN_GATE_OVERHANGS_COMPATIBLE,2,2,The maximum number of tolerable non-gapped aligned identities between compatible overhang sequences for Golden-gate assembly");
		content.add("OLIGOSYNTHESISCOSTPERBPUSD,0.1,0.1,The oligo synthesis cost per bp ($US)");
		content.add("OLIGOPAGEPURIFICATIONCOSTPERPIECEUSD,40,40,The PAGE-purification cost per oligo ($US)");
		content.add("OLIGOMAXLENGTHNOPAGEPURIFICATIONREQUIREDBPS,60,60,The maximum oligo length that does not require PAGE-purification");
		content.add("MINIMUMPCRPRODUCTBPS,100,100,The minimum PCR product size");
		content.add("DIRECTSYNTHESISCOSTPERBPUSD,0.39,0.39,The cost per bp to do direct synthesis ($US)");
		content.add("DIRECTSYNTHESISMINIUMUMCOSTPERPIECEUSD,159,159,The minimum cost of synthesis per piece ($US)");
		content.add("PRIMER_GC_CLAMP,2,2,Primer3 parameter: length of the desired GC clamp (Primer3 default is 0)");
		content.add("PRIMER_MIN_SIZE,18,18,Primer3 parameter: the minimum length of a primer (Primer3 default is 18)");
		content.add("PRIMER_MAX_SIZE,36,36,Primer3 parameter: the maximum length of a primer (Primer3 default is 27; maximum is 36)");
		content.add("PRIMER_MIN_TM,60,60,Primer3 parameter: the minimum primer Tm (Primer3 default is 57)");
		content.add("PRIMER_MAX_TM,70,70,Primer3 parameter: the maximum primer Tm (Primer3 default is 63)");
		content.add("PRIMER_MAX_DIFF_TM,5,5,Primer3 parameter: the maximum primer pair difference in Tm (Primer3 default is 100)");
		content.add("PRIMER_MAX_SELF_ANY_TH,47,47,Primer3 parameter: the maximum primer self complementarity (Primer3 default is 47)");
		content.add("PRIMER_MAX_SELF_END_TH,47,47,Primer3 parameter: the maximum primer self end complementarity (Primer3 default is 47)");
		content.add("PRIMER_PAIR_MAX_COMPL_ANY_TH,47,47,Primer3 parameter: the maximum primer pair complementarity (Primer3 default is 47)");
		content.add("PRIMER_PAIR_MAX_COMPL_END_TH,47,47,Primer3 parameter: the maximum primer pair end complementarity (Primer3 default is 47)");
		content.add("PRIMER_TM_SANTALUCIA,1,1,Primer3 parameter: use the Santalucia formula for calculating Tms (1 = TRUE; 0 = FALSE) (Primer3 default is 0 (FALSE))");
		content.add("PRIMER_SALT_CORRECTIONS,1,1,Primer3 parameter: use the salt correction formula for calculating Tms (1 = TRUE; 0 = FALSE) (Primer3 default is 0 (FALSE))");
		content.add("PRIMER_DNA_CONC,250,250,Primer3 parameter: DNA concentration to use when calculating Tms in micromolar (IDT uses 250; Primer3 default is 50)");
		content.add("MISPRIMING_3PRIME_BOUNDARY_BP_TO_WARN_IF_HIT,4,4,Only warn of mispriming if the BLAST hit between the primer and the template contains the 3' end of the primer (within this number of bp)");
		content.add("MISPRIMING_MIN_TM,45,45,The minimum approximate Tm to consider a significant mispriming event");
		content.add("MISPRIMING_SALT_CONC,0.05,0.05,The salt concentration used when estimating the mispriming Tm in Molar");
		content.add("MISPRIMING_OLIGO_CONC,2.5e-7,2.5e-7,The oligo concentration used when estimating the mispriming Tm in Molar");
		content.add("OUTPUT_SEQUENCE_FORMAT,Genbank,Genbank,\"The output sequence file format. Options are: \"\"Genbank\"\", \"\"FASTA\"\", \"\"jbei-seq\"\", or \"\"SBOLXML\"\"\"");
		content.add("ASSEMBLY_PRODUCT_TYPE,circular,circular,\"Determines whether the assembled DNA product will be circular or linear. Options are: \"\"circular\"\" or \"\"linear\"\"\"");
		content.add("SUPPRESS_PURE_PRIMERS,TRUE,TRUE,\"Suppress the output of pure primers. Options are: \"\"TRUE\"\" or \"\"FALSE\"\"\"");
		content.add("SUPPRESS_PRIMER_ANNOTATIONS,FALSE,FALSE,\"Suppress primer annotations. Options are: \"\"TRUE\"\" or \"\"FALSE\"\"\"");
		content.add("HOMOLOGY_MIN_LENGTH_BPS,26,26,The minimum length in bps for a homologous sequence repeat to be considered significant");
		content.add("HOMOLOGY_MAX_FRACTION_MISMATCHES,0.05,0.05,The maximum fraction of mismatches for a homologous sequence repeat to be considered significant");
		content.add("APPEND_UUID_TO_PLASMID_OLIGO_AND_SYNTHESIS_NAME,FALSE,FALSE,\"Append UUIDs to all new plasmid, oligo, and DNA synthesis names. Options are: \"\"TRUE\"\" or \"\"FALSE\"\"\"");
		Path path = Paths.get(j5ParameterFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 parameters file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5SequencesListFile(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String sequencesListFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getSequencesListFileName();
		List<String> content = new ArrayList<String>();
		content.add("Sequence File Name,Format");
		HashSet<String> uniqueSequenceFileNames = new HashSet<String>();
		for (TuSubunit tuSubunit : transcriptionalUnit.getTuSubunits()) {
			if (!uniqueSequenceFileNames.contains(tuSubunit
					.getSequenceFileName())) {
				String entryLine = tuSubunit.getSequenceFileName() + ","
						+ tuSubunit.getSequenceFormat();
				content.add(entryLine);
				uniqueSequenceFileNames.add(tuSubunit.getSequenceFileName());
			}
		}

		Path path = Paths.get(sequencesListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 sequences list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	/*
	 * Needed for generating a sequence input file if the user pastes a
	 * sequence in plain text format.
	 */
	public void writeGenebankInputFile(AssemblXWebModel model,
			TuSubunit tuSubunit) throws IOException {
		GenbankData genbankData = new GenbankData();
		String sequence = tuSubunit.getPureRawSequence();
		sequence = sequence.replaceAll("\\s", "");
		sequence = sequence.toUpperCase();
		genbankData.setSequence(sequence);
		//genbankData.setLocusName(tuSubunit.getPartSource());
		genbankData.setLocusName(tuSubunit.getPartName());
		genbankData.setConformation(Conformation.CIRCULAR);
		AnnotationRecord annotationRecord = new AnnotationRecord(model
				.getAssemblXWebEnumTranslator().getTuSubunitTypeName(
						tuSubunit.getType()), TuSubunitDirection.FORWARD);
		AnnotationPartItem annotationPartItem = new AnnotationPartItem();
		annotationPartItem.setPartSequence(sequence);
		annotationRecord.setAnnotationPartItem(annotationPartItem);
		//annotationRecord.getQualifiers().add("/label=" + tuSubunit.getPartSource());
		annotationRecord.getQualifiers().add("/label=" + tuSubunit.getPartName());
		ArrayList<AnnotationRecord> annotationRecords = new ArrayList<AnnotationRecord>();
		annotationRecords.add(annotationRecord);
		genbankData.setAnnotationRecords(annotationRecords);
		GenbankWriter genbankWriter = new GenbankWriter();
		// since we have no sequence file we generate
		// sequence file name will be needed later when writing the sequence
		// list
		String sequenceDirectoryPath = model.getWorkingDirectory()
				+ File.separator
				+ tuSubunit.getParentTuUnit().getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"sequenceFileDirectory");
		genbankWriter.writeGenbankFile(genbankData, sequenceDirectoryPath);
		tuSubunit.setSequenceFileContent(genbankWriter.getContentAsString());
	}

	public void writeJ5ZippedSequencesArchive(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String zippedSequencesFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getZippedSequencesFileName();
		String pathToSequenceFilesDirectory = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"sequenceFileDirectory");
		int BUFFER = 2048;
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(
					zippedSequencesFilePath);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			File sequenceFilesDirectory = new File(pathToSequenceFilesDirectory);
			String sequenceFiles[] = sequenceFilesDirectory.list();

			for (int i = 0; i < sequenceFiles.length; i++) {
				String currentFileWithPath = pathToSequenceFilesDirectory
						+ File.separator + sequenceFiles[i];
				FileInputStream in = new FileInputStream(currentFileWithPath);
				origin = new BufferedInputStream(in, BUFFER);
				ZipEntry zipEntry = new ZipEntry(sequenceFiles[i]);
				out.putNextEntry(zipEntry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			String message = "Error during writing of j5 zipped sequences archive: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}

	}

	public void writeJ5PartsListFile(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String partsListFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getPartsListFileName();
		List<String> content = new ArrayList<String>();
		content.add("\"Part Name\",\"Part Source (Sequence Display ID)\",\"Reverse Compliment?\","
				+ "\"Start (bp)\",\"End (bp)\",\"Five Prime Internal Preferred Overhangs?\","
				+ "\"Three Prime Internal Preferred Overhangs?\"");
		List<TuSubunit> tuSubunits = null;
		if (transcriptionalUnit.getOrientation() == TUOrientation.ANTI_SENSE){
			tuSubunits = revertSubunitList(transcriptionalUnit.getTuSubunits());
		} else {
			tuSubunits = transcriptionalUnit.getTuSubunits();
		}
		for (TuSubunit tuSubunit : tuSubunits) {
			String reverseComplement = "";
			if (tuSubunit.getIsReverseComplement() == true) {
				reverseComplement = "TRUE";
			} else {
				reverseComplement = "FALSE";
			}
			String start;
			if (tuSubunit.getStart() == -1) {
				start = "";
			} else {
				start = tuSubunit.getStart().toString();
			}
			String end;
			if (tuSubunit.getEnd() == -1) {
				end = "";
			} else {
				end = tuSubunit.getEnd().toString();
			}
			String entryLine = tuSubunit.getPartName() + ","
					+ tuSubunit.getPartSource() + "," + reverseComplement + ","
					+ start + "," + end + ","
					+ tuSubunit.getFivePrimeInternalPreferredOverhangs() + ","
					+ tuSubunit.getThreePrimeInternalPreferredOverhangs();
			content.add(entryLine);
		}
		Path path = Paths.get(partsListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 parts list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5TargetPartsOrderListFile(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String targetPartsOrderListFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getTargetPartOrderListFileName();
		List<String> content = new ArrayList<String>();
		content.add("(>Bin) or Part Name,Direction,Forced Assembly Strategy?,Forced Relative Overhang Position?"
				+ ",Direct Synthesis Firewall?,Extra 5' CPEC overlap bps,Extra 3' CPEC overlap bps");

		List<TuSubunit> tuSubunits = null;
		if (transcriptionalUnit.getOrientation() == TUOrientation.ANTI_SENSE){
			tuSubunits = revertSubunitList(transcriptionalUnit.getTuSubunits());
		} else {
			tuSubunits = transcriptionalUnit.getTuSubunits();
		}
		for (TuSubunit tuSubunit : tuSubunits) {
			String entryLine = tuSubunit.getPartName()
					+ ","
					+ model.getTuSubunitDirections().get(
							tuSubunit.getDirection())
					+ ","
					+ model.getAssemblXWebEnumTranslator()
							.getTuSubunitAssemblyStrategyName(
									tuSubunit.getForcedAssemblyStrategy())
					+ "," + tuSubunit.getForcedRelativeOverhangPosition() + ","
					+ tuSubunit.getDirectSynthesisFirewall() + ",,";
			/*
			 * + tuSubunit.getExtra5PrimeCPECOverlapBps() + "," +
			 * tuSubunit.getExtra3PrimeCPECOverlapBps();
			 */
			content.add(entryLine);
		}
		Path path = Paths.get(targetPartsOrderListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 target parts order list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5EugeneRulesListFile(AssemblXWebModel model,
			TranscriptionalUnit transcriptionalUnit) throws Exception {
		String eugeneRulesListFilePath = model.getWorkingDirectory()
				+ File.separator
				+ transcriptionalUnit.getTuDirectoryName()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"parameterFileDirectory") + File.separator
				+ transcriptionalUnit.getEugeneRulesListFileName();
		List<String> content = new ArrayList<String>();
		Path path = Paths.get(eugeneRulesListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 eugene rules list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5MasterPlasmidsListFile(AssemblXWebModel model) throws Exception {
		String masterPlasmidsListFilePath = model
				.getPathToMasterFilesDirectory()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"masterPlasmidsListFileName");
		List<String> content = new ArrayList<String>();
		content.add("Plasmid Name,Alias,Contents,Length,Sequence");
		String initialPlasmidName = OtherUtils.createMasterPlasmidEntryName(model, 0);
		content.add(initialPlasmidName + ",,,,");
		Path path = Paths.get(masterPlasmidsListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 plasmids list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5MasterOligosListFile(AssemblXWebModel model) throws Exception {
		String masterOligosListFilePath = model.getPathToMasterFilesDirectory()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"masterOligosListFileName");
		List<String> content = new ArrayList<String>();
		content.add("Oligo Name,Length,Tm,Tm (3' only),Sequence");
		Path path = Paths.get(masterOligosListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 master oligos list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	public void writeJ5MasterDirectSynthesisListFile(AssemblXWebModel model) throws Exception {
		String masterDirectSynthesisListFilePath = model
				.getPathToMasterFilesDirectory()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"masterDirectSynthesisListFileName");
		List<String> content = new ArrayList<String>();
		content.add("Direct Synthesis Name,Alias,Contents,Length,Sequence");
		Path path = Paths.get(masterDirectSynthesisListFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 master direct synthesis list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}
	
	private List<TuSubunit> revertSubunitList(List<TuSubunit> tuSubunits) {
		List<TuSubunit> revertedTuSubunits = new ArrayList<TuSubunit>();
		//add the backbone at first position - it has to remain there
		revertedTuSubunits.add(tuSubunits.get(0));
		//add the other elements in reversed order
		for (int i = tuSubunits.size() - 1; i > 0; i--) {
			TuSubunit copiedTu = tuSubunits.get(i).copy();
			if (copiedTu.getDirection() == TuSubunitDirection.FORWARD) {
				copiedTu.setDirection(TuSubunitDirection.REVERSE);
			} else {
				copiedTu.setDirection(TuSubunitDirection.FORWARD);
			}
			revertedTuSubunits.add(copiedTu);
		}
		return revertedTuSubunits;
	}
}
