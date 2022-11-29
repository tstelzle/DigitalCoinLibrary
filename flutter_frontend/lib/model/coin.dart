import 'edition.dart';

class Coin {
  final int id;
  final Edition edition;
  final int year;
  final bool special;
  final String name;
  final int coinSize;
  final bool available;
  final String editionString;
  final String imagePath;

  const Coin(
      {required this.id,
      required this.edition,
      required this.year,
      required this.special,
      required this.name,
      required this.coinSize,
      required this.available,
      required this.editionString,
      required this.imagePath});

  factory Coin.fromJson(Map<String, dynamic> json) {
    return Coin(
        id: json['id'],
        edition: Edition.fromJson(json["edition"]),
        year: json['year'],
        special: json['special'],
        name: json['name'] ?? "",
        coinSize: json['size'],
        available: json['available'],
        editionString: json['editionString'] ?? "",
        imagePath: json["imagePath"] ?? "");
  }

  @override
  String toString() {
    return "Coin: ${edition.country}, $year, $special, $name, $coinSize";
  }
}
