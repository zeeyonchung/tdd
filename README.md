# TDD (Test-Driven Development)

- 테스트 > 테스트를 통과할 만큼의 코딩 > 리팩토링 > 테스트

- 테스트를 통과시킬 만큼의 기능을 구현한다. 아직 추가하지 않은 테스트를 고려해서 구현하지 않는다.

- 첫 번째 테스트는 가장 쉽거나 가장 예외적인 상황을 선택한다.
첫 번째 테스트로 만들어야 할 코드가 길어지면 구현을 다 하고 테스트를 하는 방식과 다르지 않다.

- 테스트 코드도 코드이기 때문에 유지보수를 해야 한다. 각 테스트 메서드에서 발생하는 중복을 알맞게 제거하거나 의미가 잘 드러나도록 수정한다. 중복을 제거한 뒤에 오히려 테스트 코드 관리가 어려워진다면 제거했던 중복을 되돌려야 한다.

- TDD를 시작할 때 테스트할 목록을 미리 정리하면 좋다. 그 중 어느 테스트가 구현이 쉬울지, 또는 어느 테스트가 예외적인지 생각해본다.

- 범위가 큰 리팩토링은 시간이 오래 걸려 TDD의 흐름을 깨므로 리팩토링을 진행하지 말고 테스트를 통과시키는 데 집중한다.

- 범위가 큰 리팩토링을 진행하기 전에는 먼저 코드를 커밋하자.

- 시작이 안 될 때는 검증 코드부터 작성해본다.

- Junit 5 모듈 구성
    - Junit 플랫폼 : 테스팅 프레임워크 구동 런처, 테스트 엔진
    - Junit 주피터 : Junit5 를 위한 테스트 API, 실행 엔진
    - Junit 빈티지 : Junit3, 4 로 작성된 테스트를 5 플랫폼에서 실행하기 위한 모듈
    
- 각 테스트 메서드는 서로 독립적으로 동작해야 한다. 테스트 메서드가 서로 필드를 공유한다거나 실행 순서를 가정하고 테스트를 작성하지 말아야 한다.

- 가능한 많은 예외 상황을 찾기 위해 노력해야 한다.

- 테스트의 구성 요소
    - 상황(given) : 어떤 상황이 주어지고,
    - 실행(when) : 그 상황에서 기능을 실행하고,
    - 결과 확인(then) : 실행한 결과를 확인한다.
    
- 우연에 의해 테스트 결과가 달라지면 안 된다.  
    - e.g. 파일이 존재하지 않는 상황을 테스트하려는 경우 우연히 파일이 존재할 수도 있다. 이런 때는 파일이 존재하는지 검사해서 삭제하는 등 명시적으로 파일이 없는 상황을 만든다.
    
- 외부 상태에 의해 테스트 결과가 달라지면 안 된다.
    - e.g. DB 데이터의 상태에 따라 테스트가 실패할 수도 있다. 이런 때는 테스트 실행 정에 외부를 원하는 상태로 만들거나 테스트 실행 후에 외부 상태를 원래대로 되돌려 놓아야 한다.
    
- 실제 구현을 이용하면 테스트 상황을 만들기 어렵다. 제어하기 힘든 외부 상황이 존재하면 다음과 같은 방법으로 의존을 도출하고 이를 대역으로 대신할 수 있다.
    - 제어하기 힘든 외부 상황을 별도 타입으로 분리
    - 테스트 코드는 별도로 분리한 타입의 대역을 생성
    - 생성한 대역을 테스트 대상의 생성자등을 이용해서 전달
    - 대역을 이용해서 상황 구성
    
- 대역의 종류
    - Stub : 구현을 단순한 것으로 대체한다. 테스트에 맞게 단순히 원하는 동작을 수행한다.
        - e.g. CardNumberValidator의 검증 로직을 단순하게 구현한 Stub
    - Fake : 제품에는 적합하지 않지만 실제 동작하는 구현을 제공한다.
        - e.g. DB 대신에 Map(메모리)에 데이터를 저장하는 Fake
    - Spy : 호출된 내역을 기록한다. 기록한 내용은 테스트 결과를 검증할 때 사용한다. Stub이기도 하다.
    - Mock : 기대한대로 상호작용하는지 행위를 검증한다. 기대한대로 동작하지 않으면 익셉션을 발생할 수 있다. Stub이자 Spy이기도 하다.
    
- Mockito : 모의 객체 생성, 검증, 스텁을 지원하는 프레임워크
    - 모의 객체 생성
        ```
        GameNumGen genMock = Mockito.mock(GameNumGen.class);
        ```
    - 스텁 설정
        ```
        BDDMockito.given(genMock.generate(null)).willReturn("123");
        ```
    - 인자 매칭
        ```
        BDDMockito.given(genMock.generate(ArgumentMatchers.any())).willReturn("123");
        ```
        주의사항 : 인자가 여러개인 경우 일부만 ArgumentMatcher를 사용할 수는 없다. 임의의 값과 일치하는 인자와 정확하게 일치하는 인자를 함께 사용하고 싶으면 `ArgumentMatchers.eq()`을 사용한다. 
    - 행위 검증
        ```
        BDDMockito.then(genMock).should().generate(null);
        ```
    - 인자 캡쳐
        ```
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockList).should().add(captor.capture());
        ```
    - 어노테이션 사용  
        `mockito-junit-jupiter` 의존 추가
        ```
        @ExtendWith(MockitoExtension.class)
        public class Junit5ExtensionTest {
            @Mock
            private GameNumGen genMock;
        }
        ```
      
