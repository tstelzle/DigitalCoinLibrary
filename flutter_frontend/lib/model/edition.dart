import 'package:flutter_frontend/model/coin.dart';

class Edition {
  Edition({
    required this.id,
    required this.country,
    required this.edition,
    required this.yearFrom,
    required this.yearTo,
    required this.editionString,
    required this.coins,
  });

  factory Edition.fromJson(Map<String, dynamic> json, String librarianId) {
    return Edition(
      id: json['id'] as int,
      country: json['country'] as String,
      edition: json['edition'] as int,
      yearFrom: json['yearFrom'] as int,
      yearTo: json['yearTo'] as int,
      editionString: (json['editionString'] ?? '') as String,
      coins: (json['coins'] as List<dynamic>)
          .map(
            (coinJson) => Coin.fromJson(
              coinJson as Map<String, dynamic>,
              librarianId,
            ),
          )
          .toList(),
    );
  }
  final int id;
  final String country;
  final int edition;
  final int yearFrom;
  final int yearTo;
  final String editionString;
  final List<Coin> coins;
}
