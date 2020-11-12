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
	<s:form action="AssemblXWebAction" theme="simple">
		<s:if test="model == null">
			<table>
				<tr>
					<td><s:text name="label.noAccessToAssemblX" /></td>
				</tr>
				<tr>
					<td><s:submit type="button" value="goToLogin"
							name="buttonName">
							<s:text name="label.goToLogin" />
						</s:submit></td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<s:if test="userAuthenticated == false">
				<table>
					<tr>
						<td><s:text name="label.noAccessToAssemblX" /></td>
					</tr>
					<tr>
						<td><s:submit type="button" value="goToLogin"
								name="buttonName">
								<s:text name="label.goToLogin" />
							</s:submit></td>
					</tr>
				</table>
			</s:if>
			<s:else>
				<table>
					<tr>
						<td colspan="5" class="light_grey"><nobr>
								<s:text name="label.currentUser" />
								<s:property value="operator.login" />
								<s:submit type="button" value="goToLogin" name="buttonName">
									<s:text name="label.logout" />
								</s:submit>
							</nobr>
					</tr>
					<s:if test="operator.isTestOperator == true">
						<tr>
							<td colspan="5" class="noteForTestUser">You did not login
								with a valid j5 account. So you may try out every feature of the
								user interface but you will not be able to run any assembly job.</td>
						</tr>
					</s:if>
					<s:if test="showHelpTextOverview == true">
						<tr>
							<td colspan="5"><div class="help">
									<h3>Workflow</h3>
									<ul>
										<li>Add the number of Level 0 units needed for your
											assembly and organize Level 0 units into distinct Level 1
											modules A, B, C, D, E via the <q>Add/remove units</q> button.
											Name Level 0 units. Units can only be added/removed
											consecutively.
										</li>
										<li>Change the relative orientation of individual Level 0
											units by activating the <q>Change orientation</q> button and
											clicking on the appropriate arrow in the overview schema.
										</li>
										<li>Select high or low copy backbones for each Level 1
											module incorporated into the assembly and for the final Level
											2 construct.</li>
										<li>Define the detailed architecture (e.g. number of
											subunits, name, type, DNA sequence) of each Level 0 unit by
											activating <q>edit Level 0 assembly unit</q> and clicking on
											the appropriate arrow in the overview schema.
										</li>
										<li>After defining and uploading all subunit sequences,
											create a mock assembly to check your Level 0 design.</li>
										<li>Go back to overview and define the next Level 0 unit.
											The color of the traffic light indicates the status of your
											design.</li>
										<li>After finishing every Level 0 unit submit the
											assembly and download the complete assembly protocol (get a
											coffee, this might take around one minute per Level 0 unit).</li>
									</ul>
									Watch the <a
										href="https://www.youtube.com/channel/UCowwRbGlqX_CMbU_-8uv1OQ"
										target="_blank">video</a> for more details, hints and
									examples.

								</div></td>
						</tr>
						<tr>
							<td colspan="5"><s:submit type="button" value="hideHelpText"
									name="buttonName">
									<s:text name="label.hideHelpText" />
								</s:submit> <s:a href="../../html/OverviewScheme.html" target="_blank">
									<s:text name="label.showOverviewScheme" />
								</s:a></td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="5"><s:submit type="button" value="showHelpText"
									name="buttonName">
									<s:text name="label.showHelpText" />
								</s:submit> <s:a href="../../html/OverviewScheme.html" target="_blank">
									<s:text name="label.showOverviewScheme" />
								</s:a></td>
						</tr>
					</s:else>
					<tr>
						<td colspan="5" class="edit_mode"><div
								title="- Add or remove units by selecting or deselecting arrows.
- Change the orientation of the whole unit by selecting the arrow(s).
- Define subunits of selected arrows.">
								<s:radio list="editModes" name="editMode" />
							</div></td>
					</tr>
					<tr>
						<td class="errorText" colspan="5"><s:actionerror /></td>
					</tr>
					<s:iterator value="modules" status="moduleStatus">
						<tr>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr>
							<td class="bold"><s:text name="label.module" /> <s:property
									value="name" /></td>
							<td class="light_grey" colspan="5"><nobr>
									<s:text name="label.level1HighLowCopy" />
									<s:property value="name" />
									<s:select list="copyVariants"
										name="modules[%{#moduleStatus.index}].copyVariant"
										listValue="value" listKey="key"
										title="Select 2&micro; (high copy) or CEN/ARS (low copy) vector backbone for Level 1 assembly." />
								</nobr></td>
						</tr>
						<tr>
							<s:iterator value="transcriptionalUnits" status="tuStatus">
								<td width="100" class="very_light_grey"><s:text
										name="label.level0unit" /> <s:property value="identifier" />
									<s:textfield
										name="modules[%{#moduleStatus.index}].transcriptionalUnits[%{#tuStatus.index}].name" />
									<nobr>
										<s:submit type="button" value="%{getIdentifier()}"
											name="tuIdentifier">
											<img src="<s:property value="imageSource"/>" width="80px"
												height="71" />
										</s:submit>
										<img src="<s:property value="trafficLightImageSource" />"
											width="23" height="35" />
									</nobr></td>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>
			</s:else>
		</s:else>
	</s:form>
	<s:form action="ProcessAssemblyAction" theme="simple">
		<s:if test="userAuthenticated == true">
			<table width="100%">
				<tr>
					<td class="light_grey">
						<div style="font-weight:bold">
							<s:text name="label.level2HighLowCopy" />
							<s:select list="copyVariants" name="level_2_copyVariant"
								listValue="value" listKey="key"
								title="Select 2µ (high copy) or CEN/ARS (low copy) vector backbone for final Level 2 assembly." />
						</div>
					</td>

					<td class="light_grey"><s:submit type="button"
							name="buttonName" value="processAssembly">
							<s:text name="label.submitJob" />
						</s:submit></td>
					<td class="light_grey" colspan="4">&nbsp;</td>
					<s:if test="resultPresent == true">
						<td class="light_grey"><s:a href="%{downloadUrl}"
								target="_blank">
								<s:text name="label.downloadResult" />
							</s:a></td>
					</s:if>
					<s:else>
						<td class="light_grey">&nbsp;</td>
					</s:else>
				</tr>
			</table>
		</s:if>
	</s:form>
</body>
</html>