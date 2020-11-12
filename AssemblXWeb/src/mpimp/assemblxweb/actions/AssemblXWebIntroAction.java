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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.OperatorRecord;
import mpimp.assemblxweb.util.AssemblXException;
import mpimp.assemblxweb.util.AssemblXWebProperties;

public class AssemblXWebIntroAction extends AbstractAssemblXAction implements ScopedModelDriven<AssemblXWebModel>,
		ModelDriven<AssemblXWebModel>, Preparable, ServletRequestAware, ServletContextAware {

	private static final long serialVersionUID = 7752247223812501887L;

	@Override
	public String execute() throws Exception {
		if (buttonName_.equals("login")) {
			return "login";
		} else {
			if (buttonName_.equals("help_1")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = true;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("help_2")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = true;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("help_3")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = true;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("help_1_hide")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("help_2_hide")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("help_3_hide")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("webtool")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("home")) {
				home_ = true;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("paper")) {
				home_ = false;
				paper_ = true;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("manual")) {
				home_ = false;
				paper_ = false;
				manual_ = true;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("promoterLibrary")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = true;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("cell2Fab")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = true;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("faq")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = true;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("testimonials")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = true;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("seqFiles")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = true;
				pleaseCite_ = false;
			} else if (buttonName_.equals("pleaseCite")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = false;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = true;
			} else if (buttonName_.equals("showAlgorithm")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = true;
				seqFiles_ = false;
				pleaseCite_ = false;
			} else if (buttonName_.equals("hideAlgorithm")) {
				home_ = false;
				paper_ = false;
				manual_ = false;
				promoterLibrary_ = false;
				cell2fab_ = false;
				faq_ = false;
				testimonials_ = false;
				welcomeWebTool_ = true;
				help_1_ = false;
				help_2_ = false;
				help_3_ = false;
				showAlgorithm_ = false;
				seqFiles_ = false;
				pleaseCite_ = false;
			}
			return INPUT;
		}
	}
	
	public void setButtonName(String buttonName) {
		buttonName_ = buttonName;
	}

	public Boolean getHome() {
		return home_;
	}

	public Boolean getPaper() {
		return paper_;
	}

	public Boolean getManual() {
		return manual_;
	}

	public Boolean getPromoterLibrary() {
		return promoterLibrary_;
	}

	public Boolean getCell2fab() {
		return cell2fab_;
	}

	public Boolean getFaq() {
		return faq_;
	}

	public Boolean getTestimonials() {
		return testimonials_;
	}
	
	public Boolean getSeqFiles() {
		return seqFiles_;
	}

	public Boolean getPleaseCite() {
		return pleaseCite_;
	}
	
	public Boolean getWelcomeWebTool() {
		return welcomeWebTool_;
	}

	public Boolean getHelp_1() {
		return help_1_;
	}

	public Boolean getHelp_2() {
		return help_2_;
	}

	public Boolean getHelp_3() {
		return help_3_;
	}

	public Boolean getShowAlgorithm() {
		return showAlgorithm_;
	}

	@Override
	public void prepare() throws Exception {
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

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		httpServletRequest_ = httpServletRequest;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext_ = servletContext;
	}

	private String buttonName_ = "";

	private Boolean home_ = true;
	private Boolean paper_ = false;
	private Boolean manual_ = false;
	private Boolean promoterLibrary_ = false;
	private Boolean cell2fab_ = false;
	private Boolean faq_ = false;
	private Boolean testimonials_ = false;
	private Boolean seqFiles_ = false;
	private Boolean pleaseCite_ = false;
	private Boolean welcomeWebTool_ = false;
	private Boolean help_1_ = false;
	private Boolean help_2_ = false;
	private Boolean help_3_ = false;
	private Boolean showAlgorithm_ = false;

	protected HttpServletRequest httpServletRequest_;
	protected ServletContext servletContext_;
}
