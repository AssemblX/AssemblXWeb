package mpimp.assemblxweb.teststuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.J5InputFileWriter;

public class TestJ5InputFileWriter {

	public static void main(String[] args) {
		AssemblXWebModel model = new AssemblXWebModel();
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\TestWorkingDir");
		model.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir");
		Module module = new Module();
		module.setName("A");
		TranscriptionalUnit transcriptionalUnit = null;
		try {
			transcriptionalUnit = new TranscriptionalUnitForTesting(module, 1, model);
			transcriptionalUnit.setTuDirectoryName("A1");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String inputFileDirectoryName = model.getWorkingDirectory() + File.separator
				+ transcriptionalUnit.getTuDirectoryName() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("parameterFileDirectory");
		File inputFileDirectory = new File(inputFileDirectoryName);
		if (!inputFileDirectory.exists()) {
			inputFileDirectory.mkdirs();
		}

		String tuWorkingDirectoryName = model.getWorkingDirectory() + File.separator
				+ transcriptionalUnit.getTuDirectoryName() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory");
		File tuDirectory = new File(tuWorkingDirectoryName);
		if (!tuDirectory.exists()) {
			tuDirectory.mkdirs();
		}

		// model.setPathToInputFileDirectory("H:\\Programmierung_BioInf\\AssemblX\\WorkingDir\\ParameterDateien");
		transcriptionalUnit
				.setJ5ParametersFileName(AssemblXWebProperties.getInstance().getProperty("j5ParametersFileName"));
		transcriptionalUnit
				.setZippedSequencesFileName(AssemblXWebProperties.getInstance().getProperty("zippedSequencesFileName"));
		// model.setPathToSequenceFileDirectory("H:\\Programmierung_BioInf\\AssemblX\\WorkingDir\\GeneBankFiles");
		transcriptionalUnit
				.setSequencesListFileName(AssemblXWebProperties.getInstance().getProperty("sequencesListFileName"));
		transcriptionalUnit.setPartsListFileName(AssemblXWebProperties.getInstance().getProperty("partsListFileName"));
		transcriptionalUnit.setTargetPartOrderListFileName(
				AssemblXWebProperties.getInstance().getProperty("targetPartsOrderListFileName"));
		transcriptionalUnit
				.setEugeneRulesListFileName(AssemblXWebProperties.getInstance().getProperty("eugeneRulesListFileName"));

		Map<TuSubunitDirection, String> tuSubunitDirections = new HashMap<TuSubunitDirection, String>();
		tuSubunitDirections.put(TuSubunitDirection.FORWARD, "forward");
		tuSubunitDirections.put(TuSubunitDirection.REVERSE, "reverse");
		model.setTuSubunitDirections(tuSubunitDirections);

		Map<TuSubunitAssemblyStrategy, String> tuSubunitAssemblyStrategies = new HashMap<TuSubunitAssemblyStrategy, String>();
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.ANNEALED_OLIGOS, "Annealed Oligos");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.DIRECT_SYNTHESIS, "Direct Synthesis");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_FORWARD, "Embed_in_primer_forward");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_REVERSE, "Embed_in_primer_reverse");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.PCR, "PCR");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.NO_STRATEGY, "unspecified");
		model.setTuSubunitAssemblyStrategies(tuSubunitAssemblyStrategies);

		HashMap<TuSubunit.TuSubunitSequenceOption, String> sequenceOptions = new HashMap<TuSubunit.TuSubunitSequenceOption, String>();
		sequenceOptions.put(TuSubunitSequenceOption.WHOLE_SEQUENCE, "Whole sequence");
		sequenceOptions.put(TuSubunitSequenceOption.SPECIFIED_SEQUENCE, "Specified sequence");
		model.setSequenceOptions(sequenceOptions);

		J5InputFileWriter j5InputFileWriter = new J5InputFileWriter();
		try {
			j5InputFileWriter.writeJ5ParameterFile(model, transcriptionalUnit);
			j5InputFileWriter.writeJ5ZippedSequencesArchive(model, transcriptionalUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TuSubunit> tuSubunits = new ArrayList<TuSubunit>();
		TuSubunit tuSubunit_1 = new TuSubunit(transcriptionalUnit, 1);
		TuSubunit tuSubunit_2 = new TuSubunit(transcriptionalUnit, 2);
		TuSubunit tuSubunit_3 = new TuSubunit(transcriptionalUnit, 3);
		TuSubunit tuSubunit_4 = new TuSubunit(transcriptionalUnit, 4);

		tuSubunit_1.setSequenceFileName("pL0_00005.gb");
		tuSubunit_1.setSequenceFormat("Genbank");
		tuSubunit_1.setPartName("pL0_5");
		tuSubunit_1.setPartSource("pL0_00005");
		tuSubunit_1.setIsReverseComplement(false);
		tuSubunit_1.setStart(4468);
		tuSubunit_1.setEnd(4459);
		tuSubunit_1.setFivePrimeInternalPreferredOverhangs("(1,36)");
		tuSubunit_1.setThreePrimeInternalPreferredOverhangs("(-36,36)");
		tuSubunit_1.setDirection(TuSubunitDirection.FORWARD);
		tuSubunit_1.setForcedRelativeOverhangPosition("");
		tuSubunit_1.setDirectSynthesisFirewall("");
		tuSubunits.add(tuSubunit_1);

		tuSubunit_2.setSequenceFileName("pPC_00023.gb");
		tuSubunit_2.setSequenceFormat("Genbank");
		tuSubunit_2.setPartName("GPH1prom");
		tuSubunit_2.setPartSource("pPC_00023");
		tuSubunit_2.setIsReverseComplement(false);
		tuSubunit_2.setStart(4374);
		tuSubunit_2.setEnd(4873);
		tuSubunit_2.setFivePrimeInternalPreferredOverhangs("");
		tuSubunit_2.setThreePrimeInternalPreferredOverhangs("");
		tuSubunit_2.setDirection(TuSubunitDirection.FORWARD);
		tuSubunit_2.setForcedAssemblyStrategy(TuSubunitAssemblyStrategy.PCR);
		tuSubunit_2.setForcedRelativeOverhangPosition("");
		tuSubunit_2.setDirectSynthesisFirewall("");
		tuSubunits.add(tuSubunit_2);

		tuSubunit_3.setSequenceFileName("pYES3_CTlacZ.gb");
		tuSubunit_3.setSequenceFormat("Genbank");
		tuSubunit_3.setPartName("LacZ");
		tuSubunit_3.setPartSource("pYES3_CTlacZ");
		tuSubunit_3.setIsReverseComplement(false);
		tuSubunit_3.setStart(528);
		tuSubunit_3.setEnd(3581);
		tuSubunit_3.setFivePrimeInternalPreferredOverhangs("");
		tuSubunit_3.setThreePrimeInternalPreferredOverhangs("");
		tuSubunit_3.setDirection(TuSubunitDirection.FORWARD);
		tuSubunit_3.setForcedAssemblyStrategy(TuSubunitAssemblyStrategy.PCR);
		tuSubunit_3.setForcedRelativeOverhangPosition("");
		tuSubunit_3.setDirectSynthesisFirewall("");
		tuSubunits.add(tuSubunit_3);

		tuSubunit_4.setSequenceFileName("Stop_codon.gb");
		tuSubunit_4.setSequenceFormat("Genbank");
		tuSubunit_4.setPartName("Stop");
		tuSubunit_4.setPartSource("Stop_codon");
		tuSubunit_4.setIsReverseComplement(false);
		tuSubunit_4.setStart(1);
		tuSubunit_4.setEnd(3);
		tuSubunit_4.setFivePrimeInternalPreferredOverhangs("");
		tuSubunit_4.setThreePrimeInternalPreferredOverhangs("");
		tuSubunit_4.setDirection(TuSubunitDirection.FORWARD);
		tuSubunit_4.setForcedAssemblyStrategy(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_FORWARD);
		tuSubunit_4.setForcedRelativeOverhangPosition("");
		tuSubunit_4.setDirectSynthesisFirewall("");
		tuSubunits.add(tuSubunit_4);

		transcriptionalUnit.setTuSubunits(tuSubunits);

		try {
			j5InputFileWriter.writeJ5SequencesListFile(model, transcriptionalUnit);
			j5InputFileWriter.writeJ5PartsListFile(model, transcriptionalUnit);
			j5InputFileWriter.writeJ5TargetPartsOrderListFile(model, transcriptionalUnit);
			j5InputFileWriter.writeJ5EugeneRulesListFile(model, transcriptionalUnit);
			j5InputFileWriter.writeJ5MasterPlasmidsListFile(model);
			j5InputFileWriter.writeJ5MasterOligosListFile(model);
			j5InputFileWriter.writeJ5MasterDirectSynthesisListFile(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
