import 'dart:io';

import 'package:flutter_frontend/core/constants.dart' as constants;
import 'package:http/http.dart' as http;

Future<String> get(String api, Map<String, String> queryParameters) async {
  final uri = constants.generateUri(api, queryParameters);
  final response = await http.get(
    uri,
    headers: {
      HttpHeaders.contentTypeHeader: 'application/json',
    },
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to load coins');
  }

  return response.body;
}

Future<http.Response> postWithAccess(
  String api,
  Map<String, String> queryParameters,
  Map<String, String> authHeaders,
) async {
  final uri = constants.generateUri(api, queryParameters);
  authHeaders.addAll({HttpHeaders.contentTypeHeader: 'application/json'});
  final response = await http.post(
    uri,
    headers: authHeaders,
  );
  if (response.statusCode != 200) {
    throw Exception('Failed to send update');
  }

  return response;
}
