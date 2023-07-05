package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
	
	// MemberService memberService = new MemberServiceImpl();
	
	MemberService memberService ;
	
	@BeforeEach
	public void beforeEach() {
		AppConfig appConfig = new AppConfig();
		memberService = appConfig.memberService();
	}
	
	@Test // 단위테스트(스프링이나 컨테이너의 도움없이 순수한 자바 언어로만 테스트하는 것)에 적합. 빠름.
	void join() {
		// given
		Member member = new Member(1L, "memberA", Grade.VIP);
		
		// when
		memberService.join(member);
		Member findMember = memberService.findMember(1L);
		
		// then
		Assertions.assertThat(member).isEqualTo(findMember);
	}
}
