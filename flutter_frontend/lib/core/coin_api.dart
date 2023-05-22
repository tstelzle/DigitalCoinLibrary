import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;
import '../model/coin.dart';

class CoinApi {
  Future<Map<String, List<Coin>>> fetchCoins() async {
    Map<String, List<Coin>> coinMap = {};
    final Uri uri = constants.generateUri(constants.coinPath, {});

    final response = await http.get(uri, headers: {
      HttpHeaders.contentTypeHeader: 'application/json',
    });
    if (response.statusCode != 200) {
      throw Exception('Failed to load coins');
    }

    final Map<String, dynamic> countryMap = jsonDecode(response.body);

    countryMap.forEach((key, value) {
      coinMap.putIfAbsent(
          key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
    });

    return coinMap;
  }

  Future<List<Coin>> fetchCoinsByEdition(int editionId) async {
    final queryParameters = {"editionId": "$editionId"};
    final Uri uri = constants.generateUri(constants.coinPath, queryParameters);

    // String uriString = "http://localhost:8080/api/coin?editionId=4";

    final response = await http.get(uri, headers: {
      HttpHeaders.contentTypeHeader: 'application/json',
    });
    if (response.statusCode != 200) {
      throw Exception('Failed to load coins');
    }

    List<Coin> coinList = [];

    final List<dynamic> coinMap = jsonDecode(response.body);

    for (var value in coinMap) {
      coinList.add(Coin.fromJson(value));
    }

    return coinList;
  }
}
