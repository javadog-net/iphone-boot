## 前言
### 🍊缘由
####  Iphone15来了，两年之约你还记得吗？

两年前，与特别的人有一个特别的约定。虽物是人非，但思念仍在。

![](https://img.javadog.net/blog/iphone15/3ee7fd54cb1c47f6aa018bc7380fde46.png)

遂整合之前**iphone13及iphone14的相关抢购代码**，完成一个**SpringBoot监听Iphone15有货邮件提醒+python自动化脚本**小功能。

后端基于**SpringBoot**，通过**苹果官网进行有货接口调用**，将**JSON结果解析分析**是否有货，并展示近10条有货记录列；可灵活监听对应型号，通过**邮件关联，有货邮件通知**；也可灵活进行有货**自动触发自动化Selenium+Python脚本**，从而解放双手，直接起飞。

此文以**iPhone 15 Pro Max**为例进行解析，若iPhone 15 Pro可自行修改即可。

******
### 🥝成果初展
#### 测试邮箱连通

![](https://img.javadog.net/blog/iphone15/c4be16a8be67442780901a98ee99de2f.png)

![](https://img.javadog.net/blog/iphone15/6eadaa29a0ba43d9a469178e87d15be7.png)

#### 有货订阅监听

![](https://img.javadog.net/blog/iphone15/df02e0e794734ed191bfc31e1a9a085c.png)

![](https://img.javadog.net/blog/iphone15/5cde4fdf4f964e43bb50754a1bd944ea.png)

#### 有货消息列表

![](https://img.javadog.net/blog/iphone15/befdd39a9e78487aa7117cc08434af35.png)

#### Selenium+Python自动化脚本

![](https://img.javadog.net/blog/iphone15/63298ddfc63142d7a5770459bff4cb9f.gif)

******
### 🎯主要目标
#### 实现3大重点
##### 1. SpringBoot监听Iphone有货信息
##### 2. 有货JavaMail邮件通知
##### 3. 有货自动执行Selenium+Python自动化脚本

******

##  正文
### 🍋前置条件
#### Java环境（必备）
后端以**SpringBoot为技术栈**，所以Java环境是前置条件

![](https://img.javadog.net/blog/iphone15/8ff1eceb7729482ca70822d6490853d2.png)

#### 企业/SMTP邮箱（非必须）
如果配置了邮件监听，需要一个**企业邮箱或者SMTP邮箱**进行推送，此条件**非必须**，可在项目中配置是否使用邮件推送，见下图

![](https://img.javadog.net/blog/iphone15/59a360e320c5488088fbe38dc8a860af.png)

#### Selenium+Python环境（非必须）
如果想有货自动执行**Selenium+Python自动化脚本**，就需要搭建Python相关环境。如果有不了解可以参考本狗之前的文章，里面有环境的搭建流程。

[iphone14来了，可是约好的你去哪了](https://mp.weixin.qq.com/s?__biz=MzI0MTk1OTE0OA==&mid=2247483713&idx=1&sn=9c84577835bdacbad37616c0f851b7a3&chksm=e902e8c2de7561d4fccc16ca982aa163873f893e6d4fac4767e6eb7bf7a5a0869e05e22f9ae5&token=1222235819&lang=zh_CN#rd)

[iphone13到底香不香，真的这么难抢？](https://blog.csdn.net/baidu_25986059/article/details/120722135)

常见Selenium+Python问题

**Chromedriver与Chrome版本不兼容问题**
> 参考文章
> https://blog.csdn.net/jylsrnzb/article/details/131492090

本狗提供2个脚本，可灵活使用

![](https://img.javadog.net/blog/iphone15/116424b64df047a69d7ba22ca62804df.png)

******

### 🔆技术栈
#### 后端
| 插件 | 版本 | 用途 |
| --- | ----- |  ----- |
| jdk |  1.8 |java环境 |
| lombok | 1.18.16 |代码简化插件 |
| maven | 3.6.3 |包管理工具 |
| druid| 1.1.24 | JDBC组件 |
| hutool| 5.7.20 | Java工具类库|
| mybatis-plus| 3.4.1 | 基于 MyBatis 增强工具|
| mysql | 8.0 / 5.7 | 数据库 |
| spring-boot| 2.5.15 | SpringBoot的依赖配置|

##### 前端
| 插件 | 版本 | 用途 |
| --- | ----- |  ----- |
| layui|  2.5.6 |经典开源模块化前端 UI 框架 |
| jquery | 2.1.1 |简洁的JavaScript框架 |

### 🌽重点解析
#### 苹果有货接口分析
- 苹果有货接口剖析

![](https://img.javadog.net/blog/iphone15/821c32e2a802437da07b1818360c9f75.png)

其中**第一个{}表示型号**，**第二个{}表示地区**，拼接后通过HTTP请求调用并分析是否有货

![](https://img.javadog.net/blog/iphone15/ef825b87247d40e3890009873f5c79d0.png)

- 监听定时任务时间corn表达式配置

建议使用动态ip，会避免503错误

![](https://img.javadog.net/blog/iphone15/472ae05bed4b4422949751b57c90e484.png)

![](https://img.javadog.net/blog/iphone15/8087e2ea0cd14ed0a3b5f35681b529ed.png)

#### 有货邮件推送
- 引入邮箱依赖

```xml	
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>${boot.version}</version>
</dependency>
```
- 完善邮件YMAL配置

![](https://img.javadog.net/blog/iphone15/faa316dd751249af9c2d10915de884dc.png)

- 使用JavaMailSenderImpl发送

![](https://img.javadog.net/blog/iphone15/1a956ee0e12143b2acfd6dfcdda6079f.png)

- 灵活管控是否发送邮件通知

![](https://img.javadog.net/blog/iphone15/7614bd34373c47978f9dd054779c72ba.png)

******
#### Selenium+Python自动化抢购脚本
- 型号颜色/型号容量对应map整理，用于有货后自动执行python脚本传参值对应匹配

![](https://img.javadog.net/blog/iphone15/5158b9d2f05c4390b40ac4e97aaf5a7a.png)

- 灵活管控是否自动执行Python自动化抢购脚本

![](https://img.javadog.net/blog/iphone15/d71c143314444ad584822c4a1ece3643.png)

- 执行脚本绝对路径设置

![](https://img.javadog.net/blog/iphone15/fc86705c3a524829897a40e11a426860.png)

#### 全景GIF图

![](https://img.javadog.net/blog/iphone15/68d17981caac43feadfb4c6883ba55d6.gif)


## 总结
本文通过**Java配合Python**，以SpringBoot监听苹果有货接口，从而下发进行邮件通知及自动化抢购脚本执行。**熟悉后端技术栈的同时，也可以回顾Layui及Jquery等老技术**。希望协议技术大佬可以予以指点，互相学习进步。

🏳️‍🌈写在最后：**支持国货！支持国货！支持国货！**
### 🍈猜你想问
####  如何与狗哥联系进行探讨
##### 关注公众号【JavaDog程序狗】
公众号回复【入群】或者【加入】，便可成为【程序员学习交流摸鱼群】的一员，问题随便问，牛逼随便吹。

![](https://img.javadog.net/blog/iphone15/e55c65b223664e6dbed5961ecea0d270.png)

**此群优势：**
1. 技术交流随时沟通
2. 任何私活资源免费分享
3. 实时科技动态抢先知晓
4. CSDN资源免费下载
5. 本人一切源码均群内开源，可免费使用
##### 2.踩踩狗哥博客
[javadog.net](https://www.javadog.net/)
>大家可以在里面留言，随意发挥，有问必答

![](https://img.javadog.net/blog/iphone15/b98f327f0a078f13b2b80f064914d622.png)

******
###  🍯猜你喜欢
####  文章推荐
[【项目实战】SpringBoot+uniapp+uview2打造H5+小程序+APP入门学习的聊天小项目](https://mp.weixin.qq.com/s/g7AZOWLgW5vcCahyJDEPKA)

[【项目实战】SpringBoot+uniapp+uview2打造一个企业黑红名单吐槽小程序](https://mp.weixin.qq.com/s/t_qwF_HvkdW-6TI3sYUHrA)

[【模块分层】还不会SpringBoot项目模块分层？来这手把手教你！](https://mp.weixin.qq.com/s/fpkiNR2tj832a6VxZozwDg)

[【ChatGPT】手摸手，带你玩转ChatGPT](https://mp.weixin.qq.com/s/9wEelbTN6kaChkCQHmgJMQ)

[【ChatGPT】SpringBoot+uniapp+uview2对接OpenAI，带你开发玩转ChatGPT](https://mp.weixin.qq.com/s/b19J36Eo3-ba7bHbWzoZYQ)
******
 
 ![](https://img.javadog.net/blog/iphone15/e74f3636c05a430eab8819333fa004eb.jpg)
