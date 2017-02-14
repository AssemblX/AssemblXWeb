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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mpimp.assemblxweb.db.Module.CopyVariant;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;
import mpimp.assemblxweb.util.AssemblXWebEnumTranslator;
import mpimp.assemblxweb.util.Counter;

public class AssemblXWebModel {
	
	public Boolean getEnterJ5AccountCredentials() {
		return enterJ5AccountCredentials_;
	}

	public void setEnterJ5AccountCredentials(Boolean enterJ5AccountCredentials) {
		enterJ5AccountCredentials_ = enterJ5AccountCredentials;
	}
	public OperatorRecord getOperator() {
		return operator_;
	}

	public void setOperator(OperatorRecord operator) {
		operator_ = operator;
	}

	public Boolean getUserAuthenticated() {
		return userAuthenticated_;
	}

	public void setUserAuthenticated(Boolean userAuthenticated) {
		userAuthenticated_ = userAuthenticated;
	}

	public String getWorkingDirectory() {
		return workingDirectory_;
	}

	public void setWorkingDirectory(String workingDirectory) {
		workingDirectory_ = workingDirectory;
	}

	public String getServletContextRealPath() {
		return servletContextRealPath_;
	}

	public void setServletContextRealPath(String servletContextRealPath) {
		servletContextRealPath_ = servletContextRealPath;
	}

	public String getJ5SessionId() {
		return j5SessionId_;
	}

	public void setJ5SessionId(String j5SessionId) {
		j5SessionId_ = j5SessionId;
	}

	public String getPathToMasterFilesDirectory() {
		return pathToMasterFilesDirectory_;
	}

	public String getSubmitJobErrorMessage() {
		return submitJobErrorMessage_;
	}

	public void setSubmitJobErrorMessage(String submitJobErrorMessage) {
		submitJobErrorMessage_ = submitJobErrorMessage;
	}

	public void setPathToMasterFilesDirectory(String pathToMasterFilesDirectory) {
		pathToMasterFilesDirectory_ = pathToMasterFilesDirectory;
	}

	public List<Module> getModules() {
		return modules_;
	}

	public void setModules(List<Module> modules) {
		modules_ = modules;
	}
	
	public List<String> getEditModes() {
		editModes_ = new ArrayList<String>();
		editModes_.add("add/remove units");
		editModes_.add("change orientation of a Level 0 assembly unit");
		editModes_.add("edit Level 0 assembly unit");
		return editModes_;
	}
	
	public String getEditMode() {
		return editMode_;
	}

	public void setEditMode(String editMode) {
		editMode_ = editMode;
	}

	public TranscriptionalUnit getCurrentTuUnit() {
		return currentTuUnit_;
	}

	public void setCurrentTuUnit(TranscriptionalUnit currentTuUnit) {
		currentTuUnit_ = currentTuUnit;
	}

	public Map<TuSubunitType, String> getTuSubunitTypes() {
		return tuSubunitTypes_;
	}

	public void setTuSubunitTypes(Map<TuSubunitType, String> tuSubunitTypes) {
		tuSubunitTypes_ = tuSubunitTypes;
	}

	public Map<TuSubunitAssemblyStrategy, String> getTuSubunitAssemblyStrategies() {
		return tuSubunitAssemblyStrategies_;
	}

	public void setTuSubunitAssemblyStrategies(
			Map<TuSubunitAssemblyStrategy, String> tuSubunitAssemblyStrategies) {
		tuSubunitAssemblyStrategies_ = tuSubunitAssemblyStrategies;
	}

	public Map<TuSubunit.TuSubunitSequenceOption, String> getSequenceOptions() {
		return sequenceOptions_;
	}

	public void setSequenceOptions(Map<TuSubunit.TuSubunitSequenceOption, String> sequenceOptions) {
		sequenceOptions_ = sequenceOptions;
	}

	public Map<TuSubunit.TuSubunitDirection, String> getTuSubunitDirections() {
		return tuSubunitDirections_;
	}

	public void setTuSubunitDirections(
			Map<TuSubunit.TuSubunitDirection, String> tuSubunitDirections) {
		tuSubunitDirections_ = tuSubunitDirections;
	}

	public Map<Module.CopyVariant, String> getCopyVariants() {
		return copyVariants_;
	}

	public void setCopyVariants(Map<Module.CopyVariant, String> copyVariants) {
		copyVariants_ = copyVariants;
	}

	public Map<SequenceInputOption, String> getTuSubunitSequenceInputOptions() {
		return tuSubunitSequenceInputOptions_;
	}

	public void setTuSubunitSequenceInputOptions(
			Map<SequenceInputOption, String> tuSubunitSequenceInputOptions) {
		tuSubunitSequenceInputOptions_ = tuSubunitSequenceInputOptions;
	}

	public Module.CopyVariant getLevel_2_copyVariant() {
		return level_2_copyVariant_;
	}

	public void setLevel_2_copyVariant(Module.CopyVariant level_2_copyVariant) {
		level_2_copyVariant_ = level_2_copyVariant;
	}

	public String getFinalLevel_2_Construct() {
		return finalLevel_2_Construct_;
	}

	public void setFinalLevel_2_Construct(String finalLevel_2_Construct) {
		finalLevel_2_Construct_ = finalLevel_2_Construct;
	}

	public String getLevel_2_vectorName() {
		return level_2_vectorName_;
	}

	public void setLevel_2_vectorName(String level_2_vectorName) {
		level_2_vectorName_ = level_2_vectorName;
	}

