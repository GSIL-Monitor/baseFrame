package crawler.dto;

import java.util.List;

import crawler.service.impl.ZhaoshangSiteServiceimpl;

public class ZhaoshangSite implements Runnable {
	
	private List<ZhaoshangSiteServiceimpl> list;
	
	public ZhaoshangSite(List<ZhaoshangSiteServiceimpl> list) {
		this.list = list;
	}

	@Override
	public void run() {
		for(ZhaoshangSiteServiceimpl service : list){
			//TimeUnit.SECONDS.sleep(1);
			service.execute();
			
		}
	}

}
