class Edition {

  Edition({
    required this.id,
    required this.country,
    required this.edition,
    required this.yearFrom,
    required this.yearTo,
    required this.editionString,
});

  factory Edition.fromJson(Map<String, dynamic> json) {
    return Edition(
        id: json['id'] as int,
        country: json['country'] as String,
        edition: json['edition'] as int,
        yearFrom: json['yearFrom'] as int,
        yearTo: json['yearTo'] as int,
        editionString: (json['editionString'] ?? '') as String,
    );
  }
  final int id;
  final String country;
  final int edition;
  final int yearFrom;
  final int yearTo;
  final String editionString;
}