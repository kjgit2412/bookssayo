package org.koreait.configs;

import org.koreait.commons.interceptors.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
/**
 * 웹 MVC 설정을 담당하는 설정 클래스.
 */
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${file.upload.url}")
    private String fileUploadUrl;

    @Autowired
    private CommonInterceptor commonInterceptor; // 공통 인터셉터

    /**
     * 정적 리소스에 대한 경로 매핑을 추가.     *
     * @param registry ResourceHandlerRegistry 객체
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 파일 업로드 정적 경로 설정 */
        registry.addResourceHandler(fileUploadUrl + "**")
                .addResourceLocations("file:///" + fileUploadPath);
    }

    /**
     * 공통 인터셉터를 추가.     *
     * @param registry InterceptorRegistry 객체
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 공통 인터셉터 추가
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 메시지 소스를 설정.     *
     * @return MessageSource 객체
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.addBasenames("messages.commons", "messages.validations", "messages.errors");
        return ms;
    }

    /**
     * HTTP 메서드를 지원하는 필터를 빈으로 등록.     *
     * @return HiddenHttpMethodFilter 객체
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
