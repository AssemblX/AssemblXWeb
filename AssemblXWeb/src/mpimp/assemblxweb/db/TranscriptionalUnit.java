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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;
import mpimp.assemblxweb.util.AssemblXException;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.BackboneGenerator;

public class TranscriptionalUnit {

	public static enum AssemblyMethod {
		SLIC_Gibson_CPEC, Combinatorial_SLIC_Gibson_CPEC, Mock_Assembly, Golden_Gate, Combinatorial_Golden_Gate, Combinatorial_Mock_Assembly
	};

	public static enum EditingStage {
		INACTIVE, NEW, IN_PROGRESS, READY
	};

	public TranscriptionalUnit(Module parentModule, Integer position, AssemblXWebModel model) throws Exception {
		parentModule_ = parentModule;
		position_ = position;
		init(model);
	}

	protected void init(AssemblXWebModel model) throws Exception {
		tuSubunits_ = new ArrayList<TuSubunit>();
		String tuWorkingDirectoryName = model.getWorkingDirectory() + File.separator + parentModule_.getName()
				+ position_ + File.separator + AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory");

		File tuDirectory = new File(tuWorkingDirectoryName);
		if (!tuDirectory.exists()) {
			if (!tuDirectory.mkdirs()) {
				String message = "Could not create working subdirectory " + tuWorkingDirectoryName + ". ";
				throw new AssemblXException(message, this.getClass());
			}
		}
		annotationRecords_ = new ArrayList<AnnotationRecord>();
		tuSubunitPositions_ = new ArrayList<Integer>();
	}

	public static enum TUOrientation {
		SENSE, ANTI_SENSE
	};

	public TUOrientation getOrientation() {
		return orientation_;
	}

	public void setOrientation(TUOrientation orientation) {
		orientation_ = orientation;
	}

	public List<TuSubunit> getTuSubunits() {
		return tuSubunits_;
	}

