##一个统计点击的stormdemo,参照自《Storm实时数据处理》

####redis
redis命令
执行如下即可往redis队列中添加数据

rpush connt "{\"ip\":\"127.0.0.2\", \"url\":\"http://www.baidu.com\", \"client_key\":\"12313123\"}"