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

import mpimp.assemblxweb.util.AssemblXWebEnumTranslator;
import mpimp.assemblxweb.util.AssemblXWebProperties;

public class TuSubunit {

	public static enum TuSubunitType {
		PROMOTER, CODING_SEQUENCE, TAG, TERMINATOR, BACKBONE, GENE, RES, MISC_RNA, USER_DEFINED, NO_TYPE, OPERATOR, INSULATOR, ORIGIN_OF_REPLICATION, RESTRICTION_SITE, SIGNATURE
	};

	public static enum TuSubunitDirection {
		FORWARD, REVERSE
	};

	public static enum TuSubunitAssemblyStrategy {
		PCR, ANNEALED_OLIGOS, EMBED_IN_PRIMER_REVERSE, EMBED_IN_PRIMER_FORWARD, DIRECT_SYNTHESIS, DIGEST, NO_STRATEGY
	};

	public static enum TuSubunitSequenceOption {
		WHOLE_SEQUENCE, SPECIFIED_SEQUENCE
	};

	public static enum SequenceInputOption {
		LOAD_FROM_FILE, PASTE_AS_TEXT
	};

	public TuSubunit(TranscriptionalUnit parentTuUnit, Integer position) {
		parentTuUnit_ = parentTuUnit;
		position_ = position;
		enumTranslator_ = new AssemblXWebEnumTranslator();
	}

	public TuSubunit copy() {
		TuSubunit copiedUnit = new TuSubunit(parentTuUnit_, position_);
		copiedUnit.setPartName(partName_);
		copiedUnit.setPartSource(partSource_);
		copiedUnit.setIsReverseComplement(isReverseComplement_);
		copiedUnit.setStart(start_);
		copiedUnit.setEnd(end_);
		copiedUnit.setFivePrimeInternalPreferredOverhangs(fivePrimeInternalPreferredOverhangs_);
		copiedUnit.setThreePrimeInternalPreferredOverhangs(threePrimeInternalPreferredOverhangs_);
		copiedUnit.setForcedAssemblyStrategy(forcedAssemblyStrategy_);
		copiedUnit.setForcedRelativeOverhangPosition(forcedRelativeOverhangPosition_);
		copiedUnit.setDirectSynthesisFirewall(directSynthesisFirewall_);
		copiedUnit.setType(type_);
		copiedUnit.setDirection(direction_);
		copiedUnit.setAssemblyStrategy(assemblyStrategy_);
		copiedUnit.setSequenceFileName(sequenceFileName_);
		copiedUnit.setSequenceFormat(sequenceFormat_);
		copiedUnit.setSequenceFileLoaded(sequenceFileLoaded_);
		copiedUnit.setSequenceFileContent(sequenceFileContent_);
		copiedUnit.setPureRawSequence(pureRawSequence_);
		copiedUnit.setSequenceInputOption(sequenceInputOption_);
		copiedUnit.setSequenceOption(sequenceOption_);
		return copiedUnit;
	}

	public String getPartName() {
		return partName_;
	}

	public void setPartName(String partName) {
		partName_ = partName;
	}

	public String getPartSource() {
		return partSource_;
	}

	public void setPartSource(String partSource) {
		partSource_ = partSource;
	}

	public Boolean getIsReverseComplement() {
		return isReverseComplement_;
	}

