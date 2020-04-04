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