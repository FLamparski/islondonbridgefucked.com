package com.filipwieland.railstatus;

public class RailStatus {

	public static void main(String[] args) throws Exception {
		new DisruptionService().run(args);

	}

}
