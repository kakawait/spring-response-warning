# Warning header for Spring Web MVC

[![Build Status](https://img.shields.io/travis/kakawait/spring-response-warning/master.svg?style=flat)](https://travis-ci.org/kakawait/spring-response-warning)
[![Issues](https://img.shields.io/github/issues/kakawait/spring-response-warning/master.svg?style=flat)](https://github.com/kakawait/spring-response-warning/issues)
[![Coverage Status](https://img.shields.io/coveralls/kakawait/spring-response-warning/master.svg?style=flat)](https://coveralls.io/github/kakawait/spring-response-warning?branch=master)
[![License](https://img.shields.io/github/license/kakawait/spring-response-warning.svg?style=flat)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.kakawait/spring-response-warning.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.kakawait/spring-response-warning)
[![GitHub release](https://img.shields.io/github/release/kakawait/spring-response-warning.svg?style=flat)](https://github.com/kakawait/spring-response-warning/releases/)

*Spring Warning Response* is a library that makes it easy to return
`Warning` header describes on [RFC7234](https://tools.ietf.org/html/rfc7234#section-5.5) responses from a Spring
application.

This library works around a single annotation `@ResponseWarning` that you can put over every controller methods.

## Installation

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.kakawait</groupId>
    <artifactId>spring-response-warning</artifactId>
    <version>${spring-response-warning.version}</version>
</dependency>
```

## Configuration

### With Spring-boot (autoconfiguration)

Nothing for *Spring-boot* application, *autoconfiguration* will do the job for you.

### Without Spring-boot

Simply register `ResponseWarningHandlerInterceptor`

```java
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseWarningHandlerInterceptor());
    }
}
```

## Usage

Deprecating API sample:

```java
@RestController
class ProductsController {

    @ResponseWarning(
            code = WarnCode.MISCELLANEOUS_PERSISTENT_WARNING, 
            text = "Deprecated API: use /{productId} instead.")
    @RequestMapping(method = GET, value = "/get", produces = APPLICATION_JSON_VALUE)
    @Deprecated
    public Product getProduct_v0(@RequestParam(value = "id") String productId) {
        return ...;
    }

    @RequestMapping(method = GET, value = "/{productId}", produces = APPLICATION_JSON_VALUE}
    public Product getProduct_v1(String productId) {
        return ...;
    }

}
```

The following HTTP request will produce the corresponding response:

```http
GET /products?id=123 HTTP/1.1
```

```http
HTTP/1.1 200 Ok
Content-Type: application/json
Warning: 299 - "Deprecated API: use /{productId} instead." 

...
```

## Getting help

If you have questions, concerns, bug reports, etc., please file an issue in this repository's Issue Tracker.

## Getting involved

To contribute, simply make a pull request and add a brief description (1-2 sentences) of your addition or change.

## Credits and references

- [Hypertext Transfer Protocol (HTTP/1.1): Caching](https://tools.ietf.org/html/rfc7234)
