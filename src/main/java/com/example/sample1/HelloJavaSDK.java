package com.example.sample1;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.*;

public class HelloJavaSDK {
	public static void main(String[] args) throws Exception  {
		Application application = new Application(args,"tcp://pepper.local:9559");
		try {
			application.start();
			Session session = application.session();
			
			ALTextToSpeech tts = new ALTextToSpeech(session);
			
			tts.setLanguage("Japanese");
			tts.say("Java デベロッパーのみなさんこんにちは");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
