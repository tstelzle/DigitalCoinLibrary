import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/home/edition_view.dart';

import '../model/edition.dart';

class MyHomePage extends StatefulWidget {
  final String title;

  const MyHomePage({super.key, required this.title});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final EditionApi editionApi = EditionApi();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: FutureBuilder<List<Edition>>(
            future: editionApi.fetchEditions(),
            builder:
                (BuildContext context, AsyncSnapshot<List<Edition>> snapshot) {
              if (snapshot.hasData) {
                return ListView.builder(
                    physics: NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    itemCount: snapshot.data?.length,
                    itemBuilder: (BuildContext context, int index) {
                      return EditionView(edition: snapshot.data![index]);
                    });
              } else {
                return const Text("Gathering Editions.");
              }
            }));
  }
}
