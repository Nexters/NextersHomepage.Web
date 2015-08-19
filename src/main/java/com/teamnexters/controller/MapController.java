package com.teamnexters.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamnexters.util.JsonUtil;

@Controller
public class MapController {

	@RequestMapping("api/admin/mapSearch.do")
	public @ResponseBody Map<String,Object> mapSearch(@RequestParam(value="searchValue") String value){
		
		
		try {
			CloseableHttpClient httpclient=HttpClients.createDefault();
			HttpGet httpget=new HttpGet("http://maps.google.co.kr/maps/api/geocode/json?address="+value.replaceAll(" ", "%20")+"&sensor=true");
			CloseableHttpResponse response=httpclient.execute(httpget);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JsonUtil.putSuccessJsonContainer(null);
	}
}
