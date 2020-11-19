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

<nav>
	<ul>
		<li><s:submit type="button" name="buttonName" value="home"
				id="intro">Home</s:submit></li>
		<li><s:submit type="button" name="buttonName" value="webtool"
				id="intro">AssemblX Web Tool</s:submit></li>
		<!-- <li><s:submit type="button" name="buttonName" value="paper"  id="intro">Paper</s:submit></li> -->
		<li><s:submit type="button" name="buttonName" value="manual"
				id="intro">Manual</s:submit></li>
		<li><s:submit type="button" name="buttonName"
				value="promoterLibrary" id="intro">Promoter Library</s:submit></li>
		<!-- <li><s:submit type="button" name="buttonName" value="cell2Fab"  id="intro">Cell2Fab</s:submit></li> -->
		<li><s:submit type="button" name="buttonName" value="faq"
				id="intro">FAQ</s:submit></li>
		<li><s:submit type="button" name="buttonName"
				value="testimonials" id="intro">Testimonials</s:submit></li>
		<li><s:submit type="button" name="buttonName" value="seqFiles"
				id="intro">Download Sequence Files</s:submit></li>
		<li><s:submit type="button" name="buttonName" value="pleaseCite"
				id="intro">Please Cite</s:submit></li>
	</ul>
</nav>