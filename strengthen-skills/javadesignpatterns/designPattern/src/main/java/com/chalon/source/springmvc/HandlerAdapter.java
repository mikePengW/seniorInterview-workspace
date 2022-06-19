package com.chalon.source.springmvc;

public interface HandlerAdapter {
	public boolean supports(Object handler);

	public void handle(Object handler);
}

class SimpleHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof SimpleContorller);
	}

	@Override
	public void handle(Object handler) {
		((SimpleContorller) handler).doSimplerHandler();
	}
}

class HttpHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof HttpController);
	}

	@Override
	public void handle(Object handler) {
		((HttpController) handler).doHttpHandler();
	}
}

class AnnotationHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof AnnotationController);
	}

	@Override
	public void handle(Object handler) {
		((AnnotationController) handler).doAnnotationHandler();
	}
}
