import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/coin_api.dart';

import '../core/filter_state.dart';
import '../core/user_state.dart';
import '../model/coin.dart';
import '../model/edition.dart';
import 'coin_card.dart';

class EditionView extends StatefulWidget {
  final Edition edition;

  const EditionView({super.key, required this.edition});

  @override
  State<EditionView> createState() => _EditionViewState();
}

class _EditionViewState extends State<EditionView>
    with AutomaticKeepAliveClientMixin {
  @override
  bool get wantKeepAlive => true;
  final CoinApi coinApi = CoinApi();

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<FilterBloc, FilterState>(
        builder: (context, filterState) {
      return BlocBuilder<UserBloc, UserState>(builder: (context, userState) {
        return LayoutBuilder(
            builder: (BuildContext context, BoxConstraints constraints) {
          final screenWidth = constraints.maxWidth;
          const itemWidth =
              100.0; // Adjust this value based on your item's desired width
          final crossAxisCount = (screenWidth / itemWidth).floor();
          return FutureBuilder<List<Coin>>(
              future: coinApi.fetchCoinsByEdition(
                  widget.edition.id, filterState.coinSize, userState.user),
              builder:
                  (BuildContext context, AsyncSnapshot<List<Coin>> snapshot) {
                if (snapshot.hasData) {
                  if (snapshot.data?.length != 0) {
                    return Column(children: <Widget>[
                      ListTile(title: Text(widget.edition.editionString)),
                      GridView.builder(
                        physics: const ScrollPhysics(),
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
                      )
                    ]);
                  } else {
                    return const SizedBox();
                  }
                } else {
                  return Column(children: const <Widget>[
                    CircularProgressIndicator(color: Colors.black)
                  ]);
                }
              });
        });
      });
    });
  }
}
