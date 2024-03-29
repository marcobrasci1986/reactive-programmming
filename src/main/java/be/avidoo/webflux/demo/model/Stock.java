package be.avidoo.webflux.demo.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Stock {

    @Id
    private String id;

    private String name;

    @NonNull
    private BigDecimal price;

    private String currency;

}
