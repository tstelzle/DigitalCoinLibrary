import 'dart:convert';

import 'package:flutter_frontend/core/api.dart';
import 'package:flutter_frontend/core/constants.dart' as constants;
import 'package:flutter_frontend/model/edition.dart';

class EditionApi {
  Future<List<Edition>> fetchEditions(String librarianId) async {
    final body = await get(constants.editionPath, {});

    final editionJsonList = jsonDecode(body) as List<dynamic>;

    final editionList = editionJsonList
        .map((json) =>
            Edition.fromJson(json as Map<String, dynamic>, librarianId),)
        .toList();

    return editionList;
  }

  Future<List<String>> fetchCountries() async {
    final body = await get('${constants.editionPath}/countries', {});

    final decodedList = json.decode(body) as List<dynamic>;
    return decodedList.map((dynamic item) => item.toString()).toList();
  }
}
