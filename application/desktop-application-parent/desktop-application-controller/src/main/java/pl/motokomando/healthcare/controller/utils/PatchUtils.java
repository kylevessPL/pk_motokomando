package pl.motokomando.healthcare.controller.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static pl.motokomando.healthcare.controller.utils.JsonPatchOperation.ADD;

public final class PatchUtils {

    public static <T> Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> createAddOperationsMap(Map<String, String> objectsDiff) {
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap = new HashMap<>();
        objectsDiff.forEach((key, value) -> operationsMap.put(
                ADD,
                new AbstractMap.SimpleEntry<>("/" + key, Optional.ofNullable(value))));
        return operationsMap;
    }

}
