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
<section id="FAQs">
	<h2>Frequently asked questions:</h2>
	<ol>
		<li id="FAQ">Which time should be planned to assemble e.g. 4
			genes into a Level 1 vector? <br>You should plan 6-10 days for
			the assembly of up to 5 genes in a Level 1 vector.
		</li>
		<li id="FAQ">How much time do you save by propagating the level 0
			vectors in <i>E. Coli?</i> <br>You save 2-3 days, because yeast
			colonies grow more slowly.
		</li>
		<li id="FAQ">What is the minimum overlap length that can be used
			with this method? <br>The overlap length is definded by the
			cloning method you choose. We recommend at least 15 bp for <i>in
				vitro</i> cloning methods.
		</li>
		<li id="FAQ">Which efficiency of the assembly reaction can I
			expect? <br>In our experiments, the cloning efficiency for Level
			0 assemblies greatly varied with complexity and choice of the
			assembly method (efficiencies between 33% and 100% ). For Level 1 and
			Level 2 assemblies you can expect an efficiency of 100%.
		</li>
		<li id="FAQ">Is AssemblX cloning system compatible with
			electro-transformation? <br>Yes.
		</li>

		<li id="FAQ">Is it possible to prepare Level 0 vectors with A0/B0
			homology regions, which could be used for direct cloning of one
			cassette into expression vectors with A0/B0 homology regions?<br>
			Yes, please refer to the AssemblX paper. Here you find instructions
			on how to modify your custom expression vector (Supplementary Figure
			1).
		</li>
	</ol>
</section>
<s:include value="Navigation.jsp" />