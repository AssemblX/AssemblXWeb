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

import java.lang.reflect.Method;
import java.util.Map;

import mpimp.assemblxweb.db.AssemblXWebModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.PrepareInterceptor;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

public class PrepareInterceptorAssemblX extends PrepareInterceptor {

	private static final long serialVersionUID = -1915494776875256037L;

	@Override
	@SuppressWarnings("unchecked")
	public String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof ScopedModelDriven) {
			ScopedModelDriven<AssemblXWebModel> scopedModelDriven = (ScopedModelDriven) action;
			ActionContext ctx = ActionContext.getContext();
			ActionConfig config = invocation.getProxy().getConfig();

			String cName = className;
			if (cName == null) {
				try {
					Method method = action.getClass().getMethod(GET_MODEL,
							new Class[0]);
					Class cls = method.getReturnType();
					cName = cls.getName();
				} catch (NoSuchMethodException e) {
					throw new XWorkException("The " + GET_MODEL
							+ "() is not defined in action "
							+ action.getClass() + "", config);
				}
			}
			String modelName = name;
			if (modelName == null) {
				modelName = cName;
			}

			Map scopeMap = ctx.getContextMap();
			if ("session".equals(scope)) {
				scopeMap = ctx.getSession();
			}
			Object model = scopeMap.get(modelName);
			// check if there is already a suitable model in the session - 
			// specified by the settings from the struts.xml configuration file
			if (model != null && model.getClass().getName().equals(className)) {
				scopedModelDriven.setModel((AssemblXWebModel) model);
				return invocation.invoke();
			} else {
				// no suitable model present - we have to create a new one and
				// fill it with default values
				return super.doIntercept(invocation);
			}
		} else {
			// we let the workflow go on, but we don't need to take care
			// to any models
			return super.doIntercept(invocation);
		}
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private String scope;
	private String name;
	private String className;
	private static final String GET_MODEL = "getModel";
}
