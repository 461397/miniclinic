# ==========================================
# 第一階段：Build (編譯與打包環境)
# ==========================================
# 使用包含 Maven 與 JDK 17 的官方映像檔作為建置環境
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# 設定工作目錄
WORKDIR /app

# 先複製 pom.xml 並下載依賴 (這步可以利用 Docker 快取，加速後續部署)
COPY pom.xml .
RUN mvn dependency:go-offline

# 複製所有原始碼進去
COPY src ./src

# 執行 Maven 打包，略過測試以加快速度，產出 JAR 檔
RUN mvn clean package -DskipTests

# ==========================================
# 第二階段：Run (正式執行環境)
# ==========================================
# 改用非常輕量級的 JRE 17 (只包含執行環境，沒有編譯工具)
FROM eclipse-temurin:17-jre-alpine

# 設定工作目錄
WORKDIR /app

# 從第一階段 (builder) 的產出中，把編譯好的 JAR 檔複製過來並改名為 app.jar
COPY --from=builder /app/target/*.jar app.jar

# 宣告容器會使用 8080 port
EXPOSE 8080

# 啟動 Spring Boot 應用程式的指令
ENTRYPOINT ["java", "-jar", "app.jar"]