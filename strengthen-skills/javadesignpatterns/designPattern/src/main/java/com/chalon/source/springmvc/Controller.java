package com.chalon.source.springmvc;

public interface Controller {
}

class SimpleContorller implements Controller {
	public void doSimplerHandler() {
		System.out.println("simple...");
	}
}

class HttpController implements Controller {
	public void doHttpHandler() {
		System.out.println("http...");
	}
}

class AnnotationController implements Controller {
	public void doAnnotationHandler() {
		System.out.println("annotation...");
	}
}
