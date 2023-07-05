package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
	
	// private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	private final MemberRepository memberRepository;
	
	// ComponentScan 으로 빈을 등록하면, 의존관계를 Autowired 로 주입하게 된다.
	// Autowired : MemberRepository 타입의 빈을 찾아서 자동으로 연결해준다.
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}
	
	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
