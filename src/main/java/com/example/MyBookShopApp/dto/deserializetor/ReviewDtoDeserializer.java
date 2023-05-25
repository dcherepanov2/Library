package com.example.MyBookShopApp.dto.deserializetor;

import com.example.MyBookShopApp.dto.ReviewDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ReviewDtoDeserializer extends JsonDeserializer<ReviewDto> {

    @Override
    public ReviewDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException{
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String text = node.get("text").asText();
        Integer id = node.get("id").asInt();
        // дополнительная логика для получения значения времени (time)
        String time = node.get("time").asText();
        return new ReviewDto(text, id, time);
    }
}
