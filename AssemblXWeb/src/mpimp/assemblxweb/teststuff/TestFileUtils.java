package mpimp.assemblxweb.teststuff;

import mpimp.assemblxweb.util.J5FileUtils;

public class TestFileUtils {

	public static void main(String[] args) {
		String sourcePath = "/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir/Jgremmels_7";
		String targetPath = "/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir/Jgremmels_7.zip";
		
		try {
			J5FileUtils.zipDirectory(sourcePath, targetPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
