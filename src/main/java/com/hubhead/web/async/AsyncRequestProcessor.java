package com.hubhead.web.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.AsyncContext;

public class AsyncRequestProcessor implements Runnable {
    private final static int EXECUTION_TIME = 10000;

    private final static Logger LOGGER = Logger.getLogger( AsyncLongRunningServlet.class.getName());

	private AsyncContext asyncContext;

	public AsyncRequestProcessor(AsyncContext asyncCtx) {
		this.asyncContext = asyncCtx;
	}

	@Override
	public void run() {
		LOGGER.log(Level.INFO, "Async Supported? " + asyncContext.getRequest().isAsyncSupported());
        try {
            final long startTime = System.currentTimeMillis();
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.println("<HTML><BODY>");

            // wait for given time before finishing
            Thread.sleep(EXECUTION_TIME);

            out.println("<UL>");
            Arrays.asList( "one", "two", "three").stream()
                    .filter( (String it) -> it.contains("o"))
                    .forEach((String it) -> out.println("<li>Item: " + it + "</li>"));
            out.println("</UL>");
            final long endTime = System.currentTimeMillis();
            out.write("<p>Processing done for " + ( endTime - startTime) + " milliseconds</p>");
            out.println("</BODY></HTML>");
        } catch (Exception e) {
            throw new RuntimeException( "Processing failed", e);
        } finally {
            //complete the processing
            asyncContext.complete();
        }
	}
}
