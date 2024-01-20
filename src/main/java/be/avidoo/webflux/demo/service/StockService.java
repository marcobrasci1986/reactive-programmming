package be.avidoo.webflux.demo.service;

import be.avidoo.webflux.demo.dto.StockRequest;
import be.avidoo.webflux.demo.dto.StockResponse;
import be.avidoo.webflux.demo.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id) {
        return stocksRepository.findById(id)
                .map(StockResponse::fromModel);
    }

    public Flux<StockResponse> getAllStocks() {
        return stocksRepository.findAll().map(StockResponse::fromModel);
    }

    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return stocksRepository.save(stockRequest.toModel()).map(StockResponse::fromModel);
    }
}
