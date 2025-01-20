import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:loginflutter/generated/l10n.dart';
import 'package:loginflutter/view/home.view.dart';
import 'package:loginflutter/view/register.view.dart';
import 'package:loginflutter/utils/global.colors.dart';
import 'package:loginflutter/view/widgets/social.login.dart';
import 'package:loginflutter/view/widgets/button.global.dart';
import 'package:loginflutter/view/widgets/text.form.global.dart';

class LoginView extends StatelessWidget {
  LoginView({super.key});

  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  // Función para validar credenciales y navegar
  void _navigateToHomeView(BuildContext context) {
    String email = emailController.text.trim();
    String password = passwordController.text.trim();

    if (email == "profe@gmail.com" && password == "12345") {
      Get.to(() => HomeView());
    } else {
      _mostrarDialogo(context);
    }
  }

  void _mostrarDialogo(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Error a logear'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: const [
              Column(
                children: [
                  Text('Contraseña o email incorrecto', style: TextStyle(
                    color: Colors.red
                  ),),
                  SizedBox(height: 10),
                  Text("email: profe@gmail.com y contraseña: 12345")
                ],
              )
            ],
          ),
        );
      },
    );

    // Cierra el diálogo después de 5 segundos
    Future.delayed(const Duration(seconds: 3), () {
      if (Navigator.canPop(context)) {
        Navigator.of(context).pop();
      }
    });
  }

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
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
                const SizedBox(height: 20),
                Text(
                  S.current.Mensage,
                  style: TextStyle(
                    color: GlobalColors.textColor,
                    fontSize: 16,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                const SizedBox(height: 15),
                TextFormGlobal(
                  controller: emailController,
                  text: S.current.email,
                  obscure: false,
                  textInputType: TextInputType.emailAddress,
                ),
                const SizedBox(height: 10),
                TextFormGlobal(
                  controller: passwordController,
                  text: S.current.password,
                  textInputType: TextInputType.text,
                  obscure: true,
                ),
                const SizedBox(height: 20),
                ButtonGlobal(
                  onTap: () => _navigateToHomeView(context),
                ),
                const SizedBox(height: 25),
                Center(
                  child: ElevatedButton(
                    onPressed: () => _mostrarDialogo(context),
                    child: const Text('Mostrar diálogo'),
                  ),
                ),
                const SizedBox(height: 25),
                SocialLogin(),
              ],
            ),
          ),
        ),
      ),
      bottomNavigationBar: Container(
        height: 50,
        alignment: Alignment.center,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(S.current.Signp),
            InkWell(
              child: TextButton(
                child: Text(
                  S.current.loge,
                  style: TextStyle(color: GlobalColors.mainColor),
                ),
                onPressed: () => Get.to(() => RegisterView()),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
