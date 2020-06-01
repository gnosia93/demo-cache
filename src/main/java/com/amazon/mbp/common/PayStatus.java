package com.amazon.mbp.common;

import lombok.Getter;
import lombok.val;

//    pay_status              enum('Queued', 'Processing', 'error', 'Completed'),
@Getter
public enum PayStatus {

    Queued("Queued", 1),
    Processing("Processing", 2),
    Error("Error", 3),
    Completed("Completed", 4);

    String value;
    int number;

    PayStatus(String value, int number) {
        this.value = value;
        this.number = number;
    }

}
