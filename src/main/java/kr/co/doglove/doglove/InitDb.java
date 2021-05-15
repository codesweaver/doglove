package kr.co.doglove.doglove;

import kr.co.doglove.doglove.domain.Address;
import kr.co.doglove.doglove.domain.Goods;
import kr.co.doglove.doglove.domain.Order;
import kr.co.doglove.doglove.domain.OrderItem;
import kr.co.doglove.doglove.repository.GoodsRepository;
import kr.co.doglove.doglove.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        private final GoodsRepository goodsRepository;
        private final OrderRepository orderRepository;

        public void dbInit1(){
            Random random = new Random();

            // 기초상품 입력
            Goods goods1 = new Goods();
            goods1.setName("자바의 정석 Vol1");
            goodsRepository.save(goods1);

            Goods goods2 = new Goods();
            goods2.setName("자바의 정석 Vol2");
            goodsRepository.save(goods2);

            Goods goods3 = new Goods();
            goods3.setName("자바 ORM 표준 JPA 프로그래밍");
            goodsRepository.save(goods3);

            List<Goods> goodsList = new ArrayList<>(){{
                add(goods1);
                add(goods2);
                add(goods3);
            }};

            // 주문서 작성
            Order order1 = new Order();
            order1.setOrderName("김용수");
            order1.setRecvAddress(new Address("중화동","중량구","11112"));
            order1.setRecvName("김용수");

            order1.addOrderItem(new OrderItem()
                    .builder()
                    .goods(goods1)
                    .quantity(random.nextInt(2)+1)
                    .unitPrice(10.99)
                    .build()
            );
            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setOrderName("홍성현");
            order2.setRecvAddress(new Address("부평","인천","11112"));
            order2.setRecvName("홍셩현");

            for (int i = 0; i < 1; i++) {
                OrderItem build = new OrderItem()
                        .builder()
                        .goods(goodsList.get(i))
                        .quantity(random.nextInt(2)+1)
                        .unitPrice(10.99 + Double.valueOf(String.valueOf(i)))
                        .build();
                order2.addOrderItem(build);
            }

            orderRepository.save(order2);

            Order order3 = new Order();
            order3.setOrderName("이상영");
            order3.setRecvAddress(new Address("답십리","동대문구","11113"));
            order3.setRecvName("이상영");

            for (int i = 0; i < 2; i++) {
                OrderItem build = new OrderItem()
                        .builder()
                        .goods(goodsList.get(i))
                        .quantity(random.nextInt(2)+1)
                        .unitPrice(10.99 + Double.valueOf(String.valueOf(i)))
                        .build();
                order3.addOrderItem(build);
            }
            orderRepository.save(order3);

            Order order4 = new Order();
            order4.setOrderName("박성훈");
            order4.setRecvAddress(new Address("홍제동","서대문구","11114"));
            order4.setRecvName("박성훈");

            for (int i = 0; i < 3; i++) {
                OrderItem build = new OrderItem()
                        .builder()
                        .goods(goodsList.get(i))
                        .quantity(random.nextInt(2)+1)
                        .unitPrice(10.99 + Double.valueOf(String.valueOf(i)))
                        .build();
                order4.addOrderItem(build);
            }
            orderRepository.save(order4);
        }
    }
}