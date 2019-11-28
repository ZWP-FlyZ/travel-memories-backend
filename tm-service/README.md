# 服务模块

## 功能项说明
完成业务功能的服务层

## 编译环境
- JDK 1.8
- MAVEN 3.6.0

## service模块对外接口

* 账户服务相关 [AccountService](./src/main/java/com/zwp/travelmemories/service/AccountService.java)
```
// 获取用户登录必要信息
UserVo getUserByUsernameForLogin(String username)
// 注册用户
boolean addUser(UserVo user)
```

* 账户服务相关 [AccountService](./src/main/java/com/zwp/travelmemories/service/AccountService.java)
```
// 获取用户所有事件点
List<EpointVo> getAllEpointsByUid(Long uId)
// 添加用户点
EpointVo addEpoint(EpointVo point)
```
