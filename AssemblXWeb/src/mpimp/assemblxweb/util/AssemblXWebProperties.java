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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class AssemblXWebProperties extends Properties {

	private static final long serialVersionUID = -5072940676887770552L;

	public static void setPropertiesPath(String propertiesPath) {
		AssemblXWebProperties.propertiesPath_ = propertiesPath;
	}

	private AssemblXWebProperties(String propertiesFilePath) {
		this.loadProperties(propertiesFilePath);
	}

	public static void reloadProperties() {
		tpcProperties_ = new AssemblXWebProperties(propertiesPath_);
	}
	
	public static AssemblXWebProperties getInstance() {
		if (tpcProperties_ == null) {
			tpcProperties_ = new AssemblXWebProperties(propertiesPath_);
		}
		return tpcProperties_;
	}
	
	private void loadProperties(String propertiesFilePath) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(propertiesFilePath);
			loadFromXML(fis);
			propertiesLoaded_ = true;
		} catch (InvalidPropertiesFormatException e) {
			propertiesLoaded_ = false;
			e.printStackTrace();
		} catch (IOException e) {
			propertiesLoaded_ = false;
			e.printStackTrace();
		}
	}

	public static Boolean getPropertiesLoaded() {
		return propertiesLoaded_;
	}

	private static String propertiesPath_;
	private static AssemblXWebProperties tpcProperties_ = null;
	private static Boolean propertiesLoaded_ = false;
	
}
