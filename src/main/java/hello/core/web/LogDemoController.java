package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
	
	private final LogDemoService logDemoService;
	
//	private final ObjectProvider<MyLogger> myLoggerProvider;
	private final MyLogger myLogger;
	
	@RequestMapping("log-demo")
	@ResponseBody // 문자를 바로 반환
	public String logDemo(HttpServletRequest request) throws InterruptedException {
		// MyLogger myLogger = myLoggerProvider.getObject();
		
		System.out.println("myLogger = " + myLogger.getClass());
		
		String requestURL = request.getRequestURL().toString();
		myLogger.setRequestURL(requestURL);
		// 이렇게 받은 requestURL 값을 myLogger에 저장해둔다.
		// myLogger는 HTTP 요청 당 각각 구분되므로 다른 HTTP 요청 때문에 값이 섞이는 걱정은 하지 않아도 된다.
		
		myLogger.log("controller test");
		logDemoService.logic("testID");
		return "OK";
	}

	// HttpServletRequest : 고객 요청 정보를 받을 수 있음
	/*
	* @RestController 어노테이션을 명시했다면 따로 @ResponseBody 어노테이션을 명시하지 않아도
	* 자동으로 HttpResponse의 본문 responseBody에 자바 객체가 매핑되어 전달됩니다.
	*
	* 클라이언트에서 서버로 필요한 데이터를 전송하기 위해서 JSON이라는 데이터를 요청 본문에 담아서 서버로 보내면,
	* 서버에서는 @RequestBody 어노테이션을 사용하여 HTTP 요청 본문에 담긴 값들을 자바 객체로 변환 시켜, 객체에 저장시킵니다.
	* 서버에서 클라이언트로 응답 데이터를 전송하기 위해서 @ResponseBody 를 사용하여
	* 자바 객체를 HTTP 응답 본문의 객체로 변환하여 클라이언트로 전송시키는 역할을 합니다.
	* */
}
