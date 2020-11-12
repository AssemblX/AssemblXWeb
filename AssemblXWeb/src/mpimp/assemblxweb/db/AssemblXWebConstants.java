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

import java.util.HashMap;
import java.util.Map;

import mpimp.assemblxweb.db.TranscriptionalUnit.AssemblyMethod;

public class AssemblXWebConstants {

	private static Map<AssemblyMethod,String> assemblyMethods_;
	
	static {
		assemblyMethods_ = new HashMap<AssemblyMethod,String>();
		assemblyMethods_.put(AssemblyMethod.SLIC_Gibson_CPEC, "SLIC/Gibson/CPEC");
		assemblyMethods_.put(AssemblyMethod.Combinatorial_SLIC_Gibson_CPEC, "Combinatorial SLIC/Gibson/CPEC");
		assemblyMethods_.put(AssemblyMethod.Mock_Assembly, "Mock");
		assemblyMethods_.put(AssemblyMethod.Golden_Gate, "Golden Gate");
		assemblyMethods_.put(AssemblyMethod.Combinatorial_Golden_Gate, "Combinatorial Golden Gate");
		assemblyMethods_.put(AssemblyMethod.Combinatorial_Mock_Assembly, "Combinatorial Mock Assembly");
	}
	
	public static final String PROPERTIES_FILE_NAME = "AssemblXWebProperties.xml";
	
	public static String getAssemblyMethod(AssemblyMethod assemblyMethod) {
		return assemblyMethods_.get(assemblyMethod);
	}
}