	public Integer getLevel_2_vectorLength() {
		return level_2_vectorLength_;
	}

	public void setLevel_2_vectorLength(Integer level_2_vectorLength) {
		level_2_vectorLength_ = level_2_vectorLength;
	}

	public Level_2_Protocol getLevel_2_protocol() {
		return level_2_protocol_;
	}

	public void setLevel_2_protocol(Level_2_Protocol level_2_protocol) {
		level_2_protocol_ = level_2_protocol;
	}

	public Counter getNoOfProcessedTUs() {
		return noOfProcessedTUs_;
	}

	public void setNoOfProcessedTUs(Counter noOfProcessedTUs) {
		noOfProcessedTUs_ = noOfProcessedTUs;
	}

	public String getHost() {
		return host_;
	}

	public void setHost(String host) {
		host_ = host;
	}

	public String getProtocol() {
		return protocol_;
	}

	public void setProtocol(String protocol) {
		protocol_ = protocol;
	}

	public int getServerPort() {
		return serverPort_;
	}

	public void setServerPort(int serverPort) {
		serverPort_ = serverPort;
	}

	public AssemblXWebEnumTranslator getAssemblXWebEnumTranslator() {
		return assemblXWebEnumTranslator_;
	}

	public void setAssemblXWebEnumTranslator(
			AssemblXWebEnumTranslator assemblXWebEnumTranslator) {
		assemblXWebEnumTranslator_ = assemblXWebEnumTranslator;
	}

	public String getHelpAction() {
		return helpAction_;
	}

	public void setHelpAction(String helpAction) {
		helpAction_ = helpAction;
	}

	public Integer getNumberOfProcessableTUs() {
		Integer numberOfProcessableTUs = 0;
		for (Module currentModule : modules_) {
			for (TranscriptionalUnit currentTuUnit : currentModule.getTranscriptionalUnits()) {
				if (currentTuUnit.isEvaluable()) {
					numberOfProcessableTUs ++;
				}
			}
		}
		return numberOfProcessableTUs;
	}
	
	public Map<String, String> getMasterPlasmidMap() {
		return masterPlasmidMap_;
	}

	public void setMasterPlasmidMap(Map<String, String> masterPlasmidMap) {
		masterPlasmidMap_ = masterPlasmidMap;
	}

	public Counter getEvaluableTUCounter() {
		return evaluableTUCounter_;
	}

	public void setEvaluableTUCounter(Counter evaluableTUCounter) {
		evaluableTUCounter_ = evaluableTUCounter;
	}

	public Boolean getShowHelpTextOverview() {
		return showHelpTextOverview_;
	}

	public void setShowHelpTextOverview(Boolean showHelpTextOverview) {
		showHelpTextOverview_ = showHelpTextOverview;
	}

	public Boolean getShowHelpTextEditModule() {
		return showHelpTextEditModule_;
	}

	public void setShowHelpTextEditModule(Boolean showHelpTextEditModule) {
		showHelpTextEditModule_ = showHelpTextEditModule;
	}

	public Boolean getHasReadDisclaimer() {
		return hasReadDisclaimer_;
	}

	public void setHasReadDisclaimer(Boolean hasReadDisclaimer) {
		hasReadDisclaimer_ = hasReadDisclaimer;
	}

	private Boolean enterJ5AccountCredentials_ = false;
	
	// j5 credentials - hardcoded strings will be removed as soon as the user handling has been implemented
	private OperatorRecord operator_;
	private Boolean userAuthenticated_ = false;
	private String j5SessionId_ = "";
	private String workingDirectory_;
	private String servletContextRealPath_;
	//stores masterplasmidlist.csv, masteroligolist.csv and masterdirectsynthesislist.csv
	private String pathToMasterFilesDirectory_;
	
	private String submitJobErrorMessage_;

	private List<Module> modules_;
	
	private List<String> editModes_;
	private String editMode_ = "add/remove units";
	// the transcriptional unit which is edited just now
	private TranscriptionalUnit currentTuUnit_;
	//maps and lists, needed for filling drop down menues
	private Map<TuSubunitType, String> tuSubunitTypes_;
	private Map<TuSubunitAssemblyStrategy, String> tuSubunitAssemblyStrategies_;
	private Map<TuSubunit.TuSubunitDirection, String> tuSubunitDirections_;
	private Map<TuSubunit.TuSubunitSequenceOption, String> sequenceOptions_;
	private Map<Module.CopyVariant, String> copyVariants_;
	private Map<SequenceInputOption, String> tuSubunitSequenceInputOptions_;
		
	private Module.CopyVariant level_2_copyVariant_ = CopyVariant.LOW;
	private String finalLevel_2_Construct_;
	private String level_2_vectorName_;
	private Integer level_2_vectorLength_;
	
	private Level_2_Protocol level_2_protocol_;
	
	private Counter noOfProcessedTUs_;
	
	//this list is needed to implement a workaround for writing the master plasmid list
	//private List<String> masterPlasmidList_;
	private Map<String, String> masterPlasmidMap_;
	
	private Counter evaluableTUCounter_;
	
	//things from servlet request - this might be an ugly workaround
	private String host_;
	private String protocol_;
	private int serverPort_;
	
	//key for the action for which the help page has been called - needed for return to this action
	private String helpAction_;
	private Boolean showHelpTextOverview_ = true;
	private Boolean showHelpTextEditModule_ = true;
	
	private Boolean hasReadDisclaimer_ = false;
	
	private AssemblXWebEnumTranslator assemblXWebEnumTranslator_;
}
