import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/coin_api.dart';

import '../core/country_names.dart';
import '../model/coin.dart';
import '../model/edition.dart';
import 'coin_card.dart';

class EditionView extends StatefulWidget {
  final Edition edition;

  const EditionView({super.key, required this.edition});

  @override
  State<EditionView> createState() => _EditionViewState();

  String getTitle() {
    final CountryNames countryNames = CountryNames();

    bool specialEdition = edition.edition == 0;
    String special = specialEdition ? "Sondermünzen" : "${edition.edition}.";
    String country = countryNames.getCountryName(edition.country);
    String year = "";
    if (!specialEdition) {
      if (edition.yearTo.compareTo(2100) == 0 ||
          edition.yearTo.compareTo(0) == 0) {
        if (edition.yearFrom.compareTo(1800) == 0 ||
            edition.yearFrom.compareTo(0) == 0) {
          year = "ab 1999";
        } else {
          year = "ab ${edition.yearFrom}";
        }
      } else {
        year = "${edition.yearFrom} bis ${edition.yearTo}";
      }
    }

    return "$special $country $year";
  }
}

class _EditionViewState extends State<EditionView> {
  final CoinApi coinApi = CoinApi();

  @override
  Widget build(BuildContext context) {
    return Column(children: <Widget>[
      ListTile(title: Text(widget.getTitle())),
      LayoutBuilder(
        builder: (BuildContext context, BoxConstraints constraints) {
          final screenWidth = constraints.maxWidth;
          final itemWidth =
              100.0; // Adjust this value based on your item's desired width
          final crossAxisCount = (screenWidth / itemWidth).floor();

          return FutureBuilder<List<Coin>>(
            future: coinApi.fetchCoinsByEdition(widget.edition.id),
            builder:
                (BuildContext context, AsyncSnapshot<List<Coin>> snapshot) {
              if (snapshot.hasData) {
                return GridView.builder(
                  physics: const NeverScrollableScrollPhysics(),
                  shrinkWrap: true,
                  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: crossAxisCount,
                    crossAxisSpacing: 5.0,
                    mainAxisSpacing: 5.0,
                  ),
                  itemCount: snapshot.data?.length,
                  itemBuilder: (BuildContext context, int index) {
                    return CoinCard(coin: snapshot.data![index]);
                  },
                );
              } else {
                return const Text("Gathering Coins.");
              }
            },
          );
        },
      ),
    ]);
  }
}
