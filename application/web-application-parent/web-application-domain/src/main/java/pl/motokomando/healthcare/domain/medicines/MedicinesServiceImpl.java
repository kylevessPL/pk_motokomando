package pl.motokomando.healthcare.domain.medicines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.motokomando.healthcare.domain.model.medicines.Medicine;
import pl.motokomando.healthcare.domain.model.medicines.utils.ActiveIngredient;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;
import pl.motokomando.healthcare.domain.model.medicines.utils.OpenFDAResponse;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.MEDICINE_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.NO_MEDICINES_FOUND;

@Service
public class MedicinesServiceImpl implements MedicinesService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final WebClient openFDAClient;

    @Autowired
    public MedicinesServiceImpl(WebClient openFDAClient) {
        this.openFDAClient = openFDAClient;
    }

    @Override
    public Medicine getMedicine(String productNDC) {
        Optional<OpenFDAResponse> response = openFDAClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", "(product_type:HUMAN+DRUG)+AND+product_ndc:\"" + productNDC + "\"")
                        .build())
                .retrieve()
                .bodyToMono(OpenFDAResponse.class)
                .onErrorMap(WebClientResponseException.NotFound.class, e -> new NoMedicinesFoundException(MEDICINE_NOT_FOUND))
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(e ->
                medicineToReadable(e.getMedicines().get(0))
        ).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Medicine> searchMedicine(MedicineCommand command) {
        String query = command.getQuery().trim().replaceAll(" ", "+");
        Optional<OpenFDAResponse> response = openFDAClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("search", "(product_type:HUMAN+DRUG)+AND+" + query)
                        .queryParamIfPresent("limit", Optional.ofNullable(command.getLimit()))
                        .build())
                .retrieve()
                .bodyToMono(OpenFDAResponse.class)
                .onErrorMap(WebClientResponseException.NotFound.class, e -> new NoMedicinesFoundException(NO_MEDICINES_FOUND))
                .blockOptional(REQUEST_TIMEOUT);
        return response.map(e -> e.getMedicines()
                .stream()
                .map(this::medicineToReadable)
                .collect(Collectors.toList())
        ).orElseThrow(RuntimeException::new);
    }

    private Medicine medicineToReadable(Medicine medicine) {
        Optional.ofNullable(medicine.getManufacturer())
                .ifPresent(e -> medicine.setManufacturer(textToReadable(e, true)));
        Optional.ofNullable(medicine.getProductName())
                .ifPresent(e -> medicine.setProductName(textToReadable(e, true)));
        Optional.ofNullable(medicine.getGenericName())
                .ifPresent(e -> medicine.setGenericName(textToReadable(e, true)));
        Optional.ofNullable(medicine.getActiveIngredients())
                .ifPresent(e -> medicine.setActiveIngredients(e
                        .stream()
                        .map(this::ingredientToReadable)
                        .collect(Collectors.toList())));
        Optional.ofNullable(medicine.getPackagingVariants())
                .ifPresent(e -> medicine.setPackagingVariants(Arrays.stream(e)
                        .map(v -> textToReadable(v, false))
                        .toArray(String[]::new)));
        return medicine;
    }

    private ActiveIngredient ingredientToReadable(ActiveIngredient ingredient) {
        ingredient.setName(textToReadable(ingredient.getName(), true));
        return ingredient;
    }

    private String textToReadable(String text, boolean ucFirst) {
        String[] words = text.trim().replaceAll(" +", " ").split(" ");
        Arrays.setAll(words, i -> {
            if (words[i].length() > 2) {
                String result = words[i].toLowerCase();
                if (ucFirst) {
                    result = result.substring(0, 1).toUpperCase() + result.substring(1);
                }
                return result;
            }
            return words[i];
        });
        return String.join(" ", words);
    }

}
