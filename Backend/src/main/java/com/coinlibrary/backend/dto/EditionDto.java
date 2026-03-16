package com.coinlibrary.backend.dto;

import com.coinlibrary.backend.model.Coin;

public class EditionDto {
    
    public int id;
    public String country;
    public int edition;
    public int yearFrom;
    public int yearTo;
    public Iterable<Coin> coins;
    public String editionString;
}
