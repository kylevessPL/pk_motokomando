package pl.motokomando.healthcare.domain.medicines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.motokomando.healthcare.domain.model.medicines.Medicine;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;
import pl.motokomando.healthcare.domain.model.medicines.utils.OpenFDAResponse;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class MedicinesServiceImpl implements MedicinesService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient openFDAClient;

    @Autowired
    public MedicinesServiceImpl(WebClient openFDAClient) {
        this.openFDAClient = openFDAClient;
    }

    @Override
    public Medicine getMedicineByProductNDC(String productNDC) {
        Optional<OpenFDAResponse> response = openFDAClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search=product.product_ndc", "{query}")
                        .queryParam("product.product_type", "HUMAN")
                        .build(productNDC))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new NoMedicinesFoundException()))
                .bodyToMono(OpenFDAResponse.class)
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(e -> e.getMedicines().get(0)).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Medicine> searchMedicine(MedicineCommand medicineCommand) {
        Optional<OpenFDAResponse> response = openFDAClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", "{search}")
                        .queryParam("product.product_type", "HUMAN")
                        .queryParamIfPresent("limit", Optional.of(medicineCommand.getLimit()))
                        .build(medicineCommand.getQuery()))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new NoMedicinesFoundException()))
                .bodyToMono(OpenFDAResponse.class)
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(OpenFDAResponse::getMedicines).orElseThrow(RuntimeException::new);
    }

}
