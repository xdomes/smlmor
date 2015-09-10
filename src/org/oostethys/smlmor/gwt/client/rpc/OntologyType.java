package org.oostethys.smlmor.gwt.client.rpc;

/**
 * Ad hoc ontology categories mainly for purposes of dispatching appropriate
 * visualization/editing user interfaces.
 */
public enum OntologyType {
  /** for ontologies created with Vine */
  MAPPING,

  /** for ontologies created with voc2rdf/voc2skos */
  /** for ontologies created with voc2rdf/voc2skos */
  VOCABULARY,

  /** for other sorts of ontologies */
  OTHER,

}
