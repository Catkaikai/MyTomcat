### 动手DIY一个简陋的Tomcat :wink:

####  知识点： `java socket`  `I/O流` `HTTP协议` `反射` `注解`

#### 前言

```javascript
Tomcat是非常流行的Web Server，它还是一个满足Servlet规范的容器。Tomcat和我们的Web应用是什么关系？
从感性上来说，我们一般需要把Web应用打成WAR包部署到Tomcat 中，在我们的 Web应用中，我们要指明URL被哪个类的哪个方法所处理「不论是原始的Servlet 开发，还是现在流行的Spring MVC都必须指明」。
由于我们的Web应用是运行在Tomcat中，请求必定是先到达Tomcat的。Tomcat 对于请求实际上会进行如下的处理。
    
#第一，提供Socket服务
Tomcat的启动，是Socket服务，它支持HTTP协议
Tomcat既然是基于Socket，是基于BIO or NIO or AIO 呢? 在本案例中采用的是BIO
	BIO（Blocking I/O）同步阻塞I/O
	NIO (New I/O) 同步非阻塞I/O
	AIO （ Asynchronous I/O）：异步非阻塞I/O模型
#第二，进行请求的分发
一个Tomcat可以为多个Web应用提供服务，所以Tomcat可以把 URL下发到不同的Web应用。
#第三，需要把请求和响应封装成request/response
我们在Web应用这一层，可从来没有封装过request/response 的，我们都是直接使用的，这就是因为Tomcat已经做好了
```

#### 一、如何测试MyTomcat和学习

##### 1.打开IDE导入项目(欢迎贡献注释):kissing_heart:

```
通过阅读源码里的较为详细的注释
```

##### 2.运行MyTomcat.java

##### 3.可在源码上修改运行端口号

```java
//默认端口为8080
new MyTomcat(8080).start() 
//支持链式调用
new MyTomcat().setPort(8089).start();
```

##### 4.跑起来后即可在浏览器地址栏发送请求

```
http://localhost:8089/t1 
http://localhost:8089/t2
http://localhost:8089/t3 //采用注解的形式
```

#### 二、项目结构

```
包下的所有类即为MyTomcat主体
子包testServlet下的是两个用来测试的Myservlet的子类
```

#### 三、贡献代码的步骤

1. 在Github上fork项目到自己的repo
2. 把fork过去的项目也就是你的项目clone到你的本地
3. 修改代码（当前仅master一个分支）
4. commit后push到自己的远程仓库（master分支）
5. 登录Github在你首页可以看到一个 pull request 按钮，点击它，填写一些说明信息，然后提交即可。
6. 等待作者合并

#### 四、提供bug反馈或建议

##### 欢迎提issue（建议提供bug复现的方式以及涉及的代码块）

