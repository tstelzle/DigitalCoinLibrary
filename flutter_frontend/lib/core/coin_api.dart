import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;
import '../model/coin.dart';

class CoinApi {
  Future<List<Coin>> fetchCoinsByEdition(int editionId, int size) async {
    var queryParameters = {"editionId": "$editionId"};
    if (size > 0) {
      queryParameters["size"] = "$size";
    }
    final Uri uri = constants.generateUri(constants.coinPath, queryParameters);
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
