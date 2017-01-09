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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.OperatorRecord;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.EditingStage;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.db.TuSubunit;
import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;
import mpimp.assemblxweb.util.AssemblXException;
import mpimp.assemblxweb.util.AssemblXWebEnumTranslator;
import mpimp.assemblxweb.util.AssemblXWebProperties;
import mpimp.assemblxweb.util.J5Caller;
import mpimp.assemblxweb.util.J5FileUtils;
import mpimp.assemblxweb.util.J5InputFileWriter;

public class AssemblXWebLoginAction extends AbstractAssemblXAction implements Preparable,
		ScopedModelDriven<AssemblXWebModel>, ModelDriven<AssemblXWebModel>, ServletRequestAware, ServletContextAware {

	private static final long serialVersionUID = -8830380346941391023L;

	@Override
	public String execute() {
		if (buttonName_.equals("login") && authenticateUser()) {
			buttonName_ = "";
			try {
				initAfterLogin();
			} catch (Exception e) {
				addActionError(e.getMessage());
				return INPUT;
			}
			return SUCCESS;
		} else if (buttonName_.equals("home")) {
			return "home";
		} else if (buttonName_.equals("test")) {
			buttonName_ = "";
			try {
				initTestOperator();
				initAfterLogin();
			} catch (Exception e) {
				addActionError(e.getMessage());
				return INPUT;
			}
			return SUCCESS;
		} else {
			return INPUT;
		}
	}

	@Override
	public void prepare() throws Exception {
		if (assemblXWebModel_ == null) {
			AssemblXWebModel model = new AssemblXWebModel();
			this.setModel(model);
			assemblXWebModel_.setServletContextRealPath(servletContext_.getRealPath("/"));
			String propertiesPath = assemblXWebModel_.getServletContextRealPath() + File.separator + "WEB-INF"
					+ File.separator + "classes" + File.separator + AssemblXWebConstants.PROPERTIES_FILE_NAME;

			AssemblXWebProperties.setPropertiesPath(propertiesPath);
			AssemblXWebProperties.reloadProperties();
			if (AssemblXWebProperties.getPropertiesLoaded() == false) {
				throw new AssemblXException("Unable to load properties from file.", this.getClass());
			}
			// this is needed for fetching user credentials when creating a new
			// account
			assemblXWebModel_.setOperator(new OperatorRecord());
		}
	}

	private Boolean authenticateUser() {
		Boolean userPresent = false;
		Boolean passwordPresent = false;
		if (assemblXWebModel_.getOperator().getPassword().length() == 0) {
			addFieldError("Password", "password required");
		} else {
			passwordPresent = true;
		}
		if (assemblXWebModel_.getOperator().getLogin().length() == 0) {
			addFieldError("Username", "login name required");
		} else {
			userPresent = true;
		}
		if (!userPresent || !passwordPresent) {
			return false;
		}
		try {
			J5Caller.callGetSessionId(assemblXWebModel_);
			if (!assemblXWebModel_.getJ5SessionId().equals("")) {
				assemblXWebModel_.setUserAuthenticated(true);
			}
			return true;
		} catch (Exception e) {
			addActionError(e.getMessage());
			return false;
		}
	}

	private void initAfterLogin() throws Exception {
		assemblXWebModel_.setWorkingDirectory(assemblXWebModel_.getServletContextRealPath() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("workingDirectory") + File.separator
				+ assemblXWebModel_.getOperator().getLogin());
		setEnumTranslator();
		cleanWorkingDirectory();
		prepareResultsDirectory();
		fillModelWithModules();
		prepareMasterFilesDirectory();
		prepareTuSubunitTypes();
		prepareTuSubunitDirections();
		prepareTuSubunitAssemblyStrategies();
		prepareSequenceOptions();
		prepareCopyVariants();
		prepareSequenceInputOptions();
		extractParametersFromServletRequest();
	}

	private void initTestOperator() {
		assemblXWebModel_.getOperator().setLogin("test_user");
		assemblXWebModel_.getOperator().setIsTestOperator(true);
		assemblXWebModel_.setUserAuthenticated(true);
	}

	public void setButtonName(String buttonName) {
		buttonName_ = buttonName;
	}

	public Boolean getEnterJ5AccountCredentials() {
		return enterJ5AccountCredentials_;
	}

	private void extractParametersFromServletRequest() {
		assemblXWebModel_.setHost(httpServletRequest_.getLocalName());
		assemblXWebModel_.setProtocol(httpServletRequest_.getScheme());
		assemblXWebModel_.setServerPort(httpServletRequest_.getServerPort());
	}

	private void prepareMasterFilesDirectory() throws Exception {
		String pathToMasterFilesDirectory = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterFilesDirectory");
		File masterFilesDirectory = new File(pathToMasterFilesDirectory);
		masterFilesDirectory.mkdirs();
		assemblXWebModel_.setPathToMasterFilesDirectory(pathToMasterFilesDirectory);
		J5InputFileWriter j5InputFileWriter = new J5InputFileWriter();
		try {
			j5InputFileWriter.writeJ5MasterPlasmidsListFile(assemblXWebModel_);
			j5InputFileWriter.writeJ5MasterOligosListFile(assemblXWebModel_);
			j5InputFileWriter.writeJ5MasterDirectSynthesisListFile(assemblXWebModel_);
		} catch (Exception e) {
			String message = "Error during writing initial j5 master files. " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void cleanWorkingDirectory() throws Exception {
		String pathToWorkingDirectory = assemblXWebModel_.getWorkingDirectory();
		File workingDir = new File(pathToWorkingDirectory);
		try {
			if (workingDir.exists() && workingDir.isDirectory()) {
				J5FileUtils.removeDirRecursively(pathToWorkingDirectory);
			} else {
				workingDir.mkdirs();
			}
		} catch (Exception e) {
			String message = "Error while cleaning working directory " + pathToWorkingDirectory + ". " + e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void prepareResultsDirectory() throws Exception {
		String pathToResultsDirectory = assemblXWebModel_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory");
		File resultDir = new File(pathToResultsDirectory);
		try {
			if (resultDir.exists() && resultDir.isDirectory()) {
				J5FileUtils.removeDirRecursively(pathToResultsDirectory);
			} else {
				resultDir.mkdirs();
			}
		} catch (Exception e) {
			String message = "Error while preparing results directory " + pathToResultsDirectory + ". "
					+ e.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void fillModelWithModules() throws Exception {
		List<Module> modules = new ArrayList<Module>();
		modules.add(createModule("A"));
		modules.add(createModule("B"));
		modules.add(createModule("C"));
		modules.add(createModule("D"));
		modules.add(createModule("E"));
		this.assemblXWebModel_.setModules(modules);
		// this shall show to the user that modules can be activated
		modules.get(0).getTranscriptionalUnits().get(0).setActive(true);
		modules.get(0).getTranscriptionalUnits().get(0).setEditingStage(EditingStage.NEW);
		modules.get(0).setBackbones(assemblXWebModel_);
	}

	private Module createModule(String name) throws Exception {
		Module module = new Module();
		module.setName(name);
		List<TranscriptionalUnit> tuUnits = new ArrayList<TranscriptionalUnit>();
		for (int i = 0; i < 5; i++) {
			int number = i + 1;
			TranscriptionalUnit tuUnit = new TranscriptionalUnit(module, number, assemblXWebModel_);
			tuUnit.setName(name + number);
			tuUnit.setOrientation(TUOrientation.SENSE);
			tuUnit.setParentModule(module);
			tuUnit.setPosition(number);
			tuUnit.setTuDirectoryName(name + number);
			tuUnits.add(tuUnit);
		}
		module.setTranscriptionalUnits(tuUnits);
		return module;
	}

	private void setEnumTranslator() {
		AssemblXWebEnumTranslator enumTranslator = new AssemblXWebEnumTranslator();
		assemblXWebModel_.setAssemblXWebEnumTranslator(enumTranslator);
	}

	private void prepareTuSubunitTypes() {
		Map<TuSubunitType, String> tuSubunitTypes = new HashMap<TuSubunitType, String>();
		tuSubunitTypes.put(TuSubunitType.PROMOTER, "promoter");
		tuSubunitTypes.put(TuSubunitType.CODING_SEQUENCE, "CDS");
		tuSubunitTypes.put(TuSubunitType.TERMINATOR, "terminator");
		tuSubunitTypes.put(TuSubunitType.RES, "RES");
		tuSubunitTypes.put(TuSubunitType.USER_DEFINED, "user_defined");
		tuSubunitTypes.put(TuSubunitType.OPERATOR, "operator");
		tuSubunitTypes.put(TuSubunitType.INSULATOR, "insulator");
		tuSubunitTypes.put(TuSubunitType.ORIGIN_OF_REPLICATION, "origin_of_replication");
		tuSubunitTypes.put(TuSubunitType.RESTRICTION_SITE, "restriction_site");
		tuSubunitTypes.put(TuSubunitType.SIGNATURE, "signature");
		this.assemblXWebModel_.setTuSubunitTypes(tuSubunitTypes);
	}

	private void prepareTuSubunitDirections() {
		Map<TuSubunitDirection, String> tuSubunitDirections = new HashMap<TuSubunitDirection, String>();
		tuSubunitDirections.put(TuSubunitDirection.FORWARD, "forward");
		tuSubunitDirections.put(TuSubunitDirection.REVERSE, "reverse");
		this.assemblXWebModel_.setTuSubunitDirections(tuSubunitDirections);
	}

	private void prepareTuSubunitAssemblyStrategies() {
		Map<TuSubunitAssemblyStrategy, String> tuSubunitAssemblyStrategies = new HashMap<TuSubunitAssemblyStrategy, String>();
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.ANNEALED_OLIGOS, "Annealed Oligos");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.DIRECT_SYNTHESIS, "Direct Synthesis");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_FORWARD, "Embed_in_primer_forward");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_REVERSE, "Embed_in_primer_reverse");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.PCR, "PCR");
		tuSubunitAssemblyStrategies.put(TuSubunitAssemblyStrategy.NO_STRATEGY, "automatic");
		this.assemblXWebModel_.setTuSubunitAssemblyStrategies(tuSubunitAssemblyStrategies);
	}

	private void prepareSequenceOptions() {
		HashMap<TuSubunit.TuSubunitSequenceOption, String> sequenceOptions = new HashMap<TuSubunit.TuSubunitSequenceOption, String>();
		sequenceOptions.put(TuSubunitSequenceOption.WHOLE_SEQUENCE, "Whole sequence");
		sequenceOptions.put(TuSubunitSequenceOption.SPECIFIED_SEQUENCE, "Specified sequence");
		this.assemblXWebModel_.setSequenceOptions(sequenceOptions);
	}

	private void prepareCopyVariants() {
		Map<Module.CopyVariant, String> copyVariants = new HashMap<Module.CopyVariant, String>();
		copyVariants.put(Module.CopyVariant.LOW, "low");
		copyVariants.put(Module.CopyVariant.HIGH, "high");
		this.assemblXWebModel_.setCopyVariants(copyVariants);
	}

	private void prepareSequenceInputOptions() {
		Map<SequenceInputOption, String> sequenceInputOptions = new HashMap<TuSubunit.SequenceInputOption, String>();
		sequenceInputOptions.put(SequenceInputOption.PASTE_AS_TEXT, "paste as text");
		sequenceInputOptions.put(SequenceInputOption.LOAD_FROM_FILE, "load from file");
		this.assemblXWebModel_.setTuSubunitSequenceInputOptions(sequenceInputOptions);
	}

	private String buttonName_ = "";

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		httpServletRequest_ = httpServletRequest;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext_ = servletContext;
	}

	private Boolean enterJ5AccountCredentials_ = false;

	protected HttpServletRequest httpServletRequest_;
	protected ServletContext servletContext_;
}
