package org.oostethys.smlmor.gwt.client.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Info about the result of performing a query
 */
public class SparqlQueryResult extends BaseResult {
  private static final long serialVersionUID = 1L;


  public static class ParsedResult extends BaseResult {
    private static final long serialVersionUID = 1L;

    public List<String> keys = new ArrayList<String>();
    public List<Map<String,String>> values = new  ArrayList<Map<String,String>>();

    @Override
    public String toString() {
      return "ParsedResult{" +
          "keys=" + keys +
          ", values=" + values +
          '}';
    }
  }


  private String query;
  private String result;

  private ParsedResult parsedResult;


  public SparqlQueryResult() {
  }


  @Override
  public String toString() {
    return "SparqlQueryResult{" +
        "query='" + query + '\'' +
        ", result='" + result + '\'' +
        ", parsedResult=" + parsedResult +
        '}';
  }

  public String getQuery() {
    return query;
  }


  public void setQuery(String query) {
    this.query = query;
  }


  public String getResult() {
    return result;
  }


  public void setResult(String result) {
    this.result = result;
  }


  public void setParsedResult(ParsedResult parsedResult) {
    this.parsedResult = parsedResult;
  }


  public ParsedResult getParsedResult() {
    return parsedResult;
  }



}
