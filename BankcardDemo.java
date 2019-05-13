package com.rzg.dgztc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

/**
 * Description: 阿里云orc--银行卡信息识别
 *
 * @author: banjingwei
 * @email: banjw_129@163.com
 * @date: 2019/5/10 18:35
 * @Copyright: 2019-2018 dgztc Inc. All rights reserved.
 */
public class BankcardDemo {

    public static void main(String[] args) {
        String host = "https://yhk.market.alicloudapi.com";
        String path = "/rest/160601/ocr/ocr_bank_card.json";
        String method = "POST";
        String appcode = "d5a9608a55a4430481a6ee2ec34c9dd8";
        String imgFile = "C:\\Users\\banjw\\Desktop\\1557485211681.jpg";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        // 对图像进行base64编码
        String imgBase64;
        try {
            File file = new File(imgFile);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        System.out.println("图片："+imgBase64);
        requestObj.put("image", imgBase64);
        String bodys = requestObj.toString();
        System.out.println("请求体："+bodys);
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            System.out.println(res_obj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
