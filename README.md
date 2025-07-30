# 工程设备监控平台 - 后端服务

这是“工程设备监控平台”项目的后端服务部分。它负责处理业务逻辑、数据持久化，并通过 REST API 和 WebSocket 为前端提供支持。

## ✨ 功能概述

*   **REST API**: 提供用于查询和更新设备数据的 HTTP 端点。
*   **WebSocket**: 实现实时通信，当设备数据发生变化时，主动将更新推送到所有连接的前端客户端。
*   **数据持久化**: 使用 Spring Data JPA 与 MySQL 数据库交互，存储和管理设备信息。

## 🛠️ 技术栈

*   **框架**: [Spring Boot 2.7.18](https://spring.io/projects/spring-boot)
*   **语言**: [Java 11](https://www.java.com/)
*   **数据库**: [MySQL](https://www.mysql.com/)
*   **数据访问**: Spring Data JPA
*   **实时通信**: Spring WebSocket (使用 STOMP 协议)
*   **构建工具**: [Apache Maven](https://maven.apache.org/)

## 🚀 如何运行

### 1. 环境准备

*   安装 [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (v11 或更高版本)
*   安装 [Apache Maven](https://maven.apache.org/)
*   安装并运行 [MySQL](https://www.mysql.com/) 数据库服务

### 2. 数据库设置

1.  登录到你的 MySQL 实例。
2.  创建一个新的数据库（例如 `crane_vista_hub`）。
3.  执行项目根目录下的 `database_init.sql` 脚本来创建 `equipment` 表并插入初始数据。

### 3. 配置文件

1.  打开 `src/main/resources/application.properties` 文件。
2.  根据你的本地环境，修改以下数据库连接配置：
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/crane_vista_hub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```

### 4. 启动服务

1.  在项目根目录下打开终端。
2.  使用 Maven 启动 Spring Boot 应用：
    ```bash
    mvn spring-boot:run
    ```
3.  服务成功启动后，将在 `http://localhost:8080` 上监听请求。

## 📡 API & WebSocket 详情

### REST API 端点

*   **`GET /api/equipment`**
    *   **描述**: 获取所有设备的完整列表。
    *   **成功响应 (200 OK)**: 返回一个包含所有设备对象的 JSON 数组。

*   **`POST /api/equipment/update`**
    *   **描述**: 接收部分或全部传感器数据，更新指定设备的信息，并触发 WebSocket 推送。
    *   **请求体 (JSON)**: 必须包含 `code` 字段用于识别设备。其他字段为可选，只提供需要更新的字段即可。
        ```json
        {
          "code": "TC-001",
          "status": "warning",
          "windSpeed": 15.5,
          "humidity": 85.0
        }
        ```
    *   **成功响应 (200 OK)**: 返回更新后的设备对象的 JSON 表示。
    *   **失败响应 (500 Internal Server Error)**: 如果找不到具有给定 `code` 的设备，将返回错误。

### WebSocket 通信

*   **连接端点**: `ws://localhost:8080/ws`
    *   客户端应使用此地址建立 WebSocket (或 SockJS) 连接。
*   **订阅主题**: `/topic/equipment-updates`
    *   **描述**: 这是一个广播主题。每当有设备信息通过 `POST /api/equipment/update` 接口成功更新后，后端会将**最新的完整设备列表**（一个 JSON 数组）发送到此主题。
    *   所有订阅了此主题的客户端都会收到该列表，从而可以刷新它们的 UI。
