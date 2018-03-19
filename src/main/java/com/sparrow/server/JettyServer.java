package com.sparrow.server;

import static com.sparrow.Const.DEFAULT_SERVER_PORT;
import static com.sparrow.Const.ENV_KEY_SERVER_PORT;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ExecutorSizedThreadPool;

import com.sparrow.Const;
import com.sparrow.Env;
import com.sparrow.Sparrow;
import com.sparrow.mvc.WebContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyServer implements Server{

	private Sparrow sparrow;
	
	private Env env;
	
	private CountDownLatch latch;
	
	private JettyServer() {};
	
	private JettyServer(Sparrow sparrow) {
		this.sparrow = sparrow;
		this.env = sparrow.getEnv();
		this.latch = sparrow.getLatch();
	}
	
	public static JettyServer instance(Sparrow sparrow) {
		return new JettyServer(sparrow);
	}
	
	@Override
	public void start() throws Exception{
		
		System.out.println("init jetty server");

		WebContext.init(sparrow);
		
        String pwdPath = System.getProperty("user.dir");
		
		int port = env.getInt(ENV_KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
		//thread pool setting
        ExecutorSizedThreadPool pool = new ExecutorSizedThreadPool(
        		new ThreadPoolExecutor(20, 1000, 
        				0, TimeUnit.MILLISECONDS, 
        				new LinkedBlockingQueue<Runnable>()));
		
		org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(pool);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setResourceBase(pwdPath);
        context.setContextPath("/");
        server.setHandler(context);
		
        String staticPath = Const.CLASS_PATH + File.separator + "static";//HACK
        
        ServletHolder holderHome = new ServletHolder("static", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", staticPath);
        log.info("resourcesBase {}", staticPath);
        holderHome.setInitParameter("dirAllowed","true");
        holderHome.setInitParameter("pathInfoOnly","true");
        context.addServlet(holderHome,"/static/*");
        
        ServletHolder holderDynamic = new ServletHolder("dispatcher", DispatcherServlet.class);
        context.addServlet(holderDynamic, "/");
        
        //set connector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});
        
		server.start();
		latch.countDown();
		server.join();
	}
	
}
