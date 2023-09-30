import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/coin_api.dart';
import 'package:flutter_frontend/core/constants.dart';
import 'package:http/http.dart';

import '../core/user_state.dart';
import '../model/coin.dart';

class CoinCard extends StatefulWidget {
  final Coin coin;
  final UserState userState;

  const CoinCard({super.key, required this.coin, required this.userState});

  @override
  State<CoinCard> createState() => _CoinCardState();
}

class _CoinCardState extends State<CoinCard> {
  bool showBack = true;
  CoinApi coinApi = CoinApi();

  @override
  Widget build(BuildContext context) {
    return InkWell(
        onTap: () {
          setState(() {
            showBack = !showBack;
          });
        },
        onLongPress: _showImagePopup,
        child: getCard());
  }

  Widget getCard() {
    return AspectRatio(
        aspectRatio: 1 / 1,
        child: Card(
            shape: const CircleBorder(),
            color: widget.userState.user == null
                ? Colors.blue
                : widget.coin.available
                    ? Colors.green
                    : Colors.red,
            child: showBack
                ? widget.coin.imagePath.isEmpty
                    ? const FittedBox(child: Icon(Icons.do_disturb))
                    : Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: ClipOval(
                          child: CachedNetworkImage(
                              fit: BoxFit.fitWidth,
                              imageUrl: widget.coin.imagePath,
                              width: 540,
                              height: 540),
                        ),
                      )
                : Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: CachedNetworkImage(
                        imageUrl: generateUri(
                                "$frontImage${widget.coin.coinSize}", {})
                            .toString(),
                        fit: BoxFit.fitWidth,
                        errorWidget: (context, url, error) =>
                            const FittedBox(child: Icon(Icons.wifi_off)),
                        width: 540,
                        height: 540),
                  )));
  }

  void _showImagePopup() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          content: Wrap(children: [
            Column(children: <Widget>[
              getCard(),
              Padding(
                  padding: const EdgeInsets.fromLTRB(0.0, 15.0, 0.0, 0.0),
                  child: widget.userState.user == null
                      ? ElevatedButton(
                          onPressed: () => {print("TODO Open Email")},
                          child: const Text("Benachrichtigen"))
                      : ElevatedButton(
                          style: ElevatedButton.styleFrom(
                              backgroundColor: widget.coin.available
                                  ? Colors.red
                                  : Colors.green),
                          onPressed: () async =>
                              {await _updateCoin(widget.userState.user!.email)},
                          child: widget.coin.available
                              ? const Text("Entfernen")
                              : const Text("Hinzufügen")))
            ])
          ]),
          title: widget.coin.special
              ? Text(widget.coin.name, softWrap: true)
              : null,
        );
      },
    );
  }

  _updateCoin(String userId) async {
    Response response = await coinApi.updateCoin(
        widget.coin.id, userId, !widget.coin.available);

    if (response.statusCode == 200) {
      widget.coin.available = response.body as bool;
    } else {
      // TODO alert if update was or was not successful
    }
  }
}
