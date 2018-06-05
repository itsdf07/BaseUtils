# BaseUtils
用于积累技术点沉淀，并发布于https://bintray.com/平台中，通过compile 'xxx:xxx:xxx'进行依赖使用<br/>
1、项目的build.gradle引入<br/>
repositories {<br/>
    jcenter()<br/>
    maven {<br/>
        url 'https://bintray.com/itsdf07/maven/'<br/>
    }<br/>
}<br/>
2、依赖的module中的build.gradle中引入依赖<br/>
compile 'com.itsdf07:utils:1.0.0'<br/>

提交记录：<br/>
2018.06.05<br/>
增加控制删除文件时是否在线程中执行