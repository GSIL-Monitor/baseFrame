package crawler.model;

public class Site extends BaseModel{

	private static final long serialVersionUID = 1L;
	private String url;
	private String host;
	private String companyName;
	
	public Site() {
		super();
	}

	public Site(String url, String host, String companyName) {
		this.url = url;
		this.host = host;
		this.companyName = companyName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
