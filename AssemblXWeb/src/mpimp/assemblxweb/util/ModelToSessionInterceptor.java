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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

public class ModelToSessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1950616008110046681L;
	private static final String GET_MODEL = "getModel";
	private String scope;
	private String name;
	private String className;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();

		if (action instanceof ScopedModelDriven) {
			ScopedModelDriven modelDriven = (ScopedModelDriven) action;
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

			Object model = modelDriven.getModel();
			Map scopeMap = ctx.getContextMap();
			if ("session".equals(scope)) {
				scopeMap = ctx.getSession();
			}
			scopeMap.put(modelName, model);
		}
		return invocation.invoke();
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

}
