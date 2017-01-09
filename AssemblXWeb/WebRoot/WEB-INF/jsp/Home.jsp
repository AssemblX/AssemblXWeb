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

<section id="intro">
	<h3>A three step assembly process for up to 25 genes</h3>
	To express large sets of proteins in yeast or other host organisms, we
	have developed a cloning framework which allows the modular cloning of
	up to 25 genes into one circular artificial yeast chromosome. <img
		alt="AssemblXWorkflow" src="../../images/WorkflowScheme.png"
		id="workflow">
</section>
<s:include value="Navigation.jsp" />
<section id="advantages">
	<h3>Highlights</h3>
	<h4>User friendly</h4>
	<ul>
		<li>no prerequisites</li>
		<li>no libraries</li>
		<li>sequence independent</li>
		<li>scar free</li>
		<li>software assisted planning</li>
		<li>streamlined assembly process</li>
	</ul>
	<h4>Cheap</h4>
	<ul>
		<li>no need for expensive methods</li>
	</ul>
	<h4>Fast</h4>
	<ul>
		<li>from nothing to a 25 gene assembly in about a month</li>
	</ul>
	<h4>Reliable</h4>
	<ul>
		<li>highly efficient due to optimized assembly steps</li>
	</ul>
</section>