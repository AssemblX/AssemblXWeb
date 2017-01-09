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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.OperatorRecord;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.EditingStage;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;

public class AssemblXWebAction extends AbstractAssemblXAction implements ScopedModelDriven<AssemblXWebModel>, SessionAware {

	private static final long serialVersionUID = 6551630055903563772L;

	@Override
	public String execute() {
		if (buttonName_.equals("goToLogin")) {
			return logout();
		}
		if (buttonName_.equals("showHelpText")) {
			assemblXWebModel_.setShowHelpTextOverview(true);
			return INPUT;
		}
		if (buttonName_.equals("hideHelpText")) {
			assemblXWebModel_.setShowHelpTextOverview(false);
			return INPUT;
		}
		String returnValue = INPUT;
		try {
			returnValue = editModuleDesign();
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		return returnValue;
	}

	public void setTuIdentifier(String tuIdentifier) {
		tuIdentifier_ = tuIdentifier;
	}

	public void setButtonName(String buttonName) {
		buttonName_ = buttonName;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		session_ = session;
	}
	
	private String editModuleDesign() throws IOException {
		List<Module> modules = assemblXWebModel_.getModules();
		Module secondLastModule = null;
		for (Module currentModule : modules) {
			List<TranscriptionalUnit> tuUnits = currentModule.getTranscriptionalUnits();
			for (TranscriptionalUnit currentTuUnit : tuUnits) {
				if (currentTuUnit.getIdentifier().equals(tuIdentifier_)) {
					if (assemblXWebModel_.getEditMode().equals("change orientation of a Level 0 assembly unit")
							&& currentTuUnit.getActive() == true) {
						if (currentTuUnit.getOrientation() == TUOrientation.SENSE) {
							currentTuUnit.setOrientation(TUOrientation.ANTI_SENSE);
						} else {
							currentTuUnit.setOrientation(TUOrientation.SENSE);
						}
					} else if (assemblXWebModel_.getEditMode().equals("add/remove units")) {
						// activation is only possible if the current tu-unit is
						// the first in the row
						// or all units to the left are also active - and the
						// other way round for deactivation
						int index = tuUnits.indexOf(currentTuUnit);
						if (currentTuUnit.getActive() == true) {
							if (index == tuUnits.size() - 1 || tuUnits.get(index + 1).getActive() == false) {
								if (index > 0 || currentModule.getIsLastModule() == true) {
									currentTuUnit.setActive(false);
									currentTuUnit.setEditingFinished(false);
									currentTuUnit.setEditingStage(EditingStage.INACTIVE);
									// the TuUnit left to the TuUnit which has
									// been
									// deactivated becomes now the last TuUnit
									if (index >= 1) {
										tuUnits.get(index - 1).setIsLastUnitInModule(true);
									}
									// set backbones - last TU is no longer the
									// last TU
									currentModule.setBackbones(assemblXWebModel_);
									// after deactivating the first TuUnit the
									// module
									// contains no active TuUnits and the second
									// last
									// module becomes the last one
									if (index == 0) {
										if (secondLastModule != null) {
											secondLastModule.setIsLastModule(true);
											currentModule.setIsLastModule(false);
										}
									}
								} else {
									addActionError("Units can only be added/removed consecutively!");
								}
							} else {
								addActionError("Units can only be added/removed consecutively!");
							}
						} else {
							if ((index == 0 || tuUnits.get(index - 1).getActive() == true)
									&& ((secondLastModule != null && secondLastModule.hasActiveUnits())
											|| secondLastModule == null)) {
								currentTuUnit.setActive(true);
								currentTuUnit.setEditingStage(EditingStage.NEW);
								// the TuUnit left to the TuUnit which has been
								// activated is not the last one any longer
								if (index >= 1) {
									tuUnits.get(index - 1).setIsLastUnitInModule(false);
								}
								// set backbones - last TU is no longer the last
								// TU
								currentModule.setBackbones(assemblXWebModel_);
								// we started to activate the first TU in a
								// module
								// so the former last module is no longer the
								// last one
								if (index == 0 && secondLastModule != null) {
									secondLastModule.setIsLastModule(false);
									currentModule.setIsLastModule(true);
								}
							} else {
								addActionError("Units can only be added/removed consecutively!");
							}
						}
					} else if (assemblXWebModel_.getEditMode().equals("edit Level 0 assembly unit")
							&& currentTuUnit.getActive() == true) {
						assemblXWebModel_.setCurrentTuUnit(currentTuUnit);
						return "editTU";
					}
				}
			}
			secondLastModule = currentModule;
		}
		return SUCCESS;
	}

	private String logout() {
		assemblXWebModel_.setOperator(new OperatorRecord());
		if (session_.containsKey("assemblXWebModel")) {
			session_.remove("assemblXWebModel");
			assemblXWebModel_ = null;
		}
		return "logout";
	}

	private String tuIdentifier_ = "";
	private String buttonName_ = "";
	private Map<String, Object> session_;
	
}