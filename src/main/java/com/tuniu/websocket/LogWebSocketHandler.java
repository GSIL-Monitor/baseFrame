package com.tuniu.websocket;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.GsonBuilder;
@Component
public class LogWebSocketHandler implements WebSocketHandler {
	
	public static final Map<Integer,WebSocketSession> userSocketSessionMap;
	static{
		userSocketSessionMap = new ConcurrentHashMap<Integer, WebSocketSession>();
	}
	
	//建立连接后
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		Integer uid = (Integer) session.getAttributes().get("uid");
		if(userSocketSessionMap.get(uid)==null){
			userSocketSessionMap.put(uid, session);
		}
	}
	
	/**
	 * 广播日志
	 * @throws IOException 
	 */
	public static void broadcastLog(String log) throws IOException{
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, WebSocketSession> entry = it.next();
			Integer uid = entry.getKey();
			WebSocketSession session = userSocketSessionMap.get(uid);
			if(session!=null && session.isOpen()){
				String mes = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create().toJson(log);
				session.sendMessage(new TextMessage(mes));
				System.out.println("已执行websocket群发日志");
			}
		}
	}
	
	//给指定用户发日志
	public static void sendMessageToUser(Integer uid, String log)
			throws IOException {
		WebSocketSession session = userSocketSessionMap.get(uid);
		if (session != null && session.isOpen()) {
			String mes = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create().toJson(log);
			session.sendMessage(new TextMessage(mes));
		}
	}

	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {

	}

	//消息传输错误处理
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if(session.isOpen())session.close();
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, WebSocketSession> entry = it.next();
			if(entry.getValue().getId().equals(session.getId())){
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("消息传输错误处理,socket会话已经移除用户ID: " + entry.getKey());
				break;
			}
		}
	}
	
	//关闭连接后
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		Iterator<Entry<Integer, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, WebSocketSession> entry = it.next();
			if(entry.getValue().getId().equals(session.getId())){
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("关闭连接后,socket会话已经移除用户ID: " + entry.getKey());
				break;
			}
		}
	}

	
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
