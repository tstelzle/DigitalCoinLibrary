class CountryNames {
  static const countries = {
    "es": "Spanien",
    "gr": "Griechenland",
    "lv": "Lettland",
    "ad": "Andorra",
    "be": "Belgien",
    "cy": "Zypern",
    "pt": "Portugal",
    "va": "Vatikan",
    "mt": "Malta",
    "at": "Österreich",
    "nl": "Niederlande",
    "mo": "Monaco",
    "ie": "Irland",
    "lu": "Luxemburg",
    "fi": "Finnland",
    "it": "Italien",
    "sm": "San-Marino",
    "sk": "Slowakei",
    "sl": "Slowenien",
    "fr": "Frankreich",
    "lt": "Litauen",
    "de": "Deutschland",
    "et": "Estland",
    "hr": "Kroatien"
  };

  String getCountryName(String countryAbbreviation) {
    String? countryName = countries[countryAbbreviation];

    return countryName ?? countryAbbreviation;
  }
}
