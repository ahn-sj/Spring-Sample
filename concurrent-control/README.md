## [JAVA/Spring] 06. 동시성 제어(Concurrency Control)

---

### 🤔 문제 상황

우리 서비스의 타임 세일 이벤트에서 동시에 여러 사용자가 주문을 할 경우 요청 값에 따라 재고 수량이 남아있지 않는 문제 발생.즉, **동시성 문제**가 발생


시나리오: 한 개 상품에 대해 10명의 사용자가 1000개를 동시에 주문한다고 가정했을 때 동시성 문제 발생

![image](https://user-images.githubusercontent.com/64416833/206889857-5f6fea13-473c-4c49-8794-ee32a94a076c.png)

```java
@Test
    @DisplayName("not_used_lock")
    public void not_used_lock() throws Exception {
        final int PRODUCT_STOCK = 1000;
        final int THREAD_COUNT = 1000;
        final int EXPECTED = PRODUCT_STOCK - THREAD_COUNT; // 100 - 100 = 0

        Product product = new Product("항해", 1000, "카테고리", "배송상태", PRODUCT_STOCK, 1L);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // when
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    product.decreaseStock(1);
                } finally {
                    System.out.println("[총 스레드 개수] = " + latch.getCount() + ", [작업 스레드 이름] = " + Thread.currentThread().getName());
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        assertThat(product.getStock()).isEqualTo(EXPECTED);
    }
```

<br/>

### 🧐 문제 해결 대안 및 최종 결정
- synchronized
- Redis
    - Lettuce
    - Redisson
- Mysql
    - Optimistic Lock
    - Pessimistic Lock **(최종 결정)**

<br/>

### 🤔 의사결정 과정


- `synchronized`

synchronized를 사용하는 이유는 하나의 객체에 여러 개의 객체가 동시에 접근해 처리하는 것을 막기 위해 사용된다. 다시 말해, synchronized 키워드가 붙은 메서드 블럭은 하나의 스레드만 접근할 수 있도록 하여 스레드의 불규칙적인 자원 공유(Race Condition)를 막기 위해 사용한다.

synchronized를 적용시킨 메서드는 다음과 같이 동작하게 된다.
Thread1: Work 1
Thread2: Work 2<br>
`|--Work 1-->|--Work 2-->`

그러나, `@Transactional` 이 있다면 원하는 대로 동작하지 않을 수도 있다. <br/>
그 이유는 `@Transactional`은 프록시 객체를 생성하기 위해 AOP로 동작하게 되기 때문이다.
```java
TransactionStatus status = transactionManager.getTransaction(..);

try {
	target.logic(); // public synchronized void decrease 메서드 수행
    // logic 수행 이후 트랜잭션 종료 전에 다른 쓰레드가 decrease에 접근!
    transactionManager.commit(status);
} catch (Exception e) {
	transactionManager.rollback(status);
    throw new IllegalStateException(e); 
}
```

그런데 `Transaction`과 `synchronized`를 동시에 적용하게 될 경우 한 쓰레드가 `synchronized`가 붙은 메서드를 호출하여 수행한 이후, 트랜잭션이 종료되기 전에 다른 쓰레드가 `synchronized`가 붙은 메서드에 접근을 하여 커밋되지 않는 데이터에 접근하게 되기 때문에 @Transactional과 synchronized를 함께 사용해서는 우리가 원하는 결과를 얻을 수 없다.

뿐만 아니라 서버가 여러대 있다고 가정하면, synchronized를 사용으로는 각 서버마다 요청하는 쓰레드에 대해 정합성이 맞지 않는 문제를 해결할 수 없다.

그렇다고 해서 Transactional을 사용하지 않는다면, 재고를 감소시키는 로직은 실행이 되었지만 주문이 발생하지 않은 경우에 다시 감소시킨 상품에 대해 재고를 채워놔야 하는 문제점이 생기게 된다. 그래서 주문 처리의 로직이 정상적으로 처리가 되면 Commit을 시켜주고, 처리 중 에러가 발생하게 되면 자동으로 Rollback을 시켜주는 Transaction을 붙여주어야 한다.

그래서 해당 문제는 데이터베이스의 락을 통해 Race Condition 문제를 해결해야한다.

https://velog.io/@minnseong/Spring-synchronized로-해결할-수-있을까<br>
https://kdhyo98.tistory.com/59

<br/>

- `redis`

Redis가 Single Thread 기반이기 때문에 동시성 제어할 때 좋은 선택지이기도 하고, 락 정보가 간단한 휘발성 데이터에 가깝기 때문에 가장 좋은 선택지

redis에서 분산 락을 구현한 알고리즘으로 redlock이라는 것을 제공하는데 자바에서는 redlock의 구현체로 Lettuce와 redisson를 제공한다.

redis의 분산 락 구현체인 두 Redis Cleint는 Netty를 사용하여 non-blocking I/O 로 동작된다.

1. Lettuce 

Lettuce는 기본적으로 락 기능을 제공하고 있지 않기 때문에 락을 잡기 위해 setnx명령어를 이용해서 사용자가 직접 스핀 락 형태(Polling 방식)로 구현해야 함. 이로인해, 락을 잡지 못하면 끊임없이 루프를 돌며 재시도를 하게 되고 이는 레디스에 부하를 줄수 있다고 판단했음. 
또한, setnx명령어는 expire time을 지정할 수 없기 때문에 락을 잡고 있는 서버가 죽었을 경우 락을 해제하지 못하는 이슈가 있음(DeadLock 발생 가능성)


2. Redisson

redisson은 여러 독립된 프로세스에서 하나의 자원을 공유해야 
할 때, 데이터에 결함이 발생하지 않도록 하기 위해서 분산 락을 제공해준다.

Redisson은 락을 잡는 방식이 스핀락 방식이 아니고, expire time도 적용할 수 있다는 특징을 갖는다. 또한, Redisson은 pub/sub 방식을 사용하여 락이 해제될 때마다 subscribe하는 클라이언트들에게 "이제 락 획득을 시도해도 좋다"라는 알림을 주는 구조이다. 
그렇기 때문에 서버에 스핀락보다 훨씬 적은 부담을 준다.

https://serverwizard.tistory.com/281

그러나 Redis의 락 방식을 도입하지 않은 이유는 pub/sub을 통한 lock 획득 시도 과정에서 발생하는 네트워크 지연으로 인해 비관적 락에 비해 속도가 늦어졌고 현재 저희 서비스에 Redis를 도입하지 않았을 뿐더러 Redisson 도입을 위해 Redis를 적용해야 한다는 점에서 오버 엔지니어링, 러닝 커브 등을 고려했을 때 비관적 락으로도 처리가 가능할 것이라고 생각되어 도입하지 않음.

<br/>

- MySQL

MySQL에서 제공하는 Lock의 종류는 크게 Named Lock, Optimistic Lock, Pessimistic Lock이 존재한다.

1. Named Lock
Named Lock은 이름을 가진 lock을 획득한 후 해제할때까지 다른 세션은 이 lock을 획득 불가한 락 기법으로 transaction이 종료될 때 lock이 자동 해제 되지 않다는 문제가 존재하기 때문에 별도 명령어로 해제를 수행 or 선점시간이 끝나야 해제해줘야 한다.