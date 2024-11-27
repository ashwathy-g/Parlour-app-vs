package com.example.ParlourApp.userbilling;

import com.example.ParlourApp.cart.CartRegModel;
import com.example.ParlourApp.cart.CartRepository;
import com.example.ParlourApp.razorPay.OrderDetailsService;
import com.example.ParlourApp.razorPay.TransactionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserBillingService {
    @Autowired
    private UserBillingRepository userBillingRepository;

    @Autowired
    OrderDetailsService orderDetailsService;


    @Autowired
    CartRepository cartRepository;


    public UserBillingRegModel createUserBilling(String uniqueId, BigDecimal discount) {
        List<CartRegModel> cartItems = cartRepository.findAllByUniqueId(uniqueId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found for the given unique ID");
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(CartRegModel::getActualPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply discount if any
        if (discount != null) {
            totalPrice = totalPrice.subtract(discount);
        }


        UserBillingRegModel userBilling = new UserBillingRegModel();
        CartRegModel firstItem = cartItems.get(0);
        log.info("Setting user ID: {}", firstItem.getUserId());
        userBilling.setUserId(firstItem.getUserId());
        userBilling.setUniqueId(uniqueId);
        userBilling.setTotalPrice(totalPrice);
        userBilling.setStatus("Pending");
        userBilling.setBookingDate(firstItem.getBookingDate());
        userBilling.setBookingTime(firstItem.getBookingTime());
        userBilling.setItemId(firstItem.getItemId());
        userBilling.setParlourId(firstItem.getParlourId());
        userBilling.setEmployeeId(firstItem.getEmployeeId());
        userBilling.setQuantity(firstItem.getQuantity());
        userBillingRepository.save(userBilling);
        TransactionDetails transactionDetails = orderDetailsService.createTransaction(totalPrice.intValue(), firstItem.getUserId());
        if (transactionDetails != null) {
            userBilling.setOrderId(transactionDetails.getOrderId());
            userBilling.setPaymentId(transactionDetails.getPaymentId());


        } else {
            throw new RuntimeException("Failed to retrieve transaction details");
        }
        userBillingRepository.save(userBilling);

        return userBilling;

    }

    public Optional<UserBillingRegModel> getUserBillingById(Long id) {
        return userBillingRepository.findById(id);
    }

}



//        UserBillingRegModel userBilling = new UserBillingRegModel();
//        userBilling.setUserId(cartItems.get(0).getUserId());
//        userBilling.setUniqueId(uniqueId);
//        userBilling.setTotalPrice(totalPrice);
//        userBilling.setStatus("Pending");
//
//        // Set other fields from cartItems if needed
//
//        return userBillingRepository.save(userBilling);


