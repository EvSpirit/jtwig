/**
 * Copyright 2012 Lyncode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lyncode.jtwig.manager;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.parboiled.common.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourceLoader;

/**
 * @author "João Melo <jmelo@lyncode.com>"
 *
 */
public class ServletContextResource implements JtwigResource {
	private static Logger log = LogManager.getLogger(ServletContextResource.class);
	private ServletContext ctx;
	private Resource rsc;
	private String relativePath;
	
	public static void main (String... args) {
		File test = new File("../","../ola");
		log.debug(test.getParent());
		System.out.println(test.getParent());
	}
	
	public ServletContextResource (ServletContext ctx) {
		rsc = null;
		this.ctx = ctx;
	}
	
	private ServletContextResource (ServletContext ctx, String relativePath) {
		this.ctx = ctx;
		this.relativePath = relativePath;
		ServletContextResourceLoader loader = new ServletContextResourceLoader(ctx);
		this.rsc = loader.getResource(relativePath);
	}
	
	
	@Override
	public String retrieve() throws IOException {
		if (rsc != null) return FileUtils.readAllText(rsc.getInputStream());
		else return null;
	}

	@Override
	public JtwigResource getRelativeResource(String relativeToCurrent)
			throws IOException {
		File f;
		if (this.relativePath != null)
			f = new File((new File(relativePath)).getParent(), relativeToCurrent);
		else
			f = new File(relativeToCurrent);
		
		return new ServletContextResource(ctx, f.getPath());
	}
	
	/*private Resource resource;
	private ServletContextResourceLoader loader;
	private File parent;
	private String filename;
	
	public ServletContextResourceManager (ServletContext context, String filename) throws TemplateBuildException {
		File thisFile = new File(filename);
		filename = thisFile.getPath();
		log.debug("Filename: "+thisFile.getPath());
		this.loader = new ServletContextResourceLoader(context);
		this.resource = this.loader.getResource(filename);
		
		this.filename = filename;
		this.parent = (new File(filename)).getParentFile();
		if (this.parent == null)
			throw new TemplateBuildException("File "+ filename+ " has no parent directory");
	}
	
	
	@Override
	public InputStream getResource() throws IOException {
		return this.resource.getInputStream();
	}

	@Override
	public String getFile(String relative) throws IOException {
		String parentPath = this.parent.getPath();
		if (!parentPath.endsWith(File.separator)) 
			parentPath += File.separator;
		return parentPath + relative;
	}


	@Override
	public String getPath() {
		return this.filename;
	}*/

}
