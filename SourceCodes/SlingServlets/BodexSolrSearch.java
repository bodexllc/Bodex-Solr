import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import java.io.IOException;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

@SlingServlet(paths = "/bin/bodex/bodexsolrsearch", methods = "GET")
public class BodexSolrSearch extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = -3960692666512058118L;
	public String res;
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Content-Type", "application/json");
		String incoming=request.getParameter("searchString");
		String srchField=request.getParameter("searchByTag");
		HttpSolrServer server=new HttpSolrServer("http://localhost:8983/solr/demo");
		SolrQuery query=new SolrQuery();
		query.set("q", ""+srchField+":*"+incoming+"");
		QueryResponse qresponse;
		try {
			qresponse = server.query(query);
			SolrDocumentList results=qresponse.getResults();			
			res=String.valueOf(results.get(0));
			for (int i = 0; i < results.size(); i++) {	
				response.getWriter().print(i+ "--> "+results.get(i)+"\n");
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
}