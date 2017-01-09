package mpimp.assemblxweb.teststuff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.util.J5ResultAnalyzer;
import mpimp.assemblxweb.util.Level_0_Protocol_Writer;
import mpimp.assemblxweb.util.Level_1_ProtocolCreator;
import mpimp.assemblxweb.util.Level_1_ProtocolWriter;

public class TestLevel_1_ProtocolCreator {

	public static void main(String[] args) {
		init();
//		List<TranscriptionalUnit> tuUnits = module_.getTranscriptionalUnits();
//		for (TranscriptionalUnit currentTuUnit : tuUnits) {
//			Level_0_Protocol_Writer level_0_protocolCreator = new Level_0_Protocol_Writer(
//					currentTuUnit, assemblXWebModel_);
//			level_0_protocolCreator.writeLevel_0_ProtocolSection();
//		}
//		Level_1_ProtocolCreator level_1_protocolCreator = new Level_1_ProtocolCreator(
//				module_, assemblXWebModel_);
//		try {
//			level_1_protocolCreator.createLevel_1_Protocol();
//			Level_1_ProtocolWriter level_1_protocolWriter = new Level_1_ProtocolWriter(
//					assemblXWebModel_);
//			level_1_protocolWriter.writeLevel_1_ProtocolSheet();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	private static void init() {
		assemblXWebModel_ = new AssemblXWebModel();
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\TestWorkingDir");
		assemblXWebModel_
				.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir/Jgremmels_5");
		module_ = new Module();
		module_.setName("B");
		try {
			List<TranscriptionalUnit> tuUnits = new ArrayList<TranscriptionalUnit>();

			TranscriptionalUnit transcriptionalUnit = new TranscriptionalUnitForTesting(
					module_, 1, assemblXWebModel_);
			transcriptionalUnit.setActive(true);
			transcriptionalUnit.setName("B1");
			transcriptionalUnit.setOrientation(TUOrientation.SENSE);
			transcriptionalUnit.setParentModule(module_);
			transcriptionalUnit.setTuDirectoryName("B1");
			transcriptionalUnit.setResultFileName("pmas00001.csv");
			transcriptionalUnit
					.setResultDirectoryName("j5_Jgremmels_20160308072056N1mA");
			transcriptionalUnit.setIsLastUnitInModule(false);
			tuUnits.add(transcriptionalUnit);
			J5ResultAnalyzer.parseResultFile(transcriptionalUnit,
					assemblXWebModel_);

			transcriptionalUnit = new TranscriptionalUnitForTesting(module_, 2,
					assemblXWebModel_);
			transcriptionalUnit.setActive(true);
			transcriptionalUnit.setName("B2");
			transcriptionalUnit.setOrientation(TUOrientation.SENSE);
			transcriptionalUnit.setParentModule(module_);
			transcriptionalUnit.setTuDirectoryName("B2");
			transcriptionalUnit.setResultFileName("pmas00001.csv");
			transcriptionalUnit
					.setResultDirectoryName("j5_Jgremmels_20160308072138CFy8");
			transcriptionalUnit.setIsLastUnitInModule(true);
			tuUnits.add(transcriptionalUnit);
			J5ResultAnalyzer.parseResultFile(transcriptionalUnit,
					assemblXWebModel_);

			module_.setTranscriptionalUnits(tuUnits);
			List<Module> modules = new ArrayList<Module>();
			modules.add(module_);
			assemblXWebModel_.setModules(modules);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private static AssemblXWebModel assemblXWebModel_;
	private static Module module_;
	// private static TranscriptionalUnit transcriptionalUnit_;

}
