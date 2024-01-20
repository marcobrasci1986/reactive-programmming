package be.avidoo.webflux.demo.controller;

import be.avidoo.webflux.demo.model.Stock;
import be.avidoo.webflux.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{id}")
    public Mono<Stock> getOneStock(@PathVariable String id) {
        return stockService.getOneStock(id);
    }

    @GetMapping
    public Flux<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PostMapping
    public Mono<Stock> getAllStocks(@RequestBody Stock stock) {
        return stockService.createStock(stock);
    }

}
