package com.mayur.solrtest;

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
import org.apache.solr.common.SolrInputDocument;


@SlingServlet(paths = "/bin/bodex/solrpostdata", methods = "GET")
public class PostData extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = -3960692666512058118L;
	public String res;
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException, IOException {
		response.setHeader("Content-Type", "application/json");
		/**/
		String incoming=request.getParameter("srchstr");
		String srchField=request.getParameter("srchfield");
	
		HttpSolrServer server=new HttpSolrServer("http://localhost:8983/solr/demo");
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id", incoming);
		doc.addField("title",srchField);
		
		try {
			server.add(doc);
			server.commit();
			response.getWriter().print("Added");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		/**/
	}