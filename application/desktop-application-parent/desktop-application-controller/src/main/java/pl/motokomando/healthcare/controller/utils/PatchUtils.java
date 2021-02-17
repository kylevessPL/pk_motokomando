package pl.motokomando.healthcare.controller.utils;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static pl.motokomando.healthcare.controller.utils.JsonPatchOperation.ADD;

public final class PatchUtils {

    public static <T> Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> createAddOperationsMap(T object1, T object2) throws IllegalAccessException {
        Map<String, String> diff = getObjectsDiff(object1, object2);
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap = new HashMap<>();
        diff.forEach((key, value) -> operationsMap.put(
                ADD,
                new AbstractMap.SimpleEntry<>("/" + key, Optional.ofNullable(value))));
        return operationsMap;
    }

    private static <T> Map<String, String> getObjectsDiff(T object1, T object2) throws IllegalAccessException {
        Field[] fields = object1.getClass().getFields();
        Map<String, String> diffMap = new HashMap<>();
        for (Field field : fields) {
            if (!Objects.equals(field.get(object1), field.get(object2))) {
                diffMap.put(field.getName(), field.get(object2).toString());
            }
        }
        return diffMap;
    }

}
