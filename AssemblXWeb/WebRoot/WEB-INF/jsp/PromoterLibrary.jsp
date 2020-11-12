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
<section id="promLib">
	<h2>Promoter library for constitutive protein expression in yeast
	</h2>
	To construct functional biosynthetic pathways it is necessary to
	tightly control the expression of a large number of genes. We have
	cloned a panel of 40 pairs of constitutive yeast promoters together
	with their native terminator sequences. These were analyzed for their
	ability to drive expression of the yEGFP fluorescence protein from a
	Level 1 CEN/ARS low copy plasmid backbone. <img alt="Promoter Diagram"
		src="../../images/PromoterLibrary.png" width="800px" height="367px">
	<section id="promLIbDiagramText">
		<b>Comparison of 40 different constitutive
			promoter-terminator-pairs from <i>S. cerevisiae</i>.</b> The mean yEGFP
		fluorescence per cell was measured using flow cytometry after 16 h of
		batch cultur in either glucose- or galactose-containing SD medium.
		Fluorescence values are given relative to TDH3 promoter-terminator
		pair.
	</section>
	<s:url action="AssemblXWebIntroAction" id="seqFilesUrl">
		<s:param name="buttonName">seqFiles</s:param></s:url>
	Sequences of all promoter-terminator pairs can be downloaded
	<s:a href="%{seqFilesUrl}">here</s:a>
	.
</section>

<s:include value="Navigation.jsp" />