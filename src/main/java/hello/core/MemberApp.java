package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
	
	public static void main(String[] args) {
		// MemberService memberService = new MemberServiceImpl();
		
		// OCP, DI
//		AppConfig appConfig = new AppConfig();
//		MemberService memberService = appConfig.memberService();
		
		// 스프링 전환
		
		// 스프링 컨테이너 생성
		ApplicationContext applicationContext
			= new AnnotationConfigApplicationContext(AppConfig.class);
		// AppConfig 에 있는 Bean 들을 스프링컨테이너(ApplicationContext)에 넣어서 관리해준다. (AppConfig 도 포함)
		
		// 빈 이름, 타입
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		
		Member member = new Member(1L, "memberA", Grade.VIP);
		memberService.join(member);
		
		Member findMember = memberService.findMember(1L);
		System.out.println("new member = " + member.getName());
		System.out.println("find Member = " + findMember.getName());
	}
}
