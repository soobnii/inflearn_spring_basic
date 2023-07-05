package hello.core.member;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component

public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> store = new HashMap<>();
	// 실무에서는 동시성 이슈가 있으므로로HashMap보다는 ConcurrentHashMap
	// ConcurrentHashMap을 사용하게 되면 put()이 확실하게 다 끝난 상태에서 get()이 호출되는 것을 보장해줍니다.
	
	@Override
	public void save(Member member) {
		store.put(member.getId(), member);
	}
	
	@Override
	public Member findById(Long memberId) {
		return store.get(memberId);
	}
}
