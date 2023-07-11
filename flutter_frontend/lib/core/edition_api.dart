import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

import '../core/constants.dart' as constants;
import '../model/edition.dart';

class EditionApi {
  Future<List<Edition>> fetchEditions(int pageKey, String country, bool special) async {
    var queryParameters = <String, String>{};
    if (country != "all") {
      queryParameters["country"] = country;
    }
    if (special == true) {
      queryParameters["special"] = "true";
    }

    List<Edition> editionList = [];
    final Uri uri =
        constants.generateUri("${constants.editionPath}/page/$pageKey", queryParameters);

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

  Future<List<String>> fetchCountries() async {
    List<String> countries = [];
    final Uri uri =
        constants.generateUri("${constants.editionPath}/countries", {});

    final response = await http.get(uri, headers: {
      HttpHeaders.contentTypeHeader: 'application/json',
    });
    if (response.statusCode != 200) {
      throw Exception('Failed to load edition countries');
    }

    countries = List<String>.from(json.decode(response.body).cast<String>());

    return countries;
  }
}
