class Edition {
  final String country;
  final int edition;
  final int yearFrom;
  final int yearTo;

  Edition({
    required this.country,
    required this.edition,
    required this.yearFrom,
    required this.yearTo
});

  factory Edition.fromJson(Map<String, dynamic> json) {
    return Edition(
        country: json["country"],
        edition: json['edition'],
        yearFrom: json['year_from'],
        yearTo: json['year_to'],
    );
  }
}