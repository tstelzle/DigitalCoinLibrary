import 'dart:convert';

import 'package:flutter_frontend/core/api.dart';
import 'package:flutter_frontend/core/constants.dart' as constants;
import 'package:flutter_frontend/model/edition.dart';

class EditionApi {
  Future<List<Edition>> fetchEditions(
      int pageKey, String country, bool special,) async {
    final queryParameters = <String, String>{};
    if (country != 'all') {
      queryParameters['country'] = country;
    }
    if (special == true) {
      queryParameters['special'] = 'true';
    }

    final body =
        await get('${constants.editionPath}/page/$pageKey', queryParameters);

    final editionList = <Edition>[];
    final editionMap = jsonDecode(body) as Map<String, dynamic>;

    for (final value in editionMap['content'] as List<dynamic>) {
      editionList.add(Edition.fromJson(value as Map<String, dynamic>));
    }

    return editionList;
  }

  Future<List<String>> fetchCountries() async {
    final body = await get('${constants.editionPath}/countries', {});

    final decodedList = json.decode(body) as List<dynamic>;
    return decodedList.map((dynamic item) => item.toString()).toList();
  }
}
