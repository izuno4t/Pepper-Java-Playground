package com.example.sample3;

public class Main {

	private static String REAL_ROBOT = "tcp://192.168.3.48:9559";
	private static String VIRTUAL_ROBOT = "tcp://127.0.0.1:61460";

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.connect(args, REAL_ROBOT, true); // VIRTUAL_ROBOT のとき第３引数
		// false に

		// controller.connect(args, VIRTUAL_ROBOT, false);
	}
}
