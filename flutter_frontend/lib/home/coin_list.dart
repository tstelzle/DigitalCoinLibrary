import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;
import '../model/coin.dart';

class CoinList extends ChangeNotifier {
  Map<String, List<Coin>> coinMap = {};

  CoinList() {
    updateMap();
  }

  Future<void> updateMap() async {
    final response = await http.get(Uri.parse(constants.coinUrl));
    if (response.statusCode != 200) {
      return;
    }

    Map countryMap = Map.from(jsonDecode(utf8.decode(response.bodyBytes)));

    countryMap.forEach((key, value) {
      coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
    });

    notifyListeners();
  }
}
