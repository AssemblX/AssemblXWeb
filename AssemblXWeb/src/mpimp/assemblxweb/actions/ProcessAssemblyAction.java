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
package mpimp.assemblxweb.actions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.util.AssemblXException;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.Counter;
import mpimp.assemblxweb.util.J5FileUtils;
import mpimp.assemblxweb.util.Level_1_ProtocolCreator;
import mpimp.assemblxweb.util.Level_2_ProtocolCreator;
import mpimp.assemblxweb.util.ProtocolWriter;

public class ProcessAssemblyAction extends AbstractAssemblXAction
		implements ScopedModelDriven<AssemblXWebModel>, SessionAware {

	private static final long serialVersionUID = -8524194026094354005L;

	@Override
	public String execute() {
		if (buttonName_.equals("goToLogin")) {
			return "logout";
		}
		if (buttonName_.equals("processAssembly") && assemblXWebModel_.getOperator().getIsTestOperator() == false) {
			try {
				processAssembly();
				return SUCCESS;
			} catch (Exception e) {
				addActionError(e.getMessage());
				return INPUT;
			}
		} else {
			return INPUT;
		}
	}

	public void setButtonName(String buttonName) {
		buttonName_ = buttonName;
	}

	public String getDownloadUrl() {
		return downloadUrl_;
	}

	public Boolean getResultPresent() {
		return resultPresent_;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		session_ = session;
	}

	private void processAssembly() throws Exception {
		downloadUrl_ = "";
		resultPresent_ = false;
		generateLevel_2_Protocol();
		resultPresent_ = true;
	}

	private void generateLevel_1_Protocol(Module module, ProtocolWriter protocolWriter) throws Exception {
		Level_1_ProtocolCreator protocolCreator = new Level_1_ProtocolCreator(module, assemblXWebModel_);
		protocolCreator.createLevel_1_Protocol(protocolWriter);
	}

	private void generateLevel_2_Protocol() throws Exception {
		ProtocolWriter protocolWriter = new ProtocolWriter(assemblXWebModel_);
		protocolWriter.prepareLevel_0_Protocol();// writes the overall header to
													// the level 0 protocol
													// sheet
		assemblXWebModel_.setEvaluableTUCounter(new Counter(1));
		List<Module> modules = assemblXWebModel_.getModules();
		assemblXWebModel_.setNoOfProcessedTUs(new Counter(0));
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				try {
					generateLevel_1_Protocol(currentModule, protocolWriter);
				} catch (Exception e) {
					String message = "Error during processing of module " + currentModule.getName() + ". "
							+ e.getMessage();
					throw new AssemblXException(message, this.getClass());
				}
			}
		}
		try {
			protocolWriter.appendLevel_1_Sheet();
		} catch (Exception e) {
			String message = "Error while appending level 1 protocol sheet. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		List<Module> activeModules = new ArrayList<Module>();
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				activeModules.add(currentModule);
			}
		}

		if (activeModules.size() > 1) {
			Level_2_ProtocolCreator level_2_protocolCreator = new Level_2_ProtocolCreator(assemblXWebModel_);
			try {
				level_2_protocolCreator.createLevel_2_Protocol();
			} catch (Exception e) {
				String message = "Error during generation of level 2 protocol. " + e.getMessage();
				throw new AssemblXException(message, this.getClass());
			}
			try {
				protocolWriter.appendLevel_2_Sheet();
			} catch (Exception e) {
				String message = "Error during writing of level 2 protocol sheet. " + e.getMessage();
				throw new AssemblXException(message, this.getClass());
			}
		}
		try {
			protocolWriter.writeProtocolToFile();
		} catch (Exception e) {
			String message = "Error during writing of protocol to file. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			copyMasterFilesToResultDir();
		} catch (Exception e) {
			String message = "Error while copying master files to result directory. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			copySupportProtocolToResultDir();
		} catch (Exception e) {
			String message = "Error while copying support protocol file to result directory. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			downloadTempDirPath_ = J5FileUtils.createDownloadTempDir(assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error while creating path to temporary download directory. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			zipResults();
		} catch (Exception e) {
			String message = "Error while creating results zip archive. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
		try {
			downloadUrl_ = J5FileUtils.createDownloadUrl(assemblXWebModel_, getResultFilePathForDownload());
		} catch (Exception e) {
			String message = "Error while creating download URL. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void copyMasterFilesToResultDir() throws Exception {
		String resultDirectoryPath = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory");
		String masterFilesDirectoryPath = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterFilesDirectory");

		// String masterPlasmidListPath = masterFilesDirectoryPath +
		// File.separator
		// +
		// AssemblXWebProperties.getInstance().getProperty("masterPlasmidsListFileName");
		String masterOligoListPath = masterFilesDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterOligosListFileName");
		String masterDirectSynthesisPath = masterFilesDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterDirectSynthesisListFileName");

		String masterPlasmidListPathResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterPlasmidsListFileName");
		String masterOligoListPathResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterOligosListFileName");
		String masterDirectSynthesisListPathResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterDirectSynthesisListFileName");

		/*
		 * The following step (copying the master plasmid file) has been
		 * inactivated due to an unknown error which prevents the plasmid list
		 * from being completed as expected (and replaced by a workaround). It
		 * should be reactivated as soon as the error has been detected.
		 */
		// Files.copy(Paths.get(masterPlasmidListPath),
		// Paths.get(masterPlasmidListPathResult),
		// StandardCopyOption.REPLACE_EXISTING);
		// ################ workaround #####################################
		writeFinalMasterPlasmidListFile(masterPlasmidListPathResult);
		// #################################################################
		Files.copy(Paths.get(masterOligoListPath), Paths.get(masterOligoListPathResult),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(Paths.get(masterDirectSynthesisPath), Paths.get(masterDirectSynthesisListPathResult),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private void copySupportProtocolToResultDir() throws Exception {
		String supportProtocolInResultDirPath = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory") + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("supportProtocolName");
		String supportProtocolPath = assemblXWebModel_.getServletContextRealPath() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("pdfDirName") + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("supportProtocolName");
		Files.copy(Paths.get(supportProtocolPath), Paths.get(supportProtocolInResultDirPath),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private void writeFinalMasterPlasmidListFile(String filePath) throws Exception {
		List<String> content = new ArrayList<String>();
		content.add("Plasmid Name,Alias,Contents,Length,Sequence");
		for (Module currentModule : assemblXWebModel_.getModules()) {
			List<TranscriptionalUnit> unitsOfCurrentModule = currentModule.getTranscriptionalUnits();
			for (TranscriptionalUnit currentTuUnit : unitsOfCurrentModule) {
				if (currentTuUnit.isEvaluable()) {
					content.add(currentTuUnit.getPlasmidName()
							+ assemblXWebModel_.getMasterPlasmidMap().get(currentTuUnit.getIdentifier()));
				}
			}
		}
		Path path = Paths.get(filePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			String message = "Error during writing of j5 plasmids list file: " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private String getResultFilePathForDownload() {
		String filePath = "/" + AssemblXWebProperties.getInstance().getProperty("downloadTempDirBaseName") + "/"
				+ assemblXWebModel_.getOperator().getLogin() + "/" + assemblXWebModel_.getOperator().getLogin()
				+ ".zip";
		return filePath;
	}

	private String getResultZipFilePath() {
		String filePath = downloadTempDirPath_ + File.separator + assemblXWebModel_.getOperator().getLogin() + ".zip";
		return filePath;
	}

	private void zipResults() throws Exception {
		String resultDirectoryPath = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory");
		J5FileUtils.zipDirectory(resultDirectoryPath, getResultZipFilePath());
	}

	private String buttonName_ = "";
	private String downloadUrl_ = "";
	private String downloadTempDirPath_;
	private Boolean resultPresent_ = false;
	private Map<String, Object> session_;

}
