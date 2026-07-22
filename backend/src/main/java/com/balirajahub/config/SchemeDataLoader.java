package com.balirajahub.config;

import com.balirajahub.entity.Scheme;
import com.balirajahub.entity.enums.SchemeCategory;
import com.balirajahub.repository.SchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchemeDataLoader implements CommandLineRunner {

    private final SchemeRepository schemeRepository;

    @Override
    public void run(String... args) {

        if (schemeRepository.count() > 0) {
            return;
        }

        Scheme scheme1 = new Scheme();
        scheme1.setTitle("PM Kisan Samman Nidhi");
        scheme1.setDescription("Income support of ₹6000 per year.");
        scheme1.setCategory(SchemeCategory.SUBSIDY);
        scheme1.setEligibility("Small and marginal farmers");
        scheme1.setBenefits("₹6000 annually");
        scheme1.setOfficialLink("https://pmkisan.gov.in");

        Scheme scheme2 = new Scheme();
        scheme2.setTitle("Pradhan Mantri Fasal Bima Yojana");
        scheme2.setDescription("Crop insurance scheme.");
        scheme2.setCategory(SchemeCategory.INSURANCE);
        scheme2.setEligibility("All farmers");
        scheme2.setBenefits("Crop loss insurance");
        scheme2.setOfficialLink("https://pmfby.gov.in");

        Scheme scheme3 = new Scheme();
        scheme3.setTitle("Kisan Credit Card");
        scheme3.setDescription("Easy agricultural loans.");
        scheme3.setCategory(SchemeCategory.LOAN);
        scheme3.setEligibility("Eligible farmers");
        scheme3.setBenefits("Low-interest agricultural loan");
        scheme3.setOfficialLink("https://www.myscheme.gov.in");

        schemeRepository.save(scheme1);
        schemeRepository.save(scheme2);
        schemeRepository.save(scheme3);
    }
}