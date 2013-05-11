package ch.fhnw.emoba.madstorm.detector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Test class providing fake devices.
 */
public class DummyRobotDetector implements RobotDetector {

	private static final List<LegoDevice> devices;
	
	static {
		List<LegoDevice> devs = new LinkedList<LegoDevice>();
		devs.add(new LegoDevice("One", "00:80:41:ae:fd:7e"));
		devs.add(new LegoDevice("Two", "00:80:41:ae:fd:7a"));
		devs.add(new LegoDevice("Three", "00:80:41:ae:fd:7b"));
		devices = Collections.unmodifiableList(devs);
	}
	
	@Override
	public List<LegoDevice> getConnectedLegoDevices() {
		return devices;
	}

}
