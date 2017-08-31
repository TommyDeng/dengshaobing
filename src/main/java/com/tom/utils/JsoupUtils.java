package com.tom.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午11:18:30
 *
 */

public class JsoupUtils {
	public static void findAnswerer(String[] args) throws Exception {
		String url = "http://stackoverflow.com/questions/2835505";
		Document document = Jsoup.connect(url).get();

		String question = document.select("#question .post-text").text();
		System.out.println("Question: " + question);

		Elements answerers = document.select("#answers .user-details a");
		for (Element answerer : answerers) {
			System.out.println("Answerer: " + answerer.text());
		}
	}
	
//	public static void main(String[] args) throws IOException {
////		String url = "http://www.henzan.com/live?from=plugin_toolbar";
//		
//		String url = "https://www.amazon.cn/dp/B01MR5B9G2/";
//		Document document = Jsoup.connect(url).get();
//		String targetTxt = document.html();
//		
//		System.out.println("获取结果:\n" + targetTxt);
//	}
}
