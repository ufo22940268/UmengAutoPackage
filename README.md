解决什么问题
=================

屌丝程序员(~~包括我~~)在用gradle打渠道包特别慢。

使用效果
=================

![](./cli.gif)

使用方法
======================

更改umeng渠道包的获取方法
-------------------------

1. 首先删除AndroidManifest.xml下的关于友盟渠道的那个meta-data标签
2. 在MyApplication上手动设置umeng渠道。[参见demo](https://github.com/ufo22940268/UmengAutoPackage/blob/master/sample/src/main/java/com/bettycc/sample/MyApplication.java#L24)

批量打包
-----------------------
 1. 使用原来的方法打一个apk包。

 2. 在build.gradle里面继承打包插件。
 
```groovy
    buildscript {
      repositories {
        maven {
          url "https://plugins.gradle.org/m2/"
        }
      }
      dependencies {
        classpath "gradle.plugin.com.bettycc.umengauto:core:1.0"
      }
    }
    
    apply plugin: "com.bettycc.umengauto"
```

 3. 配置生成apk文件的位置和渠道列表
 
```groovy 
       task umengBuild(type: com.bettycc.UmengTask) {
           /**
            *  编译生成的apk文件
            */
           outFile = "build/outputs/apk/sample-debug.apk"
       
           /**
            * 你的umeng渠道的列表文件
            */
           channelFile = "channels.txt";
       }
```
 
 4. 这时候调用gradle umengBuild就能够开始批量打包了。

 5. 如果你用sample中的代码进行测试的话，你可以在运行生成的apk来查看每个包的umeng渠道名。

特别感谢
=================================
- [美团大牛的文章](tech.meituan.com/mt-apk-packaging.html)
- Wen xiao fei
