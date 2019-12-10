# 数据内容模块

## 功能项说明
提供数据操作的相关内容

## 编译环境
- JDK 1.8
- MAVEN 3.6.0

## repo模块对外接口列表

* 用户账户相关[UserMapper](./src/main/java/com/zwp/travelmemories/repo/mybatis/mappers/UserMapper.java)

```
UserVo selectUserByUsername(String username);
Integer insertUser(UserVo vo);
```

* 事件点相关[EpointMapper](./src/main/java/com/zwp/travelmemories/repo/mybatis/mappers/EpointMapper.java)

```
    List<EpointVo> selectAllEpointByUid(@Param("uId") Long uId);
    Integer insertEpoint(EpointVo point);
    Integer updateEpoint(EpointVo point);
    Integer updateEpointForDelete(@Param("epId")Long epId,@Param("uId")Long uId);
```