	public void setIsReverseComplement(Boolean isReverseComplement) {
		isReverseComplement_ = isReverseComplement;
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

	public String getFivePrimeInternalPreferredOverhangs() {
		return fivePrimeInternalPreferredOverhangs_;
	}

	public void setFivePrimeInternalPreferredOverhangs(String fivePrimeInternalPreferredOverhangs) {
		fivePrimeInternalPreferredOverhangs_ = fivePrimeInternalPreferredOverhangs;
	}

	public String getThreePrimeInternalPreferredOverhangs() {
		return threePrimeInternalPreferredOverhangs_;
	}

	public void setThreePrimeInternalPreferredOverhangs(String threePrimeInternalPreferredOverhangs) {
		threePrimeInternalPreferredOverhangs_ = threePrimeInternalPreferredOverhangs;
	}

	public TuSubunitType getType() {
		return type_;
	}

	public void setType(TuSubunitType type) {
		type_ = type;
	}

	public String getTypeName() {
		return enumTranslator_.getTuSubunitTypeName(type_);
	}

	public String getSequenceInputOptionName() {
		return enumTranslator_.getTuSubunitSequenceInputOptionName(sequenceInputOption_);
	}

	public TuSubunitDirection getDirection() {
		return direction_;
	}

	public void setDirection(TuSubunitDirection direction) {
		direction_ = direction;
	}

	public TuSubunitAssemblyStrategy getAssemblyStrategy() {
		return assemblyStrategy_;
	}

	public void setAssemblyStrategy(TuSubunitAssemblyStrategy assemblyStrategy) {
		assemblyStrategy_ = assemblyStrategy;
	}

	public String getSequenceFileName() {
		return sequenceFileName_;
	}

	public void setSequenceFileName(String sequenceFileName) {
		sequenceFileName_ = sequenceFileName;
	}

	public String getSequenceFormat() {
		return sequenceFormat_;
	}

	public void setSequenceFormat(String sequenceFormat) {
		sequenceFormat_ = sequenceFormat;
	}

	public TuSubunitAssemblyStrategy getForcedAssemblyStrategy() {
		return forcedAssemblyStrategy_;
	}

	public void setForcedAssemblyStrategy(TuSubunitAssemblyStrategy forcedAssemblyStrategy) {
		forcedAssemblyStrategy_ = forcedAssemblyStrategy;
	}

	public String getForcedRelativeOverhangPosition() {
		return forcedRelativeOverhangPosition_;
	}

	public void setForcedRelativeOverhangPosition(String forcedRelativeOverhangPosition) {
		forcedRelativeOverhangPosition_ = forcedRelativeOverhangPosition;
	}

	public String getDirectSynthesisFirewall() {
		return directSynthesisFirewall_;
	}

	public void setDirectSynthesisFirewall(String directSynthesisFirewall) {
		directSynthesisFirewall_ = directSynthesisFirewall;
	}

	public String getImageSource() {
		switch (type_) {
		case PROMOTER:
			if (direction_ == TuSubunitDirection.FORWARD) {
				return AssemblXWebProperties.getInstance().getProperty("imgPromoter");
			} else {
				return AssemblXWebProperties.getInstance().getProperty("imgPromoterReverse");
			}
		case CODING_SEQUENCE:
			if (direction_ == TuSubunitDirection.FORWARD) {
				return AssemblXWebProperties.getInstance().getProperty("imgCodingSequence");
			} else {
				return AssemblXWebProperties.getInstance().getProperty("imgCodingSequenceReverse");
			}
		case TAG:
			return AssemblXWebProperties.getInstance().getProperty("imgTag");
		case TERMINATOR:
			return AssemblXWebProperties.getInstance().getProperty("imgTerminator");
		case BACKBONE:
			return AssemblXWebProperties.getInstance().getProperty("imgBackbone");
		case USER_DEFINED:
			return AssemblXWebProperties.getInstance().getProperty("imgUserDefined");
		case OPERATOR:
			return AssemblXWebProperties.getInstance().getProperty("imgOperator");
		case INSULATOR:
			return AssemblXWebProperties.getInstance().getProperty("imgInsulator");
		case ORIGIN_OF_REPLICATION:
			return AssemblXWebProperties.getInstance().getProperty("imgOriginOfReplication");
		case RESTRICTION_SITE:
			return AssemblXWebProperties.getInstance().getProperty("imgRestrictionSite");
		case RES:
			return AssemblXWebProperties.getInstance().getProperty("imgRes");
		case SIGNATURE:
			if (direction_ == TuSubunitDirection.FORWARD) {
				return AssemblXWebProperties.getInstance().getProperty("imgSignature");
			} else {
				return AssemblXWebProperties.getInstance().getProperty("imgSignatureReverse");
			}
		default:
			return "";
		}
	}

	public String getIdentifier() {
		String identifier = parentTuUnit_.getParentModule().getName() + parentTuUnit_.getPosition() + "_" + position_;
		return identifier;
	}

	public Boolean getSequenceFileLoaded() {
		return sequenceFileLoaded_;
	}

	public void setSequenceFileLoaded(Boolean sequenceFileLoaded) {
		sequenceFileLoaded_ = sequenceFileLoaded;
	}

	public String getSequenceFileContent() {
		return sequenceFileContent_;
	}

	public void setSequenceFileContent(String sequenceFileContent) {
		sequenceFileContent_ = sequenceFileContent;
	}

	public TuSubunitSequenceOption getSequenceOption() {
		return sequenceOption_;
	}

	public void setSequenceOption(TuSubunitSequenceOption sequenceOption) {
		sequenceOption_ = sequenceOption;
	}

	public String getSequenceOptionName() {
		return enumTranslator_.getTuSubunitSequenceOptionName(sequenceOption_);
	}

	public Integer getPosition() {
		return position_;
	}

	public void setPosition(Integer position) {
		position_ = position;
	}

	public String getPureRawSequence() {
		return pureRawSequence_;
	}

	public void setPureRawSequence(String pureRawSequence) {
		pureRawSequence_ = pureRawSequence;
	}

	public SequenceInputOption getSequenceInputOption() {
		return sequenceInputOption_;
	}

	public void setSequenceInputOption(SequenceInputOption sequenceInputOption) {
		sequenceInputOption_ = sequenceInputOption;
	}

	public TranscriptionalUnit getParentTuUnit() {
		return parentTuUnit_;
	}

	private String partName_ = "";
	private String partSource_ = "";
	private Boolean isReverseComplement_ = false;
	private Integer start_ = -1;
	private Integer end_ = -1;
	private String fivePrimeInternalPreferredOverhangs_ = "";
	private String threePrimeInternalPreferredOverhangs_ = "";
	private TuSubunitAssemblyStrategy forcedAssemblyStrategy_ = TuSubunitAssemblyStrategy.NO_STRATEGY;
	private String forcedRelativeOverhangPosition_ = "";
	private String directSynthesisFirewall_ = "";
	private TuSubunitType type_ = TuSubunitType.NO_TYPE;
	private TuSubunitDirection direction_ = TuSubunitDirection.FORWARD;
	private TuSubunitAssemblyStrategy assemblyStrategy_ = TuSubunitAssemblyStrategy.NO_STRATEGY;
	private String sequenceFileName_ = "";
	private String sequenceFormat_ = "";
	private Boolean sequenceFileLoaded_ = false;
	private String sequenceFileContent_ = "";

	// the user may also paste the raw sequence alternatively
	private String pureRawSequence_ = "";
	private SequenceInputOption sequenceInputOption_ = SequenceInputOption.PASTE_AS_TEXT;

	private TuSubunitSequenceOption sequenceOption_ = TuSubunitSequenceOption.WHOLE_SEQUENCE;
	private TranscriptionalUnit parentTuUnit_;
	private Integer position_ = 0;

	private AssemblXWebEnumTranslator enumTranslator_;
}
