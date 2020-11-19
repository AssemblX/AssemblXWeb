package mpimp.assemblxweb.db;

import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;

public class Level_0_Promoters {

	private static AnnotationRecord ura3Promoter_;
	private static AnnotationPartItem ura3PromoterItem_;
	private static final String ura3PromoterSequence_ = "TAGCTTTTCAATTCAATTCATCATTTTTTTTTTATTCTTTTTTTTGATTTCGGTTTCTTTGAAATTTTTTT"
			+ "GATTCGGTAATCTCCGAACAGAAGGAAGAACGAAGGAAGGAGCACAGACTTAGATTGGTATATATACGCATATGTAGTGTTGAAGAAACATGAAATTGCCCAGTATTCTTAAC"
			+ "CCAACTGCACAGAACAAAAACCTGCAGGAAACGAAGATAAATC";
	private static AnnotationRecord leu2Promoter_;
	private static AnnotationPartItem leu2PromoterItem_;
	private static final String leu2PromoterSequence_ = "AACTGTGGGAATACTCAGGTATCGTAAGATGCAAGAGTTCGAATCTCTTAGCAACCATTATTTTTTTCCTC"
			+ "AACATAACGAGAACACACAGGGGCGCTATCGCACAGAATCAAATTCGATGACTGGAAATTTTTTGTTAATTTCAGAGGTCGCCTGACGCATATACCTTTTTCAACTGAAAAAT"
			+ "TGGGAGAAAAAGGAAAGGTGAGAGGCCGGAACCGGCTTTTCATATAGAATAGAGAAGCGTTCATGACTAAATGCTTGCATCACAATACTTGAAGTTGACAATATTATTTAAGG"
			+ "ACCTATTGTTTTTTCCAATAGGTGGTTAGCAATCGTCTTACTTTCTAACTTTTCTTACCTTTTACATTTCAGCAATATATATATATATTTCAAGGATATACCATTCTA";
	private static AnnotationRecord his3Promoter_;
	private static AnnotationPartItem his3PromoterItem_;
	private static final String his3PromoterSequence_ = "AATTCCCGTTTTAAGAGCTTGGTGAGCGCTAGGAGTCACTGCCAGGTATCGTTTGAACACGGCATTAGTCA"
			+ "GGGAAGTCATAACACAGTCCTTTCCCGCAATTTTCTTTTTCTATTACTCTTGGCCTCCTCTAGTACACTCTATATTTTTTTATGCCTCGGTAATGATTTTCATTTTTTTTTTT"
			+ "CCCCTAGCGGATGACTCTTTTTTTTTCTTAGCGATTGGCATTATCACATAATGAATTATACATTATATAAAGTAATGTGATTTCTTCGAAGAATATACTAAAAAATGAGCAGG"
			+ "CAAGATAAACGAAGGCAAAG";
	private static AnnotationRecord lys2Promoter_;
	private static AnnotationPartItem lys2PromoterItem_;
	private static final String lys2PromoterSequence_ = "TAGAGGCATCGCACAGTTTTAGCGAGGAAAACTCTTCAATAGTTTTGCCAGCGGAATTCCACTTGCAATTA"
			+ "CATAAAAAATTCCGGCGGTTTTTCGCGTGTGACTCAATGTCGAAATACCTGCCTAATGAACATGAACATCGCCCAAATGTATTTGAAGACCCGCTGGGAGAAGTTCAAGATAT"
			+ "ATAAGTAACAAGCAGCCAATAGTATAAAAAAAAATCTGAGTTTATTACCTTTCCTGGAATTTCAGTGAAAAACTGCTAATTATAGAGAGATATCACAGAGTTACTCACTA";
	private static AnnotationRecord trp1Promoter_;
	private static AnnotationPartItem trp1PromoterItem_;
	private static final String trp1PromoterSequence_ = "AACGACATTACTATATATATAATATAGGAAGCATTTAATAGACAGCATCGTAATATATGTGTACTTTGCAG"
			+ "TTATGACGCCAGATGGCAGTAGTGGAAGATATTCTTTATTGAAAAATAGCTTGTCACCTTACGTACAATCTTGATCCGGAGCTTTTCTTTTTTTGCCGATTAAGAATTAATTC"
			+ "GGTCGAAAAAAGAAAAGGAGAGGGCCAAGAGGGAGGGCATTGGTGACTATTGAGCACGTGAGTATACGTGATTAAGCACACAAAGGCAGCTTGGAGT";	
	
	static {
		ura3Promoter_ = new AnnotationRecord("promoter", TuSubunitDirection.FORWARD);
		ura3PromoterItem_ = new AnnotationPartItem();
		ura3PromoterItem_.setPartSequence(ura3PromoterSequence_);
		ura3Promoter_.setAnnotationPartItem(ura3PromoterItem_);
		ura3Promoter_.getQualifiers().add("/label=URA3_promoter");
		
		leu2Promoter_ = new AnnotationRecord("promoter", TuSubunitDirection.FORWARD);
		leu2PromoterItem_ = new AnnotationPartItem();
		leu2PromoterItem_.setPartSequence(leu2PromoterSequence_);
		leu2Promoter_.setAnnotationPartItem(leu2PromoterItem_);
		leu2Promoter_.getQualifiers().add("/label=LEU2promoter");
		
		his3Promoter_ = new AnnotationRecord("promoter", TuSubunitDirection.FORWARD);
		his3PromoterItem_ = new AnnotationPartItem();
		his3PromoterItem_.setPartSequence(his3PromoterSequence_);
		his3Promoter_.setAnnotationPartItem(his3PromoterItem_);
		his3Promoter_.getQualifiers().add("/label=HIS3promoter");
		
		lys2Promoter_ = new AnnotationRecord("promoter", TuSubunitDirection.FORWARD);
		lys2PromoterItem_ = new AnnotationPartItem();
		lys2PromoterItem_.setPartSequence(lys2PromoterSequence_);
		lys2Promoter_.setAnnotationPartItem(lys2PromoterItem_);
		lys2Promoter_.getQualifiers().add("/label=LYS2promoter");
		
		trp1Promoter_ = new AnnotationRecord("promoter", TuSubunitDirection.FORWARD);
		trp1PromoterItem_ = new AnnotationPartItem();
		trp1PromoterItem_.setPartSequence(trp1PromoterSequence_);
		trp1Promoter_.setAnnotationPartItem(trp1PromoterItem_);
		trp1Promoter_.getQualifiers().add("/label=TRP1promoter");
	}
	
	public static AnnotationRecord getModuleSpecificPromoter(Module module) {
		if (module.getName().contains("A")) {
			return ura3Promoter_;
		} else if (module.getName().contains("B")) {
			return leu2Promoter_;
		} else if (module.getName().contains("C")) {
			return his3Promoter_;
		} else if (module.getName().contains("D")) {
			return lys2Promoter_;
		} else if (module.getName().contains("E")) {
			return trp1Promoter_;
		} else {
			return null;
		}
	}
	
}
