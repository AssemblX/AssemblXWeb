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
			EditTUAction.submit();
		}
	</script>
	<s:form action="EditTUAction" method="post"
		enctype="multipart/form-data" theme="simple">
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
						<td><h3>
								<s:text name="label.editAssemblyUnit" />
								<s:property value="currentTuUnit.name" />
							</h3></td>
					</tr>
					<s:if test="showHelpTextEditModule == true">
						<tr>
							<td colspan="5"><div class="help">
									<h3>Workflow</h3>
									<ul>
										<li>Design the Level 0 assembly unit.</li>
										<li>Define the number of subunits for every Level 0 unit
											by clicking the <q>add subunit</q> button.
										</li>
										<li>Define the <q>type</q> of each subunit by selecting
											from the list.
										</li>
										<li>Upload a GenBank file containing the sequence
											information for a given subunit or select <q>paste as
												text</q> to copy and paste as plain text. Define the position of
											the subunit within the sequence file or select <q>whole
												sequence</q> in case the complete sequence information shall be used.
											If the sequence file contains your desired part on the
											reverse complementary strand activate the <q>reverse
												complement</q> checkbox.
										</li>
										<li>Define the relative orientation of the subunit within
											the assembly unit by selecting <q>forward</q> or <q>reverse</q>.
											Note: This is independent of the aforementioned orientation
											of your part within the uploaded sequence. Selecting <q>reverse</q>
											will result into reverse incorporation of the selected
											subunit in the assembly.
										</li>
										<li>You may either choose a preferred assembly strategy (<q>forced
												assembly strategy</q>) or let the tool determine the most
											efficient approach.
										</li>
										<li>When all subunits are defined, you can generate a
											mock assembly by selecting <q>Generate mock</q> and check the resulting
											vector map by clicking <q>Open Vector Editor</q>.
										</li>
										<li>When satisfied with your design, check
											the <q>Editing finished</q> box and go <q>Back to
												overview</q> to proceed with the next assembly unit.
										</li>
									</ul>
								</div></td>
						</tr>
						<tr>
							<td colspan="5"><s:submit type="button" value="hideHelpText"
									name="buttonName">
									<s:text name="label.hideHelpText" />
								</s:submit></td>
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
								</s:submit></td>
						</tr>
					</s:else>
					<tr>
						<td>
							<table>
								<tr>
									<s:iterator value="currentTuUnit.tuSubunits"
										status="tuSubunitStatus">
										<td class="very_light_grey">
											<table>
												<tr>
													<s:if test="typeName == 'backbone'">
														<td colspan="3"><img alt="transcriptional subunit"
															src="<s:property value="imageSource"/> "
															title="The backbone is automatically  set based on the position in the final assembly">
														</td>
													</s:if>
													<s:else>
														<td colspan="3"><img alt="transcriptional subunit"
															src="<s:property value="imageSource"/>"></td>
													</s:else>

												</tr>
												<s:if test="typeName != 'backbone'">
													<tr>
														<td colspan="3"><s:text name="label.type" /> <s:select
																list="tuSubunitTypes"
																name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].type"
																listValue="value" listKey="key"
																onchange="triggerChanges()"
																title="Select the type of subunit"
																disabled="currentTuUnit.editingFinished == true" /></td>
													</tr>
													<tr>
														<td><s:radio list="tuSubunitSequenceInputOptions"
																name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].sequenceInputOption"
																onchange="triggerChanges()"
																disabled="currentTuUnit.editingFinished == true" /></td>
													</tr>
													<s:if test="sequenceInputOptionName == 'load from file'">
														<tr>
															<td colspan="3"><s:file name="sequenceFile"
																	theme="simple"
																	title="Browse local file directories to upload genbank files"
																	disabled="currentTuUnit.editingFinished == true" /></td>
														</tr>
														<tr>
															<td colspan="3"><s:submit type="button"
																	name="buttonName"
																	value="loadSequenceFile_%{getIdentifier()}"
																	disabled="currentTuUnit.editingFinished == true">
																	<s:text name="label.loadSequenceFile" />
																</s:submit></td>
														</tr>
													</s:if>
													<s:else>
														<tr>
															<td><s:textarea
																	name="currentTuUnit.tuSubunits[%{#tuSubunitStatus.index}].pureRawSequence"
																	cols="50"
																	disabled="currentTuUnit.editingFinished == true" /></td>
														</tr>
													</s:else>
												</s:if>
												<s:if test="sequenceInputOptionName == 'load from file'">
													<s:include value="EditTUDetailsLoadFromFile.jsp" />
												</s:if>
												<s:else>
													<s:include value="EditTUDetailsPasteSequence.jsp" />
												</s:else>
												<tr>
													<td><s:fielderror cssClass="errorText">
															<s:param value="%{getIdentifier()}" />
														</s:fielderror></td>
												</tr>
												<s:if test="typeName != 'backbone'">
													<tr>
														<td colspan="3"><s:submit type="button"
																name="buttonName" value="delete_%{getIdentifier()}"
																disabled="currentTuUnit.editingFinished == true">
																<s:text name="label.delete" />
															</s:submit></td>
													</tr>
												</s:if>
											</table>
										</td>
									</s:iterator>
									<td><s:submit type="button" value="addSubunit"
											name="buttonName" title="Add an additional subunit"
											disabled="currentTuUnit.editingFinished == true">
											<s:text name="label.addSubunitAtPosition" />
										</s:submit> <s:select list="currentTuUnit.tuSubunitPositions"
											name="insertTuPosition" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td><s:submit type="button" value="generateMock"
											name="buttonName"
											title="Generate mock assembly to verify the desired design.">
											<s:text name="label.generateMock" />
										</s:submit></td>
									<td><s:submit type="button" value="backToOverview"
											name="buttonName" title="Return to the assembly overview.">
											<s:text name="label.backToOverview" />
										</s:submit></td>
								</tr>
								<s:if test="mockPresent == true">
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="2" class="bold"><s:text
												name="Mock assembly finished. " /> <s:a
												href="../VectorEditor.html" target="_blanc"
												title="Click to view the mock assembly in vector editor.">
												<s:text name="label.clickHere" />
											</s:a> <s:text name="label.toViewTheResultInEditor" /></td>
									</tr>
								</s:if>
								<tr>
									<td class="errorText"><s:if
											test="currentTuUnit.j5FaultString != ''">
											<s:text name="label.j5FaultString" />
											<s:property value="currentTuUnit.j5FaultString" />
										</s:if></td>
									<td class="errorText"><s:if
											test="currentTuUnit.j5ErrorMessage != ''">
											<s:text name="label.j5ErrorMessage" />
											<s:property value="currentTuUnit.j5ErrorMessage" />
										</s:if></td>
								</tr>
								<tr>
									<td class="errorText"><s:actionerror /></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>

									<td colspan="2"><s:checkbox
											name="currentTuUnit.editingFinished"
											onchange="triggerChanges()" /> <s:text
											name="label.editingFinished" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:else>
		</s:else>
	</s:form>
</body>