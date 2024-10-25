package org.peter.order.client;

//@Service
//@RequiredArgsConstructor
//public class ProductClient {
//
//    @Value("${application.config.product-service}")
//    private String productUrl;
//    private final RestTemplate restTemplate;
//
//    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests, Integer loggedInUserId) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("loggedInUserId", String.valueOf(loggedInUserId));
//        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requests, headers);
//        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
//                productUrl + "/purchase",
//                HttpMethod.POST,
//                requestEntity,
//                responseType
//        );
//
//        if (responseEntity.getStatusCode().isError()) {
//            throw new RuntimeException("Error with order purchase in product-ms. Error: " + responseEntity.getStatusCode());
//        }
//
//        return responseEntity.getBody();
//
//    }
//}
