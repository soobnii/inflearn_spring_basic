package hello.core.singleton;

public class SingletonService {
	
	private static final SingletonService instance = new SingletonService();
	// 자기자신을 내부의 private static 으로 가지고 있음 -> 딱 하나만 존재함(같은값)
	
	// jvm 뜰때 instance 를 생성해서 가지고있음
	public static SingletonService getInstance() {
		return instance;
		// 이 객체 인스턴스가 필요하면, 오직 getInstance 메서드를 통해서만 조회할 수 있다.
		// 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
	}
	
	private SingletonService() {
		// 외부 클래스에서 아래처럼 new 키워드로 새로운 인스턴스를 만들면 안되므로, private 생성자로 막는다.
		// 이제 외부에서 새로운 객체를 만들려고 하면, 컴파일 오류남
		/*
			public static void main(String[] args) {
				SingletonService singletonService1  = new SingletonService();
			}
		*/
	}
	
	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}
	
}
