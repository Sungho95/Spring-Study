package hello.core.singleton;

public class SingletonService {

    // 1. static 영역에 객체를 1개만 생성한다.
    private static final SingletonService instance = new SingletonService();

    // 2. 객체 인스턴스가 필요하면 해당 static 메서드를 통해서 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 3. 생성자를 private로 선언하여 외부에서 new 키워드를 사용하지 못하도록 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
