package org.example;

import org.example.config.AppContextConfig;
import org.example.config.WebContextConfig;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext){


        //loading app config
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(AppContextConfig.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));


        //loading web config
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebContextConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        ServletRegistration.Dynamic servlet = servletContext.addServlet("h2-console",new WebServlet());
        servlet.setLoadOnStartup(2);
        servlet.addMapping("/console/*");
    }
}
