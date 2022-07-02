package com.chalon.source.springmvc;

import java.util.ArrayList;
import java.util.List;

public class DispatchServlet {

	public static List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();

	public DispatchServlet() {
		handlerAdapters.add(new AnnotationHandlerAdapter());
		handlerAdapters.add(new HttpHandlerAdapter());
		handlerAdapters.add(new SimpleHandlerAdapter());
	}

	public void doDispatch() {
//		HttpController controller = new HttpController();
//		AnnotationController controller = new AnnotationController();
		SimpleContorller controller = new SimpleContorller();
		
		HandlerAdapter adapter = getHandler(controller);
		adapter.handle(controller);
	}

	public HandlerAdapter getHandler(Controller controller) {
		for (HandlerAdapter adapter : handlerAdapters) {
			if (adapter.supports(controller)) {
				return adapter;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		new DispatchServlet().doDispatch();
	}

}
