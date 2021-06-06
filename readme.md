> 软件工程导论作业
# 匿名留言版

实现匿名、实名留言



# 后端

核心框架：[spring boot]([ https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot))

token：[jwt](https://github.com/jwtk/jjwt)

验证码：[kaptcha]()

持久层框架：[MyBatis-plus](https://github.com/baomidou/mybatis-plus)

分页插件：[MyBatis-plus](https://github.com/baomidou/mybatis-plus)

NoSQL缓存：[Redis]()



# 环境搭建

## 数据库环境

### MySQL数据库

1.本地建立MySQL，推荐版本8.0.23，[官网](https://www.mysql.com/downloads/)

2.部署数据库

3.初始化数据库

- 执行`initDataBase.sql`脚本创建数据库

### Redis 缓存数据库

1.本地搭建Redis， 推荐版本5.0.10，[官网](https://redis.io/download)