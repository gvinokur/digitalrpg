package com.digitalrpg.web.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.annotations.AbstractDiscoverableAnnotationHandler;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationDecorator;
import org.eclipse.jetty.annotations.AnnotationParser;
import org.eclipse.jetty.annotations.AnnotationParser.DiscoverableAnnotationHandler;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.annotations.ClassNameResolver;
import org.eclipse.jetty.annotations.WebFilterAnnotationHandler;
import org.eclipse.jetty.annotations.WebListenerAnnotationHandler;
import org.eclipse.jetty.annotations.WebServletAnnotationHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.TagLibConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.springframework.web.WebApplicationInitializer;

import com.digitalrpg.web.security.config.SecurityWebApplicationInitializer;

public class DigitalRpgRunnner {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		WebAppContext context = new WebAppContext();
	    context.setServer(server);
	    context.setContextPath("/");
	    context.setWar("src/main/webapp");
	    context.setConfigurations(new Configuration[] {
	    	    new WebXmlConfiguration(),
	    	    new WebInfConfiguration(),
	    	    new TagLibConfiguration(),
	    	    new AnnotationConfiguration() {
	    	        @Override
	    	        public void preConfigure(WebAppContext context) throws Exception {
	    	            MultiMap<String> map = new MultiMap<String>();
	    	            map.add(WebApplicationInitializer.class.getName(), com.digitalrpg.web.config.WebApplicationInitializer.class.getName());
	    	            map.add(WebApplicationInitializer.class.getName(), SecurityWebApplicationInitializer.class.getName());
	    	            context.setAttribute(CLASS_INHERITANCE_MAP, map);
	    	            _classInheritanceHandler = new ClassInheritanceHandler(map);
	    	        }
	    	    }
	    	});

        server.setHandler(context);
        server.start();
        server.join();  
	}

}
