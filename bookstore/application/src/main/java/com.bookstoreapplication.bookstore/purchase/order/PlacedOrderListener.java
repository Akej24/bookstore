package com.bookstoreapplication.bookstore.purchase.order;

import com.bookstoreapplication.bookstore.mail_sender.IEmailSenderService;
import com.bookstoreapplication.bookstore.user.UserFacade;
import com.bookstoreapplication.bookstore.user.value_objects.UserEmail;
import com.bookstoreapplication.bookstore.user.value_objects.Username;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PlacedOrderListener {

    private final IEmailSenderService emailSenderService;

    @EventListener
    public void handlePlacedOrderEvent(PlacedOrderEvent placedOrderEvent) {
        Username username = UserFacade.getUsernameByCustomerId(placedOrderEvent.getOrder().getCustomerId());
        UserEmail userEmail = UserFacade.getUserEmailByCustomerId(placedOrderEvent.getOrder().getCustomerId());

        String toEmail = userEmail.getEmail();
        String subject = "Order number " + placedOrderEvent.getOrder().getOrderId() + " in Bookstore Application";
        String text = generateText(placedOrderEvent, username);
        emailSenderService.sendEmail(toEmail.trim(), subject.trim(), text.trim());
    }

    private static String generateText(PlacedOrderEvent placedOrderEvent, Username username) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder
                .append("Welcome ")
                .append(username.getUsername())
                .append(", we have registered a new order to your Bookstore Application account, below are the order details: \n\n");
        placedOrderEvent.getCart()
                .getCartLines()
                .forEach( cartLine -> textBuilder
                        .append("Amount: ")
                        .append(cartLine.getAmount().getBooksAmount())
                        .append("\n")
                        .append('"')
                        .append(cartLine.getBookProduct().getBookTitle().getBookTitle())
                        .append('"')
                        .append(", ")
                        .append(cartLine.getBookProduct().getBookAuthor().getBookAuthor())
                        .append(", ")
                        .append(cartLine.getBookProduct().getBookPrice().getBookPrice())
                        .append("\n"));
        textBuilder
                .append("Total price: \n")
                .append(placedOrderEvent.getCart().getTotalPrice().getTotalPrice())
                .append("\n\n")
                .append("Payment method: \n")
                .append(placedOrderEvent.getCheckoutCart().getPaymentMethod().toString())
                .append("\n\n")
                .append("Address: \n")
                .append(placedOrderEvent.getCheckoutCart().getAddress().getPhoneNumber().getPhoneNumber())
                .append("\n")
                .append(placedOrderEvent.getCheckoutCart().getAddress().getStreet().getStreet())
                .append(" ")
                .append(placedOrderEvent.getCheckoutCart().getAddress().getStreetNumber().getStreetNumber())
                .append("\n")
                .append(placedOrderEvent.getCheckoutCart().getAddress().getZipCode().getZipCode())
                .append(" ")
                .append(placedOrderEvent.getCheckoutCart().getAddress().getCity().getCity())
                .append("\n\n")
                .append("Please see your order history on our website for more information,").
                append("\n Bookstore Application");
        return textBuilder.toString();
    }

}
