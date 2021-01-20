# RtbJavaLib
屏效宝rtb广告

# release
    0.1.1
        1、添加设置超时方法
           {@link RtbManager2#setConnectTimeout(int)}
           {@link RtbManager2#setReadTimeout(int)}
    0.1.0
        1、quantity 默认0
    0.0.9
        1、去除quantity 默认值
    0.0.8
        1、去除广告位类型限制 添加两个构造方法
            {@link RtbSlot#RtbSlot(String)}
            {@link RtbSlot#RtbSlot(String, int)}
        2、修复unicode带中文不能请求问题
    0.0.7
        1、静态代码检测优化
    0.0.6
        1、废弃RtbManager 改用RtbManager2 支持多设备
        2、添加同步请求响应
    0.0.5
        1、添加支持ip、经纬度的初始化方法
    0.0.4
        1、添加对应同步方法
    
# gradle依赖
dependencies {
    implementation 'com.esell:rtb:lastVersion'
}
# maven依赖

        <dependency>
            <groupId>com.esell</groupId>
            <artifactId>rtb</artifactId>
            <version>lastVersion</version>
            <scope>compile</scope>
        </dependency>
        
# 拉取广告
        RtbManager2 rtbManager2 = RtbManager2.getInstance();
        rtbManager2.init("pxbAppId", "pxbAppKey");
        rtbManager2.request(new OnAdListener() {
            @Override
            public void onAd(Message message, List<RtbAD> adList) {
                YLog.d(message + "," + adList);
            }
        },
        new RtbSlot("广告位id", "类型", 1/*数量*/),
        new Device("unicode"));
    //        new Device("unicode","ip"));
    //        new Device("unicode",113.957647,22.544867));
    //        new Device("unicode",113.957647,22.544867,"ip"));
    //        rtbManager2.requestSync();

# 动态上报

        rtbManager.dynamicReport("广告的上报地址", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });
        
# 静态上报 推荐使用加经纬度参数的请求

        rtbManager.staticReport("设备唯一标识","广告位id", "广告id", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });

