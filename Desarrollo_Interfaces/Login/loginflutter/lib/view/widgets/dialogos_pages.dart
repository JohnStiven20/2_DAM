import 'package:flutter/material.dart';

class DialogosPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Ejemplos de Alert'),
      ),
      body: ListView(
        children: [
          TextButton(
            onPressed: () => _mostrarAlerta1(context),
            style: ElevatedButton.styleFrom(
                shape: StadiumBorder()),
            child: Text(
              'Alerta 1',
              style: TextStyle(color: Colors.yellow),
            ),
          ),
        ],
      ),
    );
  }

  void _mostrarAlerta1(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Título del diálogo'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                  'Contenido del diálogo. Este mensaje se cerrará automáticamente en 5 segundos.'),
              FlutterLogo(size: 50),
            ],
          ),
        );
      },
    );

    // Cierra el diálogo automáticamente después de 5 segundos
    Future.delayed(Duration(seconds: 5), () {
      Navigator.of(context).pop(); // Cierra el diálogo
    });
  }
}
