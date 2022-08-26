# 遇到问题
## 问题一：java.lang.NoClassDefFoundError: org/apache/http/client/HttpClient
#### 方法：导入两个依赖
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.3.4</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore -->
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpcore</artifactId>
    <version>4.4.15</version>
</dependency>

### 如果问题依旧不能解决
去本地 Maven 仓库找到这两者的依赖然后进行删除其他版本的依赖（保留一个要用的依赖即可）

