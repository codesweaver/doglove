package kr.co.doglove.doglove.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordr_item_id")
    private long id;

    private int quantity;
    private Double unitPrice;

    /** fetch = FetchType.LAZY 지연로딩  fetch = FetchType.EAGER 즉시로딩**/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Builder
    public OrderItem(int quantity, Double unitPrice, Goods goods) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.goods = goods;
    }
}
