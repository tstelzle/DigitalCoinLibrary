import 'dart:convert';
import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

import '../model/coin.dart';
import '../core/constants.dart' as Constants;

class CoinList extends ChangeNotifier {
  Map<String, List<Coin>> coinMap = {};

  CoinList() {
    updateMap();
  }

  Future<void> updateMap() async {
    final response = await http.get(Uri.parse(Constants.coinUrl));
    if (response.statusCode != HttpStatus.ok) {
      return;
    }

    Map countryMap = Map.from(jsonDecode(utf8.decode(response.bodyBytes)));

    countryMap.forEach((key, value) {
      coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
    });

    notifyListeners();
  }
}
