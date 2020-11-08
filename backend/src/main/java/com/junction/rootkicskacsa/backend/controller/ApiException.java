package com.junction.rootkicskacsa.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Ooops")
public class ApiException extends RuntimeException {}