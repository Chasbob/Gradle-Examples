package com.demo.gradle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.validation.constraints.*;

/** Greeting */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Greeting {
  @JsonProperty("greeting")
  private String greeting;

  public Greeting greeting(String greeting) {
    this.greeting = greeting;
    return this;
  }

  /**
   * Get greeting
   *
   * @return greeting
   */
  @ApiModelProperty(example = "Hello!", value = "")
  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Greeting greeting = (Greeting) o;
    return Objects.equals(this.greeting, greeting.greeting);
  }

  @Override
  public int hashCode() {
    return Objects.hash(greeting);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Greeting {\n");

    sb.append("    greeting: ").append(toIndentedString(greeting)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
