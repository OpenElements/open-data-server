package com.openelements.opendata.organization;

import com.openelements.opendata.base.DTO;

public record OrganizationDTO(String uuid,
                              String name,
                              String legalName,
                              String streetAddress,
                              String postalCode,
                              String city,
                              String country,
                              String email,
                              String telephone,
                              String founder,
                              String registerNumber,
                              String registerCourt,
                              String vatNumber,
                              String url) implements DTO {
}
