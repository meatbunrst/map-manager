#!/usr/bin/env bash


#国内源  yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo


docker pull mysql:5.6

docker run -p 3306:3306 --name mysql \
-v /data/mysql/log:/var/log/mysql \
-v /data/mysql/data:/var/lib/mysql \
-v /data/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=123456  \
-d mysql:5.6

docker cp /mydata/mall.sql mysql:/

docker exec -it mysql /bin/bash
mysql -uroot -p123456 --default-character-set=utf8

docker images
docker ps --no-trunc

 docker cp /tmp/blink.sql mysql:/tmp


docker run -p 6379:6379 --name redis \
-v /data/redis/data:/data \
-d redis:3.2 redis-server --appendonly yes

docker exec -it redis redis-cli


docker run -p 27017:27017 --name mongo \
-v /data/mongo/db:/data/db \
-d mongo:3.2

安装完成


docker run -p 80:80 --name nginx \
-v /data/nginx/html:/usr/share/nginx/html \
-v /data/nginx/logs:/var/log/nginx  \
-d nginx:1.10



# oracle

 docker search oracle
docker images
 docker run -d -p 1521:1521 -p 8080:8080 jaspeen/oracle-11g


aspeen/oracle-11g安装
docker run -it --privileged --name oracle11g -p 1521:1521 -v /data/oracle:/install jaspeen/oracle-11g bash

日志
sudo docker logs -f -t --tail 10 9be48

删除启动项
sudo docker rm 9be48

打开防火墙
firewall-cmd --zone=public --add-port=1521/tcp --permanent


# 删除镜像
docker ps -a
docker rm -f a658

 docker images
 docker rmi -f fce2