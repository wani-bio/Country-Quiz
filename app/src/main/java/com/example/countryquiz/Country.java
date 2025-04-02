package com.example.countryquiz;

/**
 * Represents a country with its name and associated continent.
 */
public class Country {
    private String name;
    private String continent;

    /**
     * Constructs a Country with the specified name and continent.
     *
     * @param name the name of the country
     * @param continent the continent the country belongs to
     */
    public Country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    /**
     * Returns the name of the country.
     *
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the continent the country belongs to.
     *
     * @return the continent name
     */
    public String getContinent() {
        return continent;
    }
}
