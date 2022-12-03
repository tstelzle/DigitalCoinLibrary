import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:flutter_frontend/core/coin_api.dart';

import '../model/coin.dart';

class CoinList extends ChangeNotifier {
  final CoinApi coinApi;
  Map<String, List<Coin>> coinMap = {};

  CoinList({required this.coinApi}) {
    updateMap();
  }

  Future<void> updateMap() async {
    try {
      final map = await coinApi.fetchCoins();
      coinMap = map;
    } catch (error, _) {
      log(error.toString());
    }
    notifyListeners();
  }
}
