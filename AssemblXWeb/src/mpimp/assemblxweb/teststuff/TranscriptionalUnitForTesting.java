package mpimp.assemblxweb.teststuff;

import java.util.ArrayList;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit;

public class TranscriptionalUnitForTesting extends TranscriptionalUnit {

	public TranscriptionalUnitForTesting(Module parentModule, Integer position, AssemblXWebModel model) throws Exception {
		super(parentModule, position, model);
	}

	@Override
	protected void init(AssemblXWebModel model) throws Exception {
		setTuSubunits(new ArrayList<TuSubunit>());
		getTuSubunits().add(new TuSubunit(this, 1));
	}
}
