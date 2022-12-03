import 'dart:convert';

import '../model/coin.dart';
import 'package:http/http.dart' as http;
import '../core/constants.dart' as constants;

class CoinApi {
  Future<Map<String, List<Coin>>> fetchCoins() async {
    Map<String, List<Coin>> coinMap = {};

    final response = await http.get(Uri.parse(constants.coinUrl));
    if (response.statusCode != 200) {
      throw Exception('Failed to load coins');
    }

    final Map<String, dynamic> countryMap = jsonDecode(response.body);

    countryMap.forEach((key, value) {
      coinMap.putIfAbsent(key, () => List<Coin>.from(value.map((coin) => Coin.fromJson(coin))));
    });

    return coinMap;
  }
}
