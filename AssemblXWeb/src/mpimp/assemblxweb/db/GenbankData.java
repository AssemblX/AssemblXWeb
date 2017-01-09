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

import java.util.ArrayList;
import java.util.List;

public class GenbankData {

	public enum Conformation {LINEAR, CIRCULAR};
	
	public GenbankData() {
		annotationRecords_ = new ArrayList<AnnotationRecord>();
	}
	
	public String getSequence() {
		return sequence_;
	}
	public void setSequence(String sequence) {
		sequence_ = sequence;
	}
	public List<AnnotationRecord> getAnnotationRecords() {
		return annotationRecords_;
	}
	public void setAnnotationRecords(List<AnnotationRecord> annotationRecords) {
		annotationRecords_ = annotationRecords;
	}
	
	public String getLocusName() {
		return locusName_;
	}
	public void setLocusName(String locusName) {
		locusName_ = locusName;
	}

	public Conformation getConformation() {
		return conformation_;
	}
	public void setConformation(Conformation conformation) {
		conformation_ = conformation;
	}

	private String sequence_;
	private List<AnnotationRecord> annotationRecords_;
	private String locusName_;
	private Conformation conformation_;
	
}
