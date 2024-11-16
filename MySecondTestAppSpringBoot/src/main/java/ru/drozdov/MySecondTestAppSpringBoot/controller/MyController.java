package ru.drozdov.MySecondTestAppSpringBoot.controller;

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
import ru.drozdov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.drozdov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.drozdov.MySecondTestAppSpringBoot.model.*;
import ru.drozdov.MySecondTestAppSpringBoot.service.*;
import ru.drozdov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifyOperationUidResponseService") ModifyOperationUidResponseService modifyOperationUidResponseService,
                        @Qualifier("ModifyDataRequestService") ModifyDataRequestService modifyDataRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyOperationUidResponseService;
        this.modifyRequestService = modifyDataRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback (@Valid @RequestBody Request request,
                                              BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .annualBonus(new AnnualBonusServiceImpl().calculate(
                        request.getPosition(),
                        request.getSalary(),
                        request.getBonus(),
                        request.getWorkDays()))
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
        modifyRequestService.modify(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
