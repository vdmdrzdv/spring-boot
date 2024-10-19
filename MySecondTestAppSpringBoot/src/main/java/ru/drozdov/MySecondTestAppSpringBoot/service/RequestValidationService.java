package ru.drozdov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.drozdov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.drozdov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.drozdov.MySecondTestAppSpringBoot.model.Request;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult.getFieldError().getField().toString() + ": " + bindingResult.getFieldError().getDefaultMessage().toString());
        }
        Request request = (Request) bindingResult.getTarget();
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("Uid не может быть равным 123");
        }
    }
}
