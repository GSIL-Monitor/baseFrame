package baseFrame;

import javax.servlet.http.HttpServlet;

//@ServerEndpoint("/websocket/chat")
public class WebSocket extends HttpServlet{
/*
	private static final long serialVersionUID = 1L;
	//当前在线连接数，线程安全
	private static int onlineCount = 0;
	private static CopyOnWriteArraySet<WebSocket> sockets = new CopyOnWriteArraySet<>();
	private Session session;
	private Timer timer = null;
	//private static String online = "login$";

	//* 连接成功调用

	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		sockets.add(this);
		addOnlineCount();
		String fullName = session.getUserPrincipal().getName();
		String userName = fullName.substring(fullName.indexOf('=')+1).replace(']', '\t');
		System.out.println("有新连接加入！当前在线人数为 ： " + getOnlineCount());
		String message = "【系统】，欢迎" + userName + "加入聊天室!" + 
				"当前在线人数为 ： " + getOnlineCount();
		//if(online.indexOf(userName)==-1)
			//online +=  userName ;
		
		//broadcast(message, session);
		//broadcast(online, session);
		 
		if(session.isOpen()){
			timer = new Timer(true);
			timer.schedule(task, 0,1000);
		}
	}
	
	TimerTask task = new TimerTask() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@Override
		public void run() {
			Date date = Calendar.getInstance().getTime();
			try {
				sendMessage(sdf.format(date));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	@OnClose
	public void onClose(){
		sockets.remove(this);
		subOnlineCount();
		System.out.println("有连接关闭！当前在线人数为 ： " + getOnlineCount());
	}
	
	private String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = Calendar.getInstance().getTime();
		return sdf.format(date);
	}
	
	@OnMessage
	public void onMessage(String message, Session session){
		String name = (session.getUserPrincipal()).getName();
		//群发消息
		for(WebSocket socket : sockets){
			try {
				if(session.isOpen())
					socket.sendMessage(getTime() + "　" + name + "  :  " + message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
				

		System.out.println("来自客户端的消息 : " + message);
//		broadcast(message, session);
	}
	
	private void broadcast(String message, Session session){
		String name = (session.getUserPrincipal()).getName();
		//群发消息
		for(WebSocket socket : sockets){
			try {
				if(session.isOpen())
					socket.sendMessage(getTime() + "　" + name + "  :  " + message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	@OnError
	public void onError(Session session , Throwable e){
		System.out.println("发生错误!");
		e.printStackTrace();
	}
	
	private void sendMessage(String message) throws IOException {
		if(session.isOpen())
			this.session.getBasicRemote().sendText(message);
	}

	private synchronized void subOnlineCount() {
		onlineCount--;
	}

	private synchronized int getOnlineCount() {
		return onlineCount;
	}
	private synchronized void addOnlineCount() {
		onlineCount++;
	}
*/
}
