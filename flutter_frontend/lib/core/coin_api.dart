import 'dart:convert';

import 'package:flutter_frontend/core/api.dart';

import '../core/constants.dart' as constants;
import '../model/coin.dart';

class CoinApi {
  Future<List<Coin>> fetchCoinsByEdition(int editionId, int size,
      String userName) async {
    var queryParameters = {"editionId": "$editionId"};
    if (size > 0) {
      queryParameters["size"] = "$size";
    }
    queryParameters["librarianName"] = userName;

    String body = await get(constants.coinPath, queryParameters);

    List<Coin> coinList = [];
    final List<dynamic> coinMap = jsonDecode(body);

    for (var value in coinMap) {
      coinList.add(Coin.fromJson(value));
    }

    return coinList;
  }

  Future<String> updateCoin(int coinId, String librarianName,
      bool available) async {
    var queryParameters = <String, String>{};
    queryParameters["coinId"] = "$coinId";
    queryParameters["librarianName"] = librarianName;
    queryParameters["available"] = "$available";

    String body = await post(constants.coinPath, queryParameters);

    return body;
  }
}
