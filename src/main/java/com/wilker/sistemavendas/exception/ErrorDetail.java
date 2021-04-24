package com.wilker.sistemavendas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetail {
  private String error;
  private String message;
  private Integer statusCode;
}
