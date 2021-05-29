package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Address;
import kr.co.doglove.doglove.domain.Goods;
import kr.co.doglove.doglove.domain.Order;
import kr.co.doglove.doglove.domain.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class JpqlOrderRepositoryTest {
    @Autowired
    //private JpqlOrderRepository jpqlOrderRepository;;
    private JpaOrderRepository orderRepository;;

    @Autowired
    private JpqlGoodsRepository jpqlGoodsRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void 기본데이터입력() {
        Goods goods1 = new Goods();
        goods1.setName("Java의 정석 vol.1");
        jpqlGoodsRepository.save(goods1);

        Goods goods2 = new Goods();
        goods2.setName("자바의 정석 vol.2");
        jpqlGoodsRepository.save(goods2);

        Order order = new Order();
        order.setOrderName("홍성현");
        order.setRecvName("이상영");
        order.setRecvAddress(new Address("전농로 10길 20", "서울시 동대문구", "02536"));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setGoods(goods1);
        orderItem1.setQuantity(1);
        orderItem1.setUnitPrice(10.00);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setGoods(goods2);
        orderItem2.setQuantity(1);
        orderItem2.setUnitPrice(15.00);

        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        orderRepository.save(order);
    }

    @Test
    @DisplayName("주문서 등록 테스트")
    void 주문서등록테스트() {

        List<Order> orders = orderRepository.findAll();
        Order order = orders.get(0);

        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);

        assertAll("주문 등록 테스트",
                () -> assertEquals(order, foundOrder),
                () -> assertNotEquals(order, null),
                () -> assertFalse(foundOrder.getRecvName().equals("박성훈")),
                () -> assertTrue(foundOrder.getRecvName().equals(order.getRecvName()))
                );
    }

    @Test
    @DisplayName("주문서 삭제 테스트")
    void 주문서삭제테스트() {
        List<Order> orders = orderRepository.findAll();
        Order order = orders.get(0);

        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);

        order.getOrderItems().remove(orderItem);

        em.flush();

        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);
        assertEquals(foundOrder.getOrderItems().size(), order.getOrderItems().size());
    }

    @Test
    @DisplayName("주문상품 추가 테스트")
    void 주문상품추가테스트() {
        List<Order> orders = orderRepository.findAll();
        Order order = orders.get(0);
        int currentCount = order.getOrderItems().size();

        Goods addGoods = new Goods();
        addGoods.setName("토비의 스프링");
        jpqlGoodsRepository.save(addGoods);

        OrderItem addOrderItem = new OrderItem();
        addOrderItem.setGoods(addGoods);
        addOrderItem.setQuantity(1);
        addOrderItem.setUnitPrice(20.00);

        order.addOrderItem(addOrderItem);

        em.flush();

        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);

        assertEquals(foundOrder.getOrderItems().size(), currentCount+1);
    }

    @Test
    @DisplayName("주문수량 수정 테스트")
    void 주문수량수정테스트() {
        List<Order> orders = orderRepository.findAll();
        Order order = orders.get(0);

        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = orderItems.get(0);
        int curremntQuantity = orderItem.getQuantity();

        orderItem.setQuantity(orderItem.getQuantity()+1);

        em.flush();

        Order foundOrder = orderRepository.findById(order.getId()).orElse(null);
        assertEquals(foundOrder.getOrderItems().get(0).getQuantity(), curremntQuantity+1);
    }
}