# RtbJavaLib
屏效宝rtb广告

# release
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
    RtbManager rtbManager = RtbManager.getInstance();
    
    rtbManager.init("pxbAppId", "pxbAppKey", "unicode");
    //        rtbManager.init("pxbAppId", "pxbAppKey", "unicode","ip");
    //        rtbManager.init("pxbAppId", "pxbAppKey", "unicode",113.957647,22.544867);
    //        rtbManager.init("pxbAppId", "pxbAppKey", "unicode",113.957647,22.544867,"ip");
    rtbManager.request(new OnAdListener() {
    
    @Override
    public void onAd(Message message, List<RtbAD> adList) {
    
     YLog.d(message + "," + adList);
     
      }
      
    }, new RtbSlot("广告位id", "类型", 1/*数量*/));

# 动态上报

        rtbManager.dynamicReport("广告的上报地址", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });
        
# 静态上报 推荐使用加经纬度参数的请求

        rtbManager.staticReport("广告位id", "广告id", new IRTBRequest.Callback() {
            @Override
            public void onFinish(Message message, String response) {
                YLog.d(message + " , response : "+response);
            }
        });

