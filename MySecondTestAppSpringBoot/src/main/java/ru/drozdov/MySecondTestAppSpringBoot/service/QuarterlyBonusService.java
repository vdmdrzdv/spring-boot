package ru.drozdov.MySecondTestAppSpringBoot.service;

import ru.drozdov.MySecondTestAppSpringBoot.model.Positions;

public interface QuarterlyBonusService {
    double calculate(Positions positions, double salary, double bonus, int workDays);
}
