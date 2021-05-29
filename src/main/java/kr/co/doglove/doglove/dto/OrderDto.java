package kr.co.doglove.doglove.dto;

import lombok.*;
import org.springframework.util.Assert;

@Getter
@ToString
@NoArgsConstructor
public class OrderDto {

    private String orderName;
    private String recvName;

    @Setter
    private String street;
    @Setter
    private String city;
    @Setter
    private String zipcode;

    public OrderDto(String orderName, String recvName) {
        this.orderName = orderName;
        this.recvName = recvName;
    }

    @Builder
    public OrderDto(String orderName, String recvName, String street, String city, String zipcode) {
//        Assert.hasText(orderName, "주문자명은 필수 입니다");
//        Assert.hasText(recvName, "수령인명은 필수 입니다");
        this.orderName = orderName;
        this.recvName = recvName;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}