	public void setTuSubunits(List<TuSubunit> tuSubunits) {
		tuSubunits_ = tuSubunits;
	}

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}

	public String getJ5ParametersFileName() {
		return j5ParametersFileName_;
	}

	public void setJ5ParametersFileName(String j5ParametersFileName) {
		j5ParametersFileName_ = j5ParametersFileName;
	}

	public String getSequencesListFileName() {
		return sequencesListFileName_;
	}

	public void setSequencesListFileName(String sequencesListFileName) {
		sequencesListFileName_ = sequencesListFileName;
	}

	public String getZippedSequencesFileName() {
		return zippedSequencesFileName_;
	}

	public void setZippedSequencesFileName(String zippedSequencesFileName) {
		zippedSequencesFileName_ = zippedSequencesFileName;
	}

	public String getPartsListFileName() {
		return partsListFileName_;
	}

	public void setPartsListFileName(String partsListFileName) {
		partsListFileName_ = partsListFileName;
	}

	public String getTargetPartOrderListFileName() {
		return targetPartOrderListFileName_;
	}

	public void setTargetPartOrderListFileName(String targetPartOrderListFileName) {
		targetPartOrderListFileName_ = targetPartOrderListFileName;
	}

	public String getEugeneRulesListFileName() {
		return eugeneRulesListFileName_;
	}

	public void setEugeneRulesListFileName(String eugeneRulesListFileName) {
		eugeneRulesListFileName_ = eugeneRulesListFileName;
	}

	public AssemblyMethod getAssemblyMethod() {
		return assemblyMethod_;
	}

	public void setAssemblyMethod(AssemblyMethod assemblyMethod) {
		assemblyMethod_ = assemblyMethod;
	}

	public String getMockOrCombinatorialMock() {
		return mockOrCombinatorialMock_;
	}

	public void setMockOrCombinatorialMock(String mockOrCombinatorialMock) {
		mockOrCombinatorialMock_ = mockOrCombinatorialMock;
	}

	public String getImageSource() {
		if (orientation_ == TUOrientation.SENSE) {
			if (active_) {
				return AssemblXWebProperties.getInstance().getProperty("imgSenseActive");
			} else {
				return AssemblXWebProperties.getInstance().getProperty("imgSenseInactive");
			}
		} else {
			if (active_) {
				return AssemblXWebProperties.getInstance().getProperty("imgAntisenseActive");
			} else {
				return AssemblXWebProperties.getInstance().getProperty("imgAntisenseInactive");
			}
		}
	}

	public String getTrafficLightImageSource() {
		if (editingStage_ == EditingStage.NEW) {
			return AssemblXWebProperties.getInstance().getProperty("imgTrafficLightRed");
		} else if (editingStage_ == EditingStage.IN_PROGRESS) {
			return AssemblXWebProperties.getInstance().getProperty("imgTrafficLightYellow");
		} else if (editingStage_ == EditingStage.READY) {
			return AssemblXWebProperties.getInstance().getProperty("imgTrafficLightGreen");
		} else if (editingStage_ == EditingStage.INACTIVE) {
			return AssemblXWebProperties.getInstance().getProperty("imgTrafficLightNoLight");
		} else {
			return "";
		}
	}

	public Boolean isEvaluable() {
		if (active_ && tuSubunits_.size() > 1 && j5ErrorMessage_.equals("") && j5FaultString_.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getActive() {
		return active_;
	}

	public void setActive(Boolean active) {
		active_ = active;
	}

	public Boolean getIsLastUnitInModule() {
		return isLastUnitInModule_;
	}

	public void setIsLastUnitInModule(Boolean isLastUnitInModule) {
		isLastUnitInModule_ = isLastUnitInModule;
	}

	public Module getParentModule() {
		return parentModule_;
	}

	public void setParentModule(Module parentModule) {
		parentModule_ = parentModule;
	}

	public Integer getPosition() {
		return position_;
	}

	public void setPosition(Integer position) {
		position_ = position;
	}

	public String getIdentifier() {
		String identifier = parentModule_.getName() + position_;
		return identifier;
	}

	public String getTuDirectoryName() {
		return tuDirectoryName_;
	}

	public void setTuDirectoryName(String tuDirectoryName) {
		tuDirectoryName_ = tuDirectoryName;
	}

	public String getResultDirectoryName() {
		return resultDirectoryName_;
	}

	public void setResultDirectoryName(String resultDirectoryName) {
		this.resultDirectoryName_ = resultDirectoryName;
	}

	public String getResultFileName() {
		return resultFileName_;
	}

	public void setResultFileName(String resultFileName) {
		resultFileName_ = resultFileName;
	}

	public String getResultGenbankFileName() {
		return resultGenbankFileName_;
	}

	public void setResultGenbankFileName(String resultGenbankFileName) {
		resultGenbankFileName_ = resultGenbankFileName;
	}

	public Boolean getIsMockAssembly() {
		return isMockAssembly_;
	}

	public void setIsMockAssembly(Boolean isMockAssembly) {
		isMockAssembly_ = isMockAssembly;
	}

	public String getJ5FaultString() {
		return j5FaultString_;
	}

	public void setJ5FaultString(String j5FaultString) {
		j5FaultString_ = j5FaultString;
	}

	public String getJ5ErrorMessage() {
		return j5ErrorMessage_;
	}

	public void setJ5ErrorMessage(String j5ErrorMessage) {
		j5ErrorMessage_ = j5ErrorMessage;
	}

	public Level_0_Protocol getLevel_0_Protocol() {
		return level_0_Protocol_;
	}

	public void setLevel_0_Protocol(Level_0_Protocol level_0_Protocol) {
		level_0_Protocol_ = level_0_Protocol;
	}

	public List<AnnotationRecord> getAnnotationRecords() {
		return annotationRecords_;
	}

	public List<Integer> getTuSubunitPositions() {
		return tuSubunitPositions_;
	}

	public void setTuSubunitPositions(List<Integer> tuSubunitPositions) {
		tuSubunitPositions_ = tuSubunitPositions;
	}

	public void updateTuSubunitPositionList() {
		Integer noOfTuSubunits = tuSubunits_.size();
		tuSubunitPositions_.clear();
		// we add positions of all current subunits (in 1..n notation)
		// and one more for the new subunit to be added at the end
		for (int i = 0; i < noOfTuSubunits + 1; i++) {
			tuSubunitPositions_.add(i + 1);
		}
		Collections.reverse(tuSubunitPositions_);
	}

	public EditingStage getEditingStage() {
		return editingStage_;
	}

	public void setEditingStage(EditingStage editingStage) {
		editingStage_ = editingStage;
	}

	public Boolean getEditingFinished() {
		return editingFinished_;
	}

	public void setEditingFinished(Boolean editingFinished) {
		editingFinished_ = editingFinished;
	}

	public String getPlasmidName() {
		return plasmidName_;
	}

	public void setPlasmidName(String plasmidName) {
		plasmidName_ = plasmidName;
	}

	// arguments for design assembly script (beyond j5sessionId)
	private String j5ParametersFileName_;
	private String sequencesListFileName_;
	private String zippedSequencesFileName_;
	private String partsListFileName_;
	private String targetPartOrderListFileName_;
	private String eugeneRulesListFileName_;
	private AssemblyMethod assemblyMethod_; // SLIC/Gibson/CPEC or GoldenGate or
	// CombinatorialSLICGibsonCPEC or
	// CombinatorialGoldenGate
	
	//the name used for the result of j5 calculations (the genbank file as well as the names used in the masterplasmid list and the protocols of all levels)
	private String plasmidName_ = "NOT_SET";
	
	private String mockOrCombinatorialMock_;
	private Boolean isMockAssembly_ = false;

	private String j5FaultString_ = "";
	private String j5ErrorMessage_ = "";

	private TUOrientation orientation_ = TUOrientation.SENSE;
	private List<TuSubunit> tuSubunits_;
	private String name_;
	private Module parentModule_;
	private Integer position_;
	private Boolean active_ = false;
	private Boolean isLastUnitInModule_ = true;
	private String tuDirectoryName_ = "";
	private String resultDirectoryName_ = "";
	private String resultFileName_ = "";
	private String resultGenbankFileName_ = "";
	private EditingStage editingStage_ = EditingStage.INACTIVE;
	private Boolean editingFinished_ = false;

	private Level_0_Protocol level_0_Protocol_;

	private List<AnnotationRecord> annotationRecords_;
	private List<Integer> tuSubunitPositions_;

}
