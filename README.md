# RtbJavaLib
屏效宝rtb广告

# gradle依赖
dependencies {
    implementation 'com.esell:rtb:0.0.1'
}
# maven依赖

        <dependency>
            <groupId>com.esell</groupId>
            <artifactId>rtb</artifactId>
            <version>0.0.1</version>
            <scope>compile</scope>
        </dependency>
        
# 拉取广告
RtbManager rtbManager = RtbManager.getInstance();

rtbManager.init("pxbAppId", "pxbAppKey", "unicode");

rtbManager.request(new OnAdListener() {

@Override
public void onAd(Message message, List<RtbAD> adList) {

 YLog.d(message + "," + adList);
 
  }
  
}, new RtbSlot("广告位id", "类型", 1/*数量*/));
