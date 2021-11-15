package com.homework.model.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookInventory {
    private int id;
    private String bookId;
    private int amount;
    private LocalDateTime updateDate;
}
