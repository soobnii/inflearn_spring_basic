package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
	
	private String uuid;
	private String requestURL;
	
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
		// requestURL 은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받는다.
	}
	
	public void log(String messsage) {
		System.out.println("[" + uuid + "] " + "[" + requestURL + "] " + messsage);
	}
	
	@PostConstruct
	public void init() {
		// 이 빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드를 사용해서 uuid를 생성해서 저장해둔다.
		// 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분할 수 있다.
		uuid = UUID.randomUUID().toString();
		System.out.println("[" + uuid + "] request scope bean create : " + this);
	}
	
	@PreDestroy
	public void close() {
		System.out.println("[" + uuid + "] request scope bean close : " + this);
	}
	
	/*
	* 스프링 컨테이너가 뜨는 시점엔 request 가 없다. 그러므로 이 때는 해당 스코프의 생존 범위가 아니다.
	* 따라서 그냥 서버 구동 시 MyLogger 가 주입될때 에러가 난다.
	* */
}
