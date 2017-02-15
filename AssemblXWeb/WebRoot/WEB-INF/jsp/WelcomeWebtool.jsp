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
<section id="questionsbefore">
	<table>
		<tr>
			<td>The intention of this web tool is to support the user in
				planning and executing multi-part assemblies using the AssemblX
				toolkit. Before starting to plan a new assembly there are just few
				questions you have to consider:
				<ol>
					<li id="QuestionsBefore">How many Level 0 units (e.g. genes)
						do you want to assemble? This determines the number of Level 1
						modules. <s:url action="AssemblXWebIntroAction" id="help1url">
							<s:param name="buttonName">help_1</s:param>
						</s:url> <s:a href="%{help1url}">(read more...)</s:a> <s:if
							test="help_1 == true">
							<div id="help1">
								<br>With AssemblX you can create constructs with up to 25
								Level 0 units. A Level 0 unit may contain multiple subunits and
								can be freely designed. A typical Level 0 unit contains
								promoter, CDS and terminator. However, more subunits may be
								included and any kind of coding or non-coding DNA can be cloned
								into a Level 0 unit. <br>Examples:
								<ul>
									<li><a href="../../html/5_PartsAssembly.html"
										target="blank_">5-parts assembly</a></li>
									<li><a href="../../html/8_PartsAssembly.html"
										target="blank_">8-parts assembly</a></li>
									<li><a href="../../html/15_PartsAssembly.html"
										target="blank_">15-parts assembly</a></li>
								</ul>
								<s:url action="AssemblXWebIntroAction" id="help1urlHide">
									<s:param name="buttonName">help_1_hide</s:param>
								</s:url>
								<s:a href="%{help1urlHide}">(hide)</s:a>
							</div>
						</s:if>
					</li>

					<li id="QuestionsBefore">Do you plan to modify or shuffle one
						or more assembly unit(s) at later timepoints?<s:url
							action="AssemblXWebIntroAction" id="help2url">
							<s:param name="buttonName">help_2</s:param>
						</s:url> <s:a href="%{help2url}">(read more...)</s:a> <s:if
							test="help_2 == true">
							<div id="help2">
								<br>AssemblX constructs are organized into distinct
								modules. If you already know, that one or more assembly units
								will be subject to modifications, or will likely need
								optimization, it is advantageous to group these units into a
								single Level 1 module. This allows it to modify only one module,
								while all other Level 1 modules will remain constant throughout
								all subsequent experiments.
								<s:url action="AssemblXWebIntroAction" id="help2urlHide">
									<s:param name="buttonName">help_2_hide</s:param>
								</s:url>
								<s:a href="%{help2urlHide}">(hide)</s:a>
							</div>
						</s:if>
					</li>

					<li id="QuestionsBefore">Which organism is your host organism
						for expression? Is there a need to subclone the final expression
						vector?<s:url action="AssemblXWebIntroAction" id="help3url">
							<s:param name="buttonName">help_3</s:param>
						</s:url> <s:a href="%{help3url}">(read more...)</s:a> <s:if
							test="help_3 == true">
							<div id="help3">
								<br>All AssemblX vectors are compatible with direct
								expression in <i>S. cerevisiae</i> or <i>E. coli</i>. In case
								you are intending to express your final construct in a different
								host, you will receive a support protocol with instructions for
								a one-step modification of your host vector of choice.
								<s:url action="AssemblXWebIntroAction" id="help3urlHide">
									<s:param name="buttonName">help_3_hide</s:param>
								</s:url>
								<s:a href="%{help3urlHide}">(hide)</s:a>
							</div>
						</s:if>
					</li>
				</ol>
			</td>
		</tr>
		<tr>
			<td>For more information on the toolkit, please refer to the
				paper or to the user manual. To get a full web tool tutorial please
				watch our <a
				href="https://www.youtube.com/channel/UCowwRbGlqX_CMbU_-8uv1OQ"
				target="_blank">video</a>. Otherwise, please read the supporting
				information provided with every step in the workflow. More
				information about the algorithms behind the AssemblX web tool can be
				found <s:url action="AssemblXWebIntroAction" id="showAlgorithmUrl">
					<s:param name="buttonName">showAlgorithm</s:param>
				</s:url> <s:a href="%{showAlgorithmUrl}">here</s:a> . <s:if
					test="showAlgorithm == true">
					<div id="showAlgorithm">
						The web tool provides an interface which allows the upload and
						arrangement of all sequences required for the complete assembly
						within the AssemblX<sup>1</sup> framework. Level 0 assemblies are
						highly individual and require extensive primer design. This is
						automated by the web tool through forwarding the Level 0 sequence
						data to the j5 web server<sup>2</sup>. j5 is a DNA assembly design
						software, which basically returns all required primers for the
						AssemblX Level 0 assemblies. For more information on j5 and the
						algorithms behind it, please refer to the <a
							href="https://j5.jbei.org/index.php/Main_Page" target="_blank">j5
							homepage</a>. The AssemblX web tool processes the j5 output and
						compiles all sequences for the standardized Level 1 and Level 2
						assembly steps. Finally, a complete benchtop protocol for cloning
						of the desired multi-gene construct is returned, together with
						reference sequences for all assembled products. <a
							href="https://github.com/AssemblX/AssemblXWeb" target="_blank">AssemblX
							web tool at GitHub</a>
						<div id="references">
							<ol>
								<li>AssemblX: A user-friendly toolkit for rapid and
									reliable multi-gene assemblies. Lena Hochrein, Fabian Machens,
									Juergen Gremmels, Karina Schulz, Katrin Messerschmidt and Bernd
									Mueller-Roeber. Nucleic Acids Research, Jan 2017. DOI:
									10.1093/nar/gkx034.</li>
								<li>j5 DNA assembly design automation. Nathan Hilson.
									Methods Mol Biol. 2014;1116:245-69</li>
							</ol>
						</div>
						<s:url action="AssemblXWebIntroAction" id="hideAlgorithmUrl">
							<s:param name="buttonName">hideAlgorithm</s:param>
						</s:url>
						<s:a href="%{hideAlgorithmUrl}">(hide)</s:a>
					</div>
				</s:if>
			</td>
		</tr>
		<tr>
			<td><s:submit type="button" value="login" name="buttonName"
					id="intro">Start the web tool</s:submit></td>
		</tr>
		<tr>
			<td><img alt="overview scheme"
				src="../../images/OverviewScheme.png" width="577" height="400">
			</td>
		</tr>
	</table>
</section>
<s:include value="Navigation.jsp" />

