
import 'package:flutter_frontend/core/api.dart' as coin_api;
import 'package:flutter_frontend/core/constants.dart' as constants;
import 'package:http/http.dart';

class CoinApi {
  Future<Response> updateCoin(
    int coinId,
    String librarianIdentification,
    bool available,
      Map<String, String> authHeaders,
  ) async {
    final queryParameters = <String, String>{};
    queryParameters['coinId'] = '$coinId';
    queryParameters['librarianIdentification'] = librarianIdentification;
    queryParameters['available'] = '$available';

    final response = await coin_api.postWithAccess(
      constants.coinPath,
      queryParameters,
      authHeaders,
    );

    return response;
  }
}