- [Github issues](https://github.com/Catkaikai/MyTomcat/issues)

#### 五、To Do

- [x] ##### 实现注解功能

- [x] ##### 静态资源的访问


- [ ] ##### 支持更多的请求方式


- [x] ##### 优化使用方式（修改配置文件而无需修改源码）

- [ ] ##### 扫描其他Jar包下的MyServlet子类

- [ ] ##### 提供maven模块以及jar包的方式使用

- [ ] ##### ......


#### 六、知识点

##### 1.I/O流

```
BIO（Blocking I/O）同步阻塞I/O
NIO (New I/O) 同步非阻塞I/O
AIO （ Asynchronous I/O）：异步非阻塞I/O模型
进程中的IO调用步骤大致可以分为以下四步：
进程向操作系统请求数据;操作系统把外部数据加载到内核的缓冲区中;操作系统把内核的缓冲区拷贝到进程的缓冲区;进程获得数据完成自己的功能;当操作系统在把外部数据放到进程缓冲区的这段时间（即上述的第二，三步），如果应用进程是挂起等待的，那么就是同步IO，反之，就是异步IO，也就是AIO。
```

##### 2.HTTP协议

###### 报文格式

[报文格式参考](https://baijiahao.baidu.com/s?id=1662842929861521073&wfr=spider&for=pc)

###### 版本演化

```javascript
Http是一个应用层协议，基于TCP协议（传输层）之上，规定WWW服务器
浏览器之间信息传递规范。使用的默认端口号为80端口。
Http的版本演化
#http 0.9
最初的版本，只有一个命令GET，服务器只能回应HTML格式字符串。
http 1.0：
引入了新的命令POST和HEAD（http数据头部）命令
每个TCP连接只能发送一个请求，发送数据完毕，连接就关闭，如果还要请求其他资源，就必须再新建一个连接
头信息是 ASCII 码，后面数据可为任何格式。服务器回应时会告诉客户端，数据是什么格式，即Content-Type字段的作用。这些数据类型总称为MIME即多用途互联网邮件扩展，每个值包括一级类型和二级类型，预定义的类型，也可自定义类型, 常见Content-Type值：text/xml image/jpeg audio/mp3
#http 1.1
新增方法：PUT、PATCH、OPTIONS、DELETE
引入了持久连接（persistent connection），即TCP连接默认不关闭，可以被多个请求复用，不用声明Connection: keep-alive。对于同一个域名，大多数浏览器允许同时建立6个持久连接引入了管道机制，即在同一个TCP连接里，客户端可以同时发送多个请求，进一步改进了HTTP协议的效率
同一个TCP连接里，所有的数据通信是按次序进行的。服务器只能顺序处理回应，前面的回应慢，会有许多请求排队，造成"队头堵塞"（Head-of-line blocking）
为避免上述问题，两种方法：一是减少请求数，二是同时多开持久连接
其实http1.1还是有些问题没有解决的
	1.传输数据是明文
	2.header头部数据太长
	3.每次传输还是要重新连接
    4.server不能主动push
这样就推动了http2.0的出现
#Http2.0
HTTP2.0是SPDY（谷歌公司研发的https的一种协议）的升级版
	1.头信息和数据体都是二进制，称为头信息帧和数据帧
	2.复用TCP连接，在一个连接里，客户端和浏览器都可以同时发送多个请求或回应，且不用按顺序一一对应，避免了“队头堵塞“,此双向的实时通信称为多工（Multiplexing）
	3.引入头信息压缩机制（header compression）,头信息使用gzip或compress压缩后再发送；客户端和服务器同时维护一张头信息表，所有字段都会存入这个表，生成一个索引号，不发送同样字段，只发送索引号，提高速度
	4.HTTP/2 允许服务器未经请求，主动向客户端发送资源，即服务器推送（server push）
```

###### http1.0和http1.1的区别

```
1.长连接 HTTP 1.1支持长连接（PersistentConnection）和请求的流水线（Pipelining）处理，在一个TCP连接上可以传送多个HTTP请求和响应，减少了建立和关闭连接的消耗和延迟，在HTTP1.1中默认开启Connection： keep-alive，弥补了HTTP1.0每次请求都要创建连接的缺点
2.缓存处理 在HTTP1.0中主要使用header里的If-Modified-Since,Expires来做为缓存判断的标准，HTTP1.1则引入了更多的缓存控制策略例如Entity tag，If-Unmodified-Since, If-Match, If-None-Match等更多可供选择的缓存头来控制缓存策略
3.带宽优化和网络连接的使用 HTTP1.0中，存在一些浪费带宽的现象，例如：客户端只是需要某个对象的一部分，而服务器却将整个对象送过来了，并且不支持断点续传功能，HTTP1.1则在请求头引入了range头域，它允许只请求资源的某个部分，即返回码是206（Partial Content），方便了开发者自由的选择以便于充分利用带宽和连接
4.错误通知的管理 在HTTP1.1中新增24个状态响应码，如
	409（Conflict）表示请求的资源与资源当前状态冲突
	410（Gone）表示服务器上的某个资源被永久性的删除
5.Host头处理  在HTTP1.0中认为每台服务器都绑定一个唯一的IP地址，因此，请求消息中的URL并没有传递主机名（hostname）。但随着虚拟主机技术的发展，在一台物理服务器上可以存在多个虚拟主机（Multi-homed Web Servers），并且它们共享一个IP地址。HTTP1.1的请求消息和响应消息都应支持Host头域，且请求消息中如果没有Host头域会报告一个错误（400 Bad Request）
```

##### 4.socket

```
首先socket 通信是基于TCP/IP 网络层上的一种传送方式，我们通常把TCP和UDP称为传输层。
```









