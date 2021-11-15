package com.homework.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookRequest {
    private String bookID;
    private int amount;
}
