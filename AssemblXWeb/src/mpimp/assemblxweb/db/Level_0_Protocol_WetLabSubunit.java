/*
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
*/
package mpimp.assemblxweb.db;

public class Level_0_Protocol_WetLabSubunit {
	
	public Level_0_Protocol_WetLabSubunit(String primaryTemplate,
			String primerForward, String primerReverse,
			Double meanOligoTm_3_prime, Integer length) {
		primaryTemplate_ = primaryTemplate;
		primerForward_ = primerForward;
		primerReverse_ = primerReverse;
		meanOligoTm_3_prime_ = meanOligoTm_3_prime;
		length_ = length;
	}
	
	public Level_0_Protocol_WetLabSubunit(String topOligoName,
			String bottomOligoName) {
		super();
		topOligoName_ = topOligoName;
		bottomOligoName_ = bottomOligoName;
	}

	public String getPrimaryTemplate() {
		return primaryTemplate_;
	}
	public String getPrimerForward() {
		return primerForward_;
	}
	public String getPrimerReverse() {
		return primerReverse_;
	}
	public Double getMeanOligoTm_3_prime() {
		return meanOligoTm_3_prime_;
	}
	public Integer getLength() {
		return length_;
	}

	public String getTopOligoName() {
		return topOligoName_;
	}

	public String getBottomOligoName() {
		return bottomOligoName_;
	}
	
	private String topOligoName_;
	private String bottomOligoName_;

	private String primaryTemplate_;
	private String primerForward_;
	private String primerReverse_;
	private Double meanOligoTm_3_prime_;
	private Integer length_;
	
}
