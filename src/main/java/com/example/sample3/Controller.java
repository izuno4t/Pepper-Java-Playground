package com.example.sample3;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by epinault and tcruz on 07/04/15.
 */
public class Controller {

	private static final java.lang.String APP_NAME = "DemoPepper";

	private Application application;
	private ALTextToSpeech tts;
	private ALMotion motion;
	private ALSpeechRecognition alSpeechRecognition;

	private static final String CMD_STOP = "止まれ";
	private static final String CMD_ROUND = "回れ";
	private boolean isRealRobot;
	private boolean isListening;

	public void connect(String[] args, String robotIP, final boolean isRealRobot) {

		application = new Application(args, robotIP);
		this.isRealRobot = isRealRobot;
		this.isListening = false;
		try {
			application.start();

			ALMemory alMemory = new ALMemory(application.session());
			tts = new ALTextToSpeech(application.session());
			motion = new ALMotion(application.session());

			alMemory.subscribeToEvent("MiddleTactilTouched", 
				new EventCallback<Float>() {
					public void onEvent(Float touch) throws InterruptedException, CallError {
						if ((float) touch == 1.0) {
							if (!isListening)
							{
								tts.say("回れ、か、止まれといってください");
								if (isRealRobot)
									startListening();
								isListening = true;
							}
							else
							{
								if (isRealRobot)
									stopListening();
								tts.say("終了します");
								isListening = false;
								application.stop();
							}
						}
				}
			});
			
			alMemory.subscribeToEvent("WordRecognized",
				new EventCallback<List<String>>() {
					@Override
					public void onEvent(List<String> words) throws InterruptedException, CallError {
						String word = words.get(0);
						System.out.println("Word " + word);
						followOrder(word);
					}
				});

			application.run();
			cleaning();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cleaning() {
		if (isRealRobot)
			stopListening();
	}

	private void stopListening() {
		try {
			if(alSpeechRecognition != null)
				alSpeechRecognition.unsubscribe(APP_NAME);
			alSpeechRecognition = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startListening()  {
		try {
			alSpeechRecognition = new ALSpeechRecognition(application.session());
			ArrayList<String> listOfWords = new ArrayList<String>();
			listOfWords.add(CMD_STOP);
			listOfWords.add(CMD_ROUND);

            alSpeechRecognition.setVocabulary(listOfWords, false);
            alSpeechRecognition.subscribe(APP_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void followOrder(String word) throws InterruptedException, CallError {
		switch (word) {
			case CMD_STOP : 
				motion.stopMove();
				break;
			case CMD_ROUND : 
				motion.moveToward(0.0f, 0.0f, 0.5f);
				break;
		}
	}
}
