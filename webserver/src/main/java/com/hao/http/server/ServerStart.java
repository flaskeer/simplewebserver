package com.hao.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class ServerStart {
	
	private static Logger logger = Logger.getLogger(ServerStart.class);
	
	private static final int DEFAULT_PORT = 8080;
	
	private static final int THREADS = 6;
	
	public static void main(String[] args) {
		String filePath = "D:/tmp";
		try {
			new ServerStart().start(getParameter(args),filePath);
		} catch (IOException e) {
			logger.error("startup error",e);
		}
	}

	/**
	 *
	 * @param port
	 * @param filePath
	 * @throws IOException
     */
	public void start(int port,String filePath) throws IOException{
		ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
		System.out.println("start listening on port..." + port);
		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
		while(true){
			executor.submit(new RequestHandler(serverSocket.accept(),filePath));
		}
	}

	/**
	 * 获取端口号
	 * @param args
	 * @return
     */
	public static int getParameter(String[] args){
		if(args.length > 0){
			int port = Integer.parseInt(args[0]);
			if(port > 0 && port < 65535){
				return port;
			}else{
				throw new NumberFormatException("invalid port");
			}
		}
		return DEFAULT_PORT;
	}

}
