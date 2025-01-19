import 'package:flutter/material.dart';
import 'package:loginflutter/generated/l10n.dart';
import 'package:loginflutter/utils/global.colors.dart';
import 'package:loginflutter/view/widgets/social.login.dart';
import 'package:loginflutter/view/widgets/button.global.dart';
import 'package:loginflutter/view/widgets/text.form.global.dart';

class RegisterView extends StatelessWidget {
  RegisterView({super.key});

  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController passwordConfirmerController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: SafeArea(
          child: Container(
            width: double.infinity,
            padding: const EdgeInsets.all(15.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  alignment: Alignment.center,
                  child: Text(
                    'CigniFi',
                    style: TextStyle(
                        color: GlobalColors.mainColor,
                        fontSize: 35,
                        fontWeight: FontWeight.bold),
                  ),
                ),
                const SizedBox(
                  height: 20,
                ),
                Text(
                  S.current.conect,
                  style: TextStyle(
                      color: GlobalColors.textColor,
                      fontSize: 16,
                      fontWeight: FontWeight.w500),
                ),
                const SizedBox(
                  height: 15,
                ),
                TextFormGlobal(
                  controller: emailController,
                  text: S.current.email,
                  obscure: false,
                  textInputType: TextInputType.emailAddress,
                ),
                const SizedBox(
                  height: 10,
                ),
                TextFormGlobal(
                    controller: passwordController,
                    text: S.current.password,
                    textInputType: TextInputType.text,
                    obscure: true),
                const SizedBox(
                  height: 10,
                ),
                TextFormGlobal(
                  controller: passwordConfirmerController,
                  obscure: true,
                  text: S.current.confirm,
                  textInputType: TextInputType.text,
                ),
                const SizedBox(
                  height: 10,
                ),
                ButtonGlobal(
                  onTap: () {},
                ),
                const SizedBox(
                  height: 25,
                ),
                SocialLogin()
              ],
            ),
          ),
        ),
      ),
    );
  }
}
