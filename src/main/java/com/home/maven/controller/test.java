package com.home.maven.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class test {

	public static void main(String[] args) throws Exception{
		
		String result = "";
		
		URL url = new URL("http://openapi.seoul.go.kr:8088"
				+ "/544d4f6a4962616e39387058634f48"
				+ "/json"
				+ "/TbHospitalInfo/1/1");
		
		BufferedReader bf = null;
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		
		bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		//bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		
		result = bf.readLine();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
		
		JSONObject resultInfo = (JSONObject)jsonObject.get("TbHospitalInfo");
		System.out.println("TbHospitalInfo : " + resultInfo.toJSONString());
		
		String resultCount = resultInfo.get("list_total_count").toString();
		
		int listTotalCount = Integer.parseInt(resultCount); //19039
		
		int roopCount = Math.round(listTotalCount/999) +1; //20
		
		System.out.println("roopCount : " + roopCount);
		
		bf.close();
		conn.disconnect();
		
		int firstPage = 0;
		int lastPage = 0;
		
		for(int i=0; i<roopCount; i++) {
			
			if(i == 0) {
				firstPage = 1;
				lastPage = 999;
			}else {
				firstPage = i*1000;
				lastPage +=1000;
			}
			
			URL url2 = new URL("http://openapi.seoul.go.kr:8088"
					+ "/544d4f6a4962616e39387058634f48"
					+ "/json"
					+ "/TbHospitalInfo"+"/"+firstPage+"/"+lastPage);
			
			System.out.println(url2);
			
			String result2 ="";
			
			BufferedReader bf2 = null;
			
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setRequestProperty("Content-type", "application/xml");
			System.out.println("Response code: " + conn2.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
			
			bf2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
			//bf2 = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
			
			result2 = bf2.readLine();
			
			JSONParser jsonParser2 = new JSONParser();
			JSONObject jsonObject2 = (JSONObject)jsonParser2.parse(result);
			JSONObject resultInfo2 = (JSONObject)jsonObject2.get("TbHospitalInfo");
			System.out.println("TbHospitalInfo : " + resultInfo2.toJSONString());
			
			bf2.close();
			conn2.disconnect();
		}
	}

}
