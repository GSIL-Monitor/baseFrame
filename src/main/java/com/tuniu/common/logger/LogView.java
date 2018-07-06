package com.tuniu.common.logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.tuniu.websocket.LogWebSocketHandler;

public class LogView {
	private static long lastTimeFileSize;//上次文件大小
	private static ScheduledFuture<?> handle ;
	private static ScheduledExecutorService exec; 
	
	/**
	 * 实时输出日志信息
	 * @throws IOException 
	 */
	public synchronized void realTimeShowLog(File logFile,int delay,int interval,Integer uid) throws IOException {
		final RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
		lastTimeFileSize = randomFile.length();
		if(exec==null){
			exec = Executors.newScheduledThreadPool(1);
			doSchedule(delay, interval, uid, randomFile);	
		}else{
			cancel();
		}
	}

	private void doSchedule(int delay, int interval, final Integer uid, final RandomAccessFile randomFile) {
		handle = exec.scheduleWithFixedDelay(new Runnable() {			
			@Override
			public void run() {
				try {
					randomFile.seek(lastTimeFileSize);
					String tmp = "";
					System.out.println("当前时间 ：" + new Date());
					while((tmp=randomFile.readLine())!=null){
						if(uid==null){
							System.out.println("broadcastLog to " + uid);
							LogWebSocketHandler.broadcastLog(new String(tmp.getBytes("ISO8859-1")));
						}else{
							System.out.println("sendMessage to " + uid);
							LogWebSocketHandler.sendMessageToUser(uid,new String(tmp.getBytes("ISO8859-1")));
						}
					}
					lastTimeFileSize = randomFile.length();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}, delay, interval, TimeUnit.SECONDS);
	}
	
	public synchronized void cancel(){
		if(handle!=null && exec!=null){
			handle.cancel(true);
			exec.shutdown();
			System.out.println("ScheduledFuture is Cancelled : " + handle.isCancelled());
			System.out.println("ScheduledExecutorService is shutdown : " + exec.isShutdown());
			handle=null;
			exec=null;
		}
	}

}
