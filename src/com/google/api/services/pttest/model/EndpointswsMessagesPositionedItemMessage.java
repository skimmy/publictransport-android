/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-06-20 19:08:55 UTC)
 * on 2013-06-25 at 13:38:23 UTC 
 * Modify at your own risk.
 */

package com.google.api.services.pttest.model;

/**
 * This is the message representing Positioned Items, positioned items are defined at least by an
 * id, a position and a type. Other optional fields are: comments, pictures
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class EndpointswsMessagesPositionedItemMessage extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> comments;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String itemId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> pictures;

  /**
   * The base position message. Positions are define by latitude longitude and an optional accuracy
   * as floats
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private EndpointswsMessagesGeoPointWithAccuracyMessage position;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String type;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComments() {
    return comments;
  }

  /**
   * @param comments comments or {@code null} for none
   */
  public EndpointswsMessagesPositionedItemMessage setComments(java.util.List<java.lang.String> comments) {
    this.comments = comments;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getItemId() {
    return itemId;
  }

  /**
   * @param itemId itemId or {@code null} for none
   */
  public EndpointswsMessagesPositionedItemMessage setItemId(java.lang.String itemId) {
    this.itemId = itemId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getPictures() {
    return pictures;
  }

  /**
   * @param pictures pictures or {@code null} for none
   */
  public EndpointswsMessagesPositionedItemMessage setPictures(java.util.List<java.lang.String> pictures) {
    this.pictures = pictures;
    return this;
  }

  /**
   * The base position message. Positions are define by latitude longitude and an optional accuracy
   * as floats
   * @return value or {@code null} for none
   */
  public EndpointswsMessagesGeoPointWithAccuracyMessage getPosition() {
    return position;
  }

  /**
   * The base position message. Positions are define by latitude longitude and an optional accuracy
   * as floats
   * @param position position or {@code null} for none
   */
  public EndpointswsMessagesPositionedItemMessage setPosition(EndpointswsMessagesGeoPointWithAccuracyMessage position) {
    this.position = position;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getType() {
    return type;
  }

  /**
   * @param type type or {@code null} for none
   */
  public EndpointswsMessagesPositionedItemMessage setType(java.lang.String type) {
    this.type = type;
    return this;
  }

  @Override
  public EndpointswsMessagesPositionedItemMessage set(String fieldName, Object value) {
    return (EndpointswsMessagesPositionedItemMessage) super.set(fieldName, value);
  }

  @Override
  public EndpointswsMessagesPositionedItemMessage clone() {
    return (EndpointswsMessagesPositionedItemMessage) super.clone();
  }

}
