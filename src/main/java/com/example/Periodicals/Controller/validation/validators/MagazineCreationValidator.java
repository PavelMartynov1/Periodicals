package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagCreationRule;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagazineDescriptionRule;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagazineNameRule;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagazinePriceRule;

public class MagazineCreationValidator extends Validator<MagCreationRule, MagazineCreationInput>{
    public MagazineCreationValidator() {
        this.addRule(new MagazineNameRule());
        this.addRule(new MagazinePriceRule());
        this.addRule(new MagazineDescriptionRule());
    }
}
