package rewards.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import rewards.config.ApplicationConfig;
import rewards.config.InfrastructureConfig;
import rewards.config.WebMvcConfig;

public class RewardsWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// TODO 01: Create a "root" application context
		// Use ApplicationConfig and InfrastructureConfig classes
		// which have been provided for you.
		// Don't forget to load the "root" application context
		// through a listener.
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class, InfrastructureConfig.class);

		container.addListener(new ContextLoaderListener(rootContext));

		// TODO 02: Create a "dispatcher" application context
		// Use WebMvcConfig class which you will complete in subsequent steps.
		AnnotationConfigWebApplicationContext dispatcher = new AnnotationConfigWebApplicationContext();
		dispatcher.register(WebMvcConfig.class);

		// TODO 03: Create a dispatcher servlet
		// Make it use the "dispatcher" application context created
		// in the previous step.
		// Map it to handle all requests and to load on startup.
		ServletRegistration.Dynamic dispatcherServlet = container.addServlet("dispatcher",
				new DispatcherServlet(dispatcher));
		dispatcherServlet.addMapping("/");
		dispatcherServlet.setLoadOnStartup(1);

	}

}
