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
package mpimp.assemblxweb.util;

import java.util.HashMap;
import java.util.Map;

import mpimp.assemblxweb.db.TuSubunit.SequenceInputOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitAssemblyStrategy;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitDirection;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitSequenceOption;
import mpimp.assemblxweb.db.TuSubunit.TuSubunitType;

public class AssemblXWebEnumTranslator {

	public AssemblXWebEnumTranslator() {
		prepareTuSubunitTypes();
		prepareTuSubunitDirections();
		prepareTuSubunitAssemblyStrategies();
		prepareTuSubunitSequenceOptions();
		prepareTuSubunitSequenceInputOptions();
	}
	
	public String getTuSubunitTypeName(TuSubunitType type) {
		return tuSubunitTypes_.get(type);
	}
	
	public String getTuSubunitDirectionName(TuSubunitDirection direction) {
		return tuSubunitDirections_.get(direction);
	}
	
	public String getTuSubunitAssemblyStrategyName(TuSubunitAssemblyStrategy strategy) {
		return tuSubunitAssemblyStrategies_.get(strategy);
	}

	public String getTuSubunitSequenceOptionName(TuSubunitSequenceOption sequenceOption) {
		return tuSubunitSequenceOptions_.get(sequenceOption);
	}
	
	public String getTuSubunitSequenceInputOptionName(SequenceInputOption sequenceInputOption) {
		return tuSubunitSequenceInputOptions_.get(sequenceInputOption);
	}
	
	public TuSubunitType getTuSubunitType(String typeName) {
		return tuSubunitTypeNames_.get(typeName);
	}
	
	public TuSubunitDirection getTuSubunitTypeDirection(String directionName) {
		return tuSubunitDirectionNames_.get(directionName);
	}
	
	public TuSubunitAssemblyStrategy getTuSubunitAssemblyStrategy(String strategyName) {
		return tuSubunitAssemblyStrategyNames_.get(strategyName);
	}

	public SequenceInputOption getTuSubunitSequenceInputOption(String sequenceInputOption) {
		return tuSubunitSequenceInputOptionNames_.get(sequenceInputOption);
	}
	
	private void prepareTuSubunitTypes() {
		tuSubunitTypes_ = new HashMap<TuSubunitType, String>();
		tuSubunitTypes_.put(TuSubunitType.NO_TYPE, "misc feature");
		tuSubunitTypes_.put(TuSubunitType.BACKBONE, "backbone");
		tuSubunitTypes_.put(TuSubunitType.PROMOTER, "promoter");
		tuSubunitTypes_.put(TuSubunitType.CODING_SEQUENCE, "CDS");
		tuSubunitTypes_.put(TuSubunitType.TAG, "tag");
		tuSubunitTypes_.put(TuSubunitType.TERMINATOR, "terminator");
		tuSubunitTypes_.put(TuSubunitType.GENE, "gene");
		tuSubunitTypes_.put(TuSubunitType.RES, "RES");
		tuSubunitTypes_.put(TuSubunitType.MISC_RNA, "misc RNA");
		tuSubunitTypes_.put(TuSubunitType.USER_DEFINED, "user_defined");
		tuSubunitTypes_.put(TuSubunitType.OPERATOR, "operator");
		tuSubunitTypes_.put(TuSubunitType.INSULATOR, "insulator");
		tuSubunitTypes_.put(TuSubunitType.ORIGIN_OF_REPLICATION, "origin_of_replication");
		tuSubunitTypes_.put(TuSubunitType.RESTRICTION_SITE, "restriction_site");
		tuSubunitTypes_.put(TuSubunitType.SIGNATURE, "signature");
		tuSubunitTypeNames_ = new HashMap<String, TuSubunitType>();
		tuSubunitTypeNames_.put("misc feature", TuSubunitType.NO_TYPE);
		tuSubunitTypeNames_.put("backbone", TuSubunitType.BACKBONE);
		tuSubunitTypeNames_.put("promoter", TuSubunitType.PROMOTER);
		tuSubunitTypeNames_.put("CDS", TuSubunitType.CODING_SEQUENCE);
		tuSubunitTypeNames_.put("tag", TuSubunitType.TAG);
		tuSubunitTypeNames_.put("terminator", TuSubunitType.TERMINATOR);
		tuSubunitTypeNames_.put("gene", TuSubunitType.GENE);
		tuSubunitTypeNames_.put("RES", TuSubunitType.RES);
		tuSubunitTypeNames_.put("misc RNA", TuSubunitType.MISC_RNA);
		tuSubunitTypeNames_.put("user_defined", TuSubunitType.USER_DEFINED);
		tuSubunitTypeNames_.put("operator", TuSubunitType.OPERATOR);
		tuSubunitTypeNames_.put("insulator", TuSubunitType.INSULATOR);
		tuSubunitTypeNames_.put("origin_of_replication", TuSubunitType.ORIGIN_OF_REPLICATION);
		tuSubunitTypeNames_.put("restriction_site", TuSubunitType.RESTRICTION_SITE);
		tuSubunitTypeNames_.put("signature", TuSubunitType.SIGNATURE);
	}

