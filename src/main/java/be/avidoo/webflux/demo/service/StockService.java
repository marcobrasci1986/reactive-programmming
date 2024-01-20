package be.avidoo.webflux.demo.service;

import be.avidoo.webflux.demo.model.Stock;
import be.avidoo.webflux.demo.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StocksRepository stocksRepository;

    public Mono<Stock> getOneStock(String id) {
        return stocksRepository.findById(id);
    }

    public Flux<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }

    public Mono<Stock> createStock(Stock stock) {
        return stocksRepository.save(stock);
    }
}
