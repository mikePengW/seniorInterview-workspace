package com.chalon.designpattern.adapter.objectadapter;

public class VoltageAdapter implements IVoltage5V {

	private Voltage220V voltage220v;

	public VoltageAdapter(Voltage220V voltage220v) {
		this.voltage220v = voltage220v;
	}

	@Override
	public int output5V() {
		int dst = 0;
		if (null != voltage220v) {
			int srcV = voltage220v.output220V();
			System.out.println("使用对象适配器，进行适配~~~");
			dst = srcV / 44;
			System.out.println("适配完成，输出的电压为=" + dst);
		}
		return dst;
	}

}
