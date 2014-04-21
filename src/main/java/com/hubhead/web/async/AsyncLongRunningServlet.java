package com.hubhead.web.async;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/AsyncLongRunningServlet", asyncSupported = true)
public class AsyncLongRunningServlet extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger( AsyncLongRunningServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "AsyncLongRunningServlet Start::Name=" + Thread.currentThread().getName() + "::ID=" + Thread.currentThread().getId());

		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AppAsyncListener());
		asyncCtx.setTimeout(60000);

		ExecutorService executor = (ExecutorService) request.getServletContext().getAttribute("executor");
		executor.execute(new AsyncRequestProcessor(asyncCtx));

		long endTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "AsyncLongRunningServlet End::Name=" + Thread.currentThread().getName() + "::ID=" + Thread.currentThread().getId() + "::Time Taken=" + (endTime - startTime) + " ms.");
	}

}
