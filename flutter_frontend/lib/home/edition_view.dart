import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/coin_api.dart';
import 'package:flutter_frontend/core/filter_state.dart';
import 'package:flutter_frontend/core/user_state.dart';
import 'package:flutter_frontend/home/coin_card.dart';
import 'package:flutter_frontend/model/coin.dart';
import 'package:flutter_frontend/model/edition.dart';

class EditionView extends StatefulWidget {

  const EditionView(
      {required this.edition, required this.userState, required this.filterState, required this.librarianID, super.key,});
  final Edition edition;
  final UserState userState;
  final FilterState filterState;
  final String? librarianID;

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
    return LayoutBuilder(
        builder: (BuildContext context, BoxConstraints constraints) {
      final screenWidth = constraints.maxWidth;
      const itemWidth =
          100.0; // Adjust this value based on your item's desired width
      final crossAxisCount = (screenWidth / itemWidth).floor();
      return FutureBuilder<List<Coin>>(
          future: coinApi.fetchCoinsByEdition(widget.edition.id,
              widget.filterState.coinSize, widget.librarianID ?? '',),
          builder: (BuildContext context, AsyncSnapshot<List<Coin>> snapshot) {
            if (snapshot.hasData) {
              if (snapshot.data!.isNotEmpty) {
                return Column(children: <Widget>[
                  ListTile(title: Text(widget.edition.editionString)),
                  GridView.builder(
                    physics: const ScrollPhysics(),
                    shrinkWrap: true,
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: crossAxisCount,
                      crossAxisSpacing: 5,
                      mainAxisSpacing: 5,
                    ),
                    itemCount: snapshot.data?.length,
                    itemBuilder: (BuildContext context, int index) {
                      return CoinCard(coin: snapshot.data![index], userState: widget.userState, librarianAvailable: widget.librarianID != null);
                    },
                  ),
                ],);
              } else {
                return const SizedBox();
              }
            } else {
              return const Column(children: <Widget>[
                CircularProgressIndicator(color: Colors.black),
              ],);
            }
          },);
    },);
  }
}
