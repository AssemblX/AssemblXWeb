package mpimp.assemblxweb.teststuff;

public class StringStuffTester {

	public static void main(String[] args) {
		extractSequenceFromLinearizedCircularRawSequence();
	}
	
	public static void extractSequenceFromLinearizedCircularRawSequence() {
		String rawSequence = "TCTAGACCAGCCAGGACAGAAATGCCTCGACTTCGCTGCTGCCCAAGGTTGCCGGGTGACGCACACCGTGGAAACGGATGAAGGCACGAACCCAGTGGACATAAGCCTGTTCGGTTCGTAAGCTGTAATGCAAGTAGCGTATGCG";
		
		String H0 = "CCGTGGAAACGGATGAAGGCA";
		String HR = "AGCGTATGCGTCTAGACCAGCC";
		
		String expectedSequence = "CCGTGGAAACGGATGAAGGCACGAACCCAGTGGACATAAGCCTGTTCGGTTCGTAAGCTGTAATGCAAGTAGCGTATGCGTCTAGACCAGCC";
		
		int indexH0 = rawSequence.indexOf(H0);
		int indexHR = rawSequence.indexOf(HR);
		
		System.out.println(indexH0);
		System.out.println(indexHR);
		
		while (indexHR == -1 || indexH0 == -1) {
			String firstBase = rawSequence.substring(0,1);
			System.out.println(firstBase);
			rawSequence = rawSequence.substring(1);
			rawSequence += firstBase;
			indexHR = rawSequence.indexOf(HR);
			indexH0 = rawSequence.indexOf(H0);
			System.out.println(rawSequence);
		}
		
//		indexH0 = rawSequence.indexOf(H0);
//		indexHR = rawSequence.indexOf(HR);
		
		System.out.println(indexH0);
		System.out.println(indexHR);
		
		String partSequence = rawSequence.substring(indexH0, indexHR + HR.length());
		
		System.out.println(expectedSequence);
		System.out.println(partSequence);
	}
}
