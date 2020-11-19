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

/**
 * 
 * @author gremmels This class represents one contiguous part of a sequence
 *         specified in a genbank file. Described as <sequence> where
 *         <sequence> itself may consist of one ore more annotation part items.
 */

public class AnnotationPartItem {

	public AnnotationPartItem() {
		childItems_ = new ArrayList<AnnotationPartItem>();
	}

	public Integer getStartPosition() {
		return startPosition_;
	}

	public void setStartPosition(Integer startPosition) {
		startPosition_ = startPosition;
	}

	public Integer getEndPosition() {
		return endPosition_;
	}

	public void setEndPosition(Integer endPosition) {
		endPosition_ = endPosition;
	}

	public void setChildItems(List<AnnotationPartItem> childItems) {
		childItems_ = childItems;
	}

	public AnnotationPartItem getParentItem() {
		return parentItem_;
	}

	public void setParentItem(AnnotationPartItem parentItem) {
		parentItem_ = parentItem;
	}

	public List<AnnotationPartItem> getChildItems() {
		return childItems_;
	}

	public String getPartSequence() {
		return partSequence_;
	}

	public void setPartSequence(String partSequence) {
		partSequence_ = partSequence;
	}

	public Integer getPositionOfItem() {
		return positionOfItem_;
	}

	public void setPositionOfItem(Integer positionOfItem) {
		positionOfItem_ = positionOfItem;
	}

	public Boolean getIsReverseComplement() {
		return isReverseComplement_;
	}

	public void setIsReverseComplement(Boolean isReverseComplement) {
		isReverseComplement_ = isReverseComplement;
	}

	public Boolean getIsJoinItem() {
		return isJoinItem_;
	}

	public void setIsJoinItem(Boolean isJoinItem) {
		isJoinItem_ = isJoinItem;
	}

	public Boolean getIsSiteItem() {
		return isSiteItem_;
	}

	public void setIsSiteItem(Boolean isSiteItem) {
		isSiteItem_ = isSiteItem;
	}

	public Integer getRelativeSiteStart() {
		return relativeSiteStart_;
	}

	public void setRelativeSiteStart(Integer relativeSiteStart) {
		relativeSiteStart_ = relativeSiteStart;
	}

	private Integer startPosition_ = -1;// in 1..n notation
	private Integer endPosition_ = -1;// in 1..n notation
	private String partSequence_ = "";
	private AnnotationPartItem parentItem_;
	private List<AnnotationPartItem> childItems_;
	private Integer positionOfItem_ = -1;
	private Boolean isReverseComplement_ = false;
	private Boolean isJoinItem_ = false;
	private Boolean isSiteItem_ = false;
	private Integer relativeSiteStart_ = -1;// in 1..n notation

}
