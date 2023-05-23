class Edition {
  final int id;
  final String country;
  final int edition;
  final int yearFrom;
  final int yearTo;
  final String editionString;

  Edition({
    required this.id,
    required this.country,
    required this.edition,
    required this.yearFrom,
    required this.yearTo,
    required this.editionString
});

  factory Edition.fromJson(Map<String, dynamic> json) {
    return Edition(
        id: json["id"],
        country: json["country"],
        edition: json['edition'],
        yearFrom: json['yearFrom'],
        yearTo: json['yearTo'],
        editionString: json['editionString'] ?? "",
    );
  }
}