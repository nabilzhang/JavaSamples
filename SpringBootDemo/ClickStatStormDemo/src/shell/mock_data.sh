#!/usr/bin/env bash
REDIS_PATH=/Users/baidu/soft/redis-3.0.6/src
$REDIS_PATH/redis-cli -r 100 rpush connt "{\"ip\":\"127.0.0.1\", \"url\":\"http://www.baidu.com/a.html\", \"client_key\":\"322\"}"
$REDIS_PATH/redis-cli -r 100 rpush connt "{\"ip\":\"127.0.0.2\", \"url\":\"http://www.baidu.com\", \"client_key\":\"12313123\"}"