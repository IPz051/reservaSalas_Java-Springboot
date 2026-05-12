package br.com.alura.reservasdesalas.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
