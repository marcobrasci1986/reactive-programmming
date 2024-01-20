package be.avidoo.webflux.demo.controller;

import be.avidoo.webflux.demo.model.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @GetMapping("/{id}")
    public Mono<Stock> getOneStock(@PathVariable String id) {
        return Mono.just(Stock.builder().name("stock-%s".formatted(id)).build());
    }

    @GetMapping
    public Flux<Stock> getAllStocks() {
        return Flux.range(1, 5)
                .map(i -> Stock.builder().name("stock-%s".formatted(i)).build());
    }

}
