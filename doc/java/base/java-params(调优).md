` JAVA_OPTS="-server -Xms2048m -Xmx2048m -XX:PermSize=1024m -XX:MaxPermSize=1024m" `
+ 1.-server: 切换成服务器模式（一定要作为第一个参数，在多个CPU时性能佳）。
+ 2.-Xms: 初始Heap（堆）大小，使用的最小内存,cpu性能高时此值应设的大一些。