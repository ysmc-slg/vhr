package top.zxqs.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zxqs.common.constant.Constants;

/**
 * 通用配置
 * 
 * @author ruoyi
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer
{
//    @Autowired
//    private RepeatSubmitInterceptor repeatSubmitInterceptor;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry)
//    {
//        /** 本地文件上传路径 */

            // 1. addResourceHandler 参数可以有多个
            // 2. addResourceLocations 参数可以是多个，可以混合使用 file: 和 classpath : 资源路径
            // 3. addResourceLocations 参数中资源路径必须使用 / 结尾，如果没有此结尾则访问不到

            // 将 /profile/**的请求映射到 RuoYiConfig.getProfile() 中
//        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
//                .addResourceLocations("file:" + RuoYiConfig.getProfile() + "/");
//
//        /** swagger配置 */
//        registry.addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//    }

    /**
     * 自定义拦截规则
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
//    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}