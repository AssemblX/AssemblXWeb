package mpimp.assemblxweb.teststuff;

import mpimp.assemblxweb.util.AssemblXWebEnumTranslator;

public class TestEnumTranslator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AssemblXWebEnumTranslator enumTranslator = new AssemblXWebEnumTranslator();
		String line = "4,4,EFT2_term,forward,\"Direct Synthesis\"";
		String[] elements = line.split(",");
		String element = "";
		//if (elements.length == 4) {
			element = elements[4];
		//}
		System.out.println(element);
		System.out.println(enumTranslator.getTuSubunitAssemblyStrategy(element));
	}

}
