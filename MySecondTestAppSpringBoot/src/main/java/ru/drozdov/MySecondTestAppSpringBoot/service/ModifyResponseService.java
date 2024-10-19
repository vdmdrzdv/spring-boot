package ru.drozdov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.drozdov.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
