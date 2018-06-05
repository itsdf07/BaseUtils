## BaseUtils
用于积累技术点沉淀，并发布于https://bintray.com/平台中，使得任意Demo均可通过compile 'xxx:xxx:xxx'进行依赖使用<br/>
## 使用:Gradlew
* 项目的build.gradle引入如下代码
```
repositories {<br/>
    jcenter()<br/>
    maven {<br/>
        url 'https://bintray.com/itsdf07/maven/'<br/>
    }<br/>
}
```
* 依赖的module中的build.gradle中引入依赖
```
compile 'com.itsdf07:utils:1.0.0'
```

## 提交记录
* 2018.06.05
增加控制删除文件时是否在线程中执行