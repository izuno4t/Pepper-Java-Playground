package com.example.sample2;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALBehaviorManager;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

public class ReactToTouch {
	ALBehaviorManager behaviorManager;
	ALMemory memory;
	ALTextToSpeech tts;
	ALAnimatedSpeech speech;

	public ReactToTouch(Session session) {
		try {
			memory = new ALMemory(session);
			tts = new ALTextToSpeech(session);
			behaviorManager = new ALBehaviorManager(session);
			speech = new ALAnimatedSpeech(session);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startReacting() throws Exception {
		behaviorManager.startBehavior("animations/Stand/Gestures/Hey_1");
		tts.setLanguage("Japanese");
		speech.say("Javaデベロッパーのみなさんこんにちは");

		memory.subscribeToEvent("MiddleTactilTouched",
				new EventCallback<Float>() {
					public void onEvent(Float touched)
							throws InterruptedException, CallError {
						if (touched > 0) {
							System.out
									.println("MiddleTactilTouched event detected!");
							speech.say("頭さわったね");
						}
					}
				});

		// Java SE 8 以降　
		/*
		 * memory.subscribeToEvent("MiddleTactilTouched",touch -> { if ((float)
		 * touch == 1.0) {
		 * System.out.printf("MiddleTactilTouched event detected!");
		 * tts.say("頭さわったね"); } });
		 */
	}

	public static void main(String[] args) throws Exception {

		Application application = new Application(args,
				"tcp://192.168.3.48:9559");
		// Application application = new Application(args,
		// "tcp://127.0.0.1:62744");

		application.start();
		ReactToTouch reactToTouch = new ReactToTouch(application.session());
		reactToTouch.startReacting();
		application.run();
	}

}
