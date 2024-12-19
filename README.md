# Spring Safe HTTP
Spring Boot 웹 서버 애플리케이션의 HTTP 바디 데이터를 암호화하여 응답하고 암호화 된 데이터를 복호화해서 받을 수 있는 라이브러리 입니다.


## How to
### gradle
1. Add the JitPack repository to your build file
```
  repositories{
    maven { url 'https://jitpack.io' }
  }
```
2. Add the dependency
```
	dependencies {
	        implementation 'com.github.youabledev:spring-safe-http:v0.2.0'
	}
```
### maven
1. Add the JitPack repository to your build file
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
2. Add the dependency
```
	<dependency>
	    <groupId>com.github.youabledev</groupId>
	    <artifactId>spring-safe-http</artifactId>
	    <version>Tag</version>
	</dependency>
```

## Example
```java
    @EncryptResponse
    @DecryptRequest
    @PostMapping("/regist")
    public ResponseEntity<String> regist(
            @RequestBody BookRequest request
    ) {
        return ResponseEntity.ok().body(request.toString());
    }
```
- request body를 복호화 하고자 할 때 @DecryptRequest를 사용합니다.
- response body를 암호화 하고자 할 때 @EncryptResponse를 사용합니다.
```
// request body
/PJ/p778x9sf8G0YXUUxGwn5NRhrPu4gWqwxKxDdQMxwq+wxJAxOucVvZSmzEnjNu/Z41THSQKzaQn8IVEZxfg==
```
- 암호화된 request body는 복호화되어 Controller의 parameter 객체로 바인딩 됩니다.
```
// response body
MTBIpe9JkN7EI9tFA6Fi8RuT1+mRUsHGn75VJLuJDfyQWib05UV7dHFOyiFUQrav
```
- Controller에서 return되는 ResponseEntity의 response body는 암호화 됩니다.


## LICENSE
spring-safe-http 라이브러리의 LICENSE는 [LICENSE](https://github.com/youabledev/spring-safe-http/blob/main/LICENSE)를 확인하세요
