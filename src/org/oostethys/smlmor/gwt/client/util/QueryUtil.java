package org.oostethys.smlmor.gwt.client.util;

import java.util.HashMap;
import java.util.Map;

//import org.mmisw.iserver.gwt.client.rpc.SparqlQueryInfo;
//import org.mmisw.iserver.gwt.client.rpc.SparqlQueryResult;
//import org.mmisw.iserver.gwt.client.rpc.SparqlQueryResult.ParsedResult;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import org.oostethys.smlmor.gwt.client.rpc.SparqlQueryInfo;
import org.oostethys.smlmor.gwt.client.rpc.SparqlQueryResult;

/**
 * Provides client-side utilities related with queries.
 */
public class QueryUtil {

  /**
   * Parses a query result, expected to be in one of the formats:
   * "json", "csv".
   *
   * @param query
   * @param sqResult
   */
  public static void parseResult(SparqlQueryInfo query, SparqlQueryResult sqResult) {
    SparqlQueryResult.ParsedResult parsedResult = new SparqlQueryResult.ParsedResult();
    sqResult.setParsedResult(parsedResult);

    if ( query.getFormat().equalsIgnoreCase("json") ) {
      _parseResultJson(query, sqResult);
    }
    else if ( query.getFormat().equalsIgnoreCase("csv") ) {
      _parseResultCsv(query, sqResult);
    }
    else {
      String error = "parse result not available for output format '" +query.getFormat()+ "'.";
      parsedResult.setError(error);
      return;
    }

  }

  /**
   * Parses a query result in JSON format.
   */
  private static void _parseResultJson(SparqlQueryInfo query, SparqlQueryResult sqResult) {
    SparqlQueryResult.ParsedResult parsedResult = sqResult.getParsedResult();

    assert query.getFormat().equalsIgnoreCase("json") ;

    JSONValue jsonValue = JSONParser.parse(sqResult.getResult());

    JSONArray vars = jsonValue.isObject().get("head").isObject().get("vars").isArray();
    for ( int i = 0, cnt = vars.size(); i < cnt; i++ ) {
      String key = _valueOfNoQuotes(vars.get(i));
      parsedResult.keys.add(key);
    }

    JSONArray bindings = jsonValue.isObject().get("results").isObject().get("bindings").isArray();

    for ( int i = 0, cnt = bindings.size(); i < cnt; i++ ) {
      JSONObject tuple = bindings.get(i).isObject();
      Map<String,String> record = new HashMap<String,String>();
      for ( String key : parsedResult.keys ) {
        JSONValue val = tuple.get(key);
        // the following check because there may be OPTIONAL variables with no value
        String value = val == null ? "" : _valueOfNoQuotes(val.isObject().get("value"));
        record.put(key, value);
      }
      parsedResult.values.add(record);
    }
  }

  private static String _valueOfNoQuotes(Object obj) {
    return String.valueOf(obj).replaceAll("^\"+|\"+$", "").replaceAll("^<|>$", "");
  }

  /**
   * Parses a query result in CSV format.
   */
  private static void _parseResultCsv(SparqlQueryInfo query, SparqlQueryResult sqResult) {
    SparqlQueryResult.ParsedResult parsedResult = sqResult.getParsedResult();

    assert query.getFormat().equalsIgnoreCase("csv") ;

    String[] lines = sqResult.getResult().split("\n|\n\r|\r");
    if ( lines.length == 0 ) {
      return;   // empty
    }

    String[] keys = lines[0].split(",");
    for ( int jj = 0; jj < keys.length; jj++ ) {
      keys[jj] = _valueOfNoQuotes(keys[jj]);
      parsedResult.keys.add(keys[jj]);
    }

    for ( int ii = 1; ii < lines.length; ii++ ) {
      String[] values = lines[ii].split(",");

      int count = Math.min(keys.length, values.length);

      Map<String,String> record = new HashMap<String,String>();
      for ( int jj = 0; jj < count; jj++ ) {
        values[jj] = _valueOfNoQuotes(values[jj]);
        if(values[jj] != null && values[jj].trim().length() > 0) {
          record.put(keys[jj], values[jj].trim());
        }
      }
      if(record.size() > 0) {
        parsedResult.values.add(record);
      }
    }
  }

}
