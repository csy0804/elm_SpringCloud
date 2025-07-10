# SpringCloud Elm 微服务项目

## 项目简介
本项目是基于 Spring Cloud 的饿了么外卖系统微服务架构实现，包含用户、商家、订单、购物车、配送、积分、网关、配置中心、注册中心等多个微服务模块，适合学习和实践微服务架构设计与开发。

## 目录结构
```
springcloud_elm/
│
├── business_server_10300/   # 商家服务（端口10300）
├── business_server_10301/   # 商家服务副本
├── cart_server_10400/       # 购物车服务（端口10400）
├── cart_server_10401/       # 购物车服务副本
├── config_server_15000/     # 配置中心（端口15000）
├── config_server_15001/     # 配置中心副本
├── credit_server_10700/     # 积分服务（端口10700）
├── deliveryaddress_server_10500/ # 配送地址服务（端口10500）
├── eureka_server_13000/     # 注册中心（端口13000）
├── eureka_server_13001/     # 注册中心副本
├── food_server_10200/       # 菜品服务（端口10200）
├── food_server_10201/       # 菜品服务副本
├── gateway_server_14000/    # 网关服务（端口14000）
├── gpt_server_10900/        # GPT服务（端口10900，可选）
├── orders_server_10600/     # 订单服务（端口10600）
├── orders_server_10601/     # 订单服务副本
├── user_server_10100/       # 用户服务（端口10100）
├── docker-compose.yml       # Docker编排文件
├── start-services.bat       # 一键启动所有服务脚本（Windows）
├── stop-services.bat        # 一键停止所有服务脚本（Windows）
├── elm.sql                  # 数据库初始化脚本
└── pom.xml                  # 父级Maven配置
```

## 技术栈
- Spring Cloud
- Spring Boot
- Spring Cloud Gateway
- Eureka 注册中心
- Spring Cloud Config 配置中心
- MyBatis/MyBatis-Plus
- MySQL
- Docker

## 启动方式
1. Docker 启动：
    - 配置好 `docker-compose.yml` 后，运行 `docker-compose up -d`。
2. 数据库准备：
    - 使用 `elm.sql` 初始化 MySQL 数据库。
3. 配置修改：
    - 根据实际环境修改各服务下的 `application.yml` 或 `bootstrap.yml`。
4. 启动注册中心、配置中心：
    - 先启动 `eureka_server_13000`、`eureka_server_13001`、`config_server_15000`、`config_server_15001`。
5. 启动业务微服务：
    - 启动用户、商家、订单、购物车、配送、菜品、积分等服务。
6. 启动网关服务：
    - 启动 `gateway_server_14000`。
7. 一键启动（Windows）：
    - 可直接运行 `start-services.bat`。

## 说明
- 各服务端口可在对应 `yml` 文件中修改。
- 推荐使用 JDK 17，Maven 3.6+。
- 如需扩展服务，可参考现有模块结构。

---
如有问题欢迎提 issue 或联系作者。 