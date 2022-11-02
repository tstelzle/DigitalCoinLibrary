import 'dart:convert';
import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

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
      imagePath: json["imagePath"] ?? ""
    );
  }

  @override
  String toString() {
    return "Coin: ${edition.country}, $year, $special, $name, $coinSize";
  }
}

class CoinList extends ChangeNotifier {
  Map<String, List<Coin>> coinMap = {};

  Future<void> updateMap() async {
    final response =
    await http.get(Uri.parse("http://192.168.177.20:9010/api/coin"));
    if (response.statusCode == HttpStatus.ok) {
      // Map countryMap = Map.from(jsonDecode(utf8.decode(response.bodyBytes)));
      Map countryMap = Map.from(json.decode(response.body));

      countryMap.forEach((key, value) {
        coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
      });
    }

    notifyListeners();
  }
}
