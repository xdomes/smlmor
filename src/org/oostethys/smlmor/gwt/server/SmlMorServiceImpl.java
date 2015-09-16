package org.oostethys.smlmor.gwt.server;

import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oostethys.schemas.x010.oostethys.OostethysDocument;
import org.oostethys.smlmor.gwt.client.rpc.*;
import org.oostethys.smlmor.gwt.client.rpc.model.BasicModels;
import org.oostethys.smlmor.gwt.client.rpc.model.OostethysValues;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * Implementation of the service.
 *
 * @author Carlos Rueda
 * @version $Id$
 */
public class SmlMorServiceImpl extends RemoteServiceServlet implements SmlMorService {
	private static final long serialVersionUID = 1L;

	private final Log log = LogFactory.getLog(SmlMorServiceImpl.class);

	private final AppInfo appInfo = new AppInfo("smlmor");

	// TODO use it ;)
//	private IServer iserver;


	private BasicModels basicModels;



	public void init() throws ServletException {
		super.init();
		log.info("initializing " +appInfo.getAppName()+ "...");
		try {
			Config.getInstance().init(getServletConfig(), log);

			appInfo.setVersion(
					Config.Prop.VERSION.getValue()+ " (" +
						Config.Prop.BUILD.getValue()  + ")"
			);

			log.info(appInfo.toString());

			// portal initialization
//			String ontServiceUrl = Config.Prop.ONT_SERVICE_URL.getValue();
//			String bioportalRestUrl = Config.Prop.BIOPORTAL_REST_URL.getValue();
//			iserver = Server.getInstance(ontServiceUrl, bioportalRestUrl);

		}
		catch (Throwable ex) {
			log.error("Cannot initialize: " +ex.getMessage(), ex);
			// TODO throw ServletException
			// NOTE: apparently this happens because getServletConfig fails in hosted mode (GWT 1.5.2).
			// Normally we should throw a Servlet exception as the following:
//			throw new ServletException("Cannot initialize", ex);
			// but, I'm ignoring it as this is currently only for version information.
		}
	}

	public void destroy() {
		super.destroy();
		log.info(appInfo+ ": destroy called.\n\n");
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}



	public BasicModels getModels() {
		if ( basicModels == null ) {
			basicModels = ModelsCreator.createModels();
		}
		return basicModels;
	}



	public SmlResult getSensorML(OostethysValues soostValues) {
		if ( basicModels == null ) {
			throw new IllegalStateException("getModels must be called first");
		}

		SmlResult smlResult = new SmlResult();

		SOostethys2Doc soost2doc = new SOostethys2Doc(basicModels, soostValues);
		OostethysDocument doc = soost2doc.getOostethysDocument();
		smlResult.setDump(soost2doc.getDump());

		Writer os = new StringWriter();
		try {
			Doc2Sml.getSensorML(doc, os);
			smlResult.setSml(os.toString());
		}
		catch (Exception e) {
			String error = "Error: " +e.getMessage();
			smlResult.setError(error);
		}

		return smlResult;
	}


	public SparqlQueryResult runSparqlQuery(SparqlQueryInfo query) {
//		return iserver.runSparqlQuery(query);

		System.out.println("runSparqlQuery query=" + query);
		SparqlQueryResult sparqlQueryResult = new SparqlQueryResult();
		try {
			String result;
			if ( query.getAcceptEntries().length == 0 ) {
				// this is the call used for previous version of the SparqlQueryInfo object, which didn't
				// have the acceptEntries attribute
				result = OntServiceUtil.runSparqlQuery(query.getQuery(), false, query.getFormat(), "application/rdf+xml");
			}
			else {
				// use the new call:
				result = OntServiceUtil.runSparqlQuery(query.getQuery(), false, query.getFormat());
			}
			sparqlQueryResult.setResult(result);
			//QueryUtil.parseResult(query, sparqlQueryResult);
		}
		catch (Exception e) {
			String error = "Error while dispatching query: " +e.getMessage();
			sparqlQueryResult.setError(error);
		}
		System.out.println("SmlMorServiceImpl.runSparqlQuery returning sparqlQueryResult=" + sparqlQueryResult);
		return sparqlQueryResult;
	}


}

class OntServiceUtil {
	static String runSparqlQuery(
//			String endPoint,
			String query,
			boolean infer,
			String format,
			String... acceptEntries

	) throws Exception {

		String ontServiceUrl = Config.Prop.ONT_SERVICE_URL.getValue();

		query = URLEncoder.encode(query, "UTF-8");
		String ontServiceRequest = ontServiceUrl + "?infer=" +infer+ "&sparql=" +query;
		if ( format != null ) {
			ontServiceRequest += "&form=" +format;
		}
		String str = HttpUtil.getAsString(ontServiceRequest, acceptEntries);

		return str;
	}

}


class HttpUtil {
	public static String getAsString(String uri, String... acceptEntries) throws Exception {
		System.out.println("getAsString. uri= " +uri);
		String res = getAsString(uri, Integer.MAX_VALUE, acceptEntries);
		System.out.println("res= " +res);
		return res;
	}

	private static String getAsString(String uri, int maxlen, String... acceptEntries) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod meth = new GetMethod(uri);
		for ( String acceptEntry : acceptEntries ) {
			meth.addRequestHeader("accept", acceptEntry);
		}
		try {
			client.executeMethod(meth);

			if (meth.getStatusCode() == HttpStatus.SC_OK) {
				return meth.getResponseBodyAsString(maxlen);
			}
			else {
				throw new Exception("Unexpected failure: " + meth.getStatusLine().toString());
			}
		}
		finally {
			meth.releaseConnection();
		}
	}

}
