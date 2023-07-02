package com.bookstoreapplication.bookstore.purchase.order;

import org.junit.jupiter.api.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerE2ETest {

    @LocalServerPort
    private int port;

    @Value(value = "${bookstore.rabbitmq.routing-keys.payment}")
    private String paymentRoutingKey;
    @Value("${bookstore.rabbitmq.routing-keys.books-decrement}")
    private String booksDecrementRoutingKey;
    @Value("${bookstore.rabbitmq.routing-keys.delivery}")
    private String deliveryRoutingKey;

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${spring.rabbitmq.port}")
    private Integer rabbitmqPort;

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private HttpHeaders headers;

    @BeforeTestExecution
    @AfterTestExecution
    public void cleanAllDataBeforeAndAfterTests(){
        try (Jedis jedisTemplate = new Jedis(redisHost, redisPort)) {
            jedisTemplate.flushDB();
        }
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.purgeQueue(paymentRoutingKey);
        rabbitAdmin.purgeQueue(booksDecrementRoutingKey);
        rabbitAdmin.purgeQueue(deliveryRoutingKey);
    }

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Should pass when all steps to order returned 2xx status codes")
    void doAllHttpRequestsToPlaceOrder() {
        createNewUser();
        loginUser();
        String authHeader = headers.getFirst("Authorization");

        addBookToDatabase(authHeader);
        addBookToCart(authHeader);
        checkoutCart(authHeader);
        addPayment(authHeader);
        addAddress(authHeader);
        placeOrder(authHeader);
    }

    private void createNewUser() {
        String registrationBody = "{\"email\": \"testuser@gmail.com\",\"username\":\"test\",\"password\":\"Abcdef1!\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\",\"dateOfBirth\":\"2023-01-01\",\"role\":\"ADMIN\"}";
        HttpEntity<String> registrationRequest = new HttpEntity<>(registrationBody, headers);
        ResponseEntity<Void> registrationResponse = sendRequest("/api/v1/users/registration", HttpMethod.POST, registrationRequest);
        assertTrue(registrationResponse.getStatusCode().is2xxSuccessful());
    }

    private void loginUser() {
        String loginBody = "{\"email\": \"testuser@gmail.com\",\"password\": \"Abcdef1!\"}";
        HttpEntity<String> loginRequest = new HttpEntity<>(loginBody, headers);
        ResponseEntity<Void> loginResponse = sendRequest("/api/v1/login", HttpMethod.POST, loginRequest);
        assertTrue(loginResponse.getStatusCode().is2xxSuccessful());
        headers.set("Authorization", loginResponse.getHeaders().getFirst("Authorization"));
    }

    private void addBookToDatabase(String authHeader) {
        headers.set("Authorization", authHeader);
        String addBookBody = "{\"bookTitle\": \"testTitle\",\"bookAuthor\": \"testAuthor\",\"releaseDate\": \"2020-03-01\",\"numberOfPages\": 640,\"availabilityStatus\": true,\"availablePieces\": 4,\"bookPrice\": 54.43}";
        HttpEntity<String> addBookRequest = new HttpEntity<>(addBookBody, headers);
        ResponseEntity<Void> addBookResponse = sendRequest("/api/v1/books", HttpMethod.POST, addBookRequest);
        assertTrue(addBookResponse.getStatusCode().is2xxSuccessful());
    }

    private void addBookToCart(String authHeader) {
        headers.set("Authorization", authHeader);
        String addBookToCartBody = "{\"bookId\":1}";
        HttpEntity<String> addBookToCartRequest = new HttpEntity<>(addBookToCartBody, headers);
        ResponseEntity<Void> addBookToCartResponse = sendRequest("/api/v1/cart/product", HttpMethod.POST, addBookToCartRequest);
        assertTrue(addBookToCartResponse.getStatusCode().is2xxSuccessful());
    }

    private void checkoutCart(String authHeader) {
        headers.set("Authorization", authHeader);
        HttpEntity<String> checkoutCartRequest = new HttpEntity<>(null, headers);
        ResponseEntity<Void> checkoutCartResponse = sendRequest("/api/v1/checkoutcart", HttpMethod.POST, checkoutCartRequest);
        assertTrue(checkoutCartResponse.getStatusCode().is2xxSuccessful());
    }

    private void addPayment(String authHeader) {
        headers.set("Authorization", authHeader);
        String paymentBody = "{\"paymentMethod\":\"BLIK\"}";
        HttpEntity<String> paymentRequest = new HttpEntity<>(paymentBody, headers);
        ResponseEntity<Void> paymentResponse = sendRequest("/api/v1/checkoutcart/payment", HttpMethod.PUT, paymentRequest);
        assertTrue(paymentResponse.getStatusCode().is2xxSuccessful());
    }

    private void addAddress(String authHeader) {
        headers.set("Authorization", authHeader);
        String addressBody = "{\"firstName\":\"TestFirstName\",\"lastName\":\"TestLastName\",\"phoneNumber\":\"123456789\",\"street\":\"TestStreet\",\"streetNumber\":123,\"zipCode\":\"12-345\",\"city\":\"TestCity\"}";
        HttpEntity<String> addressRequest = new HttpEntity<>(addressBody, headers);
        ResponseEntity<Void> addressResponse = sendRequest("/api/v1/checkoutcart/address", HttpMethod.PUT, addressRequest);
        assertTrue(addressResponse.getStatusCode().is2xxSuccessful());
    }

    private void placeOrder(String authHeader) {
        headers.set("Authorization", authHeader);
        HttpEntity<String> orderRequest = new HttpEntity<>(null, headers);
        ResponseEntity<Void> orderResponse = sendRequest("/api/v1/orders", HttpMethod.POST, orderRequest);
        assertTrue(orderResponse.getStatusCode().is2xxSuccessful());
    }

    private ResponseEntity<Void> sendRequest(String endpoint, HttpMethod method, HttpEntity<?> requestEntity) {
        String baseUrl = "http://localhost:" + port;
        return testRestTemplate.exchange(baseUrl + endpoint, method, requestEntity, Void.class);
    }
}