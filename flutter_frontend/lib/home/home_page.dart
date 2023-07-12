import 'package:flutter/material.dart';
import 'package:flutter_frontend/home/home.dart';
import 'package:flutter_frontend/sign_in/google_sign_in.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: const Text("Digital Coin Library")),
        body: Center(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
              const Padding(
                  padding: EdgeInsets.all(20.0), child: GoogleSignInWidget()),
              Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: ElevatedButton(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    const LibraryPage(title: "Alle Münzen")));
                      },
                      child: const Text("Alle Münzen")))
            ])));
  }
}
