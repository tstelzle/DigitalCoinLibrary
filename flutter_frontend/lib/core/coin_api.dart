import 'dart:convert';

import 'package:flutter_frontend/core/api.dart' as coin_api;
import 'package:http/http.dart';

import '../core/constants.dart' as constants;
import '../model/coin.dart';

class CoinApi {
  Future<List<Coin>> fetchCoinsByEdition(int editionId, int size,
      String librarianIdentification) async {
    var queryParameters = {"editionId": "$editionId"};
    if (size > 0) {
      queryParameters["size"] = "$size";
    }
    queryParameters["librarianIdentification"] = librarianIdentification;

    String body = await coin_api.get(constants.coinPath, queryParameters);

    List<Coin> coinList = [];
    final List<dynamic> coinMap = jsonDecode(body);

    for (var value in coinMap) {
      coinList.add(Coin.fromJson(value));
    }

    return coinList;
  }

  Future<Response> updateCoin(int coinId, String librarianIdentification,
      bool available, String? accessToken) async {
    var queryParameters = <String, String>{};
    queryParameters["coinId"] = "$coinId";
    queryParameters["librarianIdentification"] = librarianIdentification;
    queryParameters["available"] = "$available";

    Response response = await coin_api.postWithAccess(constants.coinPath, queryParameters, accessToken!);

    return response;
  }
}
