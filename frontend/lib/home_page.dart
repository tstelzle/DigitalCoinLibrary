import 'package:flutter/material.dart';
import 'package:frontend/coin.dart';
import 'package:provider/provider.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    CoinList coinList = context.watch<CoinList>();
    coinList.updateMap();

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Consumer<CoinList>(builder: (context, coinMap, child) {
        return ListView.builder(
            shrinkWrap: true,
            itemCount: coinList.coinMap.length,
            itemBuilder: (BuildContext context, int index) {
              return Column(children: <Widget>[
                ListTile(title: Text(coinList.coinMap.keys.elementAt(index))),
                ListView.builder(
                    shrinkWrap: true,
                    itemCount: coinList.coinMap.values.elementAt(index).length,
                    itemBuilder: (BuildContext context, int index2) {
                      return createCoinWidget(coinList.coinMap.values
                          .elementAt(index)
                          .elementAt(index2));
                    })
              ]);
            });
      }), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

Widget createCoinWidget(Coin coin) {
  return Card(
      child: ListTile(
          title: Text("${coin.year.toString()} ${coin.coinSize}"),
          subtitle: coin.special ? Text(coin.name) : null));
}
