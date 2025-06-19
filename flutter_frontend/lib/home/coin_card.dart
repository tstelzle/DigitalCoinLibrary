// import 'package:cached_network_image/cached_network_image.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_frontend/core/coin_api.dart';
// import 'package:flutter_frontend/home/html_image.dart';
// import 'package:flutter_frontend/model/coin.dart';
// import 'package:google_sign_in/google_sign_in.dart';

// class CoinCard extends StatefulWidget {
//   const CoinCard(
//       {required this.coin, required this.librarianAvailable, super.key});
//   final Coin coin;
//   final bool librarianAvailable;

//   @override
//   State<CoinCard> createState() => _CoinCardState();
// }

// class _CoinCardState extends State<CoinCard> {
//   bool showBack = true;
//   CoinApi coinApi = CoinApi();
//   final GoogleSignInAccount? user = getIt<UserBloc>().state.user;

//   @override
//   Widget build(BuildContext context) {
//     return InkWell(
//       onTap: () {
//         setState(() {
//           showBack = !showBack;
//         });
//       },
//       onLongPress: _showImagePopup,
//       child: getCard(),
//     );
//   }

//   Widget getCard() {
//     return AspectRatio(
//       aspectRatio: 1 / 1,
//       child: Card(
//         shape: const CircleBorder(),
//         color: widget.coin.availableColor,
//         child: showBack
//             ? widget.coin.imagePath.isEmpty
//                 ? const FittedBox(child: Icon(Icons.do_disturb))
//                 : Padding(
//                     padding: const EdgeInsets.all(8),
//                     child: ClipOval(
//                       child: HtmlImageWidget(imageUrl: widget.coin.imagePath),
//                     ),
//                   )
//             : Padding(
//                 padding: const EdgeInsets.all(8),
//                 child: CachedNetworkImage(
//                   imageUrl: widget.coin.frontImagePath(),
//                   fit: BoxFit.fitWidth,
//                   errorWidget: (context, url, error) =>
//                       const FittedBox(child: Icon(Icons.wifi_off)),
//                   width: 540,
//                   height: 540,
//                 ),
//               ),
//       ),
//     );
//   }

//   void _showImagePopup() {
//     showDialog<AlertDialog>(
//       context: context,
//       builder: (BuildContext context) {
//         return AlertDialog(
//           content: Wrap(
//             children: [
//               Column(
//                 children: <Widget>[
//                   getCard(),
//                   Padding(
//                     padding: const EdgeInsets.fromLTRB(0, 15, 0, 0),
//                     // TODO(tarek): compare if logged in user
//                     // is equal to user on route
//                     child: user == null
//                         ? ElevatedButton(
//                             onPressed: () => {print('TODO Open Email')},
//                             child: const Text('Benachrichtigen'),
//                           )
//                         : ElevatedButton(
//                             style: ElevatedButton.styleFrom(
//                               backgroundColor: widget.coin.availableColor,
//                             ),
//                             onPressed: () async => {
//                               await _updateCoin(
//                                 user!.email,
//                               ),
//                             },
//                             child: widget.coin.available
//                                 ? const Text('Entfernen')
//                                 : const Text('Hinzufügen'),
//                           ),
//                   ),
//                 ],
//               ),
//             ],
//           ),
//           title: widget.coin.special
//               ? Text(widget.coin.name, softWrap: true)
//               : null,
//         );
//       },
//     );
//   }

//   Future<void> _updateCoin(String userId) async {
//     final authHeaders = await user?.authHeaders;

//     final response = await coinApi.updateCoin(
//       widget.coin.id,
//       userId,
//       !widget.coin.available,
//       authHeaders!,
//     );

//     if (response.statusCode == 200) {
//       widget.coin.available = response.body as bool;
//     } else {
//       // TODO(tarek): alert if update was or was not successful
//       // TODO update coin card, to show updated color
//     }
//   }
// }
