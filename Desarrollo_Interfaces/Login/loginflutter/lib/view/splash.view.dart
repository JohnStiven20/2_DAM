import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:loginflutter/view/login.view.dart';
import 'package:loginflutter/utils/global.colors.dart';

class SplasView extends StatelessWidget {
  const SplasView({super.key});

  @override
  Widget build(BuildContext context) {

    Future.delayed(const Duration(seconds: 2), () {
      Get.to( () =>  LoginView());
    });


    return Scaffold(
      backgroundColor: GlobalColors.mainColor,
      body: const Center(
        child: Text(
          'CigniFi',
          style: TextStyle(
              color: Colors.white, fontSize: 35, fontWeight: FontWeight.bold),
        ),
      ),
    );
  }
}
