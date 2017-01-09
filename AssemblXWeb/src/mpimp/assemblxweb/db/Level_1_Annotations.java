package mpimp.assemblxweb.db;

import java.util.ArrayList;
import java.util.List;

import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;

public class Level_1_Annotations {

	private static AnnotationRecord ura3CDS_;
	private static AnnotationPartItem ura3CDSItem_;
	private static final String ura3CDSSequence_ = "ATGTCGAAAGCTACATATAAGGAACGTGCTGCTACTCATCCTAGTCCTGTTGCTG"
			+ "CCAAGCTATTTAATATCATGCACGAAAAGCAAACAAACTTGTGTGCTTCATTGGATGTTCGTACCACCAAGGAATTACTGGAGTTAGTTGAA"
			+ "GCATTAGGTCCCAAAATTTGTTTACTAAAAACACATGTGGATATCTTGACTGATTTTTCCATGGAGGGCACAGTTAAGCCGCTAAAGGCATT"
			+ "ATCCGCCAAGTACAATTTTTTACTCTTCGAAGACAGAAAATTTGCTGACATTGGTAATACAGTCAAATTGCAGTACTCTGCGGGTGTATACA"
			+ "GAATAGCAGAATGGGCAGACATTACGAATGCACACGGTGTGGTGGGCCCAGGTATTGTTAGCGGTTTGAAGCAGGCGGCAGAAGAAGTAACA"
			+ "AAGGAACCTAGAGGCCTTTTGATGTTAGCAGAATTGTCATGCAAGGGCTCCCTATCTACTGGAGAATATACTAAGGGTACTGTTGACATTGC"
			+ "GAAGAGCGACAAAGATTTTGTTATCGGCTTTATTGCTCAAAGAGACATGGGTGGAAGAGATGAAGGTTACGATTGGTTGATTATGACACCCG"
			+ "GTGTGGGTTTAGATGACAAGGGAGACGCATTGGGTCAACAGTATAGAACCGTGGATGATGTGGTCTCTACAGGATCTGACATTATTATTGTT"
			+ "GGAAGAGGACTATTTGCAAAGGGAAGGGATGCTAAGGTAGAGGGTGAACGTTACAGAAAAGCAGGCTGGGAAGCATATTTGAGAAGATGCGG"
			+ "CCAGCAAAACTAA";
	private static AnnotationRecord leu2CDS_;
	private static AnnotationPartItem leu2CDSItem_;
	private static final String leu2CDSSequence_ = "ATGTCTGCCCCTAAGAAGATCGTCGTTTTGCCAGGTGACCACGTTGGTCAAGAAA"
			+ "TCACAGCCGAAGCCATTAAGGTTCTTAAAGCTATTTCTGATGTTCGTTCCAATGTCAAGTTCGATTTCGAAAATCATTTAATTGGTGGTGCT"
			+ "GCTATCGATGCTACAGGTGTCCCACTTCCAGATGAGGCGCTGGAAGCCTCCAAGAAGGTTGATGCCGTTTTGTTAGGTGCTGTGGGTGGTCC"
			+ "TAAATGGGGTACAGGTAGTGTTAGACCTGAACAAGGTTTACTAAAAATCCGTAAAGAACTTCAATTGTACGCCAACTTAAGACCATGTAACT"
			+ "TTGCATCCGACTCTCTTTTAGACTTATCTCCAATCAAGCCACAATTTGCTAAAGGTACTGACTTCGTTGTTGTCAGAGAATTAGTGGGAGGT"
			+ "ATTTACTTTGGTAAGAGAAAGGAAGACGATGGTGATGGTGTCGCTTGGGATAGTGAACAATACACCGTTCCAGAAGTGCAAAGAATCACAAG"
			+ "AATGGCCGCTTTCATGGCCCTACAACATGAGCCACCATTGCCTATTTGGTCCTTGGATAAAGCTAATGTTTTGGCCTCTTCAAGATTATGGA"
			+ "GAAAAACTGTGGAGGAAACCATCAAGAACGAATTTCCTACATTGAAGGTTCAACATCAATTGATTGATTCTGCCGCCATGATCCTAGTTAAG"
			+ "AACCCAACCCACCTAAATGGTATTATAATCACCAGCAACATGTTTGGTGATATCATCTCCGATGAAGCCTCCGTTATCCCAGGTTCCTTGGG"
			+ "TTTGTTGCCATCTGCGTCCTTGGCCTCTTTGCCAGACAAGAACACCGCATTTGGTTTGTACGAACCATGCCACGGTTCTGCTCCAGATTTGC"
			+ "CAAAGAATAAGGTTGACCCTATCGCCACTATCTTGTCTGCTGCAATGATGTTGAAATTGTCATTGAACTTGCCTGAAGAAGGTAAGGCCATT"
			+ "GAAGATGCAGTTAAAAAGGTTTTGGATGCAGGTATCAGAACTGGTGATTTAGGTGGTTCCAACAGTACCACCGAAGTCGGTGATGCTGTCGC"
			+ "CGAAGAAGTTAAGAAAATCCTTGCTTAA";
	private static AnnotationRecord his3CDS_;
	private static AnnotationPartItem his3CDSItem_;
	private static final String his3CDSSequence_ = "ATGACAGAGCAGAAAGCCCTAGTAAAGCGTATTACAAATGAAACCAAGATTCAGA"
			+ "TTGCGATCTCTTTAAAGGGTGGTCCCCTAGCGATAGAGCACTCGATCTTCCCAGAAAAAGAGGCAGAAGCAGTAGCAGAACAGGCCACACAA"
			+ "TCGCAAGTGATTAACGTCCACACAGGTATAGGGTTTCTGGACCATATGATACATGCTCTGGCCAAGCATTCCGGCTGGTCGCTAATCGTTGA"
			+ "GTGCATTGGTGACTTACACATAGACGACCATCACACCACTGAAGACTGCGGGATTGCTCTCGGTCAAGCTTTTAAAGAGGCCCTACTGGCGC"
			+ "GTGGAGTAAAAAGGTTTGGATCAGGATTTGCGCCTTTGGATGAGGCACTTTCCAGAGCGGTGGTAGATCTTTCGAACAGGCCGTACGCAGTT"
			+ "GTCGAACTTGGTTTGCAAAGGGAGAAAGTAGGAGATCTCTCTTGCGAGATGATCCCGCATTTTCTTGAAAGCTTTGCAGAGGCTAGCAGAAT"
			+ "TACCCTCCACGTTGATTGTCTGCGAGGCAAGAATGATCATCACCGTAGTGAGAGTGCGTTCAAGGCTCTTGCGGTTGCCATAAGAGAAGCCA"
			+ "CCTCGCCCAATGGTACCAACGATGTTCCCTCCACCAAAGGTGTTCTTATGTAG";
	private static AnnotationRecord lys2CDS_;
	private static AnnotationPartItem lys2CDSItem_;
	private static final String lys2CDSSequence_ = "ATGACTAACGAAAAGGTCTGGATAGAGAAGTTGGATAATCCAACTCTTTCAGTGT"
			+ "TACCACATGACTTTTTACGCCCACAACAAGAACCTTATACGAAACAAGCTACATATTCGTTACAGCTACCTCAGCTCGATGTGCCTCATGAT"
			+ "AGTTTTTCTAACAAATACGCTGTCGCTTTGAGTGTATGGGCTGCATTGATATATAGAGTAACCGGTGACGATGATATTGTTCTTTATATTGC"
			+ "GAATAACAAAATCTTAAGATTCAATATTCAACCAACGTGGTCATTTAATGAGCTGTATTCTACAATTAACAATGAGTTGAACAAGCTCAATT"
			+ "CTATTGAGGCCAATTTTTCCTTTGACGAGCTAGCTGAAAAAATTCAAAGTTGCCAAGATCTGGAAAGGACCCCTCAGTTGTTCCGTTTGGCC"
			+ "TTTTTGGAAAACCAAGATTTCAAATTAGACGAGTTCAAGCATCATTTAGTGGACTTTGCTTTGAATTTGGATACCAGTAATAATGCGCATGT"
			+ "TTTGAACTTAATTTATAACAGCTTACTGTATTCGAATGAAAGAGTAACCATTGTTGCGGACCAATTTACTCAATATTTGACTGCTGCGCTAA"
			+ "GCGATCCATCCAATTGCATAACTAAAATCTCTCTGATCACCGCATCATCCAAGGATAGTTTACCTGATCCAACTAAGAACTTGGGCTGGTGC"
			+ "GATTTCGTGGGGTGTATTCACGACATTTTCCAGGACAATGCTGAAGCCTTCCCAGAGAGAACCTGTGTTGTGGAGACTCCAACACTAAATTC"
			+ "CGACAAGTCCCGTTCTTTCACTTATCGCGACATCAACCGCACTTCTAACATAGTTGCCCATTATTTGATTAAAACAGGTATCAAAAGAGGTG"
			+ "ATGTAGTGATGATCTATTCTTCTAGGGGTGTGGATTTGATGGTATGTGTGATGGGTGTCTTGAAAGCCGGCGCAACCTTTTCAGTTATCGAC"
			+ "CCTGCATATCCCCCAGCCAGACAAACCATTTACTTAGGTGTTGCTAAACCACGTGGGTTGATTGTTATTAGAGCTGCTGGACAATTGGATCA"
			+ "ACTAGTAGAAGATTACATCAATGATGAATTGGAGATTGTTTCAAGAATCAATTCCATCGCTATTCAAGAAAATGGTACCATTGAAGGTGGCA"
			+ "AATTGGACAATGGCGAGGATGTTTTGGCTCCATATGATCACTACAAAGACACCAGAACAGGTGTTGTAGTTGGACCAGATTCCAACCCAACC"
			+ "CTATCTTTCACATCTGGTTCCGAAGGTATTCCTAAGGGTGTTCTTGGTAGACATTTTTCCTTGGCTTATTATTTCAATTGGATGTCCAAAAG"
			+ "GTTCAACTTAACAGAAAATGATAAATTCACAATGCTGAGCGGTATTGCACATGATCCAATTCAAAGAGATATGTTTACACCATTATTTTTAG"
			+ "GTGCCCAATTGTATGTCCCTACTCAAGATGATATTGGTACACCGGGCCGTTTAGCGGAATGGATGAGTAAGTATGGTTGCACAGTTACCCAT"
			+ "TTAACACCTGCCATGGGTCAATTACTTACTGCCCAAGCTACTACACCATTCCCTAAGTTACATCATGCGTTCTTTGTGGGTGACATTTTAAC"
			+ "AAAACGTGATTGTCTGAGGTTACAAACCTTGGCAGAAAATTGCCGTATTGTTAATATGTACGGTACCACTGAAACACAGCGTGCAGTTTCTT"
			+ "ATTTCGAAGTTAAATCAAAAAATGACGATCCAAACTTTTTGAAAAAATTGAAAGATGTCATGCCTGCTGGTAAAGGTATGTTGAACGTTCAG"
			+ "CTACTAGTTGTTAACAGGAACGATCGTACTCAAATATGTGGTATTGGCGAAATAGGTGAGATTTATGTTCGTGCAGGTGGTTTGGCCGAAGG"
			+ "TTATAGAGGATTACCAGAATTGAATAAAGAAAAATTTGTGAACAACTGGTTTGTTGAAAAAGATCACTGGAATTATTTGGATAAGGATAATG"
			+ "GTGAACCTTGGAGACAATTCTGGTTAGGTCCAAGAGATAGATTGTACAGAACGGGTGATTTAGGTCGTTATCTACCAAACGGTGACTGTGAA"
			+ "TGTTGCGGTAGGGCTGATGATCAAGTTAAAATTCGTGGGTTCAGAATCGAATTAGGAGAAATAGATACGCACATTTCCCAACATCCATTGGT"
			+ "AAGAGAAAACATTACTTTAGTTCGCAAAAATGCCGACAATGAGCCAACATTGATCACATTTATGGTCCCAAGATTTGACAAGCCAGATGACT"
			+ "TGTCTAAGTTCCAAAGTGATGTTCCAAAGGAGGTTGAAACTGACCCTATAGTTAAGGGCTTAATCGGTTACCATCTTTTATCCAAGGACATC"
			+ "AGGACTTTCTTAAAGAAAAGATTGGCTAGCTATGCTATGCCTTCCTTGATTGTGGTTATGGATAAACTACCATTGAATCCAAATGGTAAAGT"
			+ "TGATAAGCCTAAACTTCAATTCCCAACTCCCAAGCAATTAAATTTGGTAGCTGAAAATACAGTTTCTGAAACTGACGACTCTCAGTTTACCA"
			+ "ATGTTGAGCGCGAGGTTAGAGACTTATGGTTAAGTATATTACCTACCAAGCCAGCATCTGTATCACCAGATGATTCGTTTTTCGATTTAGGT"
			+ "GGTCATTCTATCTTGGCTACCAAAATGATTTTTACCTTAAAGAAAAAGCTGCAAGTTGATTTACCATTGGGCACAATTTTCAAGTATCCAAC"
			+ "GATAAAGGCCTTTGCCGCGGAAATTGACAGAATTAAATCATCGGGTGGATCATCTCAAGGTGAGGTCGTCGAAAATGTCACTGCAAATTATG"
			+ "CGGAAGACGCCAAGAAATTGGTTGAGACGCTACCAAGTTCGTACCCCTCTCGAGAATATTTTGTTGAACCTAATAGTGCCGAAGGAAAAACA"
			+ "ACAATTAATGTGTTTGTTACCGGTGTCACAGGATTTCTGGGCTCCTACATCCTTGCAGATTTGTTAGGACGTTCTCCAAAGAACTACAGTTT"
			+ "CAAAGTGTTTGCCCACGTCAGGGCCAAGGATGAAGAAGCTGCATTTGCAAGATTACAAAAGGCAGGTATCACCTATGGTACTTGGAACGAAAA"
			+ "ATTTGCCTCAAATATTAAAGTTGTATTAGGCGATTTATCTAAAAGCCAATTTGGTCTTTCAGATGAGAAGTGGATGGATTTGGCAAACACAGT"
			+ "TGATATAATTATCCATAATGGTGCGTTAGTTCACTGGGTTTATCCATATGCCAAATTGAGGGATCCAAATGTTATTTCAACTATCAATGTTAT"
			+ "GAGCTTAGCCGCCGTCGGCAAGCCAAAGTTCTTTGACTTTGTTTCCTCCACTTCTACTCTTGACACTGAATACTACTTTAATTTGTCAGATAA"
			+ "ACTTGTTAGCGAAGGGAAGCCAGGCATTTTAGAATCAGACGATTTAATGAACTCTGCAAGCGGGCTCACTGGTGGATATGGTCAGTCCAAATG"
			+ "GGCTGCTGAGTACATCATTAGACGTGCAGGTGAAAGGGGCCTACGTGGGTGTATTGTCAGACCAGGTTACGTAACAGGTGCCTCTGCCAATG"
			+ "GTTCTTCAAACACAGATGATTTCTTATTGAGATTTTTGAAAGGTTCAGTCCAATTAGGTAAGATTCCAGATATCGAAAATTCCGTGAATATGG"
			+ "TTCCAGTAGATCATGTTGCTCGTGTTGTTGTTGCTACGTCTTTGAATCCTCCCAAAGAAAATGAATTGGCCGTTGCTCAAGTAACGGGTCACC"
			+ "CAAGAATATTATTCAAAGACTACTTGTATACTTTACACGATTATGGTTACGATGTCGAAATCGAAAGCTATTCTAAATGGAAGAAATCATTGG"
			+ "AGGCGTCTGTTATTGACAGGAATGAAGAAAATGCGTTGTATCCTTTGCTACACATGGTCTTAGACAACTTACCTGAAAGTACCAAAGCTCCGGA"
			+ "ACTAGACGATAGGAACGCCGTGGCATCTTTAAAGAAAGACACCGCATGGACAGGTGTTGATTGGTCTAATGGAATAGGTGTTACTCCAGAAGAG"
			+ "GTTGGTATATATATTGCATTTTTAAACAAGGTTGGATTTTTACCTCCACCAACTCATAATGACAAACTTCCACTGCCAAGTATAGAACTAACTC"
			+ "AAGCGCAAATAAGTCTAGTTGCTTCAGGTGCTGGTGCTCGTGGAAGCTCCGCAGCAGCTTAA";
	private static AnnotationRecord trp1CDS_;
	private static AnnotationPartItem trp1CDSItem_;
	private static final String trp1CDSSequence_ = "ATGTCTGTTATTAATTTCACAGGTAGTTCTGGTCCATTGGTGAAAGTTTGCGGCTTG"
			+ "CAGAGCACAGAGGCCGCAGAATGTGCTCTAGATTCCGATGCTGACTTGCTGGGTATTATATGTGTGCCCAATAGAAAGAGAACAATTGACCCGG"
			+ "TTATTGCAAGGAAAATTTCAAGTCTTGTAAAAGCATATAAAAATAGTTCAGGCACTCCGAAATACTTGGTTGGCGTGTTTCGTAATCAACCTAA"
			+ "GGAGGATGTTTTGGCTCTGGTCAATGATTACGGCATTGATATCGTCCAACTGCATGGAGATGAGTCGTGGCAAGAATACCAAGAGTTCCTCGGT"
			+ "TTGCCAGTTATTAAAAGACTCGTATTTCCAAAAGACTGCAACATACTACTCAGTGCAGCTTCACAGAAACCTCATTCGTTTATTCCCTTGTTTGA"
			+ "TTCAGAAGCAGGTGGGACAGGTGAACTTTTGGATTGGAACTCGATTTCTGACTGGGTTGGAAGGCAAGAGAGCCCCGAAAGCTTACATTTTATGT"
			+ "TAGCTGGTGGACTGACGCCAGAAAATGTTGGTGATGCGCTTAGATTAAATGGCGTTATTGGTGTTGATGTAAGCGGAGGTGTGGAGACAAATGGT"
			+ "GTAAAAGACTCTAACAAAATAGCAAATTTCGTCAAAAATGCTAAGAAATAG";	
	private static AnnotationRecord a0_;
	private static AnnotationPartItem a0Item_;
	private static final String a0Sequence_ = HomologyRegions.A0;
	private static AnnotationRecord ar_;
	private static AnnotationPartItem arItem_;
	private static final String arSequence_ = HomologyRegions.AR;
	private static AnnotationRecord b0_;
	private static AnnotationPartItem b0Item_;
	private static final String b0Sequence_ = HomologyRegions.B0;
	private static AnnotationRecord br_;
	private static AnnotationPartItem brItem_;
	private static final String brSequence_ = HomologyRegions.BR;
	private static AnnotationRecord c0_;
	private static AnnotationPartItem c0Item_;
	private static final String c0Sequence_ = HomologyRegions.C0;
	private static AnnotationRecord cr_;
	private static AnnotationPartItem crItem_;
	private static final String crSequence_ = HomologyRegions.CR;
	private static AnnotationRecord d0_;
	private static AnnotationPartItem d0Item_;
	private static final String d0Sequence_ = HomologyRegions.D0;
	private static AnnotationRecord dr_;
	private static AnnotationPartItem drItem_;
	private static final String drSequence_ = HomologyRegions.DR;
	private static AnnotationRecord e0_;
	private static AnnotationPartItem e0Item_;
	private static final String e0Sequence_ = HomologyRegions.E0;
	private static AnnotationRecord er_;
	private static AnnotationPartItem erItem_;
	private static final String erSequence_ = HomologyRegions.ER;
	private static AnnotationRecord f0_;
	private static AnnotationPartItem f0Item_;
	private static final String f0Sequence_ = "AAAGTATTGGTGTGAGTAATCGCTATCTCTTCTACGACTGGCTACAACAA";
	
	
	static {
		ura3CDS_ = new AnnotationRecord("CDS", TuSubunitDirection.FORWARD);
		ura3CDSItem_ = new AnnotationPartItem();
		ura3CDSItem_.setPartSequence(ura3CDSSequence_);
		ura3CDS_.setAnnotationPartItem(ura3CDSItem_);
		ura3CDS_.getQualifiers().add("/label=URA3");
		
		leu2CDS_ = new AnnotationRecord("CDS", TuSubunitDirection.FORWARD);
		leu2CDSItem_ = new AnnotationPartItem();
		leu2CDSItem_.setPartSequence(leu2CDSSequence_);
		leu2CDS_.setAnnotationPartItem(leu2CDSItem_);
		leu2CDS_.getQualifiers().add("/label=LEU2");
		
		his3CDS_ = new AnnotationRecord("CDS", TuSubunitDirection.FORWARD);
		his3CDSItem_ = new AnnotationPartItem();
		his3CDSItem_.setPartSequence(his3CDSSequence_);
		his3CDS_.setAnnotationPartItem(his3CDSItem_);
		his3CDS_.getQualifiers().add("/label=HIS3");
		
		lys2CDS_ = new AnnotationRecord("CDS", TuSubunitDirection.FORWARD);
		lys2CDSItem_ = new AnnotationPartItem();
		lys2CDSItem_.setPartSequence(lys2CDSSequence_);
		lys2CDS_.setAnnotationPartItem(lys2CDSItem_);
		lys2CDS_.getQualifiers().add("/label=LYS2");
		
		trp1CDS_ = new AnnotationRecord("CDS", TuSubunitDirection.FORWARD);
		trp1CDSItem_ = new AnnotationPartItem();
		trp1CDSItem_.setPartSequence(trp1CDSSequence_);
		trp1CDS_.setAnnotationPartItem(trp1CDSItem_);
		trp1CDS_.getQualifiers().add("/label=TRP1");
		
		
		a0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		a0Item_ = new AnnotationPartItem();
		a0Item_.setPartSequence(a0Sequence_);
		a0_.setAnnotationPartItem(a0Item_);
		a0_.getQualifiers().add("/label=A0*");
		
		ar_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		arItem_ = new AnnotationPartItem();
		arItem_.setPartSequence(arSequence_);
		ar_.setAnnotationPartItem(arItem_);
		ar_.getQualifiers().add("/label=AR*");
		
		b0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		b0Item_ = new AnnotationPartItem();
		b0Item_.setPartSequence(b0Sequence_);
		b0_.setAnnotationPartItem(b0Item_);
		b0_.getQualifiers().add("/label=B0*");
		
		br_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		brItem_ = new AnnotationPartItem();
		brItem_.setPartSequence(brSequence_);
		br_.setAnnotationPartItem(brItem_);
		br_.getQualifiers().add("/label=BR*");
		
		c0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		c0Item_ = new AnnotationPartItem();
		c0Item_.setPartSequence(c0Sequence_);
		c0_.setAnnotationPartItem(c0Item_);
		c0_.getQualifiers().add("/label=C0*");
		
		cr_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		crItem_ = new AnnotationPartItem();
		crItem_.setPartSequence(crSequence_);
		cr_.setAnnotationPartItem(crItem_);
		cr_.getQualifiers().add("/label=CR*");
		
		d0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		d0Item_ = new AnnotationPartItem();
		d0Item_.setPartSequence(d0Sequence_);
		d0_.setAnnotationPartItem(d0Item_);
		d0_.getQualifiers().add("/label=D0*");
		
		dr_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		drItem_ = new AnnotationPartItem();
		drItem_.setPartSequence(drSequence_);
		dr_.setAnnotationPartItem(drItem_);
		dr_.getQualifiers().add("/label=DR*");
		
		e0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		e0Item_ = new AnnotationPartItem();
		e0Item_.setPartSequence(e0Sequence_);
		e0_.setAnnotationPartItem(e0Item_);
		e0_.getQualifiers().add("/label=E0*");
		
		er_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		erItem_ = new AnnotationPartItem();
		erItem_.setPartSequence(erSequence_);
		er_.setAnnotationPartItem(erItem_);
		er_.getQualifiers().add("/label=ER*");
		
		f0_ = new AnnotationRecord("Region", TuSubunitDirection.FORWARD);
		f0Item_ = new AnnotationPartItem();
		f0Item_.setPartSequence(f0Sequence_);
		f0_.setAnnotationPartItem(f0Item_);
		f0_.getQualifiers().add("/label=F0*");
	}
	
	public static List<AnnotationRecord> getModuleSpecific_level_1_annotations(Module module) {
		ArrayList<AnnotationRecord> annotations = new ArrayList<AnnotationRecord>();
		if (module.getName().contains("A")) {
			 annotations.add(ura3CDS_);
			 annotations.add(a0_);
			 annotations.add(ar_);
			 annotations.add(b0_);
		} else if (module.getName().contains("B")) {
			annotations.add(leu2CDS_);
			annotations.add(b0_);
			annotations.add(br_);
			annotations.add(c0_);
		} else if (module.getName().contains("C")) {
			 annotations.add(his3CDS_);
			 annotations.add(c0_);
			 annotations.add(cr_);
			 annotations.add(d0_);
		} else if (module.getName().contains("D")) {
			 annotations.add(lys2CDS_);
			 annotations.add(d0_);
			 annotations.add(dr_);
			 annotations.add(e0_);
		} else if (module.getName().contains("E")) {
			 annotations.add(trp1CDS_);
			 annotations.add(e0_);
			 annotations.add(er_);
			 annotations.add(f0_);
		} 
		return annotations;
	}
}