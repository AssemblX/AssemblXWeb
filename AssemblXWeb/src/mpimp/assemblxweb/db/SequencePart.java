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

public class SequencePart {

	public String getFileName() {
		return fileName_;
	}
	
	public void setFileName(String fileName) {
		fileName_ = fileName;
	}
	
	public String getSequencePartString() {
		return sequencePartString_;
	}
	
	public void setSequencePartString(String sequencePartString) {
		sequencePartString_ = sequencePartString;
	}
	
	public Boolean getReverseComplement() {
		return reverseComplement_;
	}
	
	public void setReverseComplement(Boolean reverseComplement) {
		reverseComplement_ = reverseComplement;
	}
	
	public Integer getStart() {
		return start_;
	}
	
	public void setStart(Integer start) {
		start_ = start;
	}
	
	public Integer getEnd() {
		return end_;
	}
	
	public void setEnd(Integer end) {
		end_ = end;
	}
	
	private String fileName_;
	private String sequencePartString_;
	private Boolean reverseComplement_ = false;
	private Integer start_;
	private Integer end_;
}
