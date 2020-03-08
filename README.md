###前言
   写这个项目的目的呢，主要是看到之前阿里的一套企业级服务应用(Edas)及周边生态(ACM、HSFD等)，便想着如果自己实现一套这么一套完整的体系的话是会怎么做呢？刚好公司现在有这个抽奖需求，正好集成一下

#### 介绍
   商城抽奖模块实现(目前仅包含对C端抽奖接口实现)，架构采用Spring Cloud Alibaba技术栈，
使用sentienl作为Api入口熔断降级，Nacos作为注册中心与配置中心，使用Apache-Dubbo 2.7服务间远程调用,作为skywalking作为服务链路追踪(目前没有集成traceId组件)，可以
集成ELK，通过TraceId组为服务链路标识，能够达到通过一条traceId追踪整个链路的流程,
后续将集成docker，支持Dockerfile打包生成镜像
#### 软件架构
软件架构说明
项目环境:RocketMQ4.3 + MongoDB 4.x以上  + reids5 + Mysql5.7  
SpringCloud Alibaba 2.1.0.RELEASE + spring.boot2.2.4.RELEASE  + druid + mybatis 


整体模块包括以下:
| mrfox-redis | Redis模块组件化(封装普通的StringRedisTemple及支持Spring-Cache 模块redis作为缓存源，及集成redission作为分布式锁实现) |
| --- | --- |
| prize-api | 定义接口层 |
| prize-app | API入口 |
| prize-common | 工具类(时间工具类、雪花算法生成ID，BeanUtil等) |
| prize-dependencies | 顶层依赖 |
| prize-domain | 服务提供方 |

后续项目整体架构

后期扩展
