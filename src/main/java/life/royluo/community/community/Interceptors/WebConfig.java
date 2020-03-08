package life.royluo.community.community.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //.addPathPatterns("/**")表示拦截请求那些地址，.excludePathPatterns("/admin/**")那些地址不拦截
//        registry.addInterceptor(new LocaleInterceptor());
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");

    }
}