	private void prepareTuSubunitDirections() {
		tuSubunitDirections_ = new HashMap<TuSubunitDirection, String>();
		tuSubunitDirections_.put(TuSubunitDirection.FORWARD, "forward");
		tuSubunitDirections_.put(TuSubunitDirection.REVERSE, "reverse");
		tuSubunitDirectionNames_ = new HashMap<String, TuSubunitDirection>();
		tuSubunitDirectionNames_.put("forward", TuSubunitDirection.FORWARD);
		tuSubunitDirectionNames_.put("reverse", TuSubunitDirection.REVERSE);
	}
	
	private void prepareTuSubunitAssemblyStrategies() {
		tuSubunitAssemblyStrategies_ = new HashMap<TuSubunitAssemblyStrategy, String>();
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.ANNEALED_OLIGOS, "Annealed Oligos");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.DIRECT_SYNTHESIS, "Direct Synthesis");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_FORWARD, "Embed_in_primer_forward");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_REVERSE, "Embed_in_primer_reverse");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.PCR, "PCR");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.DIGEST, "DIGEST");
		tuSubunitAssemblyStrategies_.put(TuSubunitAssemblyStrategy.NO_STRATEGY, "unspecified");
		tuSubunitAssemblyStrategyNames_ = new HashMap<String, TuSubunitAssemblyStrategy>();
		tuSubunitAssemblyStrategyNames_.put("\"Annealed Oligos\"", TuSubunitAssemblyStrategy.ANNEALED_OLIGOS);
		tuSubunitAssemblyStrategyNames_.put("Annealed Oligos", TuSubunitAssemblyStrategy.ANNEALED_OLIGOS);
		tuSubunitAssemblyStrategyNames_.put("\"Direct Synthesis\"", TuSubunitAssemblyStrategy.DIRECT_SYNTHESIS);
		tuSubunitAssemblyStrategyNames_.put("Direct Synthesis", TuSubunitAssemblyStrategy.DIRECT_SYNTHESIS);
		tuSubunitAssemblyStrategyNames_.put("Embed_in_primer_forward", TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_FORWARD);
		tuSubunitAssemblyStrategyNames_.put("Embed_in_primer_reverse", TuSubunitAssemblyStrategy.EMBED_IN_PRIMER_REVERSE);
		tuSubunitAssemblyStrategyNames_.put("PCR", TuSubunitAssemblyStrategy.PCR);
		tuSubunitAssemblyStrategyNames_.put("DIGEST", TuSubunitAssemblyStrategy.DIGEST);
		tuSubunitAssemblyStrategyNames_.put("unspecified", TuSubunitAssemblyStrategy.NO_STRATEGY);
	}
	
	private void prepareTuSubunitSequenceOptions() {
		tuSubunitSequenceOptions_ = new HashMap<TuSubunitSequenceOption, String>();
		tuSubunitSequenceOptions_.put(TuSubunitSequenceOption.WHOLE_SEQUENCE, "Whole Sequence");
		tuSubunitSequenceOptions_.put(TuSubunitSequenceOption.SPECIFIED_SEQUENCE, "Specified Sequence");
	}
	
	private void prepareTuSubunitSequenceInputOptions() {
		tuSubunitSequenceInputOptions_ = new HashMap<SequenceInputOption, String>();
		tuSubunitSequenceInputOptions_.put(SequenceInputOption.LOAD_FROM_FILE, "load from file");
		tuSubunitSequenceInputOptions_.put(SequenceInputOption.PASTE_AS_TEXT, "paste as text");
		tuSubunitSequenceInputOptionNames_ = new HashMap<String, SequenceInputOption>();
		tuSubunitSequenceInputOptionNames_.put("load from file", SequenceInputOption.LOAD_FROM_FILE);
		tuSubunitSequenceInputOptionNames_.put("paste as text", SequenceInputOption.PASTE_AS_TEXT);
	}
	
	private Map<TuSubunitType, String> tuSubunitTypes_;
	private Map<TuSubunitDirection, String> tuSubunitDirections_;
	private Map<TuSubunitAssemblyStrategy, String> tuSubunitAssemblyStrategies_;
	private Map<TuSubunitSequenceOption, String> tuSubunitSequenceOptions_;
	private Map<SequenceInputOption, String> tuSubunitSequenceInputOptions_;
	private Map<String, TuSubunitType> tuSubunitTypeNames_;
	private Map<String, TuSubunitDirection> tuSubunitDirectionNames_;
	private Map<String, TuSubunitAssemblyStrategy> tuSubunitAssemblyStrategyNames_;
	private Map<String, SequenceInputOption> tuSubunitSequenceInputOptionNames_;
}
