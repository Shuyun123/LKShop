#洛克商城APP
简单商城 开源版， 包括用户注册、登录、密码找回、短信验证、用户分享、消息推送、订单上传、商品分类、商品收藏、购物车订单的增添、删除、商品查询、用户设置、用户评价、用户评论等基本功能。

###[下载最新版本](http://www.anumbrella.net/App/AppShopService/index.php)

###[豌豆荚](http://www.wandoujia.com/apps/net.anumbrella.lkshop)

###[小米应用商店](http://app.mi.com/detail/421593)

##云端：
原本打算采用后端服务平台bmob的，后来为了更方便自己自定义接口和今后的拓展，同时也是为了学习更透彻详细，所以后端采用了php。为了快速搭建完平台使用的是thinkphp框架。[后端代码](https://github.com/Shuyun123/AppShopService)。

#应用截图：
<div>
 <ul>
   <li style="list-style-type:none;margin-top:20px;">
      <image style="margin-left:10px" src = "./introduce/introduce1.png">
      <image style="margin-left:10px" src = "./introduce/introduce2.png">
      <image style="margin-left:10px" src = "./introduce/introduce3.png">
   </li>
   <br/>
   <br/>
   <br/>
   <li style="list-style-type:none;margin-top:35px;">
      <image style="margin-left:10px" src = "./introduce/introduce4.png">
      <image style="margin-left:10px" src = "./introduce/introduce5.png">
      <image style="margin-left:10px" src = "./introduce/introduce6.png">
   </li>
    <br/>
   <br/>
   <br/>
   <li style="list-style-type:none;margin-top:35px;">
      <image style="margin-left:10px" src = "./introduce/introduce7.png">
      <image style="margin-left:10px" src = "./introduce/introduce8.png">
      <image style="margin-left:10px" src = "./introduce/introduce9.png">
   </li>
 </ul>
</div>





#使用的主要技术:
Material Design的设计、Retrofit2、RxJAVA、友盟分享、消息推送、Mob的短信验证、自动更新(根据友盟提供的思路在自己的云端服务上实现的)、fresco、butterknife、图片上传、用户评价、用户评论等技术。


#说明：
在构思应用前本来是打算采用MVP框架的，同时也看了两款MVP框架的源码，感觉都不错。一款是Jude95大神写的[Beam](https://github.com/Jude95/Beam)框架，很不错的。还有一个是张涛大神写的[TheMVP](https://github.com/kymjs/TheMVP)也很棒。而最终没有使用MVP框架，而是原生的代码，主要是为了把android学习过的所有知识拉通贯彻一下。所以要想快速开发的同学可以试试这两款MVP。
在完成这款应用过程当中，参考了很多的应用，主要参考学习了朋友写的[搜图神器](https://github.com/wenhuaijun/SearchPictureTool)。这是一款很不错的应用，大家可以去学习学习。
由于个人能力有限，所以应用的代码在有些地方设计可能存在不合理，代码也显得臃肿，同时用户体验、应用流畅性、代码健壮性和可扩展性还有待改进。欢迎大家指正。

#使用的主要库:
* [ButterKnife](http://jakewharton.github.io/butterknife/)  
* [EasyRecyclerView](https://github.com/Jude95/EasyRecyclerView)  
* [RollViewPager](https://github.com/Jude95/RollViewPager)  
* [Utils](https://github.com/Jude95/Utils)  
* [Emoji](https://github.com/vanniktech/Emoji)  
* [PromptDialog](https://github.com/ifynn/PromptDialog)  
* [dialogplus](https://github.com/orhanobut/dialogplus)  
* [materialish-progress](https://github.com/pnikosis/materialish-progress) 
* [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView) 


##License:

```bash
               Copyright 2015 Anumbrella
               
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at 
        http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.







