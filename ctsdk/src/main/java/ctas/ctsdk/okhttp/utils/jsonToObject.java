package ctas.ctsdk.okhttp.utils;

import com.alibaba.fastjson.JSON;

/**
 * Created by CTAS on 2017/8/12.
 */
public class jsonToObject {

   public static Object  toObject(String json, Class<?> c){
       Object clazz =  JSON.parseObject(json, c);
       return   clazz;
   }
}
