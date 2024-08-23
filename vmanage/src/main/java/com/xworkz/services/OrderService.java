package com.xworkz.services;

import com.xworkz.dto.OrderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {

    boolean saveorder(OrderDTO orderDTO);

    List<OrderDTO> getOrderDetailesByVendorID(String email);

    List<OrderDTO> getOrderHistoryByEmail(String email);

    boolean cancelOrderByAdmin(int orderId, String OrderStatus);

    List<OrderDTO> getOrderPaymentListByEmail(String email);

    boolean processInvoiceAndSendEmail(MultipartFile file, int orderId, String orderStatus);

    List<OrderDTO> getOrderDetailsByAdmin();

    List<OrderDTO> getAllOrderHistory();

    List<OrderDTO> getOrderStatusDetailsByAdmin();

    List<OrderDTO> getOrderPaymentHistory();

    boolean updateOrderByAdmin(OrderDTO orderDTO);

    boolean updateOrderStatusByAdmin(int orderId, String paymentStatus, String orderStatus, MultipartFile file);
}
