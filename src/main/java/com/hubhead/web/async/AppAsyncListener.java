package com.hubhead.web.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppAsyncListener implements AsyncListener {
    private final static Logger LOGGER = Logger.getLogger( AppAsyncListener.class.getName());

	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
        LOGGER.log(Level.INFO, "Complete");
		// we can do resource cleanup activity here
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
        LOGGER.log(Level.INFO, "Error");
		//we can return error response to client
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        LOGGER.log(Level.INFO, "Start async");
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        LOGGER.log(Level.INFO, "AppAsyncListener onTimeout");
		//we can send appropriate response to client
		ServletResponse response = asyncEvent.getAsyncContext().getResponse();
		PrintWriter out = response.getWriter();
		out.write("TimeOut Error in Processing");
	}

}
