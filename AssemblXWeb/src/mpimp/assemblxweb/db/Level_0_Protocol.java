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

import java.util.List;

public class Level_0_Protocol {

	public List<Level_0_ProtocolSubunit> getTargetParts() {
		return targetParts_;
	}

	public void setTargetParts(List<Level_0_ProtocolSubunit> targetParts) {
		targetParts_ = targetParts;
	}

	public String getBackboneName() {
		return backboneName_;
	}

	public void setBackboneName(String backboneName) {
		backboneName_ = backboneName;
	}

	public Integer getBackboneSize() {
		return backboneSize_;
	}

	public void setBackboneSize(Integer backboneSize) {
		backboneSize_ = backboneSize;
	}

	public List<Level_0_Protocol_WetLabSubunit> getAnnealedOligos() {
		return annealedOligos_;
	}

	public void setAnnealedOligos(
			List<Level_0_Protocol_WetLabSubunit> annealedOligos) {
		annealedOligos_ = annealedOligos;
	}

	public List<Level_0_Protocol_WetLabSubunit> getPcrReactions() {
		return pcrReactions_;
	}

	public void setPcrReactions(List<Level_0_Protocol_WetLabSubunit> pcrReactions) {
		pcrReactions_ = pcrReactions;
	}

	public String getFinalVectorSequence() {
		return finalVectorSequence_;
	}

	public void setFinalVectorSequence(String finalVectorSequence) {
		finalVectorSequence_ = finalVectorSequence;
	}

	public Integer getLengthOfFinalVector() {
		return lengthOfFinalVector_;
	}

	public void setLengthOfFinalVector(Integer lengthOfFinalVector) {
		lengthOfFinalVector_ = lengthOfFinalVector;
	}

	public String getFragment() {
		return fragment_;
	}

	public void setFragment(String fragment) {
		fragment_ = fragment;
	}

	public Integer getFragmentLength() {
		return fragmentLength_;
	}

	public void setFragmentLength(Integer fragmentLength) {
		fragmentLength_ = fragmentLength;
	}

	public String getEnzyme() {
		return enzyme_;
	}

	public void setEnzyme(String enzyme) {
		enzyme_ = enzyme;
	}

	public Boolean getNoFreeEnzyme() {
		return noFreeEnzyme_;
	}

	public void setNoFreeEnzyme(Boolean noFreeEnzyme) {
		noFreeEnzyme_ = noFreeEnzyme;
	}

	public Boolean getSimilarLengths() {
		return similarLengths_;
	}

	public void setSimilarLengths(Boolean similarLengths) {
		similarLengths_ = similarLengths;
	}

//	public String getTranscriptionalUnitName() {
//		return transcriptionalUnitName_;
//	}
//
//	public void setTranscriptionalUnitName(String transcriptionalUnitName) {
//		transcriptionalUnitName_ = transcriptionalUnitName;
//	}

	public Integer getTranscriptionalUnitPosition() {
		return transcriptionalUnitPosition_;
	}

	public void setTranscriptionalUnitPosition(Integer transcriptionalUnitPosition) {
		transcriptionalUnitPosition_ = transcriptionalUnitPosition;
	}

	public String getTuParentModuleName() {
		return tuParentModuleName_;
	}

	public void setTuParentModuleName(String tuParentModuleName) {
		tuParentModuleName_ = tuParentModuleName;
	}

	public String getResultFileName() {
		return resultFileName_;
	}

	public void setResultFileName(String resultFileName) {
		resultFileName_ = resultFileName;
	}

	//Entries under "Target Part Ordering/Selection/Strategy"
	private List<Level_0_ProtocolSubunit> targetParts_;
	//Entry under Target Part Ordering/Selection/Strategy, first row, section "Name"
	private String backboneName_;
	//Entry under Non-degenerate Part IDs and Sources, first row, section "Size"
	private Integer backboneSize_;
	//Entries under "Annealed Oligos" (leave empty if not present)
	private List<Level_0_Protocol_WetLabSubunit> annealedOligos_;
	//Entries under "PCR Reactions" (leave empty if not present)
	private List<Level_0_Protocol_WetLabSubunit> pcrReactions_;
	//Last entry in level 0 protocol
	private Integer lengthOfFinalVector_;
	//needed for level 1 protocol
	private String finalVectorSequence_;
	private String fragment_;
	private Integer fragmentLength_;
	private String enzyme_;
	private Boolean noFreeEnzyme_ = false;
	private Boolean similarLengths_ = false;
	
	//private String transcriptionalUnitName_;
	private Integer transcriptionalUnitPosition_;
	private String tuParentModuleName_;
	private String resultFileName_;
	
}
