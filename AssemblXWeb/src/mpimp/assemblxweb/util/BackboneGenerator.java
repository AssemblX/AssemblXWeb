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
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;

public class BackboneGenerator {

	public static TuSubunit generateBackboneTuSubunit(
			TranscriptionalUnit parentTu, AssemblXWebModel model)
			throws IOException {
		TuSubunit backbone = new TuSubunit(parentTu, 1);
		backbone.setSequenceInputOption(SequenceInputOption.LOAD_FROM_FILE);
		backbone.setType(TuSubunitType.BACKBONE);
		backbone.setSequenceFileName(createLevel_0_VectorSourceName(parentTu,
				true));
		backbone.setSequenceFormat("Genbank");
		backbone.setPartName(createLevel_0_VectorSourceName(parentTu, false));
		backbone.setPartSource(createLevel_0_VectorSourceName(parentTu, false));
		backbone.setIsReverseComplement(false);
		backbone.setSequenceOption(TuSubunitSequenceOption.WHOLE_SEQUENCE);
		// ##################### TODO: clarify - these settings seem to be
		// important for level 1 #############
		backbone.setFivePrimeInternalPreferredOverhangs("(1,36)");
		backbone.setThreePrimeInternalPreferredOverhangs("(-36,36)");
		// #######################################################################
		backbone.setDirection(TuSubunitDirection.FORWARD);
		backbone.setForcedAssemblyStrategy(TuSubunitAssemblyStrategy.DIGEST);
		backbone.setForcedRelativeOverhangPosition("");
		backbone.setDirectSynthesisFirewall("");
		// backbone.setExtra5PrimeCPECOverlapBps("");
		// backbone.setExtra3PrimeCPECOverlapBps("");

		String tuWorkingDirectoryName = model.getWorkingDirectory()
				+ File.separator
				+ parentTu.getParentModule().getName()
				+ parentTu.getPosition()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"sequenceFileDirectory");

		if (parentTu.getTuSubunits().size() > 0) {
			// there is already a backbone file which has to be replaced
			String sequenceFileName = parentTu.getTuSubunits().get(0)
					.getSequenceFileName();
			Path sequenceFilePath = Paths.get(tuWorkingDirectoryName
					+ File.separator + sequenceFileName);
			Files.delete(sequenceFilePath);
		}

		// copy the sequence file for the backbone from the repository to the
		// sequence directory of the transcriptional unit
		Path sequenceFilePath = Paths.get(tuWorkingDirectoryName
				+ File.separator + backbone.getSequenceFileName());
		Path backboneSequenceFileSourcePath = Paths.get(model
				.getServletContextRealPath()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"pathToLevel_0_vectorDirectory")
				+ File.separator
				+ backbone.getSequenceFileName());

		Files.copy(backboneSequenceFileSourcePath, sequenceFilePath);
		model.setCurrentTuUnit(parentTu);
		SequenceFileParser.parseSequenceFile(model, backbone, true);
		backbone.setSequenceFileLoaded(true);

		return backbone;
	}

	public static String createLevel_0_VectorSourceName(
			TranscriptionalUnit parentTu, Boolean withFileExtension) {
		String moduleName = parentTu.getParentModule().getName();
		Boolean lastTuInModule = parentTu.getIsLastUnitInModule();
		Integer positionIndex = parentTu.getPosition() - 1;
		String nextPositionIndexString = "";
		Integer nextPositionIndex = -1;
		if (lastTuInModule) {
			nextPositionIndexString = "R";
		} else {
			nextPositionIndex = positionIndex + 1;
			nextPositionIndexString = nextPositionIndex.toString();
		}
		if (withFileExtension) {
			return "pL0" + moduleName + "_" + positionIndex + "-"
					+ nextPositionIndexString + "_linear.gb";
		} else {
			return "pL0" + moduleName + "_" + positionIndex + "-"
					+ nextPositionIndexString + "_linear";
		}
	}
}
