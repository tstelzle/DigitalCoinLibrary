import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;
import '../model/edition.dart';

class EditionApi {
  Future<List<Edition>> fetchEditions(int pageKey) async {
    List<Edition> editionList = [];
    final Uri uri = constants.generateUri("${constants.editionPath}/$pageKey", {});

    final response = await http.get(uri, headers: {
      HttpHeaders.contentTypeHeader: 'application/json',
    });
    if (response.statusCode != 200) {
      throw Exception('Failed to load editions');
    }

    final Map<String, dynamic> editionMap = jsonDecode(response.body);

    for (var value in editionMap["content"]) {
      editionList.add(Edition.fromJson(value));
    }


    return editionList;
  }
}
