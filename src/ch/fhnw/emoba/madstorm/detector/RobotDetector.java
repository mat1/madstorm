package ch.fhnw.emoba.madstorm.detector;

import java.util.List;

public interface RobotDetector {

	List<LegoDevice> getConnectedLegoDevices();
	
	public static class LegoDevice {
		private final String name;
		private final String mac;
		
		public LegoDevice(String name, String mac) {
			this.name = name;
			this.mac = mac;
		}
		
		public String getName() {
			return name;
		}
		public String getMac() {
			return mac;
		}
		
	}
}
