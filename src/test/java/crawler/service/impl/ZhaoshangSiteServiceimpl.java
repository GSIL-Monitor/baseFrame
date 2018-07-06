package crawler.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import crawler.model.Site;
import crawler.service.BaseService;
import crawler.service.SiteService;

public class ZhaoshangSiteServiceimpl implements SiteService<Site> {

	private String url;
	private String host = "www.zhaoshang100.com";
	private String companyName;
	
	public ZhaoshangSiteServiceimpl(String url) {
		this.url = url;
//		this.host = host;
//		this.companyName = companyName;
	}

	@Override
	public synchronized void execute() {
		Site site = new Site(url,host,companyName);
		//1.执行访问
		String result = run(site);
		//2.解析html
		List<Site> sites = parseHtml(result);
		//3.数据处理入库
		for(Site s : sites){
			save(s);
		}
	}

	@Override
	public String run(Site obj) {
		String url = obj.getUrl();
		String host = obj.getHost();
		//String companyName = obj.getCompanyName();
		
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(10000)
				.setConnectTimeout(10000)
				.setConnectionRequestTimeout(5000)
				.setStaleConnectionCheckEnabled(true)
				.build();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.copy(config).build();
		httpGet.setConfig(requestConfig);
		String result = "";
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpGet.addHeader("Host", host);
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			httpGet.addHeader("Accept-Encoding", "gzip, deflate");
			httpGet.addHeader("Connection", "keep-alive");
			httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
			response = httpClient.execute(httpGet);
			result = EntityUtils.toString(response.getEntity(), "GB2312");
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " , " + url + " 访问错误 , " + e.getMessage());
		}finally {
			System.out.println(Thread.currentThread().getName() + "资源清理释放...");
			httpGet.releaseConnection();
			try {
				if(httpClient!=null)
					httpClient.close();
				if(response!=null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<Site> parseHtml(String result) {
		List<String> list = new ArrayList<>();
		List<String> area = new ArrayList<>();
		if(result!=null && !"".equals(result)){
			Document doc = Jsoup.parse(result);
			//解析公司信息
			Elements elements = doc.getElementsByClass("aiMain");
			if(elements!=null && elements.size()>0){
				for(Element element : elements){
					String record = "";
					for(Element e : element.select("ul li")){
						if(e.ownText()!=null && !"".equals(e.ownText())){
							//System.out.print(e.ownText() + " , ");
							record += e.ownText() + ",";
						}
					}
					list.add(record);
					//System.out.println(" - - - - - - ");
				}
			}
			//解析省市
			
			Elements elements1 = doc.getElementsByClass("coLocation");
			if(elements1!=null && elements1.size()>0){
				for(Element element : elements1){
					String record1 = "";
					for(Element e : element.select("a")){
						if(e.ownText()!=null && !"".equals(e.ownText())){
//							System.out.print(e.ownText() + " , ");
							record1 += e.ownText() + ",";
						}
					}
					area.add(record1);
//					System.out.println(" - - - - - - ");
				}
			}
		}
		List<Site> sites = new ArrayList<>();
		for(String string : list){
			String[] arr = string.split(",");
			System.out.println(Thread.currentThread().getName() + " , 当前访问地址 : " + url);
			for(int i=0;i<arr.length;i++){
				System.out.print(arr[i] + " , ");
				
			}
			System.out.println();
			Site site = new Site();
			site.setName(arr[0]);
			site.setAddress(arr[2]);
			site.setPhone(arr[5]);
			site.setRemark(arr[1]);
			site.setSource("招商100");
			site.setAdditional("联系电话 : "+arr[3]+" , 联系QQ : "+ arr[4]);
			sites.add(site);
		}
		//处理省市
		for(int i=0 ; i<sites.size(); i++){
			String[] a = area.get(i).split(",");
			if(a!=null && a.length>1){
				sites.get(i).setProvince(a[0]);
				if(!"北京".equals(a[0]) && !"上海".equals(a[0]) && !"天津".equals(a[0]) && !"重庆".equals(a[0])){
					sites.get(i).setCity(a[1]);
				}
			}
		}
		
		return sites;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	@Override
	public void save(Site obj) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		BaseService bs = (BaseService) ac.getBean("service");
		bs.add(obj);
		
	}

}
