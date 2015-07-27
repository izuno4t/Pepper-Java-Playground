package com.example.sample1;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

public class HelloJavaSDK {
	public static void main(String[] args) throws Exception {
		Application application = new Application(args,
				"tcp://192.168.3.48:9559");
		try {
			application.start();
			Session session = application.session();

			ALTextToSpeech tts = new ALTextToSpeech(session);
			float volume = tts.getVolume();
			System.out.println("ボリュームは" + volume);
			tts.setVolume(0.7f);
			tts.setLanguage("Japanese");

			// sentence += "\VCT="+ str( self.getParameter("Voice shaping (%)")
			// ) + "\ "
			// sentence += str(p)
			// sentence += "\RST\
			tts.say("\\RSPD=100\\ \\VCT=50\\ Java デベロッパーのみなさんこんにちは \\RST\\ ");
			tts.resetSpeed();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
