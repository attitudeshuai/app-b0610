# 🏃 马拉松配速计算器

一个功能完整的全栈马拉松配速计算器应用，支持配速与完赛时间的双向计算。采用现代化技术栈构建，提供优雅的响应式界面，适配PC端和移动端。

## ✨ 功能特性

- **🎯 双向计算**
  - 目标时间 → 配速计算：输入目标完赛时间，自动计算所需配速
  - 目标配速 → 时间计算：输入目标配速，预测完赛时间
  
- **📱 响应式设计**
  - PC端：双栏布局，左侧输入，右侧结果
  - H5端：单栏垂直布局，完美适配移动设备
  - 使用 Tailwind CSS 实现流畅的响应式体验

- **🏁 支持多种距离**
  - 全程马拉松（42.195公里）
  - 半程马拉松（21.0975公里）

- **🎨 现代化 UI**
  - 渐变色彩设计
  - 流畅的动画效果
  - 直观的交互体验
  - 友好的输入验证

## 🛠️ 技术栈

### 后端
- **框架**: Spring Boot 3.2.1
- **语言**: Java 17
- **构建工具**: Maven
- **API**: RESTful API

### 前端
- **框架**: Vue 3
- **构建工具**: Vite
- **样式**: Tailwind CSS
- **HTTP客户端**: Axios

### 部署
- **容器化**: Docker
- **编排**: Docker Compose
- **Web服务器**: Nginx

## 🚀 快速开始

### 前置要求

- Docker 20.10+
- Docker Compose 2.0+

### 一键启动

```bash
# 克隆项目（如果适用）
cd /path/to/project

# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

### 访问应用

前端应用将在 **http://localhost:3001** 运行。
后端 API 将在 **http://localhost:8080** 运行。**健康检查**: http://localhost:8080/api/pace/health

### 停止服务

```bash
# 停止所有服务
docker-compose down

# 停止并删除数据卷
docker-compose down -v
```

## 📁 项目结构

```
/
├── backend/                    # Spring Boot 后端
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/marathon/calculator/
│   │       │       ├── MarathonCalculatorApplication.java
│   │       │       ├── controller/
│   │       │       │   └── PaceCalculatorController.java
│   │       │       ├── service/
│   │       │       │   └── PaceCalculatorService.java
│   │       │       ├── model/
│   │       │       │   ├── TimeRequest.java
│   │       │       │   ├── PaceRequest.java
│   │       │       │   ├── DistanceType.java
│   │       │       │   └── CalculationResponse.java
│   │       │       └── config/
│   │       │           └── CorsConfig.java
│   │       └── resources/
│   │           └── application.yml
│   ├── pom.xml
│   └── Dockerfile
│
├── frontend/                   # Vue 3 前端
│   ├── src/
│   │   ├── components/
│   │   │   ├── TimeToSpeedCalculator.vue
│   │   │   ├── PaceToTimeCalculator.vue
│   │   │   └── ResultDisplay.vue
│   │   ├── api/
│   │   │   └── calculator.js
│   │   ├── assets/
│   │   │   └── main.css
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   ├── nginx.conf
│   └── Dockerfile
│
├── docker-compose.yml          # Docker Compose 配置
├── README.md                   # 项目文档
```

## 🔌 API 接口文档

### 1. 根据时间计算配速

**接口**: `POST /api/pace/calculate-from-time`

**请求体**:
```json
{
  "hours": 3,
  "minutes": 30,
  "seconds": 0,
  "distanceType": "FULL"
}
```

**响应**:
```json
{
  "distanceType": "全程马拉松",
  "distance": 42.195,
  "pace": "04:58",
  "paceMinutes": 4,
  "paceSeconds": 58,
  "finishTime": "03:30:00",
  "finishHours": 3,
  "finishMinutes": 30,
  "finishSeconds": 0,
  "totalSeconds": 12600,
  "description": "按照 04:58/公里的配速完成全程马拉松，预计用时 03:30:00"
}
```

### 2. 根据配速计算时间

**接口**: `POST /api/pace/calculate-from-pace`

**请求体**:
```json
{
  "paceMinutes": 5,
  "paceSeconds": 0,
  "distanceType": "FULL"
}
```

**响应**:
```json
{
  "distanceType": "全程马拉松",
  "distance": 42.195,
  "pace": "05:00",
  "paceMinutes": 5,
  "paceSeconds": 0,
  "finishTime": "03:30:58",
  "finishHours": 3,
  "finishMinutes": 30,
  "finishSeconds": 58,
  "totalSeconds": 12658,
  "description": "按照 05:00/公里的配速完成全程马拉松，预计用时 03:30:58"
}
```

## 💻 本地开发

### 后端开发

```bash
cd backend

# 安装依赖并运行
./mvnw spring-boot:run

# 或使用 Maven
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 前端开发

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端服务将在 http://localhost:3001 启动

### 构建生产版本

```bash
# 前端构建
cd frontend
npm run build

# 后端构建
cd backend
mvn clean package
```

## 🐛 故障排除

### Docker 容器启动失败

```bash
# 查看详细日志
docker-compose logs backend
docker-compose logs frontend

# 重新构建镜像
docker-compose build --no-cache
docker-compose up -d
```

### 端口冲突

如果 3001 或 8080 端口被占用，可以修改 `docker-compose.yml` 中的端口映射：

```yaml
ports:
  - "3002:3001"    # 前端映射到宿主机 3002
  - "8081:8080"    # 后端映射到宿主机 8081
```

### 健康检查失败

等待服务完全启动，后端大约需要 40 秒启动时间。

## 📝 许可证

本项目仅供学习和演示使用。

## 👥 作者

Created with ❤️ for marathon runners

---

**提示**: 
- 全程马拉松距离: 42.195 公里
- 半程马拉松距离: 21.0975 公里
- 配速单位: 分:秒/公里
- 完赛时间单位: 时:分:秒