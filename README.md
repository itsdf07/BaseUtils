## BaseUtils
用于积累技术点沉淀，并发布于 https://bintray.com/ 平台中，使得任意Demo均可通过 compile 'xxx:xxx:xxx' 进行依赖使用<br/>
## 使用:Gradlew
* 项目的build.gradle引入如下代码
```
repositories {
    jcenter()
    maven {
        url 'https://bintray.com/itsdf07/maven/'
    }
}
```
* 依赖的module中的build.gradle中引入依赖
```
compile 'com.itsdf07:utils:1.0.180706.1'
```

## 提交记录
* 2018.06.05
    * 增加控制删除文件时是否在线程中执行
* 2018.06.06
    * 完善ALog可自定义存储路径、自定义tag
* 2018.07.06
    * 增加AppInfoUtils工具类
* 2018.07.11
    * 增加应用重启工具类：广播接收器RestartAppReceiver
* 2018.07.18
    * 增加okhttp网络请求工具类:HttpUtils