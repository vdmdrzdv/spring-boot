package ru.drozdov.MySecondTestAppSpringBoot.service;

import ru.drozdov.MySecondTestAppSpringBoot.model.Positions;

public class QuarterlyBonusServiceImpl implements QuarterlyBonusService {
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        if (positions.isManager()) {
            return salary * bonus * 91 * positions.getPositionCoefficient() / workDays;
        } else {
            return 0;
        }
    }
}
