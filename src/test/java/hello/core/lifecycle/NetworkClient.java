package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
	
	public String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출 url = " + url);
		connect();
		call("초기화 연결 메시지");
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void connect() {
		System.out.println("connect : " + url);
	}
	
	public void call(String message) {
		System.out.println("call = " + url + " message = " + message);
	}
	
	public void disconnect() {
		System.out.println("close " + url);
	}
	
	@PostConstruct
	public void init() throws Exception {
		// 의존관계 주입 끝나면 호출
		connect();
		call("초기화 연결 메시지");
	}
	
	@PreDestroy
	public void close() throws Exception {
		// 빈 종료될 때 호출
		System.out.println("NetworkClient destroy");
		disconnect();
	}
}
