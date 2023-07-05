package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
/*@ComponentScan(
	excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)*/
public class AutoAppConfig {
	// 보통 제외시키진 않지만, 여기선 테스트를 위해 @Configuration를 제외하기위해 excludeFilters 사용
	// 기본값이 FilterType.ANNOTATION
}
