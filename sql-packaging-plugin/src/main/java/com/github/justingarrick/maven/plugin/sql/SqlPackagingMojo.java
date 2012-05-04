/*
 * Copyright (C) 2012 Justin Garrick.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.justingarrick.maven.plugin.sql;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * This is a custom packaging plugin that allows the
 * user POM to declare a .sql file as an artifact. To
 * use it, declare that sql is the packaging type:
 * <pre>
 * {@code
 * <packaging>sql</packaging>
 * }
 * </pre>
 * and turn on the extension:
 * <pre>
 * {@code
 * <build>
 *     <extensions>
 *         <extension>
 *             <groupId>com.github.justingarrick</groupId>
 *             <artifactId>sql-packaging-plugin</artifactId>
 *             <version>1.0.0-SNAPSHOT</version>
 *         </extension>
 *     </extensions>
 *     ...
 * </build>
 * }
 * </pre>
 * The plugin expects a file in \target with a standard
 * Maven name of artifactId-version.sql.
 *
 * @goal custom 
 * @phase package
 */
public class SqlPackagingMojo extends AbstractMojo {
	
	/**
	* The maven project.  This is injected by Maven.
	* 
	* @parameter expression="${project}"
	* @required
	* @readonly
	*/
	private MavenProject project;

	/**
	* Build directory.  This is injected by Maven.
	* 
	* @parameter expression="${project.build.directory}"
	* @required
	*/
	private File buildDirectory;
	
	/** {@inheritDoc} */
	public void execute() throws MojoExecutionException {
	    String fileName = project.getBuild().getFinalName() + ".sql";
	    File custFile = new File(buildDirectory, fileName);
	    if (!custFile.exists())
	    	getLog().error("Attempting to set " + custFile.toString() + " as the project artifact, but file does not exist!");
	    project.getArtifact().setFile(custFile);
	}
}
