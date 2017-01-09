<!-- /*
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
*/ -->
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<tr>
	<td colspan="3"><s:text name="label.partName" /> <s:textfield
			name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].partName"
			title="Name for the subunit. Has to be unique within the transcriptional unit."
			disabled="typeName == 'backbone' || currentTuUnit.editingFinished == true" /></td>
</tr>
<s:if test="typeName != 'backbone'">
	<tr>
		<td colspan="3"><s:checkbox
				name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].isReverseComplement"
				title="Check box, if the desired part is coded on the reverse complementary strand in your sequence file."
				disabled="currentTuUnit.editingFinished == true" /> <s:text
				name="reverse complement" /></td>
	</tr>
	<tr>
		<td colspan="3"><s:text name="label.forwardReverse" /> <s:select
				list="tuSubunitDirections"
				name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].direction"
				listValue="value" listKey="key" onchange="triggerChanges()"
				title="Select the relative orientation of the subunit in the final assembly unit."
				disabled="currentTuUnit.editingFinished == true" /></td>
	</tr>
	<tr>
		<td colspan="3"><s:text name="label.forcedAssemblyStrategy" /> <s:select
				list="tuSubunitAssemblyStrategies" headerKey="%{NO_STRATEGY}"
				name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].forcedAssemblyStrategy"
				listValue="value" listKey="key"
				title="Force a given assembly strategy or select 'automatic' for automatic selection."
				disabled="currentTuUnit.editingFinished == true" /></td>
	</tr>
</s:if>