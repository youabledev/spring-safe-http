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
	        implementation 'com.github.youabledev:spring-safe-http:Tag'
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
