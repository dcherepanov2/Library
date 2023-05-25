package com.example.MyBookShopApp.dto.deserializetor;

import com.example.MyBookShopApp.dto.ReviewDto;
import com.example.MyBookShopApp.dto.ReviewEditListDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewEditListDtoDeserializer  extends JsonDeserializer<ReviewEditListDto> {
    @Override
    public ReviewEditListDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode reviewsNode = node.get("reviews");

        List<ReviewDto> reviewsList = new ArrayList<>();
        if (reviewsNode.isArray()) {
            for (JsonNode reviewNode : reviewsNode) {
                String text = null;
                Integer id = null;
                String time = null;
                if(reviewNode.get("text") != null)
                    text = reviewNode.get("text").asText();
                if(reviewNode.get("id") != null)
                    id = reviewNode.get("id").asInt();
                // дополнительная логика для получения значения времени (time)
                if(reviewNode.get("time") != null)
                    time = reviewNode.get("time").asText();
                ReviewDto reviewDto = new ReviewDto(text, id, time);
                reviewsList.add(reviewDto);
            }
        }

        ReviewEditListDto reviewEditListDto = new ReviewEditListDto();
        reviewEditListDto.setReviews(reviewsList);
        return reviewEditListDto;
    }

}
