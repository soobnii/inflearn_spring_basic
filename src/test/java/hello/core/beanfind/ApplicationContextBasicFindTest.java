package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextBasicFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	// 실무에서 직접 빈을 조회할 일은 거의 없지만, 알고 있어야 함
	
	@Test
	@DisplayName("빈 이름으로 조회")
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("이름없이 타입으로만 조회")
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("구체 타입으로 조회")
	void findBeanByType2() {
		MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
		// Bean 의 반환나입이 아닌 구체타입으로도 가능하지만, 구체타입을 활용하는건 좋지 않음 (역할에 의존하지 않고 구현에 의존한 것)
		// 그러나 가끔 필요할때가 있음
	}
	
	@Test
	@DisplayName("빈 이름으로 조회 안됨")
	void findBeanByNameX() {
//		MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
		// 예외가 던져져야 성공
		Assertions.assertThrows(NoSuchBeanDefinitionException.class,
			() -> ac.getBean("xxxxx", MemberService.class));
	}
}
