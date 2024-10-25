package ru.drozdov.MyThirdTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.drozdov.MyThirdTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.drozdov.MyThirdTestAppSpringBoot.exception.ValidationFailedException;
import ru.drozdov.MyThirdTestAppSpringBoot.model.*;
import ru.drozdov.MyThirdTestAppSpringBoot.service.ModifyOperationUidResponseService;
import ru.drozdov.MyThirdTestAppSpringBoot.service.ModifyResponseService;
import ru.drozdov.MyThirdTestAppSpringBoot.service.ValidationService;
import ru.drozdov.MyThirdTestAppSpringBoot.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifyOperationUidResponseService") ModifyOperationUidResponseService modifyOperationUidResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyOperationUidResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback (@Valid @RequestBody Request request,
                                              BindingResult bindingResult) {

        log.info("request: {}", request);
        String systemTimeNow = DateTimeUtil.getCustomFormat().format(new Date());
        try {
            log.info("Время от отправки запроса из Postman до вывода в лог сервиса 2: {} мс", DateTimeUtil.getPassedTime(request.getSystemTime(), systemTimeNow));
        } catch (ParseException e) {
            log.error("Parse exception {} при запросе {}", e.getMessage(), request);
        }
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Первоначальный response: {}", response);

        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("Ошибка валидации ({}) при request {}", e.getMessage(), request);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.error("Ошибка неподдерживаемого UID ({}) при request {}", e.getMessage(), request);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("Неизвестная ошибка ({}) при request {}", e.getMessage(), request);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response = modifyResponseService.modify(response);
        log.info("Модифицированный response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
