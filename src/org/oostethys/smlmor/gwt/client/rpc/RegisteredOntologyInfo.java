package org.oostethys.smlmor.gwt.client.rpc;

import java.util.ArrayList;
import java.util.List;

/**
 * Info about a registered ontology.
 */
public class RegisteredOntologyInfo extends BaseOntologyInfo {
  private static final long serialVersionUID = 1L;

  private String authority;

  private String shortName;

  /** aquaportal ontology ID used, if not null, to create a new version, in which case,
   * these values are to be transferred to a CreateOntologyInfo object. */
  private String ontologyId = null;
  private String ontologyUserId = null;

  private HostingType hostingType;


  // UNversioned URI
  private String unversionedUri;

  private String userId;
  private String username;

  private String contactName;

  private String versionNumber;

  private String dateCreated;

  private String versionStatus;



  private List<RegisteredOntologyInfo> priorVersions;


  public RegisteredOntologyInfo() {
  }


  public boolean equals(Object other) {
    return other instanceof RegisteredOntologyInfo && getUri().equals(((RegisteredOntologyInfo) other).getUri());
  }
  public int hashCode() {
    return getUri().hashCode();
  }


  /**
   * @return the authority
   */
  public String getAuthority() {
    return authority;
  }
  /**
   * @param authority the authority to set
   */
  public void setAuthority(String authority) {
    this.authority = authority;
  }

  /**
   * @return the shortName
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * @param shortName the shortName to set
   */
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /** @returns the aquaportal ontology ID used, if not null, to create a new version */
  public String getOntologyId() {
    return ontologyId;
  }

  /** sets the aquaportal ontology ID used, if not null, to create a new version */
  public void setOntologyId(String ontologyId, String ontologyUserId) {
    this.ontologyId = ontologyId;
    this.ontologyUserId = ontologyUserId;
  }

  /** @returns the aquaportal userId of the ontology ID used to create a new version */
  public String getOntologyUserId() {
    return ontologyUserId;
  }


  public void setUserId(String userId) {
    this.userId = userId;
  }
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }
  public void setVersionNumber(String versionNumber) {
    this.versionNumber = versionNumber;
  }
  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }
  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }
  /**
   * @return the contactName
   */
  public String getContactName() {
    return contactName;
  }
  /**
   * @return the versionNumber
   */
  public String getVersionNumber() {
    return versionNumber;
  }
  /**
   * @return the dateCreated
   */
  public String getDateCreated() {
    return dateCreated;
  }
  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }
  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the priorVersions
   */
  public List<RegisteredOntologyInfo> getPriorVersions() {
    if ( priorVersions == null ) {
      priorVersions = new ArrayList<RegisteredOntologyInfo>();
    }
    return priorVersions;
  }

  /**
   * @return the unversionedUri
   */
  public String getUnversionedUri() {
    return unversionedUri;
  }
  /**
   * @param unversionedUri the unversionedUri to set
   */
  public void setUnversionedUri(String unversionedUri) {
    this.unversionedUri = unversionedUri;
  }


  public void setHostingType(HostingType hostingType) {
    this.hostingType = hostingType;
  }


  public HostingType getHostingType() {
    return hostingType;
  }

  public String getVersionStatus() { return versionStatus; }
  public void setVersionStatus(String versionStatus) { this.versionStatus = versionStatus; }
}
