package ru.drozdov.MySecondTestAppSpringBoot.service;

import ru.drozdov.MySecondTestAppSpringBoot.model.Positions;

public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
