## CQS 패턴 (Command Query Separation Pattern)
---

</br>

여러 디자인 패턴을 들어보긴 했지만, CQS 패턴은 나에게는 다소 생소한 패턴 중 하나이다.

> 디자인 패턴은 프로그램을 개발하는 과정에서 빈번하게 발생하는 디자인 상의 문제를 정리해서, 상황에 따라 간편하게 적용해서 쓸 수 있는 패턴 형태로 만든 것이다. - 헤드퍼스트 디자인 패턴 中 -

<br/>

시간적 여유가 난다면 가장 먼저 다루고 싶었던 것이 바로 이 CQS 패턴이었다.

김영한 님의 스프링 로드맵에서 가끔 "커맨드와 쿼리를 분리시켜라."는 말을 해주셨다. 그것이 바로 이 `CQS 패턴`을 말한 것이었다.

참고: https://www.inflearn.com/questions/27795/cqrs

</br>

CQS 패턴은 소프트웨어 디자인 패턴 중 하나로, 모든 객체의 메소드 작업을 수행하는 Command, 데이터를 반환하는 Query, 이렇게 2개로 구분하는 디자인 패턴이다.

이걸 다시 말하면, CQS는 객체의 모든 메서드를 Command와 Query 두 가지로 구분해야 하며, 하나의 메서드는 반드시 Command나 Query 둘 중 하나에만 속해야 한다는 걸 의미한다.

> Command란?
> Command는 객체의 상태를 변경하는 메서드로 값을 반환하지 않는다.
> 이 말을 즉슨, 시스템에 어떠한 side effect를 가하는 행위에서는 값을 반환하지 않아야 한다는 것이다.
```java
void decreaseStock() { // OK
    stock = stock - 1;
}

Integer decreaseStock() { // NO
    return stock = stcok - 1;
}

```

</br>

> Query란?
> Query는 객체 내부 상태를 바꾸지 않고 객체의 값을 반환하기만 한다.
> 이 말은 즉슨, 상태를 변경시키지 않아야 하고, 시스템의 상태를 단지 반환만 해야 한다.
```java
Product getProduct(Long productId) { // OK
    return products.get(productId);
}

void getProduct(Long productId) { // NO
    Product product = products.get(productId)
    product.updateModifiedAt();

    return product;
}
```

만약 하나의 함수에서 Command와 Query가 모두 동시에 일어나게 된다면, 소프트웨어의 3가지 원칙 중 KISS(Keep It Simple, Stupid)가 지켜지지 않을 것이다.

> "Most systems work best if they are kept simple rather than made complicated;
therefore, simplicity should be a key goal in design 
and unnecessary complexity should be avoided."
>
> 대부분의 시스템은 복잡하게 보다는 간단하게 만들어졌을 때 최고로 잘 동작한다. 
그러므로 심플함은 디자인 할 때 핵심 목표가 되어야 하고, 불필요한 복잡성은 피해야 한다.


이런 관점에서 연장선상에 있는 것이 바로 CQRS(Command Query Responsibility Segregation)이다.

### (CQRS 추후 추가 예정)