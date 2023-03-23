# My First Elasticsearch
> Elasticsearch 공부 레파지토리

## Tech
- `Java 11`
- `Spring Boot 2.7.9`
- `Spring Data Elastic 4.4.8`
- `Elasticsearch 8.6.2`

__built with__
- `IntelliJ`

## Elasticsearch Detail info
```
oracle-jdk: 11
version: 8.6.2
lucene_version: 9.4.2,
minimum_wire_compatibility_version: 7.17.0,
minimum_index_compatibility_version: 7.0.0
```

## API
* `GET  /api/person`
* `GET  /api/person/{id}`
* `POST /api/person body:id,name`
* `GET  /api/person/gpt/`
* `GET  /api/person/gpt/{id}`
* `POST /api/person/gpt body:id,name`


## ETC
* [인증키 관련 설정](https://github.com/Jskyu/my-first-elastic/tree/master/key)

## Elasticsearch 기본개념
* 구조
![img](https://velog.velcdn.com/images/koo8624/post/8584d80f-950b-46c9-8cd0-78ba2e2c53f4/1.png)
출처: [https://velog.io/@koo8624/Database-Elastic-Search-2편-아키텍쳐Architecture](https://velog.io/@koo8624/Database-Elastic-Search-2%ED%8E%B8-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98Architecture)

### Elasticsearch 주요특징
> Elasticsearch 는 분산형 Restful 검색 및 분석엔진이다. Elasticsearch 의 주요 특징들은 다음과 같다.
* `역색인을 통한 빠른 검색을 지원`
* `클러스터 구성을 통한 분산처리 및 고가용성`
* `Replica를 활용한 데이터 안정성 증대`
* `Shard 분배를 통한 선형적 확장(scale-out)`
* `RESTful API 지원`
* `Schemaless 지원`
* `인덱스 기반의 타입 및 색인 방식 설정 지원`

### Cluster
`Cluster`는 하나 이상의 Elasticsearch `Node`들로 구성된 `Node`의 집합을 의미한다.<br>
Elasticsearch 는 고가용성과 빠른 응답을 위해 여러 `Node`에 데이터를 `복제(Replica)`,<br>
`샤딩(Shard)`하여 저장하기 때문에 여러 `Node`를 모아서 `Cluster`를 구성하는 것이 일반적이다.<br>
`Cluster`는 Elasticsearch 시스템을 구성하는 가장 큰 단위로 독립적으로 운용된다.<br>
따라서, 서로 다른 `Cluster`사이에서는 데이터 접근이나 복제 등의 작업이 제한된다.

### Node
`Node`는 `Cluster`에 포함된 단일 서버로서 `Elasticsearch`가 실행중인 하나의 프로세스 혹은 인스턴스에 해당하며, 데이터를 저장하고 `Cluster`의 `Indexing`및 검색 기능에 참여한다.<br>
모든 `Node`는 `Cluster` 내에서 각자의 역할을 부여받는데, `Node`가 할당받을 수 있는 역할의 종류는 다음과 같다.

* `master`
* `data`
* `data_content`
* `data_hot`
* `data_warm`
* `data_cold`
* `data_frozen`
* `ingest`
* `ml`
* `remote_cluster_client`
* `transform`
<br>[자세히 보기](https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-node.html)

### Index
`Index(색인)`는 비슷한 특성을 가진 `Document`의 모음이다. 예를 들어 고객 데이터에 대한 `Index`, 제품 카탈로그에 대한 `Index`, 주문 데이터에 대한 `Index`를 각각 둘 수 있다.<br>
`Index`는 이름(모두 소문자여야 함)으로 식별되며, 이 이름은 `Index`에 포함된 `Document`에 대한 `Index`화, 검색, 업데이트, 삭제 작업에서 해당 `Index`를 가리키는 데 사용된다.<br>
단일 `Cluster`에서 원하는 개수의 `Index`을 정의할 수 있다.

### Type
하나의 `Index`에서 하나 이상의 `Type`을 정의할 수 있다.<br>
타입이란 `Index`를 논리적으로 분류/구분한 것이며 그 의미 체계는 전적으로 사용자가 결정한다.<br>
일반적으로 여러 공통된 필드를 갖는 문서에 대해 `Type`이 정의된다.<br>
예를 들어 블로그 플랫폼을 운영하고 있는데 모든 데이터를 하나의 `Index`에 저장한다고 했을 때,<br>
이 `Index`에서 사용자 데이터, 블로그 데이터, 댓글 데이터에 대한 `Type`을 각각 정의할 수 있다.

### Document
`Document`는 색인화할 수 있는 기본 정보 단위이다. 예를 들어 어떤 단일 고객, 단일 제품,  단일 주문에 대한 `Document`가 각각 존재할 수 있다. 이 `Document`는 JSON 형식으로 존재한다.<br>
하나의 `Index/Type`에 원하는 개수의 `Document`를 저장할 수 있다.<br>
`Document`가 물리적으로 어떤 `Index`에 있더라도 `Document`는 `Index`에 포함된 어떠한 `Type`으로 지정해야 한다.

### Shard
`Shard`는 데이터를 분산하여 저장하기 위해 `Index`의 범위를 나눈것이다.<br>
`Index`는 방대한 양의 데이터를 저장할 수 있는데, 데이터의 크기가 단일 노드의 하드웨어 한도를 초과할 경우
`Index`를 `Shard` 조각으로 분할하여 저장할 수 있다.<br>
`Shard`의 개수를 수정하기 위해서는 모든 데이터를 reIndexing 하는 과정이 수반되므로,`Shard`의 개수는 클러스터 내부의 `data node` 수를 고려하여 신중히 선택해야 한다.

### Replica Shard
`Shard` 또는 `Node`가 어떠한 이유로 인해 사라지거나, 오프라인 상태가 됐을 경우를 대비하여 `failover`메커니즘을 마련하기 위한 복제하여 저장하는 `Shard`를 뜻한다.<br>
`Replica Shard`의 값이 클수록 데이터 유실에 대한 위험이 적어지지만, 중복 저장을 위한 오버헤드가 추가적으로 발생한다.<br>
또한 `Replica Shard`는 원본 `Shard`와 동일한 `Node`에 배정하지 않아 병렬 방식으로 검색할 수 있다.

### 출처
* [https://www.elastic.co/guide/kr/elasticsearch/reference/current/gs-basic-concepts.html](https://www.elastic.co/guide/kr/elasticsearch/reference/current/gs-basic-concepts.html)
* [https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-node.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-node.html)
* [https://velog.io/@koo8624/Database-Elastic-Search-2편-아키텍쳐Architecture](https://velog.io/@koo8624/Database-Elastic-Search-2%ED%8E%B8-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98Architecture)

## Made
© [Jskyu](https://github.com/Jskyu)