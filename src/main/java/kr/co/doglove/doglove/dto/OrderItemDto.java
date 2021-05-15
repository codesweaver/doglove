package kr.co.doglove.doglove.dto;

public class OrderItemDto {
    private Long itemId;
    private String goodsName;
    private int quantity;
    private Double unitPrice;
    private Double salePrice;

    public OrderItemDto(){}

    public OrderItemDto(Long itemId, String goodsName, int quantity, Double unitPrice){
        this.itemId = itemId;
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.salePrice = unitPrice * quantity;
    }
}