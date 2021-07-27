# course
### 启动数据库
---
docker run -d -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=course postgres:12.4

---