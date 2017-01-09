package mpimp.assemblxweb.teststuff;

import java.util.ArrayList;
import java.util.List;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TuSubunit;

public class TestPlayground {

	public static void main(String[] args) {
		TestPlayground tp = new TestPlayground();
		tp.methodSubstring();
	}

	public void methodParent() {
		AnnotationPartItem annotationPartItem = null;
		methodChild(annotationPartItem);
		if (annotationPartItem != null) {
			System.out.println(annotationPartItem.getStartPosition() + ".." + annotationPartItem.getEndPosition());
		} else {
			System.out.println("Item is null!");
		}
	}
	
	public void methodChild(AnnotationPartItem item) {
		item = new AnnotationPartItem();
		item.setStartPosition(10);
		item.setEndPosition(19);
	}
	
	public void methodSubstring() {
		String rawSequence = "tACTGTTCTTCTAGTGTAGCCGTAGTTAGGCCACCACTTCAAGAACTCTGTAGCACCGCCTACATACCTCGCTCTGCTAATCCTGTTACCAGTGGCTGCTGCCAGTGGCGATAAGTCGTGTCTTACCGGGTTGGACTCAAGACGATAGTTACCGGATAAGGCGCAGCGGTCGGGCTGAACGGGGGGTTCGTGCACACAGCCCAGCTTGGAGCGAACGACCTACACCGAACTGAGATACCTACAGCGTGAGCTATGAGAAAGCGCCACGCTTCCCGAAGGGAGAAAGGCGGACAGGTATCCGGTAAGCGGCAGGGTCGGAACAGGAGAGCGCACGAGGGAGCTTCCAGGGGGAAACGCCTGGTATCTTTATAGTCCTGTCGGGTTTCGCCACCTCTGACTTGAGCGTCGATTTTTGTGATGCTCGTCAGGGGGGCGGAGCCTATGGAAAAACGCCAGCAACGCGGCCTTTTTACGGTTCCTGGCCTTTTGCTGGCCTTTTGCTCATGATATAATTAAATTGAAGCTCTAATTTGTGAGTTTAGTATACATGCATTTACTTATAATACAGTTTTTTAGTTTTGCTGGCCGCATCTTCTCAAATATGCTTCCCAGCCTGCTTTTCTGTAACGTTCACCCTCTACCTTAGCATCCCTTCCCTTTGCAAATAGTCCTCTTCCAACAATAATAATGTCAGATCCTGTAGAGACCACATCATCCACGGTTCTATACTGTTGACCCAATGCGTCTCCCTTGTCATCTAAACCCACACCGGGTGTCATAATCAACCAATCGTAACCTTCATCTCTTCCACCCATGTCTCTTTGAGCAATAAAGCCGATAACAAAATCTTTGTCGCTCTTCGCAATGTCAACAGTACCCTTAGTATATTCTCCAGTAGATAGGGAGCCCTTGCATGACAATTCTGCTAACATCAAAAGGCCTCTAGGTTCCTTTGTTACTTCTTCTGCCGCCTGCTTCAAACCGCTAACAATACCTGGGCCCACCACACCGTGTGCATTCGTAATGTCTGCCCATTCTGCTATTCTGTATACACCCGCAGAGTACTGCAATTTGACTGTATTACCAATGTCAGCAAATTTTCTGTCTTCGAAGAGTAAAAAATTGTACTTGGCGGATAATGCCTTTAGCGGCTTAACTGTGCCCTCCATGGAAAAATCAGTCAAGATATCCACATGTGTTTTTAGTAAACAAATTTTGGGACCTAATGCTTCAACTAACTCCAGTAATTCCTTGGTGGTACGAACATCCAATGAAGCACACAAGTTTGTTTGCTTTTCGTGCATGATATTAAATAGCTTGGCAGCAACAGGACTAGGATGAGTAGCAGCACGTTCCTTATATGTAGCTTTCGACATGATTTATCTTCGTTTCCTGCAGGTTTTTGTTCTGTGCAGTTGGGTTAAGAATACTGGGCAATTTCATGTTTCTTCAACACTACATATGCGTATATATACCAATCTAAGTCTGTGCTCCTTCCTTCGTTCTTCCTTCTGTTCGGAGATTACCGAATCAAAAAAATTTCAAAGAAACCGAAATCAAAAAAAAGAATAAAAAAAAAATGATGAATTGAATTGAAAAGCTAGCTTATCGATGATAAGCTGTCAAAGATGAGAATTAATTCCACGGACTATAGACTATACTAGATACTCCGTCTACTGTACGATACACTTCCGCTCAGGTCCTTGTCCTTTAACGAGGCCTTACCACTCTTTTGTTACTCTATTGATCCAGCTCAGCAAAGGCAGTGTGATCTAAGATTCTATCTTCGCGATGTAGTAAAACTAGCTAGACCGAGAAAGAGACTAGAAATGCAAAAGGCACTTCTACAATGGCTGCCATCATTATTATCCGATGTGACGCTGCAGCTTCTCAATGATATTCGAATACGCTTTGAGGAGATACAGCCTAATATCCGACAAACTGTTTTACAGATTTACGATCGTACTTGTTACCCATCATTGAATTTTGAACATCCGAACCTGGGAGTTTTCCCTGAAACAGATAGTATATTTGAACCTGTATAATAATATATAGTCTAGCGCTTTACGGAAGACAATGTATGTATTTCGGTTCCTGGAGAAACTATTGCATCTATTGCATAGGTAATCTTGCACGTCGCATCCCCGGTTCATTTTCTGCGTTTCCATCTTGCACTTCAATAGCATATCTTTGTTAACGAAGCATCTGTGCTTCATTTTGTAGAACAAAAATGCAACGCGAGAGCGCTAATTTTTCAAACAAAGAATCTGAGCTGCATTTTTACAGAACAGAAATGCAACGCGAAAGCGCTATTTTACCAACGAAGAATCTGTGCTTCATTTTTGTAAAACAAAAATGCAACGCGACGAGAGCGCTAATTTTTCAAACAAAGAATCTGAGCTGCATTTTTACAGAACAGAAATGCAACGCGAGAGCGCTATTTTACCAACAAAGAATCTATACTTCTTTTTTGTTCTACAAAAATGCATCCCGAGAGCGCTATTTTTCTAACAAAGCATCTTAGATTACTTTTTTTCTCCTTTGTGCGCTCTATAATGCAGTCTCTTGATAACTTTTTGCACTGTAGGTCCGTTAAGGTTAGAAGAAGGCTACTTTGGTGTCTATTTTCTCTTCCATAAAAAAAGCCTGACTCCACTTCCCGCGTTTACTGATTACTAGCGAAGCTGCGGGTGCATTTTTTCAAGATAAAGGCATCCCCGATTATATTCTATACCGATGTGGATTGCGCATACTTTGTGAACAGAAAGTGATAGCGTTGATGATTCTTCATTGGTCAGAAAATTATGAACGGTTTCTTCTATTTTGTCTCTATATACTACGTATAGGAAATGTTTACATTTTCGTATTGTTTTCGATTCACTCTATGAATAGTTCTTACTACAATTTTTTTGTCTAAAGAGTAATACTAGAGATAAACATAAAAAATGTAGAGGTCGAGTTTAGATGCAAGTTCAAGGAGCGAAAGGTGGATGGGTAGGTTATATAGGGATATAGCACAGAGATATATAGCAAAGAGATACTTTTGAGCAATGTTTGTGGAAGCGGTATTCGCAATGGGAAGCTCCACCCCGGTTGATAATCAGAAAAGCCCCAAAAACAGGAAGATTGTATAAGCAAATATTTAAATTGTAATTCTTTCCTGCGTTATCCCCTGATTCTGTGGATAACCGTATTACCGCCTTTGAGTGAGCTGATACCGCTCGCCGCAGCCGAACGACCGAGCGCAGCGAGTCAGTGAGCGAGGAAGCGGAAGAGGGTCTGACGCTCAGTGGAACGAAAACTCACGTTAAGGGATTTTGGTCATGAGATTATCAAAAAGGATCTTCACCTAGATCCTTTTAAATTAAAAATGAAGTTTTAAATCAATCTAAAGTATATATGAGTAAACTTGGTCTGACAGTCAGAAGAACTCGTCAAGAAGGCGATAGAAGGCGATGCGCTGCGAATCGGGAGCGGCGATACCGTAAAGCACGAGGAAGCGGTCAGCCCATTCGCCGCCAAGCTCTTCAGCAATATCACGGGTAGCCAACGCTATGTCCTGATAGCGGTCCGCCACACCCAGCCGGCCACAGTCGATGAATCCAGAAAAGCGGCCATTTTCCACCATGATATTCGGCAAGCAGGCATCGTCATGGGTCACGACGAGATCCTCGCCGTCGGGCATGCTCGCCTTGAGCCTGGCGAACAGTTCGGCTGGCGCGAGCCCCTGATGCTCTTCGTCCAGATCATCCTGATCGACAAGACCGGCTTCCATCCGAGTACGTGCTCGCTCGATGCGATGTTTCGCTTGGTGGTCGAATGGGCAGGTAGCCGGATCAAGCGTATGCAGCCGCCGCATTGCATCAGCCATGATGGATACTTTCTCGGCAGGAGCAAGGTGAGATGACAGGAGATCCTGCCCCGGCACTTCGCCCAATAGCAGCCAGTCCCTTCCCGCTTCAGTGACAACGTCGAGCACAGCTGCGCAAGGAACGCCCGTCGTGGCCAGCCACGATAGCCGCGCTGCCTCGTCTTGCAGTTCATTCAGGGCACCGGACAGGTCGGTCTTGACAAAAAGAACCGGGCGCCCCTGCGCTGACAGCCGGAACACGGCGGCATCAGAGCAGCCGATTGTCTGTTGTGCCCAGTCATAGCCGAATAGCCTCTCCACCCAAGCGGCCGGAGAACCTGCGTGCAATCCATCTTGTTCAATCATACTCTTCCTTTTTCAATATTATTGAAGCATTTATCAGGGTTATTGTCTCATGAGCGGATACATATTTGAATGTATTTAGAAAAATAAACAAATAGGGGTTCCGCGCACATTTCCCCGAAAAGTGCCACCTGACGTCGGACGGATCGCTTGCCTGTAACTTACACGCGCCTCGTGGCGCGCCCCTGCAGGATTTAAATGGCCGGCCGTTTAAACTAAAGTGAGATTCGCACAATAAACGCTGCTTCTGTTAAGATAATGAAAACCACGATAATGAGATAAGAAAAATCATGATGGAAGAACAACCACGTTGACGAACTGAGTGACTAATAAATAAATATATATGTATAAATAAAAAATAAATATGAATATTTTAATAGACGGAAGAAAGAAAATATGTGTGAATAACCAAATAGGAAATAAACAAAAGCACATTCACTGCCCACTTTCCAGTCTGGAAACCTGTCTTGCTAGCTGCATGAGTGAATCAGCCAATGCCCGGGGAGAGGCAGTTTGTGTATTGGGTGCCAGGGTGGTTTTTCTCTTCACCAGTGAGACTGGCAACAGCTGATTGCCCTTCACCGCCTGGCCCTGAGAGAGTTGCAGCAAGCGGTCCACGCTGGTTTGCCCCAGCAGGCGAAAATCCTGTTTGATGGTGGTTAACGGCGGGATATAACATGAGCTGTCTTCGGTATCGTCGTATCCCACTACCGAGATATCTGCACCAACTCTCAGCCCAGACTCAGTAATGGCTCTCATTGCACCCAGTGCCATCTGATCATTGGCAACCAGCATTGCAGTGGGAACAATGCCCTCATTCAGCATTTGCATGGTTTGTTGAAACCCAGACATGGCACTCCAGTCCCCTTCTCTTTCAGCTATTGGCTGAATTTGATTCCTAGTGAGATATTTATGCCAGCCGGCCAGTCTCAGCCTTGCTGAGACAGAACTGAGTGGGCCCGCAAGCAGTGCAATTTGCTGGTGTCCCAATGCAACCAGATGCTCCACACCCAGTCTTGTACCATCTTCATGGGAGAAAATAATACTGTTGATGGGTGTCTGGTCAGAGACATCAAGAAAGAGTGCTGGAACATTAGTGCAGGCAGCTTCCACAGCAATGGCATCCTGGTCATCCAGTGGATAGTTAATGATCAGCCCACTGACTCTTTGTGCCAGAAGATTGTGCACAGCAGCTTTACAGGCTTCAACTCCACTTCTTTCTACCATTGACACCACCACAGAGGCTCCCAGTTGATCAGCTCTAGATTTAATGGCTGCCACAATTTGAGATGGTGCATGCAGGGCCAGACTGGAGGTGGCAACTCCAATCAGCAAGCTCTGTTTGCCTGCCAGTTGTTGTGCCACTCTGTTGGGAATGTAATTCAGCTCTGCCATGGCTGCTTCCACTTTTTCCCTGGTTTTGGCAGAAACATGGCTGGCCTGGTTCACCACTCTGGAAACAGTCTGATAAGAGACACCGGCATACTCTGCGACATCATACAATGTTACTGGTTTCATTTTGATTGATTTGACTGTGTTATTTTGCGTGAGGTTATGAGTAGAAAATAATAATTGAGAAAGGAATATGACAAGAAATATGAAAATAAAGGGAACAAACCCAAATCTGATTGCAAGGAGAGTGAAAGAGCCTTGTTTATATATTTTTTTTTCCTATGTTCAACGAGGACAGCTAGGTTTATGCAAAAATGTGCCATCACCATAAGCTGATTCAAATGAGCTAAAAAAAAAATAGTTAGAAAATAAGGTGGTGTTGAACGATAGCAAGTAGATCAAGACACCGTCTAACAGAAAAAGGGGCAGCGGACAATATTATGCAATTATGAAGAAAAGTACTCAAAGGGTCGGAAAAATATTCAAACGATATTTGCATAAAATCCTCAATTGATTGATTATTCCATAGTAAAATACCGTAACAACACAAAATTGTTCTCAAATTCATAAATTATTCATTTTTTCCACGAGCCTCATCACACGAAAAGTCAGAAGAGCATACATAATCTTTTAAATGCATAGGTTATGCATTTTGCAAATGCCACCAGGCAACAAAAATATGCGTTTAGCGGGCGGAATCGGGAAGGAAGCCGGAACCACCAAAAACTGGAAGCTACGTTTTTAAGGAAGGTATGGGTGCAGTGTGCTTATCTCAAGAAATATTAGTTATGATATAAGGTGTTGAAGTTTAGAGATAGGTAAATAAACGCGGGGTGTGTTTATTACATGAAGAAGAAGTTAGTTTCTGCCTTGCTTGTTTATCTTGCACATCACATCAGCGGAACATATGCTCACCCAGTCGCATGTTGTTACCCTCATGAAACATGTATGAGATATTACTTTGAATAGGTTACTTGCATGCATTTCGGCGTGCGGGTACCACCTCTTTGCATGAGCTTAGATTCTGTGAAACTCTCAATAAAGGGACTTCGTCATTTAAATGGCGCGCCGTTTAAACCCTGCAGGGGCCGGCCCCGTAGAAAAGATCAAAGGATCTTCTTGAGATCCTTTTTTTCTGCGCGTAATCTGCTGCTTGCAAACAAAAAAACCACCGCTACCAGCGGTGGTTTGTTTGCCGGATCAAGAGCTACCAACTCTTTTTCCGAAGGTAACTGGCTTCAGCAGAGCGCAGATACCAAAT";
		
		String part = rawSequence.substring(4429, 6659);
		//Integer index = rawSequence.indexOf("GGAACGTGCT", 50);
		System.out.println(part);
		//System.out.println(index);
	}
	
	public void methodDivision() {
		Integer backbone = 4464;
		Integer fragment = 3718;
		System.out.println(backbone / fragment);
		
		Double quotient = (double) backbone / fragment;
		
		System.out.println(quotient);
	}
	
	public void testDeletionInReferenceCollection() {
		AssemblXWebModel model = new AssemblXWebModel();
		List<Module> modules = new ArrayList<Module>();
		Module m1 = new Module();
		Module m2 = new Module();
		Module m3 = new Module();
		modules.add(m1);
		modules.add(m2);
		modules.add(m3);
		model.setModules(modules);
		
		System.out.println(model.getModules().size());
		
		List<Module> modulesReference = model.getModules();
		modulesReference.remove(1);
		
		System.out.println(model.getModules().size());
	}
	
	public void getIndex() {
		TuSubunitForTesting tst = new TuSubunitForTesting();
		tst.setStart(3);
		Integer start = tst.getStart();
		Integer otherStart = tst.getStart() - 1;
		
		System.out.println(start);
		System.out.println(otherStart);
	}
}