package wen.myblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wen.myblog.interceptor.AdminBlogInter;

/**
 * 添加 拦截器
 */
@Configuration
public class AdminBlogWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注意！！！！！！！！！！！一定不能拦截校检的请求(/admin/adminBlog)，会各种错误和死循环，因为拦截它后无法校检
        registry.addInterceptor(new AdminBlogInter()).addPathPatterns("/admin/**","/blog/**","/tag/**","/type/**")
                .excludePathPatterns("/admin","/admin/adminBlog");
    }
}
