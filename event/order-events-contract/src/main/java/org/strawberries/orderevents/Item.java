package org.strawberries.orderevents;

import java.util.Objects;
import java.util.UUID;

public class Item {
  private UUID id;

  private UUID productId;

  private int productAmount;

  public Item() {
  }

  public Item(UUID id, UUID productId, int productAmount) {
    this.id = id;
    this.productId = productId;
    this.productAmount = productAmount;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getProductId() {
    return productId;
  }

  public void setProductId(UUID productId) {
    this.productId = productId;
  }

  public int getProductAmount() {
    return productAmount;
  }

  public void setProductAmount(int productAmount) {
    this.productAmount = productAmount;
  }

  @Override
  public String toString() {
    return "Item{id='" + id + "', productId='" + productId + "', productAmount='" + productAmount + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item that = (Item) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(productId, that.productId) &&
        productAmount == that.productAmount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productId, productAmount);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private UUID id;

    private UUID productId;

    private int productAmount;

    public Item build() {
      Item result = new Item();
      result.id = this.id;
      result.productId = this.productId;
      result.productAmount = this.productAmount;
      return result;
    }

    public Builder id(UUID id) {
      this.id = id;
      return this;
    }

    public Builder productId(UUID productId) {
      this.productId = productId;
      return this;
    }

    public Builder productAmount(int productAmount) {
      this.productAmount = productAmount;
      return this;
    }
  }
}
