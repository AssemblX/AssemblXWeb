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
import java.util.ArrayList;
import java.util.List;

import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.I_SceI_Sites;
import mpimp.assemblxweb.db.Level_1_Annotations;
import mpimp.assemblxweb.db.Module;

public class Level_2_ProtocolCreator {

	public Level_2_ProtocolCreator(AssemblXWebModel assemblXWebModel) {
		assemblXWebModel_ = assemblXWebModel;
	}

	public void createLevel_2_Protocol() throws Exception {
		List<Module> modules = assemblXWebModel_.getModules();
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				extractInsert(currentModule);
				checkFragmentVectorLengthRelation(currentModule);
				checkFragmentForIScelISites(currentModule);
			}
		}
		createFinalAssembly();
	}

	private void extractInsert(Module module) {
		String level_1_Construct = module.getFinalLevel_1_Construct();
		String startSite = I_SceI_Sites.i_sci_one;
		String endSite = I_SceI_Sites.i_sci_two;
		Integer indexStartSite = level_1_Construct.indexOf(startSite);
		Integer indexEndSite = level_1_Construct.indexOf(endSite);

		while (indexStartSite == -1 || indexEndSite == -1 || indexEndSite < indexStartSite) {
			String firstBase = level_1_Construct.substring(0, 1);
			level_1_Construct = level_1_Construct.substring(1);
			level_1_Construct += firstBase;
			indexStartSite = level_1_Construct.indexOf(startSite);
			indexEndSite = level_1_Construct.indexOf(endSite);
		}

		// String level_2_fragment = level_1_Construct.substring(indexStartSite,
		// indexEndSite + endSite.length());
		String level_2_fragment = level_1_Construct.substring(indexStartSite + startSite.length(), indexEndSite);

		module.setLevel_2_fragment(level_2_fragment);
		module.setLevel_2_fragmentLength(level_2_fragment.length());
	}

	private void checkFragmentVectorLengthRelation(Module module) {
		Integer level_1_vector_length = module.getLevel_1_vectorLength();
		Integer level_2_fragment_length = module.getLevel_2_fragmentLength();

		Double quotient = (double) level_2_fragment_length / level_1_vector_length;

		if ((quotient > 0.9) && (quotient < 1.1)) {
			module.setSimilarSizes(true);
		} else {
			module.setSimilarSizes(false);
		}
	}

	private void checkFragmentForIScelISites(Module module) {
		String level_2_fragment = module.getLevel_2_fragment().toUpperCase();
		if ((level_2_fragment.contains(I_SceI_Sites.i_sci_one)
				|| (level_2_fragment.contains(I_SceI_Sites.i_sci_two)))) {
			module.setContainsI_Scel_I_sites(true);
		} else {
			module.setContainsI_Scel_I_sites(false);
		}
	}

	private void createFinalAssembly() throws Exception {
		String finalLevel_2_construct = "";
		List<Module> modules = assemblXWebModel_.getModules();
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits() && !currentModule.getIsLastModule()) {
				Integer endIndex = currentModule.getLevel_2_fragmentLength() - 50;
				finalLevel_2_construct += currentModule.getLevel_2_fragment().substring(0, endIndex);
			} else if (currentModule.hasEvaluableUnits() && currentModule.getIsLastModule()) {
				Integer endIndex = currentModule.getLevel_2_fragmentLength();
				finalLevel_2_construct += currentModule.getLevel_2_fragment().substring(0, endIndex);
			}
			assemblXWebModel_.getModules().get(0).getTranscriptionalUnits().get(0).getAnnotationRecords()
					.addAll(Level_1_Annotations.getModuleSpecific_level_1_annotations(currentModule));
		}
		assemblXWebModel_.setLevel_2_vectorName(createLevel_2_VectorSourceName(false));
		String vectorFilePath = assemblXWebModel_.getServletContextRealPath() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("pathToLevel_2_vectorDirectory") + File.separator
				+ assemblXWebModel_.getLevel_2_vectorName() + ".gb";
		Integer vectorLength = SequenceFileParser.extractLengthFromVectorFile(vectorFilePath);
		assemblXWebModel_.setLevel_2_vectorLength(vectorLength);
		String vectorSequence = SequenceFileParser.extractSequenceFromGenbankFile(vectorFilePath);
		// delete overlap of 50 bp at begin and end
		vectorSequence = vectorSequence.substring(50, vectorSequence.length() - 50);
		finalLevel_2_construct = vectorSequence + finalLevel_2_construct;
		assemblXWebModel_.setFinalLevel_2_Construct(finalLevel_2_construct);
		// remove annotations of level-1-backbone which will not be necessary
		// any longer
		for (Module currentModule : assemblXWebModel_.getModules()) {
			List<AnnotationRecord> annotationsForCurrentModule = currentModule.getTranscriptionalUnits().get(0)
					.getAnnotationRecords();
			List<Integer> indicesOfAnnotationsToDelete = new ArrayList<Integer>();
			for (AnnotationRecord currentAnnotation : annotationsForCurrentModule) {
				if (currentAnnotation.getIdentifier()
						.equals(AssemblXWebProperties.getInstance().getProperty("idLevel1Backbone"))) {
					indicesOfAnnotationsToDelete.add(annotationsForCurrentModule.indexOf(currentAnnotation));
				}
			}
			for (Integer indexToDelete : indicesOfAnnotationsToDelete) {
				annotationsForCurrentModule.remove(indexToDelete);
			}
		}
		try {
			List<AnnotationRecord> annotationsOfLevel2Backbone = new ArrayList<AnnotationRecord>();
			annotationsOfLevel2Backbone = GenbankAnnotationParser.parseAnnotations(vectorFilePath, null, null);
			assemblXWebModel_.getModules().get(0).getTranscriptionalUnits().get(0).getAnnotationRecords()
					.addAll(annotationsOfLevel2Backbone);
		} catch (Exception e) {
			String message = "Error during extracting annotations from level 2 backbone " + vectorFilePath + ". "
					+ e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private String createLevel_2_VectorSourceName(Boolean withFileExtension) {
		String lastModuleName = "";
		List<Module> modules = assemblXWebModel_.getModules();
		for (Module currentModule : modules) {
			if (currentModule.getIsLastModule() && currentModule.hasEvaluableUnits()) {
				lastModuleName = currentModule.getName();
			}
		}
		String vectorFileName = "pL2A" + lastModuleName + "-";
		if (assemblXWebModel_.getLevel_2_copyVariant() == Module.CopyVariant.HIGH) {
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

	private AssemblXWebModel assemblXWebModel_;
}
