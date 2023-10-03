## 项目简介

**项目名称：基于SpringBoot的光明金融理财产品**

**技术栈：**

- 后端：springboot+redis+mybatis-plus+knife4j+fastjson+jwt+httpclient
- 前端：Vue3+axios
- 项目采用dubbo+zookeeper分布式微服务架构以及前后端分离

**项目架构：**

- financial_dataservice 数据库服务以及redis缓存
- financial_web 提供前端的数据访问接口，包括平台信息，充值功能，用户信息，手机登录注册以及实名认证等功能
- financial_api  dubbo公共模块，包含通用工具类等
- financial_task  各种定时任务
- financial_pay   调用快钱api实现充值，退款，提现等功能

**项目特色：**

1.使用zookeeper作为注册中心，管理调度dubbo服务以及消费者，实现了项目的分布式架构，每个模块完成单独的功能

2.使用redis替换springboot默认的cache缓存，配置并使用自定义的redistemplate读取数据

3.使用aliyun旗下api实现手机收发验证码注册以及登录

4.使用aliyun旗下api登陆时实名验证三要素

5.使用快钱api实现复杂的付款、退款、提现功能

6.前后端分离项目，使用jwt规范的Token作为登录凭证，管理用户会话，并且前后端都对用户密码进行md5加盐处理，保证了用户信息的安全

7.使用mybatis-plus逆向工程进行crud，大大降低工作量，这样可以专注于复杂的sql语句

8.实现了task定时功能，自动计算产品收益以及创建收益表，以及fastjson返回统一的数据给前端








   