- 모의 객체를 과하게 사용하는 것보다 대역 클래스를 만드는 것이 낫다.

- 테스트가 어려운 코드
    - 하드 코딩된 경로
    - 의존 객체를 직접 생성
    - 정적 메서드 사용
    - 실행 시점에 따라 달라지는 결과
        - e.g. 현재 시간에 따라 결과가 달라질 수 있는 코드, Random 값을 따라 결과가 달라질 수 있는 코드
    - 역할이 섞여있는 코드
    - 메서드 중간에 소켓 통신 코드가 포함된 코드
    - 콘솔에서 입력을 받거나 결과를 콘솔에 출력하는 코드
    - 테스트 대상이 사용하는 의존 대상 클래스나 메서드가 final인 경우
    - 테스트 대상의 소스를 소유하고 있지 않아 수정이 어려운 경우
    
- 테스트 코드에서 사용하는 파일은 src/test/file이나 src/test/resources 폴더에 함께 등록해놓는다.

- 외부 라이브러릴를 쉽게 대체할 수 없는 경우가 있다. (e.g. 정적 메서드를 제공하는 경우)  
  대역으로 대체하기 어려운 외부 라이브러리는 직접 사용하지 말고 감싸서 사용한다.
  
- WireMock을 사용하여 외부 API 서버를 대체할 수 있다. WireMockServer를 시작하면 실제 HTTP 서버가 실행된다.

- 스프링 내장 서버를 구동하여 어플리케이션을 실행하고 TestRestTemplate을 이용해서 HTTP 요청을 전송해 API를 테스트할 수 있다.

- 변수나 필드를 사용해서 기댓값을 표현하지 않는다.

- 한 테스트 메서드에서 한 기능만 검증한다.

- 정확하게 일치하는 값으로 모의 객체를 설정하지 않는다.
    ```
    BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw"))
        .willReturn(true);
  
    assertThrows(WeakPasswordException.class, () -> {
        userRegister.register("id", "pw", "email");
    });
    ```
  이 테스트는 userRegister.register("id", "pw", "email"); 를 userRegister.register("id", "pwa", "email");로 바꾸면 실패하여 의도와 다르게 동작한다. 
    ```
    BDDMockito.given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString()))
          .willReturn(true);
    ```
  위처럼 바꾸면 어떤 문자열을 인자로 전달하더라도 테스트는 깨지지 않고 약한 암호인 경우에 대한 테스트를 수행한다.
  
- 불필요한 구현 검증을 하지 않는다. 내부 구현을 검증하면 구현을 조금만 변경해도 테스트가 깨질 수 있다. 내부 구현보다 실행 결과를 검증해야 한다.

- 상황 구성 코드를 @BeforeEach 셋업 코드에 위치시키지 않는다. 테스트 메서드가 자체적으로 테스트 하려는 상황을 기술하고 있어야 한다.

- 통합 테스트에서 데이터 공유를 주의한다. @Sql을 사용해서 DB 데이터 초기화 쿼리를 실행할 수 있다.
    ```
    @SpringBootTest
    @Sql("classpath:init-data.sql")
    public class Test {}
    ``` 
  
- 통합 테스트의 상황 설정을 위해 보조 클래스를 사용할 수 있다.
    ```
    // 보조클래스에서 상황 구성을 위한 회원 데이터를 생성하도록 하여 insert 중복 코드를 없앤다.
    public class UserGivenHelper {
        private EntityManager em;
  
        public UserGivenHelper(EntityManager em) {
            this.em = em;
        }
  
        public void givenUser(String id, String pw, String email) {
            em.createNativeQuery("INSERT INTO person (id, pw, email) VALUES (:id, :pw, :email)")
                    .setParameter("id", id)
                    .setParameter("pw", pw)
                    .setParameter("email", email)
                    .executeUpdate();
        }
    }
    ```
    ```
    @SpringBootTest
    public class Test {
  
        @Autowired
        private EntityManager em;
        private UserGivenHelper;
        private UserRegister register;
  
        @BeforeEach
        void setUp() {
            given = new UserGivenHelper(em);
            register = new UserRegister();
        }
        
        @Test
        void dupId() {
            given.givenUser("id", "pw", "email");
  
            assertThrows(DupIdException.class,
                () -> register.register("id", "pw2", "email2")
            );
        }
    }
    ```
  
- 실행 환경이 달라서 테스트가 실패하지 말아야 한다.
    ```
    public class Test {
        @Test
        void export() {
            // 실행 환경에 알맞는 임시 폴더 경로를 구한다.
            String folder = System.getProperty("java.io.tmpdir");
            Exporter expoter = new Exporter(folder);
            exporter.export("file.txt");
  
            Path path = Paths.get(folder, "file.txt");
            assertTrue(Files.exists(path));
        }
    }
    ```
  테스트를 특정 OS 환경에서만 실행해야 한다면 @EnabledOnOs와 @DisabledOnOs를 사용하면 된다.
  
- 테스트에 필요한 값만 설정하면 된다.
    ```
    // id 값만 세팅하여 id 중복 여부를 테스트한다.
    RegisterReq req = RegisterReq.builder()
        .id("id")
        .build();
    ```

- 단위 테스트에서 상황 구성을 위해 필요한 데이터가 복잡한 경우가 있다. 이 때 객체 생성 클래스를 따로 만들어 복잡함을 줄일 수 있다.

- 테스트는 성공하거나 실패해야 한다. 단언을 실행하지 않고 넘어가면 안 된다.

- 필요하지 않은 범위까지 연동하지 않는다.
    - @JdbcTest를 사용하면 디비 연동과 관련된 설정만 초기화한다.