import 'dart:convert';

import '../core/constants.dart' as constants;
import '../model/edition.dart';
import 'api.dart';

class EditionApi {
  Future<List<Edition>> fetchEditions(
      int pageKey, String country, bool special) async {
    var queryParameters = <String, String>{};
    if (country != "all") {
      queryParameters["country"] = country;
    }
    if (special == true) {
      queryParameters["special"] = "true";
    }

    String body =
        await get("${constants.editionPath}/page/$pageKey", queryParameters);

    List<Edition> editionList = [];
    final Map<String, dynamic> editionMap = jsonDecode(body);

    for (var value in editionMap["content"]) {
      editionList.add(Edition.fromJson(value));
    }

    return editionList;
  }

  Future<List<String>> fetchCountries() async {
    List<String> countries = [];

    String body = await get("${constants.editionPath}/countries", {});

    countries = List<String>.from(json.decode(body).cast<String>());

    return countries;
  }
}
