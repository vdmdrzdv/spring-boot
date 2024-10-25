package ru.drozdov.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.drozdov.MyThirdTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
