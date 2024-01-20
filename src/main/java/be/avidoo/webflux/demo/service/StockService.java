package be.avidoo.webflux.demo.service;

import be.avidoo.webflux.demo.dto.StockRequest;
import be.avidoo.webflux.demo.dto.StockResponse;
import be.avidoo.webflux.demo.exception.StockCreationException;
import be.avidoo.webflux.demo.exception.StockNotFoundException;
import be.avidoo.webflux.demo.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id) {
        return stocksRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                        new StockNotFoundException("Stock not found with id %s".formatted(id))
                ))
                .doFirst(() -> log.info("Retrieving stock with id: %s".formatted(id)))
                .doOnNext(stock -> log.info("Stock found %s".formatted(stock)))
                .doOnError(ex -> log.error("Something went wrong while retrieving the stock with id %s".formatted(id)))
                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                .doFinally(signalType -> log.info("Finalized retrieving stock with signal type %s".formatted(signalType)));
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stocksRepository.findAll()
                .filter(stock -> stock.getPrice().compareTo(priceGreaterThan) > 0)
                .map(StockResponse::fromModel)
                .doFirst(() -> log.info("Retrieving all stocks" ))
                .doOnNext(stock -> log.info("Stock found %s".formatted(stock)))
                .doOnError(ex -> log.error("Something went wrong while retrieving all stocks"))
                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                .doFinally(signalType -> log.info("Finalized retrieving stock with signal type %s".formatted(signalType)));
    }

    /**
     * onErrorReturn only works if the method that throws the exception is encapsulated in a reactive pipeline
     */
    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stocksRepository::save) // unwraps Mono or Flux from its argument
                .map(StockResponse::fromModel)
                //                .onErrorReturn(StockResponse.builder().build());
                //                .onErrorResume(ex -> {
                //                    log.warn("Exception thrown while creating new stock: ", ex);
                //                    return Mono.just(StockResponse.builder().build());
                //                });
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
