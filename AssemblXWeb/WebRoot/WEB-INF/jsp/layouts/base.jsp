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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1" />
<!-- <link rel="stylesheet" type="text/css" href="../css/AssemblXWeb.css" /> -->
<link rel="stylesheet" type="text/css"
	href="/AssemblXWeb/css/AssemblXWeb.css" />
<title><tiles:insertAttribute name="title" /></title>

<!-- Piwik -->
<script type="text/javascript">
	var _paq = _paq || [];
	_paq.push([ 'trackPageView' ]);
	_paq.push([ 'enableLinkTracking' ]);
	(function() {
		var u = "//piwik.mpg.de/piwik/";
		_paq.push([ 'setTrackerUrl', u + 'piwik.php' ]);
		_paq.push([ 'setSiteId', '239' ]);
		var d = document,
			g = d.createElement('script'),
			s = d.getElementsByTagName('script')[0];
		g.type = 'text/javascript';
		g.async = true;
		g.defer = true;
		g.src = u + 'piwik.js'; s.parentNode.insertBefore(g, s);
	})();
</script>
<noscript>
	<p>
		<img src="//piwik.mpg.de/piwik/piwik.php?idsite=239&rec=1"
			style="border:0;" alt="" />
	</p>
</noscript>
<!-- End Piwik Code -->

</head>
<body>
	<header>
		<tiles:insertAttribute name="header" />
	</header>
	<aside>
		<div class="uni-potsdam">
			<tiles:insertAttribute name="uni-potsdam" />
		</div>
		<div class="cell2fab">
			<tiles:insertAttribute name="cell2fab-logo" />
		</div>
		<div class="mpimp-logo">
			<tiles:insertAttribute name="mpimp-logo" />
		</div>
		<div class="minerva">
			<tiles:insertAttribute name="minerva" />
		</div>
		<div class="j5">
			<tiles:insertAttribute name="j5" />
		</div>
		<div class="bmbf-logo">
			<tiles:insertAttribute name="bmbfLogo" />
		</div>
		<div class="impressum">
			<tiles:insertAttribute name="impressum" />
		</div>
		<div class="privacyPolicy">
			<tiles:insertAttribute name="privacyPolicy" />
		</div>
		<div class="disclaimer">
			<tiles:insertAttribute name="disclaimer" />
		</div>
		<div class="contact">
			<tiles:insertAttribute name="contact" />
		</div>
	</aside>
	<main>
		<tiles:insertAttribute name="body" />
	</main>
</body>
</html>