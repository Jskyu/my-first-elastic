### 키 인증 등록

- 해당 프로젝트는 임시로 발급한 인증서로 실행되는 Elasticsearch 을 기반으로 동작한다.
JAVA 에서 신뢰하지 못하는 https 주소를 연결했을 때 터지는
SSLHandshakeException 예외를 해결하기 위해 JAVA에 인증키를 등록하여야 한다.

`keytool.exe -import -alias "별칭" -keystore "자바 JRE 경로\lib\security\cacerts" -storepass changeit -file "projectDir\key\key.crt"`

* 출처 [https://colabear754.tistory.com/87](https://colabear754.tistory.com/87)