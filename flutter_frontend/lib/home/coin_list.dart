import 'dart:convert';
import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

import '../model/coin.dart';

class CoinList extends ChangeNotifier {
  Map<String, List<Coin>> coinMap = {};

  CoinList() {
    updateMap();
  }

  Future<void> updateMap() async {
    final response = await http.get(Uri.parse("http://192.168.177.21:9010/api/coin"));
    if (response.statusCode == HttpStatus.ok) {
      Map countryMap = Map.from(jsonDecode(utf8.decode(response.bodyBytes)));

      countryMap.forEach((key, value) {
        coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
      });
    }

    notifyListeners();
  }
}
