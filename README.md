# ProcessorLearning
这是一个用来演示如何编写自定义注解的例子，该示例的功能是利用编译时注解，在项目编译阶段自动生成包含一系列Activity的findViewById的模板代码块，以避免编写繁杂冗余的findViewById的过程。此外该项目还使用了反射来封装findViewById的操作，方便与编译时注解进行对比分析。

## 项目结构分析
项目主要分为两个模块：app和lib。
### app模块
app为主模块，包含一个MainActivity（负责界面展示）和一个辅助类BindUtil(使用反射来实现对findViewById操作的封装)。
### lib模块
lib模块为库模块，包含主模块使用到的几个注解的定义（DIActivity和DIView）和一个自定义注解处理器（BindViewProcessor）。注意这里使用的是java library而不是android library，因为android library里面并没有包含java注解的相关类，所以如果你使用了android library的话，在新建自定义注解处理器时，是找不到需要继承的父类AbstractProcessor的。
### 需要注意的几个地方
-  在项目根目录下的build.gradle中需要添加android-apt这个注解处理工具，否则注解无法被正确解析处理。

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'//必须加上
    }
}
```
-  在app module的build.gradle中也必须添加apt相关的插件，同时添加对lib module的依赖。

```
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'//必须加上

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.1.0'
    testCompile 'junit:junit:4.12'

    //必须加上
    compile project(':lib')
    apt project(':lib')
}
```
- 在lib module中必须添加注解自定义注解处理器需要用到的第三方类库。

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.google.auto.service:auto-service:1.0-rc3'//用来向jvm注册自定义processor
    compile 'com.squareup:javapoet:1.7.0' //用来生成优雅的java代码
}
```
## 总结
整个项目比较简单，主要是用来帮助初学者对于自定义注解的使用有一个基本的认识。事实上，如果你能熟练掌握java注解的语法，以及javapoet，那么注解处理器的作用可以变得很强大，这里推荐大家去阅读和分析那些使用apt的知名第三方开源库，如JakeWharton的butterknife，google的dagger2等，相信对于apt的理解会大有帮助。

