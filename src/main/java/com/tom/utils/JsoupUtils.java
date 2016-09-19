package com.tom.utils;

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
	public static void main(String[] args) throws Exception {
		String url = "http://stackoverflow.com/questions/2835505";
		Document document = Jsoup.connect(url).get();

		String question = document.select("#question .post-text").text();
		System.out.println("Question: " + question);

		Elements answerers = document.select("#answers .user-details a");
		for (Element answerer : answerers) {
			System.out.println("Answerer: " + answerer.text());
		}
	}
}
