# skybase
一个提供数据服务的框架。

主要包括2大块内容：

1. 类似于Struct2的请求分发模板
2. 类似于Spring IOC容器，用于实例化类之间的关系


**参考文献**

有关请求分发请看[https://github.com/ubuntuvim/myMVC](https://github.com/ubuntuvim/myMVC)。

有关容器请看[http://blog.csdn.net/shan9liang/article/details/37598305](http://blog.csdn.net/shan9liang/article/details/37598305)和[http://blog.csdn.net/it_man/article/details/4402245](http://blog.csdn.net/it_man/article/details/4402245)


项目开发的目的：

根据请求的URL创建实体对象，并生对象对应的数据库结构。意在提供数据库服务。

通过自定义数据的适配器，自定义返回数据数据格式。

**思路**

项目初始化时构建一个容器（`HashMap<Strign, Object>`），此容器保存配置的适配器。在返回数据的总控制器中判断容器中是否有适配器，
如果有适配器则根据适配器转换数据格式，否则返回普通的json格式数据。

本框架提供2中方式的`Action`处理：

1. 有跳转的处理，比如`request.forward()`跳转或者`response.sendRedirct()`跳转
2. 无跳转处理，数据格式统一为json。返回的json格式按照提供的适配器中指定的格式返回。


### 有关jsonapi的资料

1. [json-api-zh_CN](https://github.com/justjavac/json-api-zh_CN/blob/gh-pages/examples/index.md)
2. [Node.js实现jsonapi](http://fortunejs.com/)
3. [ember-json-api](https://github.com/kurko/ember-json-api)
4. [emberfire实现](https://github.com/firebase/emberfire)

在前端和数据库中间应该需要一层格式转换层，或者不需要，如果可以直接把符合jsonapi格式的数据直接保存数据库。

**数据库**

**数据库使用MongoDB。**

有关MongoDB与Firebase的使用介绍

1. [firebase迁移到MongoDB+node.js](http://thejackalofjavascript.com/re-architecting-a-firebase-app-in-node/)
2. [构建测试数据](http://www.betterpixels.co.uk/projects/2015/05/09/mock-up-your-rest-api-with-json-server/)
3. []()

