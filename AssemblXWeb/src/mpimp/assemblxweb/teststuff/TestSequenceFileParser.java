package mpimp.assemblxweb.teststuff;

import java.io.IOException;

import mpimp.assemblxweb.util.SequenceFileParser;

public class TestSequenceFileParser {

	public static void main(String[] args) {
//		String fileContent = "LOCUS       pL0_00005      4713 bp    DNA     circular UNA \n"
//				+ "ACCESSION   pL0_00005\n"
//				+ "VERSION     pL0_00005.1\n"
//				+ "KEYWORDS    .\n"
//				+ "FEATURES             Location/Qualifiers\n"
//				+ "  rep_origin      1..460\n"
//				+ "/label=pBR322_origin\n"
//				+ "source          1..501\n"
//				+ "/mol_type=\"other DNA\"\n"
//				+ "/label=pUC19\n"
//				+ "primer          complement(111..130)\n"
//				+ "\n"
//				+ "ORIGIN\n"
//				+ "        1 TGTTCTTCTA GTGTAGCCGT AGTTAGGCCA CCACTTCAAG AACTCTGTAG CACCGCCTAC\n"
//				+ "61 ATACCTCGCT CTGCTAATCC TGTTACCAGT GGCTGCTGCC AGTGGCGATA AGTCGTGTCT\n"
//				+ "121 TACCGGGTTG GACTCAAGAC GATAGTTACC GGATAAGGCG CAGCGGTCGG GCTGAACGGG\n"
//				+ "181 GGGTTCGTGC ACACAGCCCA GCTTGGAGCG AACGACCTAC ACCGAACTGA GATACCTACA\n"
//				+ "241 GCGTGAGCTA TGAGAAAGCG CCACGCTTCC CGAAGGGAGA AAGGCGGACA GGTATCCGGT\n"
//				+ "301 AAGCGGCAGG GTCGGAACAG GAGAGCGCAC GAGGGAGCTT CCAGGGGGAA ACGCCTGGTA\n"
//				+ "361 TCTTTATAGT CCTGTCGGGT TTCGCCACCT CTGACTTGAG CGTCGATTTT TGTGATGCTC\n"
//				+ "421 GTCAGGGGGG CGGAGCCTAT GGAAAAACGC CAGCAACGCG GCCTTTTTAC GGTTCCTGGC\n"
//				+ "481 CTTTTGCTGG CCTTTTGCTC ATGATATAAT TAAATTGAAG CTCTAATTTG TGAGTTTAGT\n"
//				+ "     " + "//\n";
//		String sequence = SequenceFileParser
//				.extractSequenceFromGenbankFileContent(fileContent);
		
		String filePath = "F:\\tmp\\sequences\\pX1_00031.gb";
		
		String sequence;
		try {
			sequence = SequenceFileParser.extractSequenceFromGenbankFile(filePath);
			System.out.println(sequence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
