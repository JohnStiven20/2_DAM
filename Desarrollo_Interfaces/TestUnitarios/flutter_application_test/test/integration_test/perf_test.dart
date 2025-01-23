import 'package:flutter/material.dart';
import 'package:flutter_application_test/main.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  group('Testing App Performance', () {        // Para agrupar pruebas
    final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();     //Inicializa el entorno de pruebas de integración
    binding.framePolicy = LiveTestWidgetsFlutterBindingFramePolicy.fullyLive;  //Establece la política de fotogramas para las pruebas

    testWidgets('Scrolling test', (tester) async {                       //Define la prueba de widgets llamada "Scrolling test"
      await tester.pumpWidget(const TestingApp());                //Carga el widget TestingApp para que la prueba lo ejecute

      final listFinder = find.byType(ListView);                           //Busca un widget de tipo ListView en la UI

      await binding.traceAction(() async {                                //Utiliza traceAction para medir y realizar un seguimiento del rendimiento de la acción
        await tester.fling(listFinder, const Offset(0, -500), 10000);   //Simula un gesto de deslizamiento (desplazamiento) en el ListView
        await tester.pumpAndSettle();                                         //Para esperar a que la animación y el renderizado se completen

        await tester.fling(listFinder, const Offset(0, 500), 10000);     //Simula un segundo desplazamiento, pero esta vez hacia abajo.
        await tester.pumpAndSettle();                                        //Se espera nuevamente
      }, reportKey: 'scrolling_summary');                                //Proporciona una clave identificadora para el informe generado
    });
  });
}