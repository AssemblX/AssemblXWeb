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
<!DOCTYPE HTML>
<HTML>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1" />
</head>
<body>
	<script type="text/javascript">
		function triggerChanges() {
			AssemblXWebLoginAction.submit();
		}
	</script>
	<s:form action="AssemblXWebLoginAction" theme="simple">
		<table>
			<tr>
				<td colspan="3"><s:text name="label.assemblxLogin" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><s:text name="label.userName" /></td>
				<td><s:textfield name="operator.login" /></td>
				<td><s:fielderror fieldName="Username" cssClass="errorText" />
			</tr>
			<tr>
				<td><s:text name="label.password" /></td>
				<td><s:password name="operator.password" /></td>
				<td><s:fielderror fieldName="Password" cssClass="errorText" /></td>
			</tr>
			<tr>
				<td colspan="3"><s:actionerror cssClass="errorText" /></td>
			</tr>
			<tr>
				<td><s:checkbox name="hasReadDisclaimer" onchange="triggerChanges()"/>
					<s:text name="label.haveReadDisclaimer" /><a
					href="../html/Disclaimer.html" target="_blank"><s:text name="label.disclaimer" /></a></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><s:submit type="button" value="login" name="buttonName" disabled="hasReadDisclaimer == false">
						<s:text name="label.login" />
					</s:submit> <s:submit type="button" value="test" name="buttonName"
						title="Click here to try out the user interface. You will not be able to submit any job.">
						<s:text name="label.testWithoutLogin" />
					</s:submit></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><s:submit type="button" value="home" name="buttonName"><s:text name="label.home" /></s:submit>
				</td>
			</tr>
		</table>
	</s:form>
</body>