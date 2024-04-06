import 'dart:convert';

import 'package:flutter_frontend/core/api.dart' as coin_api;
import 'package:flutter_frontend/core/constants.dart' as constants;
import 'package:flutter_frontend/model/coin.dart';
import 'package:http/http.dart';

class CoinApi {
  Future<List<Coin>> fetchCoinsByEdition(
    int editionId,
    int size,
    String librarianIdentification,
  ) async {
    final queryParameters = {'editionId': '$editionId'};
    if (size > 0) {
      queryParameters['size'] = '$size';
    }
    queryParameters['librarianIdentification'] = librarianIdentification;

    final body = await coin_api.get(constants.coinPath, queryParameters);

    final coinList = <Coin>[];
    final coinMap = jsonDecode(body) as List<dynamic>;

    for (final value in coinMap) {
      coinList.add(Coin.fromJson(value as Map<String, dynamic>));
    }

    return coinList;
  }

  Future<Response> updateCoin(
    int coinId,
    String librarianIdentification,
    bool available,
    String? accessToken,
  ) async {
    final queryParameters = <String, String>{};
    queryParameters['coinId'] = '$coinId';
    queryParameters['librarianIdentification'] = librarianIdentification;
    queryParameters['available'] = '$available';

    final response = await coin_api.postWithAccess(
      constants.coinPath,
      queryParameters,
      accessToken!,
    );

    return response;
  }
}
