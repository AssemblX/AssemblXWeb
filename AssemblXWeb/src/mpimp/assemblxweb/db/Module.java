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
package mpimp.assemblxweb.db;

import java.io.IOException;
import java.util.List;

import mpimp.assemblxweb.util.BackboneGenerator;

public class Module {

	public static enum CopyVariant {
		HIGH, LOW
	};

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}

	public List<TranscriptionalUnit> getTranscriptionalUnits() {
		return transcriptionalUnits_;
	}

	public void setTranscriptionalUnits(
			List<TranscriptionalUnit> transcriptionalUnits) {
		transcriptionalUnits_ = transcriptionalUnits;
	}

	public Boolean getIsLastModule() {
		return isLastModule_;
	}

	public void setIsLastModule(Boolean isLastModule) {
		isLastModule_ = isLastModule;
	}

	public void setBackbones(AssemblXWebModel model) throws IOException {
		if (transcriptionalUnits_ != null && transcriptionalUnits_.size() > 0) {
			for (TranscriptionalUnit currentTu : transcriptionalUnits_) {
				if (currentTu.getActive() == true && currentTu.getJ5ErrorMessage().equals("")
						&& currentTu.getJ5FaultString().equals("")) {
					TuSubunit tuSubunit = BackboneGenerator
							.generateBackboneTuSubunit(currentTu, model);
					if (currentTu.getTuSubunits().size() > 0) {
						//backbone (and maybe other subunits) already present but outdated
						currentTu.getTuSubunits().set(0, tuSubunit);
					} else {
						currentTu.getTuSubunits().add(tuSubunit);
					}
					currentTu.updateTuSubunitPositionList();
					// Please note: if a tu unit is deactivated, the backbone entry (and all other entries
					// will remain in the working directory file system - since only
					// active tus will be processed this should be no big problem.
				}
			}
		}
	}

	public CopyVariant getCopyVariant() {
		return copyVariant_;
	}

	public void setCopyVariant(CopyVariant copyVariant) {
		copyVariant_ = copyVariant;
	}

	public String getLevel_1_vectorName() {
		return level_1_vectorName_;
	}

	public void setLevel_1_vectorName(String level_1_vectorName) {
		level_1_vectorName_ = level_1_vectorName;
	}

	public Integer getLevel_1_vectorLength() {
		return level_1_vectorLength_;
	}

	public void setLevel_1_vectorLength(Integer level_1_vectorLength) {
		level_1_vectorLength_ = level_1_vectorLength;
	}

	public String getInsertSequence() {
		return insertSequence_;
	}

	public void setInsertSequence(String insertSequence) {
		insertSequence_ = insertSequence;
	}

	public String getFinalLevel_1_Construct() {
		return finalLevel_1_Construct_;
	}

	public void setFinalLevel_1_Construct(String finalLevel_1_Construct) {
		finalLevel_1_Construct_ = finalLevel_1_Construct;
	}

	public String getLevel_2_fragment() {
		return level_2_fragment_;
	}

	public void setLevel_2_fragment(String level_2_fragment) {
		level_2_fragment_ = level_2_fragment;
	}

	public Integer getLevel_2_fragmentLength() {
		return level_2_fragmentLength_;
	}

	public void setLevel_2_fragmentLength(Integer level_2_fragmentLength) {
		level_2_fragmentLength_ = level_2_fragmentLength;
	}

	public Boolean getContainsI_Scel_I_sites() {
		return containsI_Scel_I_sites_;
	}

	public void setContainsI_Scel_I_sites(Boolean containsI_Scel_I_sites) {
		containsI_Scel_I_sites_ = containsI_Scel_I_sites;
	}

	public Boolean getSimilarSizes() {
		return similarSizes_;
	}

	public void setSimilarSizes(Boolean similarSizes) {
		similarSizes_ = similarSizes;
	}

	public Boolean hasEvaluableUnits() {
		Boolean hasEvaluableUnits = false;
		if (transcriptionalUnits_ != null) {
			for (TranscriptionalUnit currentTuUnit : transcriptionalUnits_) {
				if (currentTuUnit.isEvaluable()) {
					hasEvaluableUnits = true;
					break;
				}
			}
		}
		return hasEvaluableUnits;
	}

	public Boolean hasActiveUnits() {
		Boolean hasActiveUnits = false;
		if (transcriptionalUnits_ != null) {
			for (TranscriptionalUnit currentTuUnit : transcriptionalUnits_) {
				if (currentTuUnit.getActive()) {
					hasActiveUnits = true;
					break;
				}
			}
		}
		return hasActiveUnits;
	}

	public Level_1_Protocol getLevel_1_protocol() {
		return level_1_protocol_;
	}

	public void setLevel_1_protocol(Level_1_Protocol level_1_protocol) {
		level_1_protocol_ = level_1_protocol;
	}

	private String name_;
	private List<TranscriptionalUnit> transcriptionalUnits_;
	private Boolean isLastModule_ = true;
	private CopyVariant copyVariant_ = CopyVariant.LOW;
	private String level_1_vectorName_;
	private Integer level_1_vectorLength_;
	// assembled level 0 fragments for cloning into level 1 vector
	private String insertSequence_;
	private String finalLevel_1_Construct_;
	// for cloning into level 2 vector
	private String level_2_fragment_;
	private Integer level_2_fragmentLength_;
	private Boolean containsI_Scel_I_sites_ = false;
	private Boolean similarSizes_ = false;
	
	private Level_1_Protocol level_1_protocol_;
}
