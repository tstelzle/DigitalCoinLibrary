import 'dart:ui_web' as ui;
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:web/web.dart' as web;

class HtmlImageWidget extends StatefulWidget {
  final String imageUrl;

  const HtmlImageWidget({super.key, required this.imageUrl});

  @override
  State<HtmlImageWidget> createState() => _HtmlImageWidgetState();
}

class _HtmlImageWidgetState extends State<HtmlImageWidget> {
  late String _viewId;

  @override
  void initState() {
    super.initState();
    _viewId = "image_${widget.imageUrl.hashCode}";
    
    // Register a view factory
    ui.platformViewRegistry.registerViewFactory(_viewId, (int viewId) {
      final imgElement = web.document.createElement('img') as web.HTMLImageElement;
      imgElement.src = widget.imageUrl;
      imgElement.style.width = '100%';
      imgElement.style.height = '100%';
      return imgElement;
    });
  }

  @override
  Widget build(BuildContext context) {
    return  SizedBox(
      width: 540,
      height: 540,
      child: HtmlElementView(viewType: _viewId),
    );
  }
}