import 'dart:convert';
import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

class Coin {
  final int id;
  final String country;
  final int year;
  final bool special;
  final String name;
  final int coinSize;

  const Coin(
      {required this.id,
      required this.country,
      required this.year,
      required this.special,
      required this.name,
      required this.coinSize});

  factory Coin.fromJson(Map<String, dynamic> json) {
    return Coin(
      id: json['id'],
      country: json['country'],
      year: json['year'],
      special: json['special'],
      name: json['name'],
      coinSize: json['size'],
    );
  }

  @override
  String toString() {
    return "Coin: $country, $year, $special, $name, $coinSize";
  }
}

class CoinList extends ChangeNotifier {
  Map<String, List<Coin>> coinMap = {};

  Future<void> updateMap() async {
    final response =
    await http.get(Uri.parse("http://192.168.177.20:9010/api/coin"));
    if (response.statusCode == HttpStatus.ok) {
      Map countryMap = Map.from(json.decode(response.body));

      countryMap.forEach((key, value) {
        coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
      });
    }

    notifyListeners();
  }
}
