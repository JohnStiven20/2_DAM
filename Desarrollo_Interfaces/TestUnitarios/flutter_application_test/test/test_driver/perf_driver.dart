import 'package:flutter_driver/flutter_driver.dart' as driver;
import 'package:integration_test/integration_test_driver.dart';

Future<void> main() {                                  //Realiza una operación asincrónica dentro de ella
  return integrationDriver(                           //Maneja la interacción con la aplicación de prueba y recopila datos durante la ejecución de las pruebas
    responseDataCallback: (data) async {  //Callback que se ejecuta cuando la prueba de integración devuelve datos
      if (data != null) {                                      //Se asegura de que los datos no sean nulos
        final timeline = driver.Timeline.fromJson(   //Los datos recibidos (data) se analizan como una estructura JSON
            data['scrolling_summary'] as Map<String, dynamic>);   //Representa el tiempo transcurrido para las acciones de la aplicación

        final summary = driver.TimelineSummary.summarize(timeline);   // Obtiene un resumen de la línea de tiempo

        await summary.writeTimelineToFile(            //Resumen de la línea de tiempo (summary) se guarda en un archivo
          'scrolling_summary',
          pretty: true,
          includeSummary: true,
        );
      }
    },
  );
}