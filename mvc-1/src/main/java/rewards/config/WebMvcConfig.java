package rewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// TODO 04: Complete this configuration to include your controller bean.
// You'll complete this controller in subsequent steps.
// You may use component scanning.
@Configuration
@ComponentScan({ "rewards.web" })
public class WebMvcConfig {

	// TODO 05: Define a view resolver bean.
	// Use an InternalResourceViewResolver and set its
	// prefix to "/WEB-INF/views/" and suffix to ".jsp".
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}
