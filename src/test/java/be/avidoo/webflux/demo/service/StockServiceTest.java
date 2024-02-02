package be.avidoo.webflux.demo.service;

import be.avidoo.webflux.demo.client.StockMarketClient;
import be.avidoo.webflux.demo.dto.StockRequest;
import be.avidoo.webflux.demo.exception.StockCreationException;
import be.avidoo.webflux.demo.model.Stock;
import be.avidoo.webflux.demo.repository.StocksRepository;
import com.pluralsight.springwebflux6.stockmarket.api.stockpublish.StockPublishResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockServiceTest {

    private static final String STOCK_ID = "48920ba2-0795-487c-90cd-c199fb9a1d7b";
    private static final String STOCK_NAME = "Globamantics";
    private static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    private static final String STOCK_CURRENCY = "USD";
    @Mock
    private StocksRepository stocksRepository;
    @Mock
    private StockMarketClient stockMarketClient;
    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateStock() {
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        Stock stock = Stock.builder()
                .id(STOCK_ID)
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        StockPublishResponse stockPublishResponse = StockPublishResponse.builder()
                .stockName(STOCK_NAME)
                .price(STOCK_PRICE)
                .currencyName(STOCK_CURRENCY)
                .status("SUCCESS")
                .build();

        when(stocksRepository.save(any())).thenReturn(Mono.just(stock));
        when(stockMarketClient.publishStock(any())).thenReturn(Mono.just(stockPublishResponse));

        StepVerifier.create(stockService.createStock(stockRequest))
                .assertNext(stockResponse -> {
                    assertNotNull(stockResponse);
                    assertEquals(STOCK_ID, stockResponse.getId());
                    assertEquals(STOCK_NAME, stockResponse.getName());
                    assertEquals(STOCK_PRICE, stockResponse.getPrice());
                    assertEquals(STOCK_CURRENCY, stockResponse.getCurrency());
                })
                .verifyComplete();
    }

    @Test
    void shouldThrowExceptionWhenSavingToDB() {
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        when(stocksRepository.save(any())).thenThrow(new RuntimeException("Connection lost"));

        StepVerifier.create(stockService.createStock(stockRequest))
                .verifyError(StockCreationException.class);
    }

    @Test
    void shouldThrowException() {
        StockRequest stockRequest = StockRequest.builder()
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        Stock stock = Stock.builder()
                .id(STOCK_ID)
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();

        StockPublishResponse stockPublishResponse = StockPublishResponse.builder()
                .stockName(STOCK_NAME)
                .price(STOCK_PRICE)
                .currencyName(STOCK_CURRENCY)
                .status("FAIL")
                .build();

        when(stocksRepository.save(any())).thenReturn(Mono.just(stock));
        when(stockMarketClient.publishStock(any())).thenReturn(Mono.just(stockPublishResponse));

        StepVerifier.create(stockService.createStock(stockRequest))
                .verifyError(StockCreationException.class);
    }
}