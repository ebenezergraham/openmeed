package me.ebenezergraham.honours.platform.payload;

public class CriteriaDetail {
  private String message;
  private String key;
  private String value;

  public CriteriaDetail() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
