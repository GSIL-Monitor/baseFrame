package crawler.test;

import java.util.ArrayList;
import java.util.List;

import crawler.dto.ZhaoshangSite;
import crawler.service.impl.ZhaoshangSiteServiceimpl;
import crawler.util.Tool;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		List<ZhaoshangSiteServiceimpl> list = new ArrayList<>();
		String url = "";
		ZhaoshangSiteServiceimpl service = null;
		for(int i=1000000;i<2000000;i++){
			url = "http://www.zhaoshang100.com/hy" + i + ".html";
			service = new ZhaoshangSiteServiceimpl(url);
			list.add(service);
		}
		
		List<Thread> threads = new ArrayList<>();
		if(list!=null && list.size()>0){
			int begin = 0;
			int end = 200000;
			ZhaoshangSite task ;
			while(true){
					List<ZhaoshangSiteServiceimpl> subSite = Tool.getSubListPage(list, begin, end);
					if(subSite!=null && subSite.size()>0){
						task = new ZhaoshangSite(subSite);
						Thread thread = new Thread(task, "thread_" + begin);
						threads.add(thread);
						thread.start();
						System.out.println(thread.getName() + "  -  启动");
						begin +=end;
					}else{
						break;
					}
				}
		}
		
		for(Thread t : threads){
			t.join();
		}
	}

}
