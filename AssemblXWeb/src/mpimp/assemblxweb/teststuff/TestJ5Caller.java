package mpimp.assemblxweb.teststuff;

import java.io.File;
import java.io.IOException;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.OperatorRecord;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.AssemblyMethod;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.J5Caller;

public class TestJ5Caller {

	public static void main(String[] args) {
		// Module module = new Module();
		// module.setName("A");
		AssemblXWebModel model = new AssemblXWebModel();
		// //
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\WorkingDir");
		// model.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir/Jgremmels/");
		// String pathToMasterFilesDirectory = model.getWorkingDirectory()
		// + File.separator +
		// AssemblXWebProperties.getInstance().getProperty("masterFilesDirectory");
		// model.setPathToMasterFilesDirectory(pathToMasterFilesDirectory);
		// //
		// model.setPathToInputFileDirectory("H:\\Programmierung_BioInf\\AssemblX\\Trainingsset\\ParameterDateien");
		// TranscriptionalUnit transcriptionalUnit = null;
		// try {
		// transcriptionalUnit = new TranscriptionalUnitForTesting(module, 1,
		// model);
		// transcriptionalUnit.setTuDirectoryName("A1");
		// } catch (IOException e) {
		// e.printStackTrace();
		// return;
		// }
		//
		// transcriptionalUnit.setJ5ParametersFileName(AssemblXWebProperties
		// .getInstance().getProperty("j5ParametersFileName"));
		// transcriptionalUnit.setSequencesListFileName(AssemblXWebProperties
		// .getInstance().getProperty("sequencesListFileName"));
		// transcriptionalUnit.setZippedSequencesFileName(AssemblXWebProperties
		// .getInstance().getProperty("zippedSequencesFileName"));
		// transcriptionalUnit.setPartsListFileName(AssemblXWebProperties
		// .getInstance().getProperty("partsListFileName"));
		// transcriptionalUnit
		// .setTargetPartOrderListFileName(AssemblXWebProperties
		// .getInstance().getProperty(
		// "targetPartsOrderListFileName"));
		// transcriptionalUnit.setEugeneRulesListFileName(AssemblXWebProperties
		// .getInstance().getProperty("eugeneRulesListFileName"));
		// transcriptionalUnit.setIsMockAssembly(true);
		OperatorRecord operator = new OperatorRecord();
		operator.setLogin("Jgremmels");
		operator.setPassword("kmZumGkmGuK");
		// transcriptionalUnit.setAssemblyMethod(AssemblyMethod.Mock_Assembly);

		try {
			J5Caller.callGetSessionId(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(model.getJ5SessionId());
		// J5Caller.callDesignAssemblyScript(model, transcriptionalUnit);
	}

}
