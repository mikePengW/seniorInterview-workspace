package com.chalon.designpattern.prototype.deepclone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepPrototype implements Serializable, Cloneable {

	public String name;
	public DeepCloneableTarget deepCloneableTarget;

	public DeepPrototype() {
		super();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object deep = null;
		deep = super.clone();
		DeepPrototype deepPrototype = (DeepPrototype) deep;
		deepPrototype.deepCloneableTarget = (DeepCloneableTarget) deepCloneableTarget.clone();
		return deep;
	}

	public Object deepClone() {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);

			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			DeepPrototype copyObj = (DeepPrototype) ois.readObject();
			return copyObj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

	}

}
