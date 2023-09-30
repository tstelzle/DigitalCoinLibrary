import 'edition.dart';

class Coin {
  final int id;
  final Edition edition;
  final int year;
  final bool special;
  final String name;
  final int coinSize;
  late bool available;
  final String imagePath;

  Coin(
      {required this.id,
      required this.edition,
      required this.year,
      required this.special,
      required this.name,
      required this.coinSize,
      required this.available,
      required this.imagePath});

  factory Coin.fromJson(Map<String, dynamic> json) {
    return Coin(
        id: json['id'],
        edition: Edition.fromJson(json["edition"]),
        year: json['year'] ?? -1,
        special: json['special'] ?? false,
        name: json['name'] ?? "",
        coinSize: json['size'] ?? -1,
        available: json['available'] ?? false,
        imagePath: json["imagePath"] ?? "");
  }

  @override
  String toString() {
    return "Coin: ${edition.country}, $year, $special, $name, $coinSize";
  }
}
