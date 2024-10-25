package ru.drozdov.MyThirdTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.drozdov.MyThirdTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.drozdov.MyThirdTestAppSpringBoot.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException;
}
