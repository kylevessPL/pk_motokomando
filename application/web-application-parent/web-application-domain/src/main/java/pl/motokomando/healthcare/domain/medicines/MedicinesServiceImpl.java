package pl.motokomando.healthcare.domain.medicines;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.motokomando.healthcare.domain.model.medicines.Medicine;
import pl.motokomando.healthcare.domain.model.medicines.utils.ActiveIngredient;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;
import pl.motokomando.healthcare.domain.model.medicines.utils.OpenFDAResponse;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class MedicinesServiceImpl implements MedicinesService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

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
                        .queryParam("search", "(product_type=HUMAN+DRUG)+AND+product_ndc=" + productNDC)
                        .build())
                .retrieve()
                .onStatus(NOT_FOUND::equals, clientResponse ->
                        Mono.error(new NoMedicinesFoundException()))
                .bodyToMono(OpenFDAResponse.class)
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(e ->
                medicineToReadable(e.getMedicines().get(0))
        ).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Medicine> searchMedicine(MedicineCommand medicineCommand) {
        Optional<OpenFDAResponse> response = openFDAClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", "(product_type=HUMAN+DRUG)+AND+" + medicineCommand.getQuery())
                        .queryParamIfPresent("limit", Optional.ofNullable(medicineCommand.getLimit()))
                        .build())
                .retrieve()
                .onStatus(NOT_FOUND::equals, clientResponse ->
                        Mono.error(new NoMedicinesFoundException()))
                .bodyToMono(OpenFDAResponse.class)
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(e -> e.getMedicines()
                .stream()
                .map(this::medicineToReadable)
                .collect(Collectors.toList())
        ).orElseThrow(RuntimeException::new);
    }

    private Medicine medicineToReadable(Medicine medicine) {
        medicine.setManufacturer(textToReadable(medicine.getManufacturer(), true));
        medicine.setProductName(textToReadable(medicine.getProductName(), true));
        medicine.setGenericName(textToReadable(medicine.getGenericName(), true));
        medicine.setActiveIngredients(medicine.getActiveIngredients()
                .stream()
                .map(this::ingredientToReadable)
                .collect(Collectors.toList()));
        medicine.setPackagingVariants(Arrays.stream(medicine.getPackagingVariants())
                .map(e -> textToReadable(e, false))
                .toArray(String[]::new));
        return medicine;
    }

    private ActiveIngredient ingredientToReadable(ActiveIngredient ingredient) {
        ingredient.setName(textToReadable(ingredient.getName(), true));
        return ingredient;
    }

    private String textToReadable(String text, boolean ucFirst) {
        String[] words = text.trim().replaceAll(" +", " ").split(" ");
        Arrays.setAll(words, i -> {
            String result = words[i].toLowerCase();
            if (ucFirst) {
                result = result.substring(0, 1).toUpperCase() + result.substring(1);
            }
            return result;
        });
        return String.join(" ", words);
    }

}
