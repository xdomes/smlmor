package org.oostethys.smlmor.gwt.client.rpc;

import java.io.Serializable;
import java.util.List;

/**
 * Base data for the contents of an ontology.
 */
public class BaseOntologyData implements Serializable {
  private static final long serialVersionUID = 1L;


  private List<EntityInfo> subjects;

  private List<IndividualInfo> individuals;

  private List<PropertyInfo> properties;

  private List<ClassInfo> classes;


  public BaseOntologyData() {
  }


  public List<EntityInfo> getSubjects() {
    return subjects;
  }

  public void setSubjects(List<EntityInfo> subjects) {
    this.subjects = subjects;
  }



  /**
   * @return the individuals
   */
  public List<IndividualInfo> getIndividuals() {
    return individuals;
  }


  /**
   * @param individuals the individuals to set
   */
  public void setIndividuals(List<IndividualInfo> individuals) {
    this.individuals = individuals;
  }


  /**
   * @return the properties
   */
  public List<PropertyInfo> getProperties() {
    return properties;
  }


  /**
   * @param properties the properties to set
   */
  public void setProperties(List<PropertyInfo> properties) {
    this.properties = properties;
  }


  /**
   * @return the classes
   */
  public List<ClassInfo> getClasses() {
    return classes;
  }


  /**
   * @param classes the classes to set
   */
  public void setClasses(List<ClassInfo> classes) {
    this.classes = classes;
  }




}
