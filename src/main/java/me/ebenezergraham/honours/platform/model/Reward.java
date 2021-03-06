package me.ebenezergraham.honours.platform.model;

import me.ebenezergraham.honours.platform.model.audit.DateAudit;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ebenezer Graham
 * Created on 9/30/19
 */
@Entity
@Table(name = "rewards", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "issueId",
        "url"
    })
})
public class Reward extends DateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String issueId;
  private String htmlUrl;
  private String url;

  @Transient
  private Issue issue;

  private String type;

  @Column(nullable = false)
  private String value;

  @ElementCollection
  private List<String> authorizer;

  @ElementCollection
  private List<String> receipients;

  @ElementCollection
  private Map<String, Boolean> claimConstraints;

  @ElementCollection
  private Map<String, Date> timeConstraints;

  public Reward() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIssueId() {
    return issueId;
  }

  public void setIssueId(String issueId) {
    this.issueId = issueId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<String> getAuthorizer() {
    return authorizer;
  }

  public void setAuthorizer(List<String> authorizer) {
    this.authorizer = authorizer;
  }

  @JoinColumn(name = "issue_url")
  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public List<String> getReceipients() {
    return receipients;
  }

  public void setReceipients(List<String> receipients) {
    this.receipients = receipients;
  }

  public Map<String, Boolean> getClaimConstraints() {
    return claimConstraints;
  }

  public void setClaimConstraints(Map<String, Boolean> claimConstraints) {
    this.claimConstraints = claimConstraints;
  }

  public Map<String, Date> getTimeConstraints() {
    return timeConstraints;
  }

  public void setTimeConstraints(Map<String, Date> timeConstraints) {
    this.timeConstraints = timeConstraints;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}


