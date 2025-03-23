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
    _viewId = 'image_${widget.imageUrl.hashCode}';

    ui.platformViewRegistry.registerViewFactory(_viewId, (int viewId) {
      final container = web.HTMLDivElement()
        ..style.width = '100%'
        ..style.height = '100%'
        ..style.position = 'relative'
        ..style.overflow = 'hidden';

      final img = web.HTMLImageElement()
        ..src = widget.imageUrl
        ..style.width = '100%'
        ..style.height = '100%'
        ..style.objectFit = 'cover'
        ..style.pointerEvents = 'none';

      container.append(img);
      return container;
    });
  }

  @override
  Widget build(BuildContext context) {
    return IgnorePointer(child: HtmlElementView(viewType: _viewId));
  }
}
