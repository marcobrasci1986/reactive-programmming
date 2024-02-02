package be.avidoo.webflux.demo.it;

import be.avidoo.webflux.demo.client.StockMarketClient;
import be.avidoo.webflux.demo.dto.StockResponse;
import be.avidoo.webflux.demo.model.Stock;
import be.avidoo.webflux.demo.repository.StocksRepository;
import com.pluralsight.springwebflux6.stockmarket.api.currencyrate.CurrencyRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StockIt {

    private static final String STOCK_ID = "48920ba2-0795-487c-90cd-c199fb9a1d7b";
    private static final String STOCK_NAME = "Globamantics";
    private static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    private static final String STOCK_CURRENCY = "USD";
    @Autowired
    WebTestClient client;
    @MockBean
    private StocksRepository stocksRepository;
    @MockBean
    private StockMarketClient stockMarketClient;

    @Test
    void shouldGetOneStock() {
        Stock stock = Stock.builder()
                .id(STOCK_ID)
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        CurrencyRate currencyRate = CurrencyRate.builder()
                .currencyName("USD")
                .rate(BigDecimal.ONE)
                .build();

        when(stocksRepository.findById(STOCK_ID)).thenReturn(Mono.just(stock));
        when(stockMarketClient.getCurrencyRates()).thenReturn(Flux.just(currencyRate));

        // when

        StockResponse stockResponse = client.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").build(STOCK_ID))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockResponse.class)
                .returnResult()
                .getResponseBody();


        assertNotNull(stockResponse);
        assertEquals(STOCK_ID, stockResponse.getId());
        assertEquals(STOCK_NAME, stockResponse.getName());
        assertEquals(STOCK_PRICE, stockResponse.getPrice());
        assertEquals(STOCK_CURRENCY, stockResponse.getCurrency());

    }

    @Test
    void shouldReturnNotFoundForUnknownId() {
        when(stocksRepository.findById(STOCK_ID)).thenReturn(Mono.empty());
        // when
        ProblemDetail problemDetail = client.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").build(STOCK_ID))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult()
                .getResponseBody();


        // THEN
        assertTrue(problemDetail.getDetail().contains("Stock not found"));
    }
}
