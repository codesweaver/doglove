package kr.co.doglove.doglove.repository;

import kr.co.doglove.doglove.domain.Address;
import kr.co.doglove.doglove.domain.Order;
import kr.co.doglove.doglove.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {

//    Order findOne(@Param("id") Long id);

    @Query("select o from Order o where o.id=:id")
    Order findOne(@Param("id") Long id);
    List<Order> findByOrderName(String orderName);

    @Query("select " +
            "new kr.co.doglove.doglove.dto.OrderDto(o.orderName, o.recvName, o.recvAddress.street, o.recvAddress.city, o.recvAddress.zipcode) " +
            "from Order o")
    List<OrderDto> findByOrderDto();

    @Query("select o.recvAddress from Order o")
    List<Address> findRecvAddress();

    @Query("select o from Order o where o.orderName in :names")
    List<Order> findByNames(@Param("names") List<String> names);
}
