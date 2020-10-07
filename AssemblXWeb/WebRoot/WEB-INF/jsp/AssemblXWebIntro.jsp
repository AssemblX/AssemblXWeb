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
<link rel="stylesheet" type="text/css" href="../../css/AssemblXWeb.css" />
</head>
<body>
	<s:form action="AssemblXWebIntroAction" theme="simple">
		<s:if test="home == true">
			<s:include value="Home.jsp" />
		</s:if>
		<s:elseif test="paper == true">
			<s:include value="Paper.jsp" />
		</s:elseif>
		<s:elseif test="manual == true">
			<s:include value="Manual.jsp" />
		</s:elseif>
		<s:elseif test="promoterLibrary == true">
			<s:include value="PromoterLibrary.jsp" />
		</s:elseif>
		<s:elseif test="cell2fab == true">
			<s:include value="Cell2Fab.jsp" />
		</s:elseif>
		<s:elseif test="faq == true">
			<s:include value="FAQ.jsp" />
		</s:elseif>
		<s:elseif test="testimonials == true">
			<s:include value="Testimonials.jsp" />
		</s:elseif>
		<s:elseif test="pleaseCite == true">
			<s:include value="PleaseCite.jsp"></s:include>
		</s:elseif>
		<s:elseif test="seqFiles == true">
			<s:include value="DownloadSeqFiles.jsp"></s:include>
		</s:elseif>
		<s:elseif test="welcomeWebTool == true">
			<s:include value="WelcomeWebtool.jsp" />
		</s:elseif>
	</s:form>
</body>
</HTML>