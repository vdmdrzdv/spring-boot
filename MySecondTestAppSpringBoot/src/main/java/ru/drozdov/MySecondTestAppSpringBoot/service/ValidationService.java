package ru.drozdov.MySecondTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.drozdov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.drozdov.MySecondTestAppSpringBoot.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException;
}
