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

**项目已实现功能：**

1. 展示光明金融平台的各项基本信息

2. 各种理财产品的crud

3. 实现手机号注册以及登录token验证

4. 登录时的实名认证

5. 用户个人信息的修改和显示

6. 快钱接口的调用

7. 定时任务获取利息以及生成收益表

   