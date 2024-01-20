package be.avidoo.webflux.demo.controller;

import be.avidoo.webflux.demo.dto.StockRequest;
import be.avidoo.webflux.demo.dto.StockResponse;
import be.avidoo.webflux.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{id}")
    public Mono<StockResponse> getOneStock(@PathVariable String id) {
        return stockService.getOneStock(id);
    }

    @GetMapping
    public Flux<StockResponse> getAllStocks(
            @RequestParam(required = false, defaultValue = "0") BigDecimal priceGreaterThan
    ) {
        return stockService.getAllStocks(priceGreaterThan);
    }

    @PostMapping
    public Mono<StockResponse> getAllStocks(@RequestBody StockRequest stockRequest) {
        return stockService.createStock(stockRequest);
    }

}
