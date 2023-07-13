import 'dart:io';

import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;

Future<String> get(String api, Map<String, String> queryParameters) async {
  final Uri uri = constants.generateUri(api, queryParameters);
  final response = await http.get(uri, headers: {
    HttpHeaders.contentTypeHeader: 'application/json',
  });
  if (response.statusCode != 200) {
    throw Exception('Failed to load coins');
  }

  return response.body;
}