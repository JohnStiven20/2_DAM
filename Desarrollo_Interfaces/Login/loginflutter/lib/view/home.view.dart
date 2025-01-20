import 'package:flutter/material.dart';
import 'package:loginflutter/utils/global.colors.dart';

class HomeView extends StatelessWidget {
  const HomeView({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.account_circle,
              size: 100,
              color: GlobalColors.textColor,
            ),
            const SizedBox(height: 20), // Espaciado
            Text(
              "Hola usuario",
              textAlign: TextAlign.center,
              style: TextStyle(
                color: GlobalColors.textColor,
                fontWeight: FontWeight.bold,
                fontSize: 45,
              ),
            ),
            const SizedBox(height: 10), // Espaciado
            Text(
              "Bienvenido de nuevo a la aplicación",
              textAlign: TextAlign.center,
              style: TextStyle(
                color: GlobalColors.textColor.withOpacity(0.7),
                fontSize: 18,
              ),
            ),
            const SizedBox(height: 30), // Espaciado
            ElevatedButton(
              onPressed: () {
                print("Botón presionado");
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: GlobalColors.mainColor, // Color del botón
                padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 15),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30), // Bordes redondeados
                ),
              ),
              child: Text(
                "Continuar",
                style: TextStyle(
                  color: GlobalColors.textColor,
                  fontWeight: FontWeight.bold,
                  fontSize: 18,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
