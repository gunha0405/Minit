package kr.or.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import kr.or.iei.util.AdminInterceptor;
import kr.or.iei.util.LoginInterceptor;


//스프링부트 설정파일
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${file.root}")
	private String root;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry
				.addResourceHandler("/**")
				.addResourceLocations("classpath:/templates/","classpath:/static/");
		
		registry
				.addResourceHandler("/photo/**")
				.addResourceLocations("file:///"+root+"/photo/");
		registry
			.addResourceHandler("/notice/editor/**")
			.addResourceLocations("file:///"+root+"/notice/editor/");
		
		registry
		.addResourceHandler("/feed/**")
		.addResourceLocations("file:///"+root+"/feed/");
		


		registry
		.addResourceHandler("/user/**")
		.addResourceLocations("file:///"+root+"/user/");

			
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new LoginInterceptor())
					.addPathPatterns("/photo/list","/user/logout","/user/mypage","/user/update","/user/delete","/following",
									 "/feed/writeForm","/feed/userStorage","/feed/feedWrite","/feed/feedUpdate","/feed/delete","/feed//updateFrm");
			
			registry.addInterceptor(new AdminInterceptor())
					.addPathPatterns("/admin/**");
	}

	
}
