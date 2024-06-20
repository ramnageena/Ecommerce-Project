package net.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
