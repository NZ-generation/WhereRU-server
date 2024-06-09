package com.example.nzgeneration.domain.retool.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetPercentCategoryResponse {
        List<Double> values;
        List<String> labels;
}