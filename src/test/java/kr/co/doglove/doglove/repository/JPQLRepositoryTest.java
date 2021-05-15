package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Address;
import kr.co.doglove.doglove.domain.Order;
import kr.co.doglove.doglove.domain.OrderItem;
import kr.co.doglove.doglove.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class JPQLRepositoryTest {


    @PersistenceContext
    private EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void 이름기준파라미터바인딩() {
        List<Order> resultList = em.createQuery("select o from Order o where o.orderName=:orderName", Order.class)
                .setParameter("orderName", "홍성현")
                .getResultList();

        // 주문자명에 '홍성현'만 있어야 한다
        // 리스트의 사이즈가 1 이상이다
        assertAll("조회테스트",
                () -> assertTrue(resultList.size() > 0, "주문을 조회할 수 없습니다"),
                () -> assertTrue(()->{
                        for (Order order : resultList) {
                            if (!order.getOrderName().equals("홍성현")) {
                                return false;
                            }
                        }
                        return true;
                }, "홍성현이 아닌 주문이 있습니다")
        );
    }

    @Test
    void 파라미터바인딩_순서기준() {
        List<Order> resultList = em.createQuery("select o from Order o where o.orderName=?1", Order.class)
                .setParameter(1, "홍성현")
                .getResultList();

        // 주문자명에 '홍성현'만 있어야 한다
        // 리스트의 사이즈가 1 이상이다
        assertAll("조회테스트",
                () -> assertTrue(resultList.size() > 0, "주문을 조회할 수 없습니다"),
                () -> assertTrue(()->{
                    return resultList.stream().allMatch(order->order.getOrderName().equals("홍성현"));
                }, "홍성현이 아닌 주문이 있습니다")
        );
    }

    @Test
    void 엔티티타입_프로젝션() {
        List<Order> resultList = em.createQuery("select i.order from OrderItem i", Order.class)
                .getResultList();
        assertTrue(()-> {
            for (Order order : resultList) {
                if (!(order instanceof Order)) {
                    return false;
                }
            }
            return true;
        });
        assertTrue(()->resultList.stream().allMatch(order->order instanceof Order));
    }

    @Test
    void 임베디드타입_프로젝션() {
        List<Address> resultList = em.createQuery("select o.recvAddress from Order o", Address.class)
                .getResultList();
        for (Address address : resultList) {
            logger.info("Address = {} {} {}", address.getStreet(), address.getCity(), address.getZipcode());
        }

        assertTrue(resultList.stream().allMatch(a->a instanceof Address));
    }

    @Test
    void 스칼라타입_프로젝션() {
        List<String> resultList = em.createQuery("select o.orderName from Order o", String.class)
                .getResultList();
        for (String s : resultList) {
            logger.info("orderName = {}", s);
        }

        List<String> orderNameList = new ArrayList<>(Arrays.asList("김용수", "홍성현", "이상영", "박성훈"));
        assertTrue(resultList.stream().allMatch(s->orderNameList.contains(s)));
    }

    @Test
    void 여러값조회_일반() {
        List<Object> resultList = em.createQuery("select o.orderName, o.recvName, o.recvAddress from Order o")
                .getResultList();
        for (Object o : resultList) {
            Object[] obj = (Object[]) o;

            String orderName = (String) obj[0];
            String recvName = (String) obj[1];
            Address address = (Address) obj[2];
            logger.info("orderName: {}", orderName);
            logger.info("recvName: {}", recvName);
            logger.info("street: {}", address.getStreet());
            logger.info("city: {}", address.getCity());
            logger.info("zipcode: {}", address.getZipcode());
        }
    }

    @Test
    void 여러값조회_배열() {
        List<Object[]> resultList = em.createQuery("select o.orderName, o.recvName, o.recvAddress from Order o")
                .getResultList();
        for (Object[] obj : resultList) {
            String orderName = (String) obj[0];
            String recvName = (String) obj[1];
            Address address = (Address) obj[2];
            logger.info("orderName: {}", orderName);
            logger.info("recvName: {}", recvName);
            logger.info("street: {}", address.getStreet());
            logger.info("city: {}", address.getCity());
            logger.info("zipcode: {}", address.getZipcode());
        }
    }

    @Test
    void DTO이용하기_일반() {
        List<Object[]> resultList = em.createQuery("select o.orderName, o.recvName, o.recvAddress from Order o")
                .getResultList();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Object[] o : resultList) {
            Address recvAddress = (Address) o[2];

            OrderDto orderDto = new OrderDto();
//            orderDto.setOrderName((String) o[0]);
//            orderDto.setRecvName((String) o[1]);
            orderDto.setStreet(recvAddress.getStreet());
            orderDto.setCity(recvAddress.getCity());
            orderDto.setZipcode(recvAddress.getZipcode());
            
            orderDtoList.add(orderDto);
        }

        for (OrderDto dto : orderDtoList) {
            logger.info(dto.toString());
        }
    }

    @Test
    void DTO이용하기_생성자() {
        List<Object[]> resultList = em.createQuery("select o.orderName, o.recvName, o.recvAddress from Order o")
                .getResultList();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Object[] o : resultList) {
            Address recvAddress = (Address) o[2];
            OrderDto orderDto = new OrderDto(
                    (String) o[0],
                    (String) o[1]
//                    recvAddress.getStreet(),
//                    recvAddress.getCity(),
//                    recvAddress.getZipcode()
            );

            // 필수값은 생성자로, 변경될 수 있는 값은 setter를 제공해서 변경 할 수 있게 한다
            // 모든 값에 대한 setter를 제공하지는 않는다
            orderDto.setCity(recvAddress.getCity());
            orderDto.setStreet(recvAddress.getStreet());
            orderDto.setZipcode(recvAddress.getZipcode());

            orderDtoList.add(orderDto);
        }

        for (OrderDto dto : orderDtoList) {
            logger.info(dto.toString());
        }
    }

    @Test
    void DTO이용하기_빌더() {
        List<Object[]> resultList = em.createQuery("select o.orderName, o.recvName, o.recvAddress from Order o")
                .getResultList();
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Object[] o : resultList) {
            Address address = (Address) o[2];
            OrderDto orderDto = new OrderDto()
                    .builder()
                    .orderName((String) o[0])
                    .recvName((String) o[1])
                    .build();
            orderDto.setStreet(address.getStreet());
            orderDto.setCity(address.getCity());
            orderDto.setZipcode(address.getZipcode());

            orderDtoList.add(orderDto);
        }

        for (OrderDto dto : orderDtoList) {
            logger.info(dto.toString());
        }
    }

    @Test
    void DTD이용하기_객체생성() {
        List<OrderDto> resultList = em.createQuery("select new kr.co.doglove.doglove.dto.OrderDto(" +
                "o.orderName, o.recvName, o.recvAddress.street, o.recvAddress.city, o.recvAddress.zipcode" +
                ") from Order o", OrderDto.class)
                .getResultList();
        for (OrderDto o : resultList) {
            logger.info(o.toString());
        }
    }

    @Test
    void 페이징처리() {
        List<OrderItem> orderItemList = em.createQuery("select i from OrderItem i order by i.order.orderName desc", OrderItem.class)
                .setMaxResults(5)
                .getResultList();

        for (OrderItem orderItem : orderItemList) {
            logger.info(orderItem.toString());
        }

        assertEquals(orderItemList.size(), 5);
    }

    @Test
    void 집계함수() {
        List<Object[]> resultList = em.createQuery("select count(i), sum(i.quantity), avg(i.quantity), max(i.quantity), min(i.quantity) from OrderItem i")
                .getResultList();
        for (Object[] obj : resultList) {
            logger.info("Data: {} {} {} {} {}", obj[0], obj[1], obj[2], obj[3], obj[4]);
        }
    }

    @Test
    void 그룹화() {
        List<Object[]> resultList = em.createQuery("select i.order.id, count(i), sum(i.quantity), avg(i.quantity), max(i.quantity), min(i.quantity) " +
                "from OrderItem i " +
                "group by i.order.id " +
                "having sum(i.quantity) > 2")
                .getResultList();
        for (Object[] obj : resultList) {
            logger.info("Order.id: {}", obj[0]);
            logger.info("Count: {}", obj[1]);
            logger.info("Sum: {}", obj[2]);
            logger.info("Avg: {}", obj[3]);
            logger.info("Max: {}", obj[4]);
            logger.info("Min: {}", obj[5]);
        }
    }

    @Test
    void JPQL변환() {
        List<OrderItem> resultList = em.createQuery("select i from OrderItem i where i.order.orderName=:orderName", OrderItem.class)
                .setParameter("orderName", "박성훈")
                .getResultList();
        for (OrderItem orderItem : resultList) {
            logger.info(orderItem.toString());
        }
    }
}