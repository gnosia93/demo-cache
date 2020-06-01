package com.amazon.mbp.request;

import com.amazon.mbp.common.DeliveryType;
import com.amazon.mbp.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductRequest {

    private int productId;

    private String name;

    private int price;

    private String description;

    private String thumImageUrl;

    private String imageUrl;

    private DeliveryType deliveryType;

    private int commentCount;

    private int buyCount;

    public Product toProduct() {
        return Product.builder().name(this.name)
                .price(this.price)
                .description(this.description)
                .thumImageUrl(this.thumImageUrl)
                .imageUrl(this.imageUrl)
                .deliveryType(this.deliveryType)
                .commentCount(this.commentCount)
                .buyCount(this.buyCount)
                .build();
    }

}
