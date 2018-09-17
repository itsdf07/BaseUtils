## BaseUtils是用来干嘛的？
我认为每位Android开发者都希望能有一套属于自己的代码，这份代码肯定蕴含着程序员的心血、汗水与泪水。

所以这个即将成为我个人在代码生涯中的财富。
## 对于BaseUtils有什么规划？
工具工具，既然是工具，那肯定是多方面的工具，那么就肯定是一个个独立的工具模块，而工具多了，就需要有一个工具箱，实现手提这样一个工具箱，就可以仗工具箱走IT路的理想！

## 对于BaseUtils，里面会有什么工具？
* alog
* okhttp3

## 对于module utils的使用
* 项目的build.gradle增加工具库的目标地址
```javascript
    repositories {
        jcenter()
        maven {
            url 'https://bintray.com/itsdf07/maven/'
        }
    }
```
* 需要依赖该工具库的Module中的'build.gradle'中添加'compile'依赖
```javascript
    compile('com.itsdf07:utils:1.2.0')
            {
                //去掉重复依赖的包
                exclude group: 'com.squareup.okhttp3'
                exclude group: 'com.squareup.okio'
                exclude group: 'com.google.code.gson'
            }
```