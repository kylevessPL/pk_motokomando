package pl.motokomando.healthcare.controller.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class LocalDateAdapter implements JsonSerializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

    @Override
    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(date.format(formatter));
    }

}
