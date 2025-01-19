import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:loginflutter/generated/l10n.dart';
import 'package:loginflutter/view/Splash.view.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

void main() {
   runApp(App());
}

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      home: SplasView(),
      localizationsDelegates: [
              S.delegate,
              GlobalMaterialLocalizations.delegate,
              GlobalWidgetsLocalizations.delegate,
              GlobalCupertinoLocalizations.delegate,
            ],
            supportedLocales: S.delegate.supportedLocales,
    );
  }
}
