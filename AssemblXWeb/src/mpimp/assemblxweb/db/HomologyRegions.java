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

import java.util.HashMap;
import java.util.Map;

public class HomologyRegions {

	public static final String A0 = "AAATAGGAAAGTCTGGTAACTCAGGACACTGTATCTGCTACGCTGTTTAT";
	public static final String A1 = "TAAAGTGAGATTCGCACAATAAACGCTGCTTCTGTT";
	public static final String A2 = "TAGATTCTGTGAAACTCTCAATAAAGGGACTTCGTC";
	public static final String A3 = "TCCTTCCCAGTGAATAGAAAAGTAGTTGCTGTTACA";
	public static final String A4 = "TAATAAGTTCAGTTTCGGATACCTGCCCAATAGAGT";
	public static final String AR = "ATGTCGAAAGCTACATATAAGGAACGTGCTGCTACTCATCCTAGTCCTGTT";
	public static final String B0 = "TAAGGTTTCAGTCACACTACGGATACTTTTACAACGGGAGCAGTTATTCA";
	public static final String B1 = "CAATAAGTGCTATCCTGAATCTCTGGTTGTGAAACA";
	public static final String B2 = "AGTTGAGAATCTATTTTACTACCCGAAGACTGCGTA";
	public static final String B3 = "ATTACGGTCTGTGGATTTCTCACTGAACGCAATAAA";
	public static final String B4 = "TTGGAAGTCTACTCCCTGAACTGTGAAGAATACTAT";
	public static final String BR = "ATGTCTGCCCCTAAGAAGATCGTCGTTTTGCCAGGTGACCACGTTGGTCAA";
	public static final String C0 = "TATTGCGTGTGATACTTCAGGAGAACCGTTATTCCGTAAAACTACAGTCA";
	public static final String C1 = "GGTATCGTAATCGCAACTCACTGTAAGGACTATTTA";
	public static final String C2 = "TAGTAAGTTATTATCGCACTCACGAGAAGCGTATTC";
	public static final String C3 = "ATAAACTCTGAATACTCGGGATTTCCTGGCAATAGT";
	public static final String C4 = "TGGTGATTCAATAAAGCGTCGTTACACAGATACTTC";
	public static final String CR = "ATGACAGAGCAGAAAGCCCTAGTAAAGCGTATTACAAATGAAACCAAGAT";
	public static final String D0 = "CACGAGCGTTGAGGCGGATTTCAGATTATTTACAGACACTATTCTACAAT";
	public static final String D1 = "AAATAACTGCCACTACTGAAGATTCGGTGATTGTCT";
	public static final String D2 = "ACCACTGTAAAGGTATTATTCGGAAGTTCTCTACGA";
	public static final String D3 = "AACTGAGATTGGATAGACTACCTGTTCGTGTAATAC";
	public static final String D4 = "ATTTATCGGAGAAAACCTCGTCGTTGAACCAGATTT";
	public static final String DR = "ATGACTAACGAAAAGGTCTGGATAGAGAAGTTGGATAATCCAACTCTTTCA";
	public static final String E0 = "TCTCTTACAATAAAATAGAATACTCTGCGAAGGGCGTGACACTGCTGTTT";
	public static final String E1 = "AGTTTATCAATCCGCTACAGGACGGTATTTCGTTAT";
	public static final String E2 = "TTGATACGAAGTGTGACAATCCTTTTACTGAACCGA";
	public static final String E3 = "TAAAAGTAAACTCTCACCCGTAGGATTGGTATCTGT";
	public static final String E4 = "TAGTGATAGCGTTCAATACTTCCTGGCACGATAATA";
	public static final String ER = "ATGTCTGTTATTAATTTCACAGGTAGTTCTGGTCCATTGGTGAAAGTTTGC";
	public static final String F0 = "AAAGTATTGGTGTGAGTAATCGCTATCTCTTCTACGACTGGCTACAACAA";

private static Map<String, String> homologyRegionsMap = new HashMap<String, String>();
	
	static {
		homologyRegionsMap.put("A0", A0);
		homologyRegionsMap.put("A1", A1);
		homologyRegionsMap.put("A2", A2);
		homologyRegionsMap.put("A3", A3);
		homologyRegionsMap.put("A4", A4);
		homologyRegionsMap.put("AR", AR);
		homologyRegionsMap.put("B0", B0);
		homologyRegionsMap.put("B1", B1);
		homologyRegionsMap.put("B2", B2);
		homologyRegionsMap.put("B3", B3);
		homologyRegionsMap.put("B4", B4);
		homologyRegionsMap.put("BR", BR);
		homologyRegionsMap.put("C1", C1);
		homologyRegionsMap.put("C2", C2);
		homologyRegionsMap.put("C3", C3);
		homologyRegionsMap.put("C4", C4);
		homologyRegionsMap.put("CR", CR);
		homologyRegionsMap.put("C0", C0);
		homologyRegionsMap.put("C1", C1);
		homologyRegionsMap.put("C2", C2);
		homologyRegionsMap.put("C3", C3);
		homologyRegionsMap.put("C4", C4);
		homologyRegionsMap.put("CR", CR);
		homologyRegionsMap.put("D0", D0);
		homologyRegionsMap.put("D1", D1);
		homologyRegionsMap.put("D2", D2);
		homologyRegionsMap.put("D3", D3);
		homologyRegionsMap.put("D4", D4);
		homologyRegionsMap.put("DR", DR);
		homologyRegionsMap.put("E0", E0);
		homologyRegionsMap.put("E1", E1);
		homologyRegionsMap.put("E2", E2);
		homologyRegionsMap.put("E3", E3);
		homologyRegionsMap.put("E4", E4);
		homologyRegionsMap.put("ER", ER);
		homologyRegionsMap.put("F0", F0);
	}
	
	public static String getHomologyRegion(String key) {
		return homologyRegionsMap.get(key);
	}
	
}
