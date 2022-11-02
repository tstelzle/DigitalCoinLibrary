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
                GridView.builder(
                    shrinkWrap: true,
                    gridDelegate:
                        const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 8,
                      crossAxisSpacing: 5.0,
                      mainAxisSpacing: 5.0,
                    ),
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
      color: coin.available ? Colors.green : Colors.red,
      child: ListTile(
          title: Text(coin.coinSize >= 100
              ? "${coin.coinSize / 100}€"
              : "${coin.coinSize} ct."),
          subtitle: coin.special ? Text(coin.name) : null));
}
